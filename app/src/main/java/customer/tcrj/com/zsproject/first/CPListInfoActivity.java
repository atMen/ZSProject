package customer.tcrj.com.zsproject.first;


import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import org.sufficientlysecure.htmltextview.HtmlHttpImageGetter;
import org.sufficientlysecure.htmltextview.HtmlTextView;

import butterknife.BindView;
import butterknife.OnClick;
import customer.tcrj.com.zsproject.R;
import customer.tcrj.com.zsproject.base.BaseActivity;
import customer.tcrj.com.zsproject.bean.cpInfo;

public class CPListInfoActivity extends BaseActivity  {

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
    @BindView(R.id.html_text)
    HtmlTextView htmlTextView;
    @BindView(R.id.num)
    TextView num;

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
        num.setVisibility(View.VISIBLE);
        num.setText("编辑");
//        webView.getSettings().setJavaScriptEnabled(true);
//        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
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

        htmlTextView.setHtml(cpinfo.getCpms(),
                new HtmlHttpImageGetter(htmlTextView));

//      webView.loadData(cpinfo.getCpms(), "text/html; charset=UTF-8", null);
    }

    @OnClick({R.id.btnback,R.id.num})
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.btnback:

                finish();
                break;

            case R.id.num:
                Bundle bundle = new Bundle();
                bundle.putSerializable("cpinfo",cpinfo);
                toClass(CPListInfoActivity.this,AddCPinfoActivity.class,bundle);//产品信息录入

                break;

            default:
                break;

        }

    }
}
