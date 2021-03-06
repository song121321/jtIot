package song.jtslkj.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jtslkj.R;

import java.util.HashMap;

import song.jtslkj.app.MyApplication;
import song.jtslkj.config.MyConfig;
import song.jtslkj.util.AccountSharedPreferenceHelper;

public class LoginActivity extends BaseActivity {
	Button bt_login;
	TextView tv_prmt;
	EditText et_account, et_pass;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		MyApplication.getInstance().addActivity(this);
		findview();
		setcliker();
	}

	private void findview() {
		bt_login = (Button) findViewById(R.id.bt_zhlogin_login);
		tv_prmt = (TextView) findViewById(R.id.tv_zhlogin_prmt);
		et_account = (EditText) findViewById(R.id.et_zhlogin_account);
		et_pass = (EditText) findViewById(R.id.et_zhlogin_password);
	}

	private void setcliker() {
		bt_login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				bt_login.setEnabled(false);
				bt_login.setText(getString(R.string.login_zhlogin_logining));
				if (et_account.getText().toString().trim().equals("")
						|| et_pass.getText().toString().trim().equals("")) {
					tv_prmt.setText(getString(R.string.login_zhlogin_password_account_nonull));
					bt_login.setEnabled(true);
					bt_login.setText(getString(R.string.login_zhlogin_login));

				} else {

					new LoginTask(et_account.getText().toString().trim(),
							et_pass.getText().toString().trim()).execute();
				}
			}
		});
	}

	private class LoginTask extends AsyncTask<Void, Void, String> {
		String name;
		String password;

		public LoginTask(String account, String password) {
			super();
			this.name = account;
			this.password = password;
		}

		@Override
		protected String doInBackground(Void... arg0) {
			HashMap<String, String> params = new HashMap<String, String>();
			params.put("name", name);
			params.put("pass", password);
			String nameSpace = MyConfig.nameSpace;
			String methodName = MyConfig.methodName_Login;
			String endPoint = MyConfig.endPoint;
//			return WebServiceUtil.getAnyType(nameSpace, methodName, endPoint,
//					params);
			return "true";
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			System.out.println("-----" + result);
			if (!result.equals("") && result.equals("true")) {
				showShortToast(getResources().getString(
						R.string.login_zhlogin_loginsuccess));
				AccountSharedPreferenceHelper asph = new AccountSharedPreferenceHelper(
						LoginActivity.this);
				asph.writeStringToSharedpreference(
						MyConfig.sharedpreference_tablecol_account, et_account
								.getText().toString());
				Intent i = new Intent(LoginActivity.this, MainActivity.class);
				startActivity(i);
				finish();
			} else {
				tv_prmt.setText(getResources().getString(
						R.string.login_zhlogin_loginfail));
				et_pass.setText("");
				bt_login.setEnabled(true);
				bt_login.setText(getString(R.string.login_zhlogin_login));

			}
		}
	}
}
