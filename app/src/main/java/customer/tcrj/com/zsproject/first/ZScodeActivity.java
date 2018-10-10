package customer.tcrj.com.zsproject.first;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.tsy.sdk.myokhttp.MyOkHttp;
import com.tsy.sdk.myokhttp.response.GsonResponseHandler;
import com.yyydjk.library.DropDownMenu;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import customer.tcrj.com.zsproject.MyApp;
import customer.tcrj.com.zsproject.R;
import customer.tcrj.com.zsproject.Utils.ACache;
import customer.tcrj.com.zsproject.Utils.Utils;
import customer.tcrj.com.zsproject.adapter.ListDropDownAdapter;
import customer.tcrj.com.zsproject.adapter.OnItemClickLitener;
import customer.tcrj.com.zsproject.adapter.sclcAdapter;
import customer.tcrj.com.zsproject.adapter.zssqAdapter;
import customer.tcrj.com.zsproject.base.BaseActivity;
import customer.tcrj.com.zsproject.bean.addCPInfo;
import customer.tcrj.com.zsproject.bean.cpInfo;
import customer.tcrj.com.zsproject.bean.xslcCxInfo;
import customer.tcrj.com.zsproject.dialog.SweetAlertDialog;
import customer.tcrj.com.zsproject.net.ApiConstants;
import customer.tcrj.com.zsproject.widget.CustomLoadMoreView;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

public class ZScodeActivity extends BaseActivity implements BaseQuickAdapter.OnItemClickListener, zssqAdapter.OnItemClickListener, View.OnClickListener {


    private final static int REQUESTCODE = 1; // 返回的结果码

    @BindView(R.id.btn_tj)
    Button btn_tj;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.mPtrFrameLayout)
    PtrFrameLayout mPtrFrameLayout;

    @BindView(R.id.btnback)
    ImageView btnback;
    @BindView(R.id.txtTitle)
    TextView txtTitle;

    private int pageNum = 1;


    private List<cpInfo.DataBean.ContentBean> selectDatas = new ArrayList<>();//记录选中项

    private boolean canPull = true;
    private List<cpInfo.DataBean.ContentBean> beanList;
    private zssqAdapter detailAdapter;
    private MyOkHttp mMyOkhttp;
    private String token;

    @Override
    protected int setLayout() {
        return R.layout.activity_zscode_dropdown;
    }


    @Override
    protected void setView() {
        txtTitle.setText("追溯码审批申请");
        mMyOkhttp = MyApp.getInstance().getMyOkHttp();
        token = ACache.get(this).getAsString("token");
        initview();
    }

    private void initview() {
        btn_tj.setOnClickListener(this);
        btnback.setOnClickListener(this);
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
                selectDatas.clear();
                pageNum = 1;
                getData(pageNum);

            }
        });
        beanList = new ArrayList<>();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(ZScodeActivity.this));
//        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(detailAdapter = new zssqAdapter(beanList, ZScodeActivity.this,mRecyclerView));
//        detailAdapter.setPreLoadNumber(1);
        detailAdapter.setLoadMoreView(new CustomLoadMoreView());
        detailAdapter.setEnableLoadMore(true);
        detailAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);

        detailAdapter.setOnItemClickListener(this);
        detailAdapter.setOnItemUpdataClickListener(this);
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
            jsonObject.put("status", "1,4");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        mMyOkhttp.post()
                .url(ApiConstants.cplistinfoApi)
                .jsonParams(jsonObject.toString())
                .enqueue(new GsonResponseHandler<cpInfo>() {
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
                    public void onSuccess(int statusCode, cpInfo response) {

                        if(response.getErrorcode().equals("9999")){

                            if(num > 1){//上拉加载
                                Log.e("TAG","上拉加载更多数据");
                                loadMoreData(response,false);
                            }else{//下拉刷新
                                loadData(response.getData().getContent(),false);
                            }

                        }else if(response.getErrorcode().equals("204")){

                            Utils.toLogin(ZScodeActivity.this);
                        }


                    }
                });

    }

    //上拉加载更多数据
    private void loadMoreData(cpInfo response,boolean isError) {
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
                List<cpInfo.DataBean.ContentBean> content = response.getData().getContent();
                pageNum++;
                detailAdapter.addData(content);
                detailAdapter.loadMoreComplete();
            }

        }

    }

    //下拉刷新
    private void loadData(List<cpInfo.DataBean.ContentBean> response,boolean isError) {

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
            if(mPtrFrameLayout != null){
                mPtrFrameLayout.refreshComplete();
            }
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
    protected void setData() {
        getData(1);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_tj:
//              detailAdapter.add("New String",sclcAdapter.LAST_POSITION);//生产流程添加
                StringBuilder stringBuilder = new StringBuilder();
                for(int i = 0;i < selectDatas.size(); i++){
                    String s = selectDatas.get(i).getId();
                    stringBuilder.append(s+",");
                }

                showUpdateDialog(stringBuilder.toString());

                break;
            case R.id.btnback:
                finish();
                break;
            default:
                break;



        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

        cpInfo.DataBean.ContentBean bean = (cpInfo.DataBean.ContentBean) adapter.getItem(position);
        if(!bean.isselect()){
            bean.setIsselect(true);
            selectDatas.add(bean);
        }else {
            bean.setIsselect(false);
            selectDatas.remove(bean);
        }
        detailAdapter.notifyItemChanged(position);
    }


    private int num;
    @Override
    public void OnItemClick(int position,cpInfo.DataBean.ContentBean item) {

        num = position;
        Bundle bundle = new Bundle();
        bundle.putSerializable("cpinfo",item);
        toClass(this,UpdateZSActivity.class,bundle,REQUESTCODE);
//      detailAdapter.updata();
    }

    private void updata(String three){

        Log.e("TAG","num:"+num);
        detailAdapter.updata(three,num);

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // RESULT_OK，判断另外一个activity已经结束数据输入功能，Standard activity result:
        // operation succeeded. 默认值是-1
        if (resultCode == 2) {
            if (requestCode == REQUESTCODE) {
                String three = data.getStringExtra("three");
                Log.e("TAG","three:"+three);
                updata(three);
            }
        }
    }


    private void showUpdateDialog(final String s) {

        if(s == null || "".equals(s)){
            T("请选择要申请的产品");
            return;
        }
        Log.e("TAG","stringBuilder:"+s);
        final SweetAlertDialog sad = new SweetAlertDialog(this);
        sad.setTitleText("提交追溯码申请");
        sad.setContentText("您确定要提交吗？");
        sad.setConfirmText("取消");
        sad.setCancelText("确定");
        sad.setCanceledOnTouchOutside(true);
        sad.setCancelable(true);
        sad.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sure(s);
                sad.dismiss();
            }
        });
        sad.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {

                sad.dismiss();
            }
        });
        sad.show();
    }

    private void sure(String s) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("productIds", s);
            jsonObject.put("token", token);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mMyOkhttp.post()
                .url(ApiConstants.tjcxlcApi)
                .jsonParams(jsonObject.toString())
                .enqueue(new GsonResponseHandler<xslcCxInfo>() {
                    @Override
                    public void onFailure(int statusCode, String error_msg) {

                        Log.e("TAG","error_msg"+error_msg);

                        T(error_msg);
                    }

                    @Override
                    public void onSuccess(int statusCode, xslcCxInfo response) {

                        if(response.getErrorcode().equals("9999")){

                            T(response.getMessage());
                            getData(1);

                        }else if(response.getErrorcode().equals("204")){

                            Utils.toLogin(ZScodeActivity.this);
                        }


                    }
                });

    }






}
