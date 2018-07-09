package com.camera.simplewebcam.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import com.camera.simplewebcam.MyAppLaction;
import com.camera.simplewebcam.R;
import com.camera.simplewebcam.beans.BaoCunBean;
import com.camera.simplewebcam.beans.BaoCunBeanDao;
import com.camera.simplewebcam.beans.HuZhaoFanHuiBean;
import com.camera.simplewebcam.beans.Photos;
import com.camera.simplewebcam.beans.UserInfoBena;
import com.camera.simplewebcam.dialog.JiaZaiDialog;
import com.camera.simplewebcam.dialog.TiJIaoDialog;
import com.camera.simplewebcam.utils.FileUtil;
import com.camera.simplewebcam.utils.GsonUtil;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sdsmdg.tastytoast.TastyToast;






import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class HuZhaoActivity2 extends Activity implements View.OnClickListener {
    private ImageView huzhaoim,xianchangim;
    private EditText name,fanghao,dianhua;
    private TextView tuifang;
    private Button baocun,paizhao;
    private BaoCunBeanDao baoCunBeanDao=null;
    private BaoCunBean baoCunBean=null;
   // private TextureView videoView;
   // private MediaPlayer mediaPlayer=null;
   // private IVLCVout vlcVout=null;
   // private IVLCVout.Callback callback;
  //  private LibVLC libvlc;
  //  private Media media;
  //  private FaceDet mFaceDet;
  //  private LinearLayout ggg;
  //  private Bitmap bitmap2=null;
    private String benrenPath=null;
   // private boolean isA=false;
    private File mSavePhotoFile;
    private File mSavePhotoFile2;
    private final int REQUEST_TAKE_PHOTO=33;
    private final int REQUEST_TAKE_PHOTO2=44;
    public static final int TIMEOUT = 1000 * 60;
    private JiaZaiDialog jiaZaiDialog=null;
    private UserInfoBena userInfoBena=null;
    private TiJIaoDialog tiJIaoDialog=null;
    private String zhuji=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hu_zhao);
        baoCunBeanDao= MyAppLaction.context.getDaoSession().getBaoCunBeanDao();
        baoCunBean=baoCunBeanDao.load(12345678L);
      //  mFaceDet = MyAppLaction.mFaceDet;

        if (baoCunBean!=null && baoCunBean.getZhujiDiZhi()!=null){
            zhuji=baoCunBean.getZhujiDiZhi();
        }else {
            Toast tastyToast= TastyToast.makeText(HuZhaoActivity2.this,"请先设置主机地址", TastyToast.LENGTH_LONG, TastyToast.ERROR);
            tastyToast.setGravity(Gravity.CENTER,0,0);
            tastyToast.show();
        }

        userInfoBena=new UserInfoBena();


        String fn = "bbbb.jpg";
        FileUtil.isExists(FileUtil.PATH, fn);
        mSavePhotoFile=new File( FileUtil.SDPATH + File.separator + FileUtil.PATH + File.separator + fn);

        String fn2 = "aaaa.jpg";
        FileUtil.isExists(FileUtil.PATH, fn2);
        mSavePhotoFile2=new File( FileUtil.SDPATH + File.separator + FileUtil.PATH + File.separator + fn2);

        ititView();

    }

    private void ititView() {
       // videoView = (TextureView) findViewById(R.id.fff);
       // videoView.setAspectRatio(3, 4);
      //  ggg= (LinearLayout) findViewById(R.id.ggg);
        huzhaoim= (ImageView) findViewById(R.id.zhengjianim);
        huzhaoim.setOnClickListener(this);
        xianchangim= (ImageView) findViewById(R.id.xianchangim);
        xianchangim.setOnClickListener(this);
        name= (EditText) findViewById(R.id.name);
        fanghao= (EditText) findViewById(R.id.fanghao);
        dianhua= (EditText) findViewById(R.id.dianhua);
        tuifang= (TextView) findViewById(R.id.tuifang);
        tuifang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HuZhaoActivity2.this, DatePickActivity.class);
                startActivityForResult(intent,2);
            }
        });
        baocun= (Button) findViewById(R.id.baocun);
        baocun.setOnClickListener(this);
        paizhao= (Button) findViewById(R.id.paizhao);
        paizhao.setOnClickListener(this);
       // libvlc = LibVLCUtil.getLibVLC(HuZhaoActivity2.this);
      //  mediaPlayer = new MediaPlayer(libvlc);
       // vlcVout = mediaPlayer.getVLCVout();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.zhengjianim: //第一张
                Log.d("HuZhaoActivity2", "dddd");
                startCamera(8);

                break;
            case R.id.xianchangim: //第二张

                startCamera(9);

                break;
            case R.id.baocun:
                if (!fanghao.getText().toString().equals("") && !userInfoBena.getScanPhoto().equals("") && !userInfoBena.getCardPhoto().equals("")){
                    link_save();
                }else {

                    Toast tastyToast= TastyToast.makeText(HuZhaoActivity2.this,"你没有填写房号或拍照", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                    tastyToast.setGravity(Gravity.CENTER,0,0);
                    tastyToast.show();
                }

                break;
            case R.id.paizhao:




                break;

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_TAKE_PHOTO:  //拍照
                    //注意，如果拍照的时候设置了MediaStore.EXTRA_OUTPUT，data.getData=null
                 //   huzhaoim.setImageURI(Uri.fromFile());
                    Glide.with(HuZhaoActivity2.this)
                            .load(mSavePhotoFile)
                            .skipMemoryCache(true)
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            //.transform(new GlideCircleTransform(RenGongFuWuActivity.this,1, Color.parseColor("#ffffffff")))
                            .into(huzhaoim);
                    link_P1(mSavePhotoFile);

                    break;
                    case REQUEST_TAKE_PHOTO2:  //拍照
                        //注意，如果拍照的时候设置了MediaStore.EXTRA_OUTPUT，data.getData=null
                        //   huzhaoim.setImageURI(Uri.fromFile());
                        Glide.with(HuZhaoActivity2.this)
                                .load(mSavePhotoFile2)
                                .skipMemoryCache(true)
                                .diskCacheStrategy(DiskCacheStrategy.NONE)
                                //.transform(new GlideCircleTransform(RenGongFuWuActivity.this,1, Color.parseColor("#ffffffff")))
                                .into(xianchangim);
                        link_P2(mSavePhotoFile2);

                        break;
            }
        }
        if (resultCode == Activity.RESULT_OK && requestCode == 2) {
            // 选择预约时间的页面被关闭
            String date = data.getStringExtra("date");
            tuifang.setText(date);
        }
    }

    /**
     * 启动拍照
     * @param
     * @param i
     */
    private void startCamera(int i) {
        if (i==8){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Continue only if the File was successfully created
            if (mSavePhotoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(mSavePhotoFile));//设置文件保存的URI
                takePictureIntent.putExtra("camerasensortype", 1); // 调用前置摄像头
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
        }else {

            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            // Ensure that there's a camera activity to handle the intent
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                // Continue only if the File was successfully created
                if (mSavePhotoFile2 != null) {
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                            Uri.fromFile(mSavePhotoFile2));//设置文件保存的URI
                    takePictureIntent.putExtra("camerasensortype", 1); // 调用前置摄像头
                    startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO2);
                }
            }
        }
    }

    /***
     *保存bitmap对象到文件中
     * @param bm
     * @param path
     * @param quality
     * @return
     */
    public  void saveBitmap2File(Bitmap bm, final String path, int quality) {
        if (null == bm || bm.isRecycled()) {
            Log.d("InFoActivity", "回收|空");
            return ;
        }
        try {
            File file = new File(path);
            if (file.exists()) {
                file.delete();
            }
            BufferedOutputStream bos = new BufferedOutputStream(
                    new FileOutputStream(file));
            bm.compress(Bitmap.CompressFormat.JPEG, quality, bos);
            bos.flush();
            bos.close();
            benrenPath=path;

            Glide.with(HuZhaoActivity2.this)
                    .load(benrenPath)
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    //.transform(new GlideCircleTransform(RenGongFuWuActivity.this,1, Color.parseColor("#ffffffff")))
                    .into(xianchangim);

            link_P2(mSavePhotoFile2);

        } catch (Exception e) {
            e.printStackTrace();

        } finally {

            if (!bm.isRecycled()) {
                bm.recycle();
            }
            bm = null;
        }
    }

    @Override
    protected void onPause() {
      //  Log.d("HuZhaoActivity", "暂停");

        super.onPause();
    }

    @Override
    protected void onDestroy() {


        super.onDestroy();
    }

    private void link_P1(File filename1 ) {
        jiaZaiDialog=new JiaZaiDialog(HuZhaoActivity2.this);
        jiaZaiDialog.setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
        jiaZaiDialog.setText("上传图片中...");
        if (!HuZhaoActivity2.this.isFinishing()){
            jiaZaiDialog.show();
        }

        //final MediaType JSON=MediaType.parse("application/json; charset=utf-8");
        //http://192.168.2.4:8080/sign?cmd=getUnSignList&subjectId=jfgsdf
        OkHttpClient okHttpClient= new OkHttpClient.Builder()
                .writeTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
                .connectTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
                .readTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
                .retryOnConnectionFailure(true)
                .build();

         /* 第一个要上传的file */

        RequestBody fileBody1 = RequestBody.create(MediaType.parse("application/octet-stream") , filename1);
        final String file1Name = System.currentTimeMillis()+"testFile1.jpg";

//    /* 第二个要上传的文件,*/
//        File file2 = new File(fileName2);
//        RequestBody fileBody2 = RequestBody.create(MediaType.parse("application/octet-stream") , file2);
//        String file2Name =System.currentTimeMillis()+"testFile2.jpg";


//    /* form的分割线,自己定义 */
//        String boundary = "xx--------------------------------------------------------------xx";

        MultipartBody mBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
            /* 底下是上传了两个文件 */
                .addFormDataPart("voiceFile" , file1Name , fileBody1)
                  /* 上传一个普通的String参数 */
                //  .addFormDataPart("subject_id" , subject_id+"")
                //  .addFormDataPart("image_2" , file2Name , fileBody2)
                .build();
        Request.Builder requestBuilder = new Request.Builder()
                // .header("Content-Type", "application/json")
                .post(mBody)
                .url(baoCunBean.getZhujiDiZhi() + "/AppFileUploadServlet?FilePathPath=cardFilePath&AllowFileType=.jpg,.gif,.jpeg,.bmp,.png&MaxFileSize=10");

        // step 3：创建 Call 对象
        Call call = okHttpClient.newCall(requestBuilder.build());

        //step 4: 开始异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (jiaZaiDialog!=null && jiaZaiDialog.isShowing()){
                            jiaZaiDialog.dismiss();
                            jiaZaiDialog=null;
                        }
                        Toast tastyToast= TastyToast.makeText(HuZhaoActivity2.this,"上传图片出错，请返回后重试！", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                        tastyToast.setGravity(Gravity.CENTER,0,0);
                        tastyToast.show();

                    }
                });
                // Log.d("AllConnects", "请求识别失败"+e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //  Log.d("AllConnects", "请求识别成功"+call.request().toString());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (jiaZaiDialog!=null && jiaZaiDialog.isShowing()){
                            jiaZaiDialog.dismiss();
                            jiaZaiDialog=null;
                        }
                    }
                });
                //获得返回体
                try {

                    ResponseBody body = response.body();
                    String ss=body.string();

                    //  Log.d("AllConnects", "aa   "+ss);

                    JsonObject jsonObject= GsonUtil.parse(ss).getAsJsonObject();
                    Gson gson=new Gson();
                    Photos zhaoPianBean=gson.fromJson(jsonObject,Photos.class);
                    userInfoBena.setCardPhoto(zhaoPianBean.getExDesc());

                }catch (Exception e){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (jiaZaiDialog!=null && jiaZaiDialog.isShowing()){
                                jiaZaiDialog.dismiss();
                                jiaZaiDialog=null;
                            }
                            Toast tastyToast= TastyToast.makeText(HuZhaoActivity2.this,"上传图片出错，请返回后重试！", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                            tastyToast.setGravity(Gravity.CENTER,0,0);
                            tastyToast.show();
                        }
                    });
                    Log.d("WebsocketPushMsg", e.getMessage());
                }
            }
        });

    }

    private void link_P2(File mSavePhotoFile2) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                jiaZaiDialog=new JiaZaiDialog(HuZhaoActivity2.this);
                jiaZaiDialog.setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
                jiaZaiDialog.setText("上传图片中...");
                if (!HuZhaoActivity2.this.isFinishing())
                jiaZaiDialog.show();
            }
        });

        //final MediaType JSON=MediaType.parse("application/json; charset=utf-8");
        //http://192.168.2.4:8080/sign?cmd=getUnSignList&subjectId=jfgsdf
        OkHttpClient okHttpClient= new OkHttpClient.Builder()
                .writeTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
                .connectTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
                .readTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
                .retryOnConnectionFailure(true)
                .build();

//         /* 第一个要上传的file */
//        File file1 = new File(filename1);
//        RequestBody fileBody1 = RequestBody.create(MediaType.parse("application/octet-stream") , file1);
//        final String file1Name = System.currentTimeMillis()+"testFile1.jpg";

    /* 第二个要上传的文件,*/
     //  File file2 = new File(benrenPath);
        RequestBody fileBody2 = RequestBody.create(MediaType.parse("application/octet-stream") , mSavePhotoFile2);
        String file2Name =System.currentTimeMillis()+"testFile2.jpg";


//    /* form的分割线,自己定义 */
//        String boundary = "xx--------------------------------------------------------------xx";

        MultipartBody mBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
            /* 底下是上传了两个文件 */
                //  .addFormDataPart("image_1" , file1Name , fileBody1)
                  /* 上传一个普通的String参数 */
                //  .addFormDataPart("subject_id" , subject_id+"")
                .addFormDataPart("voiceFile" , file2Name , fileBody2)
                .build();
        Request.Builder requestBuilder = new Request.Builder()
                // .header("Content-Type", "application/json")
                .post(mBody)
                .url(baoCunBean.getZhujiDiZhi() + "/AppFileUploadServlet?FilePathPath=scanFilePath&AllowFileType=.jpg,.gif,.jpeg,.bmp,.png&MaxFileSize=10");


        // step 3：创建 Call 对象
        Call call = okHttpClient.newCall(requestBuilder.build());

        //step 4: 开始异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //  Log.d("AllConnects", "请求识别失败"+e.getMessage());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (jiaZaiDialog!=null && jiaZaiDialog.isShowing()){
                            jiaZaiDialog.dismiss();
                            jiaZaiDialog=null;
                        }
                        Toast tastyToast= TastyToast.makeText(HuZhaoActivity2.this,"上传图片出错，请返回后重试！", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                        tastyToast.setGravity(Gravity.CENTER,0,0);
                        tastyToast.show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //  Log.d("AllConnects", "请求识别成功"+call.request().toString());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (jiaZaiDialog!=null && jiaZaiDialog.isShowing()){
                            jiaZaiDialog.dismiss();
                            jiaZaiDialog=null;
                        }
                    }
                });

                //获得返回体
                try {

                    ResponseBody body = response.body();
                   String ss=body.string();
                    JsonObject jsonObject= GsonUtil.parse(ss).getAsJsonObject();
                    Gson gson=new Gson();
                    Photos zhaoPianBean=gson.fromJson(jsonObject,Photos.class);
                    userInfoBena.setScanPhoto(zhaoPianBean.getExDesc());

                }catch (Exception e){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (jiaZaiDialog!=null && jiaZaiDialog.isShowing()){
                                jiaZaiDialog.dismiss();
                                jiaZaiDialog=null;
                            }
                            Toast tastyToast= TastyToast.makeText(HuZhaoActivity2.this,"上传图片出错，请返回后重试！", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                            tastyToast.setGravity(Gravity.CENTER,0,0);
                            tastyToast.show();
                        }
                    });
                    Log.d("WebsocketPushMsg", e.getMessage());
                }
            }
        });

    }

    private void link_save() {
        //final MediaType JSON=MediaType.parse("application/json; charset=utf-8");
        //http://192.168.2.4:8080/sign?cmd=getUnSignList&subjectId=jfgsdf
        OkHttpClient okHttpClient= new OkHttpClient.Builder()
                .writeTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
                .connectTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
                .readTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
                .retryOnConnectionFailure(true)
                .build();


//    /* form的分割线,自己定义 */
//        String boundary = "xx--------------------------------------------------------------xx";
        RequestBody body = new FormBody.Builder()
                .add("name",name.getText().toString().trim())
                .add("gender","0")
                .add("cardPassport",userInfoBena.getCardPhoto())
                .add("scanPassport",userInfoBena.getScanPhoto())
                .add("phone",dianhua.getText().toString().trim())
                .add("homeNumber",fanghao.getText().toString().trim())
                .add("outTime2",tuifang.getText().toString().trim())
                .add("accountId",baoCunBean.getHuiyiId())
                .build();
        // Log.d("InFoActivity2", userInfoBena.getGender());
        Request.Builder requestBuilder = new Request.Builder()
                // .header("Content-Type", "application/json")
                .post(body)
                .url(zhuji+ "/savePassportResult.do");

        if (!HuZhaoActivity2.this.isFinishing() && tiJIaoDialog==null  ){
            tiJIaoDialog=new TiJIaoDialog(HuZhaoActivity2.this);
            if (!HuZhaoActivity2.this.isFinishing())
            tiJIaoDialog.show();
        }

        // step 3：创建 Call 对象
        Call call = okHttpClient.newCall(requestBuilder.build());

        //step 4: 开始异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("AllConnects", "请求识别失败"+e.getMessage());
                if (tiJIaoDialog!=null){
                    tiJIaoDialog.dismiss();
                    tiJIaoDialog=null;
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast tastyToast = TastyToast.makeText(HuZhaoActivity2.this, "网络出错,请检查网络", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                        tastyToast.setGravity(Gravity.CENTER, 0, 0);
                        tastyToast.show();

                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (tiJIaoDialog!=null){
                    tiJIaoDialog.dismiss();
                    tiJIaoDialog=null;
                }
                // Log.d("AllConnects", "请求识别成功"+call.request().toString());
                //获得返回体
                try {

                    ResponseBody body = response.body();
                    String ss = body.string();
                   // Log.d("HuZhaoActivity", ss);
                    JsonObject jsonObject= GsonUtil.parse(ss.trim()).getAsJsonObject();
                    Gson gson=new Gson();
                    HuZhaoFanHuiBean bean=gson.fromJson(jsonObject,HuZhaoFanHuiBean.class);

                        if (bean.getDtoResult()==0) {

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    Toast tastyToast = TastyToast.makeText(HuZhaoActivity2.this, "保存成功", TastyToast.LENGTH_LONG, TastyToast.INFO);
                                    tastyToast.setGravity(Gravity.CENTER, 0, 0);
                                    tastyToast.show();
                                    finish();
                                }
                            });


                        } else {

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    Toast tastyToast = TastyToast.makeText(HuZhaoActivity2.this, "保存失败", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                                    tastyToast.setGravity(Gravity.CENTER, 0, 0);
                                    tastyToast.show();

                                }
                            });

                        }

                }catch (Exception e){

                    if (tiJIaoDialog!=null){
                        tiJIaoDialog.dismiss();
                        tiJIaoDialog=null;
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            Toast tastyToast = TastyToast.makeText(HuZhaoActivity2.this, "网络出错,请检查网络", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                            tastyToast.setGravity(Gravity.CENTER, 0, 0);
                            tastyToast.show();

                        }
                    });
                    Log.d("WebsocketPushMsg", e.getMessage()+"");
                }
            }
        });
    }
}
