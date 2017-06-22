package com.ipaylinks.mobile.ipaylinkssdk.model;

/**
 * Created by happy on 2017/6/1.
 */

public class IPayLinksSDKResultModel {

    // 返回交易结果Code(枚举) 类型为VFCallBackEnum
    private String resultCode;
    // 返回成功的消息
    private String message;
    //返回的json数据字符串
    private String jsonData;


    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getJsonData() {
        return jsonData;
    }

    public void setJsonData(String jsonData) {
        this.jsonData = jsonData;
    }
}
