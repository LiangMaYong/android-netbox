package com.liangmayong.android_netbox;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.liangmayong.netbox.Netbox;
import com.liangmayong.netbox.interfaces.Method;
import com.liangmayong.netbox.interfaces.OnNetboxListener;
import com.liangmayong.netbox.response.Response;
import com.liangmayong.netbox.throwables.NetboxError;

public class MainActivity extends AppCompatActivity {


    private TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        _initView();
        Netbox.server(DemoServer.class).path("https://www.baidu.com").method(Method.POST).exec(this, new OnNetboxListener() {
            @Override
            public void onResponse(Response response) {
                Toast.makeText(getApplicationContext(), response.getBody(), Toast.LENGTH_SHORT).show();
                text.setText(response.getBody());
            }

            @Override
            public void onFailure(NetboxError error) {

            }

            @Override
            public void onProgress(int progress) {

            }
        });
    }

    private void _initView() {
        text = (TextView) findViewById(R.id.text);
    }
}
