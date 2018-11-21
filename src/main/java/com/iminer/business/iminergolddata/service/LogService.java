package com.iminer.business.iminergolddata.service;

import com.iminer.business.iminergolddata.domain.Log;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @ClassName GuestService
 * @Description 日志操作service
 * @Author guowenbin
 * @Date 2018/11/15 11:33
 * @Version 1.0
 **/
@Service
public interface LogService {
    /**
     * 添加
     *
     * @param log
     * @return
     */
    Map<String, Object> add(Log log);
}