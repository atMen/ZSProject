package customer.tcrj.com.zsproject.Utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;

import customer.tcrj.com.zsproject.R;

/**
 * Created by leict on 2018/6/20.
 */

public class ShowImageUtils {

    /**
     * (1)
     * 显示图片Imageview
     *
     * @param context  上下文
     * @param errorimg 错误的资源图片
     * @param url      图片链接
     * @param imgeview 组件
     */
    public static void showImageView(Context context, String url,
                                     ImageView imgeview) {
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.ic_placeholder)
                .error(R.drawable.ic_placeholder)
                .centerCrop()
                ;
        Glide.with(context)
                .load(url)
                .transition(DrawableTransitionOptions.withCrossFade())
                .apply(options)
                .into(imgeview);
    }


    /**
     * 常规使用
     *.transition(new DrawableTransitionOptions().crossFade(800))
     * @param context   上下文
     * @param url       图片链接
     * @param imageView 目标view
     */
    public static void LoadImage(Context context, Object url, ImageView imageView) {
        Glide.with(context).load(url)
                .apply(new RequestOptions()
                        .placeholder(R.drawable.ic_placeholder)
                        .error(R.drawable.ic_placeholder)
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.ALL))
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageView);
    }

}
