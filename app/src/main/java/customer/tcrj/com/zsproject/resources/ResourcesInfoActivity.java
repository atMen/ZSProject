package customer.tcrj.com.zsproject.resources;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.tsy.sdk.myokhttp.MyOkHttp;
import com.tsy.sdk.myokhttp.response.GsonResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;
import customer.tcrj.com.zsproject.Media.VideoPlayerActivity;
import customer.tcrj.com.zsproject.MyApp;
import customer.tcrj.com.zsproject.R;
import customer.tcrj.com.zsproject.Utils.ACache;
import customer.tcrj.com.zsproject.Utils.ShowImageUtils;
import customer.tcrj.com.zsproject.base.BaseActivity;
import customer.tcrj.com.zsproject.bean.cpInfo;
import customer.tcrj.com.zsproject.bean.loginInfo;
import customer.tcrj.com.zsproject.bean.resourcesInfo;
import customer.tcrj.com.zsproject.bean.xslcCxInfo;
import customer.tcrj.com.zsproject.dialog.SweetAlertDialog;
import customer.tcrj.com.zsproject.net.ApiConstants;
import customer.tcrj.com.zsproject.videoview.VideoViewActivity;

public class ResourcesInfoActivity extends BaseActivity {
    @BindView(R.id.iv_video_screenshot)
    ImageView iv_video_screenshot;

    @BindView(R.id.iv_btn)
    ImageView iv_btn;

    @BindView(R.id.btn_true)
    Button btn_true;

    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.edt_type)
    TextView edt_type;
    @BindView(R.id.edt_today_plan)
    TextView edt_today_plan;

    @BindView(R.id.btnback)
    ImageView btnback;
    @BindView(R.id.txtTitle)
    TextView txtTitle;
    private MyOkHttp mMyOkhttp;
    private String token;
    private resourcesInfo.DataBean info;
    String cllx;
    @Override
    protected int setLayout() {
        return R.layout.activity_resources_info;
    }

    @Override
    protected void setView() {
        mMyOkhttp = MyApp.getInstance().getMyOkHttp();
        token = ACache.get(this).getAsString("token");
        info = (resourcesInfo.DataBean) getIntent().getSerializableExtra("qyzyinfo");
        txtTitle.setText("企业资源材料信息");
        cllx = info.getCllx();
        if("10402".equals(cllx)){

        }else {
            iv_btn.setVisibility(View.GONE);
            ShowImageUtils.LoadImage(this, ApiConstants.ImageURLROOT+info.getCllj().replace("\\","/"), iv_video_screenshot);
        }
        name.setText(info.getClmc());
        edt_type.setText("10402".equals(info.getCllx())? "视频":"图片");
        edt_today_plan.setText(info.getClms());
    }

    @Override
    protected void setData() {

    }

    @OnClick({R.id.btnback,R.id.iv_video_screenshot,R.id.iv_btn,R.id.btn_true})
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btnback:
                finish();
                break;
            case R.id.iv_btn:
            case R.id.iv_video_screenshot:
//
//                if("10402".equals(cllx)){
//
//                    String videopath = ApiConstants.ImageURLROOT + info.getCllj().replace("\\", "/");
//                    Bundle bundle = new Bundle();
//                    bundle.putString("cover",videopath);
//                    toClass(this,VideoViewActivity.class,bundle);
////                    toClass(this,VideoPlayerActivity.class,bundle);
//                }

                break;

            case R.id.btn_true:
                showUpdateDialog();

                break;

            default:
                break;
        }

    }

    private void showUpdateDialog() {

/*		Intent intent = new Intent();
        intent.setClass(mContext, IsDownLoad.class);
		mContext.startActivity(intent);*/
        final SweetAlertDialog sad = new SweetAlertDialog(this);
        sad.setTitleText("删除此流程");
        sad.setContentText("您确定要进行删除操作吗？");
        sad.setConfirmText("取消");
        sad.setCancelText("确定");
        sad.setCanceledOnTouchOutside(true);
        sad.setCancelable(true);
        sad.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                deleResources();
                sad.dismiss();


            }
        });
        sad.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {

                sad.dismiss();

            }
        });
        sad.show();
    }

    private void deleResources() {

            showLoadingDialog("正在删除数据");

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("token", token);
            jsonObject.put("id", info.getId());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        mMyOkhttp.post()
                .url(ApiConstants.qyzydeleinfoApi)
                .jsonParams(jsonObject.toString())
                .tag(this)
                .enqueue(new GsonResponseHandler<loginInfo>() {
                    @Override
                    public void onFailure(int statusCode, String error_msg) {

                        hideLoadingDialog();
                        Log.e("TAG","msg"+statusCode);
                    }

                    @Override
                    public void onSuccess(int statusCode, loginInfo response) {
                        hideLoadingDialog();
                        Log.e("TAG","getErrorcode:"+response.getErrorcode());
                        if(response.getErrorcode().equals("9999")){
                            Log.e("TAG",response.getMessage());
                            Intent i = new Intent();
                            setResult(RESULT_OK, i);
                            finish();

                        }
                    }
                });

    }
}
