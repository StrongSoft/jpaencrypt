package com.regur.jpaencrypt.model;

import com.regur.jpaencrypt.utils.CryptoUtil;

import javax.persistence.*;

/**
 * @author leeseungmin on 2019-09-17
 */
@Entity
public class User {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "name")
    private String encryptedName;
//    @Column(name="name")
//    @Length(max=500)
/*    @ColumnTransformer(
            read =  "AES_DECRYPT(UNHEX(name), 'mykey')",
            write = "HEX(AES_ENCRYPT(?, 'mykey'))"
    )*/
    @Transient
    private String name;

    @Column(name = "hp_no")
    private String encryptedHpNo;

//    @Column(name="hpNo")
//    @Length(max=500)
/*    @ColumnTransformer(
            read =  "AES_DECRYPT(UNHEX(hpNo), 'mykey')",
            write = "HEX(AES_ENCRYPT(?, 'mykey'))"
    )*/
    @Transient
    private String hpNo;

    public String getName() {
        if(name == null){
            name = CryptoUtil.decryptAES256(encryptedName);
        }
        return name;
    }

    public void setName(String name) {
        this.encryptedName = CryptoUtil.encryptAES256(name);
        this.name = name;
    }

    public String getHpNo() {
        if(hpNo == null){
            hpNo = CryptoUtil.decryptAES256(encryptedHpNo);
        }
        return hpNo;
    }

    public void setHpNo(String hpNo) {
        this.encryptedHpNo = CryptoUtil.encryptAES256(hpNo);
        this.hpNo = hpNo;
    }
}
