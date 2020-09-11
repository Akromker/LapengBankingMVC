/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import org.springframework.stereotype.Component;

/**
 *
 * @author Alex
 */
@Component("AutPayAccountBean")
@Table(name = "AutoPayAccounts")
public class AutoPayAccount extends ChequeAccount{
    int months =0;
    int periods;
    boolean paying = true;
    double payment_amount;
    @Id
    private long id;
    
    public AutoPayAccount(String Name, String Pin, double Balance) {
        super(Name, Pin, Balance);
    }

    @Override
    public void monthly_update() {
        super.monthly_update();
        months +=1;
        
        if((months%periods == 0) && (paying == true)){
            balance -= payment_amount;
        }
    }
    
    
    public void autoPay(String name, double amount, int months){
        paying = true;
        periods = months;
        payment_amount=amount;
    }
    public void endPaymenst(){
        paying = false;   
    }

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
