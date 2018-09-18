package customer.tcrj.com.zsproject.first;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tsy.sdk.myokhttp.MyOkHttp;
import com.tsy.sdk.myokhttp.response.GsonResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import customer.tcrj.com.zsproject.MyApp;
import customer.tcrj.com.zsproject.R;
import customer.tcrj.com.zsproject.Utils.ACache;
import customer.tcrj.com.zsproject.Utils.Utils;
import customer.tcrj.com.zsproject.base.BaseFragment;
import customer.tcrj.com.zsproject.bean.dlsInfo;
import customer.tcrj.com.zsproject.bean.xslcCxInfo;
import customer.tcrj.com.zsproject.expandablelistview.ChildHolder;
import customer.tcrj.com.zsproject.expandablelistview.DataInfo;
import customer.tcrj.com.zsproject.expandablelistview.DefaultAdapter;
import customer.tcrj.com.zsproject.expandablelistview.ParentHolder;
import customer.tcrj.com.zsproject.net.ApiConstants;
import customer.tcrj.com.zsproject.widget.DialogDateTimePicker;


/**
 * Created by leict on 2018/3/22.
 */

public class NewXSLCFragment extends BaseFragment {

    private static final int DLSCODE = 001;
    private static final int XSQDCODE = 002;

    @BindView(R.id.xsqdmc)
    TextView tvxsqdmc;
    @BindView(R.id.dlsmc)
    TextView tvdlsmc;
    @BindView(R.id.tv_xsqd)
    TextView tv_xsqd;
    @BindView(R.id.tv_dls)
    TextView tv_dls;
    @BindView(R.id.btn_tj)
    Button btn_tj;

    @BindView(R.id.ll_time)
    LinearLayout ll_time;
    @BindView(R.id.time)
    TextView time;
    @BindView(R.id.time_pop)
    LinearLayout time_pop;

    private MyOkHttp mMyOkhttp;
    private String token;
    private String productId;
    private String cpmc;
    private String status;

    @SuppressLint("ValidFragment")
    public NewXSLCFragment(String cpmc, String productId, String status) {
        this.productId = productId;
        this.cpmc = cpmc;
        this.status = status;
    }

    @Override
    protected int setLayout() {
        return R.layout.new_xslc_fragment;
    }

    @Override
    protected void setView() {
        mMyOkhttp = MyApp.getInstance().getMyOkHttp();
        token = ACache.get(mContext).getAsString("token");

        if(!"1".equals(status)){
            btn_tj.setVisibility(View.GONE);
            tv_dls.setVisibility(View.GONE);
            tv_xsqd.setVisibility(View.GONE);

            time_pop.setVisibility(View.GONE);
        }
    }


    @Override
    protected void setData() {
        getData();

    }

    //获取网络数据
    private void getData() {
        showLoadingDialog("正在加载信息...");
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("productId", productId);
            jsonObject.put("token", token);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mMyOkhttp.post()
                .url(ApiConstants.xslcInfoApi)
                .jsonParams(jsonObject.toString())
                .enqueue(new GsonResponseHandler<xslcCxInfo>() {
                    @Override
                    public void onFailure(int statusCode, String error_msg) {

                        hideLoadingDialog();
                        Log.e("TAG","error_msg"+error_msg);


                    }

                    @Override
                    public void onSuccess(int statusCode, xslcCxInfo response) {
                        hideLoadingDialog();
                        if(response.getErrorcode().equals("9999")){


                            setXsData(response);

                        }else if(response.getErrorcode().equals("204")){

                            Utils.toLogin(mContext);
                        }


                    }
                });

    }

    private void setXsData(xslcCxInfo response) {

        if(response.getData() != null && response.getData().size() > 0){
            xslcCxInfo.DataBean dataBean = response.getData().get(0);
            tvdlsmc.setText("代理商管理: "+dataBean.getDlsmc());
            tv_dls.setText("修改");

            tvxsqdmc.setText("销售渠道管理: "+dataBean.getQdmc());
            tv_xsqd.setText("修改");

            time.setText(dataBean.getChsj());
        }else {

        }
    }


    @OnClick({R.id.tv_dls,R.id.tv_xsqd,R.id.btn_tj,R.id.ll_time})
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.tv_dls:
                toClass(mContext,DlsActivity.class,null,DLSCODE);//代理商
                break;
            case R.id.tv_xsqd:
                toClass(mContext,XsqdActivity.class,null,XSQDCODE);//销售渠道
                break;

            case R.id.btn_tj:

                String chsj = time.getText().toString();
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
                if("1".equals(status)){
                    setTimd();
                }
                break;

            default:
                break;

        }

    }

    private void setTimd() {

        DialogDateTimePicker start = new DialogDateTimePicker(mContext);
        start.onDatePickerListener(new DialogDateTimePicker.DatePickerCallBack() {
            @Override
            public void onClickListener(String s) {
                time.setText(s);

            }
        });
        start.show();
    }

    private String dlsName;
    private String dlsId;
    private String qdName;
    private String qdId;
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




                        }else if(response.getErrorcode().equals("204")){

                            Utils.toLogin(mContext);
                        }


                    }
                });
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
                tvdlsmc.setText("代理商管理: "+dlsmc);
                tv_dls.setText("修改");
            }else if(requestCode== XSQDCODE && resultCode == 3 && data != null){
                qdName = dlsmc;
                qdId = dlsid;
                tvxsqdmc.setText("销售渠道管理: "+dlsmc);
                tv_xsqd.setText("修改");
            }
        }





    }

}
