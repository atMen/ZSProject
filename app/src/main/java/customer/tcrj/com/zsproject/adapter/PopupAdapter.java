package customer.tcrj.com.zsproject.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import customer.tcrj.com.zsproject.R;

/**
 * Created by leict on 2018/10/31.
 */

public class PopupAdapter extends BaseAdapter {
    private List<String> lists;
    public Context mContext;

    public PopupAdapter(Context context,List lists){
        this.mContext=context;
        this.lists=lists;
    }
    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public String getItem(int position) {
        return lists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null){
            convertView= LinearLayout.inflate(mContext, R.layout.item_layout,null);
            viewHolder = new ViewHolder();
            viewHolder.textView=(TextView) convertView.findViewById(R.id.item_view);
            convertView.setTag(viewHolder);
        }else{
            convertView.getTag();
        }
        String content = lists.get(position);
        if(!TextUtils.isEmpty(content)){
            viewHolder.textView.setText(lists.get(position));
        }
        return convertView;
    }

    class ViewHolder {
        public TextView textView;
    }
}
