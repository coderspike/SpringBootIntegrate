package com.iminer.business.iminergolddata.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * 封装事务的具体数据库操作实现类
 */
public class DbOperateHelperImpl extends BaseDbOperateHelper implements BeanNameAware {

    private static final Logger LOG = LoggerFactory.getLogger(DbOperateHelperImpl.class);

    private String beanName;

    @Override
    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public DataSourceTransactionManager dataSourceTransactionManager;

    public DataSourceTransactionManager getDataSourceTransactionManager() {
        return dataSourceTransactionManager;
    }

    public void setDataSourceTransactionManager(DataSourceTransactionManager dataSourceTransactionManager) {
        this.dataSourceTransactionManager = dataSourceTransactionManager;
    }

    public void initialize() {
        DataSource writeDataSource = dataSourceTransactionManager.getDataSource();
        try {
            writeDataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        writeJdbcTemplate = new JdbcTemplate(writeDataSource);

        LOG.info("DbOperateHelperImpl[" + beanName + "] is Ok");
    }
}
