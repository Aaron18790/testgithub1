package com.ipaylinks.mobile.ipaylinkssdk.context;


import com.ipaylinks.mobile.ipaylinkssdk.activity.ActivityStackManager;

/**
 * Created by happy on 2017/5/26.
 */


public class PaymentContext extends BaseContext {

    private  String server_url;

    private String outer_trade_no;

    private String currency;


    private String telco;

    private String     channel_type;
    private String       payway;


    private String         price;

    private String    priceid;
    private String           product_name;

    private String    buyer_id;
    private String          buyer_id_type;
    private String primary_phone_number;
    private String  second_phone_number;
    private String serial;//留着备用
    private String        pin;//留着备用
    private String  extend;



    private static PaymentContext instance;

    private  PaymentContext(){

    }

    public static synchronized PaymentContext getInstance(){
        if(instance==null){
            instance=new PaymentContext();
        }
        return instance;
    }

    public String getOuter_trade_no() {
        return outer_trade_no;
    }

    public void setOuter_trade_no(String outer_trade_no) {
        this.outer_trade_no = outer_trade_no;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getTelco() {
        return telco;
    }

    public void setTelco(String telco) {
        this.telco = telco;
    }

    public String getChannel_type() {
        return channel_type;
    }

    public void setChannel_type(String channel_type) {
        this.channel_type = channel_type;
    }

    public String getPayway() {
        return payway;
    }

    public void setPayway(String payway) {
        this.payway = payway;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPriceid() {
        return priceid;
    }

    public void setPriceid(String priceid) {
        this.priceid = priceid;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getBuyer_id() {
        return buyer_id;
    }

    public void setBuyer_id(String buyer_id) {
        this.buyer_id = buyer_id;
    }

    public String getBuyer_id_type() {
        return buyer_id_type;
    }

    public void setBuyer_id_type(String buyer_id_type) {
        this.buyer_id_type = buyer_id_type;
    }

    public String getPrimary_phone_number() {
        return primary_phone_number;
    }

    public void setPrimary_phone_number(String primary_phone_number) {
        this.primary_phone_number = primary_phone_number;
    }

    public String getSecond_phone_number() {
        return second_phone_number;
    }

    public void setSecond_phone_number(String second_phone_number) {
        this.second_phone_number = second_phone_number;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getExtend() {
        return extend;
    }

    public void setExtend(String extend) {
        this.extend = extend;
    }

    public String getServer_url() {
        return server_url;
    }
    public void setServer_url(String server_url) {
        this.server_url = server_url;
    }
}
