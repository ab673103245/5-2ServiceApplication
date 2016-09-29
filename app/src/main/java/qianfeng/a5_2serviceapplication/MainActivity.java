package qianfeng.a5_2serviceapplication;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private Intent service;
    private MyBroadcast myBroadcast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        service = new Intent(this,MyMusicService.class);

        myBroadcast = new MyBroadcast();
        IntentFilter filter = new IntentFilter();
        filter.addAction("dialog");
        registerReceiver(myBroadcast,filter);

    }


    public void startService(View view) { // 开启Service服务

        // 启动
        startService(service);
    }

    public void stopService(View view) { // 关闭Service服务

        stopService(service);

    }

    public void play(View view) {
        service.putExtra("type",0);
        startService(service);
    }

    public void pause(View view) {
        service.putExtra("type",1);
        startService(service);
    }


    public void stop(View view) {
        service.putExtra("type",2);
        startService(service);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(myBroadcast);
    }
}
