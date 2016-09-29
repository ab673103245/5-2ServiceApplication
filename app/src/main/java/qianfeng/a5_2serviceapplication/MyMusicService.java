package qianfeng.a5_2serviceapplication;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.File;
import java.io.IOException;

/**
 * Created by Administrator on 2016/9/21 0021.
 */
public class MyMusicService extends Service {

    private MediaPlayer mediaPlayer;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("google-my:", "onCreate: " + Thread.currentThread());
//        Notification notification = new Notification.Builder(this).setContentText("1213").setContentTitle("音乐").setSmallIcon(R.mipmap.ic_launcher).build();

//        //使Service处于前台，这样Service更不容易被销毁
//        startForeground(1,notification);
        initMediaPlayer();



    }

    private void initMediaPlayer() {
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(this, Uri.fromFile(new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "m1.mp3")));
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                // 发送一条广播，通知Activity应该弹一个警告框，因为Service的上下文不是Actiivty，而警告框只能在上下文为Activity里面弹出来
                // Fragment也可以弹警告框，因为Fragment可以用getActivity来获取宿主Activity的上下文，也是Activity
                // 而且这条广播只能是动态注册的，因为静态注册的广播的上下文同样不是Actvity，动态注册的广播的上下文就是Activity
                // 静态注册的广播的上下文是 Application,
                sendBroadcast(new Intent("dialog"));

            }
        });

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("google-my:", "onStartCommand: " + Thread.currentThread());

        switch (intent.getIntExtra("type", -1)) {
            case 0:
                if (mediaPlayer == null) {
                    initMediaPlayer();
                }
//                mediaPlayer.seekTo(mediaPlayer.getDuration() - 3000);
                mediaPlayer.start();
                break;

            case 1:
                if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                }
                break;

            case 2:
                if (mediaPlayer != null) {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    mediaPlayer = null;
                }
                break;
        }

        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("google-my:", "onDestroy: " + Thread.currentThread());
    }


}
