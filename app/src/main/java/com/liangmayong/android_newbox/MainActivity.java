package com.liangmayong.android_newbox;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.liangmayong.netbox.Netbox;
import com.liangmayong.netbox.NetboxConfig;
import com.liangmayong.netbox.defualts.OnDefualtNetboxListener;
import com.liangmayong.netbox.response.Response;
import com.liangmayong.netbox.throwables.NetboxError;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        NetboxConfig.getInstance(Action.class).putHeader("header1", "111111111111").putHeader("header2", "111111111111").putHeader("header3", "111111111111").putHeader("header4", "111111111111");
        Netbox.generateAction(Action.class).path("url").param("username", "sss").exec(this, new OnDefualtNetboxListener<String>() {
            @Override
            public void handleResponseSuccess(String data) {
                Toast.makeText(MainActivity.this, data, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void handleResponseError(String code, String message) {
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Response response) {
                super.onResponse(response);
                Toast.makeText(MainActivity.this, response.getBody(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(NetboxError error) {
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        NetboxConfig.getInstance(Action.class).putHeader("header5","2")
                .putHeader("header6","11");
        Netbox.generateAction(Action.class).path("url").param("username", "sss").exec(this, new OnDefualtNetboxListener<String>() {
            @Override
            public void handleResponseSuccess(String data) {
                Toast.makeText(MainActivity.this, data, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void handleResponseError(String code, String message) {
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Response response) {
                super.onResponse(response);
                Toast.makeText(MainActivity.this, response.getBody(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(NetboxError error) {
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
