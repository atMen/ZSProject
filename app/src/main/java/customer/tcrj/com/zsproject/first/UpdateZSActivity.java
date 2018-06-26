package customer.tcrj.com.zsproject.first;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import customer.tcrj.com.zsproject.R;
import customer.tcrj.com.zsproject.base.BaseActivity;

import static customer.tcrj.com.zsproject.R.id.edt_today_plan;

public class UpdateZSActivity extends Activity implements View.OnClickListener {



    EditText edt_work_datetime;
    Button btn_tj;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_zs);
        findview();
        initview();
    }

    private void initview() {


    }

    private void findview() {
        edt_work_datetime = findViewById(R.id.edt_work_datetime);

        btn_tj = findViewById(R.id.btn_tj);

        btn_tj.setOnClickListener(this);
    }


    private void tofinish() {
        // TODO Auto-generated method stub
        Intent intent = new Intent();
        // 获取用户计算后的结果
        String three = edt_work_datetime.getText().toString();

        if(three.equals("")){
            finish(); //结束当前的activity的生命周期
        }else {
            intent.putExtra("three", three); //将计算的值回传回去
            //通过intent对象返回结果，必须要调用一个setResult方法，
            //setResult(resultCode, data);第一个参数表示结果返回码，一般只要大于1就可以，但是
            setResult(2, intent);

            finish(); //结束当前的activity的生命周期
        }

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.btn_tj:
                tofinish();
                break;
//
//            case R.id.ll_zsmsq:
//                toClass(mContext,MainActivity.class);//追溯码申请
//                break;
//
//            case R.id.ll_spjg:
//                toClass(mContext,MainActivity.class);//审批结果查询
//                break;

        }
    }
}
