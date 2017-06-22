package com.ipaylinks.mobile.ipaylinkssdk.activity;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

/**
 * Created by happy on 2017/5/29.
 */

public class IpayLinksAdroidJS {
    //用于通知第3方的通知
    private Handler callbackHandler = null;

    //构造函数
    /*public IpayLinksAdroidJS(Handler callbackHandler) {
        callbackHandler = callbackHandler;
    }*/

    public IpayLinksAdroidJS() {
        Log.e("testcallback","here");
        callbackHandler = new Handler();
    }


    //发送短信
    @JavascriptInterface
    public void callSMS(final String telephone, final String msg) {
    }

    //回调
    @JavascriptInterface
    /*public void callbackHandler(final String msg) {
        //callbackHandler.obtainMessage(msg).sendToTarget();
        Log.e("testcallback","yzy");
        Message callbackmsg = new Message();
        callbackmsg.obj = msg;
        callbackHandler.sendMessage(callbackmsg);
    }*/
    /*public void callbackHandler() {
        //callbackHandler.obtainMessage(msg).sendToTarget();
        Log.e("testcallback","yzy");
        Message callbackmsg = new Message();
        callbackmsg.obj = "here";
        callbackHandler.sendMessage(callbackmsg);
    }*/

    public void callbackHandler(final String msg) {
        //callbackHandler.obtainMessage(msg).sendToTarget();
        Log.e("testcallback","yzy");
        Message callbackmsg = new Message();
        callbackmsg.obj = msg;
        callbackHandler.sendMessage(callbackmsg);
    }






}
