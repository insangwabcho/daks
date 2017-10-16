package com.example.darks;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PhotoSetActivity extends AppCompatActivity {

  ArrayList<File> filelists;
  ListView list1;
  ImageView imgview;
  ArrayList<String> filenames;
  Map<String,File> map;
  Button btnOk, btnExit;
  String selectedFilePath;
  RadioGroup rg;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    super.onCreate(savedInstanceState);
    setContentView(R.layout.photoset);

    list1 = (ListView) findViewById(R.id.list1);
    imgview = (ImageView) findViewById(R.id.imageView2);
    btnOk= (Button) findViewById(R.id.btnOk);
    btnExit= (Button) findViewById(R.id.btnExit);
    rg= (RadioGroup) findViewById(R.id.radioGroup);


    btnOk.setEnabled(false);

    filelists = new ArrayList<>();
    map= new HashMap<>();
    filenames= new ArrayList<>();

    getImages();
    if (filelists.size()==0 || filelists==null){
      Toast.makeText(this, "사진이 한장도 없네요", Toast.LENGTH_SHORT).show();
      finish();
    }

    for (File f: filelists){
      filenames.add(f.getName());
      map.put(f.getName(),f);
    }

    ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, filenames);
    list1.setAdapter(adapter);

    list1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        setImage(filenames.get(position));
      }
    });
    
    btnOk.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        saveImage();
      }
    });

    btnExit.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        finish();
      }
    });
  }

  private void saveImage(){
    String path="/data/data/"+getPackageName()+"/files/";
    switch (rg.getCheckedRadioButtonId()){
      case 2131427456: //1번사진
        path+= "daks1.jpg";
        break;
      case 2131427453: //2번사진
        path+= "daks2.jpg";
        break;
      case 2131427458: //3번사진
        path+= "daks3.jpg";
        break;
      case 2131427457: //4번사진
        path+= "daks4.jpg";
        break;
      case 2131427454: //5번사진
        path+= "daks5.jpg";
        break;
    }
    BufferedOutputStream bos= null;
    BufferedInputStream bis= null;
    try {
      bos= new BufferedOutputStream(new FileOutputStream(new File(path)));
      bis= new BufferedInputStream(new FileInputStream(new File(selectedFilePath)));
      int i=0;
      while ((i=bis.read())!= -1){
        bos.write(i);
      }
      bos.flush();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (bos!= null) try {
        bos.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
      if (bis!= null) try {
        bis.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    Toast.makeText(this, "사진교체 완료!", Toast.LENGTH_SHORT).show();
    finish();
  }
  
  private void setImage(String filename){
    selectedFilePath= map.get(filename).getPath();
    Bitmap mBitmap= BitmapFactory.decodeFile(map.get(filename).getPath());
    imgview.setImageBitmap(mBitmap);
    rg.setEnabled(true);
    btnOk.setEnabled(true);
  }

  private void getImages() {
    checkPermission();
    String path1 = Environment.getExternalStorageDirectory().getPath()+"/Download";
    String path2 = Environment.getExternalStorageDirectory().getPath() + "/Pictures";
    String path3 = Environment.getExternalStorageDirectory().getPath() + "/Pictures/KakaoTalk";

    File f1 = new File(path1); //경로
    File[] files1 = f1.listFiles();

    File f2 = new File(path2); //경로
    File[] files2 = f2.listFiles();

    File f3 = new File(path3); //경로
    File[] files3 = f2.listFiles();

    if (files1 != null) {
      for (File t : files1) {
        String name = t.getName();
        if (name.indexOf(".jpg") != -1 || name.indexOf(".png") != -1 || name.indexOf(".jpeg") != -1) {
          filelists.add(t);
        }
      }
    }
    if (files2 != null) {
      for (File t : files2) {
        String name = t.getName();
        if (name.indexOf(".jpg") != -1 || name.indexOf(".png") != -1 || name.indexOf(".jpeg") != -1) {
          filelists.add(t);
        }
      }
    }
    if (files3 != null) {
      for (File t : files3) {
        String name = t.getName();
        if (name.indexOf(".jpg") != -1 || name.indexOf(".png") != -1 || name.indexOf(".jpeg") != -1) {
          filelists.add(t);
        }
      }
    }

  }

  private void checkPermission() {
    if (Build.VERSION.SDK_INT == Build.VERSION_CODES.M) {

                    /* 사용자 단말기의 권한 중 "전화걸기" 권한이 허용되어 있는지 체크한다.
                    *  int를 쓴 이유? 안드로이드는 C기반이기 때문에, Boolean 이 잘 안쓰인다.
                    */
      int permissionResult = checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);

                    /* CALL_PHONE의 권한이 없을 때 */
      // 패키지는 안드로이드 어플리케이션의 아이디다.( 어플리케이션 구분자 )
      if (permissionResult == PackageManager.PERMISSION_DENIED) {


                        /* 사용자가 CALL_PHONE 권한을 한번이라도 거부한 적이 있는 지 조사한다.
                        * 거부한 이력이 한번이라도 있다면, true를 리턴한다.
                        */
        if (shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {

          AlertDialog.Builder dialog = new AlertDialog.Builder(PhotoSetActivity.this);
          dialog.setTitle("권한이 필요합니다.")
            .setMessage("이 기능을 사용하기 위해서는 권한이 필요합니다. 계속하시겠습니까?")
            .setPositiveButton("네", new DialogInterface.OnClickListener() {
              @Override
              public void onClick(DialogInterface dialog, int which) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                  requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1000);
                }

              }
            })
            .setNegativeButton("아니오", new DialogInterface.OnClickListener() {
              @Override
              public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(PhotoSetActivity.this, "기능을 취소했습니다.", Toast.LENGTH_SHORT).show();
              }
            })
            .create()
            .show();
        }

        //최초로 권한을 요청할 때
        else {
          // CALL_PHONE 권한을 Android OS 에 요청한다.
          requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1000);
        }
      }
    }
  }

  void test(){
    String filepath1 = Environment.getExternalStorageDirectory().getPath() + "/Picture";
    ArrayList<File> folder = new ArrayList<>();
    int aCount=0;
    while (true) {
      ArrayList<File> imgs = new ArrayList<>();

      File f = new File(filepath1);
      if (!f.exists()) {
        f.mkdir();
      }

      File[] files = f.listFiles();
      for (File t : files) {
        if (t.isDirectory()) {
          folder.add(t);
        } else if (t.getName().indexOf(".jpg") != -1) {
          imgs.add(t);
        }
        filepath1= filepath1+folder.get(aCount).getName();
      }
    }
  }
}
