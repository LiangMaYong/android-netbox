package com.liangmayong.android_netbox;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.liangmayong.netbox.Netbox;
import com.liangmayong.netbox.callbacks.NetboxCallback;
import com.liangmayong.netbox.interfaces.OnNetboxListener;
import com.liangmayong.netbox.response.Response;
import com.liangmayong.netbox.throwables.NetboxError;

import java.util.List;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Netbox.server(ExServer.class).path("list").param("key", "param").exec(this, new NetboxCallback<List<UserBean>>() {
            @Override
            public void handleResponseSuccess(List<UserBean> data) {
                Toast.makeText(MainActivity.this, data + "", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void handleResponseError(String code, String message) {
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(NetboxError error) {
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        Netbox.server(ExServer.class).path("list").param("key", "param").exec(this, new OnNetboxListener() {
            @Override
            public void onResponse(Response response) {
                Toast.makeText(MainActivity.this, response.getData("id", String.class) + "", Toast.LENGTH_SHORT).show();

                Toast.makeText(MainActivity.this, response.getUrl(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(NetboxError error) {

            }
        });
    }
}
