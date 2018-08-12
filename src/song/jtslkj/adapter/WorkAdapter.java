package song.jtslkj.adapter;

import java.util.ArrayList;

import song.jtslkj.bean.WorkBean;
import song.jtslkj.util.ToolBox;
import com.jtslkj.R;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

@SuppressLint("NewApi")
public class WorkAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<WorkBean> worklist = new ArrayList<WorkBean>();
	private String name;
	private String person;
	private String status;

	public WorkAdapter(Context context, ArrayList<WorkBean> worklist) {
		super();
		this.context = context;
		this.worklist = worklist;
	}

	public WorkAdapter(Context context, ArrayList<WorkBean> worklist,
			String name, String person, String status) {
		super();
		this.context = context;

		this.name = name;
		this.person = person;
		this.status = status;
		if (!name.equals("")) {
			ArrayList<WorkBean> sublist = new ArrayList<WorkBean>();
			for (WorkBean workBean : worklist) {
				if (workBean.getName().trim().equals(name)) {
					sublist.add(workBean);
				}
			}
			worklist = sublist;
		}

		if (!person.equals("")) {
			ArrayList<WorkBean> sublist = new ArrayList<WorkBean>();
			for (WorkBean workBean : worklist) {
				if (workBean.getPerson().trim().equals(person)) {
					sublist.add(workBean);
				}
			}
			worklist = sublist;
		}
		if (!status.equals("")) {
			ArrayList<WorkBean> sublist = new ArrayList<WorkBean>();
			if (status.equals("20") && worklist.size() > 20) {
				for (int i = 0; i < 20; i++) {
					sublist.add(worklist.get(i));
				}
			} else {
				for (WorkBean workBean : worklist) {
					if (workBean.getStatus().trim().equals(status)) {
						sublist.add(workBean);
					}
				}
			}
			worklist = sublist;
		}
		this.worklist = worklist;
	}

	public ArrayList<WorkBean> getdata() {
		return worklist;
	}

	@Override
	public int getCount() {
		return worklist.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return worklist.get(arg0);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public float getTotalMoney() {

		float total = 0f;
		for (WorkBean cb : worklist) {
			float money = Float.parseFloat(cb.getZhongjia());
			total = total + money;
		}
		return total;
	}

	@SuppressLint("NewApi")
	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		convertView = LayoutInflater.from(context).inflate(
				R.layout.item_chijia_work, null);
		TextView name = (TextView) convertView
				.findViewById(R.id.tv_item_work_name);
		TextView singleprice = (TextView) convertView
				.findViewById(R.id.tv_item_work_singleprice);
		TextView number = (TextView) convertView
				.findViewById(R.id.tv_item_work_number);
		TextView person = (TextView) convertView
				.findViewById(R.id.tv_item_work_person);
		TextView changge = (TextView) convertView
				.findViewById(R.id.tv_item_work_changge);
		TextView worktime = (TextView) convertView
				.findViewById(R.id.tv_item_work_worktime);
		TextView status = (TextView) convertView
				.findViewById(R.id.tv_item_work_status);
		TextView zhongjia = (TextView) convertView
				.findViewById(R.id.tv_item_work_zhongjia);
		ImageView iv_person = (ImageView) convertView
				.findViewById(R.id.iv_item_work_gender);

		TextView iv_content = (TextView) convertView
				.findViewById(R.id.tv_item_work_ifcontent);
		String sFormat = "";
		String sFinal = "";

		WorkBean workbean = worklist.get(position);
		name.setText(workbean.getName());
		sFormat = context.getResources().getString(
				R.string.chijia_work_item_singleprice);
		sFinal = String.format(sFormat, workbean.getSingleprice());
		singleprice.setText(sFinal);
		sFormat = context.getResources().getString(
				R.string.chijia_work_item_number);
		sFinal = String.format(sFormat, workbean.getNumber());

		number.setText(sFinal);
		person.setText(workbean.getPerson());

		sFormat = context.getResources().getString(
				R.string.chijia_work_item_changge);
		sFinal = String.format(sFormat, workbean.getChangge());

		changge.setText(sFinal);
		worktime.setText(ToolBox.formaDateStr(workbean.getWorktime()));
		status.setText(workbean.getStatus());
		sFormat = context.getResources().getString(
				R.string.chijia_work_item_zhongjia);
		sFinal = String.format(sFormat, workbean.getZhongjia());
		zhongjia.setText(sFinal);
		if (workbean.getPerson().trim().equals("李松")) {
			iv_person.setImageResource(R.drawable.item_consume_person_ls);
		} else if (workbean.getPerson().trim().equals("侯双双")) {
			iv_person.setImageResource(R.drawable.item_consume_person_hss);
		} else {
			iv_person.setImageResource(R.drawable.item_consume_person_public);
		}
		if (workbean.getStatus().trim().equals("已结算")) {
			status.setBackground(context.getResources().getDrawable(
					R.drawable.btn_tab_bg_yijiesuan));
		} else {
			status.setBackground(context.getResources().getDrawable(
					R.drawable.btn_tab_bg_weijiesuan));
		}
		System.out.println();
		if (workbean.getContent().equals("!!")) {
			iv_content.setVisibility(View.GONE);
		} else {
			iv_content.setVisibility(View.VISIBLE);
		}
		return convertView;
	}

}
