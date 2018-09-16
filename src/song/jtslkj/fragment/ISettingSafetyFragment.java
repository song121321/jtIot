package song.jtslkj.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.jtslkj.R;
import com.kyleduo.switchbutton.SwitchButton;

import song.jtslkj.activity.ISettingContentActivity;
import song.jtslkj.config.MyConfig;
import song.jtslkj.util.AccountSharedPreferenceHelper;
import song.jtslkj.util.LoginGesturePatternUtils;

public class ISettingSafetyFragment extends Fragment {
    private RelativeLayout rl_clearpass;
    private SwitchButton sb_closepass;
    private View v;
    AccountSharedPreferenceHelper asph;
    String clearpassworddone = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        clearpassworddone = getActivity().getString(
                R.string.i_setting_safety_clear_gesture_lock_password_done);
        v = inflater.inflate(R.layout.fragment_i_setting_safety, null);
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

                final ISettingContentActivity iSettingContentActivity = (ISettingContentActivity) getActivity();
                iSettingContentActivity.mBuilder = new MaterialDialog.Builder(iSettingContentActivity);
                iSettingContentActivity.mBuilder.title(R.string.system_prompt);
                iSettingContentActivity.mBuilder.content(R.string.i_setting_safety_sure_to_clear_gesture_lock_passwd);
                iSettingContentActivity.mBuilder.positiveText(R.string.system_sure);
                iSettingContentActivity.mBuilder.titleGravity(GravityEnum.CENTER);
                iSettingContentActivity.mBuilder.buttonsGravity(GravityEnum.START);
                iSettingContentActivity.mBuilder.negativeText(R.string.system_cancel);
                iSettingContentActivity.mBuilder.theme(Theme.LIGHT);
                iSettingContentActivity.mBuilder.cancelable(false);
                iSettingContentActivity.mMaterialDialog = iSettingContentActivity.mBuilder.build();
                iSettingContentActivity.mMaterialDialog.show();
                iSettingContentActivity.mBuilder.onAny(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        if (which == DialogAction.POSITIVE) {
                            LoginGesturePatternUtils lgp = new LoginGesturePatternUtils(
                                    getActivity());
                            lgp.clearLock();
                            Toast.makeText(getActivity(),
                                    clearpassworddone, Toast.LENGTH_SHORT)
                                    .show();

                        } else if (which == DialogAction.NEGATIVE) {
                            iSettingContentActivity.mMaterialDialog.dismiss();
                        }
                    }
                });
            }
        });
        return v;
    }

}
