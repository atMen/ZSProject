package customer.tcrj.com.zsproject.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.CheckedTextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import customer.tcrj.com.zsproject.R;
import customer.tcrj.com.zsproject.bean.dlsInfo;
import customer.tcrj.com.zsproject.bean.qdInfo;


/**
 * desc: .
 * author: Will .
 * date: 2017/9/27 .
 */
public class qdAdapter extends BaseQuickAdapter<qdInfo.DataBean.ContentBean, BaseViewHolder>{
    private Context mContext;
    private int select = -1;

    public qdAdapter(@Nullable List<qdInfo.DataBean.ContentBean> data, Context context) {
        super(R.layout.item_message, data);
        this.mContext = context;
    }


    @Override
    protected void convert(final BaseViewHolder helper, qdInfo.DataBean.ContentBean item) {

        int position = helper.getPosition();
        CheckedTextView view = (CheckedTextView) helper.getView(R.id.ctv_single_choice);
        view.setText(item.getQdmc());
        if (select == position) {
            view.setChecked(true);
        } else {
            view.setChecked(false);
        }

    }

    public void setSelectItem(int position) {
        this.select = position;
    }



}
