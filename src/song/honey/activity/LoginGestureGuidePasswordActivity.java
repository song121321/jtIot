package song.honey.activity;

import song.honey.app.MyApplication;
import song.honey.util.LoginGesturePatternUtils;
import song.shuang.honey.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class LoginGestureGuidePasswordActivity extends Activity {
	LoginGesturePatternUtils lgp;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		lgp = new LoginGesturePatternUtils(this);
		MyApplication.getInstance().addActivity(this);
		setContentView(R.layout.logingesture_password_guide);
		findViewById(R.id.gesturepwd_guide_btn).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				lgp.clearLock();
				Intent intent = new Intent(LoginGestureGuidePasswordActivity.this,
						LoginGestureCreatePasswordActivity.class);
				// 打开新的Activity
				startActivity(intent);
				finish();
			}
		});
	}

}