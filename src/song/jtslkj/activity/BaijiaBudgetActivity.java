package song.jtslkj.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import song.jtslkj.adapter.BudgetAdapter;
import song.jtslkj.app.MyApplication;
import song.jtslkj.bean.BudgetBean;
import song.jtslkj.config.MyConfig;
import song.jtslkj.ui.DragLayout;
import song.jtslkj.ui.DragLayout.DragListener;
import song.jtslkj.util.ParseTools;
import song.jtslkj.util.ToolBox;
import song.jtslkj.util.WebServiceUtil;
import song.jtslkj.xlistview.XListView;
import song.jtslkj.xlistview.XListView.IXListViewListener;
import com.jtslkj.R;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class BaijiaBudgetActivity extends BaseActivity implements
		OnClickListener, OnItemClickListener, IXListViewListener {
	/** 左边侧滑菜单 */
	private DragLayout mDragLayout;
	private ListView menuListView;// 菜单列表
	private ArrayList<BudgetBean> budgetlistdata = new ArrayList<BudgetBean>();
	private XListView budgetlist;
	private ImageView iv_batchadd, iv_add;
	private Spinner sp_time;
	private String timestr;
	private List<Map<String, Object>> data;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_baijia_budget_main);
		MyApplication.getInstance().addActivity(BaijiaBudgetActivity.this);
		findview();
		initView();
		initdata();
		setclicker();
	}

	private void findview() {
		mDragLayout = (DragLayout) findViewById(R.id.dl_chijia);
		menuListView = (ListView) findViewById(R.id.menu_listview);
		budgetlist = (XListView) findViewById(R.id.budgetlist);
		iv_batchadd = (ImageView) findViewById(R.id.iv_actionbar_addbatch);
		iv_add = (ImageView) findViewById(R.id.iv_actionbar_add);
		sp_time = (Spinner) findViewById(R.id.sp_actionbar_budgetmonth);
	}

	private void initView() {
		budgetlist.setPullLoadEnable(true);
		budgetlist.setPullRefreshEnable(true);
		budgetlist.setXListViewListener(this);
		budgetlist.setOnItemClickListener(this);
		initActionBar();
	}

	private void setclicker() {

		mDragLayout.setDragListener(new DragListener() {// 动作监听
					@Override
					public void onOpen() {
					}

					@Override
					public void onClose() {
					}

					@Override
					public void onDrag(float percent) {

					}
				});

		menuListView.setAdapter(new SimpleAdapter(this, data,
				R.layout.menulist_item_text, new String[] { "item" },
				new int[] { R.id.menu_text }));
		menuListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				if (arg2 == 1) {
					Intent intent = new Intent(BaijiaBudgetActivity.this,
							BaijiaConsumeActivity.class);
					startActivity(intent);
					BaijiaBudgetActivity.this.finish();
				} else {
					mDragLayout.close();
				}
			}
		});
	}

	public void initActionBar() {
		iv_batchadd.setOnClickListener(this);
		iv_add.setOnClickListener(this);
		ArrayAdapter<String> spinneradapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item,
				ToolBox.generateChinesemonthlist("2014-09-09"));
		spinneradapter
				.setDropDownViewResource(android.R.layout.select_dialog_item);
		sp_time.setAdapter(spinneradapter);
		sp_time.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				timestr = ToolBox.generatemonthlist("2014-09-09").get(arg2);
				refresh();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});
	}

	private void initdata() {
		timestr = "";
		data = new ArrayList<Map<String, Object>>();
		Map<String, Object> item = new HashMap<String, Object>();
		item.put("item", "预算");
		data.add(item);
		Map<String, Object> item1 = new HashMap<String, Object>();
		item1.put("item", "消费");
		data.add(item1);
	}

	private void refresh() {
		showLoadingDialog();
		new GetBudgetTask(timestr).execute();
		UpdateLoglateID();
	}

	private void setBudgetAdapter() {
		BudgetAdapter badapter = new BudgetAdapter(this, budgetlistdata);
		budgetlist.setAdapter(badapter);
		closeLoadingDialog();
		Toast.makeText(
				BaijiaBudgetActivity.this,
				"共计预算" + badapter.getCount() + "项，总金额为"
						+ badapter.gettotalmoney() + "元，已经消费共计"
						+ badapter.getUsedmoney() + "元，剩余"
						+ badapter.getleftmoney() + "元！", Toast.LENGTH_LONG)
				.show();
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.iv_actionbar_addbatch:
			Intent intent0 = new Intent(this,
					BaijiaBudgetAddBatchActivity.class);
			startActivity(intent0);
			break;
		case R.id.iv_actionbar_add:
			Intent intent = new Intent(this,
					BaijiaBudgetAddSingleActivity.class);
			startActivity(intent);
			break;

		default:
			break;
		}

	}

	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub
		budgetlist.stopLoadMore();
	}

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		refresh();
		budgetlist.stopRefresh();
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position,
			long arg3) {

		Intent intent = new Intent();
		intent.setClass(BaijiaBudgetActivity.this,
				BaijiaBudgetDetailActivity.class);
		Bundle bundle = new Bundle();
		bundle.putSerializable("bb", budgetlistdata.get(position - 1));
		intent.putExtras(bundle);
		this.startActivity(intent);
	}

	private class GetBudgetTask extends
			AsyncTask<Void, Void, ArrayList<String>> {
		String timestr;

		public GetBudgetTask(String timestr) {
			super();
			this.timestr = timestr;
		}

		@Override
		protected ArrayList<String> doInBackground(Void... arg0) {
			HashMap<String, String> params = new HashMap<String, String>();
			params.put("timestr", timestr);
			String nameSpace = MyConfig.nameSpace;
			String methodName = MyConfig.methodName_GetBudgetInfo;
			String endPoint = MyConfig.endPoint;
			return WebServiceUtil.getArrayOfAnyType(nameSpace, methodName,
					endPoint, params);
		}

		@Override
		protected void onPostExecute(ArrayList<String> result) {
			super.onPostExecute(result);
			budgetlistdata = ParseTools.strlist2budgetlist(result);
			setBudgetAdapter();
		}
	}

}
