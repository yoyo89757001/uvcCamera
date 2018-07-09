//package com.camera.simplewebcam.ui;
//
//import android.animation.Animator;
//import android.animation.ObjectAnimator;
//import android.app.Activity;
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//import android.content.IntentFilter;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.graphics.ImageFormat;
//
//import android.graphics.PointF;
//
//import android.graphics.YuvImage;
//import android.hardware.Camera;
//import android.media.FaceDetector;
//import android.net.Uri;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Message;
//
//import android.util.Log;
//import android.view.Gravity;
//import android.view.SurfaceHolder;
//import android.view.SurfaceView;
//
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.camera.simplewebcam.MyAppLaction;
//import com.camera.simplewebcam.R;
//import com.camera.simplewebcam.beans.Photos;
//import com.camera.simplewebcam.beans.ShiBieBean;
//import com.camera.simplewebcam.beans.UserInfoBena;
//import com.camera.simplewebcam.dialog.JiaZaiDialog;
//import com.camera.simplewebcam.dialog.TiJIaoDialog;
//import com.camera.simplewebcam.kaer.BeepManager;
//import com.camera.simplewebcam.utils.AppendAPI;
//import com.camera.simplewebcam.utils.FileUtil;
//import com.camera.simplewebcam.utils.GsonUtil;
//
//import com.camera.simplewebcam.utils.Utils;
//import com.camera.simplewebcam.view.HorizontalProgressBarWithNumber;
//import com.google.gson.Gson;
//import com.google.gson.JsonObject;
//
//import com.kaeridcard.tools.Tool;
//import com.lzw.qlhs.Wlt2bmp;
//
//import com.sdsmdg.tastytoast.TastyToast;
//import com.tzutalin.dlib.FaceDet;
//import com.tzutalin.dlib.VisionDetRet;
//
//
//
//import java.io.BufferedOutputStream;
//import java.io.ByteArrayOutputStream;
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.lang.reflect.Type;
//import java.util.Arrays;
//import java.util.List;
//import java.util.concurrent.TimeUnit;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//import okhttp3.Call;
//import okhttp3.Callback;
//import okhttp3.FormBody;
//import okhttp3.MediaType;
//import okhttp3.MultipartBody;
//import okhttp3.OkHttpClient;
//import okhttp3.Request;
//import okhttp3.RequestBody;
//import okhttp3.Response;
//import okhttp3.ResponseBody;
//import slam.ajni.JniCall;
//
//public class InFoActivity3 extends Activity implements SurfaceHolder.Callback {
//    private EditText name,shenfengzheng,xingbie,mingzu,chusheng,dianhua,fazhengjiguan,
//            youxiaoqixian,zhuzhi,fanghao,chepaihao,shibiejieguo,xiangsifdu;
//    private ImageView zhengjianzhao,xianchengzhao;
//    private Button button;
//    private HorizontalProgressBarWithNumber progressBarWithNumber;
//    private BeepManager _beepManager;
//   // public static final String HOST="http://192.168.0.104:8080";
//    private JiaZaiDialog jiaZaiDialog=null;
//    private String xiangsi="";
//    private String biduijieguo="";
//    private TiJIaoDialog tiJIaoDialog=null;
//    private Button quxiao;
//    public static final String HOST="http://192.168.2.101:8081";
//   // public static final String HOST="http://174p2704z3.51mypc.cn:11100";
//  //  public static final String HOST="http://192.168.2.43:8080";
////    private MediaPlayer mediaPlayer=null;
////    private IVLCVout vlcVout=null;
////    private IVLCVout.Callback callback;
////    private LibVLC libvlc;
////    private Media media;
//    private static int cishu=0;
//    private Bitmap bmp2=null;
////    private static boolean isTrue=true;
////    private static boolean isTrue2=true;
//    private static boolean isTrue3=true;
//    private static boolean isTrue4=true;
//    private Bitmap bitmapBig=null;
//    private ImageView imageView;
//    private int numberOfFace = 4;       //最大检测的人脸数
//    private FaceDetector myFaceDetect;  //人脸识别类的实例
//    private FaceDetector.Face[] myFace; //存储多张人脸的数组变量
//    int myEyesDistance;           //两眼之间的距离
//    int numberOfFaceDetected=0;       //实际检测到的人脸数
//    private static final int MESSAGE_QR_SUCCESS = 1;
//    private TextView tishi;
//    private static int count=1;
//    private UserInfoBena userInfoBena=null;
//    private SensorInfoReceiver sensorInfoReceiver;
//    private String filePath=null;
//    private String filePath2=null;
//    private File file1=null;
//    private File file2=null;
//    private  String ip=null;
//    private FaceDet mFaceDet;
//    long c=0;
//    private String shengfenzhengPath=null;
//    private LinearLayout jiemian;
//    private RelativeLayout shipingRL;
//    private float    mRelativeFaceSize   = 0.2f;
//    private int      mAbsoluteFaceSize   = 0;
//    private static int lian=0;
//    private static JniCall myJni;
//    private Handler mhandler = null;
//    private int iDetect = 0;
//    private static boolean isPaiZhao=true;
//    private static boolean isPaiZhao2=true;
//    ToastShow toastShow1 = null;
//    private int iInit;
//    byte[] cardinfo = new byte[256 * 8 + 1024];
//
//    private SurfaceView surfaceView;
//    private Camera mCamera;
//    private SurfaceHolder sh;
//    private int cameraId;
//    private android.view.ViewGroup.LayoutParams lp;
//    private ImageView ceshi_im;
//
//    // 性别数组
//    private String[] strSex = new String[10];
//    // 民簇数组
//    private String[] strCluster = { "汉", "蒙古", "回", "藏", "维吾尔", "苗", "彝", "壮",
//            "布依", "朝鲜", "满", "侗", "瑶", "白", "土家", "哈尼", "哈萨克", "傣", "黎", "傈僳",
//            "佤", "畲", "高山", "拉祜", "水", "东乡", "纳西", "景颇", "柯尔克孜", "土", "达斡尔",
//            "仫佬", "羌", "布朗", "撒拉", "毛南", "仡佬", "锡伯", "阿昌", "普米", "塔吉克", "怒",
//            "乌孜别克", "俄罗斯", "鄂温克", "德昂", "保安", "裕固", "京", "塔塔尔", "独龙", "鄂伦春",
//            "赫哲", "门巴", "珞巴", "基诺"
//
//    };
//
//
//        Handler mHandler2 = new Handler() {
//            @Override
//            public void handleMessage(Message msg) {
//                switch (msg.what) {
//                    case MESSAGE_QR_SUCCESS:
//
//                        Bitmap bitmap= (Bitmap) msg.obj;
//                        imageView.setImageBitmap(bitmap);
//                        Log.d("InFoActivity", "设置图片");
//
//                        break;
//                    case 22:
//
//                        tishi.setVisibility(View.VISIBLE);
//                        tishi.setText("比对失败,开始第"+count+"次比对");
//
//                        break;
//
//                }
//
//            }
//        };
//
//
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.zhujiemian3);
//        mFaceDet=MyAppLaction.mFaceDet;
//
//        surfaceView = (SurfaceView) findViewById(R.id.fff);
//        lp = surfaceView.getLayoutParams();
//        sh = surfaceView.getHolder();
//        sh.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
//        sh.addCallback(this);
//
//        isTrue3=true;
//        isTrue4=true;
//        isPaiZhao=true;
//        isPaiZhao2=true;
//        cishu=0;
//
//        strSex[0] = "未知";
//        strSex[1] = "男";
//        strSex[2] = "女";
//        strSex[9] = "未说明";
//
//        toastShow1 = new ToastShow(InFoActivity3.this);
//
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                myJni = JniCall.getInstance();
//                 iInit = myJni.Mini_Init(0x01);
//
//                // 4.8.4 探测二代证阅读器
//                iDetect = myJni.Mini_idcard_device_detect();
//                if (iDetect == -1) {
//                    toastShow1.toastShow("请确认二代证阅读器是否存在！");
//
//                }
//            }
//        }).start();
//        // 创建、初始化接口类
//
//
//        if (iInit == -1) {
//            toastShow1.toastShow("JniCall初始化失败！");
//
//        } else {
//            mhandler = new Handler() {
//                public void handleMessage(Message msg) {
//                    switch (msg.what) {
//                        case 1:
//                            String strMsg1 = (String) msg.obj;
//                            toastShow1.toastShow(strMsg1);
//
//                            break;
//                        case 14: //姓名
//
//                            String strObject = (String) msg.obj;
//                         //   Log.d("InFoActivity2", strObject);
//
//                            try {
//                                Pattern p = Pattern.compile("\\s+");
//                                Matcher m = p.matcher(strObject);
//                                strObject = m.replaceAll(" ");
//                                // 显示身份证信息
//                                String[] strRfidCardArray = strObject.split(" ");
//                                name.setText(strRfidCardArray[0]); // 姓名
//                                userInfoBena.setPartyName(strRfidCardArray[0]);
//
//                                String strTmp = strRfidCardArray[1];
//                                // 性别
//                                String strTmp3 = strTmp.substring(0, 1);
//                                int i1 = Integer.parseInt(strTmp3);
//                                xingbie.setText(strSex[i1]); // 性别 民簇 出生日期 地址
//                                userInfoBena.setGender(strSex[i1]);
//                                // 民簇
//                                strTmp3 = strTmp.substring(1, 3);
//                                i1 = Integer.parseInt(strTmp3);
//                                mingzu.setText(strCluster[i1 - 1]);
//                                userInfoBena.setNation(strCluster[i1 - 1]);
//                                // 出生日期
//                                String  strTmp4 = strTmp.substring(3, 7) + "-"
//                                        + strTmp.substring(7, 9) + "-"
//                                        + strTmp.substring(9, 11);
//                                chusheng.setText(strTmp4);
//                                userInfoBena.setBornDay(strTmp4);
//                                // 地址
//                                String strTmp5 = strTmp.substring(11, strTmp.length());
//                                zhuzhi.setText(strTmp5);
//                                userInfoBena.setCertAddress(strTmp5);
//
//                                strTmp = strRfidCardArray[2];
//                                String strTmp6 = strTmp.substring(0, 18);
//                                strTmp6 = strTmp6.substring(0, 2) + "**************" + strTmp6.substring(16, 18);
//                                shenfengzheng.setText(strTmp6);
//                                userInfoBena.setCertNumber(strTmp6);
//
//                                String strTmp7 = strTmp.substring(18, strTmp.length());
//                                fazhengjiguan.setText(strTmp7);
//                                userInfoBena.setCertOrg(strTmp7);
//
//                                strTmp = strRfidCardArray[3];
//                                String kaishi=strTmp.substring(0,4)+"-"+strTmp.substring(4,6)+"-"+strTmp.substring(6,8);
//
//                                String jieshu=strTmp.substring(8,12)+"-"+strTmp.substring(12,14)+"-"+strTmp.substring(14,16);
//
//                                youxiaoqixian.setText(kaishi+" 至 "+jieshu);
//                                userInfoBena.setEffDate(kaishi);
//                                userInfoBena.setExpDate(jieshu);
//
//                            } catch (Exception e) {
//                                Log.d("InFoActivity2", ""+e.getMessage());
//                                toastShow1.toastShow("身份证件信息不正确，请联系软件开发人员！");
//
//                            }
//
//                            break;
//                        case 15:
//                            String strMsg4 = (String) msg.obj;
//                            toastShow1.toastShow(strMsg4);
//                            break;
//                        case 16:
//                            String strMsg16 = (String) msg.obj;
//                            toastShow1.toastShow(strMsg16);
//
//                            break;
//                        case 144:
//                            //读取成功
////                            String strMsg144 = (String) msg.obj;
////                            toastShow1.toastShow(strMsg144);
//                            if (jiaZaiDialog!=null && jiaZaiDialog.isShowing()){
//                                jiaZaiDialog.setText("开启摄像头中,请稍后...");
//                            }
//
//
//                            break;
//
//                        case 155:
//
//                            String strMsg155 = (String) msg.obj;
//                            toastShow1.toastShow(strMsg155);
//
//                            break;
//                        case 188:
//
//                            String strMsg188 = (String) msg.obj;
//                            toastShow1.toastShow(strMsg188);
//                            if (jiaZaiDialog!=null && jiaZaiDialog.isShowing()){
//                                jiaZaiDialog.setText("开启摄像失败,请检查网络或ip地址");
//                            }
//
//                            break;
//                    }
//                    super.handleMessage(msg);
//                }
//            };
//        }
//
//
//
//        IntentFilter intentFilter1 = new IntentFilter();
//        intentFilter1.addAction("guanbi");
//        sensorInfoReceiver = new SensorInfoReceiver();
//        registerReceiver(sensorInfoReceiver, intentFilter1);
//
//        imageView= (ImageView) findViewById(R.id.ffff);
//
//        jiemian= (LinearLayout) findViewById(R.id.jiemian);
//        shipingRL= (RelativeLayout) findViewById(R.id.shiping_rl);
//        tishi= (TextView) findViewById(R.id.tishi);
////        libvlc= LibVLCUtil.getLibVLC(InFoActivity3.this);
////        mediaPlayer=new MediaPlayer(libvlc);
////        vlcVout = mediaPlayer.getVLCVout();
//
//
//
//        userInfoBena=new UserInfoBena();
//
//        ip = Utils.getIp(this);
//        String source = Utils.getPassword(this);
//
//        ImageView imageView= (ImageView) findViewById(R.id.dd);
//        imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//
//            }
//        });
//
//        initView();
//
//        _beepManager = new BeepManager(InFoActivity3.this);
//
//        jiaZaiDialog=new JiaZaiDialog(InFoActivity3.this);
//        jiaZaiDialog.setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
//        jiaZaiDialog.show();
//
//        connectDevice();
//        cameraId = 1;// default id
//        OpenCameraAndSetSurfaceviewSize(cameraId);
//
//    }
//
//
//    private Void OpenCameraAndSetSurfaceviewSize(int cameraId) {
//        mCamera = Camera.open(cameraId);
//        Camera.Parameters parameters = mCamera.getParameters();
//        Camera.Size pre_size = parameters.getPreviewSize();
//        Camera.Size pic_size = parameters.getPictureSize();
//        android.hardware.Camera.CameraInfo info = new android.hardware.Camera.CameraInfo();
//        android.hardware.Camera.getCameraInfo(cameraId, info);
//
//        lp.height =pre_size.height*2;
//        lp.width = pre_size.width*2;
//
//        mCamera.setPreviewCallback(new Camera.PreviewCallback() {
//            @Override
//            public void onPreviewFrame(byte[] data, Camera camera) {
//
//                Camera.Size size = camera.getParameters().getPreviewSize();
//                try{
//                    YuvImage image = new YuvImage(data, ImageFormat.NV21, size.width, size.height, null);
//                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
//                    image.compressToJpeg(new android.graphics.Rect(0, 0, size.width, size.height), 80, stream);
//
//                      bmp2 = BitmapFactory.decodeByteArray(stream.toByteArray(), 0, stream.size());
//
//                    if (isTrue4) {
//                        isTrue4=false;
//
//                    List<VisionDetRet>  results = mFaceDet.detect(bmp2);
//
//                    if (results!=null) {
//
//                        int s = results.size();
//                        VisionDetRet face;
//                        if (s > 0) {
//                            if (s > count - 1) {
//
//                                face = results.get(count - 1);
//
//                            } else {
//
//                                face = results.get(0);
//
//                            }
//
//                            int xx = 0;
//                            int yy = 0;
//                            int xx2 = 0;
//                            int yy2 = 0;
//                            int ww = bmp2.getWidth();
//                            int hh = bmp2.getHeight();
//                            if (face.getRight() - 150 >= 0) {
//                                xx = face.getRight() - 150;
//                            } else {
//                                xx = 0;
//                            }
//                            if (face.getTop() - 160 >= 0) {
//                                yy = face.getTop() - 160;
//                            } else {
//                                yy = 0;
//                            }
//                            if (xx + 200 <= ww) {
//                                xx2 = 200;
//                            } else {
//                                xx2 = ww - xx;
//                            }
//                            if (yy + 300 <= hh) {
//                                yy2 = 300;
//                            } else {
//                                yy2 = hh - yy;
//                            }
//
//
//                            Bitmap bitmap = Bitmap.createBitmap(bmp2, xx, yy, xx2, yy2);
//
//                            Message message = Message.obtain();
//                            message.what = MESSAGE_QR_SUCCESS;
//                            message.obj = bitmap;
//                            mHandler2.sendMessage(message);
//
//
//                            String fn = "bbbb.jpg";
//                            FileUtil.isExists(FileUtil.PATH, fn);
//                            saveBitmap2File2(bitmap, FileUtil.SDPATH + File.separator + FileUtil.PATH + File.separator + fn, 100);
//
//                        } else {
//                            isTrue4 = true;
//                        }
//
//
////                                    if (isTrue4) {
////                                        isTrue4=false;
////                                        Log.d("InFoActivity3", "获取图片2222");
////
////                                        Bitmap bmpf = bmp2.copy(Bitmap.Config.RGB_565, true);
//                        //返回识别的人脸数
//                        //	int faceCount = new FaceDetector(bmpf.getWidth(), bmpf.getHeight(), 1).findFaces(bmpf, facess);
//                        //	FaceDetector faceCount2 = new FaceDetector(bmpf.getWidth(), bmpf.getHeight(), 2);
//
////                                        myFace = new FaceDetector.Face[numberOfFace];       //分配人脸数组空间
////                                        myFaceDetect = new FaceDetector(bmpf.getWidth(), bmpf.getHeight(), numberOfFace);
////                                        numberOfFaceDetected = myFaceDetect.findFaces(bmpf, myFace);    //FaceDetector 构造实例并解析人脸
////
////                                        if (numberOfFaceDetected > 0) {
////
////                                            FaceDetector.Face face;
////                                            if (numberOfFaceDetected > count - 1) {
////                                                face = myFace[count - 1];
////
////                                            } else {
////                                                face = myFace[0];
////
////                                            }
////
////                                            PointF pointF = new PointF();
////                                            face.getMidPoint(pointF);
////                                            Log.d("InFoActivity2", "pointF.x:" + pointF.x);
////                                            Log.d("InFoActivity2", "pointF.y:" + pointF.y);
////
////                                            //  myEyesDistance = (int)face.eyesDistance();
////
////                                            int xx = 0;
////                                            int yy = 0;
////                                            int xx2 = 0;
////                                            int yy2 = 0;
////
////                                            if ((int) pointF.x - 200 >= 0) {
////                                                xx = (int) pointF.x - 200;
////                                            } else {
////                                                xx = 0;
////                                            }
////                                            if ((int) pointF.y - 200 >= 0) {
////                                                yy = (int) pointF.y - 200;
////                                            } else {
////                                                yy = 0;
////                                            }
////                                            if (xx + 350 >= bmp2.getWidth()) {
////                                                xx2 = bmp2.getWidth() - xx;
////                                                Log.d("fff", "xxxxxxxxxx:" + xx2);
////                                            } else {
////                                                xx2 = 350;
////                                            }
////                                            if (yy + 350 >= bmp2.getHeight()) {
////                                                yy2 = bmp2.getHeight() - yy;
////                                                Log.d("fff", "yyyyyyyyy:" + yy2);
////                                            } else {
////                                                yy2 = 350;
////                                            }
////                                            Log.d("InFoActivity2", "xx:" + xx);
////                                            Log.d("InFoActivity2", "yy:" + yy);
////                                            Log.d("InFoActivity2", "xx2:" + xx2);
////                                            Log.d("InFoActivity2", "yy2:" + yy2);
////
////                                            Bitmap bitmap = Bitmap.createBitmap(bmp2, xx, yy, xx2, yy2);
////
////                                            //  Bitmap bitmap = Bitmap.createBitmap(bitmapBig,0,0,bitmapBig.getWidth(),bitmapBig.getHeight());
////
////                                            Message message = Message.obtain();
////                                            message.what = MESSAGE_QR_SUCCESS;
////                                            message.obj = bitmap;
////                                            mHandler2.sendMessage(message);
////
////
////                                            String fn = "bbbb.jpg";
////                                            FileUtil.isExists(FileUtil.PATH, fn);
////                                            saveBitmap2File2(bitmap, FileUtil.SDPATH + File.separator + FileUtil.PATH + File.separator + fn, 100);
////
////                                        }else {
////                                            isTrue4=true;
////                                        }
////
////                                        bmpf.recycle();
////                                        bmpf = null;
//
//                    }
//
//                    }
//                    stream.close();
//
//                    }catch(Exception ex){
//                    Log.e("Sys","Error:"+ex.getMessage());
//                }
//            }
//        });
//
//        return null;
//    }
//
//    private Handler handlerGongGao = new Handler();
//    private Runnable runnableGongGao = new Runnable() {
//
//        @Override
//        public void run() {
//            // //【功能】探测身份证卡片是否存在
//
//             int iRfidCardIsFind = myJni.Mini_rfid_card_is_find (600);
//            Log.d("InFoActivity2", "iRfidCardIsFind:" + iRfidCardIsFind);
//            if (iRfidCardIsFind==1){
//                //读卡
//                ReadRfidCardThread readSimCardThread = new ReadRfidCardThread();
//                readSimCardThread.run();
//
//            }else {
//
//                handlerGongGao.postDelayed(runnableGongGao,1000);//4秒后再次执行
//            }
//
//        }
//    };
//
//    private void kill_camera() {
//        Log.d("InFoActivity3", "销毁");
//        isTrue4=false;
//        isTrue3=false;
//        surfaceView.setVisibility(View.GONE);
//
//    }
//
//
//
//    @Override
//    public void surfaceCreated(SurfaceHolder holder) {
//
//        SetAndStartPreview(holder);
//    }
//
//    @Override
//    public void surfaceChanged(SurfaceHolder holder, int format, int width,
//                               int height) {
//
//    }
//
//    @Override
//    public void surfaceDestroyed(SurfaceHolder holder) {
//
//        Log.d("InFoActivity3", "surfaceView销毁");
//        mCamera.setPreviewCallback(null) ;
//        if (mCamera != null) {
//            mCamera.stopPreview();
//            mCamera.release();
//            mCamera = null;
//        }
//
//    }
//
//    private Void SetAndStartPreview(SurfaceHolder holder) {
//        try {
//            mCamera.setPreviewDisplay(holder);
//            mCamera.setDisplayOrientation(0);
//
//        } catch (IOException e) {
//
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    private void  connectDevice() {
//        try {
//
//            handlerGongGao.postDelayed(runnableGongGao,800);//4秒后再次执行
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//
//
//    // 读取身份证信息的线程
//    public class ReadRfidCardThread extends Thread {
//        //public Context ywclActivity = null;
//        //public JniCall myJni = null;
//        final int iReadTimeOut = 3000;
//        private String strMsg3 = "";
//
//        @Override
//        public void run() {
//
//                Arrays.fill(cardinfo, (byte) 32);
//                short[] len = new short[4];
//                int iCardRead = myJni.Mini_idcard_read((short) iReadTimeOut,
//                        cardinfo, len);
//
//                if (iCardRead == -1) {
//                    strMsg3 = "搜寻设备失败！";
//                    //Toast.makeText(ywclActivity, "搜寻设备失败！", Toast.LENGTH_SHORT).show();
//                } else if (iCardRead == -2) {
//                    strMsg3 = "搜寻身份证失败！";
//                    //Toast.makeText(ywclActivity, "搜寻身份证失败！", Toast.LENGTH_SHORT).show();
//                } else if (iCardRead == -3) {
//                    strMsg3 = "读取身份证信息失败！";
//                    //Toast.makeText(ywclActivity, "读取身份证信息失败！", Toast.LENGTH_SHORT).show();
//                } else if (iCardRead == -4) {
//                    strMsg3 = "用户取消身份证识别！";
//                    //Toast.makeText(ywclActivity, "用户取消身份证识别！", Toast.LENGTH_SHORT).show();
//                } else if (iCardRead == 1) {
//
//
//                    // 文字信息长度
//                    int iDataLen3 = cardinfo[10] * 256 + cardinfo[11];
//                    // 照片信息长度
//                    int iDataLen4 = cardinfo[12] * 256 + cardinfo[13];
//                    String strRfidCard = "";
//                    strRfidCard = AppendAPI.Byte2Unicode(cardinfo, 14, iDataLen3);
//
//                    Message message = new Message();
//                    message.what = 14;
//                    message.obj = strRfidCard;
//
//                    mhandler.sendMessage(message);
//
//
//                    try {
//
//                        //照片信息解码
//                        byte[] bPicBitmap = new byte[iDataLen4]; // src, srcPos, dest, destPos, length
//                        System.arraycopy(cardinfo, 14 + iDataLen3, bPicBitmap, 0,
//                                iDataLen4);
//                        final Bitmap bitmap1 = getPicBitmap(bPicBitmap);
//
//                        zhengjianzhao.setImageBitmap(bitmap1);
//
//
//                                String fn="aaaa.jpg";
//                                FileUtil.isExists(FileUtil.PATH,fn);
//                                saveBitmap2File(bitmap1.copy(Bitmap.Config.ARGB_8888, true), FileUtil.SDPATH+ File.separator+FileUtil.PATH+File.separator+fn,100);
//
//
//                    } catch (Exception e) {
//
//                        e.printStackTrace();
//                    }
//                    strMsg3 = "读取身份证信息成功！";
//
//                }
//
//            Message message3 = new Message();
//            message3.what = 144;
//            message3.obj = strMsg3;
//            mhandler.sendMessage(message3);
//
//
//        }
//    }
//
//
//    // 身份证图片解码
//    private Bitmap getPicBitmap(byte[] pic_src) {
//        final int HEAD_LEN = 0x132;
//        final int BMP_LEN = 38556;
//
//        byte[] tmp_data = new byte[40 * 1024];
//        int result = 0;
//        try {
//            System.out.println("before unpack");
//            // Wlt2bmp.kaerunpack();
//            result = Wlt2bmp.picUnpack(pic_src, tmp_data);
//            System.out.println("after unpack");
//
//        } catch (Exception e) {
//
//            e.printStackTrace();
//        }
//
//        if (result != 1)
//            return null;
//
//        byte[] bmp_data = new byte[BMP_LEN];
//        System.arraycopy(tmp_data, 0, bmp_data, 0, BMP_LEN);
//        return Tool.createRgbBitmap(bmp_data, 102, 126);
//
//    }
//
//    public class ToastShow {
//        private Context context;
//        private Toast toast = null;
//        public ToastShow(Context context) {
//            this.context = context;
//
//        }
//        public void toastShow(String text) {
//
//
//                toast = TastyToast.makeText(InFoActivity3.this,text,TastyToast.LENGTH_LONG,TastyToast.INFO);
//                toast.setGravity(Gravity.CENTER,0,0);
//                toast.show();
//
//        }
//    }
//
//
//    public String getMD5(byte[] source) {
//        String s = null;
//        char hexDigits[] = { // 用来将字节转换成 16 进制表示的字符
//                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
//                'e', 'f' };
//        try {
//            java.security.MessageDigest md = java.security.MessageDigest
//                    .getInstance("MD5");
//            md.update(source);
//            byte tmp[] = md.digest(); // MD5 的计算结果是一个 128 位的长整数，
//            // 用字节表示就是 16 个字节
//            char str[] = new char[16 * 2]; // 每个字节用 16 进制表示的话，使用两个字符，
//            // 所以表示成 16 进制需要 32 个字符
//            int k = 0; // 表示转换结果中对应的字符位置
//            for (int i = 0; i < 16; i++) { // 从第一个字节开始，对 MD5 的每一个字节
//                // 转换成 16 进制字符的转换
//                byte byte0 = tmp[i]; // 取第 i 个字节
//                str[k++] = hexDigits[byte0 >>> 4 & 0xf]; // 取字节中高 4 位的数字转换,
//                // >>> 为逻辑右移，将符号位一起右移
//                str[k++] = hexDigits[byte0 & 0xf]; // 取字节中低 4 位的数字转换
//            }
//            s = new String(str); // 换后的结果转换为字符串
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return s;
//    }
//
//    private void initView() {
//        name= (EditText) findViewById(R.id.name);
//        shenfengzheng= (EditText) findViewById(R.id.shenfenzheng);
//        xingbie= (EditText) findViewById(R.id.xingbie);
//        mingzu= (EditText) findViewById(R.id.mingzu);
//        chusheng= (EditText) findViewById(R.id.chusheng);
//        dianhua= (EditText) findViewById(R.id.dianhua);
//        fazhengjiguan= (EditText) findViewById(R.id.jiguan);
//        youxiaoqixian= (EditText) findViewById(R.id.qixian);
//        zhuzhi= (EditText) findViewById(R.id.dizhi);
//        fanghao= (EditText) findViewById(R.id.fanghao);
//        chepaihao= (EditText) findViewById(R.id.chepai);
//        xiangsifdu= (EditText) findViewById(R.id.xiangsidu);
//        shibiejieguo= (EditText) findViewById(R.id.jieguo);
//        zhengjianzhao= (ImageView) findViewById(R.id.zhengjian);
//        xianchengzhao= (ImageView) findViewById(R.id.paizhao);
//        button= (Button) findViewById(R.id.wancheng);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if (!userInfoBena.getCertNumber().equals("")){
//                    try {
//
//                        link_save();
//
//                    }catch (Exception e){
//                        Toast tastyToast= TastyToast.makeText(InFoActivity3.this,"数据异常,请返回后重试",TastyToast.LENGTH_LONG,TastyToast.ERROR);
//                        tastyToast.setGravity(Gravity.CENTER,0,0);
//                        tastyToast.show();
//                        Log.d("InFoActivity", ""+e.getMessage());
//                    }
//
//                }else {
//                    Toast tastyToast= TastyToast.makeText(InFoActivity3.this,"请先读取身份证信息",TastyToast.LENGTH_LONG,TastyToast.ERROR);
//                    tastyToast.setGravity(Gravity.CENTER,0,0);
//                    tastyToast.show();
//                }
//
//            }
//        });
//        quxiao= (Button) findViewById(R.id.quxiao);
//        quxiao.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
//
//        progressBarWithNumber= (HorizontalProgressBarWithNumber) findViewById(R.id.id_progressbar01);
//
//    }
//
//
//    private class SensorInfoReceiver extends BroadcastReceiver {
//
//        @Override
//        public void onReceive(Context context, Intent intent) {
//
//            String action = intent.getAction();
//            if (action.equals("guanbi")) {
//                userInfoBena.setCardPhoto(intent.getStringExtra("cardPath"));
//                userInfoBena.setScanPhoto(intent.getStringExtra("saomiaoPath"));
//
//               if (intent.getBooleanExtra("biduijieguo",true)){
//                   shibiejieguo.setText("比对通过");
//                   biduijieguo="比对通过";
//                   quxiao.setVisibility(View.GONE);
//
//               }else {
//                   shibiejieguo.setText("比对不通过");
//                   biduijieguo="比对不通过";
//                   quxiao.setVisibility(View.VISIBLE);
//
//               }
//               xiangsi=intent.getStringExtra("xiangsidu");
//                xiangsifdu.setText(intent.getStringExtra("xiangsidu")+"");
//
//                Bitmap bitmap= BitmapFactory.decodeFile(FileUtil.SDPATH+ File.separator+FileUtil.PATH+File.separator+"bbbb.jpg");
//                xianchengzhao.setImageBitmap(bitmap);
//
//                kill_camera();
//
//            }
//        }
//    }
//
//    /***
//     *保存bitmap对象到文件中
//     * @param bm
//     * @param path
//     * @param quality
//     * @return
//     */
//    public  void saveBitmap2File(Bitmap bm, final String path, int quality) {
//        if (null == bm || bm.isRecycled()) {
//            Log.d("InFoActivity", "回收|空");
//            return ;
//        }
//        try {
//            File file = new File(path);
//            if (file.exists()) {
//                file.delete();
//            }
//            BufferedOutputStream bos = new BufferedOutputStream(
//                    new FileOutputStream(file));
//            bm.compress(Bitmap.CompressFormat.JPEG, quality, bos);
//            bos.flush();
//            bos.close();
//            shengfenzhengPath=path;
//
//            kaishiPaiZhao();
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//
//        } finally {
//
//            if (!bm.isRecycled()) {
//                bm.recycle();
//            }
//            bm = null;
//        }
//    }
//
//    private void kaishiPaiZhao(){
//
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                if (jiaZaiDialog!=null && jiaZaiDialog.isShowing()){
//                    jiaZaiDialog.dismiss();
//                }
//
//                ObjectAnimator animator2 = ObjectAnimator.ofFloat(jiemian, "scaleY", 1f, 0f);
//                animator2.setDuration(600);//时间1s
//                animator2.start();
//                //起始为1，结束时为0
//                ObjectAnimator animator = ObjectAnimator.ofFloat(jiemian, "scaleX", 1f, 0f);
//                animator.setDuration(600);//时间1s
//                animator.addListener(new Animator.AnimatorListener() {
//                    @Override
//                    public void onAnimationStart(Animator animation) {
//
//                    }
//
//                    @Override
//                    public void onAnimationEnd(Animator animation) {
//                        jiemian.setVisibility(View.GONE);
//                    }
//
//                    @Override
//                    public void onAnimationCancel(Animator animation) {
//
//                    }
//
//                    @Override
//                    public void onAnimationRepeat(Animator animation) {
//
//                    }
//                });
//                animator.start();
//            }
//        });
//
//        mCamera.startPreview();
//
//        Log.d("InFoActivity3", "进2");
//      //  startThread();
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        Log.d("InFoActivity3", "暂停");
//        handlerGongGao.removeCallbacks(runnableGongGao);
//        if (myJni!=null)
//            myJni.Mini_release();
//        count=1;
//
//        isTrue4=false;
//        isTrue3=false;
//        cishu=0;
//        isPaiZhao=false;
//        isPaiZhao2=false;
//
//        _beepManager.close();
//
//        if (jiaZaiDialog!=null && jiaZaiDialog.isShowing()){
//            jiaZaiDialog.dismiss();
//        }
//    }
//
//    @Override
//    protected void onDestroy() {
//        unregisterReceiver(sensorInfoReceiver);
//
//        super.onDestroy();
//
//    }
//
//
//
//    private void link_save() {
//        //final MediaType JSON=MediaType.parse("application/json; charset=utf-8");
//        //http://192.168.2.4:8080/sign?cmd=getUnSignList&subjectId=jfgsdf
//        OkHttpClient okHttpClient= new OkHttpClient.Builder()
//                .writeTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
//                .connectTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
//                .readTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
//                .retryOnConnectionFailure(true)
//                .build();
//
//
////    /* form的分割线,自己定义 */
////        String boundary = "xx--------------------------------------------------------------xx";
//        RequestBody body = new FormBody.Builder()
//                .add("cardNumber",userInfoBena.getCertNumber())
//                .add("name",userInfoBena.getPartyName())
//                .add("gender",userInfoBena.getGender())
//                .add("birthday",userInfoBena.getBornDay())
//                .add("address",userInfoBena.getCertAddress())
//                .add("cardPhoto",userInfoBena.getCardPhoto())
//                .add("scanPhoto",userInfoBena.getScanPhoto())
//                .add("organ",userInfoBena.getCertOrg())
//                .add("termStart",userInfoBena.getEffDate())
//                .add("termEnd",userInfoBena.getExpDate())
//                .add("accountId","1")
//                .add("result",biduijieguo)
//                .add("homeNumber",fanghao.getText().toString().trim())
//                .add("phone",dianhua.getText().toString().trim())
//                .add("carNumber",chepaihao.getText().toString().trim())
//                .add("score",xiangsi)
//				.build();
//
//        Request.Builder requestBuilder = new Request.Builder()
//                // .header("Content-Type", "application/json")
//                .post(body)
//                .url(HOST + "/saveCompareResult.do");
//
//        if (tiJIaoDialog==null){
//            tiJIaoDialog=new TiJIaoDialog(InFoActivity3.this);
//            tiJIaoDialog.show();
//        }
//
//        // step 3：创建 Call 对象
//        Call call = okHttpClient.newCall(requestBuilder.build());
//
//        //step 4: 开始异步请求
//        call.enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                Log.d("AllConnects", "请求识别失败"+e.getMessage());
//                if (tiJIaoDialog!=null){
//                    tiJIaoDialog.dismiss();
//                }
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                if (tiJIaoDialog!=null){
//                    tiJIaoDialog.dismiss();
//                }
//                Log.d("AllConnects", "请求识别成功"+call.request().toString());
//                //获得返回体
//                try {
//
//                    ResponseBody body = response.body();
//                    String ss=body.string();
//                    Log.d("InFoActivity", "ss.contains():" + ss.contains("1"));
//                    if (ss.contains("1")){
//
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//
//                                Toast tastyToast= TastyToast.makeText(InFoActivity3.this,"保存成功",TastyToast.LENGTH_LONG,TastyToast.INFO);
//                                tastyToast.setGravity(Gravity.CENTER,0,0);
//                                tastyToast.show();
//
//                            }
//                        });
//
//                        finish();
//                    }else {
//
//
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//
//                                Toast tastyToast= TastyToast.makeText(InFoActivity3.this,"保存失败",TastyToast.LENGTH_LONG,TastyToast.ERROR);
//                                tastyToast.setGravity(Gravity.CENTER,0,0);
//                                tastyToast.show();
//
//                            }
//                        });
//                        finish();
//
//                    }
//
//                }catch (Exception e){
//
//                    if (tiJIaoDialog!=null){
//                        tiJIaoDialog.dismiss();
//                    }
//                    Log.d("WebsocketPushMsg", e.getMessage());
//                }
//            }
//        });
//
//
//    }
//
//
//
//    private void startThread(){
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        if (jiaZaiDialog!=null && jiaZaiDialog.isShowing()){
//                            jiaZaiDialog.dismiss();
//                        }
//
//                        ObjectAnimator animator2 = ObjectAnimator.ofFloat(jiemian, "scaleY", 1f, 0f);
//                        animator2.setDuration(600);//时间1s
//                        animator2.start();
//                        //起始为1，结束时为0
//                        ObjectAnimator animator = ObjectAnimator.ofFloat(jiemian, "scaleX", 1f, 0f);
//                        animator.setDuration(600);//时间1s
//                        animator.addListener(new Animator.AnimatorListener() {
//                            @Override
//                            public void onAnimationStart(Animator animation) {
//
//                            }
//
//                            @Override
//                            public void onAnimationEnd(Animator animation) {
//                                jiemian.setVisibility(View.GONE);
//                            }
//
//                            @Override
//                            public void onAnimationCancel(Animator animation) {
//
//                            }
//
//                            @Override
//                            public void onAnimationRepeat(Animator animation) {
//
//                            }
//                        });
//                        animator.start();
//                    }
//                });
//
//        Thread thread=  new Thread(new Runnable() {
//            @Override
//            public void run() {
//
//
//                while (isTrue3){
//
//                    if (isTrue4){
//
//                        isTrue4=false;
//                        try {
//                            runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//
//
//                                    if (bitmapBig!=null){
//                                        Log.d("InFoActivity2", "bitmapBig.getHeight():" + bitmapBig.getHeight());
//                                        Log.d("InFoActivity2", "bitmapBig.getWidth():" + bitmapBig.getWidth());
//                                       new Thread(new Runnable() {
//                                           @Override
//                                           public void run() {
//
//                                               Bitmap bmpf = bitmapBig.copy(Bitmap.Config.RGB_565, true);
//                                               //返回识别的人脸数
//                                               //	int faceCount = new FaceDetector(bmpf.getWidth(), bmpf.getHeight(), 1).findFaces(bmpf, facess);
//                                               //	FaceDetector faceCount2 = new FaceDetector(bmpf.getWidth(), bmpf.getHeight(), 2);
//
//                                               myFace = new FaceDetector.Face[numberOfFace];       //分配人脸数组空间
//                                               myFaceDetect = new FaceDetector(bmpf.getWidth(), bmpf.getHeight(), numberOfFace);
//                                               numberOfFaceDetected = myFaceDetect.findFaces(bmpf, myFace);    //FaceDetector 构造实例并解析人脸
//                                               Log.d("InFoActivity2", "numberOfFaceDetected:" + numberOfFaceDetected);
//
//                                               if (numberOfFaceDetected > 0) {
//
//                                                   FaceDetector.Face face;
//                                                   if (numberOfFaceDetected>count-1){
//                                                       face = myFace[count-1];
//
//                                                   }else {
//                                                       face = myFace[0];
//
//                                                   }
//
//                                                   PointF pointF = new PointF();
//                                                   face.getMidPoint(pointF);
//                                                   Log.d("InFoActivity2", "pointF.x:" + pointF.x);
//                                                   Log.d("InFoActivity2", "pointF.y:" + pointF.y);
//
//                                                   myEyesDistance = (int)face.eyesDistance();
//
//                                                   int xx=0;
//                                                   int yy=0;
//                                                   int xx2=0;
//                                                   int yy2=0;
//
//                                                   if ((int)pointF.x-200>=0){
//                                                       xx=(int)pointF.x-200;
//                                                   }else {
//                                                       xx=0;
//                                                   }
//                                                   if ((int)pointF.y-200>=0){
//                                                       yy=(int)pointF.y-200;
//                                                   }else {
//                                                       yy=0;
//                                                   }
//                                                   if (xx+350 >=bitmapBig.getWidth()){
//                                                       xx2=bitmapBig.getWidth()-xx;
//                                                        Log.d("fff", "xxxxxxxxxx:" + xx2);
//                                                   }else {
//                                                       xx2=350;
//                                                   }
//                                                   if (yy+350>=bitmapBig.getHeight()){
//                                                       yy2=bitmapBig.getHeight()-yy;
//                                                        Log.d("fff", "yyyyyyyyy:" + yy2);
//                                                   }else {
//                                                       yy2=350;
//                                                   }
//                                                   Log.d("InFoActivity2", "xx:" + xx);
//                                                   Log.d("InFoActivity2", "yy:" + yy);
//                                                   Log.d("InFoActivity2", "xx2:" + xx2);
//                                                   Log.d("InFoActivity2", "yy2:" + yy2);
//
//                                                   Bitmap bitmap = Bitmap.createBitmap(bitmapBig,xx,yy,xx2,yy2);
//
//                                                 //  Bitmap bitmap = Bitmap.createBitmap(bitmapBig,0,0,bitmapBig.getWidth(),bitmapBig.getHeight());
//
//                                                   Message message=Message.obtain();
//                                                   message.what=MESSAGE_QR_SUCCESS;
//                                                   message.obj=bitmap;
//                                                   mHandler2.sendMessage(message);
//
//
//                                                   String fn="bbbb.jpg";
//                                                   FileUtil.isExists(FileUtil.PATH,fn);
//                                                   saveBitmap2File2(bitmap, FileUtil.SDPATH+ File.separator+FileUtil.PATH+File.separator+fn,100);
//
//
//                                               }else {
//                                                   isTrue4=true;
//                                               }
//
//                                               bmpf.recycle();
//                                               bmpf = null;
//                                           }
//                                       }).start();
//
//                                    }
//                                }
//                            });
//
//                        }catch (IllegalStateException e){
//                            Log.d("InFoActivity2", e.getMessage()+"");
//                        }
//
//                    }
//
//                }
//
//
//            }
//        });
//
//        thread.start();
//
//    }
//
//    public  void saveBitmap2File2(Bitmap bm, final String path, int quality) {
//        try {
//            filePath2=path;
//            if (null == bm) {
//                Log.d("InFoActivity", "回收|空");
//                return ;
//            }
//
//            File file = new File(path);
//            if (file.exists()) {
//                file.delete();
//            }
//            BufferedOutputStream bos = new BufferedOutputStream(
//                    new FileOutputStream(file));
//            bm.compress(Bitmap.CompressFormat.JPEG, quality, bos);
//            bos.flush();
//            bos.close();
//
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    tishi.setVisibility(View.VISIBLE);
//                    tishi.setText("上传图片中。。。");
//                }
//            });
//
//            link_P1(shengfenzhengPath,filePath2);
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//
//        } finally {
//
////			if (!bm.isRecycled()) {
////				bm.recycle();
////			}
//            bm = null;
//        }
//    }
//
//
//    public static final int TIMEOUT = 1000 * 60;
//    private void link_P1(String filename1, final String fileName2) {
//
//
//        //final MediaType JSON=MediaType.parse("application/json; charset=utf-8");
//        //http://192.168.2.4:8080/sign?cmd=getUnSignList&subjectId=jfgsdf
//        OkHttpClient okHttpClient= new OkHttpClient.Builder()
//                .writeTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
//                .connectTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
//                .readTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
//                .retryOnConnectionFailure(true)
//                .build();
//
//         /* 第一个要上传的file */
//        file1 = new File(filename1);
//        RequestBody fileBody1 = RequestBody.create(MediaType.parse("application/octet-stream") , file1);
//        final String file1Name = System.currentTimeMillis()+"testFile1.jpg";
//
////    /* 第二个要上传的文件,*/
////        File file2 = new File(fileName2);
////        RequestBody fileBody2 = RequestBody.create(MediaType.parse("application/octet-stream") , file2);
////        String file2Name =System.currentTimeMillis()+"testFile2.jpg";
//
//
////    /* form的分割线,自己定义 */
////        String boundary = "xx--------------------------------------------------------------xx";
//
//        MultipartBody mBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
//            /* 底下是上传了两个文件 */
//                .addFormDataPart("voiceFile" , file1Name , fileBody1)
//                  /* 上传一个普通的String参数 */
//                //  .addFormDataPart("subject_id" , subject_id+"")
//                //  .addFormDataPart("image_2" , file2Name , fileBody2)
//                .build();
//        Request.Builder requestBuilder = new Request.Builder()
//                // .header("Content-Type", "application/json")
//                .post(mBody)
//                .url(HOST + "/AppFileUploadServlet?FilePathPath=cardFilePath&AllowFileType=.jpg,.gif,.jpeg,.bmp,.png&MaxFileSize=10");
//
//        // step 3：创建 Call 对象
//        Call call = okHttpClient.newCall(requestBuilder.build());
//
//        //step 4: 开始异步请求
//        call.enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        tishi.setText("上传图片出错，请返回后重试！");
//                    }
//                });
//                Log.d("AllConnects", "请求识别失败"+e.getMessage());
//                isTrue4=false;
//                isTrue3=false;
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                Log.d("AllConnects", "请求识别成功"+call.request().toString());
//                //获得返回体
//                try {
//
//                    ResponseBody body = response.body();
//                    String ss=body.string();
//
//                    Log.d("AllConnects", "上传第一张 "+ss);
//
//                    JsonObject jsonObject= GsonUtil.parse(ss).getAsJsonObject();
//                    Gson gson=new Gson();
//                    Photos zhaoPianBean=gson.fromJson(jsonObject,Photos.class);
//                    userInfoBena.setCardPhoto(zhaoPianBean.getExDesc());
//                    link_P2(fileName2);
//
//
//                }catch (Exception e){
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            tishi.setText("上传图片出错，请返回后重试！");
//                        }
//                    });
//                    Log.d("WebsocketPushMsg", e.getMessage());
//                }
//            }
//        });
//
//
//    }
//
//    private void link_P2( String fileName2) {
//        //final MediaType JSON=MediaType.parse("application/json; charset=utf-8");
//        //http://192.168.2.4:8080/sign?cmd=getUnSignList&subjectId=jfgsdf
//        OkHttpClient okHttpClient= new OkHttpClient.Builder()
//                .writeTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
//                .connectTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
//                .readTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
//                .retryOnConnectionFailure(true)
//                .build();
//
////         /* 第一个要上传的file */
////        File file1 = new File(filename1);
////        RequestBody fileBody1 = RequestBody.create(MediaType.parse("application/octet-stream") , file1);
////        final String file1Name = System.currentTimeMillis()+"testFile1.jpg";
//
//    /* 第二个要上传的文件,*/
//        file2 = new File(fileName2);
//        RequestBody fileBody2 = RequestBody.create(MediaType.parse("application/octet-stream") , file2);
//        String file2Name =System.currentTimeMillis()+"testFile2.jpg";
//
//
////    /* form的分割线,自己定义 */
////        String boundary = "xx--------------------------------------------------------------xx";
//
//        MultipartBody mBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
//            /* 底下是上传了两个文件 */
//                //  .addFormDataPart("image_1" , file1Name , fileBody1)
//                  /* 上传一个普通的String参数 */
//                //  .addFormDataPart("subject_id" , subject_id+"")
//                .addFormDataPart("voiceFile" , file2Name , fileBody2)
//                .build();
//        Request.Builder requestBuilder = new Request.Builder()
//                // .header("Content-Type", "application/json")
//                .post(mBody)
//                .url(HOST + "/AppFileUploadServlet?FilePathPath=scanFilePath&AllowFileType=.jpg,.gif,.jpeg,.bmp,.png&MaxFileSize=10");
//
//
//        // step 3：创建 Call 对象
//        Call call = okHttpClient.newCall(requestBuilder.build());
//
//        //step 4: 开始异步请求
//        call.enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                Log.d("AllConnects", "请求识别失败"+e.getMessage());
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        tishi.setText("上传图片出错，请返回后重试！");
//                    }
//                });
//                isTrue4=false;
//                isTrue3=false;
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                Log.d("AllConnects", "请求识别成功"+call.request().toString());
//                //删掉文件
//
//                //获得返回体
//                try {
//
//                    ResponseBody body = response.body();
//                    String ss=body.string();
//                    Log.d("AllConnects", "上传第二张 "+ss);
//
//                    JsonObject jsonObject= GsonUtil.parse(ss).getAsJsonObject();
//                    Gson gson=new Gson();
//                    Photos zhaoPianBean=gson.fromJson(jsonObject,Photos.class);
//                    userInfoBena.setScanPhoto(zhaoPianBean.getExDesc());
//
//                    link_tianqi3();
//
//
//                }catch (Exception e){
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            tishi.setText("上传图片出错，请返回后重试！");
//                        }
//                    });
//                    Log.d("WebsocketPushMsg", e.getMessage());
//                }
//            }
//        });
//
//
//    }
//
//    private void link_tianqi3() {
//        //final MediaType JSON=MediaType.parse("application/json; charset=utf-8");
//        //http://192.168.2.4:8080/sign?cmd=getUnSignList&subjectId=jfgsdf
//        OkHttpClient okHttpClient= new OkHttpClient.Builder()
//                .writeTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
//                .connectTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
//                .readTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
//                .retryOnConnectionFailure(true)
//                .build();
//
//
////    /* form的分割线,自己定义 */
////        String boundary = "xx--------------------------------------------------------------xx";
//        RequestBody body = new FormBody.Builder()
//                .add("cardPhoto",userInfoBena.getCardPhoto())
//                .add("scanPhoto",userInfoBena.getScanPhoto())
//                .build();
//
//        Request.Builder requestBuilder = new Request.Builder()
//                // .header("Content-Type", "application/json")
//                .post(body)
//                .url(HOST + "/compare.do");
//
//
//        // step 3：创建 Call 对象
//        Call call = okHttpClient.newCall(requestBuilder.build());
//
//        //step 4: 开始异步请求
//        call.enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        tishi.setText("上传图片出错，请返回后重试！");
//                    }
//                });
//                Log.d("AllConnects", "请求识别失败"+e.getMessage());
//                isTrue4=false;
//                isTrue3=false;
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                Log.d("AllConnects", "请求识别成功"+call.request().toString());
//                //获得返回体
//                try {
//                    count++;
//
//                    ResponseBody body = response.body();
//                     // Log.d("AllConnects", "识别结果返回"+response.body().string());
//                        String ss=body.string();
//                    Log.d("InFoActivity", "比对结果"+ss);
//                    JsonObject jsonObject= GsonUtil.parse(ss).getAsJsonObject();
//                    Gson gson=new Gson();
//                    final ShiBieBean zhaoPianBean=gson.fromJson(jsonObject,ShiBieBean.class);
//
//                    if (zhaoPianBean.getScore()>=75.0) {
//
//                        //比对成功
//                        sendBroadcast(new Intent("guanbi").putExtra("biduijieguo",true)
//                                .putExtra("xiangsidu",(zhaoPianBean.getScore()+"").substring(0,5))
//                                .putExtra("cardPath",userInfoBena.getCardPhoto()).putExtra("saomiaoPath",userInfoBena.getScanPhoto()));
//                        count=1;
//
//                        qiehuan();
//
//                    }else {
//
//
//                        if (count<=3){
//
//                            Message message=Message.obtain();
//                            message.what=22;
//                            mHandler2.sendMessage(message);
//
//                            isTrue4=true;
//
//
//                        }else {
//
//                            sendBroadcast(new Intent("guanbi").putExtra("biduijieguo",false)
//                                    .putExtra("xiangsidu",(zhaoPianBean.getScore()+"").substring(0,5))
//                                    .putExtra("cardPath",userInfoBena.getCardPhoto()).putExtra("saomiaoPath",userInfoBena.getScanPhoto()));
//                            count=1;
//
//                            qiehuan();
//                        }
//
//                    }
//
//
//                }catch (Exception e){
//
//                    if (count<=3){
//
//                        Message message=Message.obtain();
//                        message.what=22;
//                        mHandler2.sendMessage(message);
//
//                        isTrue4=true;
//
//
//                    }else {
//
//                        sendBroadcast(new Intent("guanbi").putExtra("biduijieguo",false).putExtra("xiangsidu","43.21")
//                                .putExtra("cardPath",userInfoBena.getCardPhoto()).putExtra("saomiaoPath",userInfoBena.getScanPhoto()));
//                        count=1;
//
//                        qiehuan();
//
//                    }
//
//                    Log.d("WebsocketPushMsg", e.getMessage());
//                }
//            }
//        });
//
//
//    }
//
//    private void qiehuan(){
//
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//
//                jiemian.setVisibility(View.VISIBLE);
//                ObjectAnimator animator2 = ObjectAnimator.ofFloat(jiemian, "scaleY", 0f, 1f);
//                animator2.setDuration(600);//时间1s
//                animator2.start();
//                //起始为1，结束时为0
//                ObjectAnimator animator = ObjectAnimator.ofFloat(jiemian, "scaleX", 0f, 1f);
//                animator.setDuration(600);//时间1s
//                animator.start();
//            }
//        });
//
//    }
//}
