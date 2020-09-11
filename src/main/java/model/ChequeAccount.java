package model;

import ExceptionClasses.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.swing.JOptionPane;
import org.springframework.stereotype.Component;

/**
 *
 * @author Alex
 */
@Component("ChequeAccountBean")
@Table(name = "ChequeAccounts")
public class ChequeAccount extends BasicAccount {

    String name, pin;
    double balance, rate;
    double interest = 0;
    double minBalance = 100;
    double penalty = 10.00;
    int transactions = 0;
    @Id
    private Long id;

    public ChequeAccount(String Name, String Pin, double Balance) {
        name = Name;
        pin = Pin;
        balance = Balance;

        try {
            CheckAccountName(name);
            toFile();
        } catch (IOException | AccountExistException ex) {
            Logger.getLogger(ChequeAccount.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public double balance() {
        return balance;
    }

    @Override
    public double rate() {
        return rate;
    }

    @Override
    public void deposit(double amount, String Pin) throws AccountDepositException, AccountSecurityException {
        double cost = (balance / 100) * 20;

        if (!checkPin(Pin)) {
            throw new AccountSecurityException();
        }
        if (amount <= 0) {
            throw new AccountDepositException();
        }
        if (amount <= (balance - cost)) {
            balance -= cost;
            balance += amount;
        }
    }

    @Override
    public void withdraw(double amount, String Pin) throws AccountWithdrawException, AccountBalanceException, AccountSecurityException {
        if (!checkPin(Pin)) {
            throw new AccountSecurityException();
        }
        if (amount <= 0) {
            throw new AccountWithdrawException();
        }
        if (amount <= (balance - 0.1)) {
            balance -= amount;
            transactions += 1;
        } else {
            throw new AccountBalanceException();
        }
    }

    @Override
    public void setRate(double Rate) {
        this.rate = Rate;
    }

    @Override
    public void monthly_update() {
        try {
            double fee = computeFees();

            balance += computeInterest();
            interest += computeInterest();

            if (balance - fee >= 0) {
                balance -= fee;
            } else {
                JOptionPane.showMessageDialog(null, "Not enough funds");
            }
        } catch (AccountBalanceException ex) {
            Logger.getLogger(ChequeAccount.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public double computeFees() throws AccountBalanceException {
        double smaller = balance / 100 * 10;
        double cost = transactions * 0.1;
        smaller = Double.min(smaller, 10);

        if (smaller >= balance) {
            throw new AccountBalanceException();
        }

        if ((balance - smaller) < minBalance) {
            return smaller + penalty + cost;
        } else {
            return smaller + cost;
        }
    }

    @Override
    public double computeInterest() {
        return (balance * 0.07);
    }

    @Override
    public String toString() {
        return "Account name: " + name + "\n"
                + "Current Balance: " + String.valueOf(balance) + "\n"
                + "Rates: 7% monthly";
    }

    @Override
    public boolean checkPin(String Pin) {
        return this.pin.equals(Pin);
    }

    @Override
    public void toFile() throws IOException {
        File file = new File("Accounts.txt");
        String str = name;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(str);
        }
    }

    @Override
    public final void CheckAccountName(String Name) throws AccountExistException {
        FileReader fr = null;
        boolean exists = false;
        try {
            File file = new File("Accounts.txt");
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                if (line.equals(name)) {
                    throw new AccountExistException();
                }
            }
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Exception ocurred: FileNotFoundException");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "IOException");
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
