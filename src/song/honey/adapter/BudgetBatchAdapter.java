package song.honey.adapter;

import java.util.ArrayList;

import song.honey.bean.BudgetBatchBean;
import song.shuang.honey.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;

public class BudgetBatchAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<BudgetBatchBean> bbblist = new ArrayList<BudgetBatchBean>();
	private onCheckedChanged listener;

	public BudgetBatchAdapter(Context context,
			ArrayList<BudgetBatchBean> bbblist) {
		super();
		this.setContext(context);
		this.setBbblist(bbblist);
	}

	public int getCheckedCount() {
		int sum = 0;
		for (BudgetBatchBean bbb : bbblist) {
			if (bbb.isIscheck()) {
				sum += 1;
			}
		}
		return sum;
	}

	public float getCheckedMoney() {
		float sum = 0f;
		for (BudgetBatchBean bbb : bbblist) {
			if (bbb.isIscheck()) {
				sum += Float.parseFloat(bbb.getMoney().trim());
			}
		}
		return sum;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return bbblist.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return bbblist.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup arg2) {
		ViewHolder holder = null;
		BudgetBatchBean bbean = bbblist.get(position);
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.item_baijia_budget_addbatch, null);
			holder.name = (TextView) convertView
					.findViewById(R.id.tv_item_budget_addbatch_name);
			holder.ischek = (CheckBox) convertView
					.findViewById(R.id.cb_item_budget_addbatch_ischeck);
			holder.icon = (ImageView) convertView
					.findViewById(R.id.iv_item_budget_addbatch_image);
			holder.money = (TextView) convertView
					.findViewById(R.id.tv_item_budget_addbatch_money);
			holder.usual = (TextView) convertView
					.findViewById(R.id.tv_item_budget_addbatch_isusual);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.ischek.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				bbblist.get(position).setIscheck(arg1);
				listener.NoticeChangge();
			}
		});
		holder.ischek.setChecked(bbblist.get(position).isIscheck());
		if (bbblist.get(position).isIsusual()) {
			holder.usual.setVisibility(View.VISIBLE);
		}
		String sFormat, sFinal;
		sFormat = context.getResources().getString(
				R.string.baijia_add_monthbudget);
		sFinal = String.format(sFormat, bbblist.get(position).getMoney());
		holder.money.setText(sFinal);
		holder.name.setText(bbblist.get(position).getName());
		if (bbean.getName().contains("购物") || bbean.getName().contains("超市")
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
		} else if (bbean.getName().equals("美容")
				|| bbean.getName().equals("化妆品")) {
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
		return convertView;

	}

	public ArrayList<BudgetBatchBean> getBbblist() {
		return bbblist;
	}

	public void setBbblist(ArrayList<BudgetBatchBean> bbblist) {
		this.bbblist = bbblist;
	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	static class ViewHolder {
		public CheckBox ischek;
		public TextView name;
		public ImageView icon;
		public TextView money;
		public TextView usual;
	}

	public interface onCheckedChanged {

		public void NoticeChangge();
	}

	public void setOnCheckedChanged(onCheckedChanged listener) {
		this.listener = listener;
	}

}
