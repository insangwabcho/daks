package com.example.darks;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class SettingActivity extends AppCompatActivity {

  Properties prop;
  String optionTime;
  EditText editTime;
  Button btnSave, btnOk;
  TextView txtResult;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    super.onCreate(savedInstanceState);
    setContentView(R.layout.setting);

    prop = new Properties();
    editTime = (EditText) findViewById(R.id.editTime);
    btnSave = (Button) findViewById(R.id.btnSave);
    btnOk= (Button) findViewById(R.id.btnOk);
    txtResult= (TextView) findViewById(R.id.txtResult);

    loadOption();

    btnSave.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        saveOption("time");
      }
    });

    btnOk.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        finish();
      }
    });
  }

  private void loadOption() {
    File f = new File("/data/data/" + getPackageName() + "/files/set.prop");
    BufferedWriter bw = null;
    if (!f.exists()) {
      try {
        bw = new BufferedWriter(new FileWriter(f));
        bw.write("time:10000");
        bw.flush();
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      } finally {
        try {
          bw.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }

    try {
      InputStream is = new FileInputStream(f);
      prop.load(is);
      optionTime = prop.getProperty("time");
      int viewTime = Integer.parseInt(optionTime) / 1000;
      editTime.setText(viewTime + "");
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void saveOption(final String args) {
    String select = args;

    if (select.equals("time")) {
      int time = Integer.parseInt(editTime.getText().toString());
      if (time<2){
        Toast.makeText(this, "2초 미만으로 입력하실수 없습니다", Toast.LENGTH_SHORT).show();
        return;
      }
      File f = new File("/data/data/" + getPackageName() + "/files/set.prop");
      BufferedWriter bw = null;
      try {
        bw = new BufferedWriter(new FileWriter(f));
        bw.write("time:" + (time * 1000));
        bw.flush();
      } catch (IOException e) {
        e.printStackTrace();
        Toast.makeText(this, "실패하였습니다.", Toast.LENGTH_SHORT).show();
      } finally {
        try {
          bw.close();
          txtResult.setTextColor(Color.BLUE);
          txtResult.setText("변경사항 저장완료");
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    } else {

    }
  }
}
