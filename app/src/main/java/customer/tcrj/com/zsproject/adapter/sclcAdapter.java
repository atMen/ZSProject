package customer.tcrj.com.zsproject.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import org.sufficientlysecure.htmltextview.HtmlHttpImageGetter;
import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.util.List;

import customer.tcrj.com.zsproject.R;
import customer.tcrj.com.zsproject.bean.cpSCLCinfo;


/**
 * desc: .
 * author: Will .
 * date: 2017/9/27 .
 */
public class sclcAdapter extends BaseQuickAdapter<cpSCLCinfo.DataBean.ContentBean, BaseViewHolder>{

    public static final int LAST_POSITION = -1;
    private Context mContext;
    private RecyclerView mRecyclerView;
    String status;


    public sclcAdapter(@Nullable List<cpSCLCinfo.DataBean.ContentBean> data, Context context,
                       RecyclerView mRecyclerView, String status) {
        super(R.layout.sclc_item, data);
        this.mContext = context;
        this.mRecyclerView = mRecyclerView;
        this.status = status;
    }


    @Override
    protected void convert(final BaseViewHolder helper, final cpSCLCinfo.DataBean.ContentBean item) {
        helper.setText(R.id.cpname, "名称："+item.getName());
        helper.setText(R.id.cptime, item.getSort());



        TextView remo = helper.getView(R.id.simple_remo);
        TextView xg = helper.getView(R.id.simple_xg);


        Log.e("TAG","html:"+item.getDescription());
        HtmlTextView htmlTextView = helper.getView(R.id.html_text);
        htmlTextView.setHtml(item.getDescription(),
                new HtmlHttpImageGetter(htmlTextView));

        //        WebView webView = helper.getView(R.id.simple_content);
//        webView.getSettings().setJavaScriptEnabled(true);
//        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
//        webView.loadData(item.getDescription(), "text/html; charset=UTF-8", null);

        if(!"1".equals(status)){
            remo.setVisibility(View.GONE);
            xg.setVisibility(View.GONE);
        }



        xg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onxgItemClickListener != null){
                    onxgItemClickListener.OnXGItemClick(item,helper.getPosition());
                }
            }
        });

        remo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("TAG","position:"+helper.getPosition()+"");

//                remove(helper.getPosition());

                if(onItemClickListener != null){
                    onItemClickListener.OnItemClick(item,helper.getPosition());
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public void add(cpSCLCinfo.DataBean.ContentBean s,int position) {
        Log.e("add","s:"+s);
        position = position == LAST_POSITION ? getItemCount()  : position;
        mData.add(position,s);

        notifyItemInserted(position);

//        notifyItemRangeInserted(position,1);
//        mRecyclerView.smoothScrollToPosition(position);


        //如果在第一项添加模拟数据需要调用 scrollToPosition（0）把列表移动到顶端（可选）
        mRecyclerView.scrollToPosition(position);
    }

    public void remove(int position){
        Log.e("remove","position:"+position);
        if (position == LAST_POSITION && getItemCount()>0)
            position = getItemCount() -1 ;

        if (position > LAST_POSITION && position < getItemCount()) {
            mData.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void updata(cpSCLCinfo.DataBean.ContentBean s, int position) {
        Log.e("TAG","描述:"+s.getDescription()+"position:"+position);
        mData.set(position,s);
        notifyItemRangeChanged(position,1);
    }


    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {  //定义接口，实现Recyclerview点击事件
        void OnItemClick(cpSCLCinfo.DataBean.ContentBean position, int helperPosition);
    }


    public void setOnItemRemoveClickListener(OnItemClickListener onItemClickListener) {//实现点击
        this.onItemClickListener = onItemClickListener;
    }



    private OnxgItemClickListener onxgItemClickListener;

    public interface OnxgItemClickListener {  //定义接口，实现Recyclerview点击事件
        void OnXGItemClick(cpSCLCinfo.DataBean.ContentBean position, int helperPosition);
    }


    public void setOnItemXGClickListener(OnxgItemClickListener onItemClickListener) {   //实现点击
        this.onxgItemClickListener = onItemClickListener;
    }

}
