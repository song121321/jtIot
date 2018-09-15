package song.jtslkj.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import com.bin.david.form.core.SmartTable;
import com.jtslkj.R;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.HashMap;
import java.util.List;

import song.jtslkj.app.MyApplication;
import song.jtslkj.bean.StaDayNetBean;
import song.jtslkj.config.MyConfig;
import song.jtslkj.util.ParseTools;
import song.jtslkj.util.WebServiceUtil;

public class DeviceStaActivity extends BaseActivity {

    private RefreshLayout mRefreshLayout;
    private SmartTable<StaDayNetBean> smartTable;
    private TextView tv_title;
    private List<StaDayNetBean> tableDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_sta_table);
        MyApplication.getInstance().addActivity(this);
        findViewByid();
        setClicker();
        refresh();
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refresh();
                refreshlayout.finishRefresh();
            }
        });
    }

    private void findViewByid() {
        mRefreshLayout = (RefreshLayout) findViewById(R.id.refreshLayout);
        smartTable = (SmartTable<StaDayNetBean>) findViewById(R.id.st_device_sta_table);
        tv_title = (TextView) findViewById(R.id.tv_actionbar_usual_title);
    }

    private void setClicker() {


    }

    private void refresh() {
        showLoadingDialog();
        new StaNetDownLoadTask().execute();
    }

    private void drawData() {
        tv_title.setText("日净统计");
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
