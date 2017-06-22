package com.ipaylinks.mobile.ipaylinkssdk.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.github.lzyzsd.jsbridge.DefaultHandler;
import com.google.gson.Gson;
import com.ipaylinks.mobile.ipaylinkssdk.R;
import com.ipaylinks.mobile.ipaylinkssdk.common.MyWebViewClient;
import com.ipaylinks.mobile.ipaylinkssdk.context.BaseContext;
import com.ipaylinks.mobile.ipaylinkssdk.context.PaymentContext;

import android.webkit.WebView;
import android.webkit.WebViewClient;
//import  android.util.log;



import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;


/**
 *
 */
public class CashierActivity extends BaseActivity {
    private BridgeWebView webView;
    private String htmlContent;
    //private Handler handler = new Handler();
    //private BaseContext baseContext;


    private PaymentContext paymentContext;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("bb", "onCreate: 0");
        setContentView(R.layout.activity_cashier, FLAG_TITLE_LINEARLAYOUT);
        //setContentView(R.layout.activity_cashier);
        //addWithdrawCardContext = (AddWithdrawCardContext) this.getIntent().getExtras()
        //        .getSerializable("context");

        Log.e("bb", "onCreate: ");



        this.getTitlebarView().setTitle("支付");

        Log.e("bb", "onCreate: 1");

        getTitlebarView().initRight("关闭", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backOnClick();
            }
        });
        getTitlebarView().setLeftVisible(false);
        //baseContext = (BaseContext) getIntent().getExtras().getSerializable("context");
        webView = (BridgeWebView) findViewById(R.id.webView);

        //htmlContent = getIntent().getStringExtra("content");
        initWebView();
        //webView.loadData("http://www.baidu.com", "text/html", "UTF-8");
        //webView.loadData("http://baidu.com", "text/html", "UTF-8");

        IpayLinksAdroidJS ipayLinkJS = new IpayLinksAdroidJS();

        webView.addJavascriptInterface(ipayLinkJS, "Android");

        webView.addJavascriptInterface(ipayLinkJS, "Android");

        //webView.passPayArg(PaymentContext.getInstance());

        webView.loadUrl("http://192.168.3.131/test8.html");

        //webView.loadUrl("http://192.168.1.6/test8.html");

        //webView.loadUrl("javascript:myFunction()");
        /*webView.setDefaultHandler(new DefaultHandler());

        webView.setWebChromeClient(new WebChromeClient());*/

        webView.setWebViewClient( new MyWebViewClient(webView));


        /*webView.getSettings().setJavaScriptEnabled(true);

        webView.getSettings().setDomStorageEnabled(true);*/


        Gson gson = new Gson();
        String paymentarg = gson.toJson(PaymentContext.getInstance());


        //传递pay的参数
        webView.callHandler("iPayLinksPay", paymentarg, new CallBackFunction() {

            @Override
            public void onCallBack(String data) {

                showToast("===",6 );
            }
        });


        //webView.loadUrl("http://172.168.0.132:10086/SDK/shuping/index.html");
    }



    @Override
    public void onBackPressed() {
        backOnClick();
    }

    private void backOnClick() {
        //finishAll();
        webView.loadUrl("javascript:myFunction()");
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        webView.postDelayed(new Runnable() {
            @Override
            public void run() {
                webView.loadUrl("javascript:postForm()");
            }
        },500);
    }

    private void initWebView() {
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
            }
        });

        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if("http://www.baidu.com/".equals(url)){

                    //queryServiceResultForTen();
                    return true;
                }else
                    view.loadUrl(url);
                return super.shouldOverrideUrlLoading(view, url);
            }

        });

        webView.requestFocusFromTouch();//如果webView中需要用户手动输入用户名、密码或其他，则webview必须设置支持获取手势焦点。

        WebSettings webSettings = webView.getSettings();
        //打开页面时， 自适应屏幕
        webSettings.setUseWideViewPort(true);//设置此属性，可任意比例缩放
        webSettings.setLoadWithOverviewMode(true);

        //设置js可用
        webSettings.setJavaScriptEnabled(true);
        //页面支持缩放
        webSettings.setBuiltInZoomControls(true);
        webSettings.setSupportZoom(true);



    }

    private void queryServiceResultForTen() {
       // showProgress();
        //handler.post(queryRunnable);
    }

    /*private Runnable queryRunnable = new Runnable() {
        private int count;
        @Override
        public void run() {
            if(count++ < 10) {
                RequestParams requestParams = new RequestParams();
                requestParams.putData("service", "query_trade");
                requestParams.putData("token", baseContext.getToken());
                if(baseContext instanceof RechargeContext) {
                    requestParams.putData("trade_type", "DEPOSIT");
                    requestParams.putData("outer_trade_no", ((RechargeContext) baseContext).getOutTradeNumber());
                }else{
                    requestParams.putData("trade_type", "INSTANT");
                    requestParams.putData("outer_trade_no", ((PaymentContext) baseContext).getOrderNums());
                }
                HttpUtils.getInstance(VFWebViewActivity.this).excuteHttpRequest(HttpRequsetUri.getInstance().AcquirerDoUri, requestParams, new VFinResponseHandler() {
                    @Override
                    public void onSuccess(Object responseBean, String responseString) {
                        count = 11;
                        hideProgress();
                        showShortToast("交易成功");
                        Intent intent = new Intent();
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("context", baseContext);
                        intent.putExtras(bundle);
                        intent.setClass(VFWebViewActivity.this, TradeResultActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onError(String statusCode, String errorMsg) {
                        if(count == 10) {
                            hideProgress();
                            showShortToast(errorMsg);
                            finishAll();
                        }
                    }
                }, this);
                handler.postDelayed(this, 3000);
            }
        }
    };*/

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //handler.removeCallbacks(queryRunnable);
    }

    @Override
    protected void onPause(){
        super.onPause();

        webView.pauseTimers();
        if(isFinishing()){
            webView.loadUrl("about:blank");
            //setContentView(new FrameLayout(this));
        }
    }

    @Override
    protected void onResume(){
        super.onResume();
        webView.resumeTimers();
    }



   /* public void JavacallHtml2(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                webView.loadUrl("javascript: passPayArg('IT-homer blog')");
                Toast.makeText(JSAndroidActivity.this, "clickBtn2", Toast.LENGTH_SHORT).show();
            }
        });
    }*/
}

