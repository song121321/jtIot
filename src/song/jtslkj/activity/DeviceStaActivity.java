package song.jtslkj.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jtslkj.R;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.HashMap;
import java.util.List;

import song.jtslkj.adapter.StaAdapter;
import song.jtslkj.app.MyApplication;
import song.jtslkj.bean.StaBean;
import song.jtslkj.config.MyConfig;
import song.jtslkj.util.ParseTools;
import song.jtslkj.util.WebServiceUtil;

public class DeviceStaActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    private ListView chooseList;
    private RefreshLayout mRefreshLayout;
    private LinearLayout llBack;
    private TextView tvTitle;
    private List<StaBean> staBeans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_sta);
        MyApplication.getInstance().addActivity(this);
        findViewById();
        initView();
        setClicker();
    }

    private void findViewById() {
        chooseList = (ListView) findViewById(R.id.lv_device_sta);
        tvTitle = (TextView) findViewById(R.id.tv_actionbar_usual_title);
        mRefreshLayout = (RefreshLayout) findViewById(R.id.refreshLayout);
        llBack = (LinearLayout) findViewById(R.id.ll_actionbar_left);
    }

    private void initView() {
        initActionBar();
        refresh();
    }


    private void setAdapter(List<StaBean> staBeans) {

        StaAdapter staAdapter = new StaAdapter(DeviceStaActivity.this, staBeans);
        chooseList.setAdapter(staAdapter);
        closeLoadingDialog();
    }

    private void initActionBar() {
        tvTitle.setText(getResources().getString(R.string.device_main_sta));
        llBack.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                MyApplication.getInstance().removeActivity(
                        DeviceStaActivity.this);
                finish();

            }
        });

    }

    private void refresh() {
        showLoadingDialog();
        new StaDownLoadTask().execute();
    }

    private void setClicker() {
        chooseList.setOnItemClickListener(this);
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refresh();
                refreshlayout.finishRefresh();
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        String text = chooseList.getItemAtPosition(position) + "";
        Toast.makeText(this, "position=" + position + "text=" + text,
                Toast.LENGTH_SHORT).show();
        Intent deviceStaIntent = new Intent(DeviceStaActivity.this,
                DeviceStaTableActivity.class);
        startActivity(deviceStaIntent);
    }

    private class StaDownLoadTask extends AsyncTask<Void, Void, String> {

        StaDownLoadTask() {
            super();
        }

        @Override
        protected String doInBackground(Void... arg0) {
            HashMap<String, String> params = new HashMap<String, String>();
            String nameSpace = MyConfig.nameSpace;
            String methodName = MyConfig.methodName_GetStaChooseList;
            String endPoint = MyConfig.endPoint;
            return WebServiceUtil.getAnyType(nameSpace, methodName,
                    endPoint, params);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            staBeans = ParseTools.json2StaBeanList(result);
            setAdapter(staBeans);
        }
    }

}
