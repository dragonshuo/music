package com.example.music;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

class HttpDownloader {
    private URL url=null;
    public String download(final String urlStr) {
        new AsyncTask().execute(urlStr);
        //---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
        /*new Thread(new Runnable() {
            @Override
            public void run() {
                StringBuffer StringBuffer=new StringBuffer();//字符串数组对象
                String line=null;
                BufferedReader bufferedReader=null;
                try{
            /**
            httpURLConnection.getInputStream()以字节的方式读取需要下载文件
            InputStreamReader把一个字节流转换成字符流
            BufferedReader把字符流套上一个BufferReader装饰方便一行行读取
             **/
                   /* url=new URL(urlStr);
                    HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
                    httpURLConnection.setRequestMethod("GET");
                    httpURLConnection.setDoInput(true);
                    httpURLConnection.setDoOutput(true);
                    InputStream inputStream=httpURLConnection.getInputStream();
                    bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
                    //bufferedReader=new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
             /*
                以行为单位读取数据，每次读取一行，并把这个行字符返回到line中
                当line不为null指针，则line里有数据
                这时把读取的line对象添加到将要返回的Stringbuffer中
                当字符读取完毕readline方法没有读取到数据则返回null
                 */
                  /*  while((line=bufferedReader.readLine())!=null)
                    {
                        StringBuffer.append(line);
                        System.out.println(line);
                    }
                    System.out.println(StringBuffer.toString());
                    if(httpURLConnection!=null)
                    {
                        httpURLConnection.disconnect();
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();
        return  null;*/
                  //------------------------------------------------------------------------------------------------------

        return urlStr;
    }


    public int downloadFile(String urlStr,String name) {
        new dfile().execute(urlStr,name);
        /*
        -1 下载文件出错
        0 下载文件成功
        1 文件已存在
         */
//        InputStream inputStream=null;
//        try {
//            FileUtils fileUtils=new FileUtils();
//            if (fileUtils.isFileExist(path+fileName))
//            {
//                return 1;
//            }
//            else {
//                inputStream=getInputStreamFromUrl(urlStr);
//                File file=fileUtils.writeInputStream2SD(path,fileName,inputStream);
//                if(file==null)
//                {
//                    return -1;
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            return  -1;
//        }finally {
//            try {
//                inputStream.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
        return 0;
    }
    public InputStream getInputStreamFromUrl(String urlStr)throws  MalformedURLException,IOException
    {
        url=new URL(urlStr);
        HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
        InputStream inputStream=httpURLConnection.getInputStream();
        return  inputStream;
    }
}
