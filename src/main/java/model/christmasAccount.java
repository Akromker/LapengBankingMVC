/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import javax.persistence.Table;
import javax.swing.JOptionPane;

/**
 *
 * @author Alex
 */
//@Component("christmasAccount")
@Table(name = "christmasAccounts")
public class christmasAccount extends BasicAccount {

    private String name;
    private double balance;
    private String pin;
    private int months;
    private double rate;

    public christmasAccount() {
        super();
    }

    public christmasAccount(String name, double balance, String pin) {
        super();
        this.name = name;
        this.balance = balance;
        this.pin = pin;
        this.months = 0;
        this.rate = 20 / 100 / 12;
        
        try {
            CheckAccountName(name);
            toFile();
        } catch (IOException | AccountExistException ex) {
            Logger.getLogger(christmasAccount.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String name() {
        return this.name;
    }

    @Override
    public double balance() {
        return this.balance;
    }

    @Override
    public double rate() {
        return this.rate;
    }

    @Override
    public void deposit(double amount,String Pin) throws AccountDepositException, AccountSecurityException {
        if(!checkPin(Pin))
            throw new AccountSecurityException();
        if (amount <= 0) {
            throw new AccountDepositException();
        }
        balance += amount;
    }

    @Override
    public void withdraw(double amount, String Pin) throws AccountWithdrawException, AccountBalanceException, AccountSecurityException {
        if (amount <= 0) {
            throw new AccountWithdrawException();
        }
        if (amount > balance) {
            throw new AccountBalanceException();
        }
        if (months >= 12) {
            balance -= amount;
        } else {
            System.out.println("Withdraw rejected");
        }
    }

    @Override
    public void setRate(double rate) {
        this.rate = rate;
    }

    @Override
    public void monthly_update() {
        balance += computeInterest();

        months++;
    }

    @Override
    public double computeFees() {
        return 0;
    }

    @Override
    public double computeInterest() {
        return balance * rate();
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean checkPin(String Pin) {
        return this.pin.equals(Pin);
    }
    
    @Override
    public final  void toFile() throws IOException{
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

}
