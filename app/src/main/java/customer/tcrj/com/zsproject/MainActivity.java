package customer.tcrj.com.zsproject;

import android.content.pm.ActivityInfo;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;


import com.jaeger.library.StatusBarUtil;

import customer.tcrj.com.zsproject.Utils.AppManager;
import customer.tcrj.com.zsproject.first.FirstFragment;
import customer.tcrj.com.zsproject.mine.MineFragment;
import customer.tcrj.com.zsproject.resources.NewResourcesFragment;
import customer.tcrj.com.zsproject.resources.ResourcesFragment;
import customer.tcrj.com.zsproject.search.SearchFragment;
import customer.tcrj.com.zsproject.widget.BottomNavigationViewHelper;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private FragmentManager fragmentManager;
    private MineFragment mineFragment;
    private FirstFragment newsFragment;
    private SearchFragment settingFragment;
    private NewResourcesFragment djFragment;

    private TextView poiat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        StatusBarUtil.setTranslucentForImageViewInFragment(MainActivity.this, 5,null);
        AppManager.getAppManager().addActivity(this);
        initview();
    }

    private void initview() {
        fragmentManager = getSupportFragmentManager();
        BottomNavigationView navigationView = (BottomNavigationView) findViewById(R.id.bnve);
        BottomNavigationViewHelper.disableShiftMode(navigationView);

        navigationView.setOnNavigationItemSelectedListener(this);

        setTabSelection(0);
    }

    private int num = -1;
    private void setTabSelection(int index) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideFragments(transaction);
        switch (index) {
            case 0:
                if (newsFragment == null) {
                    newsFragment = new FirstFragment();
                    transaction.add(R.id.contentContainer, newsFragment);
                } else {
                    transaction.show(newsFragment);
                }

                break;
            case 2:
                if (djFragment == null) {
                    djFragment = new NewResourcesFragment();
                    transaction.add(R.id.contentContainer, djFragment);
                } else {
                    transaction.show(djFragment);
                }
                break;
            case 3:
                if (mineFragment == null) {
                    mineFragment = new MineFragment();
                    transaction.add(R.id.contentContainer, mineFragment);
                } else {
                    transaction.show(mineFragment);
                }
                break;

            case 1:
                if (settingFragment == null) {
                    settingFragment = new SearchFragment();
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
        if (mineFragment != null) {
            transaction.hide(mineFragment);
        }
        if (newsFragment != null) {
            transaction.hide(newsFragment);
        }
        if (settingFragment != null) {
            transaction.hide(settingFragment);
        }
        if (djFragment != null) {
            transaction.hide(djFragment);
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_music:

                if(num != 0){
                    setTabSelection(0);
                }

                num = 0;
                return true;

            case R.id.menu_backup:

//              poiat.setVisibility(View.GONE);
                if(num != 1) {
                    setTabSelection(1);
                }
                num = 1;

                return true;

            case R.id.menu_friends:
                if(num != 2) {
                    setTabSelection(2);
                }
                num = 2;

                return true;

            case R.id.menu_set:
                if(num != 3) {
                    setTabSelection(3);
                }
                num = 3;

                return true;
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
        AppManager.getAppManager().finishActivity(this); //从栈中移除
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exit() {
        if (!isExit) {
            isExit = true;
            Toast.makeText(getApplicationContext(), "再按一次退出程序",
                    Toast.LENGTH_SHORT).show();
            // 利用handler延迟发送更改状态信息

            mHandler.sendEmptyMessageDelayed(0, 2000);
        } else {
            finish();
//            System.exit(0);
        }
    }

    //定义一个变量，
    //来标识是否退出
    private  boolean isExit = false;
    Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }
    };
}

