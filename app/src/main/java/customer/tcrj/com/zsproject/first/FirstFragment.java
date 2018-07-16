package customer.tcrj.com.zsproject.first;


import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jaeger.library.StatusBarUtil;

import butterknife.BindView;
import butterknife.OnClick;
import customer.tcrj.com.zsproject.MainActivity;
import customer.tcrj.com.zsproject.R;
import customer.tcrj.com.zsproject.base.BaseFragment;


/**
 * Created by leict on 2018/3/22.
 */

public class FirstFragment extends BaseFragment {

    @BindView(R.id.icon)
    ImageView icon;
    @BindView(R.id.ll_cpxx)
    LinearLayout ll_cpxx;
    @BindView(R.id.ll_cxlc)
    LinearLayout ll_cxlc;
    @BindView(R.id.ll_zsmsq)
    LinearLayout ll_zsmsq;
    @BindView(R.id.ll_spjg)
    LinearLayout ll_spjg;

    @Override
    protected int setLayout() {
        return R.layout.first_fragment;
    }

    @Override
    protected void setView() {
    }

    @Override
    protected void setData() {
    }

    @OnClick({R.id.ll_cpxx,R.id.ll_cxlc,R.id.ll_zsmsq,R.id.ll_spjg})
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_cpxx:
                toClass(mContext,CPinfoActivity.class);//产品信息录入

                break;

            case R.id.ll_cxlc:
//                toClass(mContext,SCLCLRActivity.class);//产销流程

                toClass(mContext,SCLClistActivity.class);//产销流程

                break;

            case R.id.ll_zsmsq:
                toClass(mContext,ZScodeActivity.class);//追溯码申请
                break;

            case R.id.ll_spjg:
                toClass(mContext,SPjgActivity.class);//审批结果查询
                break;
            default:
                break;

        }
    }

//    @Override
//    public void onClick(View v) {
//
////        switch (v.getId()){
////
////            case R.id.ll_xxks:
////                toClass(mContext,XxkcActivity.class);//学习考试
////                break;
////            case R.id.ll_xxcx:
////                toClass(mContext,XxcxActivity.class);//信息查询
////                break;
////            case R.id.ll_hdjl:
////                toClass(mContext,hdjlnewActivity.class);//互动交流
////                break;
////            case R.id.ll_xxfb:
////                toClass(mContext,releaseActivity.class);//信息发布
////                break;
////
////        }
//
//    }

}
