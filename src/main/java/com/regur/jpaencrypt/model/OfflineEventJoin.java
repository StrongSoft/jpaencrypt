package com.regur.jpaencrypt.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author leeseungmin on 2019-09-24
 */
@Entity
@Getter
@Setter
public class OfflineEventJoin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userNm;

    private String hpNo;

    private String resvNo;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "eventCd")
    private OfflineEventConfig offlineEventConfig;
}
