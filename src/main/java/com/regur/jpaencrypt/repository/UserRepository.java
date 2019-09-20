package com.regur.jpaencrypt.repository;

import com.regur.jpaencrypt.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author leeseungmin on 2019-09-17
 */
public interface UserRepository extends JpaRepository<User, Long> {

}
