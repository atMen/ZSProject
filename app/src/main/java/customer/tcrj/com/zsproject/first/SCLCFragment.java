package customer.tcrj.com.zsproject.first;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
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
import customer.tcrj.com.zsproject.MainActivity;
import customer.tcrj.com.zsproject.MyApp;
import customer.tcrj.com.zsproject.R;
import customer.tcrj.com.zsproject.Utils.ACache;
import customer.tcrj.com.zsproject.Utils.Utils;
import customer.tcrj.com.zsproject.adapter.sclcAdapter;
import customer.tcrj.com.zsproject.base.BaseFragment;
import customer.tcrj.com.zsproject.bean.cpInfo;
import customer.tcrj.com.zsproject.bean.cpSCLCinfo;
import customer.tcrj.com.zsproject.bean.loginInfo;
import customer.tcrj.com.zsproject.dialog.SweetAlertDialog;
import customer.tcrj.com.zsproject.net.ApiConstants;
import customer.tcrj.com.zsproject.widget.CustomLoadMoreView;
import customer.tcrj.com.zsproject.widget.SlideInOutRightItemAnimator;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;


/**
 * Created by leict on 2018/3/22.
 */

@SuppressLint("ValidFragment")
public class SCLCFragment extends BaseFragment implements BaseQuickAdapter.OnItemClickListener, sclcAdapter.OnItemClickListener, sclcAdapter.OnxgItemClickListener {

    private final static int REQUESTCODE = 1; // 返回的结果码

    @BindView(R.id.btn_add)
    Button btn_add;
    @BindView(R.id.btn_tj)
    Button btn_tj;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.mPtrFrameLayout)
    PtrFrameLayout mPtrFrameLayout;

    @BindView(R.id.zwsj)
    LinearLayout zwsj;
    @BindView(R.id.ll_listview)
    LinearLayout ll_listview;

    private boolean canPull = true;
    private List<cpSCLCinfo.DataBean.ContentBean> beanList;
    private sclcAdapter detailAdapter;

    private int pageNum = 1;
    private MyOkHttp mMyOkhttp;
    private String token;
    String productId;

    @SuppressLint("ValidFragment")
    public SCLCFragment(String productId) {
        this.productId = productId;
    }

    @Override
    protected int setLayout() {
        return R.layout.sclc_fragment;
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
                getData(pageNum);

            }
        });
        beanList = new ArrayList<>();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
//        mRecyclerView.setItemAnimator(new SlideInOutRightItemAnimator(mRecyclerView));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(detailAdapter = new sclcAdapter(beanList, mContext,mRecyclerView));
        detailAdapter.setPreLoadNumber(1);
        detailAdapter.setLoadMoreView(new CustomLoadMoreView());
        detailAdapter.setEnableLoadMore(false);
        detailAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        detailAdapter.setOnItemClickListener(this);
        detailAdapter.setOnItemRemoveClickListener(this);
        detailAdapter.setOnItemXGClickListener(this);
//        detailAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
//            @Override
//            public void onLoadMoreRequested() {
//                Log.e("TAG","点击重新加载数据");
//                getData(pageNum);
//            }
//        }, mRecyclerView);

    }

    //获取网络数据
    private void getData(final int num) {

        Log.e("TAG","productId:"+productId);

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("page", num+"");
            jsonObject.put("size", "30");
            jsonObject.put("token", token);
            jsonObject.put("productId", productId);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        mMyOkhttp.post()
                .url(ApiConstants.cpjglistinfoApi)
                .jsonParams(jsonObject.toString())
                .enqueue(new GsonResponseHandler<cpSCLCinfo>() {
                    @Override
                    public void onFailure(int statusCode, String error_msg) {



                        if(num > 1){
//                            loadMoreData(null,true);
                        }else{
                            loadData(null,true);

                        }
                    }

                    @Override
                    public void onSuccess(int statusCode, cpSCLCinfo response) {

                        if(response.getErrorcode().equals("9999")){

                            if(num > 1){//上拉加载
//                                loadMoreData(response,false);
                            }else{//下拉刷新
                                loadData(response.getData().getContent(),false);
                            }

                        }else if(response.getErrorcode().equals("204")){

                            Utils.toLogin(mContext);
                        }


                    }
                });

    }

//    //上拉加载更多数据
//    private void loadMoreData(cpSCLCinfo response,boolean isError) {
//
//        if (response == null) {
//            if(isError){
//                detailAdapter.loadMoreFail();
//                Toast.makeText(mContext, getResources().getString(R.string.data_failed), Toast.LENGTH_SHORT).show();
//            }else{
//                detailAdapter.loadMoreFail();
//            }
//
//        } else {
//
//            if(pageNum > response.getData().getTotalPages()){//没有更多数据
//                detailAdapter.loadMoreFail();
//                Log.e("TAG","loadMoreFail");
//            }else{
//                List<cpSCLCinfo.DataBean.ContentBean> content = response.getData().getContent();
//                pageNum++;
//                detailAdapter.addData(content);
//                detailAdapter.loadMoreComplete();
//            }
//
//        }
//
//    }

    //下拉刷新
    private void loadData(List<cpSCLCinfo.DataBean.ContentBean> response,boolean isError) {

        if (response == null  || response.size() <= 0) {



            if(mPtrFrameLayout != null){
                mPtrFrameLayout.refreshComplete();
            }
            if(isError){
                showFaild();
            }else{
//                showEmptyView();

                showSuccess();
                zwsj.setVisibility(View.VISIBLE);
                ll_listview.setVisibility(View.GONE);

            }
//            canPull = false;

        } else {

            zwsj.setVisibility(View.GONE);
            ll_listview.setVisibility(View.VISIBLE);

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
                        Log.e("TAG","setEnableLoadMore(true)："+size);
                    }

                    Log.e("TAG","测试："+(linearLayoutManager.findLastCompletelyVisibleItemPosition() + 1));
                }
            }, 1000);


        }
    }


    @Override
    protected void setData() {
        getData(1);
    }

    @OnClick({R.id.btn_add,R.id.btn_tj})
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_add:
                Bundle bundle = new Bundle();
                bundle.putString("productId",productId);
                toClass(mContext,SCLCwriteActivity.class,bundle,REQUESTCODE);
                break;

            case R.id.btn_tj:

                toClass(mContext,DialogmsgActivity.class);
                break;


        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // RESULT_OK，判断另外一个activity已经结束数据输入功能，Standard activity result:
                // operation succeeded. 默认值是-1
               if (resultCode == 2) {
                   if (requestCode == REQUESTCODE) {
                       cpSCLCinfo.DataBean.ContentBean contentBean = (cpSCLCinfo.DataBean.ContentBean) data.getSerializableExtra("three");
                       Log.e("TAG","信息回调");

                       zwsj.setVisibility(View.GONE);
                       ll_listview.setVisibility(View.VISIBLE);
                       add(contentBean);

                   }
               }else if(resultCode == 3){
                   if (requestCode == REQUESTCODE) {
                       cpSCLCinfo.DataBean.ContentBean contentBean = (cpSCLCinfo.DataBean.ContentBean) data.getSerializableExtra("three");
                       Log.e("TAG","修改信息回调");

                       updata(contentBean);

                   }
               }
    }

    private void updata(cpSCLCinfo.DataBean.ContentBean contentBean) {
        detailAdapter.updata(contentBean,num);

    }


    private void add(cpSCLCinfo.DataBean.ContentBean s){
    detailAdapter.add(s,sclcAdapter.LAST_POSITION);//生产流程添加
    }
    private void remove(){
    detailAdapter.remove(sclcAdapter.LAST_POSITION);//删除

    }

    @Override
    public void OnItemClick(cpSCLCinfo.DataBean.ContentBean position, int helperPosition) {
        showUpdateDialog(position,helperPosition);
    }

    private void showUpdateDialog(final cpSCLCinfo.DataBean.ContentBean contentBean, final int position) {

/*		Intent intent = new Intent();
        intent.setClass(mContext, IsDownLoad.class);
		mContext.startActivity(intent);*/
        final SweetAlertDialog sad = new SweetAlertDialog(getActivity());
        sad.setTitleText("删除此流程");
        sad.setContentText("您确定要进行删除操作吗？");
        sad.setConfirmText("确定");
        sad.setCancelText("取消");
        sad.setCanceledOnTouchOutside(true);
        sad.setCancelable(true);
        sad.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sad.dismiss();


            }
        });
        sad.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sureRemove(contentBean,position);
                sad.dismiss();

            }
        });
        sad.show();
    }

    private void sureRemove(cpSCLCinfo.DataBean.ContentBean contentBean, final int position) {

        showLoadingDialog("正在删除数据");

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("token", token);
            jsonObject.put("id", contentBean.getId());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        mMyOkhttp.post()
                .url(ApiConstants.removecpjglistinfoApi)
                .jsonParams(jsonObject.toString())
                .tag(this)
                .enqueue(new GsonResponseHandler<loginInfo>() {
                    @Override
                    public void onFailure(int statusCode, String error_msg) {

                        hideLoadingDialog();
                        T(error_msg);
                        Log.e("TAG","msg"+statusCode);
                    }

                    @Override
                    public void onSuccess(int statusCode, loginInfo response) {
                        hideLoadingDialog();
                        T(response.getMessage());
                        Log.e("TAG","getErrorcode:"+response.getErrorcode());
                        if(response.getErrorcode().equals("9999")){
                            Log.e("TAG",response.getMessage());

                            detailAdapter.remove(position);//删除

                        }
                    }
                });




    }


    private int num;
    @Override
    public void OnXGItemClick(cpSCLCinfo.DataBean.ContentBean position, int helperPosition) {
        num = helperPosition;
        Bundle bundle = new Bundle();
        bundle.putSerializable("ContentBean",position);
        toClass(mContext,SCLCwriteActivity.class,bundle,REQUESTCODE);

    }
}
