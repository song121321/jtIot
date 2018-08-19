package song.jtslkj.fragment;

import song.jtslkj.config.MyConfig;
import song.jtslkj.util.AccountSharedPreferenceHelper;
import song.jtslkj.util.LoginGesturePatternUtils;
import com.jtslkj.R;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.kyleduo.switchbutton.SwitchButton;

public class WojiaSettingSafetyFragment extends Fragment {
	private RelativeLayout rl_clearpass;
	private SwitchButton sb_closepass;
	private View v;
	AccountSharedPreferenceHelper asph;
	String clearpassworddone = "";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		clearpassworddone = getActivity().getString(
				R.string.wojia_setting_anquan_cleargesturelock_passworddone);
		v = inflater.inflate(R.layout.fragment_wojia_setting_safety, null);
		rl_clearpass = (RelativeLayout) v
				.findViewById(R.id.rl_wojia_setting_anquan);
		sb_closepass = (SwitchButton) v
				.findViewById(R.id.sb_wojia_setting_anquan);
		asph = new AccountSharedPreferenceHelper(getActivity());
		if (asph.readStringFromSharedpreference(
				MyConfig.sharedpreference_tablecol_closegesturelock).trim()
				.equals("")) {

		} else {
			sb_closepass.setChecked(true);

		}
		sb_closepass.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if (arg1) {
					asph.writeStringToSharedpreference(
							MyConfig.sharedpreference_tablecol_closegesturelock,
							"yes");
				} else {
					asph.writeStringToSharedpreference(
							MyConfig.sharedpreference_tablecol_closegesturelock,
							"");

				}
			}
		});

		rl_clearpass.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				AlertDialog.Builder builder = new Builder(getActivity());
				builder.setMessage(getActivity().getString(
						R.string.wojia_setting_anquan_suretoclearglpass));

				builder.setTitle(getActivity()
						.getString(R.string.system_prompt));

				builder.setPositiveButton(
						getActivity().getString(R.string.system_sure),
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();
								LoginGesturePatternUtils lgp = new LoginGesturePatternUtils(
										getActivity());
								lgp.clearLock();
								Toast.makeText(getActivity(),
										clearpassworddone, Toast.LENGTH_SHORT)
										.show();
							}
						});

				builder.setNegativeButton(
						getActivity().getString(R.string.system_cancel),
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();
							}
						});

				builder.create().show();
			}
		});
		return v;
	}

}
