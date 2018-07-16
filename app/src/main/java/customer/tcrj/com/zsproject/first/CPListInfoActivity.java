package customer.tcrj.com.zsproject.first;


import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import customer.tcrj.com.zsproject.R;
import customer.tcrj.com.zsproject.base.BaseActivity;
import customer.tcrj.com.zsproject.bean.cpInfo;

public class CPListInfoActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.cpname)
    TextView cpname;
    @BindView(R.id.cptime)
    TextView cptime;
    @BindView(R.id.tv01)
    TextView tv01;
    @BindView(R.id.tv02)
    TextView tv02;
    @BindView(R.id.tv03)
    TextView tv03;
    @BindView(R.id.tv04)
    TextView tv04;
    @BindView(R.id.tv05)
    TextView tv05;
    @BindView(R.id.tv06)
    TextView tv06;
    @BindView(R.id.tv07)
    TextView tv07;
    @BindView(R.id.tv08)
    TextView tv08;
    @BindView(R.id.tv09)
    TextView tv09;
    @BindView(R.id.tv10)
    WebView webView;

    @BindView(R.id.txtTitle)
    TextView txtTitle;
    @BindView(R.id.btnback)
    ImageView btnback;

    cpInfo.DataBean.ContentBean cpinfo;

    @Override
    protected int setLayout() {
        return R.layout.activity_cplist_info;
    }

    @Override
    protected void setView() {
        txtTitle.setText("产品详情信息");
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        btnback.setOnClickListener(this);
    }

    @Override
    protected void setData() {
        cpinfo = (cpInfo.DataBean.ContentBean) getIntent().getSerializableExtra("cpinfo");

        cpname.setText("产品名称："+cpinfo.getCpmc());
        cptime.setText(cpinfo.getTimestamp());
        tv01.setText(cpinfo.getCppp());
        tv02.setText(cpinfo.getJdmc());
        tv03.setText(cpinfo.getCpbcgg());
        tv04.setText(cpinfo.getCpbcggdw());
        tv05.setText(cpinfo.getBxq());
        tv06.setText(cpinfo.getCplx());
        tv07.setText(cpinfo.getZsfs());
        tv08.setText(cpinfo.getZsywlb());
        tv09.setText(cpinfo.getEwmsl());

        webView.loadData(cpinfo.getCpms(), "text/html; charset=UTF-8", null);
    }

    @Override
    public void onClick(View v) {
        finish();
    }
}
