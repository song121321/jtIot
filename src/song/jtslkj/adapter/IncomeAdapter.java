package song.jtslkj.adapter;

import java.util.ArrayList;

import song.jtslkj.bean.IncomeBean;
import song.jtslkj.util.ToolBox;
import com.jtslkj.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class IncomeAdapter extends BaseAdapter {
	private String type;
	private String person;
	private String time;
	private Context context;
	private ArrayList<IncomeBean> incomelist = new ArrayList<IncomeBean>();

	public IncomeAdapter(Context context, ArrayList<IncomeBean> incomelist) {
		super();
		this.context = context;
		this.incomelist = incomelist;
	}

	public IncomeAdapter(Context context, ArrayList<IncomeBean> incomelist,String type, String person, String time) {
		this.type = type;
		this.person = person;
		this.time = time;
		this.context = context;
		if (!type.equals("")) {
			ArrayList<IncomeBean> sublist = new ArrayList<IncomeBean>();
			for (IncomeBean bean : incomelist) {
				if (bean.getLabel().trim().equals(type)) {
					sublist.add(bean);
				}
			}
			incomelist = sublist;
		}

		if (!person.equals("")) {
			ArrayList<IncomeBean> sublist = new ArrayList<IncomeBean>();
			for (IncomeBean bean : incomelist) {
				if (bean.getName().trim().equals(person)) {
					sublist.add(bean);
				}
			}
			incomelist = sublist;
		}
		if (!time.equals("")) {
			ArrayList<IncomeBean> sublist = new ArrayList<IncomeBean>();
			for (IncomeBean bean : incomelist) {
					sublist.add(bean);
			}
			incomelist = sublist;
		}
		this.incomelist = incomelist;
	}

	@Override
	public int getCount() {
		return incomelist.size();
	}

	@Override
	public Object getItem(int arg0) {
		return incomelist.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	public float getTotalMoney() {
		float sum = 0f;
		float single = 0f;
		for (IncomeBean ib : incomelist) {
			if (!ib.getMoney().trim().equals("")) {
				try {
					single = Float.parseFloat(ib.getMoney().trim());
				} catch (Exception e) {
					single = 0f;
				}
			}
			sum = sum + single;
		}
		return sum;
	}

	@Override
	public View getView(int arg0, View convertView, ViewGroup arg2) {
		convertView = LayoutInflater.from(context).inflate(
				R.layout.item_chijia_income, null);
		TextView title = (TextView) convertView
				.findViewById(R.id.tv_item_income_title);
		TextView money = (TextView) convertView
				.findViewById(R.id.tv_item_income_money);
		TextView xjk = (TextView) convertView
				.findViewById(R.id.tv_item_income_xjk);
		ImageView iv_person = (ImageView) convertView
				.findViewById(R.id.iv_item_income_person);
		TextView time = (TextView) convertView
				.findViewById(R.id.tv_item_income_time);

		String sFormat = "";
		String sFinal = "";
		IncomeBean ib = incomelist.get(arg0);
		time.setText(ToolBox.formaDateStr(ib.getTime()));
		sFormat = context.getResources().getString(
				R.string.chijia_work_item_zhongjia);
		sFinal = String.format(sFormat, ib.getMoney());
		money.setText(sFinal);
		title.setText(ib.getTitle());
		if (!ib.getLabel().trim().equals("1")) {
			xjk.setVisibility(View.GONE);
		}

		if (ib.getName().trim().equals("李松")) {
			iv_person.setImageResource(R.drawable.item_consume_person_ls);
		} else if (ib.getName().trim().equals("侯双双")) {
			iv_person.setImageResource(R.drawable.item_consume_person_hss);
		} else {
			iv_person.setImageResource(R.drawable.item_consume_person_public);
		}

		return convertView;
	}

}
