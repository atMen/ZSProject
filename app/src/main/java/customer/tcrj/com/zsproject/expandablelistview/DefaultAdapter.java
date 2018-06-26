package customer.tcrj.com.zsproject.expandablelistview;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by miao on 2017/10/20.
 */

public abstract class DefaultAdapter<T> extends BaseExpandableListAdapter {
    private Context mContext;
    private List<T> parentLists;
    private Map<T, List<T>> mMap;

    public DefaultAdapter(Context context, List<T> parentList, Map<T, List<T>> map) {
        this.mContext = context;
        this.parentLists = new ArrayList<>();
        if (parentLists != null) {
            this.parentLists.addAll(parentList);
        }
        this.mMap = new HashMap<>();
        if (mMap != null) {
            this.mMap.putAll(map);
        }
    }

    /**
     * 刷新Group数据
     *
     * @param list
     */
    public void nodfiyParentData(List<T> list) {
        if (list != null) {
            this.parentLists.clear();
            this.parentLists.addAll(list);
        }
        notifyDataSetChanged();
    }

    /**
     * 刷新map数据
     *
     * @param map
     */
    public void nodfiyMapData(Map<T, List<T>> map) {
        if (map != null) {
            this.mMap.clear();
            this.mMap.putAll(map);
        }
        notifyDataSetChanged();
    }

    /**
     * 父条目的数量
     *
     * @return
     */
    @Override
    public int getGroupCount() {
        return parentLists.size();
    }

    /**
     * 每个父条目对应的子条目的数量
     *
     * @param groupPosition
     * @return
     */
    @Override
    public int getChildrenCount(int groupPosition) {
        T t = parentLists.get(groupPosition);
        List<T> ts = mMap.get(t);
        if (ts == null) {
            ts = new ArrayList<>();
        }
        return ts.size();
    }

    /**
     * 根据父条目的位置获取对应的对象
     *
     * @param groupPosition
     * @return
     */
    @Override
    public Object getGroup(int groupPosition) {
        return parentLists.get(groupPosition);
    }

    /**
     * 根据子条目的位置获取对应的对象
     *
     * @param groupPosition
     * @param childPosition
     * @return
     */
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        T t = parentLists.get(groupPosition);
        List<T> ts = mMap.get(t);
        if (ts == null) {
            ts = new ArrayList<>();
        }
        return ts.get(childPosition);
    }

    /**
     * 父位置
     *
     * @param groupPosition
     * @return
     */
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    /**
     * 子位置
     *
     * @param groupPosition
     * @param childPosition
     * @return
     */
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    /**
     * 父布局
     *
     * @param groupPosition
     * @param isExpanded
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ParentHolder<T> holder = null;
        if (convertView == null) {
            holder = getParentHolder();
        } else {
            holder = (ParentHolder<T>) convertView.getTag();
        }
        //isExpanded group是否有展开
        if (groupPosition < parentLists.size()) {
            holder.refreshView(parentLists, groupPosition, isExpanded);
        }
        return holder.getConvertView();
    }

    protected abstract ParentHolder<T> getParentHolder();

    /**
     * 子布局
     *
     * @param groupPosition
     * @param childPosition
     * @param isLastChild
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildHolder<T> holder = null;
        if (convertView == null) {
            holder = getChildHolder();
        } else {
            holder = (ChildHolder<T>) convertView.getTag();
        }
        T t = parentLists.get(groupPosition);
        List<T> ts = mMap.get(t);
        holder.refreshView(ts, childPosition);
        return holder.getConvertView();
    }

    protected abstract ChildHolder<T> getChildHolder();

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        //返回true才会触发setOnChildClickListener子条目事件
        return true;
    }
}
