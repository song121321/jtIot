package song.honey.activity;

import java.util.ArrayList;
import java.util.HashMap;

import song.honey.app.MyApplication;
import song.honey.config.MyConfig;
import song.honey.util.WebServiceUtil;
import song.shuang.honey.R;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class BaijiaBudgetAddSingleActivity extends BaseActivity implements
		OnClickListener {
	EditText et_name, et_money, et_content;
	TextView tv_actionbar_cancel, tv_actionbar_sure, tv_actionbar_title;
	String name, money, detail;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_baijia_budget_addsingle);
		findview();
		initview();
		setlistener();
	}

	private void findview() {
		et_name = (EditText) findViewById(R.id.et_baijia_add_name);
		et_money = (EditText) findViewById(R.id.et_baijia_add_money);
		et_content = (EditText) findViewById(R.id.et_baijia_add_content);
		tv_actionbar_cancel = (TextView) findViewById(R.id.tv_actionbar_usual_cancel);
		tv_actionbar_sure = (TextView) findViewById(R.id.tv_actionbar_usual_sure);
		tv_actionbar_title = (TextView) findViewById(R.id.tv_actionbar_usual_title);
	}

	private void initview() {
		name = "";
		money = "0";
		tv_actionbar_title
				.setText(getString(R.string.baijia_daohang_addnewbudgetsingle));
	}

	private void setlistener() {
		tv_actionbar_cancel.setOnClickListener(this);
		tv_actionbar_sure.setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.tv_actionbar_usual_cancel:
			MyApplication.getInstance().removeActivity(
					BaijiaBudgetAddSingleActivity.this);
			finish();
			break;
		case R.id.tv_actionbar_usual_sure:
			addBudgetSingle();
			break;
		default:
			break;
		}
	}

	private void addBudgetSingle() {

		if (et_name.getText().toString().equals("")
				|| et_money.getText().toString().equals("")) {
			// 通过AlertDialog.Builder这个类来实例化我们的一个AlertDialog的对象
			AlertDialog.Builder builder = new AlertDialog.Builder(
					BaijiaBudgetAddSingleActivity.this);
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
			showLoadingDialog("正在添加...");
			name = et_name.getText().toString();
			money = et_money.getText().toString();
			detail = et_content.getText().toString();
			new AddBudgetSingleTask(name, money, detail).execute();
			addlog("0", "单个预算名" + name,"0");
		}

	}

	private class AddBudgetSingleTask extends
			AsyncTask<Void, Void, ArrayList<String>> {
		String name, money, detail;

		public AddBudgetSingleTask(String name, String money, String detail) {
			super();
			this.name = name;
			this.money = money;
			this.detail = detail;
		}

		@Override
		protected ArrayList<String> doInBackground(Void... arg0) {

			HashMap<String, String> params = new HashMap<String, String>();
			params.put("name", name);
			params.put("money", money);
			params.put("detail", detail);
			String nameSpace = MyConfig.nameSpace;
			String methodName = MyConfig.methodName_AddBudget;
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
				Toast.makeText(BaijiaBudgetAddSingleActivity.this, "添加成功",
						Toast.LENGTH_LONG).show();
				MyApplication.getInstance().removeActivity(
						BaijiaBudgetAddSingleActivity.this);
				finish();
			} else {
				Toast.makeText(BaijiaBudgetAddSingleActivity.this,
						"哎呀，失败了，好好查查", Toast.LENGTH_LONG).show();

			}

		}
	}

}
