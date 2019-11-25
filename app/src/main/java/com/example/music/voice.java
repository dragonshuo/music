package com.example.music;

import android.media.MediaPlayer;

public class voice {
    private voice()
    {

    }
    private static  voice lei= new voice();
    public synchronized  static  voice getInstance()//单例模式即每次调用都返回相同的对象
    {
        return  lei;
    }
    public void play(MediaPlayer mediaPlayer)
    {
        mediaPlayer.start();
    }
    public void  pause(MediaPlayer mediaPlayer)
    {
        mediaPlayer.pause();
    }
    public boolean isplay(MediaPlayer mediaPlayer)
    {
        return mediaPlayer.isPlaying();
    }
    public long getduring(MediaPlayer mediaPlayer)
    {
        return mediaPlayer.getDuration();//获取播放时长
    }
    public long getcurrentduring(MediaPlayer mediaPlayer)
    {
        return mediaPlayer.getCurrentPosition();//获取当前播放进度
    }
    public int position(int current)
    {
        return  current;//获取位置
    }
    public void curento(int position,MediaPlayer mediaPlayer)
    {
        mediaPlayer.seekTo(position);//设置进度
    }
    public void stop(MediaPlayer mediaPlayer)
    {
        if(mediaPlayer!=null)
        {
            if(mediaPlayer.isPlaying())
            {
                mediaPlayer.stop();
            }
            mediaPlayer.release();
        }
    }
}
