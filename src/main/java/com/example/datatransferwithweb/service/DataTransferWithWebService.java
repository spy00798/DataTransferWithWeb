package com.example.datatransferwithweb.service;

import org.springframework.stereotype.Service;


import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

@Service
public class DataTransferWithWebService {

    private final String FILE_PATH = "D:\\receive\\";
    private final String FILE_EXTENSION = ".jpg";
    private static final SimpleDateFormat FORMAT = new SimpleDateFormat ("yyyyMMddHHmmss");
    private HashMap<HttpSession, InputStream> imgMap = new HashMap<>();

    public void getAPI(HttpServletRequest request, String apiurl) {
        HttpURLConnection conn = null;

        try {
            URL url = new URL(apiurl);

            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            conn.setDoOutput(false);

            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                imgMap.put(request.getSession(), conn.getInputStream());
//                readImage(conn.getInputStream());
            }
        } catch (MalformedURLException | ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    public void readImage (InputStream is) {
//        try {
//            int len = 0;
//            byte[] buffer = new byte[1024];

//            File filepath = new File("D:\\new");
//            filepath.mkdir();
//            FileOutputStream fos = new FileOutputStream(filepath + "\\" + "img" + ".jpg", false);
//               while((len = is.read(buffer)) !=  -1) {
//                fos.write(buffer, 0, len);
//            }

//            is.close();
////            fos.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    public void ImageSender(HttpServletRequest request) {
//        File file = new File("D:\\new\\img.jpg");
        int len = 0;
        byte[] buffer = new byte[1024];
        HttpURLConnection conn = null;

        try {
            URL url = new URL("http://localhost/receive2");
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(5000);
            conn.setDoInput(true);
            conn.setDoOutput(true);

//          FileInputStream fis = new FileInputStream(file);

            InputStream is = imgMap.get(request.getSession());

            OutputStream cos = conn.getOutputStream();

            while((len = is.read(buffer)) != -1) {
                cos.write(buffer, 0, len);
            }
            is.close();
            cos.flush();

            System.out.println(conn.getResponseCode());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } //finally {
//            if(file.exists()) {
//                if(file.delete()) {
//                    System.out.println("삭제완료");
//                }
//                else {
//                    System.out.println("삭제실패");
//                }
//            } else {
//                    System.out.println("파일이 존재하지 않음");
//            }
//
//        }
    }

    public void ReceiveImage(HttpServletRequest request) {
        System.out.println("saved.");
        int len = 0;
        byte[] buffer = new byte[1024];
        Date nowDate = new Date();
        DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        String date = format.format(nowDate);
        File filepath = new File("D:\\receive");
        filepath.mkdir();
        try {
            InputStream is = request.getInputStream();
            FileOutputStream fos = new FileOutputStream(filepath + "\\" + date + ".jpg");
            while ((len = is.read(buffer)) != -1) {
                fos.write(buffer, 0 , len);
            }

            is.close();
            fos.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void ImageReceiver(HttpServletRequest request) {
        Date time = new Date();
        String fileName = FORMAT.format(time);


        try {

            FileOutputStream fileOutputStream = new FileOutputStream(FILE_PATH + fileName + FILE_EXTENSION);
            int len;
            byte[] buffer = new byte[1024];



            while((len = request.getInputStream().read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createImage(HttpServletRequest request) {
        getAPI(request, "http://192.168.0.240/cgi-bin/snapshot.jpg");
    }
}
