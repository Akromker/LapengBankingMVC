/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Repositories;

import java.util.List;
import model.ChequeAccount;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Alex
 */
public interface ChequeAccountRepository extends JpaRepository<ChequeAccount, String>{
    List<ChequeAccount> findAccount(String name);
}
