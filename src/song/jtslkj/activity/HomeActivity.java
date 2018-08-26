package song.jtslkj.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Spannable;
import android.text.style.ImageSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.TextView;

import com.jtslkj.R;

import java.util.ArrayList;

import song.jtslkj.tool.htmlspanner.HtmlSpanner;
import song.jtslkj.tool.htmlspanner.LinkMovementMethodExt;
import song.jtslkj.tool.htmlspanner.MyImageSpan;

public class HomeActivity extends Activity {

    TextView tv;
    HtmlSpanner htmlSpanner;
    ArrayList<String> imglist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        imglist = new ArrayList<>();
        htmlSpanner = new HtmlSpanner(this, dm.widthPixels, handler);
        tv = (TextView) findViewById(R.id.tv);
        final String html = "<p style=\"text-align: center;\">\n" +
                "    <span style=\"font-size: 36px; font-family: 楷体, 楷体_GB2312, SimKai;\">桓台县2017年农业水价综合改革设施配套项目区概况</span>\n" +
                "</p>\n" +
                "<p style=\"text-indent: 0px; line-height: 1.75em;\">\n" +
                "    <span style=\"color: rgb(51, 51, 51); font-family: arial, 宋体, sans-serif; text-indent: 28px; background-color: rgb(255, 255, 255); font-size: 20px;\">&nbsp; &nbsp; &nbsp; </span><span style=\"font-family: 黑体, SimHei; color: rgb(54, 96, 146);\"><span style=\"font-family: 黑体, SimHei; text-indent: 28px; background-color: rgb(255, 255, 255); font-size: 20px;\">&nbsp;</span><span style=\"font-family: 黑体, SimHei; font-size: 20px;\">桓台属淄博市辖县，位于鲁中山区和鲁北平原的结合地带，位于山东省中部偏北，淄博市北部，介于北纬36°51′50″-37°06′00″，东经117°50′00″-118°10′40″，北邻博兴、高青两县，东靠临淄区，南与张店区、周村区毗连，西与邹平县接壤。全县版图面积509平方公里。桓台县辖2个街道，7个镇。2017年，全县总人口50.15万人。</span></span>\n" +
                "</p>\n" +
                "<p>\n" +
                "    <span style=\"font-size: 20px; font-family: 黑体, SimHei; color: rgb(54, 96, 146);\">&nbsp; 2017年农业水价综合改革项目区，覆盖辖区包括邢家、唐山镇、新城镇、陈庄、索镇、侯庄、周家、果里镇、田庄镇、荆家镇，安装80眼机井水电双控远传计量配套设施。项目实施投入运行后,将有效提高桓台县农业节水灌溉工程现代化管理水平，发挥较好的社会经济效益。</span>\n" +
                "</p>\n" +
                "    <span style=\"color: rgb(51, 51, 51); font-family: arial, 宋体, sans-serif; text-indent: 28px; background-color: rgb(255, 255, 255); font-size: 20px;\"><img src=\"http://wlw.jtslkj.cn:8021/UEditor/net/upload/image/20180623/6366534790131929111012666.jpg\" title=\"1057155715_1151257_6.jpg\" alt=\"1057155715_1151257_6.jpg\" width=\"1124\" height=\"662\" style=\"width: 1124px; height: 662px;\"/></span>\n" +
                "<p style=\"text-align: center;\">\n" +
                "    <br/>\n" +
                "</p>\n" +
                "<p>\n" +
                "    <br/>\n" +
                "</p>\n" +
                "<p>\n" +
                "    <br/>\n" +
                "</p>\n" +
                "<p>\n" +
                "    <br/>\n" +
                "</p>\n" +
                "<p>\n" +
                "    <br/>\n" +
                "</p>\n" +
                "<p>\n" +
                "    <br/>\n" +
                "</p>\n" +
                "<p>\n" +
                "    <br/>\n" +
                "</p>\n" +
                "<p>\n" +
                "    <br/>\n" +
                "</p>\n" +
                "<p>\n" +
                "    <br/>\n" +
                "</p>\n" +
                "<p>\n" +
                "    <br/>\n" +
                "</p>\n" +
                "<p>\n" +
                "    <br/>\n" +
                "</p>\n" +
                "<p>\n" +
                "    <br/>\n" +
                "</p>\n" +
                "<p><span style=\"font-family: 宋体;color: rgb(0, 0, 0);letter-spacing: 0;font-size: 80px;background: rgb(255, 255, 255)\"><span style=\"font-family:宋体\">&nbsp; &nbsp; 阎楼镇位于阳谷县中部，距县城</span>9公里，辖53个行政村，辖区总面积67.1平方公里，人口5.1万人、耕地68000亩。2017年农业水价综合改革项目区，覆盖辖区内2.99万亩耕地，安装350眼机井水电双控远传计量配套设施，其中安装水表计量设施的23眼，安装以电折水计量设施的327眼。项目实施投入运行后,将有效提高阎楼镇农业节水灌溉工程现代化管理水平，发挥较好的社会经济效益。</span></p>" +
                "<p style=\"text-align: center;\">&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp;<img src=\"http://58.57.168.190:3002/UEditor/net/upload/image/20180622/6366527905930335113972383.jpg\" title=\"_副本111.jpg\" alt=\"_副本111.jpg\">&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp;<img src=\"http://58.57.168.190:3002/UEditor/net/upload/image/20180622/6366527907153781497134027.jpg\" title=\"_副本222.jpg\" alt=\"_副本222.jpg\"><br></p>";
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
}
