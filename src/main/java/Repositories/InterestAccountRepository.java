/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Repositories;

import java.util.List;
import model.InterestAccount;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Alex
 */
public interface InterestAccountRepository extends JpaRepository<InterestAccount, String>{
    List<InterestAccount> findAccount(String name);
}
