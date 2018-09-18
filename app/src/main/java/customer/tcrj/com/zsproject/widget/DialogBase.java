package customer.tcrj.com.zsproject.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import customer.tcrj.com.zsproject.R;
import customer.tcrj.com.zsproject.Utils.Utils;

/**
 * Created by leict on 2017/11/15.
 */

public abstract class DialogBase extends Dialog implements DialogInterface {
    private Window window = null;
    private Context context = null;

    public DialogBase(Context context) {
        super(context, R.style.Theme_picker);
        this.context = context;
        window = getWindow();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleContent();
        setContainer();
        setWindowParams();
    }

    public void setWindowParams() {
        WindowManager.LayoutParams p = window.getAttributes(); // 获取对话框当前的参数值
        p.width = (int) (Utils.getWidth(context) * 0.8);       // 宽度设置为屏幕的0.8
        window.setAttributes(p);
    }

    public void addView(View view) {
        if (window == null) {
            return;
        }
        window.setContentView(view);
    }

    protected void onStop() {
    }

    public View.OnClickListener viewOnClickListen = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            OnClickListenEvent(v);
        }
    };
}
