package customer.tcrj.com.zsproject.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import customer.tcrj.com.zsproject.R;
import customer.tcrj.com.zsproject.Utils.ShowImageUtils;
import customer.tcrj.com.zsproject.bean.cpInfo;
import customer.tcrj.com.zsproject.net.ApiConstants;


/**
 * desc: .
 * author: Will .
 * date: 2017/9/27 .
 */
public class cpInfoAdapter extends BaseQuickAdapter<cpInfo.DataBean.ContentBean, BaseViewHolder>{
    private Context mContext;

    public cpInfoAdapter(@Nullable List<cpInfo.DataBean.ContentBean> data, Context context) {
        super(R.layout.item_cpinfo, data);
        this.mContext = context;
    }


    @Override
    protected void convert(final BaseViewHolder helper, cpInfo.DataBean.ContentBean item) {

        helper.setText(R.id.cpname, "产品名称："+item.getCpmc());

        String timestamp = item.getTimestamp();
        String substring = "";
        if(timestamp != null){substring = timestamp.substring(0, 10);}

        helper.setText(R.id.cptime, substring);
        helper.setText(R.id.name, "产品品牌："+item.getCppp());
        helper.setText(R.id.sex, "产品标称规格："+item.getCpbcgg());
        helper.setText(R.id.duty, "产品标称规格单位："+item.getCpbcggdw());


//        ShowImageUtils.showImageView(mContext,ApiConstants.ImageURLROOT+item.getYpt(), (ImageView) helper.getView(R.id.im_icon));
        ShowImageUtils.LoadImage(mContext,ApiConstants.ImageURLROOT+item.getYpt(), (ImageView) helper.getView(R.id.im_icon));



    }



}
