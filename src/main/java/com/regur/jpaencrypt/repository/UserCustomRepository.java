package com.regur.jpaencrypt.repository;

import java.util.List;

/**
 * @author leeseungmin on 2019-09-27
 */
public interface UserCustomRepository<T> {
    <S extends T> S encryptSave(S entity);
    List<T> findByHpNo(String hpNo);
}
