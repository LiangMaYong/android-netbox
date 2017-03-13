package com.liangmayong.android_netbox;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
        NetboxConfig.getDefaultInstance().putParam("event", "User");
        Map<String, String> pre = new HashMap<>();
        pre.put("name", "bane");
        pre.put("age", "1");
        Netbox.server(DemoServer.class).interfaceServer(this, DemoInterface.class).login("1", "2", pre, new OnNetboxListener() {

            @Override
            public void onResponseHistory(Response historyResponse) {
                super.onResponseHistory(historyResponse);
                Log.e("TAG", historyResponse + "");
                text.setText("historyResponse:" + historyResponse + "");
            }

            @Override
            public void onResponse(Response response) {
                text.setText(response + "");
            }

            @Override
            public void onFailure(NetboxError error) {

            }
        });
    }

    private void _initView() {
        text = (TextView) findViewById(R.id.text);
    }
}
