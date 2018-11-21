package com.iminer.business.iminergolddata.common;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * @ClassName MyMapper
 * @Description 通用mapper
 * @Author guowenbin
 * @Date 2018/9/5 9:47
 * @Version 1.0
 **/
public interface MyMapper<T> extends Mapper<T>, MySqlMapper<T> {
}
