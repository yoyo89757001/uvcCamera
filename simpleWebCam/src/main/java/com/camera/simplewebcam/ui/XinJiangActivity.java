package com.camera.simplewebcam.ui;



import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.camera.simplewebcam.MyAppLaction;
import com.camera.simplewebcam.R;
import com.camera.simplewebcam.beans.BaoCunBean;
import com.camera.simplewebcam.beans.BaoCunBeanDao;
import com.camera.simplewebcam.beans.UserInfoBena;
import com.camera.simplewebcam.beans.UserInfoBenaDao;
import com.camera.simplewebcam.dialog.JiaZaiDialog;

import com.camera.simplewebcam.dialog.TiJIaoDialog;
import com.camera.simplewebcam.kaer.BeepManager;
import com.camera.simplewebcam.utils.AppendAPI;
import com.camera.simplewebcam.utils.FileUtil;

import com.camera.simplewebcam.view.AutoFitTextureView;
import com.kaeridcard.tools.Tool;
import com.lzw.qlhs.Wlt2bmp;
import com.sdsmdg.tastytoast.TastyToast;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import java.util.Arrays;

import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import slam.ajni.JniCall;

public class XinJiangActivity extends Activity {
    private EditText name,shenfengzheng,xingbie,mingzu,chusheng,dianhua,fazhengjiguan,
            youxiaoqixian,zhuzhi,fanghao,chepaihao,shibiejieguo,xiangsifdu;
    private ImageView zhengjianzhao,xianchengzhao;
    private Button button;
   // private HorizontalProgressBarWithNumber progressBarWithNumber;
    private BeepManager _beepManager;
   // public static final String zhuji="http://192.168.0.104:8080";
    private JiaZaiDialog jiaZaiDialog=null;
    private String xiangsi="";
    private String biduijieguo="";
    private TiJIaoDialog tiJIaoDialog=null;
    private Button quxiao;
    public static final int TIMEOUT = 1000 * 80;

  //  public static final String zhuji="http://192.168.2.101:8081";
   // public static final String zhuji="http://174p2704z3.51mypc.cn:11100";
  //  public static final String zhuji="http://192.168.2.43:8080";
//    private MediaPlayer mediaPlayer=null;
//    private IVLCVout vlcVout=null;
//    private IVLCVout.Callback callback;
//    private LibVLC libvlc;
//    private Media media;
//    private static int cishu=0;
//    private static boolean isTrue=true;
//    private static boolean isTrue2=true;
   // private static boolean isTrue3=true;
   // private static boolean isTrue4=true;
   // private Bitmap bitmapBig=null;
    private ImageView imageView;
 //   private int numberOfFace = 4;       //最大检测的人脸数
  //  private FaceDetector myFaceDetect;  //人脸识别类的实例
  //  private FaceDetector.Face[] myFace; //存储多张人脸的数组变量
   // int myEyesDistance;           //两眼之间的距离
  //  int numberOfFaceDetected=0;       //实际检测到的人脸数
    private static final int MESSAGE_QR_SUCCESS = 1;
   // private static int count=1;
    private UserInfoBenaDao userInfoBenaDao=MyAppLaction.context.getDaoSession().getUserInfoBenaDao();
    private UserInfoBena userInfoBena=null;
    private SensorInfoReceiver sensorInfoReceiver;
//  //  private String filePath=null;
//    private String filePath2=null;
//    private File file1=null;
//    private File file2=null;
  //  private  String ip=null;
    private AutoFitTextureView videoView;
    long c=0;
    private String shengfenzhengPath=null;
   // private LinearLayout jiemian;
  //  private RelativeLayout shipingRL;
//    private float    mRelativeFaceSize   = 0.2f;
//    private int      mAbsoluteFaceSize   = 0;
//    private static int lian=0;
    private static JniCall myJni;
    private Handler mhandler = null;
    private int iDetect = 0;
//    private static boolean isPaiZhao=true;
//    private static boolean isPaiZhao2=true;
    ToastShow toastShow1 = null;
    private int iInit;
    private byte[] cardinfo = new byte[256 * 8 + 1024];
   // private FaceDet mFaceDet;
   // private  String zhuji=null;
   // private  JiuDianBean jiuDianBean=null;
   // private boolean isBaoCun=false;
    private BaoCunBeanDao baoCunBeanDao=MyAppLaction.context.getDaoSession().getBaoCunBeanDao();
    private BaoCunBean baoCunBean=null;
  //  private boolean isReadCard=false;

    // 性别数组
    private String[] strSex = new String[10];
    // 民簇数组
    private String[] strCluster = { "汉", "蒙古", "回", "藏", "维吾尔", "苗", "彝", "壮",
            "布依", "朝鲜", "满", "侗", "瑶", "白", "土家", "哈尼", "哈萨克", "傣", "黎", "傈僳",
            "佤", "畲", "高山", "拉祜", "水", "东乡", "纳西", "景颇", "柯尔克孜", "土", "达斡尔",
            "仫佬", "羌", "布朗", "撒拉", "毛南", "仡佬", "锡伯", "阿昌", "普米", "塔吉克", "怒",
            "乌孜别克", "俄罗斯", "鄂温克", "德昂", "保安", "裕固", "京", "塔塔尔", "独龙", "鄂伦春",
            "赫哲", "门巴", "珞巴", "基诺"

    };


        Handler mHandler2 = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case MESSAGE_QR_SUCCESS:

                        Bitmap bitmap= (Bitmap) msg.obj;
                        imageView.setImageBitmap(bitmap);
                        Log.d("InFoActivity", "设置图片");

                        break;
                    case 22:

                      //  tishi.setVisibility(View.VISIBLE);
                     //   tishi.setText("比对失败,开始第"+count+"次比对,请再次看下摄像头");

                        break;

                }

            }
        };




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zhujiemian2);
        userInfoBenaDao.deleteByKey(123456L);
        userInfoBena=new UserInfoBena();
        userInfoBena.setId(123456L);
        userInfoBenaDao.insert(userInfoBena);
        baoCunBean=baoCunBeanDao.load(12345678L);

        //mFaceDet= MyAppLaction.mFaceDet;
     //   ip=MyAppLaction.sip;
       // jiuDianBean=MyAppLaction.jiuDianBean;

       // isTrue3=true;
       // isTrue4=true;

        //cishu=0;

        strSex[0] = "未知";
        strSex[1] = "男";
        strSex[2] = "女";
        strSex[9] = "未说明";

        toastShow1 = new ToastShow(XinJiangActivity.this);

        new Thread(new Runnable() {
            @Override
            public void run() {
                myJni = JniCall.getInstance();
                 iInit = myJni.Mini_Init(0x01);

                // 4.8.4 探测二代证阅读器
                iDetect = myJni.Mini_idcard_device_detect();
                if (iDetect == -1) {
                    toastShow1.toastShow("请确认二代证阅读器是否存在！");

                }
            }
        }).start();
        // 创建、初始化接口类


        if (iInit == -1) {
            toastShow1.toastShow("JniCall初始化失败！");

        } else {
            mhandler = new Handler() {
                public void handleMessage(Message msg) {
                    switch (msg.what) {
                        case 1:
                            String strMsg1 = (String) msg.obj;
                            toastShow1.toastShow(strMsg1);

                            break;
                        case 14: //姓名

                            String strObject = (String) msg.obj;
                         //   Log.d("InFoActivity2", strObject);

                            try {
                                Pattern p = Pattern.compile("\\s+");
                                Matcher m = p.matcher(strObject);
                                strObject = m.replaceAll(" ");
                                // 显示身份证信息
                                String[] strRfidCardArray = strObject.split(" ");
                                name.setText(strRfidCardArray[0]); // 姓名
                                userInfoBena.setPartyName(strRfidCardArray[0]);

                                String strTmp = strRfidCardArray[1];
                                // 性别
                                String strTmp3 = strTmp.substring(0, 1);
                                int i1 = Integer.parseInt(strTmp3);
                                xingbie.setText(strSex[i1]); // 性别 民簇 出生日期 地址
                                if (strSex[i1].equals("男")){
                                    userInfoBena.setGender("1");
                                }else
                                userInfoBena.setGender("2");
                                // 民簇
                                strTmp3 = strTmp.substring(1, 3);
                                i1 = Integer.parseInt(strTmp3);
                                mingzu.setText(strCluster[i1 - 1]);
                                userInfoBena.setNation(strCluster[i1 - 1]);
                                // 出生日期
                                String  strTmp4 = strTmp.substring(3, 7) + "-"
                                        + strTmp.substring(7, 9) + "-"
                                        + strTmp.substring(9, 11);
                                chusheng.setText(strTmp4);
                                userInfoBena.setBornDay(strTmp4);
                                // 地址
                                String strTmp5 = strTmp.substring(11, strTmp.length());
                                zhuzhi.setText(strTmp5);
                                userInfoBena.setCertAddress(strTmp5);

                                strTmp = strRfidCardArray[2];
                                String strTmp6 = strTmp.substring(0, 18);
                                userInfoBena.setCertNumber(strTmp6);
                                strTmp6 = strTmp6.substring(0, 2) + "**************" + strTmp6.substring(16, 18);
                                shenfengzheng.setText(strTmp6);

                                String strTmp7 = strTmp.substring(18, strTmp.length());
                                fazhengjiguan.setText(strTmp7);
                                userInfoBena.setCertOrg(strTmp7);

                                strTmp = strRfidCardArray[3];
                                String kaishi=strTmp.substring(0,4)+"-"+strTmp.substring(4,6)+"-"+strTmp.substring(6,8);
                                String jieshu="";
                                if (strTmp.length()>=15){
                                     jieshu=strTmp.substring(8,12)+"-"+strTmp.substring(12,14)+"-"+strTmp.substring(14,16);

                                }else {
                                    jieshu="长期";
                                }

                                youxiaoqixian.setText(kaishi+" 至 "+jieshu);
                                userInfoBena.setEffDate(kaishi);
                                userInfoBena.setExpDate(jieshu);
                                userInfoBenaDao.update(userInfoBena);

                            } catch (Exception e) {
                                Log.d("InFoActivity2", ""+e.getMessage());
                                toastShow1.toastShow("身份证件信息不正确，请联系软件开发人员！");

                            }

                            break;
                        case 15:
                            String strMsg4 = (String) msg.obj;
                            toastShow1.toastShow(strMsg4);
                            break;
                        case 16:
                            String strMsg16 = (String) msg.obj;
                            toastShow1.toastShow(strMsg16);

                            break;
                        case 144:
                            //读取成功
//                            String strMsg144 = (String) msg.obj;
//                            toastShow1.toastShow(strMsg144);
//                            if (jiaZaiDialog!=null && jiaZaiDialog.isShowing()){
//                                jiaZaiDialog.setText("开启摄像头中,请稍后...");
//                            }
                            Bitmap bitmap=(Bitmap) msg.obj;
                            zhengjianzhao.setImageBitmap(bitmap);


                            String fn="aaaa.jpg";
                            FileUtil.isExists(FileUtil.PATH,fn);
                            saveBitmapToCamera(bitmap.copy(Bitmap.Config.ARGB_8888, true), FileUtil.SDPATH+ File.separator+FileUtil.PATH+File.separator+fn,100);


                            break;

                        case 155:

                            String strMsg155 = (String) msg.obj;
                            toastShow1.toastShow(strMsg155);

                            break;
                        case 188:
                            Toast tastyToast= TastyToast.makeText(XinJiangActivity.this,"当前网络不稳定,需要重新读卡开启摄像头,请稍候",TastyToast.LENGTH_LONG,TastyToast.INFO);
                            tastyToast.setGravity(Gravity.CENTER,0,0);
                            tastyToast.show();
//                            String strMsg188 = (String) msg.obj;
//                            toastShow1.toastShow(strMsg188);
//                            if (jiaZaiDialog!=null && jiaZaiDialog.isShowing()){
//                                jiaZaiDialog.setText("开启摄像失败,返回后重试");
//                            }

                            break;
                        case 33:


                            break;
                    }
                    super.handleMessage(msg);
                }
            };
        }


        IntentFilter intentFilter1 = new IntentFilter();
        intentFilter1.addAction("guanbi");
        sensorInfoReceiver = new SensorInfoReceiver();
        registerReceiver(sensorInfoReceiver, intentFilter1);

        videoView= (AutoFitTextureView) findViewById(R.id.fff);
        videoView.setAspectRatio(4,3);
        imageView= (ImageView) findViewById(R.id.ffff);

        //jiemian= (LinearLayout) findViewById(R.id.jiemian);
        //shipingRL= (RelativeLayout) findViewById(R.id.shiping_rl);

//        libvlc= LibVLCUtil.getLibVLC(XinJiangActivity.this);
//        mediaPlayer=new MediaPlayer(libvlc);
//        vlcVout = mediaPlayer.getVLCVout();
//
//
//                callback=new IVLCVout.Callback() {
//                    @Override
//                    public void onNewLayout(IVLCVout vlcVout, int width, int height, int visibleWidth, int visibleHeight, int sarNum, int sarDen) {
//
//                    }
//
//                    @Override
//                    public void onSurfacesCreated(IVLCVout vlcVout) {
//
//                        if (ip!=null){
//                            if (mediaPlayer != null) {
//                                final Uri uri=Uri.parse("rtsp://"+ip+"/user=admin&password=&channel=1&stream=0.sdp");
//                                media = new Media(libvlc, uri);
//                                mediaPlayer.setMedia(media);
//                                videoView.setKeepScreenOn(true);
//                                mediaPlayer.play();
//
//                            }
//                        }else {
//
//                            Toast tastyToast = TastyToast.makeText(XinJiangActivity.this, "请先设置摄像头IP地址", TastyToast.LENGTH_LONG, TastyToast.ERROR);
//                            tastyToast.setGravity(Gravity.CENTER, 0, 0);
//                            tastyToast.show();
//
//                        }
//
//                    }
//
//                    @Override
//                    public void onSurfacesDestroyed(IVLCVout vlcVout) {
//
//                    }
//
//                    @Override
//                    public void onHardwareAccelerationError(IVLCVout vlcVout) {
//
//                    }
//                };
//                videoView.setSurfaceTextureListener(new TextureView.SurfaceTextureListener() {
//                    @Override
//                    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
//                        vlcVout.attachViews();
//
//
//                    }
//
//                    @Override
//                    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
//
//                    }
//
//                    @Override
//                    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
//                        Log.d("InFoActivity2", "onSurfaceTextureDestroyed销毁");
//
//                        return true;
//                    }
//
//                    @Override
//                    public void onSurfaceTextureUpdated(SurfaceTexture surface) {
//
//
//                    }
//                });
//
//
//                vlcVout.addCallback(callback);
//                vlcVout.setVideoView(videoView);



        ImageView imageView= (ImageView) findViewById(R.id.dd);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });

        initView();

        _beepManager = new BeepManager(XinJiangActivity.this);

        if (baoCunBean==null || baoCunBean.getHuiyiId()==null || baoCunBean.getHuiyiId().equals("")){
            Toast tastyToast= TastyToast.makeText(XinJiangActivity.this,"请先设置主机地址",TastyToast.LENGTH_LONG,TastyToast.ERROR);
            tastyToast.setGravity(Gravity.CENTER,0,0);
            tastyToast.show();
           // Log.d("XinJiangActivity", "ddddddddddd");
            return;
        }
        jiaZaiDialog=new JiaZaiDialog(XinJiangActivity.this);
        jiaZaiDialog.setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
        jiaZaiDialog.show();
        connectDevice();
    }



    private Handler handlerGongGao = new Handler();
    private Runnable runnableGongGao = new Runnable() {

        @Override
        public void run() {
            // //【功能】探测身份证卡片是否存在
            if (myJni != null) {
            int iRfidCardIsFind = myJni.Mini_rfid_card_is_find(800);

            if (iRfidCardIsFind == 1) {
                //读卡
                ReadRfidCardThread readSimCardThread = new ReadRfidCardThread();
                readSimCardThread.run();

               // Log.d("ReadRfidCardThread", "再读卡1");
            } else {
              //  Log.d("ReadRfidCardThread", "再读卡");
                handlerGongGao.postDelayed(runnableGongGao, 1200);//4秒后再次执行
            }
        }

        }
    };

    private void  connectDevice() {
        try {

            handlerGongGao.postDelayed(runnableGongGao,800);//4秒后再次执行
           // Log.d("ReadRfidCardThread", "再读卡3");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    // 读取身份证信息的线程
    public class ReadRfidCardThread extends Thread {
        //public Context ywclActivity = null;
        //public JniCall myJni = null;
        final int iReadTimeOut = 3000;
        private String strMsg3 = "";

        @Override
        public void run() {
          //  Log.d("ReadRfidCardThread", "读卡");

                Arrays.fill(cardinfo, (byte) 32);
                short[] len = new short[4];
                int iCardRead = myJni.Mini_idcard_read((short) iReadTimeOut,
                        cardinfo, len);

                if (iCardRead == -1) {
                  //  Log.d("ReadRfidCardThread", "搜寻设备失败");
//                    Toast tastyToast= TastyToast.makeText(XinJiangActivity.this,"读取信息失败！因身份证磁性较弱,再次读取中...",TastyToast.LENGTH_LONG,TastyToast.ERROR);
//                    tastyToast.setGravity(Gravity.CENTER,0,0);
//                    tastyToast.show();
                    ReadRfidCardThread readSimCardThread = new ReadRfidCardThread();
                    readSimCardThread.run();
                    return;
                    //Toast.makeText(ywclActivity, "搜寻设备失败！", Toast.LENGTH_SHORT).show();
                } else if (iCardRead == -2) {
                    strMsg3 = "读取信息失败！";
                    Toast.makeText(XinJiangActivity.this, "读取信息失败！", Toast.LENGTH_SHORT).show();
                } else if (iCardRead == -3) {
                    strMsg3 = "读取信息失败！";
                    //Toast.makeText(ywclActivity, "读取身份证信息失败！", Toast.LENGTH_SHORT).show();
                } else if (iCardRead == -4) {
                    strMsg3 = "用户取消身份证识别！";
                    //Toast.makeText(ywclActivity, "用户取消身份证识别！", Toast.LENGTH_SHORT).show();
                } else if (iCardRead == 1) {
                //    Log.d("ReadRfidCardThread", "读卡成功");

                    if (myJni!=null){
                        myJni.Mini_release();
                        myJni=null;
                    }
                    // 文字信息长度
                    int iDataLen3 = cardinfo[10] * 256 + cardinfo[11];
                    // 照片信息长度
                    int iDataLen4 = cardinfo[12] * 256 + cardinfo[13];
                    String strRfidCard = "";
                    strRfidCard = AppendAPI.Byte2Unicode(cardinfo, 14, iDataLen3);

                    Message message = new Message();
                    message.what = 14;
                    message.obj = strRfidCard;
                    mhandler.sendMessage(message);

                    try {

                        //照片信息解码
                        byte[] bPicBitmap = new byte[iDataLen4]; // src, srcPos, dest, destPos, length
                        System.arraycopy(cardinfo, 14 + iDataLen3, bPicBitmap, 0,
                                iDataLen4);
                        final Bitmap bitmap1 = getPicBitmap(bPicBitmap);
                        Message message3 = new Message();
                        message3.what = 144;
                        message3.obj = bitmap1;
                        mhandler.sendMessage(message3);

                    } catch (Exception e) {

                        e.printStackTrace();
                    }
                    strMsg3 = "读取身份证信息成功！";

                }
            Log.d("ReadRfidCardThread", strMsg3);

        }
    }


    // 身份证图片解码
    private Bitmap getPicBitmap(byte[] pic_src) {
        final int HEAD_LEN = 0x132;
        final int BMP_LEN = 38556;

        byte[] tmp_data = new byte[40 * 1024];
        int result = 0;
        try {
            System.out.println("before unpack");
            // Wlt2bmp.kaerunpack();
            result = Wlt2bmp.picUnpack(pic_src, tmp_data);
            System.out.println("after unpack");

        } catch (Exception e) {

            e.printStackTrace();
        }

        if (result != 1)
            return null;

        byte[] bmp_data = new byte[BMP_LEN];
        System.arraycopy(tmp_data, 0, bmp_data, 0, BMP_LEN);
        return Tool.createRgbBitmap(bmp_data, 102, 126);

    }

    public class ToastShow {
        private Context context;
        private Toast toast = null;
        private ToastShow(Context context) {
            this.context = context;

        }
        private void toastShow(String text) {

                toast = TastyToast.makeText(XinJiangActivity.this,text,TastyToast.LENGTH_LONG,TastyToast.INFO);
                toast.setGravity(Gravity.CENTER,0,0);
                toast.show();

        }
    }


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

    private void initView() {
        name= (EditText) findViewById(R.id.name);
        shenfengzheng= (EditText) findViewById(R.id.shenfenzheng);
        xingbie= (EditText) findViewById(R.id.xingbie);
        mingzu= (EditText) findViewById(R.id.mingzu);
        chusheng= (EditText) findViewById(R.id.chusheng);
        dianhua= (EditText) findViewById(R.id.dianhua);
        fazhengjiguan= (EditText) findViewById(R.id.jiguan);
        youxiaoqixian= (EditText) findViewById(R.id.qixian);
        zhuzhi= (EditText) findViewById(R.id.dizhi);
        fanghao= (EditText) findViewById(R.id.fanghao);
        chepaihao= (EditText) findViewById(R.id.chepai);
        xiangsifdu= (EditText) findViewById(R.id.xiangsidu);
        shibiejieguo= (EditText) findViewById(R.id.jieguo);
        zhengjianzhao= (ImageView) findViewById(R.id.zhengjian);
        xianchengzhao= (ImageView) findViewById(R.id.paizhao);
        button= (Button) findViewById(R.id.wancheng);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!userInfoBena.getCertNumber().equals("")){
                    try {
                       // isBaoCun=true;
                        link_save();

                    }catch (Exception e){
                        Toast tastyToast= TastyToast.makeText(XinJiangActivity.this,"数据异常,请返回后重试",TastyToast.LENGTH_LONG,TastyToast.ERROR);
                        tastyToast.setGravity(Gravity.CENTER,0,0);
                        tastyToast.show();
                        Log.d("InFoActivity", ""+e.getMessage());
                    }

                }else {
                    Toast tastyToast= TastyToast.makeText(XinJiangActivity.this,"请先读取身份证信息",TastyToast.LENGTH_LONG,TastyToast.ERROR);
                    tastyToast.setGravity(Gravity.CENTER,0,0);
                    tastyToast.show();
                }

            }
        });
        quxiao= (Button) findViewById(R.id.quxiao);
        quxiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //progressBarWithNumber= (HorizontalProgressBarWithNumber) findViewById(R.id.id_progressbar01);

    }

    private boolean oo=true;
    private class SensorInfoReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            String action = intent.getAction();
            if (oo){
                oo=false;
            if (action.equals("guanbi")) {
                userInfoBena.setCardPhoto(intent.getStringExtra("cardPath"));
                userInfoBena.setScanPhoto(intent.getStringExtra("saomiaoPath"));

                if (intent.getBooleanExtra("biduijieguo", true)) {
                    shibiejieguo.setText("比对通过");
                    biduijieguo = "比对通过";
                    quxiao.setVisibility(View.GONE);

                    Toast tastyToast= TastyToast.makeText(XinJiangActivity.this,"比对通过",TastyToast.LENGTH_LONG,TastyToast.INFO);
                    tastyToast.setGravity(Gravity.CENTER,0,0);
                    tastyToast.show();

                } else {
                    shibiejieguo.setText("比对不通过");
                    biduijieguo = "比对不通过";
                    quxiao.setVisibility(View.VISIBLE);
                    Toast tastyToast= TastyToast.makeText(XinJiangActivity.this,"比对失败",TastyToast.LENGTH_LONG,TastyToast.ERROR);
                    tastyToast.setGravity(Gravity.CENTER,0,0);
                    tastyToast.show();

                }
                xiangsi = intent.getStringExtra("xiangsidu");
                xiangsifdu.setText(intent.getStringExtra("xiangsidu")+"");
              //  Log.d("SensorInfoReceiver", baoCunBean.getZhanghuId() + "/upload/scan/" + userInfoBena.getScanPhoto());
                if (baoCunBean.getZhujiDiZhi()!=null)
                Glide.with(XinJiangActivity.this)
                        .load(baoCunBean.getZhujiDiZhi()+"/upload/scan/"+userInfoBena.getScanPhoto())
                        //.load("http://121.46.3.20"+item.getTouxiang())
                        //.apply(myOptions)
                    //    .transform(new GlideCircleTransform(MyApplication.getAppContext(),2, Color.parseColor("#ffffffff")))
                        //	.bitmapTransform(new GrayscaleTransformation(VlcVideoActivity.this))
                        .into(xianchengzhao);
//                Bitmap bitmap = BitmapFactory.decodeFile(FileUtil.SDPATH + File.separator + FileUtil.PATH + File.separator + "bbbb.jpg");
//                xianchengzhao.setImageBitmap(bitmap);

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
    public  void saveBitmapToCamera(Bitmap bm, final String path, int quality) {
        if (null == bm || bm.isRecycled()) {
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
            shengfenzhengPath=path;

            //跳转到拍照界面
            Intent intent=new Intent(XinJiangActivity.this,DetecterActivity.class);
            intent.putExtra("shengfenzhengPath",shengfenzhengPath);
            startActivityForResult(intent,10);

          //  kaishiPaiZhao();


        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            if (!bm.isRecycled()) {
                bm.recycle();
            }
            bm = null;
        }
    }

//    private void kaishiPaiZhao(){
//        if (mediaPlayer==null){
//            finish();
//            Toast tastyToast= TastyToast.makeText(XinJiangActivity.this,"开启摄像头失败,请重新读卡",TastyToast.LENGTH_LONG,TastyToast.ERROR);
//            tastyToast.setGravity(Gravity.CENTER,0,0);
//            tastyToast.show();
//        }
//        if (mediaPlayer.isPlaying()){
//            startThread();
//
//        }else {
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//
//
//                            try {
//                                Thread.sleep(8000);
//                                if (mediaPlayer.isPlaying()) {
//
//                                    startThread();
//
//                                } else {
//
//                                        Message message3 = new Message();
//                                        message3.what = 188;
//                                        mhandler.sendMessage(message3);
//
//                                        Intent intent=new Intent();
//                                        intent.putExtra("date", "11");
//                                        setResult(Activity.RESULT_OK, intent);
//
//                                        finish();
//
//                                }
//
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//
//                }
//            }).start();
//        }
//    }

    @Override
    protected void onPause() {
        super.onPause();

    //    Log.d("InFoActivity2", "暂停");

    //    count=1;

      //  isTrue4=false;
       // //isTrue3=false;
       // cishu=0;

        if (myJni!=null){
            myJni.Mini_release();
            myJni=null;
        }

//        if (mediaPlayer!=null){
//            mediaPlayer=null;
//            media=null;
//        }
//        if (vlcVout!=null){
//            vlcVout.detachViews();
//            vlcVout.removeCallback(callback);
//            callback=null;
//            vlcVout=null;
//        }
//        if (videoView!=null){
//            videoView.setSurfaceTextureListener(null);
//        }
//        if (libvlc!=null){
//            libvlc.release();
//        }

        if (tiJIaoDialog!=null && tiJIaoDialog.isShowing()){
            tiJIaoDialog.dismiss();
            tiJIaoDialog=null;
        }
        if (jiaZaiDialog!=null && jiaZaiDialog.isShowing()){
            jiaZaiDialog.dismiss();
        }
    }

    @Override
    protected void onDestroy() {

        handlerGongGao.removeCallbacks(runnableGongGao);


        _beepManager.close();


        unregisterReceiver(sensorInfoReceiver);
        super.onDestroy();

//        RefWatcher refWatcher = MyAppLaction.getRefWatcher(InFoActivity2.this);
//        refWatcher.watch(this);
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
                .add("cardNumber",userInfoBena.getCertNumber())
                .add("name",userInfoBena.getPartyName())
                .add("gender",userInfoBena.getGender())
                .add("birthday",userInfoBena.getBornDay())
                .add("address",userInfoBena.getCertAddress())
                .add("cardPhoto",userInfoBena.getCardPhoto())
                .add("scanPhoto",userInfoBena.getScanPhoto())
                .add("organ",userInfoBena.getCertOrg())
                .add("termStart",userInfoBena.getEffDate())
                .add("termEnd",userInfoBena.getExpDate())
                .add("accountId",baoCunBean.getHuiyiId())
                .add("result",biduijieguo)
                .add("homeNumber",fanghao.getText().toString().trim())
                .add("phone",dianhua.getText().toString().trim())
                .add("carNumber",chepaihao.getText().toString().trim())
                .add("score",xiangsi)
				.build();
       // Log.d("InFoActivity2", userInfoBena.getGender());
        Request.Builder requestBuilder = new Request.Builder()
                // .header("Content-Type", "application/json")
                .post(body)
                .url(baoCunBean.getZhujiDiZhi() + "/saveCompareResult.do");

        if (!XinJiangActivity.this.isFinishing() && tiJIaoDialog==null  ){
            tiJIaoDialog=new TiJIaoDialog(XinJiangActivity.this);
            tiJIaoDialog.show();
        }

        // step 3：创建 Call 对象
        Call call = okHttpClient.newCall(requestBuilder.build());

        //step 4: 开始异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
              //  Log.d("AllConnects", "请求识别失败"+e.getMessage());
                if (tiJIaoDialog!=null){
                    tiJIaoDialog.dismiss();
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (tiJIaoDialog!=null){
                    tiJIaoDialog.dismiss();
                }
             //   Log.d("AllConnects", "请求识别成功"+call.request().toString());
                //获得返回体
                try {
                        ResponseBody body = response.body();
                        String ss = body.string().trim();
                           Log.d("InFoActivity", "保存" + ss);
                        if (ss.contains("1")) {

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    Toast tastyToast = TastyToast.makeText(XinJiangActivity.this, "保存成功", TastyToast.LENGTH_LONG, TastyToast.INFO);
                                    tastyToast.setGravity(Gravity.CENTER, 0, 0);
                                    tastyToast.show();

                                }
                            });

                            finish();
                        }
//                        else if (ss.equals("这个是黑名单")) {
//                            runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//
//                                    final QueRenDialog dialog = new QueRenDialog(XinJiangActivity.this, "请注意,这个是黑名单!");
//                                    dialog.setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
//                                    dialog.setOnPositiveListener(new View.OnClickListener() {
//                                        @Override
//                                        public void onClick(View v) {
//                                            dialog.dismiss();
//                                            finish();
//                                        }
//                                    });
//                                    dialog.show();
//                                }
//                            });
//
//                        }
                        else {

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    Toast tastyToast = TastyToast.makeText(XinJiangActivity.this, "保存失败", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                                    tastyToast.setGravity(Gravity.CENTER, 0, 0);
                                    tastyToast.show();

                                }
                            });
                            finish();

                        }

                }catch (Exception e){

                    if (tiJIaoDialog!=null){
                        tiJIaoDialog.dismiss();
                    }
                    Log.d("WebsocketPushMsg", e.getMessage());
                }
            }
        });


    }



//    private void startThread(){
//
//        if (jiaZaiDialog!=null && jiaZaiDialog.isShowing()){
//            jiaZaiDialog.dismiss();
//        }
//        ObjectAnimator animator2 = ObjectAnimator.ofFloat(jiemian, "scaleY", 1f, 0f);
//        animator2.setDuration(600);//时间1s
//        animator2.start();
//        //起始为1，结束时为0
//        ObjectAnimator animator = ObjectAnimator.ofFloat(jiemian, "scaleX", 1f, 0f);
//        animator.setDuration(600);//时间1s
//        animator.addListener(new Animator.AnimatorListener() {
//            @Override
//            public void onAnimationStart(Animator animation) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                jiemian.setVisibility(View.GONE);
//            }
//
//            @Override
//            public void onAnimationCancel(Animator animation) {
//
//            }
//
//            @Override
//            public void onAnimationRepeat(Animator animation) {
//
//            }
//        });
//        animator.start();
//
//
//
//        Thread thread=  new Thread(new Runnable() {
//            @Override
//            public void run() {
//
//                while (isTrue3){
//
//                    if (isTrue4){
//                        isTrue4=false;
//                        try {
//                            runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//
//                                    bitmapBig=videoView.getBitmap();
//
//                                }
//                            });
//                            try {
//                                Thread.sleep(100);
//                            } catch (InterruptedException e) {
//                                e.printStackTrace();
//                            }
//
//                            if (bitmapBig!=null){
//
//                                           List<VisionDetRet> results = mFaceDet.detect(bitmapBig);
//
//
//                                           if (results != null) {
//
//                                                   int s = results.size();
//
//                                                   VisionDetRet face;
//                                                   if (s > 0) {
//
//                                                       if (s > count - 1) {
//
//                                                           face = results.get(count - 1);
//
//                                                       } else {
//
//                                                           face = results.get(0);
//
//                                                       }
//
//                                                       int xx = 0;
//                                                       int yy = 0;
//                                                       int xx2 = 0;
//                                                       int yy2 = 0;
//                                                       int ww = bitmapBig.getWidth();
//                                                       int hh = bitmapBig.getHeight();
//                                                       if (face.getRight() - 160 >= 0) {
//                                                           xx = face.getRight() - 160;
//                                                       } else {
//                                                           xx = 0;
//                                                       }
//                                                       if (face.getTop() - 140 >= 0) {
//                                                           yy = face.getTop() - 140;
//                                                       } else {
//                                                           yy = 0;
//                                                       }
//                                                       if (xx + 200 <= ww) {
//                                                           xx2 = 200;
//                                                       } else {
//                                                           xx2 = ww - xx ;
//                                                       }
//                                                       if (yy + 320 <= hh) {
//                                                           yy2 = 320;
//                                                       } else {
//                                                           yy2 = hh - yy ;
//                                                       }
//
//                                          //     Bitmap bmpf = bitmapBig.copy(Bitmap.Config.RGB_565, true);
////
////                                               //返回识别的人脸数
////                                               //	int faceCount = new FaceDetector(bmpf.getWidth(), bmpf.getHeight(), 1).findFaces(bmpf, facess);
////                                               //	FaceDetector faceCount2 = new FaceDetector(bmpf.getWidth(), bmpf.getHeight(), 2);
////
////                                               myFace = new FaceDetector.Face[numberOfFace];       //分配人脸数组空间
////                                               myFaceDetect = new FaceDetector(bmpf.getWidth(), bmpf.getHeight(), numberOfFace);
////                                               numberOfFaceDetected = myFaceDetect.findFaces(bmpf, myFace);    //FaceDetector 构造实例并解析人脸
////
////                                               if (numberOfFaceDetected > 0) {
////
////                                                   FaceDetector.Face face;
////                                                   if (numberOfFaceDetected>count-1){
////                                                       face = myFace[count-1];
////
////                                                   }else {
////                                                       face = myFace[0];
////
////                                                   }
////
////                                                   PointF pointF = new PointF();
////                                                   face.getMidPoint(pointF);
////
////
////                                                 //  myEyesDistance = (int)face.eyesDistance();
////
////                                                   int xx=0;
////                                                   int yy=0;
////                                                   int xx2=0;
////                                                   int yy2=0;
////
////                                                   if ((int)pointF.x-200>=0){
////                                                       xx=(int)pointF.x-200;
////                                                   }else {
////                                                       xx=0;
////                                                   }
////                                                   if ((int)pointF.y-320>=0){
////                                                       yy=(int)pointF.y-320;
////                                                   }else {
////                                                       yy=0;
////                                                   }
////                                                   if (xx+350 >=bitmapBig.getWidth()){
////                                                       xx2=bitmapBig.getWidth()-xx;
////
////                                                   }else {
////                                                       xx2=350;
////                                                   }
////                                                   if (yy+500>=bitmapBig.getHeight()){
////                                                       yy2=bitmapBig.getHeight()-yy;
////
////                                                   }else {
////                                                       yy2=500;
////                                                   }
//
//                                                       Bitmap bitmap = Bitmap.createBitmap(bitmapBig, xx, yy, xx2, yy2);
//
//                                                       // Bitmap bitmap = Bitmap.createBitmap(bitmapBig,0,0,bitmapBig.getWidth(),bitmapBig.getHeight());
//
//                                                       Message message3 = Message.obtain();
//                                                       message3.what = MESSAGE_QR_SUCCESS;
//                                                       message3.obj = bitmap;
//                                                       mHandler2.sendMessage(message3);
//
//
//                                                       String fn = "bbbb.jpg";
//                                                       FileUtil.isExists(FileUtil.PATH, fn);
//                                                       saveBitmap2File2(bitmap.copy(Bitmap.Config.ARGB_8888,false), FileUtil.SDPATH + File.separator + FileUtil.PATH + File.separator + fn, 100);
//                                                       bitmapBig.recycle();
//                                                       bitmapBig=null;
//
//                                                   } else {
//                                                       isTrue4 = true;
//                                                   }
//
//                                                   //  bmpf.recycle();
//                                                   //  bmpf = null;
//                                               }else {
//                                                   isTrue4 = true;
//                                               }
//
//
//                                       }else {
//                                        isTrue4 = true;
//                                    }
//
//
//                        }catch (Exception e){
//                            Log.d("InFoActivity2", e.getMessage()+"");
//
//                        }
//
//
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

//    public  void saveBitmap2File2(Bitmap bm, final String path, int quality) {
//        try {
//            filePath2=path;
//            if (null == bm) {
//               // Log.d("InFoActivity", "回收|空");
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
//			if (!bm.isRecycled()) {
//				bm.recycle();
//			}
//            bm = null;
//        }
//    }





//    private void qiehuan(){
//
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//
//
////                if (mediaPlayer!=null){
////                    mediaPlayer=null;
////                    media=null;
////                }
////                if (vlcVout!=null){
////                    vlcVout.detachViews();
////                    vlcVout.removeCallback(callback);
////                    callback=null;
////                    vlcVout=null;
////                }
////                if (libvlc!=null){
////                    libvlc.release();
////                }
//                if (videoView!=null){
//                    videoView.setSurfaceTextureListener(null);
//                }
//
//
//                if (myJni!=null){
//                    myJni.Mini_release();
//                    myJni=null;
//                }
//
//            }
//        }).start();
//
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//
//                videoView.setVisibility(View.GONE);
//                jiemian.setVisibility(View.VISIBLE);
//                isTrue4=false;
//                isTrue3=false;
//
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
//
//
//    }

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
//            if (isReadCard && !isBaoCun){
//                link_save();
//
//            }
//            finish();
//        }
//        return super.onKeyDown(keyCode, event);
//    }

}
