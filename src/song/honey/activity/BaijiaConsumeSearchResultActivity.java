package song.honey.activity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import song.honey.adapter.ConsumeAdapter;
import song.honey.bean.ConsumeBean;
import song.honey.db.ConsumeSQLManager;
import song.honey.util.ToolBox;
import song.honey.xlistview.XListView;
import song.honey.xlistview.XListView.IXListViewListener;
import song.shuang.honey.R;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class BaijiaConsumeSearchResultActivity extends BaseActivity implements
		OnClickListener, OnItemClickListener, IXListViewListener {

	private XListView consumelist;
	private ArrayList<ConsumeBean> consumelistdata = new ArrayList<ConsumeBean>();
	private String searchtext;
	private ImageView iv_time, iv_money, iv_person, iv_search, iv_add;
	private TextView tv_time, tv_money, tv_person;
	private Boolean timeup, moneyup, personup;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_baijia_consume_searchresult);
		findview();
		initView();
		setcliker();
	}

	private void findview() {
		consumelist = (XListView) findViewById(R.id.xlv_search_consumelist);
		iv_search = (ImageView) findViewById(R.id.iv_actionbar_search);
		iv_add = (ImageView) findViewById(R.id.iv_actionbar_add);
		iv_time = (ImageView) findViewById(R.id.iv_baijia_mark_month);
		iv_money = (ImageView) findViewById(R.id.iv_baijia_mark_jine);
		iv_person = (ImageView) findViewById(R.id.iv_baijia_mark_person);
		tv_time = (TextView) findViewById(R.id.tv_baijia_section_month);
		tv_money = (TextView) findViewById(R.id.tv_baijia_section_jine);
		tv_person = (TextView) findViewById(R.id.tv_baijia_section_person);
	}

	private void initView() {
		consumelist.setPullLoadEnable(false);
		consumelist.setPullRefreshEnable(true);
		consumelist.setXListViewListener(this);
		searchtext = getIntent().getExtras().getString("searchtext");
		showLoadingDialog();
		new DBSearchTask().execute();
		timeup = false;
		moneyup = false;
		personup = false;

	}

	private void setconsumeadapter() {

		ConsumeAdapter consumeadapter = new ConsumeAdapter(
				BaijiaConsumeSearchResultActivity.this, consumelistdata);
		consumelist.setAdapter(consumeadapter);

		closeLoadingDialog();
		Toast.makeText(
				BaijiaConsumeSearchResultActivity.this,
				"总计消费" + consumeadapter.getCount() + "笔，总金额为"
						+ consumeadapter.getTotalMoney(), Toast.LENGTH_LONG)
				.show();
		// showLoadingDialog("稍等，我正在更新数据库");

	}

	private void setcliker() {
		consumelist.setOnItemClickListener(this);
		iv_search.setOnClickListener(this);
		iv_add.setOnClickListener(this);
		iv_time.setOnClickListener(this);
		iv_money.setOnClickListener(this);
		iv_person.setOnClickListener(this);
		tv_time.setOnClickListener(this);
		tv_money.setOnClickListener(this);
		tv_person.setOnClickListener(this);
	}

	// 根据不同的选择项，加深颜色显示区别
	private void sectionchecked(int sec) {
		switch (sec) {
		case 1:
			if (timeup) {
				iv_time.setImageResource(R.drawable.baijia_search_up);
				Collections
						.sort(consumelistdata, new ConsumeBeanSortByTimeUp());
			} else {
				iv_time.setImageResource(R.drawable.baijia_search_down);
				Collections.sort(consumelistdata,
						new ConsumeBeanSortByTimeDown());
			}
			tv_time.setTextColor(getResources().getColor(R.color.text_checked));
			tv_money.setTextColor(getResources().getColor(
					R.color.main_textcolor_normal));
			iv_money.setImageResource(R.drawable.baijia_search_normal);
			tv_person.setTextColor(getResources().getColor(
					R.color.main_textcolor_normal));
			iv_person.setImageResource(R.drawable.baijia_search_normal);
			setconsumeadapter();
			timeup = !timeup;
			break;
		case 2:
			if (moneyup) {
				iv_money.setImageResource(R.drawable.baijia_search_up);
				Collections.sort(consumelistdata,
						new ConsumeBeanSortByMoneyUp());

			} else {
				iv_money.setImageResource(R.drawable.baijia_search_down);
				Collections.sort(consumelistdata,
						new ConsumeBeanSortByMoneyDown());

			}
			tv_time.setTextColor(getResources().getColor(
					R.color.main_textcolor_normal));
			iv_time.setImageResource(R.drawable.baijia_search_normal);

			tv_money.setTextColor(getResources().getColor(R.color.text_checked));

			tv_person.setTextColor(getResources().getColor(
					R.color.main_textcolor_normal));
			iv_person.setImageResource(R.drawable.baijia_search_normal);

			setconsumeadapter();
			moneyup = !moneyup;
			break;
		case 3:
			if (personup) {
				iv_person.setImageResource(R.drawable.baijia_search_up);
				Collections.sort(consumelistdata,
						new ConsumeBeanSortByPersonUp());

			} else {
				iv_person.setImageResource(R.drawable.baijia_search_down);
				Collections.sort(consumelistdata,
						new ConsumeBeanSortByPersonDown());

			}
			tv_time.setTextColor(getResources().getColor(
					R.color.main_textcolor_normal));
			iv_time.setImageResource(R.drawable.baijia_search_normal);
			tv_money.setTextColor(getResources().getColor(
					R.color.main_textcolor_normal));
			iv_money.setImageResource(R.drawable.baijia_search_normal);
			tv_person.setTextColor(getResources()
					.getColor(R.color.text_checked));
			setconsumeadapter();
			personup = !personup;
			break;

		default:
			break;
		}

	}

	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onRefresh() {
		new DBSearchTask().execute();
		consumelist.stopRefresh();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		switch (v.getId()) {
		case R.id.iv_baijia_mark_month:
			sectionchecked(1);
			break;

		case R.id.tv_baijia_section_month:
			sectionchecked(1);
			break;

		case R.id.iv_baijia_mark_jine:
			sectionchecked(2);
			break;
		case R.id.tv_baijia_section_jine:
			sectionchecked(2);
			break;
		case R.id.iv_baijia_mark_person:
			sectionchecked(3);
			break;
		case R.id.tv_baijia_section_person:
			sectionchecked(3);
			break;
		case R.id.iv_actionbar_add:
			Intent intent = new Intent(this,
					BaijiaConsumeAddActivity.class);
			startActivity(intent);
		case R.id.iv_actionbar_search:
			Intent intent1 = new Intent(this,
					BaijiaConsumeSearchActivity.class);
			startActivity(intent1);
			break;
		default:
			break;
		}
	}

	private class DBSearchTask extends
			AsyncTask<Void, Void, ArrayList<ConsumeBean>> {
		ConsumeSQLManager csm = new ConsumeSQLManager(
				BaijiaConsumeSearchResultActivity.this);

		@Override
		protected ArrayList<ConsumeBean> doInBackground(Void... arg0) {
			ArrayList<ConsumeBean> cblist = csm.getConsumeVague(searchtext);
			return cblist;
		};

		@Override
		protected void onPostExecute(ArrayList<ConsumeBean> result) {
			super.onPostExecute(result);
			consumelistdata = result;
			setconsumeadapter();
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View arg1, int position,
			long arg3) {
		if (R.id.xlv_search_consumelist == parent.getId()) {
			Intent intent = new Intent();
			intent.setClass(BaijiaConsumeSearchResultActivity.this,
					BaijiaConsumeDetailActivity.class);
			Bundle bundle = new Bundle();
			bundle.putSerializable("cb", consumelistdata.get(position - 1));
			intent.putExtras(bundle);
			this.startActivity(intent);
			return;
		}

	}

	// 根据time排序降序
	class ConsumeBeanSortByTimeUp implements Comparator<ConsumeBean> {
		public int compare(ConsumeBean cb1, ConsumeBean cb2) {
			return ToolBox.comparedate(ToolBox.timeformat(cb1.getOtime()),
					ToolBox.timeformat(cb2.getOtime()));

		}
	}

	// 根据time排序降序
	class ConsumeBeanSortByTimeDown implements Comparator<ConsumeBean> {
		public int compare(ConsumeBean cb1, ConsumeBean cb2) {
			return -ToolBox.comparedate(ToolBox.timeformat(cb1.getOtime()),
					ToolBox.timeformat(cb2.getOtime()));

		}
	}

	// 根据money排序升序
	class ConsumeBeanSortByMoneyUp implements Comparator<ConsumeBean> {
		public int compare(ConsumeBean cb1, ConsumeBean cb2) {
			float m1 = Float.parseFloat(cb1.getMoney());
			float m2 = Float.parseFloat(cb2.getMoney());
			if (m1 >= m2)
				return 1;
			else
				return -1;
		}
	}

	// 根据money排序降序
	class ConsumeBeanSortByMoneyDown implements Comparator<ConsumeBean> {
		public int compare(ConsumeBean cb1, ConsumeBean cb2) {
			float m1 = Float.parseFloat(cb1.getMoney());
			float m2 = Float.parseFloat(cb2.getMoney());
			if (m1 >= m2)
				return -1;
			else
				return 1;
		}
	}

	// 根据person排序降序
	class ConsumeBeanSortByPersonDown implements Comparator<ConsumeBean> {
		public int compare(ConsumeBean cb1, ConsumeBean cb2) {
			if (cb1.getPerson().compareTo(cb2.getPerson()) > 0) {
				return 1;
			} else {
				return -1;
			}

		}
	}

	// 根据person排序降序
	class ConsumeBeanSortByPersonUp implements Comparator<ConsumeBean> {
		public int compare(ConsumeBean cb1, ConsumeBean cb2) {
			if (cb1.getPerson().compareTo(cb2.getPerson()) < 0) {
				return 1;
			} else {
				return -1;
			}

		}
	}

}
