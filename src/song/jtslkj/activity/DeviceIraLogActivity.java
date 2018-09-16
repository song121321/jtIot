package song.jtslkj.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.jtslkj.R;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.HashMap;
import java.util.List;

import song.jtslkj.adapter.IraLogAdapter;
import song.jtslkj.app.MyApplication;
import song.jtslkj.bean.IraLogBean;
import song.jtslkj.config.MyConfig;
import song.jtslkj.util.ParseTools;
import song.jtslkj.util.WebServiceUtil;

public class DeviceIraLogActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    private ListView iraLogListView;
    private RefreshLayout mRefreshLayout;
    private LinearLayout llBack;
    private TextView tvTitle;
    private List<IraLogBean> iraLogBeans;
    private MaterialDialog.Builder mBuilder;
    private MaterialDialog mMaterialDialog;
    private String[] stocks = {"海虹控股", "科大讯飞", "中科创达", "掌阅科技", "沃特股份", "海虹控股", "科大讯飞", "中科创达", "掌阅科技", "沃特股份", "海虹控股", "科大讯飞", "中科创达", "掌阅科技", "沃特股份"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //here we reuse the sta layout ,it's correct, not a mistake
        setContentView(R.layout.activity_device_sta);
        MyApplication.getInstance().addActivity(this);
        findViewById();
        initView();
        setClicker();
    }

    private void findViewById() {
        iraLogListView = (ListView) findViewById(R.id.lv_device_sta);
        tvTitle = (TextView) findViewById(R.id.tv_actionbar_usual_title);
        mRefreshLayout = (RefreshLayout) findViewById(R.id.refreshLayout);
        llBack = (LinearLayout) findViewById(R.id.ll_actionbar_left);
    }

    private void initView() {
        initActionBar();
        refresh();
    }


    private void setAdapter(List<IraLogBean> staBeans) {

        IraLogAdapter iraLogAdapter = new IraLogAdapter(DeviceIraLogActivity.this, staBeans);
        iraLogListView.setAdapter(iraLogAdapter);
        closeLoadingDialog();
    }

    private void initActionBar() {
        tvTitle.setText(getResources().getString(R.string.device_main_irrigation_log));
        llBack.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                MyApplication.getInstance().removeActivity(
                        DeviceIraLogActivity.this);
                finish();

            }
        });

    }

    private void refresh() {

//        mBuilder = new MaterialDialog.Builder(DeviceIraLogActivity.this);
//        mBuilder.title("淘牛指数");
//        mBuilder.titleGravity(GravityEnum.CENTER);
//        mBuilder.theme(Theme.LIGHT);
//        mBuilder.content("针对所有讲师的图文直播、内参、问股及用户搜索热度相关个股利用大数据检索，根据量化策略筛选出对应个股的热度指数，指数越高与讲师关联度越强.(此列表2分钟更新一次)");
//        mMaterialDialog = mBuilder.build();
//        mMaterialDialog.show();
        showLoadingDialog();
        new IraLogDownLoadTask().execute();
    }

    private void setClicker() {
        tvTitle.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mBuilder = new MaterialDialog.Builder(DeviceIraLogActivity.this);
                mBuilder.title("选择股票");
                mBuilder.titleGravity(GravityEnum.CENTER);
                mBuilder.items(stocks);
                mBuilder.theme(Theme.LIGHT);
                mBuilder.autoDismiss(true);
                mBuilder.itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                        Toast.makeText(DeviceIraLogActivity.this, text, Toast.LENGTH_SHORT).show();
                    }
                });
                mMaterialDialog = mBuilder.build();
                mMaterialDialog.show();
            }
        });
        iraLogListView.setOnItemClickListener(this);
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
        IraLogBean iraLogBean = iraLogBeans.get(position);
        String content = getResources().getString(R.string.device_ira_log_last_card_number) + iraLogBean.getLastCardNumber() + "\n"
                + getResources().getString(R.string.device_ira_log_last_elec) + iraLogBean.getLastElec() + "\n"
                + getResources().getString(R.string.device_ira_log_last_water) + iraLogBean.getLastWater() + "\n"
                + getResources().getString(R.string.device_ira_log_last_start_money) + iraLogBean.getLastStartMoney() + "\n"
                + getResources().getString(R.string.device_ira_log_last_end_money) + iraLogBean.getLastEndMoney() + "\n"
                + getResources().getString(R.string.device_ira_log_last_consume_money) + iraLogBean.getLastConsumeMoney() + "\n"
                + getResources().getString(R.string.device_ira_log_last_start_time) + iraLogBean.getLastStartTime() + "\n"
                + getResources().getString(R.string.device_ira_log_last_end_time) + iraLogBean.getLastEndTime() + "\n";
        mBuilder = new MaterialDialog.Builder(DeviceIraLogActivity.this);
        mBuilder.title(iraLogBean.getPosition());
        mBuilder.titleGravity(GravityEnum.CENTER);
        mBuilder.theme(Theme.LIGHT);
        mBuilder.content(content);
        mMaterialDialog = mBuilder.build();
        mMaterialDialog.show();

    }

    private class IraLogDownLoadTask extends AsyncTask<Void, Void, String> {

        IraLogDownLoadTask() {
            super();
        }

        @Override
        protected String doInBackground(Void... arg0) {
            HashMap<String, String> params = new HashMap<String, String>();
            String nameSpace = MyConfig.nameSpace;
            String methodName = MyConfig.methodName_GetIraLog;
            String endPoint = MyConfig.endPoint;
            return WebServiceUtil.getAnyType(nameSpace, methodName,
                    endPoint, params);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            iraLogBeans = ParseTools.json2IraLogBeanList(result);
            setAdapter(iraLogBeans);
        }
    }

}
