/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import ExceptionClasses.*;
import java.io.IOException;
import javax.persistence.Column;


/**
 *
 * @author Alex
 */
public abstract class BasicAccount {
    @Column(name = "name")
    public abstract String name();
    @Column(name = "balance")
    public abstract double balance();
    @Column(name = "rate")
    public abstract double rate();
    @Column(name = "deposits")
    public abstract void deposit(double amount,String Pin) throws AccountDepositException,AccountSecurityException;
    @Column(name = "withdrawals")
    public abstract void withdraw(double amount, String Pin) throws AccountWithdrawException,AccountBalanceException,AccountSecurityException;
    @Column(name = "Rates")
    public abstract void setRate(double Rate);
    public abstract boolean checkPin(String Pin);
    public abstract void monthly_update();
    public abstract double computeFees() throws AccountBalanceException;
    public abstract double computeInterest();
    public abstract void toFile() throws IOException;
    public abstract void CheckAccountName(String Name) throws AccountExistException;
    @Override
    public abstract String toString();
}
