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
public class AccountExistException extends Exception{
    public AccountExistException(){
        super("Account already exists");
    }
    public AccountExistException(String message){
        super(message);
    }
}
