package customer.tcrj.com.zsproject.first;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tsy.sdk.myokhttp.MyOkHttp;
import com.tsy.sdk.myokhttp.response.GsonResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;
import customer.tcrj.com.zsproject.MainActivity;
import customer.tcrj.com.zsproject.MyApp;
import customer.tcrj.com.zsproject.R;
import customer.tcrj.com.zsproject.Utils.ACache;
import customer.tcrj.com.zsproject.Utils.Utils;
import customer.tcrj.com.zsproject.base.BaseActivity;
import customer.tcrj.com.zsproject.bean.xslcCxInfo;
import customer.tcrj.com.zsproject.dialog.SweetAlertDialog;
import customer.tcrj.com.zsproject.mine.MineFragment;
import customer.tcrj.com.zsproject.net.ApiConstants;
import customer.tcrj.com.zsproject.resources.ResourcesFragment;
import customer.tcrj.com.zsproject.search.SearchFragment;

public class SCLCLRActivity extends BaseActivity {

    @BindView(R.id.btnback)
    ImageView btnback;
    @BindView(R.id.txtTitle)
    TextView  txtTitle;
    @BindView(R.id.tv_sclc)
    TextView  tv_sclc;
    @BindView(R.id.tv_xslc)
    TextView  tv_xslc;
    @BindView(R.id.num)
    TextView num;

    private FragmentManager fragmentManager;
    private SCLCFragment newsFragment;
    private NewXCLCFragment settingFragment;

    String status;
    String productId;
    String cpmc;

    private MyOkHttp mMyOkhttp;
    private String token;
    @Override
    protected int setLayout() {
        return R.layout.activity_sclclr;
    }

    @Override
    protected void setView() {
        mMyOkhttp = MyApp.getInstance().getMyOkHttp();
        token = ACache.get(this).getAsString("token");
        fragmentManager = getSupportFragmentManager();
        productId = getIntent().getStringExtra("productId");
        status = getIntent().getStringExtra("status");
        cpmc = getIntent().getStringExtra("cpmc");
        initview();
        setTabSelection(0);
    }

    private void initview() {
        txtTitle.setText("产销流程录入");
    }

    @Override
    protected void setData() {

    }


    @OnClick({R.id.tv_sclc,R.id.tv_xslc,R.id.btnback})
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_sclc:
                tv_sclc.setTextColor(getResources().getColor(R.color.blue));
                tv_xslc.setTextColor(getResources().getColor(R.color.login_textcolor));
                setTabSelection(0);
                break;

            case R.id.tv_xslc:
                tv_xslc.setTextColor(getResources().getColor(R.color.blue));
                tv_sclc.setTextColor(getResources().getColor(R.color.login_textcolor));
                setTabSelection(1);
                break;
            case R.id.btnback:

                finish();

                break;


            default:
                break;


        }
    }



    private void setTabSelection(int index) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideFragments(transaction);
        switch (index) {
            case 0:
                if (newsFragment == null) {
                    newsFragment = new SCLCFragment(productId,status);
                    transaction.add(R.id.contentContainer, newsFragment);
                } else {
                    transaction.show(newsFragment);
                }

                break;
            case 1:
                if (settingFragment == null) {
                    settingFragment = new NewXCLCFragment(cpmc,productId,status);
                    transaction.add(R.id.contentContainer, settingFragment);
                } else {
                    transaction.show(settingFragment);
                }
                break;

            default:
                break;

        }
        transaction.commit();
    }

    private void hideFragments(FragmentTransaction transaction) {

        if (newsFragment != null) {
            transaction.hide(newsFragment);
        }
        if (settingFragment != null) {
            transaction.hide(settingFragment);
        }


    }
}
