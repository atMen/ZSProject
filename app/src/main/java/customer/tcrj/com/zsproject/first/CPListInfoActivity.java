package customer.tcrj.com.zsproject.first;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tsy.sdk.myokhttp.MyOkHttp;
import com.tsy.sdk.myokhttp.response.GsonResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;
import org.sufficientlysecure.htmltextview.HtmlHttpImageGetter;
import org.sufficientlysecure.htmltextview.HtmlTextView;

import butterknife.BindView;
import butterknife.OnClick;
import customer.tcrj.com.zsproject.Media.VideoPlayerActivity;
import customer.tcrj.com.zsproject.MyApp;
import customer.tcrj.com.zsproject.R;
import customer.tcrj.com.zsproject.Utils.ACache;
import customer.tcrj.com.zsproject.Utils.ShowImageUtils;
import customer.tcrj.com.zsproject.Utils.Utils;
import customer.tcrj.com.zsproject.base.BaseActivity;
import customer.tcrj.com.zsproject.bean.cpInfo;
import customer.tcrj.com.zsproject.bean.cpInfoXq;
import customer.tcrj.com.zsproject.bean.dicBean;
import customer.tcrj.com.zsproject.bean.xslcCxInfo;
import customer.tcrj.com.zsproject.net.ApiConstants;
import customer.tcrj.com.zsproject.videoview.CpInfoVideoViewActivity;
import customer.tcrj.com.zsproject.videoview.VideoViewActivity;

public class CPListInfoActivity extends BaseActivity {

    private static final int CPREQUESTCODE = 001;
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
    @BindView(R.id.ypt_icon)
    ImageView ypt_icon;
    @BindView(R.id.xct_icon)
    ImageView xct_icon;

    @BindView(R.id.ll_ypt)
    LinearLayout ll_ypt;
    @BindView(R.id.ll_xct)
    LinearLayout ll_xct;
    @BindView(R.id.ll_xcsp)
    LinearLayout ll_xcsp;


    @BindView(R.id.txtTitle)
    TextView txtTitle;
    @BindView(R.id.btnback)
    ImageView btnback;

    cpInfo.DataBean.ContentBean cpinfo;
    private MyOkHttp mMyOkhttp;
    private String token;

    @Override
    protected int setLayout() {
        return R.layout.activity_cplist_info;
    }

    @Override
    protected void setView() {
        mMyOkhttp = MyApp.getInstance().getMyOkHttp();
        token = ACache.get(this).getAsString("token");
        txtTitle.setText("产品信息详情");


    }

    @Override
    protected void setData() {
        cpinfo = (cpInfo.DataBean.ContentBean) getIntent().getSerializableExtra("cpinfo");
        if("1".equals(cpinfo.getStatus()) || "4".equals(cpinfo.getStatus())){
            num.setVisibility(View.VISIBLE);
            num.setText("编辑");
        }
        cptime.setText(cpinfo.getTimestamp());
        getData(cpinfo.getId());

        getDataById(cpinfo.getCplx());
        getDataById2(cpinfo.getZsfs());
        getDataById3(cpinfo.getZsywlb());

//        if(cpinfo != null){
//            cpname.setText("产品名称："+cpinfo.getCpmc());
//            cptime.setText(cpinfo.getTimestamp());
//            tv01.setText(cpinfo.getCppp());
//            tv02.setText(cpinfo.getJdmc());
//            tv03.setText(cpinfo.getCpbcgg());
//            tv04.setText(cpinfo.getCpbcggdw());
//            tv05.setText(cpinfo.getBxq());
//            tv06.setText(cpinfo.getCplx());
//            tv07.setText(cpinfo.getZsfs());
//            tv08.setText(cpinfo.getZsywlb());
//            tv09.setText(cpinfo.getEwmsl());
//
//            htmlTextView.setHtml(cpinfo.getCpms(),
//                    new HtmlHttpImageGetter(htmlTextView));
//        }
    }



    String videopath =null;
    @OnClick({R.id.btnback,R.id.num,R.id.ll_xcsp})
    public void onClick(View v) {




        switch (v.getId()){

            case R.id.ll_xcsp:

                if(videopath == null){
                    videopath = ApiConstants.YPTURLROOT + cpinfo.getXcsp().replace("\\", "/");
                }

                Bundle videobundle = new Bundle();
                videobundle.putString("cover",videopath);
                toClass(this,CpInfoVideoViewActivity.class,videobundle);
                break;

            case R.id.btnback:

                finish();
                break;

            case R.id.num:
                Bundle bundle = new Bundle();
                bundle.putSerializable("cpinfo",cpinfo);
                bundle.putString("zsfsname",zsfsname);
                bundle.putString("zsywlxname",zsywlxname);
                bundle.putString("cplxname",cplxname);
                bundle.putString("jdname",jdmc);
                toClass(CPListInfoActivity.this,AddCPinfoActivity.class,bundle,CPREQUESTCODE);//产品信息录入

                break;

            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {

                case CPREQUESTCODE:
                    String cpid = data.getStringExtra("cpinfo");
                    if(cpid != null){
                        getData(cpid);
                    }
                    break;

                default:
                    break;

            }
        }
    }

    //获取网络数据
    private void getData(String cpid) {

        showLoadingDialog();
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("productId", cpid);
            jsonObject.put("token", token);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mMyOkhttp.post()
                .url(ApiConstants.cpinfoApi)
                .jsonParams(jsonObject.toString())
                .enqueue(new GsonResponseHandler<cpInfoXq>() {
                    @Override
                    public void onFailure(int statusCode, String error_msg) {
                        Log.e("TAG","error_msg"+error_msg);
                        hideLoadingDialog();
                    }

                    @Override
                    public void onSuccess(int statusCode, cpInfoXq response) {


                        hideLoadingDialog();
                        if(response.getErrorcode().equals("9999")){

                            setCpInfo(response.getData());

                        }else if(response.getErrorcode().equals("204")){

                            Utils.toLogin(CPListInfoActivity.this);
                        }
                    }
                });

    }

    private String zsywlxname;
    private String cplxname;
    private String zsfsname;
    //获取网络数据
    private void getDataById(String cpid) {

        showLoadingDialog();
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("id", cpid);
            jsonObject.put("token", token);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mMyOkhttp.post()
                .url(ApiConstants.getdicbyidinfoApi)
                .jsonParams(jsonObject.toString())
                .enqueue(new GsonResponseHandler<dicBean>() {
                    @Override
                    public void onFailure(int statusCode, String error_msg) {
                        Log.e("TAG","error_msg"+error_msg);
                        hideLoadingDialog();
                    }

                    @Override
                    public void onSuccess(int statusCode, dicBean response) {
                        hideLoadingDialog();
                        if(response.getErrorcode().equals("9999")){

                            dicBean.DataBean data = response.getData();
                            if(data != null){
                                cplxname = response.getData().getName();
                                if(tv06 != null){
                                    tv06.setText(response.getData().getName());
                                }

                            }


                        }else if(response.getErrorcode().equals("204")){

                            Utils.toLogin(CPListInfoActivity.this);
                        }


                    }
                });

    }
    //获取网络数据
    private void getDataById2(String cpid) {

        showLoadingDialog();
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("id", cpid);
            jsonObject.put("token", token);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mMyOkhttp.post()
                .url(ApiConstants.getdicbyidinfoApi)
                .jsonParams(jsonObject.toString())
                .enqueue(new GsonResponseHandler<dicBean>() {
                    @Override
                    public void onFailure(int statusCode, String error_msg) {
                        Log.e("TAG","error_msg"+error_msg);
                        hideLoadingDialog();
                    }

                    @Override
                    public void onSuccess(int statusCode, dicBean response) {
                        Log.e("TAG","error_msg"+response.getErrorcode());
                        hideLoadingDialog();
                        if(response.getErrorcode().equals("9999")){

                            dicBean.DataBean data = response.getData();
                            if(data != null){
                                zsfsname = response.getData().getName();
                                if(tv07 != null){
                                    tv07.setText(response.getData().getName());
                                }

                            }


                        }else if(response.getErrorcode().equals("204")){

                            Utils.toLogin(CPListInfoActivity.this);
                        }


                    }
                });

    }
    //获取网络数据
    private void getDataById3(String cpid) {

        showLoadingDialog();
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("id", cpid);
            jsonObject.put("token", token);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mMyOkhttp.post()
                .url(ApiConstants.getdicbyidinfoApi)
                .jsonParams(jsonObject.toString())
                .enqueue(new GsonResponseHandler<dicBean>() {
                    @Override
                    public void onFailure(int statusCode, String error_msg) {
                        Log.e("TAG","error_msg"+error_msg);
                        hideLoadingDialog();
                    }

                    @Override
                    public void onSuccess(int statusCode, dicBean response) {
                        Log.e("TAG","error_msg"+response.getErrorcode());
                        hideLoadingDialog();
                        if(response.getErrorcode().equals("9999")){

                            dicBean.DataBean data = response.getData();
                            if(data != null){
                                zsywlxname = response.getData().getName();
                                if(tv08 != null){
                                    tv08.setText(response.getData().getName());
                                }
                            }
                        }else if(response.getErrorcode().equals("204")){

                            Utils.toLogin(CPListInfoActivity.this);
                        }


                    }
                });

    }
    String jdmc;
    private void setCpInfo(cpInfoXq.DataBean cpinfo) {



        if(cpinfo != null){
            cpname.setText("产品名称："+cpinfo.getCpmc());
            jdmc = cpinfo.getJdmc();
            tv01.setText(cpinfo.getCppp());
            tv02.setText(cpinfo.getJdmc());
            tv03.setText(cpinfo.getCpbcgg());
            tv04.setText(cpinfo.getCpbcggdw());
            tv05.setText(cpinfo.getBxq());
            tv09.setText(cpinfo.getEwmsl());

            String ypt = cpinfo.getYpt();
            if(ypt != null && !"".equals(ypt)){
                ll_ypt.setVisibility(View.VISIBLE);
                ShowImageUtils.LoadImage(this,ApiConstants.YPTURLROOT+cpinfo.getYpt().replace("\\","/"), ypt_icon);
            }

            String xct = cpinfo.getXctp();
            if(xct != null && !"".equals(xct)){
                ll_xct.setVisibility(View.VISIBLE);
                ShowImageUtils.LoadImage(this,ApiConstants.YPTURLROOT+cpinfo.getXctp().replace("\\","/"), xct_icon);
            }

            String xcsp = cpinfo.getXcsp();
            if(xcsp != null && !"".equals(xcsp)){
                videopath = ApiConstants.YPTURLROOT + xcsp.replace("\\", "/");
                ll_xcsp.setVisibility(View.VISIBLE);
                Log.e("TAG","视频地址："+xcsp);

//                Bundle bundle = new Bundle();
//                bundle.putString("path",xcsp);
//                toClass(this,VideoPlayerActivity.class,bundle);
            }

            htmlTextView.setHtml(cpinfo.getCpms(),
                    new HtmlHttpImageGetter(htmlTextView));
        }

    }






}


