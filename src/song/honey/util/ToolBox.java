package song.honey.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

@SuppressLint("SimpleDateFormat")
public class ToolBox {
	private Context context;

	public ToolBox(Context context) {
		super();
		this.context = context;
	}

	/**
	 * 判读是否连网
	 * 
	 * @return
	 */
	public boolean checkNetworkAvailable() {
		ConnectivityManager connectivity = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity == null) {
			return false;
		} else {
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null) {
				for (int i = 0; i < info.length; i++) {
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						NetworkInfo netWorkInfo = info[i];
						if (netWorkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
							return true;
						} else if (netWorkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	// 获取本地IP
	public static String getLocalIpAddress() {
		try {
			for (Enumeration<NetworkInterface> en = NetworkInterface
					.getNetworkInterfaces(); en.hasMoreElements();) {
				NetworkInterface intf = en.nextElement();
				for (Enumeration<InetAddress> enumIpAddr = intf
						.getInetAddresses(); enumIpAddr.hasMoreElements();) {
					InetAddress inetAddress = enumIpAddr.nextElement();
					if (!inetAddress.isLoopbackAddress()
							&& !inetAddress.isLinkLocalAddress()) {
						return inetAddress.getHostAddress().toString();
					}
				}
			}
		} catch (SocketException ex) {
			Log.e("WifiPreference IpAddress", ex.toString());
		}

		return null;
	}

	/**
	 * 将服务器器传来的数据作进一步处理，避免有空的数据的影响，如果出现空数据，用!!代替
	 * 
	 * @param text
	 *            712^家佳源^公共^旅游基金^77.3^2015/7/6 20:23:15^^
	 * @return 712^家佳源^公共^旅游基金^77.3^2015/7/6 20:23:15^!!^
	 */
	public static String avoidnull(String text) {
		String str = text.replace("^^", "^!!^");
		return str;
	}

	/**
	 * 判断字符串是否为数字
	 * 
	 * @param str
	 *            9000
	 * @return
	 */
	public static boolean isNumeric(String str) {
		return str.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
	}

	/**
	 * 对选项卡上的文字做转义，具体如下 如果传来的数据为 所有 转义为"" 类型 转义为 ""
	 * 
	 * @param text
	 *            所有
	 * @return ""
	 */

	public static String sectionzhuanyi(String text) {
		if (text.equals("所有") || text.equals("类型") || text.equals("全部")) {
			return "";
		} else {
			return text;
		}

	}

	public static String getcurrentmonth() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		String date = sdf.format(new java.util.Date());
		return date;
	}

	public static String getYear() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		String date = sdf.format(new java.util.Date());
		return date;
	}

	public static String getcurrentdate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String date = sdf.format(new java.util.Date());
		return date;

	}

	public static String getcurrentdatetime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = sdf.format(new java.util.Date());
		return date;
	}

	public static String getPhotoFileName() {
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat sdf = new SimpleDateFormat("'PNG'_yyyyMMdd_HHmmss");
		return sdf.format(date) + ".png";
	}

	/**
	 * 将时间的格式转化一下
	 * 
	 * @param time
	 *            输入时间字符串 2015/7/10 10:16:53
	 * @return 转化后的时间字符串 2015-07-10 10:16:53
	 */
	public static String timeformat(String time) {
		if (time.equals("")) {
			return time;
		} else {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			time = time.replace("/", "-");
			try {
				time = sdf.format(sdf.parse(time));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return time;
		}
	}

	/**
	 * 比较两个时间字符串的大小
	 * 
	 * @param DATE1
	 *            日期格式固定 1995-11-12 15:21
	 * @param DATE2
	 *            1997-11-12 15:21
	 * @return 如果date1>date2 返回1，否则 返回-1，如果两个相等 返回0
	 */
	public static int comparedate(String DATE1, String DATE2) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm");
		try {
			Date dt1 = df.parse(DATE1);
			Date dt2 = df.parse(DATE2);
			if (dt1.getTime() > dt2.getTime()) {
				System.out.println("dt1 在dt2前");
				return 1;
			} else if (dt1.getTime() < dt2.getTime()) {
				System.out.println("dt1在dt2后");
				return -1;
			} else {
				return -1;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return -1;
	}

	/**
	 * 产生一个月份字符串链表，为从给定的时间开始到这个月为止
	 * 
	 * @param starttimestr
	 *            标准格式 2014-10-01
	 * @return 返回一个月份字符串链表 单项为 2015年6月份
	 */
	public static ArrayList<String> generateChinesemonthlist(String starttimestr) {
		ArrayList<String> o = generatemonthlist(starttimestr);
		for (int i = 0; i < o.size(); i++) {
			o.set(i, formatChineseMonthStr(o.get(i)));
		}
		return o;
	}

	/**
	 * 产生一个月份字符串链表，为从给定的时间开始到这个月为止
	 * 
	 * @param starttimestr
	 *            标准格式 2014-10-01
	 * @return 返回一个月份字符串链表 单项为 2015-06
	 */
	public static ArrayList<String> generatemonthlist(String starttimestr) {
		ArrayList<String> monthlist = new ArrayList<String>();
		Calendar cal = Calendar.getInstance();
		Date date = cal.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date start = new Date();
		try {
			start = sdf.parse(starttimestr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		while (date.after(start)) {
			date = cal.getTime();
			sdf = new SimpleDateFormat("yyyy-MM");
			String time = sdf.format(date);
			monthlist.add(time);
			cal.add(Calendar.MONTH, -1);
		}
		return monthlist;
	}

	/**
	 * 格式化输出月份格式
	 * 
	 * @param inputtime
	 *            2015-09
	 * 
	 * @return 2015年9月份
	 */
	public static String formatChineseMonthStr(String inputtime) {
		String year = inputtime.substring(0, 4);
		String month = inputtime.substring(5, 7);
		inputtime = year + "年" + Integer.parseInt(month) + "月份";
		return inputtime;

	}

	/**
	 * 格式化输出月份,
	 * 
	 * @param inputtime
	 *            2015-01-01 12:23:12 2015/01/01 12:23:12
	 * @return 2015-01
	 */
	public static String formaMonthStr(String inputtime) {
		if (inputtime.contains("/")) {
			System.out.println("--");
			inputtime = timeformat(inputtime);
		}
		inputtime = inputtime.substring(0, 7);
		return inputtime;

	}

	/**
	 * 格式化输出日期
	 * 
	 * @param inputtime
	 *            2015-01-01 12:23:12 2015/01/01 12:23:12
	 * @return 2015-01-05
	 */
	public static String formaDateStr(String inputtime) {
		if (inputtime.contains("/")) {
			System.out.println("--");
			inputtime = timeformat(inputtime);
		}
		inputtime = inputtime.substring(0, 10);
		return inputtime;

	}
}
