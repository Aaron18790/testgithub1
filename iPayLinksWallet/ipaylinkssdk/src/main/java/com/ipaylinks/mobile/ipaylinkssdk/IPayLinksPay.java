package com.ipaylinks.mobile.ipaylinkssdk;

/**
 * Created by happy on 2017/5/26.
 */

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;

import com.ipaylinks.mobile.ipaylinkssdk.activity.CashierActivity;
import com.ipaylinks.mobile.ipaylinkssdk.activity.IpayLinksCashierActivity;
import com.ipaylinks.mobile.ipaylinkssdk.common.Utils;
import com.ipaylinks.mobile.ipaylinkssdk.context.PaymentContext;
import com.ipaylinks.mobile.ipaylinkssdk.context.UserPaymentContext;

import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.widget.Toast;

import pub.devrel.easypermissions.EasyPermissions;

import static java.security.AccessController.getContext;

public class IPayLinksPay {



    private  Handler callbackHandler;

    private static IPayLinksPay mInstance;

    private IPayLinksPay() {
    }
    public static synchronized IPayLinksPay getInstance() {
        if (mInstance == null) {
            mInstance = new IPayLinksPay();
        }
        return mInstance;
    }

    public Handler getCallbackHandler() {
        return callbackHandler;
    }

    public void setCallbackHandler(Handler callbackHandler) {
        this.callbackHandler = callbackHandler;
    }

    /*
         * 初次调用需要调用初始化
         */
    public void init(Context context,String partnerId) {


    }

    public void init(Context context,String  serverUrl,String language, String ek, String  productId, String channelId)
    {
        Utils.getInstance().init(context.getApplicationContext());

        PaymentContext.getInstance().setLanguage(language);
        PaymentContext.getInstance().setServer_url(serverUrl);
        PaymentContext.getInstance().setPartner_id(ek);

        PaymentContext.getInstance().setAppid(productId);
        PaymentContext.getInstance().setMobile_app_market(channelId);


       /* if (EasyPermissions.hasPermissions(context, Manifest.permission.READ_PHONE_STATE)) {
            // Have permission, do the thing!
            //            doLogin();
            //if (checkTextViewIsEmpty())
            //    actLogin();

            PaymentContext.getInstance().setDevice_id(Utils.getInstance().getPhoneDeviceId());
            PaymentContext.getInstance().setMobile_number_1(Utils.getInstance().getLine1Number());

            PaymentContext.getInstance().setDevice_sn(Utils.getInstance().getSimSerialNumber());

        } else {
            // Ask for one permission
            EasyPermissions.requestPermissions(this, "读取设备信息方便使用后续服务",
                    110, Manifest.permission.READ_PHONE_STATE);
        }


        if (EasyPermissions.hasPermissions(context, Manifest.permission.BLUETOOTH)) {
            BluetoothAdapter mBluetoothAdapter= BluetoothAdapter.getDefaultAdapter();
            if(mBluetoothAdapter!=null){
                PaymentContext.getInstance().setDevice_name(mBluetoothAdapter.getName());
            }

        } else {
            // Ask for one permission
            EasyPermissions.requestPermissions(this, "读取手机名称方便使用后续服务",
                    110, Manifest.permission.BLUETOOTH);
        }
        */




        PaymentContext.getInstance().setDevice_type(android.os.Build.MODEL );

        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        int screenWidth = dm.widthPixels;
        int screenHeight = dm.heightPixels;
        PaymentContext.getInstance().setScreeen_size(""+screenWidth + "*" + screenHeight);



        PaymentContext.getInstance().setVersion("1.0.0");
        PaymentContext.getInstance().setAccess_channel("SDK");

        PaymentContext.getInstance().setOs("android");


        //int i = this.getResources().getConfiguration().orientation;


        //mac地址不好获取，各个版本不同，且要权限

        //context.getResources().getDisplayMetrics();

        //DisplayMetrics dm2 = getResources().getDisplayMetrics();





        //PaymentContext.getInstance().set


    }

    public void BackendPsmsPay(Context context,UserPaymentContext payattr, Handler callbackHandler)
    {
        copyPayAttr(payattr);
        PaymentContext.getInstance().setChannel_type("BackendPsmsPay");

        Pay(context,callbackHandler,payattr.isFullScreen(),payattr.isHorizonScreen());

    }

    public void BackendCashcardPay(Context context,UserPaymentContext payattr, Handler callbackHandler)
    {
        copyPayAttr(payattr);
        PaymentContext.getInstance().setChannel_type("BackendCashcardPay");
        Pay(context,callbackHandler,payattr.isFullScreen(),payattr.isHorizonScreen());

    }

    public void WebPagePay(Context context,UserPaymentContext payattr, Handler callbackHandler)
    {
        copyPayAttr(payattr);
        PaymentContext.getInstance().setChannel_type("WebPagePay");
        Pay(context,callbackHandler,payattr.isFullScreen(),payattr.isHorizonScreen());

    }

    public void SDKPlusPay(Context context,UserPaymentContext payattr, Handler callbackHandler)
    {
        copyPayAttr(payattr);
        PaymentContext.getInstance().setChannel_type("SDKPlusPay");
        Pay(context,callbackHandler,payattr.isFullScreen(),payattr.isHorizonScreen());

    }

    private void copyPayAttr(UserPaymentContext payattr)
    {
        PaymentContext.getInstance().setBuyer_id(payattr.getBuyer_id());
        PaymentContext.getInstance().setBuyer_id_type(payattr.getBuyer_id_type());

        //PaymentContext.getInstance().setChannel_type(payattr.get);
        PaymentContext.getInstance().setCurrency(payattr.getCurrency());


        PaymentContext.getInstance().setOuter_trade_no(payattr.getTransactionId());
        PaymentContext.getInstance().setPrimary_phone_number(payattr.getPrimary_phone_number());

        PaymentContext.getInstance().setSecond_phone_number(payattr.getSecond_phone_number());

        PaymentContext.getInstance().setExtend(payattr.getExtend());

        PaymentContext.getInstance().setTelco(payattr.getTelco());
        PaymentContext.getInstance().setProduct_name(payattr.getProduct_name());

        PaymentContext.getInstance().setSign(payattr.getSign());
        PaymentContext.getInstance().setSign_type(payattr.getSignType());


        PaymentContext.getInstance().setPrice(payattr.getPrice());
        PaymentContext.getInstance().setPriceid(payattr.getPriceid());



        if (payattr.getBuyer_id_type() != null)
        {
            PaymentContext.getInstance().setBuyer_id_type(payattr.getBuyer_id_type());
        }


    }

    /**
     * 显示toast
     * @param text 文字
     * @param duration
     */
    public void showToast(Context context,String text,int duration) {
        Toast.makeText(context, text, duration).show();
    }


    //这个参数是有点问题的，可以从外面复制进来，
    //public void Pay(Context context, PaymentContext paymentContext, Handler callbackHandler) {
    public void Pay(Context context, Handler callbackHandler,Boolean isFullScreen,Boolean isHorizonScreen) {

        //矫正参数
        //paymentContext.setLocation();


        if(!TextUtils.isEmpty(PaymentContext.getInstance().getOuter_trade_no())){
            if  ((PaymentContext.getInstance().getOuter_trade_no()).length() > 32){
                showToast(context,"Outer_trade_no  is too long,Length mustn't be more than 32",6 );
            }
        }else{
            showToast(context,"Outer_trade_no  can't be null",6 );
            return;
        }

        if(!TextUtils.isEmpty(PaymentContext.getInstance().getCurrency())){
            if  ((PaymentContext.getInstance().getCurrency()).length() > 20){
                showToast(context,"currency  is too long,Length mustn't be more than 20",6 );
            }
        }else{
            showToast(context,"currency  can't be null",6 );
            return;
        }

        if(!TextUtils.isEmpty(PaymentContext.getInstance().getTelco())){
            if  ((PaymentContext.getInstance().getTelco()).length() > 32){
                showToast(context,"Telco  is too long,Length mustn't be more than 32",6 );
            }
        }else{
            //运营商可以为空
            //showToast(context,"Telco  can't be null",6 );
            //return;
        }

        if(!TextUtils.isEmpty(PaymentContext.getInstance().getChannel_type())){
            if  ((PaymentContext.getInstance().getChannel_type()).length() > 32){
                showToast(context,"Channel type  is too long,Length mustn't be more than 32",6 );
            }
        }else{
            showToast(context,"Channel type  can't be null",6 );
            return;
        }

        if(!TextUtils.isEmpty(PaymentContext.getInstance().getPrice())){
            if  ((PaymentContext.getInstance().getPrice()).length() > 32){
                showToast(context,"price  is too long,Length mustn't be more than 32",6 );
                return;
            }
        }

        if(!TextUtils.isEmpty(PaymentContext.getInstance().getPriceid())){
            if  ((PaymentContext.getInstance().getPriceid()).length() > 32){
                showToast(context,"price id  is too long,Length mustn't be more than 32",6 );
                return;
            }
        }

        /*if(!TextUtils.isEmpty(PaymentContext.getInstance().getPriceid())
                &&(!TextUtils.isEmpty(PaymentContext.getInstance().getPrice()))){
            showToast(context,"Both price id  and price are null!",6 );
            return;
        }*/


        //if(!TextUtils.isEmpty(PaymentContext.getInstance().getCurrency()){
            if("DPA".equalsIgnoreCase(PaymentContext.getInstance().getCurrency())){
                if(TextUtils.isEmpty(PaymentContext.getInstance().getPrimary_phone_number())){
                    showToast(context,"Sim card I  can't be null if DPA is chosen!",6 );
                    return;
                }
            }
        //}

                &&(!TextUtils.isEmpty(PaymentContext.getInstance().getPrice()))){
            showToast(context,"Both price id  and price are null!",6 );
            return;



        if(!TextUtils.isEmpty(PaymentContext.getInstance().getProduct_name())){
            if  ((PaymentContext.getInstance().getProduct_name()).length() > 32){
                showToast(context,"product name  is too long,Length mustn't be more than 32",6 );
            }
        }else{
            showToast(context,"product name  can't be null",6 );
            return;
        }

        if(!TextUtils.isEmpty(PaymentContext.getInstance().getBuyer_id())){
            if  ((PaymentContext.getInstance().getBuyer_id()).length() > 32){
                showToast(context,"Buyer_id  is too long,Length mustn't be more than 32",6 );
                return;
            }
        }



        if(!TextUtils.isEmpty(PaymentContext.getInstance().getBuyer_id_type())){
            if  ((PaymentContext.getInstance().getBuyer_id_type()).length() > 20){
                showToast(context,"Buyer_id_type  is too long,Length mustn't be more than 20",6 );
                return;
            }
        }

        if(!TextUtils.isEmpty(PaymentContext.getInstance().getPrimary_phone_number())){
            if  ((PaymentContext.getInstance().getPrimary_phone_number()).length() > 32){
                showToast(context,"primary phone number  is too long,Length mustn't be more than 32",6 );
                return;
            }
        }

        if(!TextUtils.isEmpty(PaymentContext.getInstance().getSecond_phone_number())){
            if  ((PaymentContext.getInstance().getSecond_phone_number()).length() > 32){
                showToast(context,"second phone number  is too long,Length mustn't be more than 32",6 );
                return;
            }
        }

        if(!TextUtils.isEmpty(PaymentContext.getInstance().getSerial())){
            if  ((PaymentContext.getInstance().getSerial()).length() > 50){
                showToast(context,"serial  is too long,Length mustn't be more than 50",6 );
                return;
            }
        }

        if(!TextUtils.isEmpty(PaymentContext.getInstance().getPin())){
            if  ((PaymentContext.getInstance().getPin()).length() > 50){
                showToast(context,"pin  is too long,Length mustn't be more than 50",6 );
            }
        }

        if(!TextUtils.isEmpty(PaymentContext.getInstance().getExtend())){
            if  ((PaymentContext.getInstance().getExtend()).length() > 200){
                showToast(context,"pin  is too long,Length mustn't be more than 200",6 );
                return;
            }
        }


        this.callbackHandler = callbackHandler;
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable("context", PaymentContext.getInstance());
        bundle.putBoolean("isFullScreen",isFullScreen);
        bundle.putBoolean("isHorizonScreen",isHorizonScreen);
        intent.putExtras(bundle);
        intent.setClass(context,IpayLinksCashierActivity.class);

        /*this.callbackHandler = callbackHandler;
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable("context", PaymentContext.getInstance());
        bundle.putBoolean("isFullScreen",isFullScreen);
        intent.putExtras(bundle);
        intent.setClass(context,CashierActivity.class);
*/

        //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);




        context.startActivity(intent);

    }





}
