package com.ipaylinks.mobile.ipaylinkssdk.model;
;

import android.text.TextUtils;

/**
 * 列表item的类型
 */
public enum IpayLinksCallBackEnum{
    OK("200"),                   // 成功
    PROCESS("201"),             //处理中

    ERROR_CODE_USER_CANCEL("102"),       // 用户主动取消流程
    ERROR_CODE_BUSINESS("105");          // 业务逻辑出错


    private String code;

    private IpayLinksCallBackEnum(String code){
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static IpayLinksCallBackEnum getByCode(String code){
        for(IpayLinksCallBackEnum item : values()){
            if(item.getCode().equalsIgnoreCase(code)){
                return item;
            }
        }
        return null;
    }
}
