package com.liangmayong.android_netbox;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.liangmayong.netbox.Netbox;
import com.liangmayong.netbox.callbacks.OnNetboxCallback;
import com.liangmayong.netbox.interfaces.Method;
import com.liangmayong.netbox.throwables.NetboxError;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Netbox.server(DemoService.class).path().method(Method.POST).exec(new OnNetboxCallback<DemoBean>() {
            @Override
            public void handleResponseSuccess(DemoBean data) {

            }

            @Override
            public void handleResponseError(String code, String message) {

            }

            @Override
            public void onFailure(NetboxError error) {

            }
        });
    }
}
