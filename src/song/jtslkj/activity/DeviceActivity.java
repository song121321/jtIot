package song.jtslkj.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jtslkj.R;

import song.jtslkj.app.MyApplication;

public class DeviceActivity extends BaseActivity implements OnClickListener {
    TextView tvTopMiddle;
    RelativeLayout rePump, reWell, reFertilizer, reValve, reSta, reIrrigationLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_main);
        MyApplication.getInstance().addActivity(this);
        findViewByid();
        initView();
        setClicker();
    }

    private void findViewByid() {
        tvTopMiddle = (TextView) findViewById(R.id.tv_actionbar_usual_title);
        rePump = (RelativeLayout) findViewById(R.id.re_device_main_pump);
        reWell = (RelativeLayout) findViewById(R.id.re_device_main_well);
        reFertilizer = (RelativeLayout) findViewById(R.id.re_device_main_fertilizer);
        reValve = (RelativeLayout) findViewById(R.id.re_device_main_valve);
        reIrrigationLog = (RelativeLayout) findViewById(R.id.re_device_main_irrigation_log);
        reSta = (RelativeLayout) findViewById(R.id.re_device_main_sta);
    }

    private void setClicker() {
        rePump.setOnClickListener(this);
        reFertilizer.setOnClickListener(this);
        reIrrigationLog.setOnClickListener(this);
        reSta.setOnClickListener(this);
        reWell.setOnClickListener(this);
        reValve.setOnClickListener(this);
    }

    private void initView() {
        tvTopMiddle.setText(R.string.device);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.re_device_main_well:
                Intent deviceWellIntent = new Intent(DeviceActivity.this,
                        DeviceWellActivity.class);
                startActivity(deviceWellIntent);
                break;
            case R.id.re_wojia_main_myinfo:
                break;
            case R.id.re_wojia_main_friendsquan:
                break;
            case R.id.re_wojia_main_qianbao:
                break;
            case R.id.re_wojia_main_setting:

                Intent intent = new Intent(DeviceActivity.this,
                        WojiaSettingActivity.class);
                startActivity(intent);
                break;
        }
    }
}
