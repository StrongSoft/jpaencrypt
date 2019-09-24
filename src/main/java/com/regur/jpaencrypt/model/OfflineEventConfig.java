package com.regur.jpaencrypt.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author leeseungmin on 2019-09-24
 */
@Entity
@Getter@Setter
public class OfflineEventConfig {
    @Id
    @GeneratedValue
    private Long eventCd;

    private String eventNm;

    private Long deviceCd;

    private String deviceNm;

    private String useYn;
}
