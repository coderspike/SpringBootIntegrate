package com.iminer.business.iminergolddata.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @ClassName Log
 * @Description 日志
 * @Author guowenbin
 * @Date 2018/11/15 17:16
 * @Version 1.0
 **/
@Getter
@Setter
@NoArgsConstructor
@Table(name = "iminer_gold_data_log")
public class Log {
    @Id
    @Column(name = "id")
    private Integer id;
    @Column(name = "ip")
    private String ip;
    @Column(name = "create_time")
    private String createTime;
    @Column(name = "userAgent")
    private String userAgent;
}
