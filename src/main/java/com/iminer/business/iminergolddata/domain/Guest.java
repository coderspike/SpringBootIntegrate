package com.iminer.business.iminergolddata.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @ClassName Guest
 * @Description 嘉宾信息
 * @Author guowenbin
 * @Date 2018/11/15 10:23
 * @Version 1.0
 **/
@Getter
@Setter
@NoArgsConstructor
@Table(name = "iminer_gold_data_guest")
public class Guest {
    @Id
    @Column(name = "id")
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "company")
    private String company;
    @Column(name = "position")
    private String position;
    @Column(name = "contact")
    private String contact;
    @Column(name = "address")
    private String address;
}
