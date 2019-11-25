package com.example.music;

import android.os.Environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileUtils {
    private String SDPATH;
    private String PATH;
    public String getSDPATH()
    {
        return SDPATH;
    }
    public String getPATH(){return PATH;}
    public FileUtils()
    {
        SDPATH= Environment.getExternalStorageDirectory()+"/";//获取当前外部储存设备的根目录
    }
    public File creadSDfile(String filename)throws IOException{//在SD卡上创建文件
        System.out.println("文件路径："+PATH+filename);
        File file=new File(PATH+filename);
        file.createNewFile();
        System.out.println("文件创建成功："+PATH+filename);
        return file;
    }
    public File creadSDDir(String dirName){//在SD卡上创建目录
        File dir=new File(SDPATH+dirName);
        PATH=SDPATH+dirName+"/";
        System.out.println("挡墙路径:"+PATH);
        dir.mkdir();
        System.out.println("目录创建成功："+PATH);
        return  dir;
    }
    public boolean isFileExist(String fileName)//判断文件是否存在
    {
        File file=new File(SDPATH+fileName);
        return  file.exists();
    }
    public File writeInputStream2SD(String path, String fileName, InputStream inputStream)
    {
        File file=null;
        OutputStream outputStream=null;
        try{
            creadSDDir(path);//创建一个目录
            file =creadSDDir(path+fileName);//创建一个文件
            outputStream=new FileOutputStream(file);//为此文件创建一个输出流来写入数据
            byte buffer[]=new byte[1024*4];// 创建一个4字节缓存
            while((inputStream.read(buffer)!=-1))
            {
                outputStream.write(buffer);//先从输入流里读取4k字节数据到buffer里，在从buffer写到文件里
            }
            outputStream.flush();//操作完成清空输出流
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try{
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return  file;
    }
}
