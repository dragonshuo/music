package com.example.music;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.RemoteAction;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;

import androidx.core.app.NotificationCompat;

import java.io.File;
import java.io.IOException;

public class MyService extends Service {
    private  MediaPlayer mediaPlayer=new MediaPlayer();
    private  String music=null;
    private voiceBinder mBinder=new voiceBinder();
     class voiceBinder extends Binder//内部类,service 关闭后依然存在------------------------------------------------------------------------------------------------
    {

//        protected  boolean onTransact(int code, Parcel data,Parcel reply,int flags)throws RemoteException{
//
//            return super.onTransact(code,data,reply,flags);
//        }//binder 与activity的通信
        //voice v=voice.getInstance();

        //private MediaPlayer mediaPlayer=new MediaPlayer();
        public void setMusic(String m)
        {
                music = m;
        }
        public void setMediaPlayer() throws IOException {
            if (!mediaPlayer.isPlaying()) {
                System.out.println("music路径为" + music);
                mediaPlayer.setDataSource(music);
                mediaPlayer.prepare();
                System.out.println("初始化完成");
            }
        }
        public int play(int sign)
        {
            System.out.println("0:播放|1：暂停|2：停止");
            switch (sign)
            {
                case 0:
                    if(!mediaPlayer.isPlaying())
                    {
                        mediaPlayer.start();
                    }break;
                case 1:
                    if (mediaPlayer.isPlaying())
                    {
                        System.out.println("暂停");
                        mediaPlayer.pause();
                    }break;
                case 2:
                        mediaPlayer.stop();
                        System.out.println("停止成功");
                        mediaPlayer.reset();
                        System.out.println("重置成功");
                        break;
//                        try {
//                            setMediaPlayer();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                            System.out.println("初始化失败");
//                        }
                    default:break;

            }
            return 0;
        }
        public int getDuration()
        {
            System.out.println("长度为"+mediaPlayer.getDuration());
            return mediaPlayer.getDuration();
        }
        public int getCurrentPosition()
        {
            System.out.println("进度"+mediaPlayer.getCurrentPosition());
           return mediaPlayer.getCurrentPosition();
        }
        public void arriver(int seekbar)
        {
            System.out.println("到达该进度"+seekbar);
            mediaPlayer.seekTo(seekbar);
        }
    }
//-------------------------------------------------------------------------------------------------------------------------------------------
    public MyService() {
    }
    public void onCreate()
    {
        super.onCreate();
        System.out.println("服务创建");
//        Intent intent=new Intent(this,MainActivity.class);
//        PendingIntent pi=PendingIntent.getActivities(this,0, new Intent[]{intent},0);
//        Notification notification=new NotificationCompat.Builder(this)
//                .setContentTitle("这是content title")
//                .setContentText("这是contenttext")
//                .setWhen(System.currentTimeMillis())
//                .setSmallIcon(R.mipmap.ic_launcher)
//                .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher))
//                .setContentIntent(pi)
//                .build();
//        try {
//            mediaPlayer.setDataSource(Environment.getExternalStorageDirectory()+"/mymusic/aliez.mp3");
//            mediaPlayer.prepare();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        System.out.println("创建完成" );
    }
    public int onStartCommand(Intent intent,int flags,int startId)
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
//                stopSelf()
            }
        }).start();
        System.out.println("服务开始");
        return  super.onStartCommand(intent,flags,startId);
    }
    @Override
    public IBinder onBind(Intent intent) {
//        // TODO: Return the communication channel to the service.
//        throw new UnsupportedOperationException("Not yet implemented");
        System.out.println("onBind");
        return mBinder;
    }
    public void onDestroy()
    {
//        mBinder.play(1);
        super.onDestroy();
        System.out.println("服务摧毁");
    }
}
