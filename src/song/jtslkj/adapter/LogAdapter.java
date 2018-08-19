package song.jtslkj.adapter;

import java.util.ArrayList;

import song.jtslkj.bean.LogBean;
import song.jtslkj.util.ToolBox;
import com.jtslkj.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class LogAdapter extends android.widget.BaseAdapter {
	private Context context;
	private ArrayList<LogBean> loglist = new ArrayList<LogBean>();

	public LogAdapter(Context context, ArrayList<LogBean> loglist) {
		super();
		this.context = context;
		this.loglist = loglist;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return loglist.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int arg0, View convertView, ViewGroup arg2) {
		convertView = LayoutInflater.from(context).inflate(
				R.layout.item_wojia_log, null);
		TextView person = (TextView) convertView
				.findViewById(R.id.tv_item_wojia_log_person);
		ImageView iperson = (ImageView) convertView
				.findViewById(R.id.cv_item_log_wojia);
		TextView time = (TextView) convertView
				.findViewById(R.id.tv_item_wojia_log_time);
		TextView incident = (TextView) convertView
				.findViewById(R.id.tv_item_wojia_log_incident);
		TextView type = (TextView) convertView
				.findViewById(R.id.tv_item_wojia_log_type);
		TextView ip = (TextView) convertView
				.findViewById(R.id.tv_item_wojia_log_ip);
		ImageView client = (ImageView) convertView
				.findViewById(R.id.iv_item_wojia_log_client);
		ImageView module = (ImageView) convertView
				.findViewById(R.id.iv_item_wojia_log_module);
		LogBean lb = loglist.get(arg0);

		ip.setText(lb.getIp());
		if (lb.getClient().contains("金田物联")) {
			client.setImageResource(R.drawable.phone);
		}
		time.setText(ToolBox.timeformat(lb.getTime()));
		if (lb.getType().equals("登陆")) {
			type.setTextAppearance(context, R.style.text_violet_yuanjiao);
		} else if (lb.getType().equals("浏览")) {
			type.setTextAppearance(context, R.style.text_azure_yuanjiao);
		} else if (lb.getType().equals("删除")) {
			type.setTextAppearance(context, R.style.text_peachpuff_yuanjiao);
		} else if (lb.getType().equals("更新")) {
			type.setTextAppearance(context, R.style.text_tomato_yuanjiao);
		} else {
			type.setTextAppearance(context, R.style.text_green_yuanjiao);
		}
		type.setText(lb.getType());
		incident.setText(lb.getIncident());

		if (lb.getPerson().equals("admin") || lb.getPerson().equals("Admin")) {
			person.setText(context.getResources().getString(R.string.lisong));
			iperson.setImageResource(R.drawable.avatar3);
		}
		if (lb.getPerson().equals("hss") || lb.getPerson().equals("Hss")) {
			person.setText(context.getResources().getString(
					R.string.houshuangshuang));
			iperson.setImageResource(R.drawable.avatar1);
		}

		if(lb.getModule().trim().equals("预算")){
			 
			module.setImageResource(R.drawable.budget);
		}else if(lb.getModule().trim().equals("消费")){
			module.setImageResource(R.drawable.consume);
		}else if(lb.getModule().trim().equals("收入")){
			module.setImageResource(R.drawable.income);
		}else if(lb.getModule().trim().equals("工作")){
			module.setImageResource(R.drawable.work);
		}else if(lb.getModule().trim().equals("新闻")){
			module.setImageResource(R.drawable.news);
		}else if(lb.getModule().trim().equals("统计报表")){
			module.setImageResource(R.drawable.sta);
		}
		
		
		
		person.setTextColor(context.getResources().getColor(R.color.bg_Black));
		incident.setTextColor(context.getResources().getColor(R.color.bg_Black));
		return convertView;
	}
}
