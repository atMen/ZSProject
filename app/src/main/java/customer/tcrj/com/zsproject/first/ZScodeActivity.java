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
import com.yyydjk.library.DropDownMenu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import customer.tcrj.com.zsproject.R;
import customer.tcrj.com.zsproject.adapter.ListDropDownAdapter;
import customer.tcrj.com.zsproject.adapter.OnItemClickLitener;
import customer.tcrj.com.zsproject.adapter.sclcAdapter;
import customer.tcrj.com.zsproject.adapter.zssqAdapter;
import customer.tcrj.com.zsproject.base.BaseActivity;
import customer.tcrj.com.zsproject.widget.CustomLoadMoreView;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

public class ZScodeActivity extends BaseActivity implements BaseQuickAdapter.OnItemClickListener, zssqAdapter.OnItemClickListener, View.OnClickListener {


    private final static int REQUESTCODE = 1; // 返回的结果码

    Button btn_tj;
    RecyclerView mRecyclerView;
    PtrFrameLayout mPtrFrameLayout;

    @BindView(R.id.btnback)
    ImageView btnback;
    @BindView(R.id.txtTitle)
    TextView txtTitle;

    @BindView(R.id.dropDownMenu)
    DropDownMenu mDropDownMenu;

    private List<String> selectDatas = new ArrayList<>();//记录选中项

    private boolean canPull = true;
    private List<String> beanList;
    private zssqAdapter detailAdapter;

    private View inflate;
    private String citys[] = {"不限", "武汉", "北京", "上海", "成都", "广州", "深圳", "重庆", "天津", "西安", "南京", "杭州"};
    private String headers[] = {"名称", "品牌", "规格"};
    private List<View> popupViews = new ArrayList<>();
    private ListDropDownAdapter ageAdapter;
    private ListDropDownAdapter ageAdapter2;
    private ListDropDownAdapter ageAdapter3;

    @Override
    protected int setLayout() {
        return R.layout.activity_zscode_dropdown;
    }


    @Override
    protected void setView() {
        txtTitle.setText("追溯码审批申请");
        initviewdrop();

        initview();
    }

    private void initviewdrop() {
        inflate = View.inflate(this, R.layout.drop_down, null);
        mRecyclerView = inflate.findViewById(R.id.recycler_view);
        mPtrFrameLayout = inflate.findViewById(R.id.mPtrFrameLayout);
        btn_tj = inflate.findViewById(R.id.btn_tj);
        btn_tj.setOnClickListener(this);
        btnback.setOnClickListener(this);
        //init age menu
        final ListView ageView = new ListView(this);
        ageView.setDividerHeight(0);
        ageAdapter = new ListDropDownAdapter(this, Arrays.asList(citys));
        ageView.setAdapter(ageAdapter);

        //init age menu
        final ListView cityView = new ListView(this);
        cityView.setDividerHeight(0);
        ageAdapter2 = new ListDropDownAdapter(this, Arrays.asList(citys));
        cityView.setAdapter(ageAdapter2);

        //init sex menu
        final ListView sexView = new ListView(this);
        sexView.setDividerHeight(0);
        ageAdapter3 = new ListDropDownAdapter(this, Arrays.asList(citys));
        sexView.setAdapter(ageAdapter3);

        popupViews.add(ageView);
        popupViews.add(cityView);
        popupViews.add(sexView);

        ageView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ageAdapter.setCheckItem(position);
                mDropDownMenu.setTabText(position == 0 ? headers[1] : citys[position]);
                mDropDownMenu.closeMenu();
            }
        });
        cityView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ageAdapter2.setCheckItem(position);
                mDropDownMenu.setTabText(position == 0 ? headers[1] : citys[position]);
                mDropDownMenu.closeMenu();
            }
        });
        sexView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ageAdapter3.setCheckItem(position);
                mDropDownMenu.setTabText(position == 0 ? headers[1] : citys[position]);
                mDropDownMenu.closeMenu();
            }
        });

        mDropDownMenu.setDropDownMenu(Arrays.asList(headers), popupViews, inflate);

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
//                pageNum = 1;
//                getData(pageNum);

            }
        });
        beanList = new ArrayList<>();
        for (int i=0; i<10; i++) {
            beanList.add("测试"+i);
        }
        mRecyclerView.setLayoutManager(new LinearLayoutManager(ZScodeActivity.this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(detailAdapter = new zssqAdapter(beanList, ZScodeActivity.this,mRecyclerView));
        detailAdapter.setPreLoadNumber(1);
        detailAdapter.setLoadMoreView(new CustomLoadMoreView());
        detailAdapter.setEnableLoadMore(true);
        detailAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        detailAdapter.setOnItemClickListener(this);
        detailAdapter.setOnItemUpdataClickListener(this);
        detailAdapter.setOnItemClickLitener(new OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                if(!detailAdapter.isSelected.get(position)){
                    detailAdapter.isSelected.put(position, true); // 修改map的值保存状态
                    detailAdapter.notifyItemChanged(position);
                    selectDatas.add(beanList.get(position));

                }else {
                    detailAdapter.isSelected.put(position, false); // 修改map的值保存状态
                    detailAdapter.notifyItemChanged(position);
                    selectDatas.remove(beanList.get(position));
                }

//                mTvCount.setText("已选中"+selectDatas.size()+"项");
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });


        detailAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
//                Log.e("TAG","点击重新加载数据");
//                getData(pageNum);
            }
        }, mRecyclerView);

        showSuccess();
    }


    @Override
    protected void setData() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
//            case R.id.btn_tj:
////                detailAdapter.add("New String",sclcAdapter.LAST_POSITION);//生产流程添加
//
//                String str = null;
//                for(int i = 0;i < selectDatas.size(); i++){
//                    String s = selectDatas.get(i);
//                    str += s;
//                }
//
//                T("提交成功");
//
//                break;
            case R.id.btnback:
                finish();
                break;
            default:
                break;



        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

    }


    private int num;
    @Override
    public void OnItemClick(int position) {

        num = position;
        toClass(this,UpdateZSActivity.class,null,REQUESTCODE);
//        detailAdapter.updata();
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

    @Override
    public void onBackPressed() {
        //退出activity前关闭菜单
        if (mDropDownMenu.isShowing()) {
            mDropDownMenu.closeMenu();
        } else {
            super.onBackPressed();
        }
    }

}
