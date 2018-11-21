package com.iminer.business.iminergolddata.service;

import com.iminer.business.iminergolddata.domain.Guest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @ClassName GuestService
 * @Description 嘉宾操作service
 * @Author guowenbin
 * @Date 2018/11/15 11:33
 * @Version 1.0
 **/
@Service
public interface GuestService {
    /**
     * 添加
     *
     * @param guest
     * @return
     */
    Map<String, Object> add(Guest guest);

    /**
     * 查询方法
     *
     * @return
     */
    List<Guest> findAll();

    /**
     * 查询某个人
     */
    Guest findOne(String Id);
}
