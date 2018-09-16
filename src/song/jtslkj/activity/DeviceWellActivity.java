package song.jtslkj.activity;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;
import android.widget.Toast;

import com.jtslkj.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import song.jtslkj.adapter.SectionAdapter;
import song.jtslkj.adapter.WellAdapter;
import song.jtslkj.app.MyApplication;
import song.jtslkj.bean.WellBean;
import song.jtslkj.config.MyConfig;
import song.jtslkj.util.ListUtils;
import song.jtslkj.util.ListUtilsHook;
import song.jtslkj.util.ParseTools;
import song.jtslkj.util.StringUtil;
import song.jtslkj.util.ToolBox;
import song.jtslkj.util.WebServiceUtil;
import song.jtslkj.xlistview.XListView;
import song.jtslkj.xlistview.XListView.IXListViewListener;

public class DeviceWellActivity extends BaseActivity implements
        OnClickListener, OnItemClickListener, IXListViewListener {
    private XListView wellList;
    private ListView section_list;
    private LinearLayout llSearch, llBack, ll_bar;
    private TextView tvTitle, tvVillage, tvState, tvSort;
    private ImageView ivVillage, ivState, ivSort;
    private List<WellBean> wellBeans = new ArrayList<WellBean>();
    private ArrayList<String> sortOptions = new ArrayList<String>();
    private ArrayList<String> stateOptions = new ArrayList<String>();
    private ArrayList<String> villageOptions = new ArrayList<String>();
    private SectionAdapter secAdapter;
    private String currentVillage, currentSort;
    private int currentState;
    private PopupWindow mPopWin;
    private int sectionindex, updatenumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_well_main);
        MyApplication.getInstance().addActivity(this);
        findViewById();
        initView();
        initPopupWindow();
    }

    private void findViewById() {
        wellList = (XListView) findViewById(R.id.xl_device_well_list);
        tvTitle = (TextView) findViewById(R.id.tv_actionbar_title);
        tvVillage = (TextView) findViewById(R.id.tv_device_well_section_village);
        tvState = (TextView) findViewById(R.id.tv_device_well_section_state);
        tvSort = (TextView) findViewById(R.id.tv_device_well_section_sort);
        ivVillage = (ImageView) findViewById(R.id.iv_device_well_mark_village);
        ivState = (ImageView) findViewById(R.id.iv_device_well_mark_state);
        ivSort = (ImageView) findViewById(R.id.iv_device_well_mark_sort);
        llBack = (LinearLayout) findViewById(R.id.ll_actionbar_left);
        llSearch = (LinearLayout) findViewById(R.id.ll_actionbar_right);
    }

    private void initView() {
        wellList.setPullLoadEnable(true);
        wellList.setPullRefreshEnable(true);
        wellList.setXListViewListener(this);
        wellList.setOnItemClickListener(this);
        currentVillage = "";
        currentState = -2;
        currentSort = "";
        sectionindex = 1;
        initActionBar();
        refresh();
    }


    private void setAdapter(List<WellBean> wellBeanList) {

        WellAdapter wellAdapter = new WellAdapter(DeviceWellActivity.this, wellBeanList);
        wellList.setAdapter(wellAdapter);
        closeLoadingDialog();
        Toast.makeText(
                DeviceWellActivity.this, wellBeanList.size()+" devices load successfully!", Toast.LENGTH_LONG)
                .show();

    }

    private void initActionBar() {
        tvTitle.setText(getResources().getString(R.string.device_main_well));
        llBack.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                MyApplication.getInstance().removeActivity(
                        DeviceWellActivity.this);
                finish();

            }
        });


        llSearch.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(DeviceWellActivity.this,
                        BaijiaConsumeSearchActivity.class);
                startActivity(intent);
            }
        });
    }

    private void refresh() {
        showLoadingDialog();
        new WellDownLoadTask().execute();
    }

    private void refreshLocal() {
        showLoadingDialog();
        List<WellBean> wellBeansCopy = new ArrayList<>(wellBeans);
        if (StringUtil.isEmpty(currentVillage)) {
            wellBeansCopy = ListUtils.filter(wellBeansCopy, new ListUtilsHook<WellBean>() {
                @Override
                public boolean keep(WellBean wb) {
                    return wb.getLocation().equals(currentVillage.trim());
                }
            });
        }

        if (currentState != -2) {
            wellBeansCopy = ListUtils.filter(wellBeansCopy, new ListUtilsHook<WellBean>() {
                @Override
                public boolean keep(WellBean wb) {
                    return wb.getState() == currentState;
                }
            });
        }

        if (StringUtil.isEmpty(currentSort)) {

            Collections.sort(wellBeansCopy, new Comparator<WellBean>() {

                @Override
                public int compare(WellBean o1, WellBean o2) {
                    return o1.getState() - o2.getState();
                }

            });

        }

      setAdapter(wellBeansCopy);

    }


    private void initPopupWindow() {
        stateOptions = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.status_options)));
        sortOptions = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.sort_options)));
        new VillageTask().execute();
        tvVillage.setOnClickListener(this);
        tvState.setOnClickListener(this);
        tvSort.setOnClickListener(this);
    }


    private class WellDownLoadTask extends AsyncTask<Void, Void, String> {

         WellDownLoadTask() {
            super();
        }

        @Override
        protected String doInBackground(Void... arg0) {
            HashMap<String, String> params = new HashMap<String, String>();
            String nameSpace = MyConfig.nameSpace;
            String methodName = MyConfig.methodName_GetWells;
            String endPoint = MyConfig.endPoint;
			return WebServiceUtil.getAnyType(nameSpace, methodName,
					endPoint, params);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            wellBeans = ParseTools.json2WellBeanList(result);
            setAdapter(wellBeans);
        }
    }

    private class VillageTask extends AsyncTask<Void, Void, ArrayList<String>> {
        @Override
        protected ArrayList<String> doInBackground(Void... arg0) {
            HashMap<String, String> params = new HashMap<String, String>();
            String nameSpace = MyConfig.nameSpace;
            String methodName = MyConfig.methodName_GetVillages;
            String endPoint = MyConfig.endPoint;
			return WebServiceUtil.getArrayOfAnyType(nameSpace, methodName,
					endPoint, params);
        }

        @Override
        protected void onPostExecute(ArrayList<String> result) {
            super.onPostExecute(result);
            villageOptions = result;
        }

    }

    protected void selectSecCheck(int index) {
        tvVillage.setTextColor(getResources().getColor(R.color.main_textcolor_normal));
        tvState.setTextColor(getResources().getColor(R.color.main_textcolor_normal));
        tvSort.setTextColor(getResources().getColor(R.color.main_textcolor_normal));
        ivVillage.setImageResource(R.drawable.section_bg_normal);
        ivState.setImageResource(R.drawable.section_bg_normal);
        ivSort.setImageResource(R.drawable.section_bg_normal);
        switch (index) {
            case 1:
                tvVillage.setTextColor(getResources().getColor(R.color.main_textcolor_select));
                ivVillage.setImageResource(R.drawable.section_bg_selected);
                break;
            case 2:
                tvState.setTextColor(getResources().getColor(R.color.main_textcolor_select));
                ivState.setImageResource(R.drawable.section_bg_selected);
                break;
            case 3:
                tvSort.setTextColor(getResources().getColor(R.color.main_textcolor_select));
                ivSort.setImageResource(R.drawable.section_bg_selected);
                break;
        }
    }

    private void showSectionPop(int width, int height, final int secindex) {
        selectSecCheck(sectionindex);
        ll_bar = (LinearLayout) LayoutInflater.from(DeviceWellActivity.this)
                .inflate(R.layout.popup_category, null);
        section_list = (ListView) ll_bar
                .findViewById(R.id.lv_popwin_section_list);
        section_list.setOnItemClickListener(this);
        switch (secindex) {
            case 1:
                secAdapter = new SectionAdapter(DeviceWellActivity.this,
                        villageOptions, tvVillage.getText().toString());
                break;
            case 2:
                secAdapter = new SectionAdapter(DeviceWellActivity.this,
                        stateOptions, tvState.getText().toString());
                break;
            case 3:
                secAdapter = new SectionAdapter(DeviceWellActivity.this,
                        sortOptions, tvSort.getText().toString());

                break;

        }
        section_list.setAdapter(secAdapter);
        mPopWin = new PopupWindow(ll_bar, width, LayoutParams.WRAP_CONTENT,
                true);
        mPopWin.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                // TODO Auto-generated method stub
                // selectSecCheck(4);
            }
        });
        mPopWin.setBackgroundDrawable(new BitmapDrawable());
        mPopWin.showAsDropDown(tvVillage, 0, 0);
        mPopWin.update();
    }

    @Override
    public void onLoadMore() {
        Toast.makeText(DeviceWellActivity.this, "loadmore",
                Toast.LENGTH_SHORT).show();
        wellList.stopLoadMore();
    }

    @Override
    public void onRefresh() {
        refresh();
        wellList.stopRefresh();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_device_well_section_village:
                sectionindex = 1;
                showSectionPop(wellList.getWidth(), wellList.getHeight(), 1);
                break;

            case R.id.tv_device_well_section_state:
                //	new TypeTask(currentVillage).execute();
                sectionindex = 2;
                showSectionPop(wellList.getWidth(), wellList.getHeight(), 2);
                break;
            case R.id.tv_device_well_section_sort:
                sectionindex = 3;
                showSectionPop(wellList.getWidth(), wellList.getHeight(), 3);
                break;
            default:
                break;
        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View arg1, int position,
                            long arg3) {
        if (R.id.xl_device_well_list == parent.getId()) {
            Intent intent = new Intent();
            intent.setClass(DeviceWellActivity.this,
                    DeviceWellDetailActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("wellBean", wellBeans.get(position - 1));
            intent.putExtras(bundle);
            this.startActivity(intent);
            return;
        }

        switch (sectionindex) {
            case 1:
                secAdapter = new SectionAdapter(DeviceWellActivity.this,
                        villageOptions, villageOptions.get(position));

                tvVillage.setText(villageOptions.get(position));
                currentVillage = ToolBox.getVillageFromSelect(villageOptions.get(position));
                refreshLocal();
                break;
            case 2:

                secAdapter = new SectionAdapter(DeviceWellActivity.this,
                        stateOptions, stateOptions.get(position));
                tvState.setText(stateOptions.get(position));
                currentState = ToolBox.getStateFromSelect(stateOptions.get(position));
                mPopWin.dismiss();
                refreshLocal();
                break;
            case 3:

                secAdapter = new SectionAdapter(DeviceWellActivity.this,
                        sortOptions, sortOptions.get(position));
                tvSort.setText(sortOptions.get(position));
                currentSort = ToolBox.getSortFromSelect(sortOptions.get(position));
                refreshLocal();
                break;
        }
        section_list.setAdapter(secAdapter);
        mPopWin.dismiss();

    }
}
