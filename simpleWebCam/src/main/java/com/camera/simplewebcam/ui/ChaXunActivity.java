package com.camera.simplewebcam.ui;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import com.camera.simplewebcam.MyAppLaction;
import com.camera.simplewebcam.R;
import com.camera.simplewebcam.beans.BaoCunBean;
import com.camera.simplewebcam.beans.BaoCunBeanDao;
import com.camera.simplewebcam.beans.JiuDianBean;
import java.io.UnsupportedEncodingException;

import java.net.URLEncoder;

public class ChaXunActivity extends Activity {
    private WebView webView;
    private TextView title;
    private ImageView famhui;
    private JiuDianBean jiuDianBean=null;
    private BaoCunBeanDao baoCunBeanDao=MyAppLaction.context.getDaoSession().getBaoCunBeanDao();
    private BaoCunBean baoCunBean=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baoCunBean=baoCunBeanDao.load(123456L);
        setContentView(R.layout.activity_cha_xun);
        webView= (WebView) findViewById(R.id.webwiew);

        jiuDianBean= MyAppLaction.jiuDianBean;
        title= (TextView) findViewById(R.id.title);
        title.setText("比对记录");
        famhui= (ImageView) findViewById(R.id.leftim);
        famhui.setVisibility(View.VISIBLE);
        famhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        WebSettings webSetting = webView.getSettings();
        webSetting.setAllowFileAccess(true);
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSetting.setSupportZoom(true);
        webSetting.setBuiltInZoomControls(true);
        webSetting.setUseWideViewPort(true);
        webSetting.setSupportMultipleWindows(false);
        // webSetting.setLoadWithOverviewMode(true);
        webSetting.setAppCacheEnabled(true);
        // webSetting.setDatabaseEnabled(true);
        webSetting.setDomStorageEnabled(true);
        webSetting.setJavaScriptEnabled(true);
        webSetting.setGeolocationEnabled(true);
        webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
        webSetting.setAppCachePath(this.getDir("appcache", 0).getPath());
        webSetting.setDatabasePath(this.getDir("databases", 0).getPath());
        webSetting.setGeolocationDatabasePath(this.getDir("geolocation", 0)
                .getPath());
        // webSetting.setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);
        webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);

        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

            }
        });
        if (baoCunBean!=null){
            if (baoCunBean.getZhujiDiZhi()!=null && baoCunBean.getHuiyiId()!=null)
            {
//                String str =baoCunBean.getGuanggaojiMing(); //默认环境，已是UTF-8编码
//                String strGBK = null;
//                try {
//                    strGBK = URLEncoder.encode(str,"UTF-8");
//                } catch (UnsupportedEncodingException e) {
//                    e.printStackTrace();
//                }
             Log.d("ChaXunActivity", baoCunBean.getZhujiDiZhi() + "/police/ipad.html?accountId=" + baoCunBean.getHuiyiId());

                webView.loadUrl(baoCunBean.getZhujiDiZhi()+"/police/ipad.html?accountId="+baoCunBean.getHuiyiId());
            }

        }


        //    http://192.168.2.101:8081/Police/ipad.html?accountName=%E7%B3%BB%E7%BB%9F%E7%AE%A1%E7%90%86%E5%91%98
//            String strUTF8 = URLDecoder.decode(str, "UTF-8");
//            System.out.println(strUTF8);

    }
}
