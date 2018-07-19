package customer.tcrj.com.zsproject.resources;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;
import customer.tcrj.com.zsproject.Media.MediaUtils;
import customer.tcrj.com.zsproject.Media.Utils;
import customer.tcrj.com.zsproject.Media.VideoPlayerActivity;
import customer.tcrj.com.zsproject.Media.VideoRecorderActivity;
import customer.tcrj.com.zsproject.Media.VideoUltls;
import customer.tcrj.com.zsproject.R;
import customer.tcrj.com.zsproject.base.BaseFragment;

import static android.R.attr.thumbnail;


/**
 * Created by leict on 2018/3/22.
 */

public class ResourcesFragment extends BaseFragment {

    @BindView(R.id.iv_video_screenshot)
    ImageView iv_video_screenshot;

    @BindView(R.id.iv_btn)
    ImageView iv_btn;

    @BindView(R.id.delete)
    ImageView delete;



    private int REQUESTCODE = 100;

    @Override
    protected int setLayout() {
        return R.layout.resources_fragment;
    }

    @Override
    protected void setView() {

    }

    @Override
    protected void setData() {


    }

    @OnClick({R.id.iv_video_screenshot,R.id.iv_btn,R.id.delete})
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.iv_btn:
            case R.id.iv_video_screenshot:

                if(path != null){


                    Bundle bundle = new Bundle();
                    bundle.putString("path",path);
                    toClass(mContext,VideoPlayerActivity.class,bundle);

                }else {

                    //视频录制
                    toClass(mContext,VideoRecorderActivity.class,null,REQUESTCODE);
                }

                break;

            case R.id.delete:
                path = null;
                delete.setVisibility(View.GONE);
                iv_video_screenshot.setImageBitmap(null);
                deleteFile();
                break;

            default:
                break;
        }

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

        if (resultCode == 2) {
            if (requestCode == REQUESTCODE) {

                path = data.getStringExtra("path");

                setImage(path);
            }
        }
    }

    private String path = null;
    private Bitmap thumbnail;
    private void setImage(String videoUri){
        if (!TextUtils.isEmpty(videoUri)) {

            delete.setVisibility(View.VISIBLE);
            try {
                String s = Utils.encodeBase64File(videoUri);
                Log.e("TAG","SPbase64:"+s);
            } catch (Exception e) {
                e.printStackTrace();
            }
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
}
