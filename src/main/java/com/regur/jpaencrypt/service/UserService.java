package com.regur.jpaencrypt.service;

import com.regur.jpaencrypt.model.User;
import com.regur.jpaencrypt.repository.UserCustomRepository;
import com.regur.jpaencrypt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author leeseungmin on 2019-09-23
 */
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserCustomRepository userCustomRepository;

    public User save(User user){
         return (User)userCustomRepository.encryptSave(user);
    }

    public List<User> findByHpNo(String hpNo){
        return userCustomRepository.findByHpNo(hpNo);
    }
}
