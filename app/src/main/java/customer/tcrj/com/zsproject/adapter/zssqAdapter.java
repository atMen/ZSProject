package customer.tcrj.com.zsproject.adapter;

import android.content.Context;
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
public class zssqAdapter extends BaseQuickAdapter<cpInfo.DataBean.ContentBean, BaseViewHolder>{

    public static final int LAST_POSITION = -1;
    private Context mContext;
    private RecyclerView mRecyclerView;


    public zssqAdapter(@Nullable List<cpInfo.DataBean.ContentBean> data, Context context, RecyclerView mRecyclerView) {
        super(R.layout.zs_item, data);
        this.mContext = context;
        this.mRecyclerView = mRecyclerView;
    }






    @Override
    protected void convert(final BaseViewHolder helper, final cpInfo.DataBean.ContentBean item) {
        Log.e("TAG","item:"+item);
        helper.setText(R.id.cpname, "产品名称："+item.getCpmc());
        helper.setText(R.id.bh, "产品流水号(追溯码)："+item.getId());
        helper.setText(R.id.spjg_pp, item.getCppp());
        helper.setText(R.id.gg, item.getCpbcgg());
        helper.setText(R.id.simple_text, item.getEwmsl()+"份");
        TextView remo = helper.getView(R.id.simple_remo);
        CheckBox view = helper.getView(R.id.checkbox);

        view.setChecked(item.isselect());


        if (mOnItemClickLitener != null)
        {
            helper.getConvertView().setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    mOnItemClickLitener.onItemClick(helper.getConvertView(), helper.getPosition());
                }
            });
        }


        if(onItemClickListener != null) {
            remo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("TAG", "position:" + helper.getPosition() + "");
//                updata("500",helper.getPosition());

                    if (onItemClickListener != null) {
                        onItemClickListener.OnItemClick(helper.getPosition(),item);
                    }


                }
            });
        }



    }

//    @Override
//    public int getItemCount() {
//        return mData.size();
//    }


    public void updata(String s,int position) {
        cpInfo.DataBean.ContentBean s1 = mData.get(position);
        s1.setEwmsl(s);
        Log.e("TAG","s1:"+s1);
        Log.e("TAG","s:"+s);
        mData.set(position,s1);
//        notifyItemInserted(position);
        notifyItemRangeChanged(position,1);
    }


    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {//定义接口，实现Recyclerview点击事件
        void OnItemClick(int position,cpInfo.DataBean.ContentBean item);
    }


    public void setOnItemUpdataClickListener(OnItemClickListener onItemClickListener) {   //实现点击
        this.onItemClickListener = onItemClickListener;
    }


    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener)
    {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }



}
