package song.honey.adapter;

import java.util.ArrayList;

import song.honey.bean.ConsumeBean;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import song.shuang.honey.R;

public class ConsumeAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<ConsumeBean> comsumelist = new ArrayList<ConsumeBean>();

	public ConsumeAdapter(Context context, ArrayList<ConsumeBean> comsumelist) {
		super();
		this.context = context;
		this.comsumelist = comsumelist;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return comsumelist.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return comsumelist.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public float getTotalMoney() {
		float total = 0f;
		for (ConsumeBean cb : comsumelist) {
			float money = Float.parseFloat(cb.getMoney());
			total = total + money;
		}
		return total;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		convertView = LayoutInflater.from(context).inflate(
				R.layout.item_baijia_consume, null);
		TextView name = (TextView) convertView
				.findViewById(R.id.tv_item_well_name);
		TextView time = (TextView) convertView
				.findViewById(R.id.tv_item_well_time);
		TextView money = (TextView) convertView
				.findViewById(R.id.tv_item_well_money);
		ImageView type = (ImageView) convertView
				.findViewById(R.id.iv_item_consume_type);
		ImageView person = (ImageView) convertView
				.findViewById(R.id.iv_item_well_type);

		ConsumeBean consumebean = comsumelist.get(position);
		name.setText(consumebean.getName());
		time.setText(consumebean.getOtime());
		String sFormat = "";
		String sFinal = "";
		sFormat = context.getResources().getString(
				R.string.chijia_work_item_zhongjia);
		sFinal = String.format(sFormat, consumebean.getMoney());
		money.setText(sFinal);
		if (consumebean.getOtype().contains("购物")
				|| consumebean.getOtype().contains("超市")
				|| consumebean.getOtype().contains("网购")) {
			type.setImageResource(R.drawable.type_shopping);
		} else if (consumebean.getOtype().contains("话费")) {
			type.setImageResource(R.drawable.type_huafei);
		} else if (consumebean.getOtype().equals("饭卡")) {
			type.setImageResource(R.drawable.type_fanka);
		} else if (consumebean.getOtype().contains("交通")
				|| consumebean.getOtype().contains("公交卡")) {
			type.setImageResource(R.drawable.type_jiaotong);
		} else if (consumebean.getOtype().contains("浪漫")) {
			type.setImageResource(R.drawable.type_langman);
		} else if (consumebean.getOtype().equals("美容")
				|| consumebean.getOtype().equals("化妆品")) {
			type.setImageResource(R.drawable.type_meirong);
		} else if (consumebean.getOtype().contains("旅游")
				|| consumebean.getOtype().contains("穷游")) {
			type.setImageResource(R.drawable.type_lvyou);
		} else if (consumebean.getOtype().contains("生活")) {
			type.setImageResource(R.drawable.type_shenghuo);
		} else if (consumebean.getOtype().equals("水果")) {
			type.setImageResource(R.drawable.type_shuiguo);
		} else if (consumebean.getOtype().equals("小吃")) {
			type.setImageResource(R.drawable.type_xiaochi);
		} else if (consumebean.getOtype().equals("学习")) {
			type.setImageResource(R.drawable.type_xuexi);
		} else if (consumebean.getOtype().equals("衣服")) {
			type.setImageResource(R.drawable.type_yifu);
		} else if (consumebean.getOtype().equals("医疗")) {
			type.setImageResource(R.drawable.type_yiliao);
		} else {
			type.setImageResource(R.drawable.type_other);
		}

		if (consumebean.getPerson().equals("李松")) {
			person.setImageResource(R.drawable.ls);
		} else if (consumebean.getPerson().equals("侯双双")) {
			person.setImageResource(R.drawable.hss);
		}

		return convertView;
	}

}
