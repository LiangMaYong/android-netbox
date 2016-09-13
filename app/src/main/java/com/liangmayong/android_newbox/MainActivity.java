package com.liangmayong.android_newbox;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.liangmayong.netbox.NetBox;
import com.liangmayong.netbox.defualts.OnDefualtNetBoxListener;
import com.liangmayong.netbox.response.Response;
import com.liangmayong.netbox.throwables.NetBoxError;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        NetBox.generateCommonHeaders().put("header1", "111111111111");
        NetBox.generateCommonHeaders().put("header2", "111111111111");
        NetBox.generateCommonHeaders().put("header3", "111111111111");
        NetBox.generateCommonHeaders().put("header4", "111111111111");
        NetBox.generateAction(Action.class).path("url").param("username", "sss").exec(this, new OnDefualtNetBoxListener<String>() {
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
            public void onFailure(NetBoxError error) {
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        NetBox.generateCommonHeaders().put("header5", "111111111111");
        NetBox.generateAction(Action.class).path("url").param("username", "sss").exec(this, new OnDefualtNetBoxListener<String>() {
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
            public void onFailure(NetBoxError error) {
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
