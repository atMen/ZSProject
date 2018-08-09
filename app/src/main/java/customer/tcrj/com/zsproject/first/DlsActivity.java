package customer.tcrj.com.zsproject.first;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.tsy.sdk.myokhttp.MyOkHttp;
import com.tsy.sdk.myokhttp.response.GsonResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import customer.tcrj.com.zsproject.MyApp;
import customer.tcrj.com.zsproject.R;
import customer.tcrj.com.zsproject.Utils.ACache;
import customer.tcrj.com.zsproject.Utils.Utils;
import customer.tcrj.com.zsproject.adapter.dlsAdapter;
import customer.tcrj.com.zsproject.adapter.zssqAdapter;
import customer.tcrj.com.zsproject.base.BaseActivity;
import customer.tcrj.com.zsproject.bean.cpInfo;
import customer.tcrj.com.zsproject.bean.dlsInfo;
import customer.tcrj.com.zsproject.net.ApiConstants;
import customer.tcrj.com.zsproject.widget.CustomLoadMoreView;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

public class DlsActivity extends BaseActivity implements View.OnClickListener, BaseQuickAdapter.OnItemClickListener {


    @BindView(R.id.btn_add)
    Button btn_add;
    @BindView(R.id.txtTitle)
    TextView txtTitle;
    @BindView(R.id.btnback)
    ImageView btnback;
    @BindView(R.id.recycler_view)
    public RecyclerView mRecyclerView;
    @BindView(R.id.mPtrFrameLayout)
    PtrFrameLayout mPtrFrameLayout;

    private int pageNum = 1;



    private boolean canPull = true;
    private List<dlsInfo.DataBean.ContentBean> beanList;
    private dlsAdapter detailAdapter;
    private MyOkHttp mMyOkhttp;
    private String token;


    @Override
    protected int setLayout() {
        return R.layout.activity_dls;
    }
    @Override
    protected void setData() {
        getData(1);
    }

    @Override
    protected void setView() {
        txtTitle.setText("代理商信息");
        mMyOkhttp = MyApp.getInstance().getMyOkHttp();
        token = ACache.get(this).getAsString("token");
        setListener();
        initview();
    }

    private void setListener() {
        btnback.setOnClickListener(this);
        btn_add.setOnClickListener(this);
    }


    private void initview() {

        mPtrFrameLayout.disableWhenHorizontalMove(true);
        mPtrFrameLayout.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {

                if(!canPull){
                    return false;
                }
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, mRecyclerView, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                pageNum = 1;
                getData(pageNum);

            }
        });
        beanList = new ArrayList<>();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(DlsActivity.this));

        mRecyclerView.setAdapter(detailAdapter = new dlsAdapter(beanList, DlsActivity.this));
        detailAdapter.setSelectItem(0);
        detailAdapter.setLoadMoreView(new CustomLoadMoreView());
        detailAdapter.setEnableLoadMore(true);
        detailAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        detailAdapter.setOnItemClickListener(this);
        detailAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                Log.e("TAG","点击重新加载数据");
                getData(pageNum);
            }
        }, mRecyclerView);

    }

    //获取网络数据
    private void getData(final int num) {

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("page", num+"");
            jsonObject.put("size", "30");
            jsonObject.put("token", token);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mMyOkhttp.post()
                .url(ApiConstants.dlsApi)
                .jsonParams(jsonObject.toString())
                .enqueue(new GsonResponseHandler<dlsInfo>() {
                    @Override
                    public void onFailure(int statusCode, String error_msg) {

                        Log.e("TAG","error_msg"+error_msg);

                        if(num > 1){
                            loadMoreData(null,true);
                        }else{
                            loadData(null,true);

                        }
                    }

                    @Override
                    public void onSuccess(int statusCode, dlsInfo response) {

                        if(response.getErrorcode().equals("9999")){

                            if(num > 1){//上拉加载
                                Log.e("TAG","上拉加载更多数据");
                                loadMoreData(response,false);
                            }else{//下拉刷新
                                loadData(response.getData().getContent(),false);
                            }

                        }else if(response.getErrorcode().equals("204")){

                            Utils.toLogin(DlsActivity.this);
                        }


                    }
                });

    }

    //上拉加载更多数据
    private void loadMoreData(dlsInfo response,boolean isError) {
        Log.e("TAG","loadMoreData");
        if (response == null) {
            if(isError){
                detailAdapter.loadMoreFail();
                Toast.makeText(this, getResources().getString(R.string.data_failed), Toast.LENGTH_SHORT).show();
            }else{
                detailAdapter.loadMoreFail();
            }

        } else {
            Log.e("TAG","response.getData().getTotalPages()："+response.getData().getTotalPages());
            if(pageNum > response.getData().getTotalPages()){//没有更多数据
                detailAdapter.loadMoreFail();
            }else{
                List<dlsInfo.DataBean.ContentBean> content = response.getData().getContent();
                pageNum++;
                detailAdapter.addData(content);
                detailAdapter.loadMoreComplete();
            }

        }

    }

    //下拉刷新
    private void loadData(List<dlsInfo.DataBean.ContentBean> response,boolean isError) {

        if (response == null  || response.size() <= 0) {
            if(mPtrFrameLayout != null){
                mPtrFrameLayout.refreshComplete();
            }
            if(isError){
                showFaild();
            }else{
                showEmptyView();
            }
            canPull = false;

        } else {

            dlsmc = response.get(0).getDlsmc();
            dlsid = response.get(0).getId();
            canPull = true;
            pageNum++;
            detailAdapter.setNewData(response);
            mPtrFrameLayout.refreshComplete();
            showSuccess();
            disableLoadMoreIfNotFullPage(mRecyclerView,response.size());
        }
    }

    public void disableLoadMoreIfNotFullPage(RecyclerView recyclerView, final int size) {
        detailAdapter.setEnableLoadMore(false);
        if (recyclerView == null) return;
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager == null) return;
        if (manager instanceof LinearLayoutManager) {
            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) manager;

            recyclerView.postDelayed(new Runnable() {
                @Override
                public void run() {

                    //要等到列表显示出来才可以去获取：findLastCompletelyVisibleItemPosition
                    if ((linearLayoutManager.findLastCompletelyVisibleItemPosition() + 1) != size) {
                        detailAdapter.setEnableLoadMore(true);
                        Log.e("TAG","setEnableLoadMore(true)");
                    }

                    Log.e("TAG","测试："+(linearLayoutManager.findLastCompletelyVisibleItemPosition() + 1));
                }
            }, 1000);


        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnback:
                finish();
                break;
            case R.id.btn_add:
                Intent i = new Intent();
                i.putExtra("dlsmc",dlsmc);
                i.putExtra("dlsid",dlsid);
                setResult(3, i);
                finish();
                break;
            default:
                break;



        }
    }

    private String dlsmc;
    private String dlsid;
    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

        dlsInfo.DataBean.ContentBean bean = (dlsInfo.DataBean.ContentBean) adapter.getItem(position);
        dlsmc = bean.getDlsmc();
        dlsid = bean.getId();
        detailAdapter.setSelectItem(position);
        detailAdapter.notifyDataSetChanged();
    }

}
