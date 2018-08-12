package song.jtslkj.activity;

import java.util.ArrayList;
import java.util.HashMap;

import song.jtslkj.app.MyApplication;
import song.jtslkj.bean.BudgetBean;
import song.jtslkj.config.MyConfig;
import song.jtslkj.util.WebServiceUtil;
import com.jtslkj.R;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class BaijiaBudgetDetailActivity extends BaseActivity implements
		OnClickListener {
	BudgetBean bb;
	EditText et_name, et_money, et_content;
	TextView tv_actionbar_left, tv_actionbar_title, tv_actionbar_right;
	Boolean isedit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_baijia_budget_addsingle);
		MyApplication.getInstance().addActivity(this);
		findview();
		initdata();
		initview();
		setclicker();
		refreshView();
	}

	private void findview() {
		et_name = (EditText) findViewById(R.id.et_baijia_add_name);
		et_money = (EditText) findViewById(R.id.et_baijia_add_money);
		et_content = (EditText) findViewById(R.id.et_baijia_add_content);
		tv_actionbar_left = (TextView) findViewById(R.id.tv_actionbar_usual_cancel);
		tv_actionbar_title = (TextView) findViewById(R.id.tv_actionbar_usual_title);
		tv_actionbar_right = (TextView) findViewById(R.id.tv_actionbar_usual_sure);
	}

	private void initdata() {
		Intent intent = this.getIntent();
		bb = (BudgetBean) intent.getSerializableExtra("bb");
		isedit = false;

	}

	private void initview() {
		et_name.setText(bb.getName());
		et_money.setText(bb.getMoney());
		et_content.setText(bb.getDetail() + "，总计：" + bb.getMoney() + "元，已用"
				+ bb.getOmoneyuse() + "元");
		tv_actionbar_title.setText(bb.getName() + "详情");
		tv_actionbar_left.setText(getString(R.string.system_return));
		tv_actionbar_right.setText(getString(R.string.baijia_detail_bianji));
		addlog("4", "预算名：" + bb.getName(),"0");
	}

	private void setclicker() {
		tv_actionbar_left.setOnClickListener(this);
		tv_actionbar_right.setOnClickListener(this);
	}

	/**
	 * 根据isedit的状态更新界面View
	 */
	private void refreshView() {
		// 如果isedit为真
		if (isedit) {
			tv_actionbar_left.setText(getString(R.string.system_cancel));
			tv_actionbar_right.setText(getString(R.string.system_sure));
			tv_actionbar_title.setText(bb.getName() + "编辑");
			tv_actionbar_left.setTextColor(getResources().getColor(
					R.color.text_red));
			tv_actionbar_title.setTextColor(getResources().getColor(
					R.color.text_red));
			tv_actionbar_right.setTextColor(getResources().getColor(
					R.color.text_red));
			et_name.setEnabled(true);
			et_money.setEnabled(true);
			et_content.setEnabled(true);

		} else {
			tv_actionbar_left.setText(getString(R.string.system_return));
			tv_actionbar_right
					.setText(getString(R.string.baijia_detail_bianji));
			tv_actionbar_title.setText(bb.getName() + "详情");
			tv_actionbar_left.setTextColor(getResources().getColor(
					R.color.text_white));
			tv_actionbar_title.setTextColor(getResources().getColor(
					R.color.text_white));
			tv_actionbar_right.setTextColor(getResources().getColor(
					R.color.text_white));
			et_name.setEnabled(false);
			et_money.setEnabled(false);
			et_content.setEnabled(false);
		}
	}
	private void updateBudget(){
		if(et_name.getText().toString().trim().equals("")||et_money.getText().toString().trim().equals("")||et_content.getText().toString().trim().equals(""))
		{
			Toast.makeText(BaijiaBudgetDetailActivity.this, "不能为空哦", Toast.LENGTH_SHORT).show();
		}else{
			bb.setName(et_name.getText().toString().trim());
			bb.setMoney(et_money.getText().toString().trim());
			bb.setDetail(et_content.getText().toString().trim());
			new UpdateBudgetTask(bb).execute();
			addlog("1", "预算名：" + bb.getName(),"0");
		}
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		switch (v.getId()) {
		case R.id.tv_actionbar_usual_cancel:
			System.out.println("click--------" + isedit);
			if (isedit) {
				// 如果是可编辑的状态，那就是取消编辑了
				isedit = false;
				refreshView();
			} else {
				// 如果不可编辑，就直接返回呗
				MyApplication.getInstance().removeActivity(
						BaijiaBudgetDetailActivity.this);
				finish();

			}
			break;
		case R.id.tv_actionbar_usual_sure:
			if (isedit) {
				updateBudget();

			} else {
				isedit = true;
				refreshView();
			}
			break;
		case R.id.rl_baijia_detail_time:
			break;
		default:
			break;
		}

	}

	private class UpdateBudgetTask extends AsyncTask<Void, Void, ArrayList<String>> {
		BudgetBean cb ;
		
		public UpdateBudgetTask(BudgetBean cb) {
			super();
			this.cb = cb;
		}

		@Override
		protected ArrayList<String> doInBackground(Void... arg0) {
			HashMap<String, String> params = new HashMap<String, String>();
			params.put("id",  cb.getId());
			params.put("name", cb.getName());
			params.put("money", cb.getMoney());
			params.put("detail", cb.getDetail());
			String nameSpace = MyConfig.nameSpace;
			String methodName = MyConfig.methodName_UpdateBudget;
			String endPoint = MyConfig.endPoint;
			return WebServiceUtil.getArrayOfAnyType(nameSpace, methodName,
					endPoint, params);
		}

		@Override
		protected void onPostExecute(ArrayList<String> result) {
			super.onPostExecute(result);
			
			if(result.size()>0){
				if(result.get(0).trim().equals("success")){
					Toast.makeText(BaijiaBudgetDetailActivity.this, "修改成功", Toast.LENGTH_SHORT).show();	
				finish();
				MyApplication.getInstance().removeActivity(BaijiaBudgetDetailActivity.this);
				}else{
					Toast.makeText(BaijiaBudgetDetailActivity.this, "修改失败", Toast.LENGTH_SHORT).show();	
				}
				
			}
		}

	}
	
}
