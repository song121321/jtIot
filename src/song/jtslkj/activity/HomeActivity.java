package song.jtslkj.activity;


import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Spannable;
import android.text.style.ImageSpan;
import android.util.DisplayMetrics;
import android.widget.TextView;

import com.jtslkj.R;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.HashMap;

import song.jtslkj.config.MyConfig;
import song.jtslkj.tool.htmlspanner.HtmlSpanner;
import song.jtslkj.tool.htmlspanner.LinkMovementMethodExt;
import song.jtslkj.util.StringUtil;
import song.jtslkj.util.WebServiceUtil;

public class HomeActivity extends BaseActivity {

    private RefreshLayout mRefreshLayout;
    TextView tv;
    HtmlSpanner htmlSpanner;
    ArrayList<String> imglist;
    String html = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mRefreshLayout = (RefreshLayout) findViewById(R.id.refreshLayout);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        imglist = new ArrayList<>();
        htmlSpanner = new HtmlSpanner(this, dm.widthPixels, handler);
        showLoadingDialog();
        new GetIntroductionTask().execute();
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                new GetIntroductionTask().execute();
                refreshlayout.finishRefresh();
            }
        });
    }


    final Handler handler = new Handler() {
        public void handleMessage(Message msg) {
//            switch (msg.what) {
//                case 1://获取图片路径列表
//                    String url = (String) msg.obj;
//                    Log.e("jj", "url>>" + url);
//                    imglist.add(url);
//                    break;
//                case 2://图片点击事件
//                    int position=0;
//                    MyImageSpan span = (MyImageSpan) msg.obj;
//                    for (int i = 0; i < imglist.size(); i++) {
//                        if (span.getUrl().equals(imglist.get(i))) {
//                            position = i;
//                            break;
//                        }
//                    }
//                    Log.e("jj","position>>"+position);
//                    Intent intent=new Intent(HomeActivity.this,ImgPreviewActivity.class);
//                    Bundle b=new Bundle();
//                    b.putInt("position",position);
//                    b.putStringArrayList("imglist",imglist);
//                    intent.putExtra("b",b);
//                    startActivity(intent);
//                    break;
//            }
        }

        ;
    };

    private class GetIntroductionTask extends
            AsyncTask<Void, Void, String> {
        public GetIntroductionTask() {
            super();
        }

        @Override
        protected String doInBackground(Void... arg0) {
            HashMap<String, String> params = new HashMap<String, String>();
            String nameSpace = MyConfig.nameSpace;
            String methodName = MyConfig.methodName_GetIntroduction;
            String endPoint = MyConfig.endPoint;
            return WebServiceUtil.getAnyType(nameSpace, methodName,
                    endPoint, params);
        }

        @Override
        protected void onPostExecute(String result) {
            // 如果db为真，表示只是更新数据库，否则，更新UI
            super.onPostExecute(result);
            html = StringUtil.imgUrlFromRelativeFromAbsolute(result);
            tv = (TextView) findViewById(R.id.tv);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    final Spannable spannable = htmlSpanner.fromHtml(html);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tv.setText(spannable);
                            tv.setMovementMethod(LinkMovementMethodExt.getInstance(handler, ImageSpan.class));
                        }
                    });
                }
            }).start();
            closeLoadingDialog();
        }
    }
}
