package song.honey.activity;

import song.honey.app.MyApplication;
import song.honey.fragment.WojiaSettingCommonBudgetFragment;
import song.honey.fragment.WojiaSettingNewsFragment;
import song.honey.fragment.WojiaSettingSafetyFragment;
import song.honey.fragment.WojiaSettingUniverseFragment;
import song.shuang.honey.R;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class WojiaSettingContentActivity extends FragmentActivity {
	private TextView mTextView;
	private ImageView iv_top_back;
	private static Fragment[] fragments;
	private static FragmentTransaction transaction;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wojia_settingcontent);
		MyApplication.getInstance().addActivity(this);
		FragmentManager fragmentManager = getSupportFragmentManager();
		String[] naviTexts = getResources().getStringArray(R.array.setting_arr);
		transaction = fragmentManager.beginTransaction();
		initFragmentArray();
		Intent i = getIntent();
		int position = i.getIntExtra("position", -1);
		transaction.replace(R.id.fl_wojia_setting_container,
				PlaceholderFragment.newInstance(position)).commit();
		initBarTxt();
		mTextView = (TextView) findViewById(R.id.tv_actionbar_usual_title);
		mTextView.setText(naviTexts[position]);
	}

	public static class PlaceholderFragment {
		public static Fragment newInstance(int position) {
			return showOrHide(transaction, position);
		}
	}

	private static Fragment showOrHide(FragmentTransaction transaction,
			int position) {
		transaction.show(fragments[position]);
		for (int i = 0; i < fragments.length && i != position; i++) {
			transaction.hide(fragments[i]);
		}
		return fragments[position];
	}

	private void initFragmentArray() {
		Fragment f0 = new WojiaSettingUniverseFragment();
		Fragment f1 = new WojiaSettingSafetyFragment();
		Fragment f2 = new WojiaSettingCommonBudgetFragment();
		Fragment f3 = new WojiaSettingNewsFragment();
		// Fragment f4 = new ISetting_ShareAccount();
		// Fragment f5 = new ISetting_AboutUs();
		// Fragment f6 = new ISetting_DeleteRestore();
		// Fragment f7 = new ISetting_Safety();
		fragments = new Fragment[4];
		fragments[0] = f0;
		fragments[1] = f1;
		fragments[2] = f2;
		fragments[3] = f3;
		// fragments[4] = f4;
		// fragments[5] = f5;
		// fragments[6] = f6;
		// fragments[7] = f7;
	}

	private void initBarTxt() {
		iv_top_back = (ImageView) findViewById(R.id.iv_actionbar_left);
		iv_top_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});

	}
}