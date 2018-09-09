package song.jtslkj.activity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import song.jtslkj.app.MyApplication;
import song.jtslkj.bean.ConsumeBean;
import song.jtslkj.config.MyConfig;
import song.jtslkj.util.AsyncLoadImageUtil;
import song.jtslkj.util.ToolBox;
import song.jtslkj.util.WebServiceUtil;
import com.jtslkj.R;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class BaijiaConsumeDetailActivity extends BaseActivity implements
		OnClickListener {
	ConsumeBean cb;
	EditText et_name, et_money, et_content;
	RelativeLayout rl_time, rl_budget, rl_person;
	TextView tv_time, tv_budget, tv_person, tv_actionbar_left,
			tv_actionbar_title, tv_actionbar_right;
	String[] budgetlist;
	Boolean isedit;
	String time, budget;
	ImageView iv_img;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_baijia_consume_detail);
		MyApplication.getInstance().addActivity(this);
		findview();
		initdata();
		initview();
		setclicker();
		refreshView();
	}

	private void findview() {
		iv_img = (ImageView) findViewById(R.id.iv_baijia_consume_detail_image);
		et_name = (EditText) findViewById(R.id.et_baijia_detail_name);
		et_money = (EditText) findViewById(R.id.et_baijia_detail_money);
		et_content = (EditText) findViewById(R.id.et_baijia_detail_content);
		tv_time = (TextView) findViewById(R.id.tv_baijia_detail_time_temp);
		tv_budget = (TextView) findViewById(R.id.tv_baijia_detail_budget_temp);
		tv_person = (TextView) findViewById(R.id.tv_baijia_detail_person_temp);
		tv_actionbar_left = (TextView) findViewById(R.id.tv_actionbar_usual_cancel);
		tv_actionbar_title = (TextView) findViewById(R.id.tv_actionbar_usual_title);
		tv_actionbar_right = (TextView) findViewById(R.id.tv_actionbar_usual_sure);
		rl_time = (RelativeLayout) findViewById(R.id.rl_baijia_detail_time);
		rl_budget = (RelativeLayout) findViewById(R.id.rl_baijia_detail_budget);
		rl_person = (RelativeLayout) findViewById(R.id.rl_baijia_detail_person);
	}

	private void initdata() {
		Intent intent = this.getIntent();
		cb = (ConsumeBean) intent.getSerializableExtra("cb");
		isedit = false;

	}

	private void initview() {
		et_name.setText(cb.getName());
		et_money.setText(cb.getMoney());
		et_content.setText(cb.getOther());
		tv_budget.setText(cb.getOtype());
		tv_time.setText(cb.getOtime());
		tv_person.setText(cb.getPerson());
		if (!cb.getImgurl().equals("")) {
			iv_img.setVisibility(View.VISIBLE);
			ImageLoader.getInstance().init(
					ImageLoaderConfiguration.createDefault(this));
			AsyncLoadImageUtil.loadImage(iv_img, cb.getImgurl(), 5);// 加载图片
		}
		tv_actionbar_title.setText(cb.getName() + "详情");
		tv_actionbar_left.setText(getString(R.string.system_return));
		tv_actionbar_right.setText(getString(R.string.baijia_detail_bianji));
		addlog("4", "消费名：" + cb.getName(),"1");
	}

	private void setclicker() {
		tv_actionbar_left.setOnClickListener(this);
		tv_actionbar_right.setOnClickListener(this);
		rl_budget.setOnClickListener(this);
		rl_person.setOnClickListener(this);
		rl_time.setOnClickListener(this);
		iv_img.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(BaijiaConsumeDetailActivity.this,
						ImageShowerActivity.class);
				i.putExtra("imguri", cb.getImgurl());
				startActivity(i);
			}
		});
	}

	/**
	 * 根据isedit的状态更新界面View
	 */
	private void refreshView() {
		// 如果isedit为真
		if (isedit) {
			tv_actionbar_left.setText(getString(R.string.system_cancel));
			tv_actionbar_right.setText(getString(R.string.system_sure));
			tv_actionbar_title.setText(cb.getName() + "编辑");
			tv_actionbar_left.setTextColor(getResources().getColor(
					R.color.text_red));
			tv_actionbar_title.setTextColor(getResources().getColor(
					R.color.text_red));
			tv_actionbar_right.setTextColor(getResources().getColor(
					R.color.text_red));
			et_name.setEnabled(true);
			et_money.setEnabled(true);
			et_content.setEnabled(true);
			rl_budget.setEnabled(true);
			rl_person.setEnabled(true);
			rl_time.setEnabled(true);
		} else {
			tv_actionbar_left.setText(getString(R.string.system_return));
			tv_actionbar_right
					.setText(getString(R.string.baijia_detail_bianji));
			tv_actionbar_title.setText(cb.getName() + "详情");
			tv_actionbar_left.setTextColor(getResources().getColor(
					R.color.text_white));
			tv_actionbar_title.setTextColor(getResources().getColor(
					R.color.text_white));
			tv_actionbar_right.setTextColor(getResources().getColor(
					R.color.text_white));

			et_name.setEnabled(false);
			et_money.setEnabled(false);
			et_content.setEnabled(false);
			rl_budget.setEnabled(false);
			rl_person.setEnabled(false);
			rl_time.setEnabled(false);
		}

	}

	private void showPersonDialog() {
		final AlertDialog dlg = new AlertDialog.Builder(this).create();
		dlg.show();
		Window window = dlg.getWindow();
		// *** 主要就是在这里实现这种效果的.
		// 设置窗口的内容页面,shrew_exit_dialog.xml文件中定义view内容
		window.setContentView(R.layout.item_baijai_add_person);
		// 为确认按钮添加事件,执行退出应用操作
		TextView tv_public = (TextView) window.findViewById(R.id.tv_content1);
		tv_public.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				tv_person.setText(getString(R.string.baijia_add_person_public));
				dlg.cancel();
			}
		});
		TextView tv_ls = (TextView) window.findViewById(R.id.tv_content2);
		tv_ls.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				tv_person.setText(getString(R.string.lisong));
				dlg.cancel();
			}
		});

		TextView tv_hss = (TextView) window.findViewById(R.id.tv_content3);
		tv_hss.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				tv_person.setText(getString(R.string.houshuangshuang));
				dlg.cancel();
			}
		});

	}

	private void showTimeDialog() {
		Dialog dialog = null;
		dialog = new DatePickerDialog(BaijiaConsumeDetailActivity.this,
				new DatePickerDialog.OnDateSetListener() {
					public void onDateSet(DatePicker dp, int year, int month,
							int dayOfMonth) {
						month = month + 1;
						if (month <= 9) {
							time = year + "-0" + month;
						} else {
							time = year + "-" + month;
						}
						if (dayOfMonth <= 9) {
							time = time + "-0" + dayOfMonth;
						} else {
							time = time + "-" + dayOfMonth;
						}
						tv_time.setText(time);
					}

				},

				Calendar.getInstance().get(Calendar.YEAR), Calendar
						.getInstance().get(Calendar.MONTH), Calendar
						.getInstance().get(Calendar.DAY_OF_MONTH));
		dialog.show();

	}

	private void showBudgetDialog() {
		showLoadingDialog("正在拼命加载当月预算");
		new TypeTask(ToolBox.formaMonthStr(cb.getOtime())).execute();
	}

	private void updateConsume() {
		if (et_name.getText().toString().trim().equals("")
				|| et_money.getText().toString().trim().equals("")
				|| et_content.getText().toString().trim().equals("")) {
			Toast.makeText(BaijiaConsumeDetailActivity.this, "不能为空哦",
					Toast.LENGTH_SHORT).show();
		} else {
			cb.setName(et_name.getText().toString().trim());
			cb.setMoney(et_money.getText().toString().trim());
			cb.setOther(et_content.getText().toString().trim());
			cb.setOtype(tv_budget.getText().toString().trim());
			cb.setPerson(tv_person.getText().toString().trim());
			cb.setOtime(tv_time.getText().toString());
			
			new UpdateConsumeTask(cb).execute();
			addlog("1", "消费名：" + cb.getName(),"1");
		}

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		switch (v.getId()) {
		case R.id.tv_actionbar_usual_cancel:
			//System.out.println("click--------" + isedit);
			if (isedit) {
				// 如果是可编辑的状态，那就是取消编辑了
				isedit = false;
				refreshView();
			} else {
				// 如果不可编辑，就直接返回呗
				MyApplication.getInstance().removeActivity(
						BaijiaConsumeDetailActivity.this);
				finish();

			}
			break;
		case R.id.tv_actionbar_usual_sure:
			if (isedit) {
				updateConsume();

			} else {
				isedit = true;
				refreshView();
			}
			break;
		case R.id.rl_baijia_detail_time:
			showTimeDialog();
			break;
		case R.id.rl_baijia_detail_budget:
			showBudgetDialog();
			break;
		case R.id.rl_baijia_detail_person:
			showPersonDialog();
			break;
		default:
			break;
		}

	}

	private class TypeTask extends AsyncTask<Void, Void, ArrayList<String>> {

		String month;

		public TypeTask(String month) {
			super();
			this.month = month;
		}

		@Override
		protected ArrayList<String> doInBackground(Void... arg0) {
			HashMap<String, String> params = new HashMap<String, String>();
			params.put("time", month);
			String nameSpace = MyConfig.nameSpace;
			String methodName = MyConfig.methodName_GetIntroduction;
			String endPoint = MyConfig.endPoint;
			return WebServiceUtil.getArrayOfAnyType(nameSpace, methodName,
					endPoint, params);
		}

		@Override
		protected void onPostExecute(ArrayList<String> result) {
			super.onPostExecute(result);
			result.remove(0);
			budgetlist = new String[result.size()];
			result.toArray(budgetlist);
			new AlertDialog.Builder(BaijiaConsumeDetailActivity.this)
					.setTitle("选择预算")
					.setIcon(android.R.drawable.ic_dialog_info)
					.setSingleChoiceItems(budgetlist, 0,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									budget = budgetlist[which];
									tv_budget.setText(budget);
									dialog.dismiss();
								}
							})
					.setNegativeButton(getString(R.string.system_cancel), null)
					.show();
			closeLoadingDialog();
		}

	}

	private class UpdateConsumeTask extends
			AsyncTask<Void, Void, ArrayList<String>> {
		ConsumeBean cb;

		public UpdateConsumeTask(ConsumeBean cb) {
			super();
			this.cb = cb;
		}

		@Override
		protected ArrayList<String> doInBackground(Void... arg0) {
			HashMap<String, String> params = new HashMap<String, String>();
			params.put("id", cb.getId());
			params.put("name", cb.getName());
			params.put("person", cb.getPerson());
			params.put("month", ToolBox.formaMonthStr(cb.getOtime()));
			params.put("Budget", cb.getOtype());
			params.put("omoney", cb.getMoney());
			params.put("detail", cb.getOther());
			String nameSpace = MyConfig.nameSpace;
			String methodName = MyConfig.methodName_UpdateConsume;
			String endPoint = MyConfig.endPoint;
			return WebServiceUtil.getArrayOfAnyType(nameSpace, methodName,
					endPoint, params);
		}

		@Override
		protected void onPostExecute(ArrayList<String> result) {
			super.onPostExecute(result);

			if (result.size() > 0) {
				if (result.get(0).trim().equals("success")) {
					Toast.makeText(BaijiaConsumeDetailActivity.this, "添加成功",
							Toast.LENGTH_SHORT).show();
					finish();
					MyApplication.getInstance().removeActivity(
							BaijiaConsumeDetailActivity.this);
				} else {
					Toast.makeText(BaijiaConsumeDetailActivity.this, "添加失败",
							Toast.LENGTH_SHORT).show();
				}

			}
		}

	}

}
