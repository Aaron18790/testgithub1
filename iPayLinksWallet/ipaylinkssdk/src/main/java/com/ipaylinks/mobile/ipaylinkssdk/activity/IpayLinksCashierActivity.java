package com.ipaylinks.mobile.ipaylinkssdk.activity;
import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

//import com.github.lzyzsd.jsbridge.Message;
import android.os.Message;
import com.google.gson.Gson;
import com.ipaylinks.mobile.ipaylinkssdk.IPayLinksPay;
import com.ipaylinks.mobile.ipaylinkssdk.R;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.github.lzyzsd.jsbridge.DefaultHandler;

import com.ipaylinks.mobile.ipaylinkssdk.R;
import com.ipaylinks.mobile.ipaylinkssdk.common.MyWebViewClient;
import com.ipaylinks.mobile.ipaylinkssdk.common.Utils;
import com.ipaylinks.mobile.ipaylinkssdk.context.PaymentContext;
import com.ipaylinks.mobile.ipaylinkssdk.model.IPayLinksSDKResultModel;
//import com.legendmohe.permissionutil.PermissionUtil;

import java.io.File;
import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

import static android.webkit.WebSettings.LOAD_NO_CACHE;



//public class IpayLinksCashierActivity extends AppCompatActivity {

public class IpayLinksCashierActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks{


    class SendToInfo {
        String sendto;
        String body;



    }

    BridgeWebView bridgeWebView;
    Button button;

    SendToInfo sms;

    private Activity context=this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //setTheme("Theme.Dialog");
        //setTheme(android.R.style.Theme_Dialog);

        //super.setTheme(android.R.style.Theme);
        //super.setTheme(R.style.Theme_TransparentWithTitleBar);

        //dialog();




        Intent intent =this.getIntent();

        Bundle bundle =intent.getExtras();
        Boolean isFullScreen = bundle.getBoolean("isFullScreen");
        Boolean isHorizonScreen = bundle.getBoolean("isHorizonScreen");
        //String serverurl = bundle.getString("serverurl");
        /*if (isFullScreen )
        {
            this.requestWindowFeature(Window.FEATURE_NO_TITLE);
            super.setTheme(android.R.style.Theme);
        }*/





        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ipay_links_cashier);

        getSupportActionBar().hide();
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);



        }

        /*DisplayMetrics dm = getResources().getDisplayMetrics();
        int screenWidth = dm.widthPixels;
        int screenHeight = dm.heightPixels;*/


        /*if (!isFullScreen ) {
            //this.setTheme(R.style.Theme.Dialog);

            WindowManager m = getWindowManager();

            //WindowManager wm = this.getWindowManager();

            Display d = m.getDefaultDisplay(); // 为获取屏幕宽、高
            android.view.WindowManager.LayoutParams p = getWindow().getAttributes();
            p.height = (int) (m.getDefaultDisplay().getHeight() * 0.3); // 高度设置为屏幕的0.3
            p.width = (int) (m.getDefaultDisplay().getWidth() * 0.7); // 宽度设置为屏幕的0.7
            getWindow().setAttributes(p);

        }*/



       //getSupportActionBar().setTitle("Cashier");

        //button = (Button) findViewById(R.id.button3);

        bridgeWebView = (BridgeWebView) findViewById(R.id.JsBridgeWebView);
        bridgeWebView.clearCache(true);

        if (!isFullScreen ) {
            DisplayMetrics dm = getResources().getDisplayMetrics();
            // width = dm.widthPixels;
            // height = dm.heightPixels;
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) bridgeWebView.getLayoutParams();
//获取当前控件的布局对象
            params.width = dm.widthPixels *2/ 3;//设置当前控件布局的高度
            params.height = dm.heightPixels *2/ 3;
            bridgeWebView.setLayoutParams(params);

            //this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        }


        bridgeWebView.setDefaultHandler(new DefaultHandler());

        bridgeWebView.setWebChromeClient(new WebChromeClient());

        bridgeWebView.setWebViewClient( new MyWebViewClient(bridgeWebView));


        bridgeWebView.getSettings().setJavaScriptEnabled(true);

        bridgeWebView.getSettings().setDomStorageEnabled(true);




        //bridgeWebView.loadUrl("http://172.168.0.150/SDK/shuping/index.html");



        //bridgeWebView.loadUrl("http://172.168.0.132:10086/SDK3/SDK/shuping/index.html");
        //bridgeWebView.loadUrl("http://172.168.0.132:10086/SDK/shuping/index.html");

        //bridgeWebView.loadUrl("http://172.168.0.150/test8.html");
        //bridgeWebView.loadUrl("http://192.168.1.8/test8.html");
        //bridgeWebView.loadUrl("http://172.168.0.150/test8.html");


        if (EasyPermissions.hasPermissions(context, Manifest.permission.READ_PHONE_STATE)) {
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



        String url =  PaymentContext.getInstance().getServer_url();
        if(isFullScreen){
        if(isHorizonScreen){
            url = url + "/hengping/index.html";
        }else{
            url = url + "/shuping/index.html" ;
        }
        }else{
            /*if(isHorizonScreen){
                url = url + "/tchengping/index.html";
            }else{
                url = url + "/tcshuping/index.html" ;
            }*/
            if(isHorizonScreen){
                url = url + "/hengping/index.html";
            }else{
                url = url + "/shuping/index.html" ;
            }

        }



        System.out.print("url is" + url);
        bridgeWebView.loadUrl(url);
        //bridgeWebView.loadUrl(serverurl + "/shuping/index.html");

        //bridgeWebView.loadUrl("http://106.14.190.53:8580/static-wap/shuping/index.html");


        bridgeWebView.getSettings().setCacheMode(LOAD_NO_CACHE);


        if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            PaymentContext.getInstance().setScreenOrientation("landscape");

        } else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            //Log.i("info", "portrait");
            PaymentContext.getInstance().setScreenOrientation("portrait");
        }

        Gson gson = new Gson();
        String paymentarg = gson.toJson(PaymentContext.getInstance());

        //传递pay的参数
        bridgeWebView.callHandler("iPayLinksPay", paymentarg, new CallBackFunction() {

            @Override
            public void onCallBack(String data) {

                showToast("==="  + data);
            }
        });

        //bridgeWebView.loadUrl("file:///android_asset/test.html");


        /**
         * js发送给按住消息   callbackhandler 是js调用的方法名    安卓\返回给js
         */
        bridgeWebView.registerHandler("callbackhandler", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                //显示接收的消息
                showToast(data);

                //IPayLinksSDKResultModel model  = new IPayLinksSDKResultModel();

                Gson gson = new Gson();
                IPayLinksSDKResultModel model = gson.fromJson(data,IPayLinksSDKResultModel.class);
                System.out.println("model result is " + model.getResultCode());
                showToast("model result is " + model.getResultCode());
                //if("userCancel".equalsIgnoreCase(data))
                /*if()
                {
                    showToast("close");
                }*/
                //返回给html的消息
                /* Message msg2 = new Message();
                //msg2.setData("success");

                Bundle bundle = new Bundle();
                bundle.putString("text1","大明的消息传递参数的例子！");  //往Bundle中存放数据
                bundle.putString("text2","Time：2011-09-05");  //往Bundle中put数据
                msg2.setData(bundle);
                */
                //IPayLinksSDKResultModel resultModel =  new IPayLinksSDKResultModel();
                //resultModel.setResultCode("Success");

                Message msg = IPayLinksPay.getInstance().getCallbackHandler().obtainMessage();
                //msg.what = callBackMessageId;
                msg.obj = model;

                IPayLinksPay.getInstance().getCallbackHandler().sendMessage(msg);

                //function.onCallBack( "返回给Toast的alert");

                context.finish();
            }
        });




        /*protected void dialog() {
　　  AlertDialog.Builder builder = new Builder(Main.this);
　　  builder.setMessage("确认退出吗？");
　　  builder.setTitle("提示");
　　  builder.setPositiveButton("确认", new OnClickListener() {
　　   @Override
　　   public void onClick(DialogInterface dialog, int which) {
　　    dialog.dismiss();
　　    Main.this.finish();
　　   }
　　  });
　　  builder.setNegativeButton("取消", new OnClickListener() {
　　   @Override
　　   public void onClick(DialogInterface dialog, int which) {
　　    dialog.dismiss();
　　   }
　　  });
　　  builder.create().show();
　　 }*/



        bridgeWebView.registerHandler("callSMS", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                //显示接收的消息



                Log.e("here0",data);
                showToast(data);

                Gson gson = new Gson();

                sms = gson.fromJson(data, SendToInfo.class);

                dialog1(function);



                /*IPayLinksSDKResultModel resultModel = new IPayLinksSDKResultModel();
                resultModel.setResultCode("Success");
                if (EasyPermissions.hasPermissions(getBaseContext(), Manifest.permission.SEND_SMS)) {
                    SmsManager smsManager = SmsManager.getDefault();
                    if (smsManager != null  &&  sms.sendto != null && sms.sendto.length() > 0) {
                            smsManager.sendTextMessage(sms.sendto, null, sms.body, null, null);
                            function.onCallBack("返回给Toast的alert");
                    }
                } else {
                    // Ask for one permission
                    requstPermissions();
                    //EasyPermissions.requestPermissions(this, "读取设备信息方便使用后续服务",
                     //       110, Manifest.permission.SEND_SMS);
                }*/

            }
        });
        bridgeWebView.registerHandler("showActionBar", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                //显示接收的消息

                // dialog1(function);
                Log.e("here0",data);
                getSupportActionBar().show();


            }
        });

        bridgeWebView.registerHandler("hidActionBar", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                //显示接收的消息

                // dialog1(function);
                Log.e("here0",data);
                getSupportActionBar().hide();


            }
        });




        bridgeWebView.registerHandler("getPayArg", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                //显示接收的消息

                Log.e("getPayArg",data);
                showToast(data);

                Gson gson = new Gson();
                String paymentarg = gson.toJson(PaymentContext.getInstance());

                function.onCallBack( paymentarg);


            }
        });



        /*button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                bridgeWebView.callHandler("iPayLinksPay", "调用js的方法", new CallBackFunction() {

                    @Override
                    public void onCallBack(String data) {

                        showToast("==="  + data);
                    }
                });
            }
        });*/

    }

    protected void dialog() {
  AlertDialog.Builder builder = new AlertDialog.Builder(this);
  builder.setMessage("确认发送短信吗？");
  builder.setTitle("提示");
  builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
    @Override
      public void onClick(DialogInterface dialog, int which) {
    dialog.dismiss();
    //Main.this.finish();
   }
  });
  builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
   @Override
            public void onClick(DialogInterface dialog, int which){
   dialog.dismiss();
   }
  });
  builder.create().show();
 };


    private void dialog1(final CallBackFunction function){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);  //先得到构造器
        builder.setTitle("hint"); //设置标题
        builder.setMessage("phone will send a message ,are you agree?"); //设置内容
        //builder.setIcon(R.mipmap.ic_launcher);//设置图标，图片id即可
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() { //设置确定按钮
            @Override
            public void onClick(DialogInterface dialog, int which) {

                IPayLinksSDKResultModel resultModel = new IPayLinksSDKResultModel();
                resultModel.setResultCode("Success");
                if (EasyPermissions.hasPermissions(getBaseContext(), Manifest.permission.SEND_SMS)) {
                    SmsManager smsManager = SmsManager.getDefault();
                    if (smsManager != null  &&  sms.sendto != null && sms.sendto.length() > 0) {
                        smsManager.sendTextMessage(sms.sendto, null, sms.body, null, null);
                        function.onCallBack("T");
                    }else
                    {
                        function.onCallBack("T");
                    }
                } else {

                    // Ask for one permission
                    requstPermissions();
                    function.onCallBack("T");
                    //EasyPermissions.requestPermissions(this, "读取设备信息方便使用后续服务",
                    //       110, Manifest.permission.SEND_SMS);
                }

                dialog.dismiss(); //关闭dialog
                //Toast.makeText(this, "确认" + which, Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() { //设置取消按钮
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Toast.makeText(IpayLinksCashierActivity.this, "You don't send message,can't pay" , Toast.LENGTH_SHORT).show();
                finish();
            }
        });


        //参数都设置完成了，创建并显示出来
        builder.create().show();
    };




    void  requstPermissions()
    {
        EasyPermissions.requestPermissions(this, "读取设备信息方便使用后续服务",
                110, Manifest.permission.SEND_SMS);
    }

    public void showToast (String msg){
        //Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        /*String change01 = data.getStringExtra("change01");
        String change02 = data.getStringExtra("change02");
        // 根据上面发送过去的请求吗来区别
        switch (requestCode) {
            case 0:
                mText01.setText(change01);
                break;
            case 2:
                mText02.setText(change02);
                break;
            default:
                break;
        }*/
        Log.e("yzy","ok");
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        Log.e("ok","not sure");
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }


    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        // 请求权限
       /* if (PermissionUtil.checkInitPermission(this)) {
            jumpMainActivityDeLay(2000);
        }*/
       Log.e("ok","success");
        SmsManager smsManager = SmsManager.getDefault();
        if(sms!=null) {
            if(!TextUtils.isEmpty(sms.sendto)) {
                if (smsManager != null && sms.sendto != null && sms.sendto.length() > 0) {
                    smsManager.sendTextMessage(sms.sendto, null, sms.body, null, null);

                }
            }
        }

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {

        Log.e("ok","failure");



      /* if (requestCode == PermissionUtil.PERMISSION_REQUEST_CODE_INIT) {
            // (Optional) Check whether the user denied any permissions and checked "NEVER ASK AGAIN."
            // This will display a dialog directing them to enable the permission in app settings.
            if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
                PermissionUtil.showPermissionDetail(this, "应用必须权限", true);*/
//                new AppSettingsDialog.Builder(this, "应用必须权限")
//                        .setTitle("权限设置")
//                        .setPositiveButton(getString(R.string.setting))
//                        .setNegativeButton(getString(R.string.cancel), null /* click listener */)
//                        .setRequestCode(PermissionUtil.PERMISSION_REQUEST_CODE_INIT)
//                        .build()
//                        .show();
    /*        } else {
                PermissionUtil.checkInitPermission(this);
            }
        }*/
    }


    /*@Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(android.R.id.home == item.getItemId()){
            //Toast.makeText(this, "aa", Toast.LENGTH_SHORT).show();
            this.finish();
            return true;

        }
        else if  ( R.id.action_compose == item.getItemId()){
            this.finish();
            //Toast.makeText(this, "bb", Toast.LENGTH_SHORT).show();
            return true;

        }else{
            this.finish();
            //Toast.makeText(this, "cc", Toast.LENGTH_SHORT).show();
            return true;
        }

        /*    else if()

        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish(); // back button
                return true;
           case R.id.action_compose
            default:
                Toast.makeText(this, "Compose", Toast.LENGTH_SHORT).show();
                return true;
        }*/
        //return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.ipaymenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onDestroy() {
        Log.e("cashier","destroy");
        // TODO Auto-generated method stub
        if(bridgeWebView!=null){

            bridgeWebView = (BridgeWebView) findViewById(R.id.JsBridgeWebView);
            RelativeLayout a = (RelativeLayout)findViewById(R.id.WebViewRelativeLayout);

            a.removeView(bridgeWebView);
            bridgeWebView.removeAllViews();

            bridgeWebView.destroy();
        }
        super.onDestroy();
    }

    /**
     * 清除WebView缓存
     */
    /*public void clearWebViewCache(){

        //清理Webview缓存数据库
        try {
            deleteDatabase("webview.db");
            deleteDatabase("webviewCache.db");
        } catch (Exception e) {
            e.printStackTrace();
        }

        //WebView 缓存文件
        File appCacheDir = new File(getFilesDir().getAbsolutePath()+APP_CACAHE_DIRNAME);
        Log.e(TAG, "appCacheDir path="+appCacheDir.getAbsolutePath());

        File webviewCacheDir = new File(getCacheDir().getAbsolutePath()+"/webviewCache");
        Log.e(TAG, "webviewCacheDir path="+webviewCacheDir.getAbsolutePath());

        //删除webview 缓存目录
        if(webviewCacheDir.exists()){
            deleteFile(webviewCacheDir);
        }
        //删除webview 缓存 缓存目录
        if(appCacheDir.exists()){
            deleteFile(appCacheDir);
        }
    }*/



}
