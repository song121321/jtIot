package song.jtslkj.activity;


import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Window;

import cn.bmob.v3.Bmob;
import song.jtslkj.app.MyApplication;
import song.jtslkj.config.MyConfig;

/**
 * 闪屏页面基类
 * @ClassName: BaseActivity
 * @Description: TODO
 * @author ryan
 * @date 2015-2-1 
 */
public abstract class SplashBaseActivity extends Activity {

	protected int mScreenWidth;
	protected int mScreenHeight;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		Bmob.initialize(this, MyConfig.Bmob_APPID);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		MyApplication.getInstance().addActivity(this);
		DisplayMetrics metric = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metric);
		mScreenWidth = metric.widthPixels;
		mScreenHeight = metric.heightPixels;
		
		setContentView();
		initViews();
		initListeners();
		initData();
	}

	public abstract void setContentView();


	public abstract void initViews();

	public abstract void initListeners();
	

	public abstract void initData();


	
}
