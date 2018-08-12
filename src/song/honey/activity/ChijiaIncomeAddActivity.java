package song.honey.activity;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import song.honey.app.MyApplication;
import song.honey.config.MyConfig;
import song.honey.ui.CustomDialog;
import song.honey.ui.CustomDialog.Builder.OnClickOk;
import song.honey.util.ToolBox;
import song.honey.util.WebServiceUtil;
import song.shuang.honey.R;
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
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kyleduo.switchbutton.SwitchButton;

public class ChijiaIncomeAddActivity extends BaseActivity implements
		OnClickListener, OnClickOk {
	private EditText et_name, et_money, et_content;
	private RelativeLayout rl_time, rl_tax, rl_person;
	private TextView tv_time_temp, tv_person_temp, tv_tax, tv_actionbar_cancel,
			tv_actionbar_sure, tv_actionbar_title;
	private SwitchButton sb_istax;
	private boolean issettle = false;
	String name, person, money, taxmoney, time, detail;
	String settlename = "";
	String settlemoney = "";
	String settleperson = "";
	String settleidser = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chijia_income_add);
		MyApplication.getInstance().addActivity(this);
		findview();
		initview();
		setlistener();
	}

	private void findview() {
		et_name = (EditText) findViewById(R.id.et_chijia_income_add_name);
		et_money = (EditText) findViewById(R.id.et_chijia_income_add_money);
		et_content = (EditText) findViewById(R.id.et_chijia_income_add_content);
		rl_time = (RelativeLayout) findViewById(R.id.rl_chijia_income_add_time);
		rl_tax = (RelativeLayout) findViewById(R.id.rl_chijia_income_add_istax);
		rl_person = (RelativeLayout) findViewById(R.id.rl_chijia_income_add_person);
		tv_time_temp = (TextView) findViewById(R.id.tv_chijia_income_add_time_temp);
		sb_istax = (SwitchButton) findViewById(R.id.sb_chijia_income_add_istax);
		tv_tax = (TextView) findViewById(R.id.tv_chijia_income_add_istax);
		tv_person_temp = (TextView) findViewById(R.id.tv_chijia_income_add_person_temp);
		tv_actionbar_cancel = (TextView) findViewById(R.id.tv_actionbar_usual_cancel);
		tv_actionbar_sure = (TextView) findViewById(R.id.tv_actionbar_usual_sure);
		tv_actionbar_title = (TextView) findViewById(R.id.tv_actionbar_usual_title);

	}

	private void initview() {
		time = ToolBox.getcurrentdatetime();
		tv_time_temp.setText(time);
		name = "";
		money = "0";
		taxmoney = "0";
		if (!initdata()) {

			person = getString(R.string.baijia_add_person_public);
			detail = getString(R.string.baijia_add_person_fromphone);
			tv_actionbar_title
					.setText(getString(R.string.chijia_income_add_addnewincome));

		} else {
			tv_actionbar_title
					.setText(getString(R.string.chijia_income_add_settlepage));
			et_name.setText(settleperson + "结算" + settlename);
			et_money.setText(settlemoney);
			tv_person_temp.setText(settleperson);

		}
	}

	private boolean initdata() {
		Intent intent = this.getIntent();
		settlemoney = intent.getStringExtra("money");
		settlename = intent.getStringExtra("name");
		settleperson = intent.getStringExtra("person");
		settleidser = intent.getStringExtra("id");
		if (settleidser == null || settlemoney == null || settlename == null
				|| settleperson == null) {
			return false;
		}
		issettle = true;
		return true;

	}

	private void setlistener() {
		tv_actionbar_cancel.setOnClickListener(this);
		tv_actionbar_sure.setOnClickListener(this);
		rl_time.setOnClickListener(this);
		rl_person.setOnClickListener(this);
		rl_tax.setOnClickListener(this);
		sb_istax.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if (arg1) {
					if (ToolBox.isNumeric(et_money.getText().toString())) {
						money = et_money.getText().toString();
						DecimalFormat decimalFormat = new DecimalFormat(".00");
						taxmoney = decimalFormat.format(Float.parseFloat(money) * 0.1);
						tv_tax.setText(taxmoney);
					} else {
						Toast.makeText(ChijiaIncomeAddActivity.this, "金额不是数字",
								Toast.LENGTH_SHORT).show();
					}
				} else {
					tv_tax.setText(R.string.chijia_income_add_istax);
				}
			}
		});
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.tv_actionbar_usual_cancel:
			MyApplication.getInstance().removeActivity(this);
			finish();
			break;
		case R.id.tv_actionbar_usual_sure:
			if (issettle) {
				addIncome(true);
			} else {
				addIncome(false);
			}
			break;
		case R.id.rl_chijia_income_add_time:
			showTimeDialog();
			break;
		case R.id.rl_chijia_income_add_istax:
			showTax();
			break;
		case R.id.rl_chijia_income_add_person:
			showPersonDialog();
			break;
		default:
			break;
		}

	}

	private void showTax() {
		if (sb_istax.isChecked()) {
			final CustomDialog.Builder builder = new CustomDialog.Builder(this);
			builder.setTitle(R.string.chijia_income_add_taxmoney);
			builder.setOnCheckedChanged(this);
			builder.setPositiveButton(R.string.system_sure,
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
						}
					});

			builder.setNegativeButton(R.string.system_cancel,
					new android.content.DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
						}
					});

			builder.create().show();
		}
	}

	/**
	 * 如果settle是true说明是结算工资，应当调用结算工资，如果是false，直接是加入收入就行
	 * 
	 * @param settle
	 */
	private void addIncome(boolean settle) {

		if (et_name.getText().toString().equals("")
				|| et_money.getText().toString().equals("")) {
			// 通过AlertDialog.Builder这个类来实例化我们的一个AlertDialog的对象
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			// 设置Title的图标
			builder.setIcon(android.R.drawable.ic_dialog_info);
			// 设置Title的内容
			builder.setTitle("提示");
			// 设置Content来显示一个信息
			builder.setMessage("内容和金额不能为空");
			// 设置一个PositiveButton
			builder.setPositiveButton("确定",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
						}
					});
			// 显示出该对话框
			builder.show();
		} else {
			if (ToolBox.isNumeric(et_money.getText().toString())) {
				money = et_money.getText().toString();
				name = et_name.getText().toString();
				detail = et_content.getText().toString();
				if (settle) {
					new SettleWorkTask(settleidser, settlename, settleperson, money,
							taxmoney, detail).execute();
					addlog("0", "结算工作，名称：" + settlename,"3");
				} else {
					showLoadingDialog("正在添加...");
					new AddIncomeTask(name, person, "0", money, time, detail)
							.execute();
					addlog("0", "普通收入：" + name,"3");
					if (sb_istax.isChecked()) {
						if (!taxmoney.equals("0")) {
							new AddIncomeTask(name, person, "1", taxmoney,
									time, detail).execute();
							addlog("0", "小金库收入：" + name,"3");
						}
					}
				}
			} else {
				Toast.makeText(this, "金额不是数字", Toast.LENGTH_SHORT).show();
			}

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
				person = getString(R.string.baijia_add_person_public);
				tv_person_temp.setText(person);
				dlg.cancel();
			}
		});
		TextView tv_ls = (TextView) window.findViewById(R.id.tv_content2);
		tv_ls.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				person = getString(R.string.lisong);
				tv_person_temp.setText(person);
				dlg.cancel();
			}
		});

		TextView tv_hss = (TextView) window.findViewById(R.id.tv_content3);
		tv_hss.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				person = getString(R.string.houshuangshuang);
				tv_person_temp.setText(person);
				dlg.cancel();
			}
		});

	}

	private void showTimeDialog() {
		Dialog dialog = null;
		dialog = new DatePickerDialog(this,
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
						tv_time_temp.setText(time);
					}

				},

				Calendar.getInstance().get(Calendar.YEAR), Calendar
						.getInstance().get(Calendar.MONTH), Calendar
						.getInstance().get(Calendar.DAY_OF_MONTH));
		dialog.show();

	}

	private class SettleWorkTask extends AsyncTask<Void, Void, String> {
		String idser, title, person, money, tax, detail;

		public SettleWorkTask(String idser, String title, String person,
				String money, String tax, String detail) {
			super();
			this.idser = idser;
			this.title = title;
			this.person = person;
			this.money = money;
			this.tax = tax;
			this.detail = detail;
		}

		@Override
		protected String doInBackground(Void... arg0) {
			HashMap<String, String> params = new HashMap<String, String>();
			params.put("idstr", idser);
			params.put("title", title);
			params.put("person", person);
			params.put("money", money);
			params.put("tax", tax);
			params.put("detail", detail);
			String nameSpace = MyConfig.nameSpace;
			String methodName = MyConfig.methodName_Settlework;
			String endPoint = MyConfig.endPoint;
			return WebServiceUtil.getAnyType(nameSpace, methodName, endPoint,
					params);
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			if (!result.equals("") && result.equals("true")) {
				showShortToast("结算成功");
				ChijiaIncomeAddActivity.this.finish();
			} else {
				showShortToast("结算失败");
			}

		}
	}

	private class AddIncomeTask extends
			AsyncTask<Void, Void, ArrayList<String>> {
		String name, person, label, money, time, detail;

		public AddIncomeTask(String name, String person, String label,
				String money, String time, String detail) {
			super();
			this.name = name;
			this.person = person;
			this.label = label;
			this.money = money;
			this.time = time;
			this.detail = detail;
		}

		@Override
		protected ArrayList<String> doInBackground(Void... arg0) {

			HashMap<String, String> params = new HashMap<String, String>();
			params.put("name", name);
			params.put("person", person);
			params.put("money", money);
			params.put("time", time);
			params.put("detail", detail);
			params.put("label", label);
			String nameSpace = MyConfig.nameSpace;
			String methodName = MyConfig.methodName_AddIncome;
			String endPoint = MyConfig.endPoint;
			return WebServiceUtil.getArrayOfAnyType(nameSpace, methodName,
					endPoint, params);
		}

		@Override
		protected void onPostExecute(ArrayList<String> result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			Boolean res = false;
			if (result.size() == 1) {
				if (result.get(0).trim().equals("success")) {
					res = true;
				}
			}
			closeLoadingDialog();
			if (res) {
				Toast.makeText(ChijiaIncomeAddActivity.this, "添加成功",
						Toast.LENGTH_LONG).show();
				MyApplication.getInstance().removeActivity(
						ChijiaIncomeAddActivity.this);
				finish();
			} else {
				Toast.makeText(ChijiaIncomeAddActivity.this, "哎呀，失败了，好好查查",
						Toast.LENGTH_LONG).show();

			}

		}
	}

	/**
	 * 当用户点击了弹出选项框里面的"确定"按钮之后发生的事情
	 */
	@Override
	public void ClickOK(String tempmoney) {
		if (ToolBox.isNumeric(tempmoney)) {
			taxmoney = tempmoney;
		} else {
			taxmoney = "0.0";
		}
		tv_tax.setText(tempmoney);
	}
}
