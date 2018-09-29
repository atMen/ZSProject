package customer.tcrj.com.zsproject.first;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.tsy.sdk.myokhttp.MyOkHttp;
import com.tsy.sdk.myokhttp.response.GsonResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import butterknife.BindView;
import butterknife.OnClick;
import customer.tcrj.com.zsproject.Media.Utils;
import customer.tcrj.com.zsproject.Media.VideoPlayerActivity;
import customer.tcrj.com.zsproject.Media.VideoRecorderActivity;
import customer.tcrj.com.zsproject.Media.VideoUltls;
import customer.tcrj.com.zsproject.MyApp;
import customer.tcrj.com.zsproject.R;
import customer.tcrj.com.zsproject.Utils.ACache;
import customer.tcrj.com.zsproject.base.BaseActivity;
import customer.tcrj.com.zsproject.bean.updataInfo;
import customer.tcrj.com.zsproject.net.ApiConstants;
import customer.tcrj.com.zsproject.resources.ImageActivity;
import customer.tcrj.com.zsproject.resources.ResourcesFragment;
import customer.tcrj.com.zsproject.widget.selectDialog;

public class AddResourcesActivity extends BaseActivity {

    @BindView(R.id.btnback)
    ImageView btnback;
    @BindView(R.id.txtTitle)
    TextView txtTitle;

    @BindView(R.id.iv_video_screenshot)
    ImageView iv_video_screenshot;

    @BindView(R.id.iv_btn)
    ImageView iv_btn;

    @BindView(R.id.delete)
    ImageView delete;

    @BindView(R.id.btn_true)
    Button btn_true;

    @BindView(R.id.edt_cpname)
    EditText edt_cpname;
    @BindView(R.id.edt_today_plan)
    EditText edt_today_plan;

    private List<LocalMedia> selectList = new ArrayList<>();
    private MyOkHttp mMyOkhttp;

    public static final int REQUESTCODE = 100;

    @Override
    protected int setLayout() {
        return R.layout.resources_fragment;
    }

    @Override
    protected void setView() {
        mMyOkhttp = MyApp.getInstance().getMyOkHttp();
        txtTitle.setText("企业资源录入");
    }

    @Override
    protected void setData() {


    }

    @OnClick({R.id.btnback,R.id.iv_video_screenshot,R.id.iv_btn,R.id.delete,R.id.btn_true})
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btnback:


                finish();

                break;
            case R.id.iv_btn:
            case R.id.iv_video_screenshot:

                if(path != null){

                    Bundle bundle = new Bundle();
                    bundle.putString("path",path);
                    if(!isvideo){
                        toClass(this,ImageActivity.class,bundle);
                    }else {
                        toClass(this,VideoPlayerActivity.class,bundle);
                    }


                }else {

                    //弹出选择框
                    showSelectDialog();
//                  //视频录制
//                  toClass(mContext,VideoRecorderActivity.class,null,REQUESTCODE);
                }

                break;

            case R.id.delete:
                path = null;
                delete.setVisibility(View.GONE);
                iv_video_screenshot.setImageBitmap(null);
                deleteFile();
                break;

            case R.id.btn_true:

                if(!TextUtils.isEmpty(edt_cpname.getText().toString()) && path != null){
//                    showLoadingDialog("正在上传资料内容..");


                    new MyAsyncTask().execute(1);
//                    upLoadeData(path,edt_cpname.getText().toString(),edt_today_plan.getText().toString());
                }else {
                    T("请将信息填写完整");
                }
                break;

            default:
                break;
        }

    }

    private void upLoadeData(String path, String mc, String ms) {

        String base64File = null;
        try {
            base64File = Utils.encodeBase64File(path);

        } catch (Exception e) {
            e.printStackTrace();
        }

        String time = new SimpleDateFormat("yyyy/MM/dd-HH:mm:ss",
                Locale.getDefault()).format(System.currentTimeMillis());

        if(base64File != null){
            getDataFromNet(mc,ms,base64File,time);
        }

    }

    private String token;
    public void getDataFromNet(String mc, String ms, String file,String time) {

        Log.e("TAG","base64:"+"data:image/jpeg;base64,"+file);
        token =  ACache.get(this).getAsString("token");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("token",token);
            jsonObject.put("clmc", edt_cpname.getText().toString());
            jsonObject.put("cllx", (isvideo)?"10402":"10401");
            jsonObject.put("clms", edt_today_plan.getText().toString());
            jsonObject.put("fileName", (isvideo)? UUID.randomUUID()+time+".mp4" : UUID.randomUUID()+time+".jpg");
            jsonObject.put("file", file);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mMyOkhttp.post()
                .url(ApiConstants.uploadfileApi)
                .jsonParams(jsonObject.toString())
                .enqueue(new GsonResponseHandler<updataInfo>() {
                    @Override
                    public void onFailure(int statusCode, String error_msg) {
                        hideLoadingDialog();
//                        path = null;
//                        edt_cpname.setText(null);
//                        edt_today_plan.setText(null);
//                        delete.setVisibility(View.GONE);
//                        iv_video_screenshot.setImageBitmap(null);
                        T(error_msg);
                    }

                    @Override
                    public void onSuccess(int statusCode, updataInfo response) {
                        hideLoadingDialog();
                        path = null;
                        edt_cpname.setText(null);
                        edt_today_plan.setText(null);
                        delete.setVisibility(View.GONE);
                        iv_video_screenshot.setImageBitmap(null);
                        T(response.getMessage());

                        Intent i = new Intent();
                        setResult(RESULT_OK, i);
                        finish();
                    }
                });
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            dialog.dismiss();
            switch (view.getId()){
                case R.id.pictrue :
//                    Toast.makeText(mContext, "图片", Toast.LENGTH_SHORT).show();
                    onAddPicClick();
                    break;
                case R.id.video:
//                    Toast.makeText(mContext, "视频", Toast.LENGTH_SHORT).show();
                    //视频录制
                    toClass(AddResourcesActivity.this,VideoRecorderActivity.class,null,REQUESTCODE);
                    break;
                default:
                    break;
            }
        }
    };
    selectDialog dialog;
    private void showSelectDialog() {

        selectDialog.Builder builder = new selectDialog.Builder(this);
        dialog = builder
                .style(R.style.Dialog)
                .cancelTouchout(false)
                .view(R.layout.dialog_select)
                .cancelTouchout(true)
                .addViewOnclick(R.id.pictrue,listener)
                .addViewOnclick(R.id.video,listener)
                .build();
        dialog.show();


    }

    private void deleteFile() {
        if (!TextUtils.isEmpty(path)) {
            File file = new File(path);
            if (file != null && file.exists()) {
                file.delete();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.e("TAG","resultCode:"+resultCode+"----requestCode:"+requestCode);
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
                        Log.i("原图片-----》", media.getPath());
                        Log.i("压缩图片-----》", media.getCompressPath());
                        //1.4M可压缩到500多K
//                        path = media.getPath();
                        path = media.getCompressPath();
                    }
                    isvideo = false;
                    setImage(path);

                    break;
                case REQUESTCODE:
                    isvideo = true;
                    path = data.getStringExtra("path");
                    setImage(path);
                    break;

                default:
                    break;

            }
        }

//        if (resultCode == 2) {
//            if (requestCode == REQUESTCODE) {
//
//                path = data.getStringExtra("path");
//
//                setImage(path);
//            }
//        }
    }

    private boolean isvideo;
    private String path = null;
    private Bitmap thumbnail;
    private void setImage(String videoUri){
        if (!TextUtils.isEmpty(videoUri)) {

            delete.setVisibility(View.VISIBLE);

//            File file = new File(videoUri);
//            long fileSize = getFileSize(file);
//            String formetFileSize = FormetFileSize(fileSize);
//            Log.d("upLoadImageToNet", "视频大小:" + ">>>>>>>>>" + formetFileSize);
//            spsize.setText("视频大小:" + formetFileSize);


            if(isvideo){
                thumbnail = VideoUltls.getVideoThumbnail(videoUri);
            }else {
                thumbnail = BitmapFactory.decodeFile(videoUri);
            }



        }
        if (thumbnail != null) {
            iv_video_screenshot.setImageBitmap(thumbnail);
        }
    }

    public void onAddPicClick() {
        // 进入相册 以下是例子：不需要的api可以不写
        PictureSelector.create(this)
                .openGallery(PictureMimeType.ofImage())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .theme(R.style.picture_default_style)// 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style
                .maxSelectNum(1)// 最大图片选择数量
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
//                .selectionMedia(selectList)// 是否传入已选图片
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


    public class MyAsyncTask extends AsyncTask<Integer, String, String> {
        public MyAsyncTask() {
            super();
        }

        //该方法运行在UI线程当中,并且运行在UI线程当中 可以对UI空间进行设置
        @Override
        protected void onPreExecute() {
            showLoadingDialog("正在努力上传...");
        }

        @Override
        protected String doInBackground(Integer... integers) {
            Log.e("xxxxxx","xxxxxxexecute传入参数="+integers[0]);

            String base64File = null;
            try {
                base64File = Utils.encodeBase64File(path);
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

            String time = new SimpleDateFormat("yyyy/MM/dd-HH:mm:ss",
                    Locale.getDefault()).format(System.currentTimeMillis());
            if(result != null){
                getDataFromNet(edt_cpname.getText().toString(),edt_today_plan.getText().toString(),result,time);
            }
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
