package com.example.music;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.accessibilityservice.AccessibilityService;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.io.PipedReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, android.widget.SeekBar.OnSeekBarChangeListener {
    private MyService.voiceBinder voiceBinder;//获取service中的Ibinder,通过使用其中函数达成与service 的通信
    private SeekBar SeekBar;
    private String m=new String();
    private int sign,init=1,update;
    private Button play,search,next,last,random,add,deletemusic;//--------------------------------------------------------------
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE" };
    private ArrayList<String> filenames=new ArrayList<String>();
    private int i=0,r=0;
    private FileUtils fileUtils=new FileUtils();



    public static void verifyStoragePermissions(Activity activity) {

        try {
            //检测是否有写的权限
            int permission = ActivityCompat.checkSelfPermission(activity,
                    "android.permission.WRITE_EXTERNAL_STORAGE");
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // 没有写的权限，去申请写的权限，会弹出对话框
                ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,REQUEST_EXTERNAL_STORAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//------------------------------------------------------------------------------------------------------------------
    private ServiceConnection connection=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
//            if(init==0) {
            System.out.println("连接成功");
                voiceBinder = (MyService.voiceBinder) iBinder;
                String [] musiclist= new String[filenames.size()];
                filenames.toArray(musiclist);
                System.out.println("组合文件路径为"+fileUtils.getPATH()+musiclist[0]);
                voiceBinder.setMusic(fileUtils.getPATH()+musiclist[0]);
            try {
                voiceBinder.setMediaPlayer();
                SeekBar.setMax(voiceBinder.getDuration());
            } catch (IOException e) {
                e.printStackTrace();
            }
//                //voiceBinder.setMusic(m);
//                System.out.println("m当前路径为" + m);
//                    //voiceBinder.setMediaPlayer();
//                    SeekBar.setMax(voiceBinder.getDuration());
//                init=init+1;//控制初始化
//            }
//            update=voiceBinder.progress();
//            voiceBinder.play(sign);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            System.out.println("断开服务连接");
        }
    };
    //跟新进度条
    private Handler handler=new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);
            switch (msg.what)
            {
                case 1:
                    updateProgress();
                    break;
            }
        }
    };

    private void updateProgress(){
        if(SeekBar.getProgress()==SeekBar.getMax())
        {
            SeekBar.setProgress(0);
            voiceBinder.play(2);
            String [] musiclist= new String[filenames.size()];
            filenames.toArray(musiclist);
            if(init==-1){//=========================================
                r = (int) (0 + Math.random() * (filenames.size()-1 - 0 + 1));
                System.out.println("随机完成"+r);
                i=r;
            }
            else {//======================================================
                if ((i + 1) < musiclist.length) {
                    i = i + 1;
                } else {
                    i = 0;
                }
            }
            System.out.println("第"+(i+1)+"首");
            System.out.println("组合文件路径为"+fileUtils.getPATH()+musiclist[i]);
            voiceBinder.setMusic(fileUtils.getPATH()+musiclist[i]);
            try {
                voiceBinder.setMediaPlayer();
            } catch (IOException e) {
                e.printStackTrace();
            }
            SeekBar.setMax(voiceBinder.getDuration());
            voiceBinder.play(0);
        }else {
            int currenPostion = voiceBinder.getCurrentPosition();
            System.out.println("当前进度" + currenPostion);
            SeekBar.setProgress(currenPostion);
        }
        handler.sendEmptyMessageDelayed(1, 500);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SeekBar=(SeekBar)findViewById(R.id.progress);
        search=(Button)findViewById(R.id.search);
        next=(Button)findViewById(R.id.next);
        last=(Button)findViewById(R.id.last);
        random=(Button)findViewById(R.id.random_order) ;
        add=(Button)findViewById(R.id.addmusic);
        deletemusic=(Button)findViewById(R.id.deletemusic);
        SeekBar.setOnSeekBarChangeListener(this);
        search.setOnClickListener(this);
        verifyStoragePermissions(MainActivity.this);
        play=findViewById(R.id.play);
        play.setOnClickListener(this);
        next.setOnClickListener(this);
        last.setOnClickListener(this);
        random.setOnClickListener(this);
        add.setOnClickListener(this);
        deletemusic.setOnClickListener(this);
        fileUtils.creadSDDir("mymusic");
        String path=fileUtils.getSDPATH()+"mymusic";
        System.out.println("获取path"+path);
        File file=new File(path);
        if(file.exists())
        {
            System.out.println("文件存在");
        }
//       File file=new File(fileUtils.getPATH()+"/aliez.mp3");

        sign=-1;
        searchfile searchfile=new searchfile();
        ArrayList<File> list=searchfile.searchmusicfile(file);
        System.out.println("检索完成");
        for(File file1:list)
        {
            if(file1.exists())
            {
                System.out.println("检索文件存在");
            }
            System.out.println("正在转型");
            filenames.add(file1.getName());
            System.out.println(file1.getName());
        }
        String [] musiclist= new String[filenames.size()];
        filenames.toArray(musiclist);
        System.out.println("第一个歌曲名为："+musiclist[0]);
        Intent startIntent=new Intent(this,MyService.class);
        startService(startIntent);
        System.out.println("服务创建成功");
        Intent bindIntent=new Intent(this,MyService.class);
        if(bindService(bindIntent,connection,BIND_AUTO_CREATE)) {
            System.out.println("绑定成功");
        }
        //-----------------------------------------------------------------------
//        if(file.exists())
//        {
//            System.out.println("文件存在"+file.getPath());
//            m=file.getPath();
//            sign=-1;
        //}----------------------------------------------------------------
//        Intent intent=getIntent();
//        String music=intent.getStringExtra("file");
//        file=new File(music);
//        if(file.exists())
//        {
//            voiceBinder.setMusic(music);
//            try {
//                voiceBinder.setMediaPlayer();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }//---------------------------------------------------------------

//        Point p=new Point();
//        WindowManager windowManager=(WindowManager) getSystemService(Context.WINDOW_SERVICE);
//        windowManager.getDefaultDisplay().getSize(p);
//        int screenWidth=p.x;
//        int screenHeight=p.y;
//        System.out.println("屏幕大小为"+screenWidth+"*"+screenHeight);
    }
//    public void anotherright(View v)
//    {
//        fragmentManager=getSupportFragmentManager();
//        fragmentTransaction=fragmentManager.beginTransaction();
//        fragmentTransaction.replace(R.id.list,new list());
//        fragmentTransaction.addToBackStack(null);
//        frameLayout=(frameLayout)findViewById(R.id.list);
//        frameLayout.setLayoutParams(new LinearLayout.LayoutParams(0,MATCH_PARENT,2,0f));
//        fragmentTransaction.commit();
//        Toast.makeText(MainActivity.this,"你点击了按钮",Toast.LENGTH_SHORT).show();
//    }

    public void find()
    {
        filenames.clear();
        String path=fileUtils.getSDPATH()+"mymusic";
        System.out.println("获取path"+path);
        File file=new File(path);
        if(file.exists())
        {
            System.out.println("文件存在");
        }
//       File file=new File(fileUtils.getPATH()+"/aliez.mp3");

        sign=-1;
        searchfile searchfile=new searchfile();
        ArrayList<File> list=searchfile.searchmusicfile(file);
        System.out.println("检索完成");
        for(File file1:list)
        {
            if(file1.exists())
            {
                System.out.println("检索文件存在");
            }
            System.out.println("正在转型");
            filenames.add(file1.getName());
            System.out.println(file1.getName());
        }
        String [] musiclist= new String[filenames.size()];
        filenames.toArray(musiclist);
        System.out.println("第一个歌曲名为："+musiclist[0]);
        System.out.println("一共"+filenames.size());
    }
    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
//            case R.id.call:
//                replaceFragment(new list());
//            break;
//            case R.id.service_start:
//                Intent startIntent=new Intent(this,MyService.class);
//                startService(startIntent);
//                break;
//            case R.id.service_stop:
//                Intent stopIntent=new Intent(this,MyService.class);
//                stopService(stopIntent);
//                break;
//            case R.id.bind_service:
//                //m
//                //sign
//                Intent bindIntent=new Intent(this,MyService.class);
//                bindService(bindIntent,connection,BIND_AUTO_CREATE);//绑定服务
//                break;
//            case R.id.unbind_service:
//                unbindService(connection);//解绑服务
//                break;
            case R.id.play:
//                unbindService(connection);
                if (sign==-1)
            {
                sign=0;
                play.setText("pause");
                handler.sendEmptyMessage(1);
            }else if(sign==0)
            {
                sign=1;
               // handler.sendEmptyMessage(2);
                play.setText("play");
            }else if(sign==1)
            {
                sign=0;
                play.setText("pause");
            }
                //Intent bindIntent1=new Intent(this,MyService.class);
//                bindService(bindIntent1,connection,BIND_AUTO_CREATE);//绑定服务
                voiceBinder.play(sign);
            break;
            case R.id.search:
                Intent intent=new Intent(MainActivity.this,selectmusic.class);
                startActivityForResult(intent,1);
                break;
            case R.id.next:
                SeekBar.setProgress(0);
                voiceBinder.play(2);
                String [] musiclist= new String[filenames.size()];
                filenames.toArray(musiclist);
                if(init==-1)//==============================================================================
                {
                    r = (int) (0 + Math.random() * (filenames.size() - 0-1 + 1));
                    System.out.println("随机下"+r);
                    i=r;
                }
                else {//====================================================================================
                    if ((i + 1) < musiclist.length) {
                        i = i + 1;
                    } else {
                        i = 0;
                    }
                }
                voiceBinder.setMusic(fileUtils.getPATH()+musiclist[i]);
                try {
                    voiceBinder.setMediaPlayer();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                SeekBar.setMax(voiceBinder.getDuration());
                voiceBinder.play(0);
                break;
            case R.id.last:
                SeekBar.setProgress(0);
                voiceBinder.play(2);
                String [] musiclist1= new String[filenames.size()];
                filenames.toArray(musiclist1);
                if(init==-1)//===================================
                {
                    r = (int) (0 + Math.random() * (filenames.size() - 0-1 + 1));
                    System.out.println("随机上"+r);
                    i=r;
                }
                else {//==================================================
                    if (i > 0) {
                        i = i - 1;
                    } else {
                        i = musiclist1.length - 1;
                    }
                }
                voiceBinder.setMusic(fileUtils.getPATH()+musiclist1[i]);
                try {
                    voiceBinder.setMediaPlayer();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                SeekBar.setMax(voiceBinder.getDuration());
                voiceBinder.play(0);
                break;
            case R.id.random_order:
                if(init!=-1) {
                    init=-1;
                    random.setText("order");
                }
                else{
                    init=1;
                    random.setText("random");
                    r=0;
                }
            case R.id.addmusic:
                Intent intent1=new Intent(MainActivity.this,list_add.class);
                startActivity(intent1);
                break;
            case R.id.deletemusic:
                Intent intent2=new Intent(MainActivity.this,list_delete.class);
                startActivity(intent2);
            default:break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        find();
    }

    @Override
    protected  void onActivityResult(int requestCode,int resultCode,Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode)
        {
            case 1:
                if(resultCode==RESULT_OK)
                {
                    String name=data.getStringExtra("file");
                    i=data.getIntExtra("int",-1);
                    System.out.println("获取位置为"+i);
                    if(i==-1)
                    {

                    }
                    else {
                        System.out.println("获取到文件名" + name);
                        voiceBinder.play(2);
                        voiceBinder.setMusic(name);
                        try {
                            voiceBinder.setMediaPlayer();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        SeekBar.setMax(voiceBinder.getDuration());
                        SeekBar.setProgress(0);
                        voiceBinder.play(0);
                        sign = 0;
                    }
                }
        }
    }
//-------------------------------------------进度条点击事件-----------------------------------------------
    @Override
    public void onProgressChanged(android.widget.SeekBar seekBar, int i, boolean b) {
       // voiceBinder.arriver(i);
    }

    @Override
    public void onStartTrackingTouch(android.widget.SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(android.widget.SeekBar seekBar) {
        voiceBinder.arriver(seekBar.getProgress());
    }
    //------------------------------------------------------------------------------------------------------
//    private void replaceFragment(Fragment fragment)
//    {
//        FragmentManager fragmentManager=getSupportFragmentManager();
//        FragmentTransaction transaction=fragmentManager.beginTransaction();
//        transaction.add(R.id.list,fragment);
////        list list=(list)getSupportFragmentManager().findFragmentById(R.id.list);//获取碎片
////        View view=list.getView();
////        ViewGroup.LayoutParams layoutParams=view.getLayoutParams();
////        layoutParams.height=0;
////        layoutParams.width=0;
//        transaction.addToBackStack(null);//返回栈
//        transaction.commit();
//    }
}
