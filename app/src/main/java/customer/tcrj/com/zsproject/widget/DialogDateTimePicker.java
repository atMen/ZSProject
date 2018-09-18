package customer.tcrj.com.zsproject.widget;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;

import customer.tcrj.com.zsproject.R;
import customer.tcrj.com.zsproject.Utils.Utils;


/**
 * Created by leict on 2017/11/15.
 */

public class DialogDateTimePicker extends DialogBase {
    private DatePicker dialogDatePicker;
    private TimePicker dialogTimePicker;
    private Button btnPickerSubmit;
    private Button btnPickerCancel;
    private DatePickerCallBack callBack = null;

    public DialogDateTimePicker(Context context) {
        super(context);

    }

    @Override
    public void setTitleContent() {

    }

    @Override
    public void setContainer() {
        this.setCanceledOnTouchOutside(true);
        View view = Utils.getLayoutInflater(getContext()).inflate(R.layout.item_timepicker_selector, null);
        addView(view);
        dialogDatePicker = (DatePicker) findViewById(R.id.dialog_date_picker);
        dialogTimePicker = (TimePicker) findViewById(R.id.dialog_time_picker);
        btnPickerSubmit = (Button) findViewById(R.id.btn_picker_submit);
        btnPickerCancel = (Button) findViewById(R.id.btn_picker_cancel);
        dialogTimePicker.setIs24HourView(true);
        btnPickerCancel.setOnClickListener(viewOnClickListen);
        btnPickerSubmit.setOnClickListener(viewOnClickListen);

//        dialogDatePicker.setMinDate(System.currentTimeMillis() - 1000);



    }

    public void setMinData(long time){
        //TODO:提供向外的接口设置可选最小时间
        Log.e("TAG","设置最小时间");
        if(dialogDatePicker != null){
            dialogDatePicker.setMinDate(time);
        }else{
            Log.e("TAG","设置最小时间时对象为空");
        }

    }

    @Override
    public void OnClickListenEvent(View v) {
        switch (v.getId()) {
            case R.id.btn_picker_cancel:
                dismiss();
                break;
            case R.id.btn_picker_submit:
                if (callBack != null) {
                    callBack.onClickListener(getTime(dialogDatePicker, dialogTimePicker));
                    dismiss();
                }
                break;
        }
    }

    private String getTime(DatePicker date, TimePicker Time) {
        StringBuffer time = new StringBuffer();
        int year = date.getYear();
        int month = date.getMonth() + 1;
        int day = date.getDayOfMonth();
        int hour = Time.getCurrentHour();
        int min = Time.getCurrentMinute();
        time.append(year);
        time.append("-");
        if (1 <= month && month <= 9) {
            time.append("0");
        }
        time.append(month);
        time.append("-");
        if (1 <= day && day <= 9) {
            time.append("0");
        }
        time.append(day);
        time.append("  ");
        if (0 <= hour && hour <= 9) {
            time.append("0");
        }
        time.append(hour);
        time.append(":");
        if (0 <= min && min <= 9) {
            time.append("0");
        }
        time.append(min);
        return time.toString();
    }

    public void onDatePickerListener(DatePickerCallBack callback) {
        this.callBack = callback;
    }

    public interface DatePickerCallBack {
        void onClickListener(String time);
    }
}
