package song.jtslkj.activity;

import song.jtslkj.app.MyApplication;
import song.jtslkj.util.InitExitUtil;
import song.jtslkj.util.LoginGesturePatternUtils;
import com.jtslkj.R;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class WojiaSettingActivity extends BaseActivity implements
		OnClickListener {
	RelativeLayout rl_tongyong, rl_anquan, rl_commonbudget, rl_news, rl_clear,
			rl_about;
	Button bt_exit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wojia_setting);
		MyApplication.getInstance().addActivity(this);
		findViewByid();
		// initview();
		setoncliker();

	}

	private void findViewByid() {
		rl_tongyong = (RelativeLayout) findViewById(R.id.rl_wojia_setting_tongyong);
		rl_anquan = (RelativeLayout) findViewById(R.id.rl_wojia_setting_anquan);

		rl_news = (RelativeLayout) findViewById(R.id.rl_wojia_setting_news);
		rl_clear = (RelativeLayout) findViewById(R.id.rl_wojia_setting_clear);
		rl_about = (RelativeLayout) findViewById(R.id.rl_wojia_setting_about);
		bt_exit = (Button) findViewById(R.id.bt_wojia_setting_exit);
	}

	private void setoncliker() {
		rl_tongyong.setOnClickListener(this);
		rl_anquan.setOnClickListener(this);
	//	rl_commonbudget.setOnClickListener(this);
		rl_news.setOnClickListener(this);
		rl_clear.setOnClickListener(this);
		rl_about.setOnClickListener(this);
		bt_exit.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent(this, WojiaSettingContentActivity.class);
		switch (v.getId()) {
		case R.id.rl_wojia_setting_tongyong:

			intent.putExtra("position", 0);
			startActivity(intent);
			break;
		case R.id.rl_wojia_setting_anquan:

			intent.putExtra("position", 1);
			startActivity(intent);
			break;

		case R.id.rl_wojia_setting_news:

			intent.putExtra("position", 3);
			startActivity(intent);
			break;
		case R.id.bt_wojia_setting_exit:

			AlertDialog.Builder builder = new Builder(WojiaSettingActivity.this);
			builder.setMessage(getString(R.string.wojia_main_suretoexit));

			builder.setTitle(getString(R.string.system_prompt));

			builder.setPositiveButton(getString(R.string.system_sure),
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
							new InitExitUtil(WojiaSettingActivity.this)
									.ExitSetting();
							MyApplication.getInstance().exit();
						}
					});

			builder.setNegativeButton(getString(R.string.system_cancel),
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
						}
					});

			builder.create().show();

			break;
		default:
			break;

		}
		
	}
}
