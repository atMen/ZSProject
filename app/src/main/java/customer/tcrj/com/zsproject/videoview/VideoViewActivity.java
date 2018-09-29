package customer.tcrj.com.zsproject.videoview;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kk.taurus.playerbase.assist.OnVideoViewEventHandler;
import com.kk.taurus.playerbase.entity.DataSource;
import com.kk.taurus.playerbase.event.OnPlayerEventListener;
import com.kk.taurus.playerbase.receiver.ReceiverGroup;
import com.kk.taurus.playerbase.widget.BaseVideoView;
import com.tsy.sdk.myokhttp.MyOkHttp;
import com.tsy.sdk.myokhttp.response.GsonResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import customer.tcrj.com.zsproject.MyApp;
import customer.tcrj.com.zsproject.R;
import customer.tcrj.com.zsproject.Utils.ACache;
import customer.tcrj.com.zsproject.bean.loginInfo;
import customer.tcrj.com.zsproject.bean.resourcesInfo;
import customer.tcrj.com.zsproject.bean.xslcCxInfo;
import customer.tcrj.com.zsproject.dialog.SweetAlertDialog;
import customer.tcrj.com.zsproject.net.ApiConstants;
import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;


/**
 * Created by Taurus on 2018/4/19.
 */

public class VideoViewActivity extends AppCompatActivity implements OnPlayerEventListener {


    TextView name;
    TextView edt_today_plan;

    Button btn_true;

    private resourcesInfo.DataBean info;

    private BaseVideoView mVideoView;
    private int margin;

    private boolean permissionSuccess;


    private ReceiverGroup mReceiverGroup;
    private boolean isLandscape;

    private boolean userPause;

    private MyOkHttp mMyOkhttp;
    private String token;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_video_view);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN
                , WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mMyOkhttp = MyApp.getInstance().getMyOkHttp();
        token = ACache.get(this).getAsString("token");
        margin = PUtil.dip2px(this,2);
        info = (resourcesInfo.DataBean) getIntent().getSerializableExtra("qyzyinfo");
        name = findViewById(R.id.name);
        edt_today_plan = findViewById(R.id.edt_today_plan);
        findViewById(R.id.btn_true).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(VideoViewActivity.this, "删除", Toast.LENGTH_SHORT).show();
                showUpdateDialog();
            }
        });

        mVideoView = (BaseVideoView) findViewById(R.id.videoView);


        name.setText(info.getClmc());
        edt_today_plan.setText(info.getClms());

        PermissionGen.with(this)
                .addRequestCode(101)
                .permissions(
                        Manifest.permission.RECORD_AUDIO,
                        Manifest.permission.MODIFY_AUDIO_SETTINGS
                )
                .request();





    }

    private void showUpdateDialog() {

/*		Intent intent = new Intent();
        intent.setClass(mContext, IsDownLoad.class);
		mContext.startActivity(intent);*/
        final SweetAlertDialog sad = new SweetAlertDialog(this);
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
                deleResources();
                sad.dismiss();

            }
        });
        sad.show();
    }

    private void deleResources() {

//            showLoadingDialog("正在删除数据");

            JSONObject jsonObject = new JSONObject();

            try {
                jsonObject.put("token", token);
                jsonObject.put("id", info.getId());

            } catch (JSONException e) {
                e.printStackTrace();
            }

            mMyOkhttp.post()
                    .url(ApiConstants.qyzydeleinfoApi)
                    .jsonParams(jsonObject.toString())
                    .tag(this)
                    .enqueue(new GsonResponseHandler<loginInfo>() {
                        @Override
                        public void onFailure(int statusCode, String error_msg) {

                            Log.e("TAG","msg"+statusCode);
                        }

                        @Override
                        public void onSuccess(int statusCode, loginInfo response) {

                            Log.e("TAG","getErrorcode:"+response.getErrorcode());
                            if(response.getErrorcode().equals("9999")){
                                Log.e("TAG",response.getMessage());
                                Intent i = new Intent();
                                setResult(RESULT_OK, i);
                                finish();

                            }
                        }
                    });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        PermissionGen.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }

    @PermissionSuccess(requestCode = 101)
    public void permissionSuccess() {
        permissionSuccess = true;
        initPlay();
    }

    @PermissionFail(requestCode = 101)
    public void permissionFailure(){
        permissionSuccess = false;
        initPlay();
    }

    private void initPlay() {
        updateVideo(false);

        mVideoView.setOnPlayerEventListener(this);
        mVideoView.setEventHandler(mOnEventAssistHandler);
        mReceiverGroup = ReceiverGroupManager.get().getReceiverGroup(this, null);
        mReceiverGroup.getGroupValue().putBoolean(DataInter.Key.KEY_NETWORK_RESOURCE, true);
        mReceiverGroup.getGroupValue().putBoolean(DataInter.Key.KEY_CONTROLLER_TOP_ENABLE, true);
        mReceiverGroup.getGroupValue().putBoolean(DataInter.Key.KEY_IS_HAS_NEXT, true);
        mVideoView.setReceiverGroup(mReceiverGroup);


//        String file = getIntent().getStringExtra("cover");

        String cllj = info.getCllj();
        if(cllj != null && !"".equals(cllj)){
            String videopath = ApiConstants.ImageURLROOT + cllj.replace("\\", "/");
            DataSource data = new DataSource(videopath);
            mVideoView.setDataSource(data);
            mVideoView.start();
        }else {
            Toast.makeText(this, "视频资源不存在", Toast.LENGTH_SHORT).show();
        }


        // If you want to start play at a specified time,
        // please set this method.
        //mVideoView.start(30*1000);
    }

    private DataSource generatorDataSource(long id){
        DataSource dataSource = new DataSource();
        dataSource.setId(id);
        return dataSource;
    }




    private void updateVideo(boolean landscape){
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mVideoView.getLayoutParams();
        if(landscape){
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
            layoutParams.setMargins(0, 0, 0, 0);
        }else{
            layoutParams.width = PUtil.getScreenW(this) - (margin*2);
            layoutParams.height = layoutParams.width * 9/16;
            layoutParams.setMargins(margin, margin, margin, margin);
        }
        mVideoView.setLayoutParams(layoutParams);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mVideoView.isInPlaybackState()){
            mVideoView.pause();
        }else{
            mVideoView.stop();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mVideoView.isInPlaybackState()){
            if(!userPause)
                mVideoView.resume();
        }else{
            mVideoView.rePlay(0);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mVideoView.stopPlayback();
    }

    @Override
    public void onBackPressed() {
        if(isLandscape){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            return;
        }
        super.onBackPressed();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if(newConfig.orientation==Configuration.ORIENTATION_LANDSCAPE){
            isLandscape = true;
            updateVideo(true);
        }else{
            isLandscape = false;
            updateVideo(false);
        }
        mReceiverGroup.getGroupValue().putBoolean(DataInter.Key.KEY_IS_LANDSCAPE, isLandscape);
    }

    private OnVideoViewEventHandler mOnEventAssistHandler = new OnVideoViewEventHandler(){
        @Override
        public void onAssistHandle(BaseVideoView assist, int eventCode, Bundle bundle) {
            super.onAssistHandle(assist, eventCode, bundle);
            switch (eventCode){
                case DataInter.Event.CODE_REQUEST_PAUSE:
                    userPause = true;
                    break;
                case DataInter.Event.EVENT_CODE_REQUEST_BACK:
                    if(isLandscape){
                        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    }else{
                        finish();
                    }
                    break;
                case DataInter.Event.EVENT_CODE_REQUEST_NEXT:
//                    mDataSourceId++;
//                    mVideoView.setDataSource(generatorDataSource(mDataSourceId));
//                    mVideoView.start();
                    break;
                case DataInter.Event.EVENT_CODE_REQUEST_TOGGLE_SCREEN:
                    setRequestedOrientation(isLandscape ?
                            ActivityInfo.SCREEN_ORIENTATION_PORTRAIT:
                            ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    break;
                case DataInter.Event.EVENT_CODE_ERROR_SHOW:
                    mVideoView.stop();
                    break;
            }
        }
    };


    @Override
    public void onPlayerEvent(int eventCode, Bundle bundle) {
        switch (eventCode){

            case OnPlayerEventListener.PLAYER_EVENT_ON_PLAY_COMPLETE:

                break;
            case OnPlayerEventListener.PLAYER_EVENT_ON_RESUME:
                userPause = false;
                break;
        }
    }


}
