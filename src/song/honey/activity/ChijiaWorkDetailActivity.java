package song.honey.activity;

import song.honey.app.MyApplication;
import song.honey.bean.WorkBean;
import song.honey.util.ToolBox;
import song.shuang.honey.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class ChijiaWorkDetailActivity extends Activity {
	WorkBean wb;
	ImageView iv_person;
	TextView tv_title, tv_status, tv_price, tv_number, tv_changge, tv_address,
			tv_worktime, tv_paytime, tv_content, tv_money;
	ImageView iv_back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chijia_work_detail);
		MyApplication.getInstance().addActivity(this);
		findview();
		initView();
		setclik();
	}

	private void findview() {
		iv_back = (ImageView) findViewById(R.id.iv_actionbar_left);
		tv_title = (TextView) findViewById(R.id.tv_actionbar_usual_title);
		tv_money = (TextView) findViewById(R.id.tv_fragment_chijia_work_detail_money);

		tv_status = (TextView) findViewById(R.id.tv_fragment_chijia_work_detail_status);
		tv_price = (TextView) findViewById(R.id.tv_fragment_chijia_work_detail_singleprice);
		tv_number = (TextView) findViewById(R.id.tv_fragment_chijia_work_detail_number);
		tv_changge = (TextView) findViewById(R.id.tv_fragment_chijia_work_detail_changgeprice);
		tv_address = (TextView) findViewById(R.id.tv_fragment_chijia_work_detail_address);
		tv_worktime = (TextView) findViewById(R.id.tv_fragment_chijia_work_detail_worktime);
		tv_content = (TextView) findViewById(R.id.tv_fragment_chijia_work_detail_content);
		tv_paytime = (TextView) findViewById(R.id.tv_fragment_chijia_work_detail_paytime);
		iv_person = (ImageView) findViewById(R.id.civ_fragment_chijia_work_detail_person);
	}

	private void initView() {
		Intent intent = this.getIntent();
		wb = (WorkBean) intent.getSerializableExtra("wb");
		if (wb != null) {
			tv_title.setText(ToolBox.formaDateStr(wb.getWorktime())
					+ wb.getName());
			String sFormat = "";
			String sFinal = "";
			if (wb.getStatus().equals(
					getString(R.string.chijia_work_item_havenotsettle))) {
				tv_status.setText(wb.getStatus());
				tv_status.setTextColor(getResources().getColor(R.color.tv_Red));
			}
			sFormat = getString(R.string.chijia_work_item_singleprice);
			sFinal = String.format(sFormat, wb.getSingleprice());
			tv_price.setText(sFinal);
			sFormat = getString(R.string.chijia_work_item_number);
			sFinal = String.format(sFormat, wb.getNumber());
			tv_number.setText(sFinal);
			sFormat = getString(R.string.chijia_work_item_changge);
			sFinal = String.format(sFormat, wb.getChangge());
			tv_changge.setText(sFinal);

			tv_address.setText(wb.getAddress());
			tv_worktime.setText(ToolBox.formaDateStr(wb.getWorktime()));
			if (!wb.getPaytime().startsWith("1900")) {
				tv_paytime.setText(ToolBox.formaDateStr(wb.getPaytime()));
			}
			tv_content.setText(wb.getContent());

			if (wb.getPerson().equals(getString(R.string.lisong))) {
				iv_person.setImageResource(R.drawable.avatar3);
			} else if (wb.getPerson().equals(
					getString(R.string.houshuangshuang))) {
				iv_person.setImageResource(R.drawable.avatar1);
			}

			float money = Float.parseFloat(wb.getSingleprice().trim())
					* Float.parseFloat(wb.getNumber())
					+ Float.parseFloat(wb.getChangge());
			tv_money.setText(money + "");
		}
	}

	private void setclik() {
		iv_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
	}

}
