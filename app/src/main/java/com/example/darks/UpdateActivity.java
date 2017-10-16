package com.example.darks;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class UpdateActivity implements Runnable {

  String img1Url = "http://extcrew.wo.to/daks/daks1.jpg";
  String img2Url = "http://extcrew.wo.to/daks/daks2.jpg";
  String img3Url = "http://extcrew.wo.to/daks/daks3.jpg";
  String img4Url = "http://extcrew.wo.to/daks/daks4.jpg";
  String img5Url = "http://extcrew.wo.to/daks/daks5.jpg";
  Bitmap bm;
  String packageName = "";


  URL url = null;
  HttpURLConnection conn = null;
  InputStream is = null;
  FileOutputStream fos = null;

  public UpdateActivity(String packageName) {
    this.packageName = packageName;
  }

  public void run() {

    try {

      url = new URL(img1Url); //url객체 생성
      conn = (HttpURLConnection) url.openConnection();
      conn.connect();
      is = conn.getInputStream(); //스트림 생성
      //스트림으로부터 이미지를 다운로드 받아 비트맵으로 저장
      bm = BitmapFactory.decodeStream(is);
      fos = new FileOutputStream("/data/data/" + packageName + "/files/daks1.jpg");
      bm.compress(Bitmap.CompressFormat.JPEG, 100, fos);

      url = new URL(img2Url); //url객체 생성
      conn = (HttpURLConnection) url.openConnection();
      conn.connect();
      is = conn.getInputStream(); //스트림 생성
      //스트림으로부터 이미지를 다운로드 받아 비트맵으로 저장
      bm = BitmapFactory.decodeStream(is);
      fos = new FileOutputStream("/data/data/" + packageName + "/files/daks2.jpg");
      bm.compress(Bitmap.CompressFormat.JPEG, 100, fos);

      url = new URL(img3Url); //url객체 생성
      conn = (HttpURLConnection) url.openConnection();
      conn.connect();
      is = conn.getInputStream(); //스트림 생성
      //스트림으로부터 이미지를 다운로드 받아 비트맵으로 저장
      bm = BitmapFactory.decodeStream(is);
      fos = new FileOutputStream("/data/data/" + packageName + "/files/daks3.jpg");
      bm.compress(Bitmap.CompressFormat.JPEG, 100, fos);

      url = new URL(img4Url); //url객체 생성
      conn = (HttpURLConnection) url.openConnection();
      conn.connect();
      is = conn.getInputStream(); //스트림 생성
      //스트림으로부터 이미지를 다운로드 받아 비트맵으로 저장
      bm = BitmapFactory.decodeStream(is);
      fos = new FileOutputStream("/data/data/" + packageName + "/files/daks4.jpg");
      bm.compress(Bitmap.CompressFormat.JPEG, 100, fos);

      url = new URL(img5Url); //url객체 생성
      conn = (HttpURLConnection) url.openConnection();
      conn.connect();
      is = conn.getInputStream(); //스트림 생성
      //스트림으로부터 이미지를 다운로드 받아 비트맵으로 저장
      bm = BitmapFactory.decodeStream(is);
      fos = new FileOutputStream("/data/data/" + packageName + "/files/daks5.jpg");
      bm.compress(Bitmap.CompressFormat.JPEG, 100, fos);

      UpdateFrameActivity.success= true;
      conn.disconnect(); //url접속 해제
    } catch (Exception e) {
      e.printStackTrace();
      UpdateFrameActivity.success= false;
    }
  }
}
