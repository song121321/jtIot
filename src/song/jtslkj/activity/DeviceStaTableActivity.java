package song.jtslkj.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bin.david.form.core.SmartTable;
import com.hdl.calendardialog.CalendarView;
import com.hdl.calendardialog.CalendarViewDialog;
import com.jtslkj.R;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import song.jtslkj.app.MyApplication;
import song.jtslkj.bean.StaDayNetBean;
import song.jtslkj.config.MyConfig;
import song.jtslkj.util.ParseTools;
import song.jtslkj.util.StringUtil;
import song.jtslkj.util.WebServiceUtil;

public class DeviceStaTableActivity extends BaseActivity {

    private RefreshLayout mRefreshLayout;
    private SmartTable<StaDayNetBean> smartTable;
    private TextView tvTitle;
    private List<StaDayNetBean> tableDataList;
    private String day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_sta_table);
        MyApplication.getInstance().addActivity(this);
        findViewByid();
        setClicker();
        initView();
        refresh();

    }

    private void findViewByid() {
        mRefreshLayout = (RefreshLayout) findViewById(R.id.refreshLayout);
        smartTable = (SmartTable<StaDayNetBean>) findViewById(R.id.st_device_sta_table);
        tvTitle = (TextView) findViewById(R.id.tv_actionbar_usual_title);
    }

    private void initView() {

        day = StringUtil.getCurrentDateStr();

    }

    private void setClicker() {
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refresh();
                refreshlayout.finishRefresh();
            }
        });
        final List<Long>  markDays = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            markDays.add(System.currentTimeMillis() - i * 24 * 60 * 60 * 1000);
        }
        tvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CalendarViewDialog.getInstance()
                        .init(DeviceStaTableActivity.this)
                        .addMarks(markDays)
                        // .setLimitMonth(true) //限制只能选2个月
                        .show(new CalendarView.OnCalendarClickListener() {
                            @Override
                            public void onDayClick(Calendar calendar) {
                                CalendarViewDialog.getInstance().close();
                                int monthInt = calendar.get(Calendar.MONTH) + 1;
                                day = calendar.get(Calendar.YEAR) +"-"+( monthInt<10? "0"+monthInt:monthInt)+"-"+calendar.get(Calendar.DAY_OF_MONTH);
                                Toast.makeText(DeviceStaTableActivity.this, "daySelect:"+day, Toast.LENGTH_SHORT).show();
                                refresh();
                            }

                            @Override
                            public void onDayNotMarkClick(Calendar daySelectedCalendar) {
                                Toast.makeText(DeviceStaTableActivity.this, "当前时间无回放（没有标记）", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }

    private void refresh() {
        showLoadingDialog();
        new StaNetDownLoadTask().execute();
    }

    private void drawData() {
        String staType = getString(R.string.device_sta_day_net);
        tvTitle.setText(String.format(staType, day));
        smartTable.setData(tableDataList);
        smartTable.getConfig().setShowXSequence(false);
        smartTable.getConfig().setShowYSequence(false);
        smartTable.getConfig().setShowTableTitle(false);
        smartTable.getConfig().setFixedCountRow(true);
        // smartTable.getConfig().setContentStyle(new FontStyle(50, Color.BLUE));

        smartTable.setZoom(true);
        closeLoadingDialog();

    }

    private class StaNetDownLoadTask extends AsyncTask<Void, Void, String> {

        StaNetDownLoadTask() {
            super();
        }

        @Override
        protected String doInBackground(Void... arg0) {
            HashMap<String, String> params = new HashMap<String, String>();
            params.put("dateStr",day);
            String nameSpace = MyConfig.nameSpace;
            String methodName = MyConfig.methodName_GetStaDayNet;
            String endPoint = MyConfig.endPoint;
            return WebServiceUtil.getAnyType(nameSpace, methodName,
                    endPoint, params);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            tableDataList = ParseTools.json2StaDayNetBeanList(result);
            drawData();
        }
    }


}
