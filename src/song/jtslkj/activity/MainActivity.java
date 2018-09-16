package song.jtslkj.activity;

import android.app.TabActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.KeyEvent;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.jtslkj.R;

import song.jtslkj.app.MyApplication;
import song.jtslkj.util.AccountSharedPreferenceHelper;

@SuppressWarnings("deprecation")
public class MainActivity extends TabActivity {
    private TabHost mTabHost;
    private RadioGroup mTabButtonGroup;
    public static final String TAB_HOME = "home";
    public static final String TAB_GIS = "gis";
    public static final String TAB_DEVICE = "device";
    public static final String TAB_PERSON = "person";
    private RadioButton rButtonHome, rButtonGis, rButtonFacility, rButtonI;
    AccountSharedPreferenceHelper asph;
    MaterialDialog.Builder mBuilder;
    MaterialDialog mMaterialDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById();
        initView();
        MyApplication.getInstance().addActivity(this);


    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();

    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();

    }

    private void findViewById() {
        mTabButtonGroup = (RadioGroup) findViewById(R.id.home_radio_button_group);
        rButtonHome = (RadioButton) findViewById(R.id.home_tab_home);
        rButtonGis = (RadioButton) findViewById(R.id.home_tab_gis);
        rButtonFacility = (RadioButton) findViewById(R.id.home_tab_facility);
        rButtonI = (RadioButton) findViewById(R.id.home_tab_i);
    }

    private void initView() {

        mTabHost = getTabHost();

        Intent iHome = new Intent(this, HomeActivity.class);
        Intent iGis = new Intent(this, GisActivity.class);
        Intent iDevice = new Intent(this, DeviceActivity.class);
        Intent i_wojia = new Intent(this, IActivity.class);

        mTabHost.addTab(mTabHost.newTabSpec(TAB_HOME)
                .setIndicator(TAB_HOME).setContent(iHome));
        mTabHost.addTab(mTabHost.newTabSpec(TAB_GIS)
                .setIndicator(TAB_GIS).setContent(iGis));
        mTabHost.addTab(mTabHost.newTabSpec(TAB_DEVICE).setIndicator(TAB_DEVICE)
                .setContent(iDevice));
        mTabHost.addTab(mTabHost.newTabSpec(TAB_PERSON).setIndicator(TAB_PERSON)
                .setContent(i_wojia));
        mTabButtonGroup
                .setOnCheckedChangeListener(new OnCheckedChangeListener() {
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        switch (checkedId) {
                            case R.id.home_tab_home:
                                mTabHost.setCurrentTabByTag(TAB_HOME);
                                changeTextColor(1);
                                break;
                            case R.id.home_tab_gis:
                                mTabHost.setCurrentTabByTag(TAB_GIS);
                                changeTextColor(2);
                                break;
                            case R.id.home_tab_facility:
                                mTabHost.setCurrentTabByTag(TAB_DEVICE);
                                changeTextColor(3);
                                break;

                            case R.id.home_tab_i:
                                mTabHost.setCurrentTabByTag(TAB_PERSON);
                                changeTextColor(4);
                                break;
                            default:
                                break;
                        }
                    }
                });
    }

    private void changeTextColor(int index) {
        switch (index) {
            case 1:
                rButtonHome.setTextColor(getResources().getColor(
                        R.color.actionbar_bg));
                rButtonGis.setTextColor(getResources().getColor(
                        R.color.main_textcolor_normal));
                rButtonFacility.setTextColor(getResources().getColor(
                        R.color.main_textcolor_normal));
                rButtonI.setTextColor(getResources().getColor(
                        R.color.main_textcolor_normal));
                break;
            case 2:
                rButtonHome.setTextColor(getResources().getColor(
                        R.color.main_textcolor_normal));
                rButtonGis.setTextColor(getResources().getColor(
                        R.color.actionbar_bg));
                rButtonFacility.setTextColor(getResources().getColor(
                        R.color.main_textcolor_normal));
                rButtonI.setTextColor(getResources().getColor(
                        R.color.main_textcolor_normal));
                break;
            case 3:
                rButtonHome.setTextColor(getResources().getColor(
                        R.color.main_textcolor_normal));
                rButtonGis.setTextColor(getResources().getColor(
                        R.color.main_textcolor_normal));
                rButtonFacility.setTextColor(getResources().getColor(
                        R.color.actionbar_bg));
                rButtonI.setTextColor(getResources().getColor(
                        R.color.main_textcolor_normal));
                break;

            case 4:
                rButtonHome.setTextColor(getResources().getColor(
                        R.color.main_textcolor_normal));
                rButtonGis.setTextColor(getResources().getColor(
                        R.color.main_textcolor_normal));
                rButtonFacility.setTextColor(getResources().getColor(
                        R.color.main_textcolor_normal));
                rButtonI.setTextColor(getResources().getColor(
                        R.color.actionbar_bg));
                break;

            default:
                break;
        }
    }


    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN
                && event.getRepeatCount() == 0) {
            mBuilder = new MaterialDialog.Builder(MainActivity.this);
            mBuilder.title(R.string.system_prompt);
            mBuilder.content(R.string.system_sureifexit);
            mBuilder.positiveText(R.string.system_sure);
            mBuilder.titleGravity(GravityEnum.CENTER);
            mBuilder.buttonsGravity(GravityEnum.START);
            mBuilder.negativeText(R.string.system_cancel);
            mBuilder.theme(Theme.LIGHT);
            mBuilder.cancelable(false);
            mMaterialDialog = mBuilder.build();
            mMaterialDialog.show();
            mBuilder.onAny(new MaterialDialog.SingleButtonCallback() {
                @Override
                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                    if (which == DialogAction.POSITIVE) {
                        MyApplication.getInstance().exit();
                    } else if (which == DialogAction.NEGATIVE) {
                        mMaterialDialog.dismiss();
                    }
                }
            });
        }

        return super.dispatchKeyEvent(event);
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        MyApplication.getInstance().removeActivity(this);
        //unregisterReceiver(TabReceiver);
    }

    BroadcastReceiver TabReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            // MyApplication.getInstance().type=2;

        }
    };
}
