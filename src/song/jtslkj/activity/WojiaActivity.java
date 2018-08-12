package song.jtslkj.activity;

import song.jtslkj.app.MyApplication;
import song.jtslkj.config.MyConfig;
import song.jtslkj.util.AccountSharedPreferenceHelper;
import com.jtslkj.R;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class WojiaActivity extends BaseActivity implements OnClickListener {
	ImageView iv_top_left, iv_top_right, iv_photo, iv_sex;
	TextView tv_top_middle, tv_name, tv_account;
	RelativeLayout re_myinfo, re_friendquan, re_setting, re_qianbao, re_zuji;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wojia_main);
		MyApplication.getInstance().addActivity(this);
		findViewByid();
		initview();
		setclicker();
	}

	private void findViewByid() {
		iv_top_left = (ImageView) findViewById(R.id.iv_actionbar_search);
		iv_top_right = (ImageView) findViewById(R.id.iv_actionbar_add);
		iv_photo = (ImageView) findViewById(R.id.iv_wojia_main_avatar);
		iv_sex = (ImageView) findViewById(R.id.iv_wojia_main_sex);
		tv_top_middle = (TextView) findViewById(R.id.tv_actionbar_title);
		tv_name = (TextView) findViewById(R.id.tv_wojia_main_name);
		tv_account = (TextView) findViewById(R.id.tv_wojia_main_account);
		re_myinfo = (RelativeLayout) findViewById(R.id.re_wojia_main_myinfo);
		re_friendquan = (RelativeLayout) findViewById(R.id.re_wojia_main_friendsquan);
		re_qianbao = (RelativeLayout) findViewById(R.id.re_wojia_main_qianbao);
		re_setting = (RelativeLayout) findViewById(R.id.re_wojia_main_setting);
		re_zuji = (RelativeLayout) findViewById(R.id.re_wojia_main_footprint);

	}

	private void setclicker() {
		re_myinfo.setOnClickListener(this);
		re_friendquan.setOnClickListener(this);
		re_qianbao.setOnClickListener(this);
		re_setting.setOnClickListener(this);
		re_zuji.setOnClickListener(this);
	}

	private void initview() {
		iv_top_left.setVisibility(View.GONE);
		iv_top_right.setVisibility(View.GONE);
		tv_top_middle.setText(R.string.main_tabtext_wojia);
		AccountSharedPreferenceHelper asph = new AccountSharedPreferenceHelper(
				this);
		String account = asph
				.readStringFromSharedpreference(MyConfig.sharedpreference_tablecol_account);
		if (!account.equals("")) {
			String sFormat, sFinal;
			sFormat = getResources().getString(R.string.wojia_main_account);
			sFinal = String.format(sFormat, account);
			tv_account.setText(sFinal);
			if (account.equals("admin") || account.equals("Admin")) {
				tv_name.setText(getString(R.string.lisong));
				iv_photo.setImageResource(R.drawable.avatar3);
			}
			if (account.equals("hss") || account.equals("Hss")) {
				tv_name.setText(getString(R.string.houshuangshuang));
				iv_sex.setImageResource(R.drawable.item_consume_person_hss);
			}

		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.re_wojia_main_footprint:
			Intent intent_footprint = new Intent(WojiaActivity.this,
					WojiaZujiActivity.class);
			startActivity(intent_footprint);
			break;
		case R.id.re_wojia_main_myinfo:
			break;
		case R.id.re_wojia_main_friendsquan:
			break;
		case R.id.re_wojia_main_qianbao:
			break;
		case R.id.re_wojia_main_setting:

			Intent intent = new Intent(WojiaActivity.this,
					WojiaSettingActivity.class);
			startActivity(intent);
			break;
		}
	}
}
