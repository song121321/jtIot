package song.jtslkj.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import song.jtslkj.adapter.ConsumeAdapter;
import song.jtslkj.adapter.SectionAdapter;
import song.jtslkj.app.MyApplication;
import song.jtslkj.bean.ConsumeBean;
import song.jtslkj.config.MyConfig;
import song.jtslkj.db.ConsumeSQLManager;
import song.jtslkj.ui.DragLayout;
import song.jtslkj.ui.DragLayout.DragListener;
import song.jtslkj.util.ParseTools;
import song.jtslkj.util.ToolBox;
import song.jtslkj.util.WebServiceUtil;
import song.jtslkj.xlistview.XListView;
import song.jtslkj.xlistview.XListView.IXListViewListener;
import com.jtslkj.R;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class BaijiaConsumeActivity extends BaseActivity implements
		OnClickListener, OnItemClickListener, IXListViewListener {
	/** 左边侧滑菜单 */
	private DragLayout mDragLayout;
	private ListView menuListView;// 菜单列表
	private XListView consumelist;
	private ListView section_list;
	private LinearLayout ll_seacher, ll_add, ll_bar;
	private TextView tv_title, tv_time, tv_leixing, tv_person;
	private ImageView iv_month, iv_leixing, iv_person;
	//private LinearLayout linLayout;
	private ArrayList<ConsumeBean> consumelistdata = new ArrayList<ConsumeBean>();
	private ArrayList<ConsumeBean> updateconsumelistdata = new ArrayList<ConsumeBean>();
	private ArrayList<String> personarr = new ArrayList<String>();
	private ArrayList<String> typearr = new ArrayList<String>();
	private ArrayList<String> montharr = new ArrayList<String>();
	private SectionAdapter secAdapter;
	private String month, type, person;
	private PopupWindow mPopWin;
	private int sectionindex, updatenumber;
	private String deleteid;
	int longitemchoseindex = 0;// 常安是的选择的选项索引
	private List<Map<String, Object>> data;
	private ConsumeSQLManager csm = new ConsumeSQLManager(
			BaijiaConsumeActivity.this);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_baijia_consume_main);
		MyApplication.getInstance().addActivity(this);
		findviewbyid();

		initView();
		initPopupWindow();
	}
	private void findviewbyid() {
		mDragLayout = (DragLayout) findViewById(R.id.dl_baijia_consume);
		menuListView = (ListView) findViewById(R.id.menu_listview);
		consumelist = (XListView) findViewById(R.id.consumelist);
		tv_title = (TextView) findViewById(R.id.tv_actionbar_title);
		tv_time = (TextView) findViewById(R.id.tv_baijia_section_month);
		tv_leixing = (TextView) findViewById(R.id.tv_baijia_section_leixing);
		tv_person = (TextView) findViewById(R.id.tv_baijia_section_person);
		iv_month = (ImageView) findViewById(R.id.iv_baijia_mark_month);
		iv_leixing = (ImageView) findViewById(R.id.iv_baijia_mark_leixing);
		iv_person = (ImageView) findViewById(R.id.iv_baijia_mark_person);
		ll_seacher = (LinearLayout) findViewById(R.id.ll_actionbar_left);
		ll_add = (LinearLayout) findViewById(R.id.ll_actionbar_right);
	//	linLayout = (LinearLayout) findViewById(R.id.dl_baijia_consume);

	}

	private void initView() {
		consumelist.setPullLoadEnable(true);
		consumelist.setPullRefreshEnable(true);
		consumelist.setXListViewListener(this);
		consumelist.setOnItemClickListener(this);
		month = ToolBox.getcurrentmonth();
		type = "";
		person = "";
		sectionindex = 1;
		initActionBar();
		setlongclick();
		refresh();
		// new ConsumeTask("", "", "", true).execute();

		new ConsumeLateTask(csm.getMaxId()).execute();
		System.out.println("csm.getMaxId()" + csm.getMaxId());
	}

	private void initdb(ArrayList<ConsumeBean> data) {
		showLoadingDialog("稍等，我正在更新数据库");
		new DBUpdateTask(data).execute();
	}

	private void setconsumeadapter() {

		ConsumeAdapter consumeadapter = new ConsumeAdapter(
				BaijiaConsumeActivity.this, consumelistdata);
		consumelist.setAdapter(consumeadapter);

		closeLoadingDialog();
		Toast.makeText(
				BaijiaConsumeActivity.this,
				"总计消费" + consumeadapter.getCount() + "笔，总金额为"
						+ consumeadapter.getTotalMoney(), Toast.LENGTH_LONG)
				.show();

	}

	private void initActionBar() {
		ImageView iv_search = (ImageView) findViewById(R.id.iv_actionbar_search);
		ImageView iv_add = (ImageView) findViewById(R.id.iv_actionbar_add);
		tv_title.setText(getString(R.string.baijia_baijiajilu));
		mDragLayout.setDragListener(new DragListener() {// 动作监听
					@Override
					public void onOpen() {
					}

					@Override
					public void onClose() {
					}

					@Override
					public void onDrag(float percent) {

					}
				});
		data = new ArrayList<Map<String, Object>>();
		Map<String, Object> item = new HashMap<String, Object>();
		item.put("item", "预算");
		data.add(item);
		Map<String, Object> item1 = new HashMap<String, Object>();
		item1.put("item", "消费");
		data.add(item1);
		menuListView.setAdapter(new SimpleAdapter(this, data,
				R.layout.menulist_item_text, new String[] { "item" },
				new int[] { R.id.menu_text }));
		menuListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				if (arg2 == 0) {
					Intent intent = new Intent(BaijiaConsumeActivity.this,
							BaijiaBudgetActivity.class);
					startActivity(intent);
					BaijiaConsumeActivity.this.finish();
				} else {
					mDragLayout.close();
				}
			}
		});
		iv_add.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(BaijiaConsumeActivity.this,
						BaijiaConsumeAddActivity.class);
				startActivity(intent);
			}
		});

		iv_search.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(BaijiaConsumeActivity.this,
						BaijiaConsumeSearchActivity.class);
				startActivity(intent);
			}
		});
	}

	private void refresh() {
		showLoadingDialog();
		new ConsumeTask(person, type, month).execute();
		UpdateLoglateID();

	}

	private void setlongclick() {
		consumelist.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				longitemchoseindex = arg2 - 1;

				final String[] items = { "修改", "删除" };
				new AlertDialog.Builder(BaijiaConsumeActivity.this)
						.setTitle("编辑")
						.setIcon(android.R.drawable.ic_dialog_info)
						.setItems(items, new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								if (which == 0) {
									Toast.makeText(getApplicationContext(),
											items[which].toString(),
											Toast.LENGTH_SHORT).show();
									Uri uri = Uri.parse("http://www.baidu.com"); // Ҫ���ӵĵ�ַ
									Intent intent = new Intent(
											Intent.ACTION_VIEW, uri);
									startActivity(intent);

								} else {
									Dialog alertDialog = new AlertDialog.Builder(
											BaijiaConsumeActivity.this)
											.setTitle("删除提示")
											.setMessage("你确定要删除吗")
											.setIcon(
													android.R.drawable.ic_dialog_info)
											.setPositiveButton(
													getString(R.string.system_sure),
													new DialogInterface.OnClickListener() {

														@Override
														public void onClick(
																DialogInterface dialog,
																int which) {
															ConsumeBean cb = consumelistdata
																	.get(longitemchoseindex);
															deleteid = cb
																	.getId();
															showLoadingDialog("正在删除...");
															new DeleteConsumeTask(
																	deleteid)
																	.execute();
															addlog("2", "消费名：" + cb.getName(),"1");
														}
													})
											.setNegativeButton(
													getString(R.string.system_cancel),
													new DialogInterface.OnClickListener() {

														@Override
														public void onClick(
																DialogInterface dialog,
																int which) {

														}
													}).create();
									alertDialog.show();
								}
								// refresh();
								dialog.dismiss();

							}
						}).setNegativeButton("取消", null).show();
				return false;
			}
		});
	}

	private void initPopupWindow() {
		String[] persons = getResources().getStringArray(R.array.person_arr);
		for (int i = 0; i < persons.length; i++) {
			personarr.add(persons[i]);
		}

		new TypeTask(month).execute();
		montharr = ToolBox.generatemonthlist("2014-10-01");
		tv_time.setOnClickListener(this);
		tv_leixing.setOnClickListener(this);
		tv_person.setOnClickListener(this);
	}

	/**
	 * 数据库更新异步任务类，因为把数据库更新放在主线程里面太慢了，影响效果，所以额外开启一个线程
	 * 
	 * @author Ryan
	 * 
	 */
	private class DBUpdateTask extends AsyncTask<Void, Void, Boolean> {
		ArrayList<ConsumeBean> cblist;
		ConsumeSQLManager csm = new ConsumeSQLManager(
				BaijiaConsumeActivity.this);

		public DBUpdateTask(ArrayList<ConsumeBean> cblist) {
			super();
			this.cblist = cblist;
		}

		@Override
		protected Boolean doInBackground(Void... arg0) {

		//	csm.deleteConsume();
			if (csm.addConsumebean(cblist)) {
				// Toast.makeText(BaijiaConsumeActivity.this,
				// "好了，数据库更新完毕，插入成功",Toast.LENGTH_SHORT).show();
				return true;
			} else
				return false;
		};

		@Override
		protected void onPostExecute(Boolean result) {
			// 如果数据库更新完毕，就通知用户成功，否则失败
			super.onPostExecute(result);
			closeLoadingDialog();
			if (result) {
				Toast.makeText(BaijiaConsumeActivity.this,
						"数据库更新完毕,成功获取到" + updatenumber + "条数据",
						Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(BaijiaConsumeActivity.this, "哎呀，数据库更新出了点小问题",
						Toast.LENGTH_SHORT).show();
			}

		}
	}

	private class ConsumeTask extends AsyncTask<Void, Void, ArrayList<String>> {
		String person;
		String type;
		String month;

		public ConsumeTask(String person, String otype, String month) {
			super();
			this.person = person;
			this.type = otype;
			this.month = month;
		}

		@Override
		protected ArrayList<String> doInBackground(Void... arg0) {
			HashMap<String, String> params = new HashMap<String, String>();
			params.put("id", "");
			params.put("person", person);
			params.put("otype", type);
			params.put("otime", month);
			String nameSpace = MyConfig.nameSpace;
			String methodName = MyConfig.methodName_GetConsumeByIf;
			String endPoint = MyConfig.endPoint;
			return WebServiceUtil.getArrayOfAnyType(nameSpace, methodName,
					endPoint, params);
		}

		@Override
		protected void onPostExecute(ArrayList<String> result) {
			// 如果db为真，表示只是更新数据库，否则，更新UI
			super.onPostExecute(result);
			consumelistdata = ParseTools.strlist2conbeanlist(result);

			setconsumeadapter();

		}
	}

	private class ConsumeLateTask extends
			AsyncTask<Void, Void, ArrayList<String>> {
		String id;

		public ConsumeLateTask(String id) {
			super();
			this.id = id;
		}

		@Override
		protected ArrayList<String> doInBackground(Void... arg0) {
			HashMap<String, String> params = new HashMap<String, String>();
			params.put("id", id);
			String nameSpace = MyConfig.nameSpace;
			String methodName = MyConfig.methodName_GetConsumeLate;
			String endPoint = MyConfig.endPoint;
			return WebServiceUtil.getArrayOfAnyType(nameSpace, methodName,
					endPoint, params);
		}

		@Override
		protected void onPostExecute(ArrayList<String> result) {
			// 如果db为真，表示只是更新数据库，否则，更新UI
			super.onPostExecute(result);
			updateconsumelistdata = ParseTools.strlist2conbeanlist(result);
			if (updateconsumelistdata == null) {
				Toast.makeText(BaijiaConsumeActivity.this, "已是最新",
						Toast.LENGTH_SHORT).show();
			} else {
				updatenumber = updateconsumelistdata.size();
				initdb(updateconsumelistdata);
			}
		}
	}

	private class DeleteConsumeTask extends
			AsyncTask<Void, Void, ArrayList<String>> {

		String deleteid;

		public DeleteConsumeTask(String deleteid) {
			super();
			this.deleteid = deleteid;
		}

		@Override
		protected ArrayList<String> doInBackground(Void... arg0) {

			csm.deleteConsume(deleteid);
			HashMap<String, String> params = new HashMap<String, String>();
			params.put("id", deleteid);
			String nameSpace = MyConfig.nameSpace;
			String methodName = MyConfig.methodName_DeleteConsume;
			String endPoint = MyConfig.endPoint;
			return WebServiceUtil.getArrayOfAnyType(nameSpace, methodName,
					endPoint, params);
		}

		@Override
		protected void onPostExecute(ArrayList<String> result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			closeLoadingDialog();
			refresh();
		}

	}

	private class TypeTask extends AsyncTask<Void, Void, ArrayList<String>> {

		String month;

		public TypeTask(String month) {
			super();
			this.month = month;
		}

		@Override
		protected ArrayList<String> doInBackground(Void... arg0) {
			// showLoadingDialog("骚等，我在更新最新的预算");
			HashMap<String, String> params = new HashMap<String, String>();
			params.put("time", month);
			String nameSpace = MyConfig.nameSpace;
			String methodName = MyConfig.methodName_GetBudget;
			String endPoint = MyConfig.endPoint;
			return WebServiceUtil.getArrayOfAnyType(nameSpace, methodName,
					endPoint, params);
		}

		@Override
		protected void onPostExecute(ArrayList<String> result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			typearr = result;
			// closeLoadingDialog();
		}

	}

	protected void selectSecCheck(int index) {
		tv_time.setTextColor(0xff696969);
		tv_leixing.setTextColor(0xff696969);
		tv_person.setTextColor(0xff696969);
		iv_month.setImageResource(R.drawable.section_bg_normal);
		iv_leixing.setImageResource(R.drawable.section_bg_normal);
		iv_person.setImageResource(R.drawable.section_bg_normal);
		switch (index) {
		case 1:
			tv_time.setTextColor(0xff14a19c);
			iv_month.setImageResource(R.drawable.section_bg_selected);
			break;
		case 2:
			tv_leixing.setTextColor(0xff14a19c);
			iv_leixing.setImageResource(R.drawable.section_bg_selected);
			break;
		case 3:
			tv_person.setTextColor(0xff14a19c);
			iv_person.setImageResource(R.drawable.section_bg_selected);
			break;
		}
	}

	private void showSectionPop(int width, int height, final int secindex) {
		selectSecCheck(sectionindex);
		ll_bar = (LinearLayout) LayoutInflater.from(BaijiaConsumeActivity.this)
				.inflate(R.layout.popup_category, null);
		section_list = (ListView) ll_bar
				.findViewById(R.id.lv_popwin_section_list);
		section_list.setOnItemClickListener(this);
		switch (secindex) {
		case 1:
			secAdapter = new SectionAdapter(BaijiaConsumeActivity.this,
					montharr, tv_time.getText().toString());
			break;
		case 2:
			secAdapter = new SectionAdapter(BaijiaConsumeActivity.this,
					typearr, tv_leixing.getText().toString());
			break;
		case 3:
			secAdapter = new SectionAdapter(BaijiaConsumeActivity.this,
					personarr, tv_person.getText().toString());

			break;

		}
		section_list.setAdapter(secAdapter);
		mPopWin = new PopupWindow(ll_bar, width, LayoutParams.WRAP_CONTENT,
				true);
		mPopWin.setOnDismissListener(new OnDismissListener() {
			@Override
			public void onDismiss() {
				// TODO Auto-generated method stub
				// selectSecCheck(4);
			}
		});
		mPopWin.setBackgroundDrawable(new BitmapDrawable());
		mPopWin.showAsDropDown(tv_time, 0, 0);
		mPopWin.update();
	}

	@Override
	public void onLoadMore() {
		Toast.makeText(BaijiaConsumeActivity.this, "loadmore",
				Toast.LENGTH_SHORT).show();
		consumelist.stopLoadMore();
	}

	@Override
	public void onRefresh() {
		refresh();
		consumelist.stopRefresh();
		consumelist.setRefreshTime("上一次更新：" + ToolBox.getcurrentdatetime());
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.tv_baijia_section_month:
			sectionindex = 1;
			showSectionPop(mDragLayout.getWidth(), mDragLayout.getHeight(), 1);
			break;

		case R.id.tv_baijia_section_leixing:
			new TypeTask(month).execute();
			sectionindex = 2;
			showSectionPop(mDragLayout.getWidth(), mDragLayout.getHeight(), 2);
			break;
		case R.id.tv_baijia_section_person:
			sectionindex = 3;
			showSectionPop(mDragLayout.getWidth(), mDragLayout.getHeight(), 3);
			break;
		default:
			break;
		}

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View arg1, int position,
			long arg3) {
		if (R.id.consumelist == parent.getId()) {
			Intent intent = new Intent();
			intent.setClass(BaijiaConsumeActivity.this,
					BaijiaConsumeDetailActivity.class);
			Bundle bundle = new Bundle();
			bundle.putSerializable("cb", consumelistdata.get(position - 1));
			intent.putExtras(bundle);
			this.startActivity(intent);
			return;
		}

		switch (sectionindex) {
		case 1:
			secAdapter = new SectionAdapter(BaijiaConsumeActivity.this,
					montharr, montharr.get(position));

			tv_time.setText(montharr.get(position));
			month = ToolBox.sectionzhuanyi(montharr.get(position));
			refresh();

			break;
		case 2:

			secAdapter = new SectionAdapter(BaijiaConsumeActivity.this,
					typearr, typearr.get(position));
			tv_leixing.setText(typearr.get(position));
			type = ToolBox.sectionzhuanyi(typearr.get(position));
			mPopWin.dismiss();
			refresh();
			break;
		case 3:

			secAdapter = new SectionAdapter(BaijiaConsumeActivity.this,
					personarr, personarr.get(position));
			tv_person.setText(personarr.get(position));
			person = ToolBox.sectionzhuanyi(personarr.get(position));
			refresh();
			break;
		}
		section_list.setAdapter(secAdapter);
		mPopWin.dismiss();

	}
}
