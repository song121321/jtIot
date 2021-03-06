package song.jtslkj.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.jtslkj.R;
import com.kyleduo.switchbutton.SwitchButton;

import song.jtslkj.util.AccountSharedPreferenceHelper;

public class ISettingUniverseFragment extends Fragment {
	private SwitchButton sb_savebudget;
	private View v;
	AccountSharedPreferenceHelper asph;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		v = inflater.inflate(R.layout.fragment_i_setting_universe, null);
		sb_savebudget = (SwitchButton) v
				.findViewById(R.id.sb_wojia_setting_universe_savabudget);
		asph = new AccountSharedPreferenceHelper(getActivity());
		if (asph.readStringFromSharedpreference(
				"MyConfig.sharedpreference_tablecol_savabudget").trim()
				.equals("")) {

		} else {
			sb_savebudget.setChecked(true);

		}
		sb_savebudget.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if (arg1) {

				} else {
					asph.writeStringToSharedpreference(
							"MyConfig.sharedpreference_tablecol_savabudget", "");
				}
			}
		});
		return v;
	}

}
