package song.honey.activity;

import song.honey.app.MyApplication;
import song.honey.fragment.ChijiaIncomeFragment;
import song.honey.fragment.ChijiaWorkFragment;
import song.shuang.honey.R;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class ChijiaActivity extends BaseActivity {
	private ChijiaIncomeFragment inf;
	private ChijiaWorkFragment wof;
	private ImageView iv_add;
	private ImageView iv_settle;
	RadioGroup radiogroup;
	int nowradiobutton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyApplication.getInstance().addActivity(this);
		setContentView(R.layout.activity_chijia_main);
		findview();
		initdata();
		setcliker();
		setDefaultFragment();
	}

	private void findview() {
		radiogroup = (RadioGroup) findViewById(R.id.rdg_chijia_main);
		iv_add = (ImageView) findViewById(R.id.iv_chijia_main_right);
		iv_settle = (ImageView) findViewById(R.id.iv_chijia_main_left);
	}

	private void setDefaultFragment() {
		FragmentManager fm = getFragmentManager();
		FragmentTransaction transaction = fm.beginTransaction();
		wof = new ChijiaWorkFragment();
		transaction.replace(R.id.fl_chijia_main, wof);
		transaction.commit();
	}

	private void initdata() {
		nowradiobutton = 0;

	}

	private void setcliker() {
		iv_settle.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(ChijiaActivity.this,
						ChijiaWorkSettleActivity.class);
				startActivity(i);
			}
		});
		iv_add.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (nowradiobutton == 0) {
					Intent intent = new Intent(ChijiaActivity.this,
							ChijiaWorkAddActivity.class);
					startActivity(intent);
				} else {
					Intent intent = new Intent(ChijiaActivity.this,
							ChijiaIncomeAddActivity.class);
					startActivity(intent);
				}
			}
		});
		radiogroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup arg0, int checkedId) {
				FragmentManager fm = getFragmentManager();
				// 开启Fragment事务
				FragmentTransaction transaction = fm.beginTransaction();
				switch (checkedId) {
				case R.id.rdb_chijia_main_income:
					if (inf == null) {
						inf = new ChijiaIncomeFragment();
					}
					// 使用当前Fragment的布局替代fl_chijia_main的控件
					transaction.replace(R.id.fl_chijia_main, inf);
					UpdateLoglateID();
					nowradiobutton = 1;
					iv_settle.setVisibility(View.INVISIBLE);
					break;
				case R.id.rdb_chijia_main_work:
					if (wof == null) {
						wof = new ChijiaWorkFragment();
					}
					// 使用当前Fragment的布局替代fl_chijia_main的控件
					transaction.replace(R.id.fl_chijia_main, wof);
					UpdateLoglateID();
					nowradiobutton = 0;
					iv_settle.setVisibility(View.VISIBLE);
					break;
				}
				// 事务提交
				transaction.commit();
				fm = null;
				transaction = null;
			}
		});
	}

}
