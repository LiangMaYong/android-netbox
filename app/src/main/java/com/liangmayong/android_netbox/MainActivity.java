package com.liangmayong.android_netbox;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.liangmayong.netbox.Netbox;
import com.liangmayong.netbox.interfaces.OnNetboxListener;
import com.liangmayong.netbox.params.FileParam;
import com.liangmayong.netbox.response.Response;
import com.liangmayong.netbox.throwables.NetboxError;

public class MainActivity extends AppCompatActivity {


    private TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        _initView();
        Netbox.server(DemoServer.class).interfaceServer(this, DemoInterface.class).uploadFile(new FileParam("a.jpg", "/storage/emulated/0/android_base/photo_take/temp_B8C37E33DEFDE51CF91E1E03E51657DA"), new OnNetboxListener() {
            @Override
            public void onResponseHistory(Response response) {
                Log.e("TAG-onResponseHistory", response.getBody());
            }

            @Override
            public void onResponse(Response response) {
                Log.e("TAG", response.getBody());
                text.setText(response.getBody());
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
