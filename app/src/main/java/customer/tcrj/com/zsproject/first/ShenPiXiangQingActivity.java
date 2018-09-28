package customer.tcrj.com.zsproject.first;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import customer.tcrj.com.zsproject.R;
import customer.tcrj.com.zsproject.base.BaseActivity;
import customer.tcrj.com.zsproject.bean.cpInfo;

public class ShenPiXiangQingActivity extends BaseActivity {

    @BindView(R.id.rl_yes)
    RelativeLayout rl_yes;
    @BindView(R.id.rl_no)
    RelativeLayout rl_no;

    @BindView(R.id.txtTitle)
    TextView txtTitle;
    @BindView(R.id.btnback)
    ImageView btnback;

    private cpInfo.DataBean.ContentBean cpinfo;
    @Override
    protected int setLayout() {
        return R.layout.activity_shen_pi_xiang_qing;
    }

    @Override
    protected void setView() {
        txtTitle.setText("审批结果");
        cpinfo = (cpInfo.DataBean.ContentBean) getIntent().getSerializableExtra("cpinfo");
        String status = cpinfo.getStatus();
        if("3".equals(status)){
            rl_yes.setVisibility(View.VISIBLE);
        }else  if("4".equals(status)){
            rl_no.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void setData() {

    }

    @OnClick({R.id.btnback})
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.btnback:
                finish();
                break;

            default:
                break;

        }
    }
}
