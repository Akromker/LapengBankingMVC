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
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.swing.JOptionPane;
import org.springframework.stereotype.Component;

/**
 *
 * @author Alex
 */
@Component("CDaccountBean")
@Table(name = "CDaccounts")
public class CDaccount extends BasicAccount {

    String name, pin;
    double balance, interest;
    double rate = 0.15;
    @Id
    private Long id;

    public CDaccount(String Name, String Pin, double Balance) throws AccountExistException {
        name = Name;
        pin = Pin;
        balance = Balance;
        
        CheckAccountName(name);
        
        try {
            CheckAccountName(Name);
            toFile();
        } catch (IOException ex) {
            Logger.getLogger(CDaccount.class.getName()).log(Level.SEVERE, null, ex);
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
        double cost = (balance / 100) * 20;

        if (!checkPin(Pin)) {
            throw new AccountSecurityException();
        }
        if (amount <= 0) {
            throw new AccountWithdrawException();
        }

        if (amount <= (balance - cost)) {
            balance -= cost;
            balance -= amount;
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
            balance += computeInterest();
            balance -= computeFees();
        } catch (AccountBalanceException ex) {
            Logger.getLogger(CDaccount.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public double computeFees() throws AccountBalanceException {
        double smaller = balance / 100 * 10;
        smaller = Double.min(smaller, 10);

        if (smaller >= balance) {
            throw new AccountBalanceException();
        }

        return smaller;
    }

    @Override
    public double computeInterest() {
        return balance * (rate / 12);
    }

    @Override
    public String toString() {
        return "Account name: " + name + "\n"
                + "Current Balance: " + String.valueOf(balance) + "\n"
                + "Rates: " + String.valueOf(rate) + " per annum";
    }

    @Override
    public boolean checkPin(String Pin) {
        return this.pin.equals(Pin);
    }

    @Override
    public  void toFile() throws IOException{
        File file = new File("Accounts.txt");
        String str = name;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(str);
        }
    }
    @Override
    public final void CheckAccountName(String Name) throws AccountExistException{
        FileReader fr = null;
        boolean exists = false;
        try {
            File file = new File("Accounts.txt");
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                if(line.equals(name))
                    throw new AccountExistException();
            }
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "FileNotFoundException");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "IOexception");
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
