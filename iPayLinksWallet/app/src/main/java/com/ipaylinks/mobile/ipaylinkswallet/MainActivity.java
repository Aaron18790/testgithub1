package com.ipaylinks.mobile.ipaylinkswallet;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ipaylinks.mobile.ipaylinkssdk.IPayLinksPay;
import com.ipaylinks.mobile.ipaylinkssdk.activity.*;
import com.ipaylinks.mobile.ipaylinkssdk.context.PaymentContext;
import com.ipaylinks.mobile.ipaylinkssdk.context.UserPaymentContext;
import com.ipaylinks.mobile.ipaylinkssdk.model.IPayLinksSDKResultModel;
import com.ipaylinks.mobile.ipaylinkssdk.model.IpayLinksCallBackEnum;

import java.util.ArrayList;
import java.util.List;

import static com.ipaylinks.mobile.ipaylinkswallet.RSAKey.charset;
import static com.ipaylinks.mobile.ipaylinkswallet.RSAKey.privateKey;





public class MainActivity extends AppCompatActivity {

    private int paycallbackMessageID = 1;

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        setContentView(R.layout.activity_main_eng);



        long mill = System.currentTimeMillis();
        EditText edit_outer_trade_no = (EditText)findViewById(R.id.edit_outer_trade_no);
        edit_outer_trade_no.setText(String.valueOf(mill));

  /*try{
      RSAKey.testSign();
  }catch (Exception e){

  }*/

        //getTheme().getResources().get

       // final int themeResId = getThemeResId();


        /*Resources.Theme a = getTheme();
        Log.e("theme",a.toString());
        setTheme(a);*/

        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/

        if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Log.i("info", "landscape");
        } else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            Log.i("info", "portrait");
        }



        Button button02 = (Button)findViewById(R.id.button_buy);

        //绑定事件源和监听器对象
        button02.setOnClickListener(new MyButtonListener());

        Spinner spinner_currency_view = (Spinner)findViewById(R.id.spinner_currency);



        spinner_currency_view.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent,
                                               View view, int position, long id) {
                        //showToast("Spinner1: position="   position   " id="   id);
                        //获取选择的位置的字符串，
                        String currency  =  (String)parent.getAdapter().getItem(position);
                        Spinner       spinner_telco = (Spinner) findViewById(R.id.spinner_telco);
                        //List<String> thetelco = new ArrayList<String>();
                        String[] mItems = new String[]{};




                        if("THB".equalsIgnoreCase(currency)) {
                             mItems = getResources().getStringArray(R.array.THB);
                            //thetelco = mItems;

                           // thetelco.add("AA");
                           // thetelco.add("BB");
                        }else if("VND".equalsIgnoreCase(currency)) {
                             mItems = getResources().getStringArray(R.array.VND);

                        }
                        else if("IDR".equalsIgnoreCase(currency)) {
                            mItems = getResources().getStringArray(R.array.IDR);

                        }
                        else if("MYR".equalsIgnoreCase(currency)) {
                            mItems = getResources().getStringArray(R.array.MYR);

                        }
                        else if("SGD".equalsIgnoreCase(currency)) {
                            mItems = getResources().getStringArray(R.array.SGD);

                        }
                        else if("MMK".equalsIgnoreCase(currency)) {
                            mItems = getResources().getStringArray(R.array.MMK);

                        }
                        else if("PHP".equalsIgnoreCase(currency)) {
                            mItems = getResources().getStringArray(R.array.PHP);

                        }



                            ArrayAdapter aspnCountries = new ArrayAdapter<String>(parent.getContext(),android.R.layout.simple_spinner_item, mItems);

                            aspnCountries.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinner_telco.setAdapter(aspnCountries);

                        }






                        /*if(position == 0)
                        {


                            if((String) spinner_currency.getSelectedItem()

                        }*/



                    public void onNothingSelected(AdapterView<?> parent) {
                        //showToast("Spinner1: unselected");
                        /*if(position == 0)
                        {

                        }*/
                    }
                });



        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                IPayLinksPay.getInstance().init(view.getContext(),"en","ek1","appid1","google");

                Handler callbackHandler = new Handler(){
                    public void handleMessage(Message msg) {
                        //if (msg.what == billcallbackMessageID) {
                        //    handleBillCallback((VFSDKResultModel) msg.obj);
                        //}
                        Log.e("mainactivity","call back to the game now");
                    }

                };

                PaymentContext.getInstance().setBuyer_id("yzy");

                UserPaymentContext payattr = new UserPaymentContext();

                payattr.setCurrency("THB");
                //payattr.setTelco();
                payattr.setPrimary_phone_number("+6613600000000");
                payattr.setSecond_phone_number("+6013700000008");

                payattr.setBuyer_id("135780021");
                payattr.setOuter_trade_no("15821189098");
                payattr.setPrice("1000");
                payattr.setPriceId("2");
                payattr.setProduct_name("牛奶");

                payattr.setExtend("牛奶很贵的好不好");

                payattr.setFullScreen (false);




                IPayLinksPay.getInstance().SDKPlusPay(view.getContext(),payattr,callbackHandler);


            }
        });


        // Example of a call to a native method
        TextView tv = (TextView) findViewById(R.id.sample_text);
        tv.setText(stringFromJNI());*/
    }
    class signData{
        private String  out_trade_no;

        private String  product_name;;

        public String getOut_trade_no() {
            return out_trade_no;
        }

        public void setOut_trade_no(String out_trade_no) {
            this.out_trade_no = out_trade_no;
        }

        public String getProduct_name() {
            return product_name;
        }

        public void setProduct_name(String product_name) {
            this.product_name = product_name;
        }
    }


    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            Log.e("mainactivity","call back to the game now");
            if (msg.what == paycallbackMessageID) {
                handlePayCallback((IPayLinksSDKResultModel) msg.obj);
            } else{

            }
        }

        ;
    };

    private void handlePayCallback(IPayLinksSDKResultModel resultModel) {
        if(resultModel.getResultCode().equalsIgnoreCase(IpayLinksCallBackEnum.OK.getCode()) == true) {
            Toast.makeText(this, resultModel.getMessage(), Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(MainActivity.this, resultModel.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    class MyButtonListener implements View.OnClickListener
    {

        public void onClick(View v)
        {

            UserPaymentContext payattr = new UserPaymentContext();

            EditText edit_currency = (EditText)findViewById(R.id.edit_currency);
            if  (TextUtils.isEmpty(edit_currency.getText())){
                Spinner spinner_currency = (Spinner)findViewById(R.id.spinner_currency);
                if(!"null".equalsIgnoreCase((String) spinner_currency.getSelectedItem())) {

                    payattr.setCurrency((String) spinner_currency.getSelectedItem());
                }

            }
            else{
                payattr.setCurrency(edit_currency.getText().toString());
            }

            EditText edit_telco = (EditText)findViewById(R.id.edit_telco);
            if  (TextUtils.isEmpty(edit_telco.getText())){
                Spinner spinner_telco = (Spinner)findViewById(R.id.spinner_telco);
                if("null".equalsIgnoreCase((String)spinner_telco.getSelectedItem())){

                }else {

                    payattr.setTelco((String) spinner_telco.getSelectedItem());
                }

            }
            else{
                payattr.setTelco(edit_telco.getText().toString());
            }

            EditText outer_trade_no = (EditText)findViewById(R.id.edit_outer_trade_no);
            if  (!TextUtils.isEmpty(outer_trade_no.getText())){
                payattr.setTransactionId(outer_trade_no.getText().toString());
                System.out.print("******"+payattr.getTransactionId());
            }




            RadioGroup isFullScreenGroup =(RadioGroup)findViewById(R.id.radioGroup_isFullScreen);
            RadioButton isFullScreenButton = (RadioButton)findViewById(isFullScreenGroup.getCheckedRadioButtonId());
            if("Yes".equalsIgnoreCase((String)isFullScreenButton.getText())){
                payattr.setFullScreen (true);
            }else{
                payattr.setFullScreen (false);
            }

            RadioGroup isHorizonScreenGroup =(RadioGroup)findViewById(R.id.radioGroup_isHorizon);
            RadioButton isHorizonScreenButton = (RadioButton)findViewById(isHorizonScreenGroup.getCheckedRadioButtonId());
            if("Yes".equalsIgnoreCase((String)isHorizonScreenButton.getText())){
                payattr.setHorizonScreen (true);
            }else{
                payattr.setHorizonScreen (false);
            }


            RadioGroup isHorizonGroup =(RadioGroup)findViewById(R.id.radioGroup_isHorizon);
            RadioButton isHorizonButton = (RadioButton)findViewById(isHorizonGroup.getCheckedRadioButtonId());
            if("Yes".equalsIgnoreCase((String)isHorizonButton.getText())){
               // payattr.setFullScreen (true);
            }else{
              //  payattr.setFullScreen (false);
            }


            EditText edit_price = (EditText)findViewById(R.id.edit_price);
            if  (!TextUtils.isEmpty(edit_price.getText())){
                payattr.setPrice(edit_price.getText().toString());
            }

            EditText edit_priceid = (EditText)findViewById(R.id.edit_priceid);
            if  (!TextUtils.isEmpty(edit_priceid.getText())){
                payattr.setPriceid(edit_priceid.getText().toString());
            }

            EditText edit_productname = (EditText)findViewById(R.id.edit_product_name);
            if  (!TextUtils.isEmpty(edit_productname.getText())){
                payattr.setProduct_name(edit_productname.getText().toString());
            }

            EditText edit_buyid = (EditText)findViewById(R.id.edit_buyer_id);
            if  (!TextUtils.isEmpty(edit_buyid.getText())){
                payattr.setBuyer_id(edit_buyid.getText().toString());
            }

            EditText edit_buyid_type = (EditText)findViewById(R.id.edit_buyer_id_type);
            if  (!TextUtils.isEmpty(edit_buyid_type.getText())){
                payattr.setBuyer_id_type(edit_buyid_type.getText().toString());
            }

            EditText edit_primary_phone_number = (EditText)findViewById(R.id.edit_primary_phone_number);
            if  (!TextUtils.isEmpty(edit_primary_phone_number.getText())){
                payattr.setPrimary_phone_number(edit_primary_phone_number.getText().toString());
            }

            EditText edit_second_phone_number = (EditText)findViewById(R.id.edit_second_phone_number);
            if  (!TextUtils.isEmpty(edit_second_phone_number.getText())){
                payattr.setSecond_phone_number(edit_second_phone_number.getText().toString());
            }

            EditText edit_extend = (EditText)findViewById(R.id.edit_extend);
            if  (!TextUtils.isEmpty(edit_extend.getText())){
                payattr.setExtend(edit_extend.getText().toString());
            }

            String serverurl = "http://172.168.2.149:8080/SDK/";
            EditText edit_server_url = (EditText)findViewById(R.id.edit_server_url);
            if  (!TextUtils.isEmpty(edit_server_url.getText())){
                serverurl = edit_server_url.getText().toString();
            }






          /*  Handler callbackHandler = new Handler(){
                public void handleMessage(Message msg) {
                    //if (msg.what == billcallbackMessageID) {
                    //    handleBillCallback((VFSDKResultModel) msg.obj);
                    //}
                    Log.e("mainactivity","call back to the game now");
                }

            };*/







            //payattr.setCurrency("MYR");
            //payattr.setTelco();
          /*  payattr.setPrimary_phone_number("+6613600000000");
            payattr.setSecond_phone_number("+6013700000008");

            payattr.setBuyer_id("135780021");

            //long mill = System.currentTimeMillis();

            //payattr.setOuter_trade_no(String.valueOf(mill));
            //payattr.setOuter_trade_no("3436394");
            //payattr.setPrice("1000");
            payattr.setPriceid("10000");
            payattr.setProduct_name("牛奶");

            payattr.setExtend("牛奶很贵的好不好");

            payattr.setFullScreen (true);
            payattr.setSign("signmock");
            payattr.setSignType("RSA");

            System.err.println("私钥加密——公钥解密");
            //String source = "[{\"out_trade_no\":\"3436394\",\"subject\":\"牛奶\",\"total_amount\":\"0.01\",\"seller\":\"15721476821\",\"seller_type\":\"MOBILE\",\"price\":\"0.01\",\"quantity\":1}]";
            signData signdata = new signData();
            signdata.setOut_trade_no(payattr.getOuter_trade_no());
            signdata.setProduct_name(payattr.getProduct_name());

            String source = new Gson().toJson(signdata);
            */


            //source = "[{\"outer_trade_no\":\"3436394\"}]";
            //source = "outer_trade_no=3436394";
            //source = "outer_trade_no=" + String.valueOf(mill);
            String source = "outer_trade_no=" + payattr.getTransactionId();
            privateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAO/6rPCvyCC+IMalLzTy3cVBz/+wamCFNiq9qKEilEBDTttP7Rd/GAS51lsfCrsISbg5td/w25+wulDfuMbjjlW9Afh0p7Jscmbo1skqIOIUPYfVQEL687B0EmJufMlljfu52b2efVAyWZF9QBG1vx/AJz1EVyfskMaYVqPiTesZAgMBAAECgYEAtVnkk0bjoArOTg/KquLWQRlJDFrPKP3CP25wHsU4749t6kJuU5FSH1Ao81d0Dn9m5neGQCOOdRFi23cV9gdFKYMhwPE6+nTAloxI3vb8K9NNMe0zcFksva9c9bUaMGH2p40szMoOpO6TrSHO9Hx4GJ6UfsUUqkFFlN76XprwE+ECQQD9rXwfbr9GKh9QMNvnwo9xxyVl4kI88iq0X6G4qVXo1Tv6/DBDJNkX1mbXKFYL5NOW1waZzR+Z/XcKWAmUT8J9AkEA8i0WT/ieNsF3IuFvrIYG4WUadbUqObcYP4Y7Vt836zggRbu0qvYiqAv92Leruaq3ZN1khxp6gZKl/OJHXc5xzQJACqr1AU1i9cxnrLOhS8m+xoYdaH9vUajNavBqmJ1mY3g0IYXhcbFm/72gbYPgundQ/pLkUCt0HMGv89tn67i+8QJBALV6UgkVnsIbkkKCOyRGv2syT3S7kOv1J+eamGcOGSJcSdrXwZiHoArcCZrYcIhOxOWB/m47ymfE1Dw/+QjzxlUCQCmnGFUO9zN862mKYjEkjDN65n1IUB9Fmc1msHkIZAQaQknmxmCIOHC75u4W0PGRyVzq8KkxpNBq62ICl7xmsPM=";
            System.out.println("原文字：\r\n" + source);
            byte[] data = source.getBytes();
            try {
                String sign = RSA.sign(source , privateKey, charset);
                System.out.println("签名：\r\n" + sign);
                payattr.setSign(sign);
                System.err.println("签名:\r" + sign);
            } catch(Exception e)
                {
                    System.err.println("签名错误:\r" );
                }
            Spinner spinner_interface = (Spinner)findViewById(R.id.spinner_interface);

            String walletInteraface =  (String)spinner_interface.getSelectedItem();

            //String partner_id = "188888888888";
            String partner_id = "200006743854";

            EditText edit_partnerid = (EditText)findViewById(R.id.edit_partner_id);
            if  (!TextUtils.isEmpty(edit_partnerid.getText())){
                partner_id = edit_partnerid.getText().toString();
            }else{

            }

            String appid = "106";

            EditText edit_app_id = (EditText)findViewById(R.id.edit_app_id);
            if  (!TextUtils.isEmpty(edit_app_id.getText())){
                appid  = edit_app_id.getText().toString();
            }else{


            }


            IPayLinksPay.getInstance().init(v.getContext(),serverurl,"en",partner_id,appid,"google");

            if ("BackendPsmsPay".equalsIgnoreCase(walletInteraface)){
                IPayLinksPay.getInstance().BackendPsmsPay(v.getContext(),payattr,mHandler);

            }else if ("BackendCashcardPay".equalsIgnoreCase(walletInteraface)){
                IPayLinksPay.getInstance().BackendCashcardPay(v.getContext(),payattr,mHandler);

            }else if ("WebPagePay".equalsIgnoreCase(walletInteraface)){
                IPayLinksPay.getInstance().WebPagePay(v.getContext(),payattr,mHandler);

            }else{

                IPayLinksPay.getInstance().SDKPlusPay(v.getContext(),payattr,mHandler);
            }




            //IPayLinksPay.getInstance().WebPagePay(v.getContext(),payattr,callbackHandler);


            //IPayLinksPay.getInstance().SDKPlusPay(v.getContext(),payattr,callbackHandler);


        }


    }





//}

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



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
}
