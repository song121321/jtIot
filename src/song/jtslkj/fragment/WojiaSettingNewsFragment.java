package song.jtslkj.fragment;

import song.jtslkj.config.MyConfig;
import song.jtslkj.util.AccountSharedPreferenceHelper;
import com.jtslkj.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.kyleduo.switchbutton.SwitchButton;

public class WojiaSettingNewsFragment extends Fragment {
	private SwitchButton sb_on, sb_add, sb_update, sb_delete, sb_login,
			sb_view;
	private View v;
	AccountSharedPreferenceHelper asph;

	private void diseblesw() {
		sb_add.setEnabled(false);
		sb_update.setEnabled(false);
		sb_delete.setEnabled(false);
		sb_login.setEnabled(false);
		sb_view.setEnabled(false);
	}

	private void enblesw() {
		sb_add.setEnabled(true);
		sb_update.setEnabled(true);
		sb_delete.setEnabled(true);
		sb_login.setEnabled(true);
		sb_view.setEnabled(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		v = inflater.inflate(R.layout.fragment_wojia_setting_news, null);
		sb_on = (SwitchButton) v
				.findViewById(R.id.sb_wojia_setting_news_switch);
		sb_add = (SwitchButton) v.findViewById(R.id.sb_wojia_setting_news_add);
		sb_update = (SwitchButton) v
				.findViewById(R.id.sb_wojia_setting_news_update);
		sb_delete = (SwitchButton) v
				.findViewById(R.id.sb_wojia_setting_news_delete);
		sb_login = (SwitchButton) v
				.findViewById(R.id.sb_wojia_setting_news_login);
		sb_view = (SwitchButton) v
				.findViewById(R.id.sb_wojia_setting_news_view);
		asph = new AccountSharedPreferenceHelper(getActivity());
		if (asph.readStringFromSharedpreference(
				MyConfig.sharedpreference_tablecol_newson).trim().equals("")) {
			sb_on.setChecked(false);
			diseblesw();
		} else {
			sb_on.setChecked(true);
			enblesw();
		}
		if (asph.readStringFromSharedpreference(
				MyConfig.sharedpreference_tablecol_typeadd).trim().equals("")) {
			sb_add.setChecked(false);
		} else {
			sb_add.setChecked(true);

		}
		if (asph.readStringFromSharedpreference(
				MyConfig.sharedpreference_tablecol_typeupdate).trim()
				.equals("")) {
			sb_update.setChecked(false);
		} else {
			sb_update.setChecked(true);

		}
		if (asph.readStringFromSharedpreference(
				MyConfig.sharedpreference_tablecol_typedelete).trim()
				.equals("")) {
			sb_delete.setChecked(false);
		} else {
			sb_delete.setChecked(true);

		}
		if (asph.readStringFromSharedpreference(
				MyConfig.sharedpreference_tablecol_typelogin).trim().equals("")) {
			sb_login.setChecked(false);
		} else {
			sb_login.setChecked(true);

		}
		if (asph.readStringFromSharedpreference(
				MyConfig.sharedpreference_tablecol_typeview).trim().equals("")) {
			sb_view.setChecked(false);
		} else {
			sb_view.setChecked(true);

		}
		sb_on.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if (arg1) {
					asph.writeStringToSharedpreference(
							MyConfig.sharedpreference_tablecol_newson, "yes");
					enblesw();
				} else {
					asph.writeStringToSharedpreference(
							MyConfig.sharedpreference_tablecol_newson, "");
					diseblesw();
				}
			}
		});
		sb_add.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if (arg1) {
					asph.writeStringToSharedpreference(
							MyConfig.sharedpreference_tablecol_typeadd, "yes");
				} else {
					asph.writeStringToSharedpreference(
							MyConfig.sharedpreference_tablecol_typeadd, "");

				}
			}
		});

		sb_update.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if (arg1) {
					asph.writeStringToSharedpreference(
							MyConfig.sharedpreference_tablecol_typeupdate,
							"yes");
				} else {
					asph.writeStringToSharedpreference(
							MyConfig.sharedpreference_tablecol_typeupdate, "");

				}
			}
		});
		sb_delete.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if (arg1) {
					asph.writeStringToSharedpreference(
							MyConfig.sharedpreference_tablecol_typedelete,
							"yes");
				} else {
					asph.writeStringToSharedpreference(
							MyConfig.sharedpreference_tablecol_typedelete, "");

				}
			}
		});
		sb_login.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if (arg1) {
					asph.writeStringToSharedpreference(
							MyConfig.sharedpreference_tablecol_typelogin, "yes");
				} else {
					asph.writeStringToSharedpreference(
							MyConfig.sharedpreference_tablecol_typelogin, "");

				}
			}
		});

		sb_view.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if (arg1) {
					asph.writeStringToSharedpreference(
							MyConfig.sharedpreference_tablecol_typeview, "yes");
				} else {
					asph.writeStringToSharedpreference(
							MyConfig.sharedpreference_tablecol_typeview, "");

				}
			}
		});
		return v;
	}
}
