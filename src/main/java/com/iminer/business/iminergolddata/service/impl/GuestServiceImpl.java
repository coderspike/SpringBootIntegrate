package com.iminer.business.iminergolddata.service.impl;

import com.google.common.collect.Maps;
import com.iminer.business.iminergolddata.common.DbUtil;
import com.iminer.business.iminergolddata.common.Log;
import com.iminer.business.iminergolddata.domain.Guest;
import com.iminer.business.iminergolddata.mapper.GuestMapper;
import com.iminer.business.iminergolddata.service.GuestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @ClassName GuestServiceImpl
 * @Description 嘉宾操作serviceImpl
 * @Author guowenbin
 * @Date 2018/11/15 11:35
 * @Version 1.0
 **/
@Component
public class GuestServiceImpl implements GuestService {

    @Autowired
    GuestMapper guestMapper;

    @Autowired
    @Qualifier("primaryJdbcTemplate")
    protected JdbcTemplate jdbcTemplate;

    @Autowired
    DbUtil dbUtil;

    /**
     * 添加方法
     *
     * @param guest
     * @return
     */
    @Override
    public Map<String, Object> add(Guest guest) {
        int i = guestMapper.insert(guest);
        Map<String, Object> res = Maps.newHashMap();
        if (i > 0) {
            res.put("result", 1);
            res.put("message", "success");
            return res;
        } else {
            res.put("result", 0);
            res.put("message", "fail");
            return res;
        }
    }

    @Override
    public List<Guest> findAll() {
        List<Guest> list = guestMapper.selectAll();
        return list;
    }

    @Override
    public Guest findOne(String id) {
        //方法一：用jdbcTemplate
        String sql = "select * from iminer_gold_data_guest where id =" + id;
        Log.info(jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Guest.class)).getName());
        //方法二：用加载完毕封装好的dbUtil
        Log.info(dbUtil.manageFans.searchDataInfo(sql, Guest.class).getName());
        Log.info(dbUtil.manage.searchDataInfo(sql, Guest.class).getName());
        return null;
    }
}
