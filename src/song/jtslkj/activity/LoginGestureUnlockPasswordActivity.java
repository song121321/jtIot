package song.jtslkj.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jtslkj.R;

import java.util.List;

import song.jtslkj.app.MyApplication;
import song.jtslkj.config.MyConfig;
import song.jtslkj.ui.LoginGesturePatternView;
import song.jtslkj.ui.LoginGesturePatternView.Cell;
import song.jtslkj.util.AccountSharedPreferenceHelper;
import song.jtslkj.util.LoginGesturePatternUtils;
import song.jtslkj.util.ToolBox;

public class LoginGestureUnlockPasswordActivity extends BaseActivity {
    private LoginGesturePatternView mLockPatternView;
    private int mFailedPatternAttemptsSinceLastTimeout = 0;
    private CountDownTimer mCountdownTimer = null;
    private Handler mHandler = new Handler();
    private TextView mHeadTextView;
    private Animation mShakeAnim;
    LoginGesturePatternUtils lgp;
    private TextView tv_forgetpass;
    private ImageView iv_face;
    private AccountSharedPreferenceHelper asp = new AccountSharedPreferenceHelper(this);

    private void showToast(CharSequence message) {
        showLongToast(message.toString());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        lgp = new LoginGesturePatternUtils(
                LoginGestureUnlockPasswordActivity.this);
        setContentView(R.layout.logingesture_password_unlock);
        MyApplication.getInstance().addActivity(this);
        tv_forgetpass = (TextView) findViewById(R.id.gesturepwd_unlock_forget);
        mLockPatternView = (LoginGesturePatternView) this
                .findViewById(R.id.gesturepwd_unlock_lockview);
        mLockPatternView.setOnPatternListener(mChooseNewLockPatternListener);
        mLockPatternView.setTactileFeedbackEnabled(true);
        mHeadTextView = (TextView) findViewById(R.id.gesturepwd_unlock_text);
        mShakeAnim = AnimationUtils.loadAnimation(this, R.anim.shake_x);
        final String forgetpassstring = getString(R.string.gesture_password_forgethint);
        tv_forgetpass.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                showToast(forgetpassstring);
                startActivity(new Intent(
                        LoginGestureUnlockPasswordActivity.this,
                        LoginActivity.class));
                finish();
            }
        });
        iv_face = (ImageView) findViewById(R.id.gesturepwd_unlock_face);
        iv_face.setOnLongClickListener(new OnLongClickListener() {

            @Override
            public boolean onLongClick(View arg0) {

                final EditText inputServer = new EditText(LoginGestureUnlockPasswordActivity.this);
                inputServer.setFocusable(true);

                AlertDialog.Builder builder = new AlertDialog.Builder(LoginGestureUnlockPasswordActivity.this);
                builder.setTitle("input server").setView(inputServer).setNegativeButton(
                        "取消", null);

                inputServer.setText(MyConfig.ServerAddress);
                builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        // TODO Auto-generated method stub
                        //	MyConfig.ServerAddress = inputServer.getText().toString();
                        asp.writeStringToSharedpreference("ServerAddress", inputServer.getText().toString());
                        showToast("重啟生效");
                    }


                });
                builder.show();

                return false;


            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        boolean test = !lgp.savedPatternExists();
        if (test) {
            startActivity(new Intent(this,
                    LoginGestureGuidePasswordActivity.class));
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mCountdownTimer != null)
            mCountdownTimer.cancel();
    }

    private Runnable mClearPatternRunnable = new Runnable() {
        public void run() {
            mLockPatternView.clearPattern();
        }
    };

    protected LoginGesturePatternView.OnPatternListener mChooseNewLockPatternListener = new LoginGesturePatternView.OnPatternListener() {

        public void onPatternStart() {
            mLockPatternView.removeCallbacks(mClearPatternRunnable);
            patternInProgress();
        }

        public void onPatternCleared() {
            mLockPatternView.removeCallbacks(mClearPatternRunnable);
        }

        public void onPatternDetected(List<LoginGesturePatternView.Cell> pattern) {
            if (pattern == null)
                return;

            if (lgp.checkPattern(pattern)) {
                mLockPatternView
                        .setDisplayMode(LoginGesturePatternView.DisplayMode.Correct);
                ToolBox tb = new ToolBox(
                        LoginGestureUnlockPasswordActivity.this);
                if (tb.checkNetworkAvailable()) {
                    Intent intent = new Intent(
                            LoginGestureUnlockPasswordActivity.this,
                            MainActivity.class);
                    startActivity(intent);
                } else {
//					Intent intent = new Intent(
//							LoginGestureUnlockPasswordActivity.this,
//							BaijiaConsumeSearchActivity.class);
//					startActivity(intent);
//					showShortToast("无网，只能使用本地搜索");
                }
                showToast(getString(R.string.gesture_password_unlock_unlocksucess));
                finish();
            } else {
                mLockPatternView
                        .setDisplayMode(LoginGesturePatternView.DisplayMode.Wrong);
                if (pattern.size() >= LoginGesturePatternUtils.MIN_PATTERN_REGISTER_FAIL) {
                    mFailedPatternAttemptsSinceLastTimeout++;
                    int retry = LoginGesturePatternUtils.FAILED_ATTEMPTS_BEFORE_TIMEOUT
                            - mFailedPatternAttemptsSinceLastTimeout;
                    if (retry >= 0) {
                        if (retry == 0)
                            showToast(getString(R.string.gesture_password_unlock_5wrong30seconds));
                        String wrongpass = getString(R.string.gesture_password_unlock_wrongnci);
                        wrongpass = String.format(wrongpass, retry);
                        mHeadTextView.setText(wrongpass);
                        mHeadTextView.setTextColor(Color.RED);
                        mHeadTextView.startAnimation(mShakeAnim);
                    }

                } else {
                    showToast(getString(R.string.lockpattern_recording_incorrect_too_short));
                }

                if (mFailedPatternAttemptsSinceLastTimeout >= LoginGesturePatternUtils.FAILED_ATTEMPTS_BEFORE_TIMEOUT) {
                    mHandler.postDelayed(attemptLockout, 2000);
                } else {
                    mLockPatternView.postDelayed(mClearPatternRunnable, 2000);
                }
            }
        }

        public void onPatternCellAdded(List<Cell> pattern) {

        }

        private void patternInProgress() {
        }
    };
    Runnable attemptLockout = new Runnable() {

        @Override
        public void run() {
            mLockPatternView.clearPattern();
            mLockPatternView.setEnabled(false);
            mCountdownTimer = new CountDownTimer(
                    LoginGesturePatternUtils.FAILED_ATTEMPT_TIMEOUT_MS + 1,
                    1000) {

                @Override
                public void onTick(long millisUntilFinished) {
                    int secondsRemaining = (int) (millisUntilFinished / 1000) - 1;
                    if (secondsRemaining > 0) {
                        String nmiao = getString(R.string.gesture_password_unlock_nmiaochonhgshi);
                        nmiao = String.format(nmiao, secondsRemaining);
                        mHeadTextView.setText(nmiao);
                    } else {
                        mHeadTextView
                                .setText(getString(R.string.gesture_password_unlock_pleaseunlock));
                        mHeadTextView.setTextColor(Color.WHITE);
                    }

                }

                @Override
                public void onFinish() {
                    mLockPatternView.setEnabled(true);
                    mFailedPatternAttemptsSinceLastTimeout = 0;
                }
            }.start();
        }
    };

}
