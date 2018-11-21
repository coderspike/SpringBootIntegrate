package com.iminer.business.iminergolddata.service.impl;

import com.google.common.collect.Maps;
import com.iminer.business.iminergolddata.domain.Log;
import com.iminer.business.iminergolddata.mapper.LogMapper;
import com.iminer.business.iminergolddata.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @ClassName GuestServiceImpl
 * @Description 日志操作serviceImpl
 * @Author guowenbin
 * @Date 2018/11/15 11:35
 * @Version 1.0
 **/
@Component
public class LogServiceImpl implements LogService {

    @Autowired
    LogMapper logMapper;

    /**
     * 添加方法
     *
     * @param log
     * @return
     */
    @Override
    public Map<String, Object> add(Log log) {
        int i = logMapper.insert(log);
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
}
