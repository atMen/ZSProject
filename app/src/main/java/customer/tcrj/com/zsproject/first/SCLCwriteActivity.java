package customer.tcrj.com.zsproject.first;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import org.sufficientlysecure.htmltextview.HtmlHttpImageGetter;
import org.sufficientlysecure.htmltextview.HtmlTextView;

import butterknife.BindView;
import butterknife.OnClick;
import customer.tcrj.com.zsproject.LoginActivity;
import customer.tcrj.com.zsproject.MainActivity;
import customer.tcrj.com.zsproject.MyApp;
import customer.tcrj.com.zsproject.R;
import customer.tcrj.com.zsproject.Utils.ACache;
import customer.tcrj.com.zsproject.base.BaseActivity;
import customer.tcrj.com.zsproject.bean.cpSCLCinfo;
import customer.tcrj.com.zsproject.bean.loginInfo;
import customer.tcrj.com.zsproject.net.ApiConstants;

public class SCLCwriteActivity extends BaseActivity {

    private static final int CODE = 001;
    @BindView(R.id.btnback)
    ImageView btnback;
    @BindView(R.id.txtTitle)
    TextView txtTitle;
    @BindView(R.id.num)
    TextView num;

    @BindView(R.id.edt_today_plan)
    EditText edt_today_plan;
    @BindView(R.id.btn_submit_daily)
    Button btn_submit_daily;
    @BindView(R.id.edt_cpname)
    EditText edt_cpname;

    private MyOkHttp mMyOkhttp;
    private String productId;
    private String token;
    cpSCLCinfo.DataBean.ContentBean position;

    boolean isadd;

    @Override
    protected int setLayout() {
        return R.layout.activity_sclcwrite;
    }

    @Override
    protected void setView() {
        num.setVisibility(View.VISIBLE);
        num.setText("加工流程");
        productId = getIntent().getStringExtra("productId");
        position = (cpSCLCinfo.DataBean.ContentBean) getIntent().getSerializableExtra("ContentBean");

        if(productId != null){
            isadd = true;
        }else{
            HtmlTextView htmlTextView = new HtmlTextView(this);
            htmlTextView.setHtml(position.getDescription(),
                    new HtmlHttpImageGetter(htmlTextView));
            String text = htmlTextView.getText().toString();

            isadd = false;
            edt_today_plan.setText(text);
            edt_cpname.setText(position.getName());
        }

        token = ACache.get(this).getAsString("token");
        txtTitle.setText("生产流程");
        mMyOkhttp = MyApp.getInstance().getMyOkHttp();
    }

    @Override
    protected void setData() {

    }


    private void tofinish(String id) {
        // TODO Auto-generated method stub
        Intent intent = new Intent();
        // 获取用户计算后的结果
        String three = edt_today_plan.getText().toString();
        String cpname = edt_cpname.getText().toString();


            cpSCLCinfo.DataBean.ContentBean contentBean = new cpSCLCinfo.DataBean.ContentBean();
            contentBean.setName(cpname);
            contentBean.setDescription(three);
            contentBean.setId(id == null? position.getId():id);
            Log.e("TAG","生产流程id"+id);
        if(position != null){
            Log.e("TAG","生产流程position.getId()"+position.getId());
        }

            intent.putExtra("three", contentBean);
//          intent.putExtra("three", three); //将计算的值回传回去
            //通过intent对象返回结果，必须要调用一个setResult方法，

        if(isadd){
            setResult(2, intent);
        }else {
            setResult(3, intent);
        }


        finish(); //结束当前的activity的生命周期


    }

    @OnClick({R.id.btnback,R.id.btn_submit_daily,R.id.layout_work_naturejob,R.id.num})
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.num:
                toClass(this,SCLCMBActivity.class,null,CODE);//生产流程模板数据
                break;

            case R.id.btnback:
                finish();
                break;

            case R.id.btn_submit_daily:

                String three = edt_today_plan.getText().toString();
                String cpname = edt_cpname.getText().toString();
                if(three.equals("") || cpname.equals("")){
                    T("请正确填写数据");
                }else {
                    if(isadd){
                        add(cpname,three);
                    }else {
                        updata(cpname,three);
                    }
                }

                break;

            default:
                break;
        }
    }

    //联网进行修改
    private void updata(String cpname, String three) {

        showLoadingDialog("正在修改...");

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("token", token);
            jsonObject.put("id", position.getId());
            jsonObject.put("name", cpname);
            jsonObject.put("description", three);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mMyOkhttp.post()
                .url(ApiConstants.updatecpjglistinfoApi)
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
                        if(response.getErrorcode().equals("9999")){
                            Log.e("TAG","成功");

                            tofinish(null);

                        }
                    }
                });



    }

    //联网进行添加
    private void add(final String name, final String description) {
        showLoadingDialog("正在添加...");

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("token", token);
            jsonObject.put("productId", productId);
            jsonObject.put("name", name);
            jsonObject.put("description", description);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mMyOkhttp.post()
                .url(ApiConstants.addcpjglistinfoApi)
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
                        if(response.getErrorcode().equals("9999")){
                            Log.e("TAG","成功");

                            tofinish(response.getData());

                        }
                    }
                });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(data != null){
            String mc = data.getStringExtra("mc");
            String ms = data.getStringExtra("ms");

            if(requestCode== CODE && resultCode == 3 && data != null){
                HtmlTextView htmlTextView = new HtmlTextView(this);
                htmlTextView.setHtml(ms,
                        new HtmlHttpImageGetter(htmlTextView));
                String text = htmlTextView.getText().toString();


                edt_today_plan.setText(text);
                edt_cpname.setText(mc);
            }
        }
    }


}
