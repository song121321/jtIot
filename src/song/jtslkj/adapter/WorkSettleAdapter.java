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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

public class WorkSettleAdapter extends BaseAdapter {
	private onCheckedChanged listener;
	private Context context;
	private ArrayList<WorkBean> worklist = new ArrayList<WorkBean>();
	ArrayList<Boolean> checklist = new ArrayList<Boolean>();
	private float checkmoney = 0f;
	private int checknum = 0;
	private ArrayList<WorkBean> checkbeanlist = new java.util.ArrayList<WorkBean>();

	public WorkSettleAdapter(Context context, ArrayList<WorkBean> worklist) {
		super();
		this.context = context;
		this.worklist = worklist;
		for (int i = 0; i < worklist.size(); i++) {
			checklist.add(false);
		}
	}

	public float getCheckmoney() {
		return checkmoney;
	}

	public int getChecknum() {
		return checknum;
	}

	public String getCheckidser() {
		String idser = "";
		for (WorkBean wb : checkbeanlist) {
			idser = idser + wb.getId() + ",";
		}
		if (idser.endsWith(",")) {
			idser = idser.substring(0, idser.lastIndexOf(","));
		}
		return idser;
	}

	public ArrayList<WorkBean> getCheckbeanlist() {
		return checkbeanlist;
	}

	public void setchecklistfalse() {
		for (int i = 0; i < checklist.size(); i++) {
			checklist.set(i, false);
		}
	}

	public void setchecklisttrue() {
		for (int i = 0; i < checklist.size(); i++) {
			checklist.set(i, true);
		}
	}

	public void setchecklist(int position, boolean value) {
		checklist.set(position, value);
		refreshbasedata();
	}

	public ArrayList<WorkBean> getdata() {
		return worklist;
	}

	@Override
	public void notifyDataSetChanged() {
		super.notifyDataSetChanged();
		refreshbasedata();
	}

	@Override
	public int getCount() {
		return worklist.size();
	}

	@Override
	public Object getItem(int arg0) {
		return worklist.get(arg0);
	}

	private void refreshbasedata() {
		checkmoney = 0f;
		checknum = 0;
		checkbeanlist.clear();
		for (int i = 0; i < checklist.size(); i++) {
			if (checklist.get(i)) {
				checknum++;
				checkmoney = checkmoney
						+ Float.parseFloat(worklist.get(i).getZhongjia());
				// checkidserize = checkidserize + worklist.get(i).getId() +
				// ",";
				checkbeanlist.add(worklist.get(i));
			}
		}

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
		final int p = position;
		convertView = LayoutInflater.from(context).inflate(
				R.layout.item_chijia_work_settle, null);
		TextView name = (TextView) convertView
				.findViewById(R.id.tv_item_worksettle_name);
		TextView person = (TextView) convertView
				.findViewById(R.id.tv_item_worksettle_person);
		TextView worktime = (TextView) convertView
				.findViewById(R.id.tv_item_worksettle_time);
		TextView zhongjia = (TextView) convertView
				.findViewById(R.id.tv_item_worksettle_zhongjia);
		CheckBox check = (CheckBox) convertView
				.findViewById(R.id.cb_item_worksettle);
		String sFormat = "";
		String sFinal = "";

		WorkBean workbean = worklist.get(position);
		name.setText(workbean.getName());
		sFormat = context.getResources().getString(
				R.string.chijia_work_item_zhongjia);
		sFinal = String.format(sFormat, workbean.getZhongjia());
		zhongjia.setText(sFinal);
		person.setText(workbean.getPerson());
		worktime.setText(ToolBox.formaDateStr(workbean.getWorktime()));
		check.setChecked(checklist.get(p));
		check.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				listener.NoticeChangge(p, arg1);
			}
		});

		return convertView;
	}

	public interface onCheckedChanged {

		public void NoticeChangge(int position, boolean check);
	}

	public void setOnCheckedChanged(onCheckedChanged listener) {
		this.listener = listener;
	}
}
