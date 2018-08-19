package song.jtslkj.fragment;

import java.util.ArrayList;

import song.jtslkj.adapter.LogAdapter;
import song.jtslkj.bean.LogBean;
import com.jtslkj.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class WojiaZujiHssFragment extends Fragment {
	private ListView lv_log;
	private LogAdapter lab;
	private ArrayList<LogBean> loglist = new ArrayList<LogBean>();
	private View v;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		v = inflater.inflate(R.layout.fragment_wojia_zuji, null);
		System.out.println("------creat");
		findview();
		refresh();
		return v;
	}

	private void findview() {
		lv_log = (ListView) v.findViewById(R.id.xlv_wojia_log);
	}

	public static WojiaZujiAllFragment newInstance(ArrayList<LogBean> loglist) {
		WojiaZujiAllFragment f = new WojiaZujiAllFragment();
		Bundle bundle = new Bundle();
		bundle.putSerializable("loglist", loglist);
		f.setArguments(bundle);
		System.out.println("------instance");
		return f;
	}

	@SuppressWarnings("unchecked")
	private void refresh() {
		System.out.println("------refresh");
		Bundle bundle = getArguments();
		loglist = (ArrayList<LogBean>) bundle.getSerializable("loglist");
		if (loglist != null) {
			lab = new LogAdapter(getActivity(), loglist);
			lv_log.setAdapter(lab);
		}
	}
}
