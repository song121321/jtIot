package song.jtslkj.activity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import song.jtslkj.app.MyApplication;
import song.jtslkj.bean.WorkItemBean;
import song.jtslkj.config.MyConfig;
import song.jtslkj.util.ParseTools;
import song.jtslkj.util.ToolBox;
import song.jtslkj.util.WebServiceUtil;
import com.jtslkj.R;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ChijiaWorkAddActivity extends BaseActivity implements
		OnClickListener {
	TextView tv_name, tv_singleprice, tv_zhongjia, tv_worktime, tv_person,
			tv_cancle, tv_ok, tv_actionbar_title;
	EditText et_number, et_changge, et_content;
	RelativeLayout rl_name, rl_worktime, rl_person;
	String name, person, time, content;
	String[] workitemlist;
	float singleprice, number, changge, zhongjia;
	ArrayList<WorkItemBean> workitemlistdata = new ArrayList<WorkItemBean>();
	int checkwork;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chijia_work_add);
		MyApplication.getInstance().addActivity(this);
		findview();
		initview();
		setclicker();
	}

	private void findview() {
		tv_name = (TextView) findViewById(R.id.tv_chijia_work_add_woritemname_temp);
		tv_singleprice = (TextView) findViewById(R.id.tv_chijia_work_add_singleprice);
		tv_zhongjia = (TextView) findViewById(R.id.tv_chijia_work_add_zhongjia);
		tv_worktime = (TextView) findViewById(R.id.tv_chijia_work_add_worktime_temp);
		tv_person = (TextView) findViewById(R.id.tv_chijia_work_add_person_temp);
		tv_cancle = (TextView) findViewById(R.id.tv_actionbar_usual_cancel);
		tv_ok = (TextView) findViewById(R.id.tv_actionbar_usual_sure);
		tv_actionbar_title = (TextView) findViewById(R.id.tv_actionbar_usual_title);

		et_number = (EditText) findViewById(R.id.et_chijia_work_add_number);
		et_changge = (EditText) findViewById(R.id.et_chijia_work_add_changge);
		et_content = (EditText) findViewById(R.id.et_chijia_work_add_content);

		rl_name = (RelativeLayout) findViewById(R.id.rl_chijia_work_add_woritemname);
		rl_worktime = (RelativeLayout) findViewById(R.id.rl_chijia_work_add_worktime);
		rl_person = (RelativeLayout) findViewById(R.id.rl_chijia_work_add_person);
	}

	private void initview() {
		checkwork = -1;
		name = "";
		singleprice = 0;
		number = 1;
		changge = 0;
		zhongjia = 0;
		time = ToolBox.getcurrentdatetime();
		person = getString(R.string.lisong);
		et_changge.setText(changge+"");
		tv_person.setText(person);
		content = time + "  " + getString(R.string.baijia_add_person_fromphone);
		tv_actionbar_title.setText(getString(R.string.chijia_daohang_add));
		tv_worktime.setText(time);
	}

	private void setclicker() {
		rl_name.setOnClickListener(this);
		rl_worktime.setOnClickListener(this);
		rl_person.setOnClickListener(this);
		tv_ok.setOnClickListener(this);
		tv_cancle.setOnClickListener(this);

		et_number.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View arg0, boolean arg1) {
				if (!arg1) {
					number = Float.parseFloat(et_number.getText().toString()
							.trim());
					refreshview();
				}
			}
		});

		et_changge.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View arg0, boolean arg1) {
				if (!arg1) {
					changge = Float.parseFloat(et_changge.getText().toString()
							.trim());
					refreshview();
				}
			}
		});
	}

	/**
	 * 根据选定的工作，重新计算单价，变化值和总价等，并且刷新
	 * 
	 */
	private void refreshview() {
		WorkItemBean wib = workitemlistdata.get(checkwork);
		singleprice = Float.parseFloat(wib.getSingleprice());
		name = wib.getName();
		tv_singleprice.setText(wib.getSingleprice());
		tv_name.setText(wib.getName());
		zhongjia = singleprice * number + changge;
		tv_zhongjia.setText(zhongjia + "");
	}

	private void showTimeDialog() {
		Dialog dialog = null;
		dialog = new DatePickerDialog(ChijiaWorkAddActivity.this,
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
						tv_worktime.setText(time);
					}

				},

				Calendar.getInstance().get(Calendar.YEAR), Calendar
						.getInstance().get(Calendar.MONTH), Calendar
						.getInstance().get(Calendar.DAY_OF_MONTH));
		dialog.show();

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
				tv_person.setText(person);
				dlg.cancel();
			}
		});
		TextView tv_ls = (TextView) window.findViewById(R.id.tv_content2);
		tv_ls.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				person = getString(R.string.lisong);
				tv_person.setText(person);
				dlg.cancel();
			}
		});

		TextView tv_hss = (TextView) window.findViewById(R.id.tv_content3);
		tv_hss.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				person = getString(R.string.houshuangshuang);
				tv_person.setText(person);
				dlg.cancel();
			}
		});

	}

	private void showWorItemDialog() {
		showLoadingDialog("正在拼命加载工作列表");
		new WorItemTask().execute();
	}

	private void addWork() {

		if (et_number.getText().toString().equals("")
				|| et_content.getText().toString().equals("")) {
			// 通过AlertDialog.Builder这个类来实例化我们的一个AlertDialog的对象
			AlertDialog.Builder builder = new AlertDialog.Builder(
					ChijiaWorkAddActivity.this);
			// 设置Title的图标
			builder.setIcon(android.R.drawable.ic_dialog_info);
			// 设置Title的内容
			builder.setTitle("提示");
			// 设置Content来显示一个信息
			builder.setMessage("内容和数量不能为空");
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
			showLoadingDialog("正在添加...");
			
			content = et_content.getText().toString();
			System.out.println(name+"------");
			new AddWorkTask(name, person, number+"", changge+"", time, content).execute();
			addlog("0", "工作：" + name,"2");
		
		}

	}
	
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		switch (v.getId()) {
		case R.id.tv_actionbar_usual_cancel:
			MyApplication.getInstance().removeActivity(this);
			finish();
			break;
		case R.id.tv_actionbar_usual_sure:
			addWork();
//			MyApplication.getInstance().removeActivity(this);
//			finish();
			break;
			
			
		case R.id.rl_chijia_work_add_woritemname:
			showWorItemDialog();
			break;
		case R.id.rl_chijia_work_add_worktime:
			showTimeDialog();
			break;
		case R.id.rl_chijia_work_add_person:
			showPersonDialog();
			break;

		default:
			break;
		}
	}

	private class WorItemTask extends AsyncTask<Void, Void, ArrayList<String>> {

		@Override
		protected ArrayList<String> doInBackground(Void... arg0) {
			HashMap<String, String> params = new HashMap<String, String>();
			String nameSpace = MyConfig.nameSpace;
			String methodName = MyConfig.methodName_GetWorkItem;
			String endPoint = MyConfig.endPoint;
			return WebServiceUtil.getArrayOfAnyType(nameSpace, methodName,
					endPoint, params);
		}

		@Override
		protected void onPostExecute(ArrayList<String> result) {
			super.onPostExecute(result);

			workitemlist = new String[result.size()];

			workitemlistdata = ParseTools.strlist2workitembeanlist(result);
			for (int i = 0; i < workitemlist.length; i++) {
				workitemlist[i] = workitemlistdata.get(i).getName();
			}
			new AlertDialog.Builder(ChijiaWorkAddActivity.this)
					.setTitle("选择工作")
					.setIcon(android.R.drawable.ic_dialog_info)
					.setSingleChoiceItems(workitemlist, 0,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									checkwork = which;
									refreshview();
									dialog.dismiss();
								}
							})
					.setNegativeButton(getString(R.string.system_cancel), null)
					.show();
			closeLoadingDialog();
		}

	}

	private class AddWorkTask extends
			AsyncTask<Void, Void, ArrayList<String>> {
		String workitemname, person, number, changgeprice, worktime, content;

		public AddWorkTask(String workitemname, String person,
				String number, String changgeprice, String worktime,
				String content) {
			super();
			this.workitemname = workitemname;
			this.person = person;
			this.number = number;
			this.changgeprice = changgeprice;
			this.worktime = worktime;
			this.content = content;
		}

		@Override
		protected ArrayList<String> doInBackground(Void... arg0) {

			HashMap<String, String> params = new HashMap<String, String>();
			params.put("workitemname", workitemname);
			System.out.println("-------"+workitemname);
			params.put("person", person);
			params.put("number", number);
			params.put("changgeprice", changgeprice);
			params.put("worktime", worktime);
			params.put("paytime", "");
			params.put("wstatus", "未结算");
			params.put("content", content);
			String nameSpace = MyConfig.nameSpace;
			String methodName = MyConfig.methodName_AddWork;
			String endPoint = MyConfig.endPoint;
			return WebServiceUtil.getArrayOfAnyType(nameSpace, methodName,
					endPoint, params);
		}

		@Override
		protected void onPostExecute(ArrayList<String> result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			System.out.println("-----"+result);
			Boolean res = false;
			if (result.size() == 1) {
				if (result.get(0).trim().equals("success")) {
					res = true;
				}
			}
			closeLoadingDialog();
			if (res) {
				Toast.makeText(ChijiaWorkAddActivity.this, "添加成功",
						Toast.LENGTH_LONG).show();
				MyApplication.getInstance().removeActivity(
						ChijiaWorkAddActivity.this);
				finish();
			} else {
				Toast.makeText(ChijiaWorkAddActivity.this, "哎呀，失败了，好好查查",
						Toast.LENGTH_LONG).show();

			}

		}
	}
}
