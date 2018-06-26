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
public class spjgAdapter extends BaseQuickAdapter<String, BaseViewHolder>{

    private Context mContext;


    public spjgAdapter(@Nullable List<String> data, Context context) {
        super(R.layout.item_spjg, data);
        this.mContext = context;
    }

    @Override
    protected void convert(final BaseViewHolder helper, String item) {
        Log.e("TAG","item:"+item);
        helper.setText(R.id.spjg_pp, item);


    }

}
