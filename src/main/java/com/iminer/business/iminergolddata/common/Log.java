package com.iminer.business.iminergolddata.common;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @ClassName Log
 * @Description Log4j2辅助类
 * @Author guowenbin
 * @Date 2018/11/21 13:51
 * @Version 1.0
 **/
public class Log {
    private static final Logger LOGGER = LogManager.getLogger();

    private Log() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static Logger getLogger() {
        return LOGGER;
    }

    public static void trace(String msg) {
        LOGGER.trace(msg);
    }

    public static void debug(String msg) {
        LOGGER.debug(msg);
    }

    public static void info(String msg) {
        LOGGER.info(msg);
    }

    public static void info(String var1, Object... var2) {
        LOGGER.info(var1, var2);
    }

    public static void warn(String msg) {
        LOGGER.warn(msg);
    }

    public static void error(String msg) {
        LOGGER.error(msg);
    }
}
