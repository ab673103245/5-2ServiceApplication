package qianfeng.a5_2serviceapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;

/**
 * Created by Administrator on 2016/9/21 0021.
 */
public class MyBroadcast extends BroadcastReceiver {
    @Override
    public void onReceive(final Context context, Intent intent) {

        // 进行警告框的显示
        new AlertDialog.Builder(context)
                .setTitle("是否重复播放该歌曲")
                .setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 调用startService（）来实际调用Service的startCommand方法(因为Service已经被onCreate()过了,还没有被onDestory(),所以调用startService()也是会一直执行startCommand())
                        Intent service = new Intent(context, MyMusicService.class);

                        service.putExtra("type",0);

                        context.startService(service); // 注意这行最关键的代码！利用context开启服务，如果Service已经被初始化过了，那就会只调用里面的StartCommand()方法
                        // 这个onReceive里面的context是最关键的！ 创建警告框(new AlertDialog.Builder(context))和调用startService()方法都在这行


                    }
                })
                .setNegativeButton("否",null)
                .create().show();
    }
}
