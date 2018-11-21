package com.iminer.business.iminergolddata.common;

/**
 * @ClassName ResponseCode
 * @Description 封装返回码
 * @Author guowenbin
 * @Date 2018/9/5 10:03
 * @Version 1.0
 **/
public enum ResponseCode {
    SUCCESS(0, "SUCCESS"),
    ERROR(1, "ERROR");

    private final int code;
    private final String desc;

    ResponseCode(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
