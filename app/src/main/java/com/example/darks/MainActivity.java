package com.example.darks;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import java.util.Properties;

public class MainActivity extends AppCompatActivity {

  Button btnStart, btnSetting;
  static Button btnUpdate;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    setContentView(R.layout.activity_main);

    btnStart = (Button) findViewById(R.id.btnStart);
    btnUpdate = (Button) findViewById(R.id.btnUpdate);
    btnSetting = (Button) findViewById(R.id.btnSetting);

    Properties prop = new Properties();

  }

  public void onClick(View v) {
    Intent intent = null;
    switch (v.getId()) {
      case R.id.btnStart:
        intent = new Intent(this, RollingImageActivity.class);
        break;
      case R.id.btnUpdate:
        intent = new Intent(this, UpdateFrameActivity.class);
        btnUpdate.setEnabled(false);
        break;
      case R.id.btnSetting:
        intent = new Intent(this, SettingActivity.class);
        break;
      case R.id.btnPhotoSet:
        intent = new Intent(this, PhotoSetActivity.class);
        break;
    }
    startActivity(intent);
  }
}
