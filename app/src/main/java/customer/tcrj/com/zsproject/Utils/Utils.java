package customer.tcrj.com.zsproject.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.List;

import customer.tcrj.com.zsproject.LoginActivity;


/**
 * Created by leict on 2017/10/25.
 */

public class Utils {
    public static int pageSize = 10;




    /**
     * 对于一个没有被载入或者想要动态载入的界面, 都需要使用inflate来载入
     *
     * @param context
     * @return
     */
    public static LayoutInflater getLayoutInflater(Context context) {
        return (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    public static String getData(){
        SimpleDateFormat sDateFormat    =   new    SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String    date    =    sDateFormat.format(new    java.util.Date());

        return  date;
    }
    /**
     * 获取屏幕高度
     *
     * @param cxt
     * @return
     */
    public static int getHeight(Context cxt) {
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) cxt).getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }


    /**
     * 获取屏幕宽度按
     *
     * @param cxt
     * @return
     */
    public static int getWidth(Context cxt) {
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) cxt).getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 判断String是否为空
     *
     * @param data
     * @return
     */
    public static boolean isStringEmpty(String data) {
        return data == null || "".equals(data);
    }

    /**
     * 判断List是否为空
     *
     * @param list
     * @return
     */
    public static boolean isStringEmpty(List<?> list) {
        return list == null || list.size() == 0;
    }

    /**
     * 读取JSON文件
     *
     * @param fileName
     * @param context
     * @return
     */
    public static String getJson(String fileName, Context context) {
        //将json数据变成字符串
        StringBuilder stringBuilder = new StringBuilder();
        try {
            //获取assets资源管理器
            AssetManager assetManager = context.getAssets();
            //通过管理器打开文件并读取
            BufferedReader bf = new BufferedReader(new InputStreamReader(assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    /**
     * 通过反射的方式获取状态栏高度
     *
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        try {
            Class<?> c = Class.forName("com.android.internal.R$dimen");
            Object obj = c.newInstance();
            Field field = c.getField("status_bar_height");
            int x = Integer.parseInt(field.get(obj).toString());
            return context.getResources().getDimensionPixelSize(x);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static void toLogin(Context context){
        Toast.makeText(context, "秘钥失效，请重新登录", Toast.LENGTH_SHORT).show();
        AppManager.getAppManager().finishAllActivity();
        ACache.get(context).clear();
        Intent intent = new Intent(context,LoginActivity.class);
        context.startActivity(intent);
    }

}
