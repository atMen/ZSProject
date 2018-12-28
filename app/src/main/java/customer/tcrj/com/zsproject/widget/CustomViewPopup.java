package customer.tcrj.com.zsproject.widget;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;

import java.util.ArrayList;

import customer.tcrj.com.zsproject.R;
import customer.tcrj.com.zsproject.adapter.PopupAdapter;

/**
 * Created by leict on 2018/10/31.
 */

public class CustomViewPopup extends PopupWindow {

    private CustomInterface custominterface;
    private Context mContext;
    private final PopupAdapter popupAdapter;
    private final View view;

    public CustomViewPopup(final Context context, CustomInterface custominterface){

        /**
         * 注意：我们的接口同时作为成员变量传入，因为我们用于监听子Item的数据监听
         * */
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.fragment_layout, null);
        mContext=context;

        this.setContentView(view);
        //自定义基础，设置我们显示控件的宽，高，焦点，点击外部关闭PopupWindow操作
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        //更新试图
        this.update();
        //设置背景
        ColorDrawable colorDrawable = new ColorDrawable(0000000000);
        this.setBackgroundDrawable(colorDrawable);

        ListView mList = (ListView) view.findViewById(R.id.lv_popup_view);

        //数据填充
        ArrayList<String> Lists = new ArrayList<>();
        Lists.add("基础底图");
        Lists.add("延安新区智慧道路网");
        Lists.add("承载网线路光交布置图");
        Lists.add("智慧安防点位布置图");
        Lists.add("承载网");
        Lists.add("智慧安防");
        Lists.add("智慧交通");

        //绑定适配器，设置子条目监听
        popupAdapter = new PopupAdapter(mContext,Lists);
        this.custominterface=custominterface;
        mList.setAdapter(popupAdapter);
        mList.setOnItemClickListener(onItemClickListener);
    }

    //监听子Item操作 -数据记录，关闭PopupWindow
    public AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener(){

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            custominterface.setData(popupAdapter.getItem(position));
            dismiss();
        }
    };

}
