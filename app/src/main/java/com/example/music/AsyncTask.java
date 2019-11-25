package com.example.music;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class AsyncTask extends android.os.AsyncTask<String,Integer,InputStream> {


    @Override
    protected void onPreExecute() {
        System.out.println("进程对话框");
    }

    protected InputStream doInBackground(String... urlStr) {//String...为可变长数组，接收0到多个参数

        StringBuffer StringBuffer = new StringBuffer();//字符串数组对象
        String line = null;
        BufferedReader bufferedReader = null;
        InputStream inputStream=null;
        try {
            /**
             httpURLConnection.getInputStream()以字节的方式读取需要下载文件
             InputStreamReader把一个字节流转换成字符流
             BufferedReader把字符流套上一个BufferReader装饰方便一行行读取
             **/
            URL url = new URL(urlStr[0]);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestProperty("Accept", "*/*");
            httpURLConnection.connect();
//            httpURLConnection.setRequestMethod("GET");
//            httpURLConnection.setDoInput(true);
//            httpURLConnection.setDoOutput(true);
            inputStream = httpURLConnection.getInputStream();
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            //bufferedReader=new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
             /*
                以行为单位读取数据，每次读取一行，并把这个行字符返回到line中
                当line不为null指针，则line里有数据
                这时把读取的line对象添加到将要返回的Stringbuffer中
                当字符读取完毕readline方法没有读取到数据则返回null
                 */
            while ((line = bufferedReader.readLine()) != null) {
                StringBuffer.append(line);
                System.out.println(line);
            }
            System.out.println(StringBuffer.toString());
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }

        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return inputStream;
    }

    @Override
    protected void onPostExecute(InputStream inputStream) {
        super.onPostExecute(inputStream);
        HttpDownloader downloader=new HttpDownloader();
        FileUtils fileUtils=new FileUtils();
        System.out.println("文件路径为"+fileUtils.getSDPATH());
        fileUtils.creadSDDir("Qbina");
        try {
            fileUtils.creadSDfile("dragon");
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (fileUtils.isFileExist("Qbina/dragon")) {
            System.out.println("存在");
        } else {
            System.out.println("不存在");
        }
        System.out.println("下载文件");
        OutputStream outputStream=null;
        File dir=new File(fileUtils.getPATH());
        File file=new File(fileUtils.getPATH()+"dragon");
        try {
            outputStream = new FileOutputStream(file);
            int length;
            byte[] buffer = new byte[2 * 1024];
            while ((length = inputStream.read(buffer) )!= -1) {
                outputStream.write(buffer, 0, length);
            }
            outputStream.flush();
            System.out.println("写入成功");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("写入失败");
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(outputStream!=null)
            {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("写入成功");
        //int result=downloader.downloadFile("https://res.iciba.com/resource/amp3/oxford/0/1c/f3/1cf3980c4529878b690ded143c409664.mp3","111","111");
        //System.out.println(result);

    }
}
