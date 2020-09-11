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
@Component("InterestAccountBean")
@Table(name = "InterestAccounts")
public class InterestAccount extends BasicAccount {

    String name, pin;
    double balance;
    double rate = 0.07;
    @Id
    private Long id;

    public InterestAccount(String Name, String Pin, double Balance) {
        name = Name;
        pin = Pin;
        balance = Balance;
        
        try {
            CheckAccountName(Name);
            toFile();
        } catch (IOException | AccountExistException ex) {
            Logger.getLogger(InterestAccount.class.getName()).log(Level.SEVERE, null, ex);
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
    public void deposit(double amount,String Pin) throws AccountDepositException,AccountSecurityException {
        if(!checkPin(Pin))
            throw new AccountSecurityException();
        if (amount <= 0) {
            throw new AccountDepositException();
        }
        balance += amount;

    }

    @Override
    public void withdraw(double amount, String Pin) throws AccountWithdrawException,AccountBalanceException, AccountSecurityException {
        if(!checkPin(Pin))
            throw new AccountSecurityException();
        if (amount <= 0) {
            throw new AccountWithdrawException();
        }
        if (amount <= balance) {
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
            Logger.getLogger(InterestAccount.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public double computeFees() throws AccountBalanceException{
        double smaller = balance / 100 * 10;
        smaller = Double.min(smaller, 10);
        
        if(smaller>=balance)
            throw new AccountBalanceException();
        
        return smaller;
    }

    @Override
    public double computeInterest() {
        return (balance * rate);
    }

    @Override
    public String toString() {
        return "Account name: " + name + "\n"
                + "Current Balance: " + String.valueOf(balance) + "\n"
                + "Rates: " + String.valueOf(rate) + " monthly";
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
