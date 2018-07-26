package customer.tcrj.com.zsproject.resources;

import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import customer.tcrj.com.zsproject.R;
import customer.tcrj.com.zsproject.Utils.ShowImageUtils;
import uk.co.senab.photoview.PhotoView;

import static android.R.attr.thumbnail;

public class ImageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_image);
        PhotoView viewById = (PhotoView) findViewById(R.id.image);
        String path = getIntent().getStringExtra("path");
        if (!TextUtils.isEmpty(path)) {
//            viewById.setImageBitmap(BitmapFactory.decodeFile(path));
//            viewById.setImageURI();
            ShowImageUtils.loadIntoUseFitWidth(this,path,viewById);
//            Glide.with(context).load(path)

        }


    }
}
