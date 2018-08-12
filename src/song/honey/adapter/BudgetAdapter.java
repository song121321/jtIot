package song.honey.adapter;

import java.util.ArrayList;

import song.honey.bean.BudgetBean;
import song.shuang.honey.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class BudgetAdapter extends BaseAdapter {
	private float totalmoney, leftmoney, usedmoney;
	private Context context;
	private ArrayList<BudgetBean> budgetlist = new ArrayList<BudgetBean>();

	public BudgetAdapter(Context context, ArrayList<BudgetBean> budgetlist) {
		super();
		this.context = context;
		this.budgetlist = budgetlist;
		totalmoney = 0f;
		leftmoney = 0f;
		usedmoney = 0f;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return budgetlist.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return budgetlist.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	public float gettotalmoney() {
		for (int i = 0; i < getCount(); i++) {
			totalmoney = totalmoney
					+ Float.parseFloat(budgetlist.get(i).getMoney());
			usedmoney = usedmoney
					+ Float.parseFloat(budgetlist.get(i).getOmoneyuse());
		}
		leftmoney = totalmoney - usedmoney;
		return totalmoney;
	}

	public float getleftmoney() {
		return leftmoney;
	}

	public float getUsedmoney() {
		return usedmoney;
	}

	public void setUsedmoney(float usedmoney) {
		this.usedmoney = usedmoney;
	}

	@Override
	public View getView(int arg0, View convertView, ViewGroup arg2) {
		// TODO Auto-generated method stub
		convertView = LayoutInflater.from(context).inflate(
				R.layout.item_baijia_budget, null);
		TextView name = (TextView) convertView
				.findViewById(R.id.tv_item_budget_name);
		TextView money = (TextView) convertView
				.findViewById(R.id.tv_item_budget_money);
		TextView omoneyuse = (TextView) convertView
				.findViewById(R.id.tv_item_budget_used);
		TextView omoneyleft = (TextView) convertView
				.findViewById(R.id.tv_item_budget_left);
		TextView detail = (TextView) convertView
				.findViewById(R.id.tv_item_budget_detail);
		BudgetBean b = budgetlist.get(arg0);
		name.setText(b.getName());
		money.setText(b.getMoney());
		String sFormat, sFinal;
		sFormat = context.getResources().getString(R.string.baijia_budget_used);
		sFinal = String.format(sFormat, b.getOmoneyuse());
		omoneyuse.setText(sFinal);
		if (Float.parseFloat(b.getOmoneyuse().toString()) > 100) {
			omoneyuse.setTextColor(context.getResources().getColor(
					R.color.text_red));
		} else {
			omoneyuse.setTextColor(context.getResources().getColor(
					R.color.text_green));
		}
		sFormat = context.getResources().getString(R.string.baijia_budget_left);
		sFinal = String.format(
				sFormat,
				Float.parseFloat(b.getMoney())
						- Float.parseFloat(b.getOmoneyuse()));
		omoneyleft.setText(sFinal);
		if (Float.parseFloat(b.getMoney()) - Float.parseFloat(b.getOmoneyuse()) > 30) {
			omoneyleft.setTextColor(context.getResources().getColor(
					R.color.text_green));
		} else {
			omoneyleft.setTextColor(context.getResources().getColor(
					R.color.text_red));

		}
		detail.setText(b.getDetail());
		return convertView;
	}

}
