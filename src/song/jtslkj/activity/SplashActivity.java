package song.jtslkj.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.Window;

import com.jtslkj.R;

import song.jtslkj.app.MyApplication;
import song.jtslkj.config.MyConfig;
import song.jtslkj.util.AccountSharedPreferenceHelper;
import song.jtslkj.util.InitExitUtil;

/**
 * 闪屏页面
 * 
 * @ClassName: SplashActivity
 * @Description: TODO
 * @author ryan
 * @date 2015-2-1
 */
public class SplashActivity extends SplashBaseActivity {

	private AccountSharedPreferenceHelper asph;

	@Override
	public void setContentView() {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_splash);
		MyApplication.getInstance().addActivity(this);
	
		AccountSharedPreferenceHelper asp = new AccountSharedPreferenceHelper(this);
		MyConfig.ServerAddress = asp.readStringFromSharedpreference("ServerAddress");
		
	}

	@Override
	public void initViews() {

	}

	@Override
	public void initListeners() {

	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub
		mHandler.sendEmptyMessageDelayed(GO_HOME, 1500);
	}

	public void goHome() {
		//
		asph = new AccountSharedPreferenceHelper(this);
		if (asph.readStringFromSharedpreference(
				MyConfig.sharedpreference_tablecol_firstin).trim().equals("")) {
			// 这是第一次使用本软件，要初始化一系列操作
			new InitExitUtil(this).initSetting();

			asph.writeStringToSharedpreference(
					MyConfig.sharedpreference_tablecol_firstin, "yes");
			Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
			startActivity(intent);
		} else {
			// 否则不是第一次使用，不用启动了
			if (asph.readStringFromSharedpreference(
					MyConfig.sharedpreference_tablecol_account).trim()
					.equals("")) {
				// 没有账号的情况，登陆获取账号
				Intent intent = new Intent(SplashActivity.this,
						LoginActivity.class);
				// 打开新的Activity
				startActivity(intent);

			} else {
				// 有账号，把账号赋给常量，拿去开门
				// 然后判断是否设置了关闭解锁的功能
				if (asph.readStringFromSharedpreference(
						MyConfig.sharedpreference_tablecol_closegesturelock)
						.trim().equals("")) {
					// 如果没有关闭解锁功能，就导向解锁界面
					Intent intent = new Intent(SplashActivity.this,
							LoginGestureUnlockPasswordActivity.class);
					startActivity(intent);
				} else {
					// 如果不为空，表示关闭解锁了，直接进入就行
					Intent intent = new Intent(SplashActivity.this,
							MainActivity.class);
					startActivity(intent);
				}
			}
		}
		// Intent intent = new Intent(SplashActivity.this,
		// LoginGestureUnlockPasswordActivity.class);
		//
		// startActivity(intent);
		this.finish();
	}

	private static final int GO_HOME = 100;
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case GO_HOME:
				goHome();
				break;
			}
		}
	};

}
