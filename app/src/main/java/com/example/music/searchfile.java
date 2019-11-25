package com.example.music;

import java.io.File;
import java.util.ArrayList;

public class searchfile {
    public ArrayList<File> searchmusicfile(File rootsFile)
    {
        ArrayList<File> filelist=new ArrayList<File>();
        File[] files=rootsFile.listFiles();//列出当前所有文件
        if(files.length>0)
        {
            for(File file:files)
            {
                if(file.isDirectory())//如果是文件夹则向文件夹里遍历
                {
                    searchmusicfile(file);
                }
                else{
                    String name=file.getName();
                    if(name.endsWith(".mp3"))//如果以MP3为结尾则添加到列表里
                    {
                        filelist.add(file);
                    }
                }
            }
        }
        return filelist;
    }
}
