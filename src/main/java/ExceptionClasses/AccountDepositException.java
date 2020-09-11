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
public class AccountDepositException extends Exception{
    
    public AccountDepositException(){
        super("Amount less than minimum withdrawal accepted");
    }
    public AccountDepositException(String message){
        super(message);
    }
}
