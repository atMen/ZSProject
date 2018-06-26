package customer.tcrj.com.zsproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;


import java.util.ArrayList;
import java.util.List;

import customer.tcrj.com.zsproject.R;
import customer.tcrj.com.zsproject.Utils.Tools;
import customer.tcrj.com.zsproject.bean.LeaveApplyEntity;
import customer.tcrj.com.zsproject.bean.zdCPLX;

/**
 * Created by leict on 2017/11/14.
 */

public class LeaveApplyAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private Context context;
    private List<zdCPLX.DataBean> itemList;
    private int select = -1;

    public LeaveApplyAdapter(Context context) {
        this.context = context;
        this.itemList = new ArrayList<>();
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        if (itemList == null) {
            return 0;
        }
        return itemList.size();
    }

    public void setData(List<zdCPLX.DataBean> list) {
        this.itemList.clear();
        this.itemList.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public Object getItem(int position) {
        return itemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        zdCPLX.DataBean entity = itemList.get(position);
        if (entity == null) {
            return null;
        }
        if (view == null) {
            view = inflater.inflate(R.layout.item_single_select, null);
        }
        CheckedTextView tvNoticeTitle = Tools.ViewHolder.get(view, R.id.ctv_single_choice);
        view.setId(position);
        tvNoticeTitle.setText(entity.getName());
        if (select == position) {
            tvNoticeTitle.setChecked(true);
        } else {
            tvNoticeTitle.setChecked(false);
        }
        return view;
    }

    public void setSelectItem(int position) {
        this.select = position;
    }
}
