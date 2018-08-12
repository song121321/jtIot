package song.honey.fragment;

import song.honey.bean.WorkBean;
import song.honey.util.ToolBox;
import song.shuang.honey.R;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class ChijiaWorkDetailFragment extends Fragment {
	private View v;
	WorkBean wb;
	ImageView iv_person;
	TextView tv_status, tv_price, tv_number, tv_changge, tv_address,
			tv_worktime, tv_paytime, tv_content, tv_money;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		v = inflater.inflate(R.layout.activity_chijia_work_detail, null);
		findview();
		initView();
		return v;
	}

	private void findview() {
		tv_money = (TextView) v
				.findViewById(R.id.tv_fragment_chijia_work_detail_money);

		tv_status = (TextView) v
				.findViewById(R.id.tv_fragment_chijia_work_detail_status);
		tv_price = (TextView) v
				.findViewById(R.id.tv_fragment_chijia_work_detail_singleprice);
		tv_number = (TextView) v
				.findViewById(R.id.tv_fragment_chijia_work_detail_number);
		tv_changge = (TextView) v
				.findViewById(R.id.tv_fragment_chijia_work_detail_changgeprice);
		tv_address = (TextView) v
				.findViewById(R.id.tv_fragment_chijia_work_detail_address);
		tv_worktime = (TextView) v
				.findViewById(R.id.tv_fragment_chijia_work_detail_worktime);
		tv_content = (TextView) v
				.findViewById(R.id.tv_fragment_chijia_work_detail_content);
		tv_paytime = (TextView) v
				.findViewById(R.id.tv_fragment_chijia_work_detail_paytime);
		iv_person = (ImageView) v
				.findViewById(R.id.civ_fragment_chijia_work_detail_person);
	}

	private void initView() {
		if (getArguments() != null) {
			String sFormat = "";
			String sFinal = "";
			wb = (WorkBean) getArguments().getSerializable("wb");
			if (wb.getStatus().equals(
					getActivity().getResources().getString(
							R.string.chijia_work_item_havenotsettle))) {
				tv_status.setText(wb.getStatus());
				tv_status.setTextColor(getActivity().getResources().getColor(
						R.color.tv_Red));
			}
			sFormat = getActivity().getResources().getString(
					R.string.chijia_work_item_singleprice);
			sFinal = String.format(sFormat, wb.getSingleprice());
			tv_price.setText(sFinal);
			sFormat = getActivity().getResources().getString(
					R.string.chijia_work_item_number);
			sFinal = String.format(sFormat, wb.getNumber());
			tv_number.setText(sFinal);
			sFormat = getActivity().getResources().getString(
					R.string.chijia_work_item_changge);
			sFinal = String.format(sFormat, wb.getChangge());
			tv_changge.setText(sFinal);

			tv_address.setText(wb.getAddress());
			tv_worktime.setText(ToolBox.formaDateStr(wb.getWorktime()));
			if (!wb.getPaytime().startsWith("1900")) {
				tv_paytime.setText(ToolBox.formaDateStr(wb.getPaytime()));
			}
			tv_content.setText(wb.getContent());

			if (wb.getPerson().equals(
					getActivity().getResources().getString(R.string.lisong))) {
				iv_person.setImageResource(R.drawable.avatar3);
			} else if (wb.getPerson().equals(
					getActivity().getResources().getString(
							R.string.houshuangshuang))) {
				iv_person.setImageResource(R.drawable.avatar1);
			}

			float money = Float.parseFloat(wb.getSingleprice().trim())
					* Float.parseFloat(wb.getNumber())
					+ Float.parseFloat(wb.getChangge());
			tv_money.setText(money+"");
		}
	}

	public static ChijiaWorkDetailFragment newInstance(WorkBean wb) {
		ChijiaWorkDetailFragment fragment = new ChijiaWorkDetailFragment();
		Bundle bundle = new Bundle();
		bundle.putSerializable("wb", wb);
		fragment.setArguments(bundle);
		return fragment;
	}

}
