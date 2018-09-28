package customer.tcrj.com.zsproject.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import customer.tcrj.com.zsproject.R;
import customer.tcrj.com.zsproject.Utils.ShowImageUtils;
import customer.tcrj.com.zsproject.bean.cpInfo;
import customer.tcrj.com.zsproject.bean.resourcesInfo;
import customer.tcrj.com.zsproject.net.ApiConstants;


/**
 * desc: .
 * author: Will .
 * date: 2017/9/27 .
 */
public class resourcesInfoAdapter extends BaseQuickAdapter<resourcesInfo.DataBean, BaseViewHolder>{
    private Context mContext;

    public resourcesInfoAdapter(@Nullable List<resourcesInfo.DataBean> data, Context context) {
        super(R.layout.item_cpinfo, data);
        this.mContext = context;
    }


    @Override
    protected void convert(final BaseViewHolder helper, resourcesInfo.DataBean item) {

        ImageView im_icon = (ImageView) helper.getView(R.id.im_icon);
        ImageView im_video = (ImageView) helper.getView(R.id.im_video);
        helper.setText(R.id.cpname, "企业名称："+item.getYwmc());

        String timestamp = item.getUploadDate();
        String substring = "";
        String cltype = "";
        if(timestamp != null){substring = timestamp.substring(0, 10);}

        String cllx = item.getCllx();
        Log.e("TAG","cllx"+cllx);
        if ("10402".equals(cllx)){
            cltype = "视频";
            im_icon.setVisibility(View.GONE);
            im_video.setVisibility(View.VISIBLE);
        }else if("10401".equals(cllx)){
            cltype = "图片";
            im_icon.setVisibility(View.VISIBLE);
            im_video.setVisibility(View.GONE);
            ShowImageUtils.LoadImage(mContext,ApiConstants.ImageURLROOT+item.getCllj().replace("\\","/"), im_icon);
        }

        helper.getView(R.id.cptime).setVisibility(View.GONE);
        helper.setText(R.id.name, "材料名称："+item.getClmc());
        helper.setText(R.id.sex, "材料类型："+cltype);
        helper.setText(R.id.duty, "上传时间："+substring);

        Log.e("TAG","图片地址："+ApiConstants.ImageURLROOT+item.getCllj().replace("\\","/"));

    }



}
