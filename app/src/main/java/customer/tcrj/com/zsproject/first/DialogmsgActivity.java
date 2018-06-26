package customer.tcrj.com.zsproject.first;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import customer.tcrj.com.zsproject.R;


public class DialogmsgActivity extends Activity implements View.OnClickListener{
    private LinearLayout ll_btn;
    private ImageView iv_close;
    private TextView title,name;
    private TextView time;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialogmsg);
        findview();
        initview();
    }

    private void initview() {


    }

    private void findview() {



    }

    @Override
    public void onClick(View v) {
        finish();

    }






}
