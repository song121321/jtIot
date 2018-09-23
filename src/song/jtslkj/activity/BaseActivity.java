package song.jtslkj.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.fingerth.supdialogutils.SYSDiaLogUtils;
import com.jtslkj.R;

import song.jtslkj.app.MyApplication;

public class BaseActivity extends Activity implements OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.getInstance().addActivity(this);
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        MyApplication.getInstance().removeActivity(this);
    }

    public void showLongToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    }

    public void showShortToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    public void showLoadingDialog() {
        SYSDiaLogUtils.showProgressDialog(this, SYSDiaLogUtils.SYSDiaLogType.IosType, getString(R.string.xlistview_header_hint_loading), true, new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {

            }
        });
    }


    public void closeLoadingDialog() {
        SYSDiaLogUtils.dismissProgress();
    }

    @Override
    public void onClick(View v) {

    }


}
