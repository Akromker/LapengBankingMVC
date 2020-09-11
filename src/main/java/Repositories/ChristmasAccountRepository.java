/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Repositories;

import java.util.List;
import model.christmasAccount;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Alex
 */
public interface ChristmasAccountRepository extends JpaRepository<christmasAccount, String>{
    List<christmasAccount> findAccount(String name);
}
