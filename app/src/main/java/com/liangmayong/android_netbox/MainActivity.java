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
        Netbox.server(DemoServer.class).interfaceServer(this, DemoInterface.class).list(new FileParam("a.jpg", "/storage/emulated/0/android_base/photo_take/temp_B8C37E33DEFDE51CF91E1E03E51657DA"), new OnNetboxListener() {
            @Override
            public void onResponse(Response response) {
                Log.e("TAG", response.getBody());
                text.setText(response.getBody());
            }

            @Override
            public void onFailure(NetboxError error) {

            }
        });
//        Map<String, VoFile> fileMap = new HashMap<String, VoFile>();
//        fileMap.put("data", new VoFile("img.jpg", result.getPath()));
//        HttpUtils.post("http://121.201.108.221/dpadmin/uploadFile.php", null, null, fileMap, new OnHttpListener() {
//            @Override
//            public void success(byte[] data, String encode, String cookie) {
//                try {
//                    Log.e("TAG", new String(data, encode));
//                    showToast(new String(data, encode));
//                } catch (UnsupportedEncodingException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void error(HttpError e) {
//                e.getException().printStackTrace();
//            }
//        });
    }

    private void _initView() {
        text = (TextView) findViewById(R.id.text);
    }
}
