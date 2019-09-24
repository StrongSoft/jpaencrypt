package com.regur.jpaencrypt.model;

import lombok.ToString;
import org.hibernate.annotations.ColumnTransformer;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author leeseungmin on 2019-09-17
 */
@Entity
@ToString
public class User {
    @Id
    @GeneratedValue
    private Long id;

    @ColumnTransformer(
            read =  "cast(AES_DECRYPT(UNHEX(name), '${encryption.key}') as char(255))",
            write = "HEX(AES_ENCRYPT(?, '${encryption.key}'))"
    )
    private String name;

    @ColumnTransformer(
            read =  "cast(AES_DECRYPT(UNHEX(hp_no), '${encryption.key}') as char(255))",
            write = "HEX(AES_ENCRYPT(?, '${encryption.key}'))"
    )
    private String hpNo;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHpNo() {
        return hpNo;
    }

    public void setHpNo(String hpNo) {
        this.hpNo = hpNo;
    }
}
