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


/**
 * desc: .
 * author: Will .
 * date: 2017/9/27 .
 */
public class zssqAdapter extends BaseQuickAdapter<String, BaseViewHolder>{

    public static final int LAST_POSITION = -1;
    private Context mContext;
    private RecyclerView mRecyclerView;
    public static HashMap<Integer, Boolean> isSelected;
    private List<String> datas;


    public zssqAdapter(@Nullable List<String> data, Context context, RecyclerView mRecyclerView) {
        super(R.layout.zs_item, data);
        this.mContext = context;
        this.mRecyclerView = mRecyclerView;
        this.datas = data;
        init();
    }

    // 初始化 设置所有item都为未选择
    public void init() {
        isSelected = new HashMap<Integer, Boolean>();
        for (int i = 0; i < mData.size(); i++) {
            isSelected.put(i, false);
        }
    }

    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener)
    {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }



    @Override
    protected void convert(final BaseViewHolder helper, String item) {
        Log.e("TAG","item:"+item);
        helper.setText(R.id.simple_text, "描述："+item);
        TextView remo = helper.getView(R.id.simple_remo);
        CheckBox view = helper.getView(R.id.checkbox);
        int size = isSelected.size();
        if(size > 0){
            view.setChecked(isSelected.get(helper.getPosition()));
        }else {
            view.setChecked(false);
        }

        Log.e("TAG","isSelected.get(helper.getPosition()):"+isSelected.get(helper.getPosition()));
//        helper.getConvertView().setSelected(false);

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
                        onItemClickListener.OnItemClick(helper.getPosition());
                    }


                }
            });
        }



    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public void updata(String s,int position) {
        String s1 = mData.get(position);
        Log.e("TAG","s1:"+s1);
        Log.e("TAG","s:"+s);
        mData.set(position,s);
//        notifyItemInserted(position);
        notifyItemRangeChanged(position,1);
    }

    public void add(String s,int position) {

        Log.e("add","s:"+s);
        position = position == LAST_POSITION ? getItemCount()  : position;
        mData.add(position,s);

        notifyItemInserted(position);

//        notifyItemRangeInserted(position,1);
//        mRecyclerView.smoothScrollToPosition(position);


        //如果在第一项添加模拟数据需要调用 scrollToPosition（0）把列表移动到顶端（可选）
        mRecyclerView.scrollToPosition(position);
    }


    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {//定义接口，实现Recyclerview点击事件
        void OnItemClick(int position);
    }


    public void setOnItemUpdataClickListener(OnItemClickListener onItemClickListener) {   //实现点击
        this.onItemClickListener = onItemClickListener;
    }



}
