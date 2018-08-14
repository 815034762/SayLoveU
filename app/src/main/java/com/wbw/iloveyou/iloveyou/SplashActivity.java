package com.wbw.iloveyou.iloveyou;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.wbw.iloveyou.LoveApplication;
import com.wbw.iloveyou.R;
import com.wbw.iloveyou.bean.Info;
import com.wbw.iloveyou.util.NetworkUtils;
import com.wbw.iloveyou.util.SharedPreferencesXml;
import com.wbw.iloveyou.util.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

/**
 * 启动页面
 */
public class SplashActivity extends Activity {

    private EditText editText;
    private Button btnSubmit;
    private SharedPreferencesXml spxml;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Util.init().setContext(SplashActivity.this);
        spxml = SharedPreferencesXml.init();
        editText = findViewById(R.id.et_phone);
        btnSubmit = findViewById(R.id.btn_ok);
        editText.setText(spxml.getPhone());

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (NetworkUtils.isAvailable(SplashActivity.this)) {
                    String phone = editText.getText().toString();
                    if (!"".equals(phone)) {
                        getUserInfo(phone);
                    } else {
                        Toast.makeText(SplashActivity.this, "你没有填写号码呢，不给你看", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(SplashActivity.this, "当前网络不可用，先打开网络线吧", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void getUserInfo(final String phone) {

        btnSubmit.setClickable(false);
        btnSubmit.setText("正在请求网络...");
        BmobQuery query = new BmobQuery("Info");
        query.addWhereEqualTo("phone", phone);
        query.setLimit(1);
        query.order("createdAt");
        //v3.5.0版本提供`findObjectsByTable`方法查询自定义表名的数据
        query.findObjectsByTable(new QueryListener<JSONArray>() {
            @Override
            public void done(JSONArray ary, BmobException e) {

                btnSubmit.setClickable(true);
                btnSubmit.setText("我已填写好啦");
                if (e == null) {
                    try {
                        JSONObject jsonObject = ary.getJSONObject(0);
                        if (null != jsonObject) {

                            spxml.setPhone(phone);

                            LoveApplication.info = new Info();

                            LoveApplication.info.setUsername(jsonObject.optString("username"));
                            LoveApplication.info.setFourContent1(jsonObject.optString("fourContent1"));
                            LoveApplication.info.setFourContent2(jsonObject.optString("fourContent2"));
                            LoveApplication.info.setFourContent3(jsonObject.optString("fourContent3"));
                            LoveApplication.info.setSecondContent(jsonObject.optString("secondContent"));
                            LoveApplication.info.setThirdContent1(jsonObject.optString("thirdContent1"));
                            LoveApplication.info.setThirdContent2(jsonObject.optString("thirdContent2"));
                            LoveApplication.info.setThirdContent3(jsonObject.optString("thirdContent3"));
                            LoveApplication.info.setThirdContent4(jsonObject.optString("thirdContent4"));
                            LoveApplication.info.setThirdContent5(jsonObject.optString("thirdContent5"));
                            LoveApplication.info.setThirdContent6(jsonObject.optString("thirdContent6"));

                            LoveApplication.info.setFourImage1(jsonObject.optString("fourImage1"));
                            LoveApplication.info.setFourImage2(jsonObject.optString("fourImage2"));
                            LoveApplication.info.setFourImage3(jsonObject.optString("fourImage3"));
                            LoveApplication.info.setFourImage4(jsonObject.optString("fourImage4"));
                            LoveApplication.info.setFourImageCenter(jsonObject.optString("fourImageCenter"));
                        }
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }
                }
                Log.e("rag", "--------------showLoading---------------");
                Intent intent = new Intent();
                intent.setClass(SplashActivity.this, FirstActivity.class);
                startActivity(intent);
                SplashActivity.this.finish();
            }
        });

    }

}
