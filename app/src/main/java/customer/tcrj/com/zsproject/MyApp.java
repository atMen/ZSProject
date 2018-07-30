package customer.tcrj.com.zsproject;


import android.app.Application;
import android.util.Log;


import com.tsy.sdk.myokhttp.MyOkHttp;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * desc: .
 * author: Will .
 * date: 2017/9/2 .
 */
public class MyApp extends Application {

    private static MyApp sMyApp;
    private MyOkHttp mMyOkhttp;

    @Override
    public void onCreate() {
        super.onCreate();
        sMyApp = this;
        initOkhttp();
    }



    //初始化极光
    private void initJPush() {
        Log.e("TAG","jg");
//        JPushInterface.setDebugMode(true);
//        JPushInterface.init(this);
    }

    //初始化okhttp
    private void initOkhttp(){

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(50000L, TimeUnit.MILLISECONDS)
                .readTimeout(50000L, TimeUnit.MILLISECONDS)
                //其他配置
                .build();

        mMyOkhttp = new MyOkHttp(okHttpClient);
    }

    public static synchronized MyApp getInstance() {
        return sMyApp;
    }

    public MyOkHttp getMyOkHttp() {
        return mMyOkhttp;
    }

}
