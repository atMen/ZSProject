package customer.tcrj.com.zsproject.first;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
import butterknife.OnClick;
import customer.tcrj.com.zsproject.MyApp;
import customer.tcrj.com.zsproject.R;
import customer.tcrj.com.zsproject.Utils.ACache;
import customer.tcrj.com.zsproject.Utils.Utils;
import customer.tcrj.com.zsproject.base.BaseActivity;
import customer.tcrj.com.zsproject.bean.addCPInfo;
import customer.tcrj.com.zsproject.bean.cpInfo;
import customer.tcrj.com.zsproject.net.ApiConstants;

import static customer.tcrj.com.zsproject.R.id.edt_today_plan;

public class UpdateZSActivity extends Activity implements View.OnClickListener {



    EditText edt_work_datetime;
    Button btn_tj;
    ImageView iv_close;

    cpInfo.DataBean.ContentBean cpinfo;
    private MyOkHttp mMyOkhttp;
    private String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_zs);
        mMyOkhttp = MyApp.getInstance().getMyOkHttp();
        token = ACache.get(this).getAsString("token");
        cpinfo = (cpInfo.DataBean.ContentBean) getIntent().getSerializableExtra("cpinfo");
        findview();
        initview();
    }

    private void initview() {


    }

    private void findview() {

        edt_work_datetime = findViewById(R.id.edt_work_datetime);

        btn_tj = findViewById(R.id.btn_tj);
        iv_close = findViewById(R.id.iv_close);

        btn_tj.setOnClickListener(this);
        iv_close.setOnClickListener(this);
    }


    private void tofinish() {
        // TODO Auto-generated method stub
            Intent intent = new Intent();

            intent.putExtra("three", three); //将计算的值回传回去
            //通过intent对象返回结果，必须要调用一个setResult方法，
            //setResult(resultCode, data);第一个参数表示结果返回码，一般只要大于1就可以，但是
            setResult(2, intent);

            finish(); //结束当前的activity的生命周期


    }

    String three;
    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.btn_tj:
                // 获取用户计算后的结果
                three = edt_work_datetime.getText().toString();
                if(three != null && !"".equals(three)){
                    addData(three);

                }else {
                    Toast.makeText(this, "填写要修改的数量", Toast.LENGTH_SHORT).show();
                }

                break;

            case R.id.iv_close:
                finish();
                break;

            default:
                break;

        }
    }


    private void addData(String three) {
        JSONObject jsonObject = new JSONObject();

        try {

            jsonObject.put("token", token);
            jsonObject.put("cpmc", cpinfo.getCpmc());
            jsonObject.put("cppp", cpinfo.getCppp());
            jsonObject.put("baseId", cpinfo.getBaseId());
            jsonObject.put("cpbcgg", cpinfo.getCpbcgg());
            jsonObject.put("cpbcggdw", cpinfo.getCpbcggdw());
            jsonObject.put("bxq", cpinfo.getBxq());
            jsonObject.put("cplx", cpinfo.getCplx());
            jsonObject.put("zsfs", cpinfo.getZsfs());
            jsonObject.put("zsywlb", cpinfo.getZsywlb());
            jsonObject.put("ewmsl", three);
            jsonObject.put("cpms", cpinfo.getCpms());
            jsonObject.put("id", cpinfo.getId());
            jsonObject.put("ypt","");
            jsonObject.put("yptName","");
            jsonObject.put("xcsp", "");
            jsonObject.put("xcspName", "");
            jsonObject.put("xctp", "");
            jsonObject.put("xctpName", "");


        } catch (JSONException e) {
            e.printStackTrace();
        }

        mMyOkhttp.post()
                .url(ApiConstants.updatecpinfoApi)
                .jsonParams(jsonObject.toString())
                .enqueue(new GsonResponseHandler<addCPInfo>() {
                    @Override
                    public void onFailure(int statusCode, String error_msg) {

                        Log.e("TAG","error_msg"+error_msg);

                    }

                    @Override
                    public void onSuccess(int statusCode, addCPInfo response) {

                        Toast.makeText(UpdateZSActivity.this, response.getMessage(), Toast.LENGTH_SHORT).show();

                        if(response.getErrorcode().equals("9999")){

                            tofinish();

                        }else if(response.getErrorcode().equals("204")){
                            Utils.toLogin(UpdateZSActivity.this);
                        }


                    }
                });



    }
}
