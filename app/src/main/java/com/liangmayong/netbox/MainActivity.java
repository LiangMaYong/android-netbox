package com.liangmayong.netbox;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.liangmayong.netbox.interfaces.OnNetBoxListener;
import com.liangmayong.netbox.response.Response;
import com.liangmayong.netbox.throwables.NetBoxError;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NetBox.getActionInstance(NetBoxAction.class).path("url").param("username","sss").exec(this, new OnNetBoxListener<Response>() {
            @Override
            public void onResponse(Response response) {

            }

            @Override
            public void onFailure(NetBoxError error) {
                error.printStackTrace();
            }
        });
    }
}
