package com.ipaylinks.mobile.ipaylinkssdk.context;

import java.io.Serializable;

/**
 * Created by happy on 2017/5/26.
 */

public class BaseContext implements Serializable {



    private String language;

    private String device_type;
    private String device_name;

    private String device_sn;

    private String mac_address;

    private String mobile_number_1;
    private String mobile_number_2;

    private  String screeen_size;
    private String os;

    private String location;
    private String mobile_app_market;

    private String service;
    private String version;

    private String partner_id;
    private String appid;

    private String input_charset;
    private String device_id;

    private String access_channel;

    private String return_url;

    private String memo;

    private String sign;

    private String sign_type;

    private String imsi;

    private String screenOrientation;




    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getDevice_type() {
        return device_type;
    }

    public void setDevice_type(String device_type) {
        this.device_type = device_type;
    }

    public String getDevice_name() {
        return device_name;
    }

    public void setDevice_name(String device_name) {
        this.device_name = device_name;
    }

    public String getDevice_sn() {
        return device_sn;
    }

    public void setDevice_sn(String device_sn) {
        this.device_sn = device_sn;
    }

    public String getMac_address() {
        return mac_address;
    }

    public void setMac_address(String mac_address) {
        this.mac_address = mac_address;
    }

    public String getMobile_number_1() {
        return mobile_number_1;
    }

    public void setMobile_number_1(String mobile_number_1) {
        this.mobile_number_1 = mobile_number_1;
    }

    public String getMobile_number_2() {
        return mobile_number_2;
    }

    public void setMobile_number_2(String mobile_number_2) {
        this.mobile_number_2 = mobile_number_2;
    }

    public String getScreeen_size() {
        return screeen_size;
    }

    public void setScreeen_size(String screeen_size) {
        this.screeen_size = screeen_size;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getMobile_app_market() {
        return mobile_app_market;
    }

    public void setMobile_app_market(String mobile_app_market) {
        this.mobile_app_market = mobile_app_market;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getPartner_id() {
        return partner_id;
    }

    public void setPartner_id(String partner_id) {
        this.partner_id = partner_id;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getInput_charset() {
        return input_charset;
    }

    public void setInput_charset(String input_charset) {
        this.input_charset = input_charset;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public String getAccess_channel() {
        return access_channel;
    }

    public void setAccess_channel(String access_channel) {
        this.access_channel = access_channel;
    }

    public String getReturn_url() {
        return return_url;
    }

    public void setReturn_url(String return_url) {
        this.return_url = return_url;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getSign_type() {
        return sign_type;
    }

    public void setSign_type(String sign_type) {
        this.sign_type = sign_type;
    }


    public String getImsi() {
        return imsi;
    }

    public void setImsi(String imsi) {
        this.imsi = imsi;
    }


    public String getScreenOrientation() {
        return screenOrientation;
    }

    public void setScreenOrientation(String screenOrientation) {
        this.screenOrientation = screenOrientation;
    }
}
