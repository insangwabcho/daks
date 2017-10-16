package com.example.darks;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import static com.example.darks.R.id.layoutk;

public class RollingImageActivity extends AppCompatActivity implements Runnable {

  ImageView mImgTrans;
  Bitmap[] mBitmaps;
  int aCount;
  String path;
  boolean current;
  String[] paths = {
    "http://extcrew.wo.to/daks/daks1.jpg",
    "http://extcrew.wo.to/daks/daks2.jpg",
    "http://extcrew.wo.to/daks/daks3.jpg",
    "http://extcrew.wo.to/daks/daks4.jpg",
    "http://extcrew.wo.to/daks/daks5.jpg"
  };
  long optionTime;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    setContentView(R.layout.rolling);
    super.onCreate(savedInstanceState);

    mImgTrans = (ImageView) findViewById(R.id.imageView);
    findViewById(layoutk).setBackgroundColor(Color.BLACK);

    mBitmaps = new Bitmap[5];

    try {
      Properties prop= new Properties();
      prop.load(new FileInputStream(new File("/data/data/"+getPackageName()+"/files/set.prop")));
      optionTime= Long.parseLong(prop.getProperty("time"));
    } catch (IOException e) {
      e.printStackTrace();
    }

    Thread ths = new Thread(new Runnable() {
      @Override
      public void run() {
          mBitmaps[0]= BitmapFactory.decodeFile("/data/data/"+getPackageName()+"/files/daks1.jpg");
          mBitmaps[1]= BitmapFactory.decodeFile("/data/data/"+getPackageName()+"/files/daks2.jpg");
          mBitmaps[2]= BitmapFactory.decodeFile("/data/data/"+getPackageName()+"/files/daks3.jpg");
          mBitmaps[3]= BitmapFactory.decodeFile("/data/data/"+getPackageName()+"/files/daks4.jpg");
          mBitmaps[4]= BitmapFactory.decodeFile("/data/data/"+getPackageName()+"/files/daks5.jpg");
      }
    });
    ths.start();
    try {
      ths.join();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    current = true;
    path = "1";
    Thread th = new Thread(this);
    th.start();
  }

  @Override
  protected void onPause() {
    super.onPause();
    current = false;
  }

  @Override
  protected void onResume() {
    super.onResume();
    current = true;
  }

  @Override
  public void run() {
    while (current) {
      runOnUiThread(new Runnable() {
        @Override
        public void run() {
          mImgTrans.clearAnimation();
          mImgTrans.setAnimation(new AnimationAlphaOfft(1.0f, 0.0f));
        }
      });
      try {
        Thread.sleep(1900);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      runOnUiThread(new Runnable() {
        @Override
        public void run() {
          mImgTrans.setVisibility(ImageView.INVISIBLE);
          mImgTrans.clearAnimation();
          mImgTrans.setAnimation(new AnimationAlphaOnt(0.0f, 1.0f));
          mImgTrans.setVisibility(ImageView.VISIBLE);
          switch (aCount) {
            case 0:
              mImgTrans.setImageBitmap(mBitmaps[0]);
              break;
            case 1:
              mImgTrans.setImageBitmap(mBitmaps[1]);
              break;
            case 2:
              mImgTrans.setImageBitmap(mBitmaps[2]);
              break;
            case 3:
              mImgTrans.setImageBitmap(mBitmaps[3]);
              break;
            case 4:
              mImgTrans.setImageBitmap(mBitmaps[4]);
              break;
          }
        }
      });
      try{
        Thread.sleep(optionTime);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      switch (aCount) {
        case 0:
          path = "2";
          aCount = 1;
          break;
        case 1:
          path = "3";
          aCount = 2;
          break;
        case 2:
          path = "4";
          aCount = 3;
          break;
        case 3:
          path = "5";
          aCount = 4;
          break;
        case 4:
          path = "0";
          aCount = 0;
          break;
      }
    }
  }

  class AnimationAlphaOnt extends AlphaAnimation {
    public AnimationAlphaOnt(float fromAlpha, float toAlpha) {
      super(fromAlpha, toAlpha);
      setDuration(2000);
    }
  }

  class AnimationAlphaOfft extends AlphaAnimation {
    public AnimationAlphaOfft(float fromAlpha, float toAlpha) {
      super(fromAlpha, toAlpha);
      setDuration(2000);
    }
  }

}