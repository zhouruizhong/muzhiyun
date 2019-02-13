package com.muzhiyun.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;
import com.muzhiyun.service.entity.Uuid;
import com.muzhiyun.service.presenter.InitPresenter;
import com.muzhiyun.service.view.UuidView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private ImageView qrCode;
    private InitPresenter initPresenter = new InitPresenter(this);
    private String uuid;
    private final String url = "";

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        qrCode = findViewById(R.id.qrCode);
        initPresenter.onCreate();
        initPresenter.attachView(mUuidView);

        //
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        // 获取uuid 加载登录二维码
        initPresenter.getUuid();
        // 轮询验证登录
        // initPresenter.checkLogin(uuid);

    }

    private UuidView mUuidView = new UuidView() {
        @Override
        public void onSuccess(Uuid mUuid) {
            uuid = mUuid.getUuid();
            System.out.println("-----------uuid :" + uuid + ":----------------");
            // 开启一个子线程，进行网络操作，等待有返回结果，使用handler通知UI
            new Thread(networkTask).start();

            //Intent intent = new Intent(MainActivity.this, IndexActivity.class);
            //startActivity(intent);
        }

        @Override
        public void onError(String result) {
            Toast.makeText(MainActivity.this, result, Toast.LENGTH_SHORT).show();
        }
    };

    /**
     * 加载图片
     *
     * @param url
     * @return
     */
    public Bitmap getURLImage(String url) {
        Bitmap bmp = null;
        try {
            URL myurl = new URL(url);
            // 获得连接
            HttpURLConnection conn = (HttpURLConnection) myurl.openConnection();
            //设置超时
            conn.setConnectTimeout(6000);
            conn.setDoInput(true);
            //不缓存
            conn.setUseCaches(false);
            conn.connect();
            //获得图片的数据流
            InputStream is = conn.getInputStream();
            bmp = BitmapFactory.decodeStream(is);
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bmp;
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            String uuid = data.getString("uuid");
            Log.i("mylog", "请求结果为-->" + uuid);
            // 加载二维码
            Bitmap bitmap = getHttpBitmap("https://repo.jianhuotech.com/loginQr/" + uuid);

            //从网上取图片
            qrCode.setImageBitmap(bitmap);
        }
    };

    /**
     * 网络操作相关的子线程
     */
    Runnable networkTask = new Runnable() {

        @Override
        public void run() {
            // TODO
            // 在这里进行 http request.网络请求相关操作
            Message msg = new Message();
            Bundle data = new Bundle();
            data.putString("uuid", uuid);
            msg.setData(data);
            handler.sendMessage(msg);
        }
    };


    /**
     * 从服务器取图片
     *
     * @param url
     * @return
     */
    public static Bitmap getHttpBitmap(String url) {
        URL myFileUrl = null;
        Bitmap bitmap = null;
        try {
            myFileUrl = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

}
