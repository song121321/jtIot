package song.jtslkj.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.jtslkj.R;

import song.jtslkj.adapter.WellAttrAdapter;
import song.jtslkj.app.MyApplication;
import song.jtslkj.bean.WellBean;
import song.jtslkj.util.ToolBox;

public class DeviceWellDetailActivity extends Activity {
    WellBean wb;
    ImageView iv_statePic;
    TextView tv_title;
    ImageView iv_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_well_detail);
        MyApplication.getInstance().addActivity(this);
        findView();
        initData();
        initView();
        setClicker();
    }

    private void findView() {
        iv_back = (ImageView) findViewById(R.id.iv_actionbar_left);
        tv_title = (TextView) findViewById(R.id.tv_actionbar_usual_title);
        iv_statePic = (ImageView) findViewById(R.id.iv_device_well_detail_pic);

    }

    private void initData() {
        Intent intent = this.getIntent();
        wb = (WellBean) intent.getSerializableExtra("wellBean");

    }

    private void initView() {
        String[] attrArray = new String[wb.getAttr().size()];
        for (int i = 0; i < wb.getAttr().size(); i++) {
            attrArray[i] = ToolBox.getMapStr(wb.getAttr().get(i));
        }
        WellAttrAdapter wellAttrAdapter = new WellAttrAdapter(DeviceWellDetailActivity.this,wb.getAttr());
        ListView lv = (ListView) findViewById(R.id.lv_device_well_detail_attr);
        lv.setAdapter(wellAttrAdapter);

        if (wb.getState() != 1) {
            iv_statePic.setBackgroundResource(R.drawable.offline);
        } else {
            iv_statePic.setBackgroundResource(R.drawable.online);
        }

        tv_title.setText(wb.getWellName());

    }

    private void setClicker() {
        iv_back.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                MyApplication.getInstance().removeActivity(
                        DeviceWellDetailActivity.this);
                finish();
            }
        });
    }

}
