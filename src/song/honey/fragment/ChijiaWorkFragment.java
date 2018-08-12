package song.honey.fragment;

import java.util.ArrayList;
import java.util.HashMap;

import song.honey.activity.ChijiaWorkDetailActivity;
import song.honey.adapter.SectionAdapter;
import song.honey.adapter.WorkAdapter;
import song.honey.bean.WorkBean;
import song.honey.bean.WorkItemBean;
import song.honey.config.MyConfig;
import song.honey.util.ParseTools;
import song.honey.util.ToolBox;
import song.honey.util.WebServiceUtil;
import song.honey.xlistview.XListView;
import song.honey.xlistview.XListView.IXListViewListener;
import song.shuang.honey.R;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ChijiaWorkFragment extends Fragment implements OnClickListener,
		OnItemClickListener, IXListViewListener {
	private View v;
	RelativeLayout rl0, rl1, rl2, rl3;
	TextView tv1, tv2, tv3;
	ImageView iv1, iv2, iv3;
	XListView xlv;
	LinearLayout ll_bar;
	private String name, person, status;
	private PopupWindow mPopWin;
	private SectionAdapter secAdapter;
	private ListView section_list;
	private int sectionindex;
	private ArrayList<String> statusarr = new ArrayList<String>();
	private ArrayList<String> itemarr = new ArrayList<String>();
	private ArrayList<String> personarr = new ArrayList<String>();
	private ArrayList<WorkBean> worklistdata = new ArrayList<WorkBean>();
	private ArrayList<WorkBean> changgeworklistdata = new ArrayList<WorkBean>();
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		v = inflater.inflate(R.layout.fragment_chijia_work_income, null);
		findview();
		initView();
		initPopupWindow();
		new WorkListTask().execute();
		return v;
	}

	private void findview() {
		rl0 = (RelativeLayout) v
				.findViewById(R.id.rl_fragment_chijia_work_income);
		rl1 = (RelativeLayout) v
				.findViewById(R.id.rl_chijia_work_income_section1);
		rl2 = (RelativeLayout) v
				.findViewById(R.id.rl_chijia_work_income_section2);
		rl3 = (RelativeLayout) v
				.findViewById(R.id.rl_chijia_work_income_section3);
		tv1 = (TextView) v.findViewById(R.id.tv_chijia_work_income_section1);
		tv2 = (TextView) v.findViewById(R.id.tv_chijia_work_income_section2);
		tv3 = (TextView) v.findViewById(R.id.tv_chijia_work_income_section3);
		iv1 = (ImageView) v.findViewById(R.id.iv_chijia_work_income_section1);
		iv2 = (ImageView) v.findViewById(R.id.iv_chijia_work_income_section2);
		iv3 = (ImageView) v.findViewById(R.id.iv_chijia_work_income_section3);

		xlv = (XListView) v.findViewById(R.id.xlv_chijia_work_income_listview);
	}

	private void initView() {
		sectionindex = 1;
		name = "";
		status = "20";
		person = "";
		changgeworklistdata = worklistdata;
		xlv.setPullLoadEnable(true);
		xlv.setPullRefreshEnable(true);
		xlv.setXListViewListener(this);
		xlv.setOnItemClickListener(this);
	}

	private void initPopupWindow() {
		personarr.clear();
		statusarr.clear();
		String[] persons = getResources().getStringArray(R.array.person_arr1);
		for (int i = 0; i < persons.length; i++) {
			personarr.add(persons[i]);
		}
		String[] status = getResources().getStringArray(R.array.status_arr);
		for (int i = 0; i < status.length; i++) {
			statusarr.add(status[i]);
		}

		new WorItemTask().execute();
		tv1.setOnClickListener(this);
		tv2.setOnClickListener(this);
		tv3.setOnClickListener(this);
	}

	private void setworkadapter() {
		WorkAdapter wap = new WorkAdapter(getActivity(), worklistdata, name,
				person, status);
		xlv.setAdapter(wap);
		changgeworklistdata = wap.getdata();
		Toast.makeText(getActivity(),
				"总计工作" + wap.getCount() + "次，总金额为" + wap.getTotalMoney(),
				Toast.LENGTH_LONG).show();

	}

	protected void selectSecCheck(int index) {
		tv1.setTextColor(0xff696969);
		tv2.setTextColor(0xff696969);
		tv3.setTextColor(0xff696969);
		iv1.setImageResource(R.drawable.section_bg_normal);
		iv2.setImageResource(R.drawable.section_bg_normal);
		iv3.setImageResource(R.drawable.section_bg_normal);
		switch (index) {
		case 1:
			tv1.setTextColor(0xff14a19c);
			iv1.setImageResource(R.drawable.section_bg_selected);
			break;
		case 2:
			tv2.setTextColor(0xff14a19c);
			iv2.setImageResource(R.drawable.section_bg_selected);
			break;
		case 3:
			tv3.setTextColor(0xff14a19c);
			iv3.setImageResource(R.drawable.section_bg_selected);
			break;
		}
	}

	@SuppressWarnings("deprecation")
	private void showSectionPop(int width, int height, final int secindex) {
		selectSecCheck(sectionindex);
		ll_bar = (LinearLayout) LayoutInflater.from(getActivity()).inflate(
				R.layout.popup_category, null);
		section_list = (ListView) ll_bar
				.findViewById(R.id.lv_popwin_section_list);
		section_list.setOnItemClickListener(this);
		switch (secindex) {
		case 1:
			secAdapter = new SectionAdapter(getActivity(), itemarr, tv1
					.getText().toString());
			break;
		case 2:
			secAdapter = new SectionAdapter(getActivity(), personarr, tv2
					.getText().toString());
			break;
		case 3:
			secAdapter = new SectionAdapter(getActivity(), statusarr, tv3
					.getText().toString());

			break;

		}
		section_list.setAdapter(secAdapter);
		mPopWin = new PopupWindow(ll_bar, width, LayoutParams.WRAP_CONTENT,
				true);
		mPopWin.setBackgroundDrawable(new BitmapDrawable());
		mPopWin.showAsDropDown(tv1, 0, 0);
		mPopWin.update();
	}

	@Override
	public void onLoadMore() {
		xlv.stopLoadMore();
	}

	@Override
	public void onRefresh() {
		new WorkListTask().execute();
		xlv.stopRefresh();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View arg1, int position,
			long arg3) {
		if (R.id.xlv_chijia_work_income_listview == parent.getId()) {
			WorkBean wb = changgeworklistdata.get(position - 1);
			Intent i = new Intent(getActivity(), ChijiaWorkDetailActivity.class);
			i.putExtra("wb", wb);
			getActivity().startActivity(i);
			return;
		} else {
			switch (sectionindex) {
			case 1:
				secAdapter = new SectionAdapter(getActivity(), itemarr,
						itemarr.get(position));

				tv1.setText(itemarr.get(position));
				name = ToolBox.sectionzhuanyi(itemarr.get(position));
				status = "";
				setworkadapter();
				break;
			case 2:

				secAdapter = new SectionAdapter(getActivity(), personarr,
						personarr.get(position));
				tv2.setText(personarr.get(position));
				person = ToolBox.sectionzhuanyi(personarr.get(position));
				mPopWin.dismiss();
				status = "";
				setworkadapter();
				break;
			case 3:

				secAdapter = new SectionAdapter(getActivity(), statusarr,
						statusarr.get(position));
				tv3.setText(statusarr.get(position));
				status = ToolBox.sectionzhuanyi(statusarr.get(position));
				setworkadapter();
				break;
			}

			section_list.setAdapter(secAdapter);
			mPopWin.dismiss();
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_chijia_work_income_section1:
			sectionindex = 1;
			showSectionPop(rl0.getWidth(), rl0.getHeight(), 1);
			break;

		case R.id.tv_chijia_work_income_section2:
			// new TypeTask(month).execute();
			sectionindex = 2;
			showSectionPop(rl0.getWidth(), rl0.getHeight(), 2);
			break;
		case R.id.tv_chijia_work_income_section3:
			sectionindex = 3;
			showSectionPop(rl0.getWidth(), rl0.getHeight(), 3);
			break;
		default:
			break;
		}

	}

	private class WorItemTask extends AsyncTask<Void, Void, ArrayList<String>> {

		@Override
		protected ArrayList<String> doInBackground(Void... arg0) {
			HashMap<String, String> params = new HashMap<String, String>();
			String nameSpace = MyConfig.nameSpace;
			String methodName = MyConfig.methodName_GetWorkItem;
			String endPoint = MyConfig.endPoint;
			return WebServiceUtil.getArrayOfAnyType(nameSpace, methodName,
					endPoint, params);
		}

		@Override
		protected void onPostExecute(ArrayList<String> result) {
			super.onPostExecute(result);
			ArrayList<WorkItemBean> workitemlistdata = ParseTools
					.strlist2workitembeanlist(result);
			itemarr.clear();
			itemarr.add("所有");
			for (WorkItemBean workItemBean : workitemlistdata) {
				itemarr.add(workItemBean.getName());
			}
		}
	}

	private class WorkListTask extends AsyncTask<Void, Void, ArrayList<String>> {
		@Override
		protected ArrayList<String> doInBackground(Void... arg0) {
			HashMap<String, String> params = new HashMap<String, String>();
			params.put("name", "");
			params.put("person", "");
			params.put("status", "");
			params.put("order", "");
			String nameSpace = MyConfig.nameSpace;
			String methodName = MyConfig.methodName_GetWorkList;
			String endPoint = MyConfig.endPoint;
			return WebServiceUtil.getArrayOfAnyType(nameSpace, methodName,
					endPoint, params);
		}

		@Override
		protected void onPostExecute(ArrayList<String> result) {
			// 如果db为真，表示只是更新数据库，否则，更新UI
			super.onPostExecute(result);

			worklistdata = ParseTools.strlist2workbeanlist(result);
			setworkadapter();

		}
	}
}
