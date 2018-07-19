package customer.tcrj.com.zsproject.mine;


import android.content.Intent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
    protected void setView() {
    }


    @Override
    protected void setData() {


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

                break;
            default:
                break;

        }

    }




    private void showUpdateDialog() {

        final SweetAlertDialog sad = new SweetAlertDialog(getActivity());
        sad.setTitleText("注销登录");
        sad.setContentText("您确定要进行注销操作吗？");
        sad.setConfirmText("确定");
        sad.setCancelText("取消");
        sad.setCanceledOnTouchOutside(true);
        sad.setCancelable(true);
        sad.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sad.dismiss();


            }
        });
        sad.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sureZX();
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


}
