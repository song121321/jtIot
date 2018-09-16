package song.jtslkj.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.jtslkj.R;

import song.jtslkj.app.MyApplication;
import song.jtslkj.util.InitExitUtil;

public class ISettingActivity extends BaseActivity implements
        OnClickListener {
    RelativeLayout rl_tongyong, rl_anquan, rl_news, rl_clear,
            rl_about;
    Button bt_exit;
    MaterialDialog.Builder mBuilder;
    MaterialDialog mMaterialDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_i_setting);
        MyApplication.getInstance().addActivity(this);
        findViewByid();
        // initview();
        setClicker();

    }

    private void findViewByid() {
        rl_tongyong = (RelativeLayout) findViewById(R.id.rl_wojia_setting_tongyong);
        rl_anquan = (RelativeLayout) findViewById(R.id.rl_wojia_setting_anquan);

        rl_news = (RelativeLayout) findViewById(R.id.rl_wojia_setting_news);
        rl_clear = (RelativeLayout) findViewById(R.id.rl_wojia_setting_clear);
        rl_about = (RelativeLayout) findViewById(R.id.rl_wojia_setting_about);
        bt_exit = (Button) findViewById(R.id.bt_wojia_setting_exit);
    }

    private void setClicker() {
        rl_tongyong.setOnClickListener(this);
        rl_anquan.setOnClickListener(this);
        rl_news.setOnClickListener(this);
        rl_clear.setOnClickListener(this);
        rl_about.setOnClickListener(this);
        bt_exit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, ISettingContentActivity.class);
        switch (v.getId()) {
            case R.id.rl_wojia_setting_tongyong:

                intent.putExtra("position", 0);
                startActivity(intent);
                break;
            case R.id.rl_wojia_setting_anquan:

                intent.putExtra("position", 1);
                startActivity(intent);
                break;

            case R.id.rl_wojia_setting_news:

                intent.putExtra("position", 3);
                startActivity(intent);
                break;
            case R.id.bt_wojia_setting_exit:
                mBuilder = new MaterialDialog.Builder(ISettingActivity.this);
                mBuilder.title(R.string.system_prompt);
                mBuilder.content(R.string.i_main_sure_to_exit);
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
                            new InitExitUtil(ISettingActivity.this)
                                    .ExitSetting();
                            MyApplication.getInstance().exit();
                        } else if (which == DialogAction.NEGATIVE) {
                            mMaterialDialog.dismiss();
                        }
                    }
                });

                break;
            default:
                break;

        }

    }
}
