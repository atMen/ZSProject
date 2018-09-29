package customer.tcrj.com.zsproject.mine;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pgyersdk.javabean.AppBean;
import com.pgyersdk.update.PgyUpdateManager;
import com.pgyersdk.update.UpdateManagerListener;

import butterknife.BindView;
import butterknife.OnClick;
import customer.tcrj.com.zsproject.LoginActivity;
import customer.tcrj.com.zsproject.R;
import customer.tcrj.com.zsproject.Utils.ACache;
import customer.tcrj.com.zsproject.Utils.AppManager;
import customer.tcrj.com.zsproject.base.BaseFragment;
import customer.tcrj.com.zsproject.dialog.SweetAlertDialog;


/**
 * Created by leict on 2018/3/22.
 */

public class MineFragment extends BaseFragment {

    @BindView(R.id.modify_psw)
    RelativeLayout modify_psw;
    @BindView(R.id.rl_about)
    RelativeLayout rl_about;
    @BindView(R.id.rl_updata)
    RelativeLayout rl_updata;

    @Override
    protected int setLayout() {
        return R.layout.mine_fragment;
    }

    @Override
    protected void setView() {}

    @Override
    protected void setData() {}

    private void showUpdateDialog() {

        final SweetAlertDialog sad = new SweetAlertDialog(getActivity());
        sad.setTitleText("退出登录");
        sad.setContentText("您确定要进行注销操作吗？");
        sad.setConfirmText("取消");
        sad.setCancelText("确定");
        sad.setCanceledOnTouchOutside(true);
        sad.setCancelable(true);
        sad.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sad.dismiss();
                sureZX();
            }
        });
        sad.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {

                sad.dismiss();

            }
        });
        sad.show();
    }

    private void sureZX() {
        AppManager.getAppManager().finishAllActivity();
        ACache.get(mContext).clear();
        goLogin();
    }

    private void goLogin() {
        Intent intent = new Intent(mContext, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @OnClick({R.id.modify_psw,R.id.rl_about,R.id.rl_updata})
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.modify_psw:
                toClass(mContext,MdifyPswActivity.class);
                break;
            case R.id.rl_about:
                showUpdateDialog();
                break;
            case R.id.rl_updata:
                init();
                break;
            default:
                break;

        }

    }

    private void init() {
        showLoadingDialog("正在检测更新..");
        PgyUpdateManager.register(getActivity(),
                new UpdateManagerListener() {

                    @Override
                    public void onUpdateAvailable(final String result) {

                        hideLoadingDialog();
                        // 将新版本信息封装到AppBean中
                        final AppBean appBean = getAppBeanFromString(result);
                        new AlertDialog.Builder(getActivity())
                                .setTitle("更新")
                                .setMessage(appBean.getReleaseNote())
                                .setNegativeButton(
                                        "确定",
                                        new DialogInterface.OnClickListener() {

                                            @Override
                                            public void onClick(



                                                    DialogInterface dialog,
                                                    int which) {
                                                startDownloadTask(
                                                        getActivity(),
                                                        appBean.getDownloadURL());
                                            }
                                        }).show();
                    }

                    @Override
                    public void onNoUpdateAvailable() {
                        hideLoadingDialog();

                        Toast.makeText(mContext, "当前为最新版本", Toast.LENGTH_LONG).show();

                    }
                });
    }

}
