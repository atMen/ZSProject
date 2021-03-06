package customer.tcrj.com.zsproject.resources;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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
import customer.tcrj.com.zsproject.adapter.cpInfoAdapter;
import customer.tcrj.com.zsproject.adapter.resourcesInfoAdapter;
import customer.tcrj.com.zsproject.base.BaseFragment;
import customer.tcrj.com.zsproject.bean.cpInfo;
import customer.tcrj.com.zsproject.bean.resourcesInfo;
import customer.tcrj.com.zsproject.first.AddResourcesActivity;
import customer.tcrj.com.zsproject.first.CPListInfoActivity;
import customer.tcrj.com.zsproject.net.ApiConstants;
import customer.tcrj.com.zsproject.videoview.VideoViewActivity;
import customer.tcrj.com.zsproject.widget.CustomLoadMoreView;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;


/**
 * Created by leict on 2018/3/22.
 */

public class NewResourcesFragment extends BaseFragment implements BaseQuickAdapter.OnItemClickListener {

    private static final int RESOURCESCODE = 001;
    private static final int RESULT_OK = -1;
    @BindView(R.id.tv_search)
    TextView tv_search;
    @BindView(R.id.tv_add)
    TextView tv_add;
    @BindView(R.id.edt_search_result)
    EditText edt_search_result;
    @BindView(R.id.recycler_view)
    public RecyclerView mRecyclerView;
    @BindView(R.id.mPtrFrameLayout)
    PtrFrameLayout mPtrFrameLayout;

    private MyOkHttp mMyOkhttp;
    private String token;


    private int pageNum = 1;
    private boolean canPull = true;

    private resourcesInfoAdapter detailAdapter;
    private List<resourcesInfo.DataBean> beanList;

    @Override
    protected int setLayout() {
        return R.layout.new_resources_fragment;
    }

    @Override
    protected void setView() {
        mMyOkhttp = MyApp.getInstance().getMyOkHttp();
        token = ACache.get(mContext).getAsString("token");
        initview();
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
                getData(pageNum,"");

            }
        });
        beanList = new ArrayList<>();

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setAdapter(detailAdapter = new resourcesInfoAdapter(beanList, mContext));
//        detailAdapter.setPreLoadNumber(1);
        detailAdapter.setLoadMoreView(new CustomLoadMoreView());
        detailAdapter.setEnableLoadMore(true);
        detailAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        detailAdapter.setOnItemClickListener(this);
        detailAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                Log.e("TAG","点击重新加载数据");
                getData(pageNum,"");
            }
        }, mRecyclerView);
    }


    //获取网络数据
    private void getData(final int num,final String s) {

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("page", num+"");
            jsonObject.put("size", "30");
            jsonObject.put("token", token);
//            jsonObject.put("cpmc", s);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mMyOkhttp.post()
                .url(ApiConstants.qyzylistinfoApi)
                .jsonParams(jsonObject.toString())
                .enqueue(new GsonResponseHandler<resourcesInfo>() {
                    @Override
                    public void onFailure(int statusCode, String error_msg) {
                        hideLoadingDialog();


                        if(num > 1){
                            loadMoreData(null,true);
                        }else{
                            loadData(null,true);

                        }
                    }

                    @Override
                    public void onSuccess(int statusCode, resourcesInfo response) {
                        hideLoadingDialog();

                        if(response.getErrorcode().equals("9999")){

                            if(num > 1){//上拉加载
                                loadMoreData(response,false);
                            }else{//下拉刷新
                                loadData(response.getData(),false);
                            }

                        }else if(response.getErrorcode().equals("204")){
                            Utils.toLogin(mContext);
                        }
                    }
                });
    }

    //上拉加载更多数据
    private void loadMoreData(resourcesInfo response,boolean isError) {

        if (response == null) {
            if(isError){
                detailAdapter.loadMoreFail();

                Toast.makeText(mContext, getResources().getString(R.string.data_failed), Toast.LENGTH_SHORT).show();
            }else{
                detailAdapter.loadMoreFail();
            }

        } else {

            List<resourcesInfo.DataBean> data = response.getData();
            if(data == null || data.size() <= 0){//没有更多数据
                detailAdapter.loadMoreFail();
            }else{
                List<resourcesInfo.DataBean> content = response.getData();
                pageNum++;
                detailAdapter.addData(content);
                detailAdapter.loadMoreComplete();
            }

        }

    }

    //下拉刷新
    private void loadData(List<resourcesInfo.DataBean> response,boolean isError) {

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
                    }

                    Log.e("TAG","测试："+(linearLayoutManager.findLastCompletelyVisibleItemPosition() + 1));
                }
            }, 1000);


        }
    }

    @Override
    protected void setData() {
        getData(1,"");
    }

    @OnClick({R.id.tv_search,R.id.tv_add})
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_search:
                showLoadingDialog("正在搜索..");
                String s = edt_search_result.getText().toString();
                getData(1,s);

                break;
            case R.id.tv_add:

                toClass(mContext,AddResourcesActivity.class,null,RESOURCESCODE);
                break;

            default:
                break;

        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        resourcesInfo.DataBean item = (resourcesInfo.DataBean) adapter.getItem(position);
        String cllx = item.getCllx();
        Bundle bundle = new Bundle();
        bundle.putSerializable("qyzyinfo",item);
        if("10402".equals(cllx)){
            toClass(mContext,VideoViewActivity.class,bundle,RESOURCESCODE);
        }else if("10401".equals(cllx)){
            toClass(mContext,ResourcesInfoActivity.class,bundle,RESOURCESCODE);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("TAG","onActivityResult");
        if (resultCode == RESULT_OK) {

            switch (requestCode) {
                case RESOURCESCODE:
                    Log.e("TAG","onActivityResult  getData");
                    pageNum = 1;
                    getData(1,"");
                    break;

                default:
                    break;
            }

        }
    }

}
