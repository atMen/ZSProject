package customer.tcrj.com.zsproject.first;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
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
import customer.tcrj.com.zsproject.adapter.GridImageAdapter;
import customer.tcrj.com.zsproject.base.BaseActivity;
import customer.tcrj.com.zsproject.bean.addCPInfo;
import customer.tcrj.com.zsproject.bean.cpInfo;
import customer.tcrj.com.zsproject.bean.jdInfo;
import customer.tcrj.com.zsproject.dialog.DialogLeaveApply;
import customer.tcrj.com.zsproject.net.ApiConstants;
import customer.tcrj.com.zsproject.widget.FullyGridLayoutManager;

public class AddCPinfoActivity extends BaseActivity implements View.OnTouchListener {


    private static final int JDCODE = 001;
    private List<LocalMedia> selectList = new ArrayList<>();
    private GridImageAdapter adapter;
    private int maxSelectNum = 1;
    private int themeId;


    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.btnback)
    ImageView btnback;
    @BindView(R.id.txtTitle)
    TextView txtTitle;

    @BindView(R.id.edt_cpname)
    EditText edt_cpname;
    @BindView(R.id.edt_cppp)
    EditText edt_cppp;
    @BindView(R.id.edt_cpbcgg)
    EditText edt_cpbcgg;
    @BindView(R.id.edt_cpbcggdw)
    EditText edt_cpbcggdw;
    @BindView(R.id.edt_ewmdynum)
    EditText edt_ewmdynum;
    @BindView(R.id.edt_cpbzq)
    EditText edt_cpbzq;
    @BindView(R.id.edt_cpms)
    EditText edt_cpms;

    @BindView(R.id.cplx)
    TextView cplx;
    @BindView(R.id.zsfs)
    TextView zsfs;
    @BindView(R.id.zsywlx)
    TextView zsywlx;
    @BindView(R.id.tv_work_naturejob)
    TextView tv_work_naturejob;

    @BindView(R.id.layout_cptype)
    LinearLayout layout_cptype;
    @BindView(R.id.layout_zsywlx)
    LinearLayout layout_zsywlx;
    @BindView(R.id.layout_zsfs)
    LinearLayout layout_zsfs;
    @BindView(R.id.layout_work_naturejob)
    LinearLayout layout_work_naturejob;

    @BindView(R.id.btn_submit)
    Button btn_submit;

    cpInfo.DataBean.ContentBean cpinfo;

    private MyOkHttp mMyOkhttp;
    private String token;
    private String path = null;

    @Override
    protected int setLayout() {
        return R.layout.activity_add_cpinfo;
    }

    @Override
    protected void setView() {
        mMyOkhttp = MyApp.getInstance().getMyOkHttp();
        token = ACache.get(this).getAsString("token");
        txtTitle.setText("产品信息录入");
        initPic();
    }

    private void initPic() {
        themeId = R.style.picture_default_style;


        FullyGridLayoutManager manager = new FullyGridLayoutManager(AddCPinfoActivity.this, 3, GridLayoutManager.VERTICAL, false);
        recycler.setLayoutManager(manager);
        adapter = new GridImageAdapter(AddCPinfoActivity.this, onAddPicClickListener);
        adapter.setList(selectList);
        adapter.setSelectMax(maxSelectNum);
        recycler.setAdapter(adapter);
        adapter.setOnItemClickListener(new GridImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                if (selectList.size() > 0) {
                    LocalMedia media = selectList.get(position);
                    String pictureType = media.getPictureType();
                    int mediaType = PictureMimeType.pictureToVideo(pictureType);
                    switch (mediaType) {
                        case 1:
                            // 预览图片 可自定长按保存路径
                            //PictureSelector.create(MainActivity.this).themeStyle(themeId).externalPicturePreview(position, "/custom_file", selectList);
                            PictureSelector.create(AddCPinfoActivity.this).themeStyle(themeId).openExternalPreview(position, selectList);
                            break;
                        case 2:
                            // 预览视频
                            PictureSelector.create(AddCPinfoActivity.this).externalPictureVideo(media.getPath());
                            break;
                        case 3:
                            // 预览音频
                            PictureSelector.create(AddCPinfoActivity.this).externalPictureAudio(media.getPath());
                            break;
                    }
                }
            }
        });
    }

    @Override
    protected void setData() {
        cpinfo = (cpInfo.DataBean.ContentBean) getIntent().getSerializableExtra("cpinfo");
        if(cpinfo != null){
            txtTitle.setText("产品信息修改");
            btn_submit.setText("保存");
            recycler.setVisibility(View.GONE);

            edt_cpname.setText(cpinfo.getCpmc());
            edt_cppp.setText(cpinfo.getCppp());
            edt_cpbcgg.setText(cpinfo.getCpbcgg());
            edt_cpbcggdw.setText(cpinfo.getCpbcggdw());
            edt_cpbzq.setText(cpinfo.getBxq());
            edt_ewmdynum.setText(cpinfo.getEwmsl());
            edt_cpms.setText(cpinfo.getCpms());

            jdCode = cpinfo.getBaseId();
            cplxCode = cpinfo.getCplx();
            zsfsCode = cpinfo.getZsfs();
            zsywlxCode = cpinfo.getZsywlb();
        }
    }


    private boolean Edit(EditText view){
        return ("".equals(view.getText().toString()))? true:false;
    }


    @OnClick({R.id.btnback,R.id.layout_cptype,R.id.layout_zsfs,R.id.layout_zsywlx,R.id.btn_submit,R.id.layout_work_naturejob})
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnback:


                finish();

                break;
            case R.id.btn_submit:

                if(Edit(edt_cpname) || Edit(edt_cppp)|| Edit(edt_cpbcgg)|| Edit(edt_cpms)
                        || Edit(edt_cpbcggdw)|| Edit(edt_ewmdynum)|| Edit(edt_cpbzq)  || cplx.getText().equals("")
                        || zsfs.getText().equals("")|| zsywlx.getText().equals("")){

                    T("请将信息填写完整");

                } else {

                    if(cpinfo == null) {

                        if (path != null) {

                            try {
                                base64File = customer.tcrj.com.zsproject.Media.Utils.encodeBase64File(path);
//                            Log.e("TAG","SPbase64:"+base64File);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            addData();//上传产品信息
                        } else {
                            T("请录入产品样品图");
                        }

                    }else {
                        addData();
                    }

                }

                break;

            case R.id.layout_cptype:


                Log.e("TAG","选择产品类型");
                final DialogLeaveApply spinner = DialogLeaveApply.getInstance(this,"100");
                spinner.setTitleName("请选择产品类型");
                spinner.setOnItemClickListener(new DialogLeaveApply.ILeaveApplyCallBack() {
                    @Override
                    public void setOnItemListener(String typeName,String typeCode) {
                        cplx.setText(typeName);
                        cplxCode = typeCode;
                    }
                });
                spinner.show();

                break;
            case R.id.layout_zsfs:

                Log.e("TAG","选择追溯方式");
                final DialogLeaveApply spinnerzsfs = DialogLeaveApply.getInstance(this,"106");
                spinnerzsfs.setTitleName("请选择追溯方式");
                spinnerzsfs.setOnItemClickListener(new DialogLeaveApply.ILeaveApplyCallBack() {
                    @Override
                    public void setOnItemListener(String typeName,String typeCode) {
                        zsfs.setText(typeName);
                        zsfsCode = typeCode;
                    }
                });
                spinnerzsfs.show();

                break;
            case R.id.layout_zsywlx:

                Log.e("TAG","选择追溯业务类型");
                final DialogLeaveApply spinnerzslx = DialogLeaveApply.getInstance(this,"107");
                spinnerzslx.setTitleName("请选择追溯业务类型");
                spinnerzslx.setOnItemClickListener(new DialogLeaveApply.ILeaveApplyCallBack() {
                    @Override
                    public void setOnItemListener(String typeName,String typeCode) {
                        zsywlx.setText(typeName);
                        zsywlxCode = typeCode;
                    }
                });
                spinnerzslx.show();

                break;

            case R.id.layout_work_naturejob:
                toClass(this,JdInfoActivity.class,null,JDCODE);
                break;

            default:
                break;

        }
    }


    private String jdCode;
    private String cplxCode;
    private String zsfsCode;
    private String zsywlxCode;

    String base64File = null;

    private void addData() {

        String cpname = edt_cpname.getText().toString().trim();
        String edtcppp = edt_cppp.getText().toString().trim();
        String edtcpbcgg = edt_cpbcgg.getText().toString().trim();
        String edtcpbcggdw = edt_cpbcggdw.getText().toString().trim();
        String edtewmdynum = edt_ewmdynum.getText().toString().trim();
        String edtcpbzq = edt_cpbzq.getText().toString().trim();
        String edtcpms = edt_cpms.getText().toString().trim();




        JSONObject jsonObject = new JSONObject();

        try {

            jsonObject.put("token", token);
            jsonObject.put("cpmc", cpname);
            jsonObject.put("cppp", edtcppp);
            jsonObject.put("baseId", jdCode);
            jsonObject.put("cpbcgg", edtcpbcgg);
            jsonObject.put("cpbcggdw", edtcpbcggdw);
            jsonObject.put("bxq", edtcpbzq);
            jsonObject.put("cplx", cplxCode);
            jsonObject.put("zsfs", zsfsCode);
            jsonObject.put("zsywlb", zsywlxCode);
            jsonObject.put("ewmsl", edtewmdynum);
            jsonObject.put("cpms", edtcpms);

            jsonObject.put("ypt", cpinfo != null? "":base64File);
            jsonObject.put("yptName", cpinfo != null? "":cpname+".jpg");

            jsonObject.put("xcsp", "");
            jsonObject.put("xcspName", "");
            jsonObject.put("xctp", "");
            jsonObject.put("xctpName", "");

            if(cpinfo != null){
                jsonObject.put("id", cpinfo.getId());
            }


        } catch (JSONException e) {
            Log.e("TAG","e"+e.getMessage());
            e.printStackTrace();
        }

        mMyOkhttp.post()
                .url(cpinfo != null? ApiConstants.updatecpinfoApi : ApiConstants.addcplistinfoApi)
                .jsonParams(jsonObject.toString())
                .enqueue(new GsonResponseHandler<addCPInfo>() {
                    @Override
                    public void onFailure(int statusCode, String error_msg) {

                        Log.e("TAG","error_msg"+error_msg);

                    }

                    @Override
                    public void onSuccess(int statusCode, addCPInfo response) {

                        T(response.getMessage());

                        if(response.getErrorcode().equals("9999")){

                            Intent i = new Intent();
                            i.putExtra("cpinfo",response.getData().getId());
                            setResult(RESULT_OK, i);
                            finish();

                        }else if(response.getErrorcode().equals("204")){
                            Utils.toLogin(AddCPinfoActivity.this);
                        }


                    }
                });



    }


    private GridImageAdapter.onAddPicClickListener onAddPicClickListener = new GridImageAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick() {
                PictureSelector.create(AddCPinfoActivity.this)
                        .openGallery(PictureMimeType.ofImage())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                        .theme(themeId)// 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style
                        .maxSelectNum(maxSelectNum)// 最大图片选择数量
                        .minSelectNum(1)// 最小选择数量
                        .imageSpanCount(3)// 每行显示个数
                        .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选
                        .previewImage(true)// 是否可预览图片
                        .isCamera(true)// 是否显示拍照按钮
                        .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                        //.imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                        //.setOutputCameraPath("/CustomPath")// 自定义拍照保存路径
                        .enableCrop(false)// 是否裁剪
                        .compress(true)// 是否压缩
                        .synOrAsy(true)//同步true或异步false 压缩 默认同步
                        //.compressSavePath(getPath())//压缩图片保存地址
                        //.sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                        .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                        .hideBottomControls(false)// 是否显示uCrop工具栏，默认不显示
                        .isGif(false)// 是否显示gif图片
                        .freeStyleCropEnabled(true)// 裁剪框是否可拖拽
                        .openClickSound(false)// 是否开启点击声音
                        .selectionMedia(selectList)// 是否传入已选图片
                        //.isDragFrame(false)// 是否可拖动裁剪框(固定)
//                        .videoMaxSecond(15)
//                        .videoMinSecond(10)
                        //.previewEggs(false)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
                        //.cropCompressQuality(90)// 裁剪压缩质量 默认100
                        .minimumCompressSize(100)// 小于100kb的图片不压缩
                        //.cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效
                        //.rotateEnabled(true) // 裁剪是否可旋转图片
                        //.scaleEnabled(true)// 裁剪是否可放大缩小图片
                        //.videoQuality()// 视频录制质量 0 or 1
                        //.videoSecond()//显示多少秒以内的视频or音频也可适用
                        //.recordVideoSecond()//录制视频秒数 默认60s
                        .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
        }

    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    selectList = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 如果裁剪并压缩了，已取压缩路径为准，因为是先裁剪后压缩的
                    for (LocalMedia media : selectList) {
//                        Log.i("原图片-----》", media.getPath());
//                        Log.i("压缩图片-----》", media.getCompressPath());
                        //1.4M可压缩到500多K
                        path = media.getCompressPath();
                    }

                    adapter.setList(selectList);
                    adapter.notifyDataSetChanged();
                    break;

                case JDCODE:

                    if(data != null){
                        String dlsmc = data.getStringExtra("dlsmc");
                        jdCode = data.getStringExtra("dlsid");

                        tv_work_naturejob.setText(dlsmc);
                    }
                    break;

                default:
                    break;
            }
        }
    }

    /**
     * EditText竖直方向是否可以滚动
     * @param editText  需要判断的EditText
     * @return  true：可以滚动   false：不可以滚动
     */
    private boolean canVerticalScroll(EditText editText) {
        //滚动的距离
        int scrollY = editText.getScrollY();
        //控件内容的总高度
        int scrollRange = editText.getLayout().getHeight();
        //控件实际显示的高度
        int scrollExtent = editText.getHeight() - editText.getCompoundPaddingTop() -editText.getCompoundPaddingBottom();
        //控件内容总高度与实际显示高度的差值
        int scrollDifference = scrollRange - scrollExtent;

        if(scrollDifference == 0) {
            return false;
        }

        return (scrollY > 0) || (scrollY < scrollDifference - 1);
    }


    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        //触摸的是EditText并且当前EditText可以滚动则将事件交给EditText处理；否则将事件交由其父类处理
        if ((view.getId() == R.id.edt_cpms && canVerticalScroll(edt_cpms))) {
            view.getParent().requestDisallowInterceptTouchEvent(true);
            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                view.getParent().requestDisallowInterceptTouchEvent(false);
            }
        }
        return false;
    }


}
