package customer.tcrj.com.zsproject.first;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tsy.sdk.myokhttp.MyOkHttp;
import com.tsy.sdk.myokhttp.response.GsonResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;
import customer.tcrj.com.zsproject.MyApp;
import customer.tcrj.com.zsproject.R;
import customer.tcrj.com.zsproject.Utils.ACache;
import customer.tcrj.com.zsproject.Utils.Utils;
import customer.tcrj.com.zsproject.base.BaseActivity;
import customer.tcrj.com.zsproject.bean.cpSCLCinfo;
import customer.tcrj.com.zsproject.bean.loginInfo;
import customer.tcrj.com.zsproject.bean.xslcCxInfo;
import customer.tcrj.com.zsproject.net.ApiConstants;
import customer.tcrj.com.zsproject.widget.DialogDateTimePicker;

public class XSLCwriteActivity extends BaseActivity {

    private static final int DLSCODE = 001;
    private static final int XSQDCODE = 002;

    @BindView(R.id.btnback)
    ImageView btnback;
    @BindView(R.id.txtTitle)
    TextView txtTitle;
    @BindView(R.id.xsqdmc)
    TextView tvxsqdmc;
    @BindView(R.id.dlsmc)
    TextView tvdlsmc;
    @BindView(R.id.tv_xsqd)
    LinearLayout tv_xsqd;
    @BindView(R.id.tv_dls)
    LinearLayout tv_dls;
    @BindView(R.id.btn_tj)
    Button btn_tj;

    @BindView(R.id.ll_time)
    LinearLayout ll_time;
    @BindView(R.id.time)
    TextView time;
    @BindView(R.id.time_pop)
    LinearLayout time_pop;

    private MyOkHttp mMyOkhttp;
    private String productId;
    private String token;

    boolean isadd;

    private String dlsName;
    private String dlsId;
    private String qdName;
    private String qdId;
    private String cpmc;
    private String chsj;

    @Override
    protected int setLayout() {
        return R.layout.new_xslc_fragment;
    }

    @Override
    protected void setView() {
        productId = getIntent().getStringExtra("productId");
        cpmc = getIntent().getStringExtra("cpmc");
        token = ACache.get(this).getAsString("token");
        txtTitle.setText("销售流程");
        mMyOkhttp = MyApp.getInstance().getMyOkHttp();
    }

    @Override
    protected void setData() {

    }




    @OnClick({R.id.btnback,R.id.tv_dls,R.id.tv_xsqd,R.id.btn_tj,R.id.ll_time})
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btnback:
                finish();
                break;

            case R.id.tv_dls:
                toClass(this,DlsActivity.class,null,DLSCODE);//代理商
                break;
            case R.id.tv_xsqd:
                toClass(this,XsqdActivity.class,null,XSQDCODE);//销售渠道
                break;

            case R.id.btn_tj:

                chsj = time.getText().toString();
                if("请选择出货时间".equals(chsj)){
                    T("请选择出货时间");
                    return;
                }


                if(TextUtils.isEmpty(dlsId) || TextUtils.isEmpty(qdId)){
                    T("保存失败");
                    return;
                }
                tjData(chsj);
                break;


            case R.id.ll_time:

                    setTimd();

                break;

            default:
                break;

        }

    }



    private void tjData(String chsj) {

        showLoadingDialog("正在提交信息...");
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("productId", productId);
            jsonObject.put("token", token);
            jsonObject.put("agentId", dlsId);
            jsonObject.put("dlsmc", dlsName);
            jsonObject.put("xsqdId", qdId);
            jsonObject.put("qdmc", qdName);
            jsonObject.put("cpmc", cpmc);
            jsonObject.put("chsj",chsj);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mMyOkhttp.post()
                .url(ApiConstants.addProductApi)
                .jsonParams(jsonObject.toString())
                .enqueue(new GsonResponseHandler<xslcCxInfo>() {
                    @Override
                    public void onFailure(int statusCode, String error_msg) {

                        hideLoadingDialog();
                        Log.e("TAG","error_msg"+error_msg);

                        T(error_msg);
                    }

                    @Override
                    public void onSuccess(int statusCode, xslcCxInfo response) {
                        T(response.getMessage());
                        hideLoadingDialog();
                        if(response.getErrorcode().equals("9999")){


                            tofinish();

                        }else if(response.getErrorcode().equals("204")){
                            Utils.toLogin(XSLCwriteActivity.this);
                        }

                    }
                });
    }

    private void tofinish() {
        // TODO Auto-generated method stub
        Intent intent = new Intent();
        xslcCxInfo.DataBean contentBean = new xslcCxInfo.DataBean();
        contentBean.setChsj(chsj);
        contentBean.setDlsmc(dlsName);
        contentBean.setQdmc(qdName);
        contentBean.setCpmc(cpmc);

        intent.putExtra("three", contentBean);

//            intent.putExtra("three", three); //将计算的值回传回去
        //通过intent对象返回结果，必须要调用一个setResult方法，


        setResult(2, intent);



        finish(); //结束当前的activity的生命周期


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(data != null){
            String dlsmc = data.getStringExtra("dlsmc");
            String dlsid = data.getStringExtra("dlsid");

            if(requestCode== DLSCODE && resultCode == 3 && data != null){
                dlsName = dlsmc;
                dlsId = dlsid;
                tvdlsmc.setText(dlsmc);
            }else if(requestCode== XSQDCODE && resultCode == 3 && data != null){
                qdName = dlsmc;
                qdId = dlsid;
                tvxsqdmc.setText(dlsmc);
            }
        }
    }

    private void setTimd() {

        DialogDateTimePicker start = new DialogDateTimePicker(this);
        start.onDatePickerListener(new DialogDateTimePicker.DatePickerCallBack() {
            @Override
            public void onClickListener(String s) {
                time.setText(s);

            }
        });
        start.show();
    }
}
