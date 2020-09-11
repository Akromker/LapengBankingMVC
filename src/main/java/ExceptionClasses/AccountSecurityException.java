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
public class AccountSecurityException extends Exception{
    public AccountSecurityException(){
        super("PIN mismatch");
    }
    public AccountSecurityException(String message){
        super(message);
    }
}
