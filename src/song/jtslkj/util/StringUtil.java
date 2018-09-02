package song.jtslkj.util;

import song.jtslkj.config.MyConfig;

public class StringUtil {
    /**
     * 将网页文本中的图片相对路径转化为绝对路径
     *
     * @param rUrl 20px;\"><img src=\"/UEditor/
     * @return 20px;\"><img src=\"http://wlw.jtslkj.cn:8021/UEditor/
     */
    public static String imgUrlFromRelativeFromAbsolute(String rUrl) {
        if (rUrl.contains("img src=\"")) {
            rUrl = rUrl.replaceAll("img src=\"", "img src=\"" + MyConfig.picServer);
        }
        return rUrl;
    }
}
