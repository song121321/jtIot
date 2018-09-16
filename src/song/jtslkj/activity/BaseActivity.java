package song.jtslkj.activity;

import java.util.ArrayList;
import java.util.HashMap;

import song.jtslkj.app.MyApplication;
import song.jtslkj.config.MyConfig;
import song.jtslkj.util.AccountSharedPreferenceHelper;
import song.jtslkj.util.DialogUtil;
import song.jtslkj.util.ToolBox;
import song.jtslkj.util.WebServiceUtil;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.fingerth.supdialogutils.SYSDiaLogUtils;

public class BaseActivity extends Activity implements OnClickListener {
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
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
        SYSDiaLogUtils.showProgressDialog(this, SYSDiaLogUtils.SYSDiaLogType.IosType, "加載中...", true, new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                Toast.makeText(BaseActivity.this, "点击消失", Toast.LENGTH_SHORT).show();
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
