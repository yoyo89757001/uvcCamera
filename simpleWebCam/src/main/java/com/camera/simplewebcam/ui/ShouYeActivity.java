package com.camera.simplewebcam.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;

import com.camera.simplewebcam.R;

public class ShouYeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shou_ye);

        final ImageView imageView= (ImageView) findViewById(R.id.dengji);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView.setClickable(false);
                startActivityForResult(new Intent(ShouYeActivity.this, InFoActivity2.class),2);

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1800);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    imageView.setClickable(true);
                                }
                            });
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });
        ImageView imageView2= (ImageView) findViewById(R.id.shezhi);
        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(ShouYeActivity.this, SheZhiActivity.class));

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK && requestCode == 2) {
          //  Log.d("ShouYeActivity", "回来了");
            // 选择预约时间的页面被关闭
            String date = data.getStringExtra("date");
            if (date.equals("11")){
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {

                            Thread.sleep(600);
                            startActivityForResult(new Intent(ShouYeActivity.this,InFoActivity2.class),2);

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                }).start();

            }

        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            System.out.println("按下了back键   onKeyDown()");
            return true;
        }else {
            return super.onKeyDown(keyCode, event);
        }

    }
}
