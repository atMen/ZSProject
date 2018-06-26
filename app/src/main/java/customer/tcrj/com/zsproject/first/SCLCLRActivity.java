package customer.tcrj.com.zsproject.first;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import customer.tcrj.com.zsproject.MainActivity;
import customer.tcrj.com.zsproject.R;
import customer.tcrj.com.zsproject.base.BaseActivity;
import customer.tcrj.com.zsproject.mine.MineFragment;
import customer.tcrj.com.zsproject.resources.ResourcesFragment;
import customer.tcrj.com.zsproject.search.SearchFragment;

public class SCLCLRActivity extends BaseActivity {


    @BindView(R.id.txtTitle)
    TextView  txtTitle;
    @BindView(R.id.tv_sclc)
    TextView  tv_sclc;
    @BindView(R.id.tv_xslc)
    TextView  tv_xslc;

    private FragmentManager fragmentManager;
    private SCLCFragment newsFragment;
    private XSLCFragment settingFragment;

    String productId;


    @Override
    protected int setLayout() {
        return R.layout.activity_sclclr;
    }

    @Override
    protected void setView() {
        fragmentManager = getSupportFragmentManager();
        productId = getIntent().getStringExtra("productId");
        initview();
        setTabSelection(0);
    }

    private void initview() {
        txtTitle.setText("产销流程录入");
    }

    @Override
    protected void setData() {

    }


    @OnClick({R.id.tv_sclc,R.id.tv_xslc})
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


        }
    }

    private void setTabSelection(int index) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideFragments(transaction);
        switch (index) {
            case 0:
                if (newsFragment == null) {
                    newsFragment = new SCLCFragment(productId);
                    transaction.add(R.id.contentContainer, newsFragment);
                } else {
                    transaction.show(newsFragment);
                }

                break;
            case 1:
                if (settingFragment == null) {
                    settingFragment = new XSLCFragment();
                    transaction.add(R.id.contentContainer, settingFragment);
                } else {
                    transaction.show(settingFragment);
                }
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
