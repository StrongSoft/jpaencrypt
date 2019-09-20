package com.regur.jpaencrypt.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author leeseungmin on 2019-09-19
 */
@Component
public class GlobalValue {
    public static String SECRET_KEY;

    @Value("${medialog.secretkey}")
    public void setSecretKey(String key) {
        GlobalValue.SECRET_KEY = key;
    }
}
