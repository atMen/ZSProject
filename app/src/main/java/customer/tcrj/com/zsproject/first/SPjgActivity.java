package customer.tcrj.com.zsproject.first;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import butterknife.OnClick;
import customer.tcrj.com.zsproject.MyApp;
import customer.tcrj.com.zsproject.R;
import customer.tcrj.com.zsproject.Utils.ACache;
import customer.tcrj.com.zsproject.Utils.Utils;
import customer.tcrj.com.zsproject.adapter.OnItemClickLitener;
import customer.tcrj.com.zsproject.adapter.cpInfoAdapter;
import customer.tcrj.com.zsproject.adapter.spjgAdapter;
import customer.tcrj.com.zsproject.adapter.zssqAdapter;
import customer.tcrj.com.zsproject.base.BaseActivity;
import customer.tcrj.com.zsproject.bean.cpInfo;
import customer.tcrj.com.zsproject.net.ApiConstants;
import customer.tcrj.com.zsproject.widget.CustomLoadMoreView;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

public class SPjgActivity extends BaseActivity implements BaseQuickAdapter.OnItemClickListener {




    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.mPtrFrameLayout)
    PtrFrameLayout mPtrFrameLayout;

    @BindView(R.id.btnback)
    ImageView btnback;
    @BindView(R.id.txtTitle)
    TextView txtTitle;
    @BindView(R.id.layout_gg)
    LinearLayout layout_gg;

    @BindView(R.id.tv_work_naturejob)
    TextView tv_work_naturejob;
    @BindView(R.id.tv_pp)
    TextView tv_pp;

    private int pageNum = 1;
    private boolean canPull = true;

//    private List<cpInfo.DataBean.ContentBean> beanList;

    private List<String> beanList;
    private spjgAdapter detailAdapter;

    private MyOkHttp mMyOkhttp;
    private String token;


    @Override
    protected int setLayout() {
        return R.layout.activity_spjg;
    }


    @Override
    protected void setView() {
        mMyOkhttp = MyApp.getInstance().getMyOkHttp();
        token = ACache.get(this).getAsString("token");
//        initview();
    }

//    private void initview() {
//        txtTitle.setText("审批结果查询");
//        layout_gg.setVisibility(View.GONE);
//        tv_pp.setText("审批状态");
//        tv_work_naturejob.setText("产品名称");
//
//
//        mPtrFrameLayout.disableWhenHorizontalMove(true);
//        mPtrFrameLayout.setPtrHandler(new PtrHandler() {
//            @Override
//            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
//
//                if(!canPull){
//                    return false;
//                }
//                return PtrDefaultHandler.checkContentCanBePulledDown(frame, mRecyclerView, header);
//            }
//
//            @Override
//            public void onRefreshBegin(PtrFrameLayout frame) {
////                pageNum = 1;
////                getData(pageNum);
//
//            }
//        });
//        beanList = new ArrayList<>();
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(SPjgActivity.this));
////        mRecyclerView.setItemAnimator(new SlideInOutRightItemAnimator(mRecyclerView));
//        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
//        mRecyclerView.setAdapter(detailAdapter = new spjgAdapter(beanList, SPjgActivity.this));
//        detailAdapter.setPreLoadNumber(1);
//        detailAdapter.setLoadMoreView(new CustomLoadMoreView());
//        detailAdapter.setEnableLoadMore(true);
//        detailAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
//        detailAdapter.setOnItemClickListener(this);
//
//        detailAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
//            @Override
//            public void onLoadMoreRequested() {
//                Log.e("TAG","点击重新加载数据");
//                getData(1);
//            }
//        }, mRecyclerView);
//
////        showSuccess();
//    }


    @Override
    protected void setData() {
    }


//    //获取网络数据
//    private void getData(final int num) {
//
//        JSONObject jsonObject = new JSONObject();
//
//        try {
//            jsonObject.put("page", num+"");
//            jsonObject.put("size", "10");
//            jsonObject.put("token", token);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        mMyOkhttp.post()
//                .url(ApiConstants.cplistinfoApi)
//                .jsonParams(jsonObject.toString())
//                .enqueue(new GsonResponseHandler<cpInfo>() {
//                    @Override
//                    public void onFailure(int statusCode, String error_msg) {
//
//
//
//                        if(num > 1){
//                            loadMoreData(null,true);
//                        }else{
//                            loadData(null,true);
//
//                        }
//                    }
//
//                    @Override
//                    public void onSuccess(int statusCode, cpInfo response) {
//
//                        if(response.getErrorcode().equals("9999")){
//
//                            if(num > 1){//上拉加载
//                                loadMoreData(response,false);
//                            }else{//下拉刷新
//                                loadData(response.getData().getContent(),false);
//                            }
//
//                        }else if(response.getErrorcode().equals("204")){
//
//                            Utils.toLogin(SPjgActivity.this);
//                        }
//
//
//                    }
//                });
//
//    }

//    //上拉加载更多数据
//    private void loadMoreData(cpInfo response,boolean isError) {
//
//        if (response == null) {
//            if(isError){
//                detailAdapter.loadMoreFail();
//                Toast.makeText(this, getResources().getString(R.string.data_failed), Toast.LENGTH_SHORT).show();
//            }else{
//                detailAdapter.loadMoreFail();
//            }
//
//        } else {
//
//            if(pageNum > response.getData().getTotalPages()){//没有更多数据
//                detailAdapter.loadMoreFail();
//            }else{
//                List<cpInfo.DataBean.ContentBean> content = response.getData().getContent();
//                pageNum++;
//                detailAdapter.addData(content);
//                detailAdapter.loadMoreComplete();
//            }
//
//        }
//
//    }
//
//    //下拉刷新
//    private void loadData(List<cpInfo.DataBean.ContentBean> response,boolean isError) {
//
//        if (response == null  || response.size() <= 0) {
//            if(mPtrFrameLayout != null){
//                mPtrFrameLayout.refreshComplete();
//            }
//            if(isError){
//                showFaild();
//            }else{
//                showEmptyView();
//            }
//            canPull = false;
//
//        } else {
//
//            canPull = true;
//            pageNum++;
//            detailAdapter.setNewData(response);
//            mPtrFrameLayout.refreshComplete();
//            showSuccess();
//            disableLoadMoreIfNotFullPage(mRecyclerView,response.size());
//        }
//    }
//
//    public void disableLoadMoreIfNotFullPage(RecyclerView recyclerView, final int size) {
//        detailAdapter.setEnableLoadMore(false);
//        if (recyclerView == null) return;
//        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
//        if (manager == null) return;
//        if (manager instanceof LinearLayoutManager) {
//            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) manager;
//
//            recyclerView.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//
//                    //要等到列表显示出来才可以去获取：findLastCompletelyVisibleItemPosition
//                    if ((linearLayoutManager.findLastCompletelyVisibleItemPosition() + 1) != size) {
//                        detailAdapter.setEnableLoadMore(true);
//                    }
//
//                    Log.e("TAG","测试："+(linearLayoutManager.findLastCompletelyVisibleItemPosition() + 1));
//                }
//            }, 1000);
//
//
//        }
//    }

    @OnClick({R.id.btnback})
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.btnback:
                finish();
                break;



        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

    }

}
