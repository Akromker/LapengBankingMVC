/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ExceptionClasses;

/**
 *
 * @author Alex
 */
public class AccountBalanceException extends Exception{
    
    public AccountBalanceException(){
        super("Overdraft not allowed");
    }
    public AccountBalanceException(String message){
        super(message);
    }
}
