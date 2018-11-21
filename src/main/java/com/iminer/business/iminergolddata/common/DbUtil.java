package com.iminer.business.iminergolddata.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DbUtil {

    @Autowired
    public DbOperateHelperImpl manage;

    @Autowired
    public DbOperateHelperImpl manageFans;

}
