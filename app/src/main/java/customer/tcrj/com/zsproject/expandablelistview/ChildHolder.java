package customer.tcrj.com.zsproject.expandablelistview;

import android.view.View;

import java.util.List;

/**
 * Created by miao on 2017/10/20.
 */

public abstract class ChildHolder<T> {
    private View convertView;

    public ChildHolder() {
        convertView = initView();
        convertView.setTag(this);
    }

    public View getConvertView() {
        return convertView;
    }

    @SuppressWarnings("unchecked")
    protected <T extends View> T findID(View v, int id) {
        return (T) v.findViewById(id);
    }

    public abstract void refreshView(List<T> list, int position);//初始化页面数据

    public abstract View initView();//加载页面ui
}
