package song.jtslkj.activity;

import java.util.ArrayList;
import java.util.HashMap;

import song.jtslkj.adapter.WorkSettleAdapter;
import song.jtslkj.adapter.WorkSettleAdapter.onCheckedChanged;
import song.jtslkj.app.MyApplication;
import song.jtslkj.bean.WorkBean;
import song.jtslkj.config.MyConfig;
import song.jtslkj.util.ParseTools;
import song.jtslkj.util.WebServiceUtil;
import com.jtslkj.R;
import android.content.Intent;
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

public class ChijiaWorkSettleActivity extends BaseActivity implements
		onCheckedChanged, OnClickListener, OnItemClickListener {
	private ListView lv_list;
	private TextView tv_top_left, tv_top_middle, tv_top_right,
			tv_bottom_alladd, tv_bottom_allprice;
	private CheckBox cb_choseall;
	WorkSettleAdapter wsa;
	ArrayList<WorkBean> worklistdata;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_baijia_budget_addbatch);
		MyApplication.getInstance().addActivity(this);
		findview();
		initView();
		setclicker();
		showLoadingDialog();
		new WorkListTask().execute();
	}

	private void findview() {
		lv_list = (ListView) findViewById(R.id.listView_cart);
		tv_top_left = (TextView) findViewById(R.id.tv_actionbar_usual_cancel);
		tv_top_middle = (TextView) findViewById(R.id.tv_actionbar_usual_title);
		tv_top_right = (TextView) findViewById(R.id.tv_actionbar_usual_sure);
		tv_bottom_allprice = (TextView) findViewById(R.id.tv_budget_addbatch_totalmoney);
		tv_bottom_alladd = (TextView) findViewById(R.id.tv_budget_addbatch_totalnumber);
		cb_choseall = (CheckBox) findViewById(R.id.cb_budget_addbatch_choseall);
	}

	private void setclicker() {
		tv_top_right.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				new WorkListTask().execute();
				tv_top_right.setEnabled(false);
			}
		});
		tv_bottom_alladd.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				if (checkworkbean()) {
					Intent i = new Intent(ChijiaWorkSettleActivity.this,
							ChijiaIncomeAddActivity.class);
					i.putExtra("name", wsa.getCheckbeanlist().get(0).getName());
					i.putExtra("person", wsa.getCheckbeanlist().get(0)
							.getPerson());
					i.putExtra("money", wsa.getCheckmoney()+"");
					i.putExtra("id", wsa.getCheckidser()+"");
					startActivity(i);
					finish();
				} else {
					showShortToast("不是同一个人且同一类别");
				}
			}
		});
		tv_top_left.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		cb_choseall.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if (arg1) {
					wsa.setchecklisttrue();
				} else {
					wsa.setchecklistfalse();
				}
				wsa.notifyDataSetChanged();
				refreshButtom(false);
			}
		});
	}

	private void initView() {
		tv_top_middle.setText(getString(R.string.chijia_income_add_settle));
		tv_top_right.setText(getString(R.string.system_refresh));
		refreshButtom(true);
	}

	/**
	 * 检查所选的工作是不是同一个人的同一个工作
	 * 
	 * @return
	 */
	private boolean checkworkbean() {
		if (wsa.getCheckbeanlist().size() > 0) {
			String name = wsa.getCheckbeanlist().get(0).getName();
			String person = wsa.getCheckbeanlist().get(0).getPerson();
			for (int i = 1; i < wsa.getCheckbeanlist().size(); i++) {
				if (!wsa.getCheckbeanlist().get(i).getName().equals(name)
						|| !wsa.getCheckbeanlist().get(i).getPerson()
								.equals(person)) {
					return false;
				}
			}
			return true;

		} else {
			return false;
		}

	}

	private void refresh() {
		wsa = new WorkSettleAdapter(this, worklistdata);
		wsa.setOnCheckedChanged(this);
		lv_list.setAdapter(wsa);
		tv_top_right.setEnabled(true);
		showShortToast("共计：" + wsa.getTotalMoney() + "元");
	}

	@Override
	public void NoticeChangge(int position, boolean check) {
		wsa.setchecklist(position, check);
		refreshButtom(false);
	}

	public void refreshButtom(Boolean first) {
		String sFormat, sFinal;
		sFormat = getString(R.string.baijia_budget_total);
		if (first) {
			sFinal = String.format(sFormat, "0.00");
			tv_bottom_allprice.setText(sFinal);

			sFormat = getString(R.string.chijia_income_add_settle1);
			sFinal = String.format(sFormat, "0");
			tv_bottom_alladd.setText(sFinal);
		} else {

			sFinal = String.format(sFormat, wsa.getCheckmoney());
			tv_bottom_allprice.setText(sFinal);

			sFormat = getString(R.string.chijia_income_add_settle1);
			sFinal = String.format(sFormat, wsa.getChecknum());
			if (wsa.getChecknum() == 0) {
				tv_bottom_alladd.setEnabled(false);
			} else {
				tv_bottom_alladd.setEnabled(true);
			}
			tv_bottom_alladd.setText(sFinal);
		}
	}

	private class WorkListTask extends AsyncTask<Void, Void, ArrayList<String>> {
		@Override
		protected ArrayList<String> doInBackground(Void... arg0) {
			HashMap<String, String> params = new HashMap<String, String>();
			params.put("name", "");
			params.put("person", "");
			params.put("status", "未结算");
			params.put("order", "");
			String nameSpace = MyConfig.nameSpace;
			String methodName = MyConfig.methodName_GetWorkList;
			String endPoint = MyConfig.endPoint;
			return WebServiceUtil.getArrayOfAnyType(nameSpace, methodName,
					endPoint, params);
		}

		@Override
		protected void onPostExecute(ArrayList<String> result) {
			// 如果db为真，表示只是更新数据库，否则，更新UI
			super.onPostExecute(result);

			worklistdata = ParseTools.strlist2workbeanlist(result);
			refresh();
			closeLoadingDialog();

		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

	}

}
