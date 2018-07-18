package customer.tcrj.com.zsproject.Media;

import android.app.Activity;
import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.os.Environment;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by wanbo on 2017/1/18.
 */

public class MediaUtils implements SurfaceHolder.Callback {
    private static final String TAG = "MediaUtils";
    public static final int MEDIA_VIDEO = 1;
    private Activity activity;
    private MediaRecorder mMediaRecorder;
    private CamcorderProfile profile;
    private Camera mCamera;
    private SurfaceView mSurfaceView;
    private SurfaceHolder mSurfaceHolder;
    private File targetDir;
    private String targetName;
    private File targetFile;
    private int previewWidth, previewHeight;
    private int recorderType;
    private boolean isRecording;
    private boolean isOpenBackCamera = true;
    private GestureDetector mDetector;
    private boolean isZoomIn = false;

    public MediaUtils(Activity activity) {
        this.activity = activity;
    }

    public void setRecorderType(int type) {
        this.recorderType = type;
    }

    // 设置摄像头方向
    public void setOpenBackCamera(boolean isOpenBackCamera) {
        this.isOpenBackCamera = isOpenBackCamera;
    }

    public void setTargetDir(File file) {
        this.targetDir = file;
    }

    public void setTargetName(String name) {
        this.targetName = name;
    }

    public String getTargetFilePath() {
        return OutputFilepath;
    }

    public boolean deleteTargetFile() {
        if (targetFile.exists()) {
            return targetFile.delete();
        } else {
            return false;
        }
    }

    public void setSurfaceView(SurfaceView view) {
        this.mSurfaceView = view;
        mSurfaceHolder = mSurfaceView.getHolder();
        mSurfaceHolder.setFixedSize(previewWidth, previewHeight);
        mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        mSurfaceHolder.addCallback(this);
        mDetector = new GestureDetector(activity, new ZoomGestureListener());
        mSurfaceView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mDetector.onTouchEvent(event);
                return true;
            }
        });
    }

    public boolean isRecording() {
        return isRecording;
    }

    public void record() {
        if (isRecording) {
            try {
                mMediaRecorder.stop();  // stop the recording
            } catch (RuntimeException e) {
                // RuntimeException is thrown when stop() is called immediately after start().
                // In this case the output file is not properly constructed ans should be deleted.
                Log.d(TAG, "RuntimeException: stop() is called immediately after start()");
                //noinspection ResultOfMethodCallIgnored
                targetFile.delete();
            }
            releaseMediaRecorder(); // release the MediaRecorder object
            if (mCamera != null) {
                mCamera.lock();
            }
            // take camera access back from MediaRecorder
            isRecording = false;
        } else {
            startRecordThread();
        }
    }



    public void stopRecordSave() {
        Log.d("Recorder", "stopRecordSave");
        if (isRecording) {
            isRecording = false;
            try {
                mMediaRecorder.stop();
                Log.d("Recorder", targetFile.getPath());
            } catch (RuntimeException r) {
                Log.d("Recorder", "RuntimeException: stop() is called immediately after start()");
            } finally {
                releaseMediaRecorder();
            }
        }
    }

    public void stopRecordUnSave() {
        Log.d("Recorder", "stopRecordUnSave");
        if (isRecording) {
            isRecording = false;
            try {
                mMediaRecorder.stop();
            } catch (RuntimeException r) {
                Log.d("Recorder", "RuntimeException: stop() is called immediately after start()");
                if (targetFile.exists()) {
                    //不保存直接删掉
                    targetFile.delete();
                }
            } finally {
                releaseMediaRecorder();
            }
            if (targetFile.exists()) {
                //不保存直接删掉
                targetFile.delete();
            }
        }
    }

    private void setCameraFacing(SurfaceHolder holder) throws IOException {

        int cameraId;

        if (null != mCamera) {
            mCamera.stopPreview();
            mCamera.release();
        }

        if (isOpenBackCamera) {
            cameraId = Camera.CameraInfo.CAMERA_FACING_BACK;
        } else {
            cameraId = Camera.CameraInfo.CAMERA_FACING_FRONT;
        }
        Log.d("cameraId", "cameraId:" + cameraId);
        mCamera = Camera.open(cameraId);

        setCameraDisplayOrientation(cameraId, mCamera);

    }

    /**
     * 解决前置拍摄翻转问题
     **/
    public void setCameraDisplayOrientation(int cameraId, Camera camera) {
        Camera.CameraInfo info = new Camera.CameraInfo();
        Camera.getCameraInfo(cameraId, info);

        int rotation = activity.getWindowManager().getDefaultDisplay().getRotation();

        int degrees = 0;

        switch (rotation) {
            case Surface.ROTATION_0:
                degrees = 0;
                break;
            case Surface.ROTATION_90:
                degrees = 90;
                break;
            case Surface.ROTATION_180:
                degrees = 180;
                break;
            case Surface.ROTATION_270:
                degrees = 270;
                break;
        }

        int result;
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {

            result = (info.orientation + degrees) % 360;
            result = (360 - result) % 360;// compensate the mirror
        } else {// back-facing
            result = (info.orientation - degrees + 360) % 360;
        }

        camera.setDisplayOrientation(result);//显示翻转result度
        Log.e("CameraTest", info.facing + "" + result);
    }

    private void startPreView(SurfaceHolder holder) throws IOException {
        setCameraFacing(holder);

        if (mCamera != null) {
            try {
                Camera.Parameters parameters = mCamera.getParameters();
                List<Camera.Size> mSupportedPreviewSizes = parameters.getSupportedPreviewSizes();
                List<Camera.Size> mSupportedVideoSizes = parameters.getSupportedVideoSizes();
                Camera.Size optimalSize = CameraHelper.getOptimalVideoSize(mSupportedVideoSizes,
                        mSupportedPreviewSizes, mSurfaceView.getWidth(), mSurfaceView.getHeight());
                previewWidth = optimalSize.width;
                previewHeight = optimalSize.height;
                parameters.setPreviewSize(previewWidth, previewHeight);
                profile = CamcorderProfile.get(CamcorderProfile.QUALITY_480P);
                //这里是重点，分辨率和比特率
                //分辨率越大视频大小越大，比特率越大视频越清晰
                //清晰度由比特率决定，视频尺寸和像素量由分辨率决定
                //比特率越高越清晰（前提是分辨率保持不变），分辨率越大视频尺寸越大。
                profile.videoFrameWidth = optimalSize.width;
                profile.videoFrameHeight = optimalSize.height;
                //这样设置 1080p的视频 大小在5M , 可根据自己需求调节
                profile.videoBitRate = optimalSize.width * optimalSize.height;
                Log.e("angcyo", "optimalSize.width:"+optimalSize.width+"-----optimalSize.height:"+optimalSize.height);
                List<String> focusModes = parameters.getSupportedFocusModes();
                if (focusModes != null) {
                    for (String mode : focusModes) {
                        mode.contains("continuous-video");
                        parameters.setFocusMode("continuous-video");
                    }
                }
                mCamera.setParameters(parameters);//把上面的设置 赋给摄像头
                mCamera.cancelAutoFocus();
                mCamera.setPreviewDisplay(holder);//把摄像头获得画面显示在SurfaceView控件里面
                mCamera.startPreview();//开始预览

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void releaseMediaRecorder() {
        if (mMediaRecorder != null) {
            // clear recorder configuration
            mMediaRecorder.reset();
            // release the recorder object
            mMediaRecorder.release();
            mMediaRecorder = null;
            // Lock camera for later use i.e taking it back from MediaRecorder.
            // MediaRecorder doesn't need it anymore and we will release it if the activity pauses.
            Log.d("Recorder", "release Recorder");
        }
    }

    private void releaseCamera() {
        if (mCamera != null) {
            // release the camera for other applications
            mCamera.release();
            mCamera = null;
            Log.d("Recorder", "release Camera");
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mSurfaceHolder = holder;
        try {
            startPreView(holder);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        try {
            startPreView(holder);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (mCamera != null) {
            Log.d(TAG, "surfaceDestroyed: ");
            releaseCamera();
        }
        if (mMediaRecorder != null) {
            releaseMediaRecorder();
        }
    }

    private void startRecordThread() {
        if (prepareRecord()) {
            try {
                mMediaRecorder.start();
                isRecording = true;
                Log.d("Recorder", "Start Record");
            } catch (RuntimeException r) {
                releaseMediaRecorder();
                Log.d("Recorder", "RuntimeException: start() is called immediately after stop()");
            }
        }
    }

    private class ZoomGestureListener extends GestureDetector.SimpleOnGestureListener {
        //双击手势事件
        @Override
        public boolean onDoubleTap(MotionEvent e) {
            super.onDoubleTap(e);
            Log.d(TAG, "onDoubleTap: 双击事件");
            if (!isZoomIn) {
                setZoom(20);
                isZoomIn = true;
            } else {
                setZoom(0);
                isZoomIn = false;
            }
            return true;
        }
    }

    private void setZoom(int zoomValue) {
        if (mCamera != null) {
            Camera.Parameters parameters = mCamera.getParameters();
            if (parameters.isZoomSupported()) {
                int maxZoom = parameters.getMaxZoom();
                if (maxZoom == 0) {
                    return;
                }
                if (zoomValue > maxZoom) {
                    zoomValue = maxZoom;
                }
                parameters.setZoom(zoomValue);
                mCamera.setParameters(parameters);
            }
        }
    }

    private static final int MAX_DURATION = 10 * 1000;

    String OutputFilepath;

//    private boolean prepareRecord() {
//        try {
//            mCamera.unlock();//注意处,一定要调用此方法,否则会崩溃. 坑3
//        } catch (Exception e) {
//            Log.e("angcyo", e.getMessage());
//            return false;
//        }
//
//        if (mMediaRecorder == null) {
//            mMediaRecorder = new MediaRecorder();
//        }
//
//        mMediaRecorder.reset();//竟可能在调用其他方法之前,调用reset方法.避免状态异常调用.坑4
//        mMediaRecorder.setCamera(mCamera);//注意顺序,请注意此方法调用的顺序.调用顺序错了,会崩溃.坑5
//
//        CamcorderProfile camcorderProfile = CamcorderProfile.get(CamcorderProfile.QUALITY_HIGH);
//        //请注意, setVideoSource 和 setAudioSource 一定要比其他方法先调用.否则会崩溃. 坑6
//        mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
//        mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.DEFAULT);
//
//
//
////        mMediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);  //H263的貌似有点不清晰
//
//        //为了避免手动设置参数导致的崩溃,建议使用系统配置的参数.^~^,后面会介绍单独配置参数.
//        mMediaRecorder.setProfile(camcorderProfile);
//
//        mMediaRecorder.setVideoSize(1280,720);
//        //设置编码比特率,不设置会使视频图像模糊
////        mMediaRecorder.setVideoEncodingBitRate(5*1024*1024);  //清晰     512*1024(不清楚)
//        mMediaRecorder.setVideoEncodingBitRate(900*1024); //较为清晰，且文件大小为3.26M(30秒)
//        mMediaRecorder.setOrientationHint(90);//视频旋转90度
//
//        targetFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES) + File.separator + "VideoRecorder");
//        if (!targetFile.exists()) {
//            //多级文件夹的创建
//            targetFile.mkdirs();
//        }
//        String path = File.separator + "VID_" + System.currentTimeMillis() + ".mp4";
//        OutputFilepath  = targetFile.getPath() + path;
//        mMediaRecorder.setOutputFile(OutputFilepath);
//
//        try {
//            mMediaRecorder.prepare();
//        } catch (IllegalStateException e) {
//            Log.d("MediaRecorder", "IllegalStateException preparing MediaRecorder: " + e.getMessage());
//            releaseMediaRecorder();
//            return false;
//        } catch (IOException e) {
//            Log.d("MediaRecorder", "IOException preparing MediaRecorder: " + e.getMessage());
//            releaseMediaRecorder();
//            return false;
//        }
//        return true;
//    }

    private boolean prepareRecord() {
        try {
            mCamera.unlock();//注意处,一定要调用此方法,否则会崩溃. 坑3
        } catch (Exception e) {
            Log.e("angcyo", e.getMessage());
            return false;
        }

        if (mMediaRecorder == null) {
            mMediaRecorder = new MediaRecorder();
        }

        mMediaRecorder.reset();//竟可能在调用其他方法之前,调用reset方法.避免状态异常调用.坑4
        mMediaRecorder.setCamera(mCamera);//注意顺序,请注意此方法调用的顺序.调用顺序错了,会崩溃.坑5

//        CamcorderProfile camcorderProfile = CamcorderProfile.get(CamcorderProfile.QUALITY_480P);
        //请注意, setVideoSource 和 setAudioSource 一定要比其他方法先调用.否则会崩溃. 坑6
        mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
        mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.DEFAULT);

        mMediaRecorder.setOrientationHint(90);//视频旋转90度


        //为了避免手动设置参数导致的崩溃,建议使用系统配置的参数.^~^,后面会介绍单独配置参数.
//        mMediaRecorder.setProfile(camcorderProfile);

        CamcorderProfile profile;

        if (CamcorderProfile.hasProfile(CamcorderProfile.QUALITY_480P)) {
            profile = CamcorderProfile.get(CamcorderProfile.QUALITY_480P);
            Log.e("TAG","QUALITY_480P");
        } else if (CamcorderProfile.hasProfile(CamcorderProfile.QUALITY_720P)) {
            profile = CamcorderProfile.get(CamcorderProfile.QUALITY_720P);
            Log.e("TAG","QUALITY_720P");
        } else {
            profile = CamcorderProfile.get(CamcorderProfile.QUALITY_LOW);
            Log.e("TAG","QUALITY_LOW");
        }

        mMediaRecorder.setProfile(profile);


////         Step 3: Set a Camera Parameters
//        mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
//        /* Fixed video Size: 640 * 480*/
//        mMediaRecorder.setVideoSize(640,480);
////        mMediaRecorder.setVideoFrameRate(16);
//        /* Encoding bit rate: 1 * 1024 * 1024*/
//        mMediaRecorder.setVideoEncodingBitRate(500*1024);
//        mMediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);
//        mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);

        targetFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES) + File.separator + "VideoRecorder");
        if (!targetFile.exists()) {
            //多级文件夹的创建
            targetFile.mkdirs();
        }
        String path = File.separator + "VID_" + System.currentTimeMillis() + ".mp4";
        OutputFilepath  = targetFile.getPath() + path;
        mMediaRecorder.setOutputFile(OutputFilepath);

        try {
            mMediaRecorder.prepare();
        } catch (IllegalStateException e) {
            Log.d("MediaRecorder", "IllegalStateException preparing MediaRecorder: " + e.getMessage());
            releaseMediaRecorder();
            return false;
        } catch (IOException e) {
            Log.d("MediaRecorder", "IOException preparing MediaRecorder: " + e.getMessage());
            releaseMediaRecorder();
            return false;
        }
        return true;
    }

}
