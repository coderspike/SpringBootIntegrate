package com.iminer.business.iminergolddata.common;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.iminer.business.iminergolddata.util.Encryption;
import com.iminer.common.sms.ResultInfo;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@Getter
@Setter
public class ResponseObject<T> {
    private final T originalData;
    private final String data;
    //0没有加密 1加密
    private int isEncrypt;
    /************需要的附加信息***********/
    private List<Map<String, Object>> addition;
    //系统返回的结果编码 1 成功 0 失败
    private int result = 1;
    //获得的请求时间参数
    private long fetchTime;
    //传递给客户端的消息
    private String message;
    //错误代码
    private String errorCode;
    //登录状态
    private int loginStatus;
    //类表查询开始时间
    private String beginId;
    //列表查询结束id
    private String endId;
    // 数据总量
    private int totalNo;
    // 数据剩余量
    private int residueNum;
    //更新的数量
    private int updatedNum;
    //当前页码
    private int pageNum;
    //一页的记录条数上限
    private int pageLimit;
    //本次获取数据的时间戳
    private long lastFetchTime;
    private Long totalNumber;

    //构造函数
    @SuppressWarnings("unchecked")
    public ResponseObject(T data) {
        this.lastFetchTime = System.currentTimeMillis();
        String encryptResult = Encryption.encryptToAESPKCS5FromObject(data, this.lastFetchTime + "000");
        if (encryptResult != null) {
            this.data = encryptResult;
            this.isEncrypt = 1;
        } else {
            this.data = JSONObject.toJSONString(data);
            this.isEncrypt = 0;
        }
        String profile = SpringContextUtil.getActiveProfile();
        // 只能开发环境下下返回原始Data，否则永远都不返回原始Data
        if ("development".equals(profile) || "test".equals(profile)) {
            this.originalData = data;
        } else {
            this.originalData = null;
        }
        this.result = ResultInfo.STATUS_SUCC;
    }

    /************ getter setter ******************/

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public long getLastFetchTime() {
        return lastFetchTime;
    }

    public void setLastFetchTime(long lastFetchTime) {
        this.lastFetchTime = lastFetchTime;
    }

    public T getOriginalData() {
        return originalData;
    }

    public String getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getLoginStatus() {
        return loginStatus;
    }

    public void setLoginStatus(int loginStatus) {
        this.loginStatus = loginStatus;
    }

    public int getResidueNum() {
        return residueNum;
    }

    public void setResidueNum(int residueNum) {
        this.residueNum = residueNum;
    }

    public List<Map<String, Object>> getAddition() {
        return addition;
    }

    public void setAddition(List<Map<String, Object>> addition) {
        this.addition = addition;
    }

    public Long getTotalNumber() {
        return totalNumber;
    }

    public void setTotalNumber(Long totalNumber) {
        this.totalNumber = totalNumber;
    }

    public int getIsEncrypt() {
        return isEncrypt;
    }

    public void setIsEncrypt(int isEncrypt) {
        this.isEncrypt = isEncrypt;
    }
}
