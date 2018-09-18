package customer.tcrj.com.zsproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jaeger.library.StatusBarUtil;
import com.tsy.sdk.myokhttp.MyOkHttp;
import com.tsy.sdk.myokhttp.response.GsonResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;
import customer.tcrj.com.zsproject.Utils.ACache;
import customer.tcrj.com.zsproject.base.BaseActivity;
import customer.tcrj.com.zsproject.bean.loginInfo;
import customer.tcrj.com.zsproject.net.ApiConstants;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.btn_login)
    Button btnlogin;

    @BindView(R.id.et_mobile)
    EditText et_mobile;
    @BindView(R.id.et_psw)
    EditText et_psw;

    private MyOkHttp mMyOkhttp;

    @Override
    protected int setLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void setView() {
        mMyOkhttp = MyApp.getInstance().getMyOkHttp();
    }

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setTranslucentForImageView(this,2, null);
    }

    @Override
    protected void setData() {

    }

    @OnClick({R.id.btn_login})
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_login:

                String username = et_mobile.getText().toString();
                String etpsw = et_psw.getText().toString();

                if(username.equals("") || etpsw.equals("")){

                }else {
                    toLogin(username,etpsw);
                }


                break;

        }
    }

    //联网进行登录
    private void toLogin(final String user, final String psw) {
        showLoadingDialog("正在登录...");

        JSONObject jsonObject = new JSONObject();

        Log.e("TAG","username:"+user+"---psw:"+psw);
        try {
            jsonObject.put("username", user);
            jsonObject.put("password", psw);

//            jsonObject.put("username", "zs");
//            jsonObject.put("password", "123456");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mMyOkhttp.post()
                .url(ApiConstants.loginApi)
                .jsonParams(jsonObject.toString())
                .tag(this)
                .enqueue(new GsonResponseHandler<loginInfo>() {
                    @Override
                    public void onFailure(int statusCode, String error_msg) {

                        hideLoadingDialog();
                        T(error_msg);
                        Log.e("TAG","msg"+statusCode);
                    }

                    @Override
                    public void onSuccess(int statusCode, loginInfo response) {
                        hideLoadingDialog();
                        T(response.getMessage());
                        Log.e("TAG","getErrorcode:"+response.getErrorcode());
                        if("9999".equals(response.getErrorcode())){
                            Log.e("TAG","成功");
                            ACache.get(LoginActivity.this).put("psw",psw);
                            ToCache(response.getData());
                            toClass(LoginActivity.this,MainActivity.class);
                            finish();
                        }
                    }
                });
    }


    //缓存数据
    private void ToCache(String key) {
        Log.e("TAG","token:"+key);
        ACache.get(this).put("token",key);

    }


}
