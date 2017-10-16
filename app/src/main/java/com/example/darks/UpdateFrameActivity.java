package com.example.darks;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.Toast;

public class UpdateFrameActivity extends AppCompatActivity {

  static boolean success;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    super.onCreate(savedInstanceState);

    CheckTypesTask th= new CheckTypesTask();
    th.execute();

  }

  private class CheckTypesTask extends AsyncTask<Void, Void, String> {

    ProgressDialog asyncDialog = new ProgressDialog(
      UpdateFrameActivity.this);

    @Override
    protected void onPreExecute() {
      asyncDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
      asyncDialog.setMessage("업데이트중");
      asyncDialog.setCancelable(false);
      // show dialog
      asyncDialog.show();
      super.onPreExecute();
    }

    @Override
    protected String doInBackground(Void... arg0) {
      String str= "";
      Thread th = new Thread(new UpdateActivity(getPackageName()));
      th.start();
      try {
        th.join();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      if (success){
        str="성공!";
      } else {
        str="실패했습니다 다시시도해주세요";
      }
      return str;
    }

    @Override
    protected void onPostExecute(final String result) {
      asyncDialog.dismiss();
      runOnUiThread(new Runnable() {
        @Override
        public void run() {
          Toast.makeText(UpdateFrameActivity.this, result, Toast.LENGTH_SHORT).show();
        }
      });
      MainActivity.btnUpdate.setEnabled(true);
      finish();
      super.onPostExecute(result);
    }
  }
}