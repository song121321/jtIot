package song.honey.activity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import song.honey.adapter.BudgetBatchAdapter;
import song.honey.adapter.BudgetBatchAdapter.onCheckedChanged;
import song.honey.app.MyApplication;
import song.honey.bean.BudgetBatchBean;
import song.honey.config.MyConfig;
import song.honey.db.BudgetBatchSQLManager;
import song.honey.ui.CustomDialog;
import song.honey.ui.CustomDialog.Builder.OnClickOk;
import song.honey.util.AccountSharedPreferenceHelper;
import song.honey.util.InitExitUtil;
import song.honey.util.ToolBox;
import song.honey.util.WebServiceUtil;
import song.shuang.honey.R;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class BaijiaBudgetAddBatchActivity extends BaseActivity implements
		OnClickOk, onCheckedChanged {
	ListView batchlist;
	TextView tv_top_left, tv_top_middle, tv_top_right, tv_bottom_alladd,
			tv_bottom_allprice;
	CheckBox cb_choseall;
	ArrayList<BudgetBatchBean> bbblist;
	BudgetBatchAdapter bba;
	String tempmoney;
	int clickchoose;// 点击后选中的选项号码

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_baijia_budget_addbatch);
		MyApplication.getInstance().addActivity(this);
		findview();
		initView();
		setclicker();
		setBatchAdapater();
	}

	private ArrayList<BudgetBatchBean> getsrc() {
		BudgetBatchSQLManager bbsm = new BudgetBatchSQLManager(this);
		try {

			return bbsm.getBudgetBatch("");

		} catch (Exception e) {
			Toast.makeText(this, "读取数据库出错，已经恢复初始化设置", Toast.LENGTH_SHORT)
					.show();
			bbsm.addBudgetBatchbeanBatch(InitExitUtil.getInitBudgetsrc());
			return InitExitUtil.getInitBudgetsrc();
		} finally {
			bbsm = null;
		}
	}

	private void findview() {
		batchlist = (ListView) findViewById(R.id.listView_cart);
		tv_top_left = (TextView) findViewById(R.id.tv_actionbar_usual_cancel);
		tv_top_middle = (TextView) findViewById(R.id.tv_actionbar_usual_title);
		tv_top_right = (TextView) findViewById(R.id.tv_actionbar_usual_sure);
		tv_bottom_allprice = (TextView) findViewById(R.id.tv_budget_addbatch_totalmoney);
		tv_bottom_alladd = (TextView) findViewById(R.id.tv_budget_addbatch_totalnumber);
		cb_choseall = (CheckBox) findViewById(R.id.cb_budget_addbatch_choseall);
	}

	private void initView() {

		bbblist = getsrc();
		// printbblist();
		bba = new BudgetBatchAdapter(this, bbblist);
		bba.setOnCheckedChanged(this);
		batchlist.setAdapter(bba);
		tv_top_right.setText(getText(R.string.system_default));
		tv_top_middle.setText(getString(R.string.baijia_add_addbatch));

	}

	private void setBatchAdapater() {
		Collections.sort(bbblist, new BudgetBatchBeanSortByusual());
		bba.notifyDataSetChanged();
		refreshAll();
	}

	/**
	 * 刷新总计金额
	 */
	private void refreshAll() {
		String sFormat, sFinal;
		sFormat = getString(R.string.baijia_budget_total);
		sFinal = String.format(sFormat, bba.getCheckedMoney());
		tv_bottom_allprice.setText(sFinal);

		sFormat = getString(R.string.baijia_budget_addbatch);
		sFinal = String.format(sFormat, bba.getCheckedCount());
		tv_bottom_alladd.setText(sFinal);
	}

	private void setclicker() {
		cb_choseall.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				for (BudgetBatchBean bbb : bbblist) {
					bbb.setIscheck(arg1);
				}
				setBatchAdapater();
			}
		});
		tv_top_right.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				bbblist = getsrc();
				cb_choseall.setChecked(false);
				setBatchAdapater();
				refreshAll();
			}
		});
		batchlist.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					final int arg2, long arg3) {
				final CustomDialog.Builder builder = new CustomDialog.Builder(
						BaijiaBudgetAddBatchActivity.this);
				builder.setOnCheckedChanged(BaijiaBudgetAddBatchActivity.this);
				String sFormat, sFinal;
				sFormat = getString(R.string.baijia_budget_settingbudget);
				sFinal = String.format(sFormat, bbblist.get(arg2).getName());
				builder.setTitle(sFinal);
				builder.setPositiveButton("确定",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();
								clickchoose = arg2;

							}
						});

				builder.setNegativeButton("取消",
						new android.content.DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();
							}
						});

				builder.create().show();

			}
		});
		tv_top_left.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				MyApplication.getInstance().removeActivity(
						BaijiaBudgetAddBatchActivity.this);
				finish();
			}
		});
		tv_bottom_alladd.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				showLoadingDialog();
				new BatchBudgetTask(GenerateBudgetBatchString()).execute();

				addlog("0", "批量预算","0");
			}
		});
	}

	/**
	 * 构造批量添加字符串 格式如下 水果$200$2015年10月份水果预算^饭卡$600$2015年10月份饭卡预算
	 * 
	 * @return
	 */
	private String GenerateBudgetBatchString() {
		String str = "";
		for (BudgetBatchBean bbb : bbblist) {
			if (bbb.isIscheck()) {
				String sstr = bbb.getName() + "$" + bbb.getMoney() + "$"
						+ ToolBox.getcurrentmonth() + bbb.getName() + "预算";
				str = str + sstr + "^";
			}
		}
		str = str.substring(0, str.length() - 1);
		return str;
	}

	@Override
	/**
	 * 监听是否bba里面的bbblist是否已经变化，我们已经在 getView 里面设置了 bbblist.get(position).setIscheck(arg1);
	 * 所以实际上这个里面的bba中的数据bbblist已经变了不用改变任何就可以了
	 */
	public void NoticeChangge() {
		refreshAll();
	}

	@Override
	/**
	 * 监听是否已经点击了ok按钮
	 */
	public void ClickOK(String tempmoney) {
		// 当点击了ok已经返回的时候，需要判定一下此事是否开启了自动保存的功能，如果开启了，就把此时改变的值更新到数据库，否则不动
		if (!tempmoney.equals("")) {
			bbblist.get(clickchoose).setMoney(tempmoney);
			AccountSharedPreferenceHelper asph = new AccountSharedPreferenceHelper(
					this);
			if (asph.readStringFromSharedpreference(
					MyConfig.sharedpreference_tablecol_savabudget).trim()
					.equals("")) {

			} else {
				BudgetBatchSQLManager bbsm = new BudgetBatchSQLManager(this);
				if (bbsm.UpdateBudgetBatch(bbblist.get(clickchoose)) == -1) {
					Toast.makeText(BaijiaBudgetAddBatchActivity.this, "同步失败",
							Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(BaijiaBudgetAddBatchActivity.this, "同步成功",
							Toast.LENGTH_SHORT).show();
				}
				bbsm = null;
			}
			setBatchAdapater();
		}
	}

	public void printbblist() {
		for (BudgetBatchBean bbb : bbblist) {
			System.out.println("-----" + bbb.isIscheck() + " " + bbb.getName()
					+ " " + bbb.getMoney() + " " + bbb.isIsusual());
		}
	}

	// 根据是否常用排序降序
	class BudgetBatchBeanSortByusual implements Comparator<BudgetBatchBean> {
		public int compare(BudgetBatchBean cb1, BudgetBatchBean cb2) {
			if (cb1.isIsusual() && !cb2.isIsusual()) {

				return -1;
			} else {
				return 1;
			}

		}
	}

	private class BatchBudgetTask extends
			AsyncTask<Void, Void, ArrayList<String>> {
		String desc;

		public BatchBudgetTask(String desc) {
			super();
			this.desc = desc;
		}

		@Override
		protected ArrayList<String> doInBackground(Void... arg0) {
			HashMap<String, String> params = new HashMap<String, String>();
			params.put("desc", desc);
			String nameSpace = MyConfig.nameSpace;
			String methodName = MyConfig.methodName_AddBudgetBatch;
			String endPoint = MyConfig.endPoint;
			return WebServiceUtil.getArrayOfAnyType(nameSpace, methodName,
					endPoint, params);
		}

		@Override
		protected void onPostExecute(ArrayList<String> result) {
			super.onPostExecute(result);
			Boolean res = false;
			if (result.size() == 1) {
				if (result.get(0).trim().equals("success")) {
					res = true;
				}
			}
			closeLoadingDialog();
			if (res) {
				Toast.makeText(BaijiaBudgetAddBatchActivity.this, "添加成功",
						Toast.LENGTH_LONG).show();
				MyApplication.getInstance().removeActivity(
						BaijiaBudgetAddBatchActivity.this);
				finish();
			} else {
				Toast.makeText(BaijiaBudgetAddBatchActivity.this,
						"哎呀，失败了，好好查查", Toast.LENGTH_LONG).show();

			}
		}
	}
}
