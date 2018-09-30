package customer.tcrj.com.zsproject.mine;


import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tsy.sdk.myokhttp.MyOkHttp;
import com.tsy.sdk.myokhttp.response.GsonResponseHandler;


import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import customer.tcrj.com.zsproject.LoginActivity;
import customer.tcrj.com.zsproject.MyApp;
import customer.tcrj.com.zsproject.R;
import customer.tcrj.com.zsproject.Utils.ACache;
import customer.tcrj.com.zsproject.Utils.AppManager;
import customer.tcrj.com.zsproject.base.BaseActivity;
import customer.tcrj.com.zsproject.bean.loginInfo;
import customer.tcrj.com.zsproject.dialog.SweetAlertDialog;
import customer.tcrj.com.zsproject.net.ApiConstants;


public class MdifyPswActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.edt_Account)
    public EditText edt_Account;

    @BindView(R.id.edt_Password)
    public EditText edt_Password;

    @BindView(R.id.edt_Password2)
    public EditText edt_Password2;

    @BindView(R.id.btn_true)
    public Button btn_true;

    @BindView(R.id.txtTitle)
    public TextView txtTitle;

    @BindView(R.id.btnback)
    public ImageView btnback;


    private MyOkHttp mMyOkhttp;

    @Override
    protected int setLayout() {
        return R.layout.activity_mdify_psw;
    }

    @Override
    protected void setView() {
        mMyOkhttp = MyApp.getInstance().getMyOkHttp();
        btn_true.setOnClickListener(this);
        btnback.setOnClickListener(this);
        txtTitle.setText("修改密码");
    }

    @Override
    protected void setData() {
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.btn_true:
                isOk();
                break;
            case R.id.btnback:
                finish();
                break;
        }
    }



    String token;
    public void getDataFromNet(String psw1, String psw2, String psw3) {
        token =  ACache.get(this).getAsString("token");

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("token",token);
            jsonObject.put("oldPassword", psw1);
            jsonObject.put("newPassword", psw2);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mMyOkhttp.post()
                .url(ApiConstants.mdifypswApi)
                .jsonParams(jsonObject.toString())
                .enqueue(new GsonResponseHandler<loginInfo>() {
                    @Override
                    public void onFailure(int statusCode, String error_msg) {
                        showUpdateDialog("密码修改失败",1);
                    }

                    @Override
                    public void onSuccess(int statusCode, loginInfo response) {
                        T(response.getMessage());
                        sureZX();
                    }
                });
    }

    private void sureZX() {
        AppManager.getAppManager().finishAllActivity();
        ACache.get(this).clear();
        goLogin();
    }

    private void goLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    //信息输入是否符合规格
    private void isOk() {
        String psw = ACache.get(this).getAsString("psw");

        String psw1 = edt_Account.getText().toString();
        String psw2 = edt_Password.getText().toString();
        String psw3 = edt_Password2.getText().toString();

        if(psw1.equals("") || psw2.equals("") || psw3.equals("")){
            Toast.makeText(this, "请将数据填写完整", Toast.LENGTH_SHORT).show();
        } else if(!psw2.equals(psw3)){
            Toast.makeText(this, "两次输入的密码不一致", Toast.LENGTH_SHORT).show();
        } else if(psw1.equals(psw3)){
            Toast.makeText(this, "新密码和旧密码不可以相同", Toast.LENGTH_SHORT).show();
        }else if(!psw.equals(psw1)){
            Toast.makeText(this, "原密码输入错误", Toast.LENGTH_SHORT).show();
        }else{
            getDataFromNet(psw1,psw2,psw3);
        }
    }

    private void showUpdateDialog(String s, final int num) {

/*		Intent intent = new Intent();
        intent.setClass(mContext, IsDownLoad.class);
		mContext.startActivity(intent);*/
        final SweetAlertDialog sad = new SweetAlertDialog(this);
        sad.setTitleText(s);
        sad.setContentText("");
        sad.setConfirmText("确定");
        sad.setCanceledOnTouchOutside(true);
        sad.setCancelable(true);

        sad.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {

                if(num == 2){
                    finish();
                }

                sad.dismiss();

            }
        });
        sad.show();
    }
}
