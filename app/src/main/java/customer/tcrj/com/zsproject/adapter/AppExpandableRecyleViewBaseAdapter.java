package customer.tcrj.com.zsproject.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public abstract class AppExpandableRecyleViewBaseAdapter<T, A extends AppExpandableRecyleViewBaseAdapter.ViewHolder> extends RecyclerView.Adapter<A> {
    private List<T> list;
    Context context;
    private int position = -1;

    public AppExpandableRecyleViewBaseAdapter(Context context, List<T> t) {
        list = initList(context, t);
        this.context = context;
    }

    /**
     * 获取对应的数据
     * @return  返回数据
     */
    public List<T> getList() {
        return list;
    }

    /**
     * 初始化数据
     * @param context  上下文
     * @param t  数据源
     * @return  返回的数据
     */
    public List<T> initList(Context context, List<T> t) {

        return t;
    }

    /**
     * 得到上下文
     * @return 返回对应的上下文
     */
    protected Context getContext() {
        return context;
    }

    /**
     * 添加数据
     * @param t  对应的数据
     */
    public void addData(ArrayList<T> t) {
        list.addAll(t);
        notifyDataSetChanged();
    }

    /**
     * 设置数据
     * @param t
     */
    public void setData(List<T> t) {
        list = t;
        notifyDataSetChanged();
    }

    /**
     * 清楚所有的数据
     */
    public void clearAll() {
        list.clear();
        list = null;
        notifyDataSetChanged();
    }


    @Override
    public A onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(getContext()).inflate(getLayout(), null);
        LinearLayout linearLayout=new LinearLayout(getContext());
        ViewGroup.LayoutParams layoutParams=new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        linearLayout.setLayoutParams(layoutParams);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        linearLayout.addView(view);
        A a = getViewHolder(linearLayout);
        initKongjian(a,linearLayout);
        return a;
    }

    @Override
    public void onBindViewHolder(A holder, int position) {
         T t = list.get(position);
        holder.setPostion(position);
        /*
          此处为解决多个view时，view状态记录错误的
         */
        holder.setIsRecyclable(false);

        initChildWidget(holder,holder.getView());
        bindView(holder, t);

    }

    protected abstract void initChildWidget(A holder, View view);

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        if (list != null) return list.size();
        return 0;
    }

    public abstract A getViewHolder(View view);


    protected boolean isUseViewCache() {
        return true;
    }

    protected abstract int getLayout();

    protected abstract void bindView(A a, T t);

    public abstract void initKongjian(A a, View view);


    public int getPosition() {
        return position;
    }


    public abstract class ViewHolder extends RecyclerView.ViewHolder {
        private int postion;
        private View view;
        A a;

        public void setPostion(int postion) {
            this.postion = postion;
        }

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;

        }

        public int getPostion() {
            return postion;
        }

        public View getView() {
            return view;
        }

    }


    protected <N extends View> N findviewById(View view, int id) {

        return (N) view.findViewById(id);
    }

}
