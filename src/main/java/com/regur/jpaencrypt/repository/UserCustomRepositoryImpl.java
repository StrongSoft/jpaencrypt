package com.regur.jpaencrypt.repository;

import com.regur.jpaencrypt.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author leeseungmin on 2019-09-27
 */
@Repository
public class UserCustomRepositoryImpl implements UserCustomRepository {


    @Value("${app.secretkey}")
    private String secretkey;

    @PersistenceContext
    EntityManager entityManager;

    @Transactional
    public Object encryptSave(Object entity) {
        User user = (User)entity;
        System.out.println("SECRET_KEY : "+secretkey);
        System.out.println("encryptSave : "+user.toString());
        entityManager.createNativeQuery("insert into user (hp_no, name) values (HEX(AES_ENCRYPT(?, '"+secretkey+"')), HEX(AES_ENCRYPT(?, '"+secretkey+"')))", User.class)
                .setParameter(1,user.getHpNo())
                .setParameter(2,user.getName())
                .executeUpdate();
        return user;
    }
    @Override
    /*public List<User> findByHpNo(String hpNo) {
        return entityManager.createNativeQuery("select id, cast(AES_DECRYPT(UNHEX(hp_no), '"+secretkey+"') as char(255)) as hp_no, "+
                            "cast(AES_DECRYPT(UNHEX(name), '"+secretkey+"') as char(255)) as name from user " +
                            "where cast(AES_DECRYPT(UNHEX(hp_no), '"+secretkey+"') as char(255)) = ?", User.class)
                            .setParameter(1, hpNo)
                            .getResultList();
    }*/
    public List<User> findByHpNo(String hpNo) {
        return entityManager.createNativeQuery("select id, cast(AES_DECRYPT(UNHEX(hp_no), '"+secretkey+"') as char(255)) as hp_no, "+
                "cast(AES_DECRYPT(UNHEX(name), '"+secretkey+"') as char(255)) as name from user " +
                "where cast(AES_DECRYPT(UNHEX(hp_no), '"+secretkey+"') as char(255)) = ?", User.class)
                .setParameter(1, hpNo)
                .getResultList();
    }
}
