package customer.tcrj.com.zsproject.Media;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.jaeger.library.StatusBarUtil;

import customer.tcrj.com.zsproject.R;


public class VideoPlayerActivity extends AppCompatActivity {

    MyVideoView vvView;
    ImageView iv_play_video;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);
        StatusBarUtil.setColor(this, getResources().getColor(R.color.black),5);
        iv_play_video= findViewById(R.id.iv_play_video);
        vvView = findViewById(R.id.vv_view);
        initView(savedInstanceState);
    }


    public void initView(Bundle savedInstanceState) {
        String path = getIntent().getStringExtra("path");
        if (!TextUtils.isEmpty(path)) {
            vvView.setVideoPath(path);
            iv_play_video.setVisibility(View.GONE);
            vvView.start();
        }

        initListener();
    }

    private void initListener() {
        iv_play_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!vvView.isPlaying()) {
                    iv_play_video.setVisibility(View.GONE);
                    vvView.start();
                }
            }
        });


        vvView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                iv_play_video.setVisibility(View.VISIBLE);
            }
        });
    }


}
