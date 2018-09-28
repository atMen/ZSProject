package customer.tcrj.com.zsproject.resources;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import customer.tcrj.com.zsproject.Media.VideoPlayerActivity;
import customer.tcrj.com.zsproject.R;
import customer.tcrj.com.zsproject.Utils.ShowImageUtils;
import customer.tcrj.com.zsproject.base.BaseActivity;
import customer.tcrj.com.zsproject.bean.cpInfo;
import customer.tcrj.com.zsproject.bean.resourcesInfo;
import customer.tcrj.com.zsproject.net.ApiConstants;

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

    private resourcesInfo.DataBean info;
    String cllx;
    @Override
    protected int setLayout() {
        return R.layout.activity_resources_info;
    }

    @Override
    protected void setView() {
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

                if("10402".equals(cllx)){


                    T("播放视频");
                }

                break;


            case R.id.btn_true:


                    T("删除");

                break;

            default:
                break;
        }

    }
}
