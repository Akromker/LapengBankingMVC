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
public class AccountWithdrawException extends Exception{
    
    public AccountWithdrawException(){
        super("Amount less than minimum withdrawal accepted");
    }
    public AccountWithdrawException(String message){
        super(message);
    }
}
