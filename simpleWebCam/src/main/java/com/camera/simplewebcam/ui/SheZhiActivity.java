package com.camera.simplewebcam.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.anupcowkur.reservoir.Reservoir;
import com.anupcowkur.reservoir.ReservoirGetCallback;
import com.anupcowkur.reservoir.ReservoirPutCallback;
import com.camera.simplewebcam.MyAppLaction;
import com.camera.simplewebcam.R;
import com.camera.simplewebcam.beans.BaoCunBean;
import com.camera.simplewebcam.beans.BaoCunBeanDao;
import com.camera.simplewebcam.beans.JiuDianBean;
import com.camera.simplewebcam.dialog.XiuGaiJiuDianDialog;
import com.camera.simplewebcam.dialog.XiuGaiXinXiDialog;
import com.google.gson.reflect.TypeToken;
import com.sdsmdg.tastytoast.TastyToast;

import java.lang.reflect.Type;

public class SheZhiActivity extends Activity {
    private Button ipDiZHI,gengxin,chaxun,zhuji2,jiudian;
    private TextView title;
    private ImageView famhui;
    private String ip=null;
    private String zhuji=null;
    private JiuDianBean jiuDianBean=null;
    private BaoCunBeanDao baoCunBeanDao= MyAppLaction.context.getDaoSession().getBaoCunBeanDao();
    private BaoCunBean baoCunBean=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baoCunBean=baoCunBeanDao.load(123456L);
        if (baoCunBean==null){
            baoCunBean=new BaoCunBean();
            baoCunBean.setId(123456L);
            baoCunBean.setZhujiDiZhi("http://183.63.123.53:8090");

        }
        setContentView(R.layout.activity_she_zhi);


        zhuji2= (Button) findViewById(R.id.zhuji);
        zhuji2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final XiuGaiXinXiDialog dialog=new XiuGaiXinXiDialog(SheZhiActivity.this);
                dialog.setOnQueRenListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        baoCunBean.setZhujiDiZhi(dialog.getContents());
                        baoCunBeanDao.update(baoCunBean);
                        dialog.dismiss();

                    }

                });
                dialog.setQuXiaoListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                if (baoCunBean.getZhujiDiZhi()!=null){
                    dialog.setContents("设置主机地址",baoCunBean.getZhujiDiZhi());
                }
                dialog.show();
            }
        });

        ipDiZHI= (Button) findViewById(R.id.shezhiip);
        gengxin= (Button) findViewById(R.id.jiancha);
        title= (TextView) findViewById(R.id.title);
        title.setText("系统设置");
        famhui= (ImageView) findViewById(R.id.leftim);
        famhui.setVisibility(View.VISIBLE);
        famhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ipDiZHI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final XiuGaiXinXiDialog dialog=new XiuGaiXinXiDialog(SheZhiActivity.this);
                dialog.setOnQueRenListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                                Reservoir.putAsync("ipipip",dialog.getContents(), new ReservoirPutCallback() {
                                    @Override
                                    public void onSuccess() {
                                        TastyToast.makeText(SheZhiActivity.this,"保存成功",TastyToast.LENGTH_LONG,TastyToast.INFO).show();
                                        dialog.dismiss();
                                    }

                                    @Override
                                    public void onFailure(Exception e) {
                                        TastyToast.makeText(SheZhiActivity.this,"保存失败",TastyToast.LENGTH_LONG,TastyToast.INFO).show();
                                        dialog.dismiss();
                                    }
                                });

                            }


                });
                dialog.setQuXiaoListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                if (ip!=null){
                    dialog.setContents("设置IP摄像头地址",ip);
                }
                dialog.show();
            }
        });
        gengxin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TastyToast.makeText(SheZhiActivity.this,"已经是最新版本",TastyToast.LENGTH_LONG,TastyToast.INFO).show();

            }
        });
        chaxun= (Button) findViewById(R.id.chaxun);
        chaxun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SheZhiActivity.this,ChaXunActivity.class));

            }
        });

        jiudian= (Button) findViewById(R.id.jiudian);
        jiudian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final XiuGaiJiuDianDialog dianDialog=new XiuGaiJiuDianDialog(SheZhiActivity.this);
                dianDialog.setOnQueRenListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        baoCunBean.setHuiyiId(dianDialog.getJiuDianBean().getId());
                        baoCunBean.setGuanggaojiMing(dianDialog.getJiuDianBean().getName());
                        baoCunBeanDao.update(baoCunBean);
                        dianDialog.dismiss();

                    }
                });
                dianDialog.setQuXiaoListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dianDialog.dismiss();
                    }
                });
                if (baoCunBean.getHuiyiId()!=null && baoCunBean.getGuanggaojiMing()!=null){
                    dianDialog.setContents(baoCunBean.getHuiyiId(),baoCunBean.getGuanggaojiMing());
                }
                dianDialog.show();

            }

        });


    }
}
