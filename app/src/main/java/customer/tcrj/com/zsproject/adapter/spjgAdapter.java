package customer.tcrj.com.zsproject.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.HashMap;
import java.util.List;

import customer.tcrj.com.zsproject.R;
import customer.tcrj.com.zsproject.bean.cpInfo;


/**
 * desc: .
 * author: Will .
 * date: 2017/9/27 .
 */
public class spjgAdapter extends BaseQuickAdapter<cpInfo.DataBean.ContentBean, BaseViewHolder>{

    private Context mContext;


    public spjgAdapter(@Nullable List<cpInfo.DataBean.ContentBean> data, Context context) {
        super(R.layout.item_spjg, data);
        this.mContext = context;
    }

    @Override
    protected void convert(final BaseViewHolder helper, cpInfo.DataBean.ContentBean item) {
        TextView view = (TextView)helper.getView(R.id.spjg_state);
        Log.e("TAG","item:"+item);
        String status = item.getStatus();
        String statusName;
        if("1".equals(status)){
            statusName = "待提交";
            view.setTextColor(mContext.getResources().getColor(R.color.login_textcolor));
        }else  if("2".equals(status)){
            statusName = "审批中";
            view.setTextColor(mContext.getResources().getColor(R.color.yellow));
        }else  if("3".equals(status)){
            statusName = "已通过";
            view.setTextColor(Color.GREEN);
        }else {
            statusName = "未通过";
            view.setTextColor(Color.RED);
        }

        helper.setText(R.id.cpname, "产品名称："+item.getCpmc());
        view.setText(statusName);
        helper.setText(R.id.spjg_bh, "产品流水号(追溯码)："+item.getId());

        helper.setText(R.id.spjg_pp, item.getCppp());
        helper.setText(R.id.spjg_gg, item.getCpbcgg()+item.getCpbcggdw());
        helper.setText(R.id.spjg_num, item.getEwmsl()+"份");
        helper.setText(R.id.simple_time,item.getTimestamp());
    }

}
