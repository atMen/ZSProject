package customer.tcrj.com.zsproject.first;


import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import customer.tcrj.com.zsproject.R;
import customer.tcrj.com.zsproject.adapter.ExpandableAdapter;
import customer.tcrj.com.zsproject.base.BaseFragment;
import customer.tcrj.com.zsproject.expandablelistview.ChildHolder;
import customer.tcrj.com.zsproject.expandablelistview.DataInfo;
import customer.tcrj.com.zsproject.expandablelistview.DefaultAdapter;
import customer.tcrj.com.zsproject.expandablelistview.ParentHolder;


/**
 * Created by leict on 2018/3/22.
 */

public class XSLCFragment extends BaseFragment {



    @BindView(R.id.expanded_list)
    ExpandableListView expandedList;

    private List<DataInfo> pList = new ArrayList<>();
    private Map<DataInfo, List<DataInfo>> cMap = new HashMap<>();
    private ExpandableAdapter adapter;
    private int groupItemSelect = -1;

    @Override
    protected int setLayout() {
        return R.layout.xslc_fragment;
    }

    @Override
    protected void setView() {
        setdata();
        adapter = new ExpandableAdapter(mContext, pList, cMap);
        //设置adapter
        expandedList.setAdapter(adapter);
        //子条目点击事件
        expandedList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                if (groupItemSelect == -1 || groupItemSelect == groupPosition) {
                    setChildSelect(groupPosition, childPosition);
                } else {
                    setChildSelect(groupPosition, childPosition);

//                    DataInfo dataInfo2 = pList.get(groupItemSelect);
//                    List<DataInfo> dataInfos1 = cMap.get(dataInfo2);
//                    for (DataInfo info : dataInfos1) {
//                        info.childItemSelect = false;
//                    }
//                    cMap.put(dataInfo2, dataInfos1);
                }
                Toast.makeText(mContext, "groupPosition-->" + groupPosition + "childPosition-->" + childPosition, Toast.LENGTH_LONG).show();

                adapter.nodfiyMapData(cMap);
                groupItemSelect = groupPosition;
                return false;
            }
        });
        expandedList.setSelector(new ColorDrawable(Color.TRANSPARENT));
        //Group点击事件
        expandedList.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                DataInfo dataInfo = pList.get(groupPosition);
                Toast.makeText(mContext, dataInfo.itemTitle, Toast.LENGTH_LONG).show();
                return false;
            }
        });
        //设置group左边图标 设置为null代表将其隐藏掉
        expandedList.setGroupIndicator(null);
//        spreadListView();
    }


    @Override
    protected void setData() {


    }


    private void setdata() {

        for (int i = 0; i < 2; i++) {
            DataInfo info = new DataInfo();
            if(i == 0){
                info.itemTitle = "代理商管理";
            }else {
                info.itemTitle = "销售渠道管理";
            }

            pList.add(info);
        }

        for (DataInfo dataInfo : pList) {
            List<DataInfo> chList = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                DataInfo info = new DataInfo();
                info.itemTitle = "child" + i;
                info.childItemId = dataInfo.itemTitle + "child" + i;
                info.childItemSelect = false;
                chList.add(info);
            }
            cMap.put(dataInfo, chList);
        }
    }

    private void setChildSelect(int group, int child) {
        DataInfo dataInfo = pList.get(group);
        List<DataInfo> dataInfos = cMap.get(dataInfo);
        DataInfo dataInfo1 = dataInfos.get(child);
        String childItemId = dataInfo1.childItemId;
        for (DataInfo info : dataInfos) {
            String itemId = info.childItemId;
            if (childItemId.equals(itemId)) {
                info.childItemSelect = true;
            } else {
                info.childItemSelect = false;
            }
        }
        cMap.put(dataInfo, dataInfos);
    }

    /**
     * 默认展开第一组 要在setAdapter后调用
     */
    private void spreadListView() {
        int groupCount = adapter.getGroupCount();
        if (groupCount > 0) {
            expandedList.expandGroup(0);
        }
    }

    /**
     * 数据适配器
     */
    class ExpandableAdapter extends DefaultAdapter<DataInfo> {

        public ExpandableAdapter(Context context, List<DataInfo> parentList, Map<DataInfo, List<DataInfo>> map) {
            super(context, parentList, map);
        }

        @Override
        protected ParentHolder<DataInfo> getParentHolder() {
            return new PHolder();
        }

        @Override
        protected ChildHolder<DataInfo> getChildHolder() {
            return new CHolder();
        }
    }

    /**
     * group holder
     */
    class PHolder extends ParentHolder<DataInfo> {
        TextView tvGroup;
        ImageView ivIcon;

        @Override
        public void refreshView(List<DataInfo> list, int position, boolean isExpanded) {
            DataInfo dataInfo = list.get(position);
            tvGroup.setText(dataInfo.itemTitle);
            //判断group是否有展开
//            if (isExpanded) {
//                ivIcon.setImageResource(R.drawable.up);
//            } else {
//                ivIcon.setImageResource(R.drawable.down);
//            }
        }

        @Override
        public View initView() {
            View view = LayoutInflater.from(mContext).inflate(R.layout.expandable_group, null, false);
            tvGroup = findID(view, R.id.tv_group);
            ivIcon = findID(view, R.id.iv_icon);
            return view;
        }
    }

    /**
     * child holder
     */
    class CHolder extends ChildHolder<DataInfo> {
        TextView tvChild;
        ImageView ivCheck;

        @Override
        public void refreshView(List<DataInfo> list, int position) {
            DataInfo dataInfo = list.get(position);
            tvChild.setText(dataInfo.itemTitle);
            boolean childItemSelect = dataInfo.childItemSelect;
            if (childItemSelect) {
                ivCheck.setImageResource(R.drawable.check);
            } else {
                ivCheck.setImageResource(R.drawable.uncheck);
            }
        }

        @Override
        public View initView() {
            View view = LayoutInflater.from(mContext).inflate(R.layout.expandable_child, null, false);
            tvChild = findID(view, R.id.tv_child);
            ivCheck = findID(view, R.id.iv_check);
            return view;
        }
    }



//    @Override
//    public void onClick(View v) {
//
////        switch (v.getId()){
////
////            case R.id.ll_xxks:
////                toClass(mContext,XxkcActivity.class);//学习考试
////                break;
////            case R.id.ll_xxcx:
////                toClass(mContext,XxcxActivity.class);//信息查询
////                break;
////            case R.id.ll_hdjl:
////                toClass(mContext,hdjlnewActivity.class);//互动交流
////                break;
////            case R.id.ll_xxfb:
////                toClass(mContext,releaseActivity.class);//信息发布
////                break;
////
////        }
//
//    }

}
