package com.liangmayong.android_netbox;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.liangmayong.netbox.Netbox;
import com.liangmayong.netbox.NetboxConfig;
import com.liangmayong.netbox.callbacks.OnNetboxListener;
import com.liangmayong.netbox.response.Response;
import com.liangmayong.netbox.throwables.NetboxError;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {


    private TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        _initView();
        Netbox.getCommonConfig().putParam("Default_key", "Default_value");
        Map<String, String> profiles = new HashMap<>();
        profiles.put("name", "Ni hao");
        profiles.put("age", "20");
        Netbox.server(DemoServer.class).interfaceServer(this, DemoInterface.class).login("username", "password", profiles, new OnNetboxListener() {

            @Override
            public void onResponseHistory(Response historyResponse) {
                super.onResponseHistory(historyResponse);
                text.setText("historyResponse:" + historyResponse + "");
            }

            @Override
            public void onResponse(Response response) {
                text.setText(response + "");
            }

            @Override
            public void onFailure(NetboxError error) {
                text.setText(error.getErrorType() + "");
            }
        });
    }

    private void _initView() {
        text = (TextView) findViewById(R.id.text);
    }
}
