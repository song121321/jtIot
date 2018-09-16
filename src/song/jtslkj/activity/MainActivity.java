package song.jtslkj.activity;

import com.kyleduo.switchbutton.SwitchButton;

import song.jtslkj.app.MyApplication;
import song.jtslkj.config.MyConfig;
import song.jtslkj.util.AccountSharedPreferenceHelper;
import song.jtslkj.util.DialogUtil;
import com.jtslkj.R;
import android.app.AlertDialog;
import android.app.TabActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost;

@SuppressWarnings("deprecation")
public class MainActivity extends TabActivity {
	private TabHost mTabHost;
	private RadioGroup mTabButtonGroup;
	public static final String TAB_HOME = "home";
	public static final String TAB_GIS = "gis";
	public static final String TAB_DEVICE = "device";
	public static final String TAB_PERSON = "person";
	public static final String ACTION_TAB = "tabaction";
	private RadioButton rButtonHome, rButtonGis, rButtonFacility, rButtonI;
	public static boolean isForeground = false;
	AccountSharedPreferenceHelper asph;
	SwitchButton sb_on;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		findViewById();
		initView();
		MyApplication.getInstance().addActivity(this);


	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();

	}

	private void findViewById() {
		mTabButtonGroup = (RadioGroup) findViewById(R.id.home_radio_button_group);
		rButtonHome = (RadioButton) findViewById(R.id.home_tab_home);
		rButtonGis = (RadioButton) findViewById(R.id.home_tab_gis);
		rButtonFacility = (RadioButton) findViewById(R.id.home_tab_facility);
		rButtonI = (RadioButton) findViewById(R.id.home_tab_i);
	}

	private void initView() {

		mTabHost = getTabHost();

		Intent iHome = new Intent(this, HomeActivity.class);
		Intent iGis = new Intent(this, GisActivity.class);
		Intent iDevice = new Intent(this, DeviceActivity.class);
		Intent i_wojia = new Intent(this, WojiaActivity.class);

		mTabHost.addTab(mTabHost.newTabSpec(TAB_HOME)
				.setIndicator(TAB_HOME).setContent(iHome));
		mTabHost.addTab(mTabHost.newTabSpec(TAB_GIS)
				.setIndicator(TAB_GIS).setContent(iGis));
		mTabHost.addTab(mTabHost.newTabSpec(TAB_DEVICE).setIndicator(TAB_DEVICE)
				.setContent(iDevice));
		mTabHost.addTab(mTabHost.newTabSpec(TAB_PERSON).setIndicator(TAB_PERSON)
				.setContent(i_wojia));
		mTabButtonGroup
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {
					public void onCheckedChanged(RadioGroup group, int checkedId) {
						switch (checkedId) {
						case R.id.home_tab_home:
							mTabHost.setCurrentTabByTag(TAB_HOME);
							changeTextColor(1);
							break;
						case R.id.home_tab_gis:
							mTabHost.setCurrentTabByTag(TAB_GIS);
							changeTextColor(2);
							break;
						case R.id.home_tab_facility:
							mTabHost.setCurrentTabByTag(TAB_DEVICE);
							changeTextColor(3);
							break;

						case R.id.home_tab_i:
							mTabHost.setCurrentTabByTag(TAB_PERSON);
							changeTextColor(4);
							break;
						default:
							break;
						}
					}
				});
	}

	private void changeTextColor(int index) {
		switch (index) {
		case 1:
			rButtonHome.setTextColor(getResources().getColor(
					R.color.actionbar_bg));
			rButtonGis.setTextColor(getResources().getColor(
					R.color.main_textcolor_normal));
			rButtonFacility.setTextColor(getResources().getColor(
					R.color.main_textcolor_normal));
			rButtonI.setTextColor(getResources().getColor(
					R.color.main_textcolor_normal));
			break;
		case 2:
			rButtonHome.setTextColor(getResources().getColor(
					R.color.main_textcolor_normal));
			rButtonGis.setTextColor(getResources().getColor(
					R.color.actionbar_bg));
			rButtonFacility.setTextColor(getResources().getColor(
					R.color.main_textcolor_normal));
			rButtonI.setTextColor(getResources().getColor(
					R.color.main_textcolor_normal));
			break;
		case 3:
			rButtonHome.setTextColor(getResources().getColor(
					R.color.main_textcolor_normal));
			rButtonGis.setTextColor(getResources().getColor(
					R.color.main_textcolor_normal));
			rButtonFacility.setTextColor(getResources().getColor(
					R.color.actionbar_bg));
			rButtonI.setTextColor(getResources().getColor(
					R.color.main_textcolor_normal));
			break;

		case 4:
			rButtonHome.setTextColor(getResources().getColor(
					R.color.main_textcolor_normal));
			rButtonGis.setTextColor(getResources().getColor(
					R.color.main_textcolor_normal));
			rButtonFacility.setTextColor(getResources().getColor(
					R.color.main_textcolor_normal));
			rButtonI.setTextColor(getResources().getColor(
					R.color.actionbar_bg));
			break;

		default:
			break;
		}
	}

	/** 含有标题、内容、两个按钮的对话框 **/
	protected void showAlertDialog(String title, String message,
			String positiveText,
			DialogInterface.OnClickListener onPositiveClickListener,
			String negativeText,
			DialogInterface.OnClickListener onNegativeClickListener) {
		new AlertDialog.Builder(this).setTitle(title).setMessage(message)
				.setPositiveButton(positiveText, onPositiveClickListener)
				.setNegativeButton(negativeText, onNegativeClickListener)
				.show();
	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN
				&& event.getRepeatCount() == 0) {
			// 具体的操作代码
			Log.e("hjq", "onBackPressed");
			DialogUtil.showNoTitleDialog(MainActivity.this,
					R.string.system_sureifexit, R.string.system_sure,
					R.string.system_cancel, new OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							MyApplication.getInstance().exit();
						}
					}, new OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub

						}
					}, true);
		}
		return super.dispatchKeyEvent(event);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		MyApplication.getInstance().removeActivity(this);
		//unregisterReceiver(TabReceiver);
	}

	BroadcastReceiver TabReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			// MyApplication.getInstance().type=2;

		}
	};
}
