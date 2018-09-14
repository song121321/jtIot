package song.jtslkj.activity;

import java.util.ArrayList;
import java.util.HashMap;

import song.jtslkj.app.MyApplication;
import song.jtslkj.config.MyConfig;
import song.jtslkj.util.AccountSharedPreferenceHelper;
import song.jtslkj.util.DialogUtil;
import song.jtslkj.util.ToolBox;
import song.jtslkj.util.WebServiceUtil;
import android.app.Activity;
import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class BaseActivity extends Activity implements OnClickListener {
	private Dialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		MyApplication.getInstance().addActivity(this);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		MyApplication.getInstance().removeActivity(this);
	}

	public void showLongToast(String text) {
		Toast.makeText(this, text, Toast.LENGTH_LONG).show();
	}

	public void showShortToast(String text) {
		Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
	}

	public void showLoadingDialog() {
		dialog = DialogUtil.createLoadingDialog(this, "正在加载中..");
		dialog.setCancelable(true);
		dialog.show();
	}

	public void showLoadingDialog(String msg) {
		dialog = DialogUtil.createLoadingDialog(this, msg);
		dialog.setCancelable(true);
		dialog.show();
	}

	public void closeLoadingDialog() {
		if (dialog != null && dialog.isShowing()) {
			dialog.dismiss();
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

	public void addlog(String addtype, String incident, String module) {
		ToolBox tb = new ToolBox(this);
		if (tb.checkNetworkAvailable()) {
			AccountSharedPreferenceHelper asph = new AccountSharedPreferenceHelper(
					this);
			String person = asph
					.readStringFromSharedpreference(MyConfig.sharedpreference_tablecol_account);
			String client = MyConfig.appname;
			String ipadd = ToolBox.getLocalIpAddress();
			new AddLogTask(person, addtype, incident, client, ipadd, module)
					.execute();
		}
	}

	private class AddLogTask extends AsyncTask<Void, Void, String> {
		String person, addtype, incident, client, ipadd, module;

		public AddLogTask(String person, String addtype, String incident,
				String client, String ipadd, String module) {
			super();
			this.person = person;
			this.addtype = addtype;
			this.incident = incident;
			this.client = client;
			this.ipadd = ipadd;
			this.module = module;
		}

		@Override
		protected String doInBackground(Void... arg0) {

			HashMap<String, String> params = new HashMap<String, String>();
			params.put("person", person);
			params.put("addtype", addtype);
			params.put("addmodule", module);
			params.put("incident", incident);
			params.put("client", client);
			params.put("ipadd", ipadd);

			String nameSpace = MyConfig.nameSpace;
			String methodName = MyConfig.methodName_AddLog;
			String endPoint = MyConfig.endPoint;
			return WebServiceUtil.getAnyType(nameSpace, methodName, endPoint,
					params);
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

		}
	}

	public void UpdateLoglateID() {
		ToolBox tb = new ToolBox(this);
		if (tb.checkNetworkAvailable()) {
		//	new GetLogLateIDTask().execute();
		}
	}

	private class GetLogLateIDTask extends AsyncTask<Void, Void, String> {
		@Override
		protected String doInBackground(Void... arg0) {
			HashMap<String, String> params = new HashMap<String, String>();
			String nameSpace = MyConfig.nameSpace;
			String methodName = MyConfig.methodName_GetLogLateId;
			String endPoint = MyConfig.endPoint;
			return WebServiceUtil.getAnyType(nameSpace, methodName, endPoint,
					params);
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			result = result.trim();
			if (result != null && !result.equals("")) {
				AccountSharedPreferenceHelper asph = new AccountSharedPreferenceHelper(
						BaseActivity.this);
				asph.writeStringToSharedpreference(
						MyConfig.sharedpreference_tablecol_loglateid, result);
			}

		}
	}
}
