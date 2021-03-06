package customer.tcrj.com.zsproject.first;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.permissions.RxPermissions;
import com.luck.picture.lib.tools.PictureFileUtils;
import com.tsy.sdk.myokhttp.MyOkHttp;
import com.tsy.sdk.myokhttp.response.GsonResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;
import org.sufficientlysecure.htmltextview.HtmlHttpImageGetter;
import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;
import customer.tcrj.com.zsproject.MainActivity;
import customer.tcrj.com.zsproject.Media.VideoPlayerActivity;
import customer.tcrj.com.zsproject.Media.VideoRecorderActivity;
import customer.tcrj.com.zsproject.Media.VideoUltls;
import customer.tcrj.com.zsproject.MyApp;
import customer.tcrj.com.zsproject.R;
import customer.tcrj.com.zsproject.Utils.ACache;
import customer.tcrj.com.zsproject.Utils.ShowImageUtils;
import customer.tcrj.com.zsproject.Utils.Utils;
import customer.tcrj.com.zsproject.adapter.GridImageAdapter;
import customer.tcrj.com.zsproject.adapter.XctGridImageAdapter;
import customer.tcrj.com.zsproject.base.BaseActivity;
import customer.tcrj.com.zsproject.bean.addCPInfo;
import customer.tcrj.com.zsproject.bean.cpInfo;
import customer.tcrj.com.zsproject.bean.jdInfo;
import customer.tcrj.com.zsproject.dialog.DialogLeaveApply;
import customer.tcrj.com.zsproject.net.ApiConstants;
import customer.tcrj.com.zsproject.resources.ImageActivity;
import customer.tcrj.com.zsproject.videoview.CpInfoVideoViewActivity;
import customer.tcrj.com.zsproject.widget.FullyGridLayoutManager;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class AddCPinfoActivity extends BaseActivity implements View.OnTouchListener {

    private static final int REQUESTCODE = 003;
    private static final int JDCODE = 001;
    private static final int XCTCODE = 002;
    private List<LocalMedia> selectList = new ArrayList<>();
    private List<LocalMedia> XctselectList = new ArrayList<>();
    private GridImageAdapter adapter;
    private XctGridImageAdapter Xctadapter;
    private int maxSelectNum = 1;
    private int themeId;

    @BindView(R.id.delete)
    ImageView delete;
    @BindView(R.id.iv_btn)
    ImageView iv_btn;
    @BindView(R.id.iv_video_screenshot)
    ImageView iv_video_screenshot;
    @BindView(R.id.recycler_xctp)
    RecyclerView recycler_xctp;
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


    @BindView(R.id.ypt_delete)
    ImageView ypt_delete;
    @BindView(R.id.xct_delete)
    ImageView xct_delete;
    @BindView(R.id.iv_ypt_screenshot)
    ImageView iv_ypt_screenshot;
    @BindView(R.id.iv_xct_screenshot)
    ImageView iv_xct_screenshot;
    @BindView(R.id.fl_ypt)
    FrameLayout fl_ypt;
    @BindView(R.id.fl_xct)
    FrameLayout fl_xct;

    cpInfo.DataBean.ContentBean cpinfo;

    private MyOkHttp mMyOkhttp;
    private String token;
    private String path = null;
    private String Xctpath = null;
    private String videopath = null;
    private String videoUrl = null;
    private Bitmap thumbnail;
    private String zsywlxname;
    private String cplxname;
    private String zsfsname;
    private String jdname;

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

        // 清空图片缓存，包括裁剪、压缩后的图片 注意:必须要在上传完成后调用 必须要获取权限
        RxPermissions permissions = new RxPermissions(this);
        permissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new Observer<Boolean>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(Boolean aBoolean) {
                if (aBoolean) {
                    PictureFileUtils.deleteCacheDirFile(AddCPinfoActivity.this);
                } else {
                    Toast.makeText(AddCPinfoActivity.this,
                            getString(R.string.picture_jurisdiction), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onComplete() {
            }
        });
        themeId = R.style.picture_default_style;
        seYPT();
        seXCT();
    }
    private void seYPT(){
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

    private void seXCT(){
        FullyGridLayoutManager manager = new FullyGridLayoutManager(AddCPinfoActivity.this, 3, GridLayoutManager.VERTICAL, false);
        recycler_xctp.setLayoutManager(manager);
        Xctadapter = new XctGridImageAdapter(AddCPinfoActivity.this, onXCTAddPicClickListener);
        Xctadapter.setList(XctselectList);
        Xctadapter.setSelectMax(maxSelectNum);
        recycler_xctp.setAdapter(Xctadapter);
        Xctadapter.setOnItemClickListener(new XctGridImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                if (XctselectList.size() > 0) {
                    LocalMedia media = XctselectList.get(position);
                    String pictureType = media.getPictureType();
                    int mediaType = PictureMimeType.pictureToVideo(pictureType);
                    switch (mediaType) {
                        case 1:
                            // 预览图片 可自定长按保存路径
                            //PictureSelector.create(MainActivity.this).themeStyle(themeId).externalPicturePreview(position, "/custom_file", selectList);
                            PictureSelector.create(AddCPinfoActivity.this).themeStyle(themeId).openExternalPreview(position, XctselectList);
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
//        String ypt = cpinfo.getYpt();
//        String xctp = cpinfo.getXctp();
//        String xcsp = cpinfo.getXcsp();

        cplxname = getIntent().getStringExtra("cplxname");
        zsfsname = getIntent().getStringExtra("zsfsname");
        zsywlxname = getIntent().getStringExtra("zsywlxname");
        jdname = getIntent().getStringExtra("jdname");


        if(cpinfo != null){

//            String xcsp = cpinfo.getXcsp();
//
//            videopath = ApiConstants.YPTURLROOT +.replace("\\", "/");

            String ypt = cpinfo.getYpt();
            if(ypt != null && !"".equals(ypt)){
                fl_ypt.setVisibility(View.VISIBLE);
                recycler.setVisibility(View.GONE);
                ShowImageUtils.LoadImage(this,ApiConstants.ImageURLROOT+cpinfo.getYpt().replace("\\","/"), iv_ypt_screenshot);
            }

            String xct = cpinfo.getXctp();
            if(xct != null && !"".equals(xct)){
                fl_xct.setVisibility(View.VISIBLE);
                recycler_xctp.setVisibility(View.GONE);
                ShowImageUtils.LoadImage(this,ApiConstants.ImageURLROOT+cpinfo.getXctp().replace("\\","/"), iv_xct_screenshot);
            }

            videoUrl = cpinfo.getXcsp();
            if(videoUrl != null && !"".equals(videoUrl)){
                delete.setVisibility(View.VISIBLE);
                Log.e("TAG","视频地址："+videoUrl);
                iv_video_screenshot.setBackgroundColor(Color.BLACK);

            }
            txtTitle.setText("产品信息修改");
            btn_submit.setText("保存");
//          recycler.setVisibility(View.GONE);

            edt_cpname.setText(cpinfo.getCpmc());
            edt_cppp.setText(cpinfo.getCppp());
            edt_cpbcgg.setText(cpinfo.getCpbcgg());
            edt_cpbcggdw.setText(cpinfo.getCpbcggdw());
            edt_cpbzq.setText(cpinfo.getBxq());
            edt_ewmdynum.setText(cpinfo.getEwmsl());


            HtmlTextView htmlTextView = new HtmlTextView(this);
            htmlTextView.setHtml(cpinfo.getCpms(),
                    new HtmlHttpImageGetter(htmlTextView));
            String text = htmlTextView.getText().toString();
            edt_cpms.setText(text);

            tv_work_naturejob.setText(jdname);
            cplx.setText(cplxname);
            zsfs.setText(zsfsname);
            zsywlx.setText(zsywlxname);

            jdCode = cpinfo.getBaseId();
            cplxCode = cpinfo.getCplx();
            zsfsCode = cpinfo.getZsfs();
            zsywlxCode = cpinfo.getZsywlb();
        }
    }

    private boolean Edit(EditText view){
        return ("".equals(view.getText().toString()))? true:false;
    }

    @OnClick({R.id.delete,R.id.ypt_delete,R.id.xct_delete,R.id.iv_btn,R.id.iv_video_screenshot,R.id.btnback,R.id.layout_cptype,R.id.layout_zsfs,R.id.layout_zsywlx,R.id.btn_submit,R.id.layout_work_naturejob})
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.delete:
                delete.setVisibility(View.GONE);

                if(cpinfo != null){
                    videoUrl = null;
                    iv_video_screenshot.setBackgroundColor(Color.WHITE);
                }else {
                    videopath = null;
                    iv_video_screenshot.setImageBitmap(null);
                }

//                deleteFile();
                break;
            case R.id.ypt_delete:
                fl_ypt.setVisibility(View.GONE);
                recycler.setVisibility(View.VISIBLE);
                break;
            case R.id.xct_delete:
                fl_xct.setVisibility(View.GONE);
                recycler_xctp.setVisibility(View.VISIBLE);
                break;
            case R.id.btnback:
                finish();
                break;
            case R.id.btn_submit:

                if(Edit(edt_cpname) || Edit(edt_cppp)|| Edit(edt_cpbcgg)|| Edit(edt_cpms)
                        || Edit(edt_cpbcggdw)|| Edit(edt_ewmdynum)|| Edit(edt_cpbzq)  || cplx.getText().equals("")
                        || zsfs.getText().equals("")|| zsywlx.getText().equals("")){

                    T("请将信息填写完整");

                } else {
                    Log.e("TAG","添加信息");

                    if(cpinfo != null){
                        String ypt = cpinfo.getYpt();
                        if(path != null){
                            btn_submit.setEnabled(false);

                            new MyAsyncTask().execute(1);
                        }else {

                            if(ypt != null && !"".equals(ypt)){
                                btn_submit.setEnabled(false);

                                new MyAsyncTask().execute(1);
                            }else {
                                T("请录入产品样品图");
                            }

                        }


                    }else {
                        //样品图上传
                        if (path != null) {
                            btn_submit.setEnabled(false);

                            new MyAsyncTask().execute(1);

//                            addData();//上传产品信息
                        } else {

                            T("请录入产品样品图");
                        }
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


            case R.id.iv_btn:
            case R.id.iv_video_screenshot:

                if(videoUrl != null){
                    String url = ApiConstants.ImageURLROOT + videoUrl.replace("\\", "/");
                    Bundle videobundle = new Bundle();
                    videobundle.putString("cover",url);
                    toClass(this,CpInfoVideoViewActivity.class,videobundle);
                }else {
                    if(videopath != null){
                        Bundle bundle = new Bundle();
                        bundle.putString("path",videopath);
                        toClass(this,VideoPlayerActivity.class,bundle);
                    }else {
//                //视频录制
                        toClass(this,VideoRecorderActivity.class,null,REQUESTCODE);
                    }
                }

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
    String Xctbase64File = null;
    String Xcspbase64File = null;

    private void addData() {

//        showLoadingDialog("正在保存数据...");
        String cpname = edt_cpname.getText().toString().trim();
        String edtcppp = edt_cppp.getText().toString().trim();
        String edtcpbcgg = edt_cpbcgg.getText().toString().trim();
        String edtcpbcggdw = edt_cpbcggdw.getText().toString().trim();
        String edtewmdynum = edt_ewmdynum.getText().toString().trim();
        String edtcpbzq = edt_cpbzq.getText().toString().trim();
        String edtcpms = edt_cpms.getText().toString().trim();

        String cplxmc = cplx.getText().toString();
        String zsfsmc = zsfs.getText().toString();
        String zsywlxmc = zsywlx.getText().toString();

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

            jsonObject.put("ypt", base64File!= null? base64File:"");
            jsonObject.put("yptName", base64File!= null? cpname+".jpg":"");

            jsonObject.put("xcsp", Xcspbase64File != null? Xcspbase64File:"");
            jsonObject.put("xcspName", Xcspbase64File != null? cpname+"宣传视频.mp4":"");
            jsonObject.put("xctp", Xctbase64File != null? Xctbase64File:"");
            jsonObject.put("xctpName", Xctbase64File != null? cpname+"宣传图.jpg":"");

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
                        btn_submit.setEnabled(true);
                        hideLoadingDialog();
                    }

                    @Override
                    public void onSuccess(int statusCode, addCPInfo response) {

                        hideLoadingDialog();

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

    private XctGridImageAdapter.onAddPicClickListener onXCTAddPicClickListener = new XctGridImageAdapter.onAddPicClickListener() {
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
                    .selectionMedia(XctselectList)// 是否传入已选图片
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
                    .forResult(XCTCODE);//结果回调onActivityResult code
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

                case XCTCODE:
                    // 图片选择结果回调
                    XctselectList = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 如果裁剪并压缩了，已取压缩路径为准，因为是先裁剪后压缩的
                    for (LocalMedia media : XctselectList) {
//                        Log.i("原图片-----》", media.getPath());
//                        Log.i("压缩图片-----》", media.getCompressPath());
                        //1.4M可压缩到500多K
                        Xctpath = media.getCompressPath();
                    }

                    Xctadapter.setList(XctselectList);
                    Xctadapter.notifyDataSetChanged();
                    break;

                case JDCODE:
                    if(data != null){
                        String dlsmc = data.getStringExtra("dlsmc");
                        jdCode = data.getStringExtra("dlsid");
                        tv_work_naturejob.setText(dlsmc);
                    }
                    break;

                case REQUESTCODE://视频
                    videopath = data.getStringExtra("path");
                    setImage(videopath);
                    break;

                default:
                    break;
            }
        }
    }
    private void setImage(String videoUri){
        if (!TextUtils.isEmpty(videoUri)) {

            delete.setVisibility(View.VISIBLE);

//            File file = new File(videoUri);
//            long fileSize = getFileSize(file);
//            String formetFileSize = FormetFileSize(fileSize);
//            Log.d("upLoadImageToNet", "视频大小:" + ">>>>>>>>>" + formetFileSize);
//            spsize.setText("视频大小:" + formetFileSize);



                thumbnail = VideoUltls.getVideoThumbnail(videoUri);




        }
        if (thumbnail != null) {
            iv_video_screenshot.setImageBitmap(thumbnail);
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

    public class MyAsyncTask extends AsyncTask<Integer, String, String> {
        public MyAsyncTask() {
            super();
        }

        //该方法运行在UI线程当中,并且运行在UI线程当中 可以对UI空间进行设置
        @Override
        protected void onPreExecute() {
            showLoadingDialog("正在提交产品信息...");
        }

        @Override
        protected String doInBackground(Integer... integers) {
            Log.e("xxxxxx","xxxxxxexecute传入参数="+integers[0]);

            try {
                base64File = customer.tcrj.com.zsproject.Media.Utils.encodeBase64File(path);
                Log.e("TAG","path="+path);
                Log.e("TAG","base64File="+base64File);
            } catch (Exception e) {
                e.printStackTrace();
            }

            //宣传图上传
            try {
                Xctbase64File = customer.tcrj.com.zsproject.Media.Utils.encodeBase64File(Xctpath);
                Log.e("TAG","Xctpath="+Xctpath);
                Log.e("TAG","Xctbase64File="+Xctbase64File);
            } catch (Exception e) {
                e.printStackTrace();
            }

            //宣传视频上传
            try {
                Xcspbase64File = customer.tcrj.com.zsproject.Media.Utils.encodeBase64File(videopath);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return base64File;
        }
        /**
         * 这里的String参数对应AsyncTask中的第三个参数（也就是接收doInBackground的返回值）
         * 在doInBackground方法执行结束之后在运行，并且运行在UI线程当中 可以对UI空间进行设置
         */
        @Override
        protected void onPostExecute(String result) {
            Log.e("TAG","线程结束"+result);

            addData();

        }

        /**
         * 这里的Intege参数对应AsyncTask中的第二个参数
         * 在doInBackground方法当中，，每次调用publishProgress方法都会触发onProgressUpdate执行
         * onProgressUpdate是在UI线程中执行，所有可以对UI空间进行操作
         */
        @Override
        protected void onProgressUpdate(String... values) {
            String vlaue = values[0]+"";
            Log.e("TAG","xxxxxx vlaue="+vlaue);
        }
    }

}
