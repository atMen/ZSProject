package customer.tcrj.com.zsproject.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import customer.tcrj.com.zsproject.Utils.Utils;

/**
 * Created by leict on 2018/7/25.
 */

public class selectDialog extends Dialog {
        private Context context;
        private int height, width;
        private boolean cancelTouchout;
        private View view;

        private selectDialog(Builder builder) {
            super(builder.context);
            context = builder.context;
            height = builder.height;
            width = builder.width;
            cancelTouchout = builder.cancelTouchout;
            view = builder.view;
        }


        private selectDialog(Builder builder, int resStyle) {
            super(builder.context, resStyle);
            context = builder.context;
            height = builder.height;
            width = builder.width;
            cancelTouchout = builder.cancelTouchout;
            view = builder.view;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            setContentView(view);

            setCanceledOnTouchOutside(cancelTouchout);

            Window win = getWindow();
            WindowManager.LayoutParams lp = win.getAttributes();
            lp.gravity = Gravity.CENTER;

//            lp.height = height;//在外部设置
//            lp.width = width;
            lp.height = Utils.dip2px(context,180);
            lp.width = Utils.dip2px(context,250);
            win.setAttributes(lp);
        }

        public static final class Builder {

            private Context context;
            private int height, width;
            private boolean cancelTouchout;
            private View view;
            private int resStyle = -1;


            public Builder(Context context) {
                this.context = context;
            }

            public Builder view(int resView) {
                view = LayoutInflater.from(context).inflate(resView, null);
                return this;
            }

            public Builder heightpx(int val) {
                height = val;
                return this;
            }

            public Builder widthpx(int val) {
                width = val;
                return this;
            }

//            public Builder heightdp(int val) {
//                height = DensityUtil.dip2px(context, val);
//                return this;
//            }
//
//            public Builder widthdp(int val) {
//                width = DensityUtil.dip2px(context, val);
//                return this;
//            }

            public Builder heightDimenRes(int dimenRes) {
                height = context.getResources().getDimensionPixelOffset(dimenRes);
                return this;
            }

            public Builder widthDimenRes(int dimenRes) {
                width = context.getResources().getDimensionPixelOffset(dimenRes);
                return this;
            }

            public Builder style(int resStyle) {
                this.resStyle = resStyle;
                return this;
            }

            public Builder cancelTouchout(boolean val) {
                cancelTouchout = val;
                return this;
            }

            public Builder addViewOnclick(int viewRes,View.OnClickListener listener){
                view.findViewById(viewRes).setOnClickListener(listener);
                return this;
            }


            public selectDialog build() {
                if (resStyle != -1) {
                    return new selectDialog(this, resStyle);
                } else {
                    return new selectDialog(this);
                }
            }
        }

}
