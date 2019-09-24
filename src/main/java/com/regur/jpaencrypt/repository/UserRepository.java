package com.regur.jpaencrypt.repository;

import com.regur.jpaencrypt.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author leeseungmin on 2019-09-17
 */
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByHpNo(String hpNo);
}
