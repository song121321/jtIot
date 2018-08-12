package song.honey.fragment;

import java.util.ArrayList;

import song.honey.bean.BudgetBatchBean;
import song.honey.db.BudgetBatchSQLManager;
import song.honey.util.SwitchButtonChangeListener;
import song.shuang.honey.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.kyleduo.switchbutton.SwitchButton;

public class WojiaSettingCommonBudgetFragment extends Fragment {
	private View v;
	private ListView mListView;
	private ArrayList<BudgetBatchBean> bbblist = new ArrayList<BudgetBatchBean>();
	private LayoutInflater mInflater;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		final int color = getActivity().getResources().getColor(
				R.color.text_balck);
		v = inflater
				.inflate(R.layout.fragment_wojia_setting_commonbudget, null);
		mInflater = inflater;
		mListView = (ListView) v.findViewById(R.id.lv_fragment_wojia_setting);
		getCommonBudget();
		final BaseAdapter adapter = new BaseAdapter() {

			@Override
			public View getView(int position, View convertView, ViewGroup arg2) {
				ViewHolder holder = null;
				if (convertView == null) {
					holder = new ViewHolder();
					convertView = mInflater.inflate(
							R.layout.item_wojia_setting_pic, null);
					holder.tv = (TextView) convertView
							.findViewById(R.id.tv_item_wojia_setting_text);
					holder.icon = (ImageView) convertView
							.findViewById(R.id.iv_item_wojia_setting_pic);
					holder.switchButton = (SwitchButton) convertView
							.findViewById(R.id.sb_item_wojia_setting_on);

					// .setOnCheckedChangeListener(new
					// SwitchButtonChangeListener(itemName[position],position,getActivity()));
					convertView.setTag(holder);
				} else {
					holder = (ViewHolder) convertView.getTag();
				}
				BudgetBatchBean bbean = bbblist.get(position);
				holder.tv.setText(bbean.getName());
				holder.tv.setTextColor(color);
				if (bbean.getName().contains("购物")
						|| bbean.getName().contains("超市")
						|| bbean.getName().contains("网购")) {
					holder.icon.setImageResource(R.drawable.type_shopping);
				} else if (bbean.getName().contains("话费")) {
					holder.icon.setImageResource(R.drawable.type_huafei);
				} else if (bbean.getName().equals("饭卡")) {
					holder.icon.setImageResource(R.drawable.type_fanka);
				} else if (bbean.getName().contains("交通")
						|| bbean.getName().contains("公交卡")) {
					holder.icon.setImageResource(R.drawable.type_jiaotong);
				} else if (bbean.getName().contains("浪漫")) {
					holder.icon.setImageResource(R.drawable.type_langman);
				} else if (bbean.getName().equals("美容")) {
					holder.icon.setImageResource(R.drawable.type_meirong);
				} else if (bbean.getName().contains("旅游")
						|| bbean.getName().contains("穷游")) {
					holder.icon.setImageResource(R.drawable.type_lvyou);
				} else if (bbean.getName().contains("生活")) {
					holder.icon.setImageResource(R.drawable.type_shenghuo);
				} else if (bbean.getName().equals("水果")) {
					holder.icon.setImageResource(R.drawable.type_shuiguo);
				} else if (bbean.getName().equals("小吃")) {
					holder.icon.setImageResource(R.drawable.type_xiaochi);
				} else if (bbean.getName().equals("学习")) {
					holder.icon.setImageResource(R.drawable.type_xuexi);
				} else if (bbean.getName().equals("衣服")) {
					holder.icon.setImageResource(R.drawable.type_yifu);
				} else if (bbean.getName().equals("医疗")) {
					holder.icon.setImageResource(R.drawable.type_yiliao);
				} else {
					holder.icon.setImageResource(R.drawable.type_other);
				}

				holder.switchButton.setChecked(bbean.isIsusual());
				holder.switchButton
						.setOnCheckedChangeListener(new SwitchButtonChangeListener(
								bbean, getActivity()));
				return convertView;
			}

			@Override
			public long getItemId(int arg0) {
				return arg0;
			}

			@Override
			public Object getItem(int arg0) {
				return arg0;
			}

			@Override
			public int getCount() {
				return bbblist.size();
			}
		};
		mListView.setAdapter(adapter);
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// boolean c = PreferenceWriteOrRead.readFormPreference(
				// getActivity(), itemName[arg2]) ^ presentCheck;
				// PreferenceWriteOrRead.wirteIntoPrefrence(getActivity(),
				// itemName[arg2], c);
				// adapter.notifyDataSetChanged();
			}
		});

		return v;
	}

	private void getCommonBudget() {
		BudgetBatchSQLManager bbsm = new BudgetBatchSQLManager(getActivity());
		bbblist = bbsm.getBudgetBatch("");

	}

	static class ViewHolder {
		public TextView tv;
		public ImageView icon;
		public SwitchButton switchButton;
	}
}
