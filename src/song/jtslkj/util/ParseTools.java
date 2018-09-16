package song.jtslkj.util;

import com.tianditu.android.maps.GeoPoint;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import song.jtslkj.bean.BudgetBean;
import song.jtslkj.bean.ConsumeBean;
import song.jtslkj.bean.IncomeBean;
import song.jtslkj.bean.IraLogBean;
import song.jtslkj.bean.LogBean;
import song.jtslkj.bean.StaBean;
import song.jtslkj.bean.StaDayNetBean;
import song.jtslkj.bean.WellAttrBean;
import song.jtslkj.bean.WellBean;
import song.jtslkj.bean.WorkBean;
import song.jtslkj.bean.WorkItemBean;
import song.jtslkj.config.MyConfig;

public class ParseTools {


	public static List<WellBean> json2WellBeanList(String json){
		List<WellBean> wellBeans = new ArrayList<>();
		JSONObject originalJson = null;
		try {
			originalJson = new JSONObject(json);
			JSONArray wellsJsonArray = originalJson.getJSONArray("data");
			for (int i = 0; i < wellsJsonArray.length(); i++) {
				WellBean wellBean = new WellBean();
				JSONObject wellJson = wellsJsonArray.getJSONObject(i);
				wellBean.setWellCode(wellJson.getString("wellCode"));
				wellBean.setWellName(wellJson.getString("wellName"));
				wellBean.setLocation(wellJson.getString("location"));
				wellBean.setPointLan(wellJson.getInt("pointLan"));
				wellBean.setPointLon(wellJson.getInt("pointLon"));
				wellBean.setState(wellJson.getInt("state"));
				wellBean.setUpdateTime(wellJson.getString("updateTime"));
				List<WellAttrBean> wellAttrBeans = new ArrayList<>();
				JSONArray wellAttrsArray = wellJson.getJSONArray("attr");
				for (int j = 0; j < wellAttrsArray.length(); j++) {
					WellAttrBean wellAttrBean  = new WellAttrBean();
					JSONObject wellAttrJson = wellAttrsArray.getJSONObject(j);
					wellAttrBean.setCode(wellAttrJson.getString("code"));
					wellAttrBean.setName(wellAttrJson.getString("name"));
					wellAttrBean.setValue(wellAttrJson.getString("value"));
					wellAttrBeans.add(wellAttrBean);
				}
				wellBean.setAttr(wellAttrBeans);
				wellBeans.add(wellBean);
			}
			return  wellBeans;

		} catch (JSONException e) {
			e.printStackTrace();
		}
       return null;

	}


	public static List<StaDayNetBean> json2StaDayNetBeanList(String json){
		List<StaDayNetBean> staDayNetBeans = new ArrayList<>();
		JSONObject originalJson = null;
		try {
			originalJson = new JSONObject(json);
			JSONArray staDayNetBeansJsonArray = originalJson.getJSONArray("data");
			for (int i = 0; i < staDayNetBeansJsonArray.length(); i++) {
				StaDayNetBean staDayNetBean = new StaDayNetBean();
				JSONObject staDayNetJson = staDayNetBeansJsonArray.getJSONObject(i);
				staDayNetBean.setWellName(staDayNetJson.getString("name"));
				staDayNetBean.setHn0(staDayNetJson.getDouble("hn0"));
				staDayNetBean.setHn1(staDayNetJson.getDouble("hn1"));
				staDayNetBean.setHn2(staDayNetJson.getDouble("hn2"));
				staDayNetBean.setHn3(staDayNetJson.getDouble("hn3"));
				staDayNetBean.setHn4(staDayNetJson.getDouble("hn4"));
				staDayNetBean.setHn5(staDayNetJson.getDouble("hn5"));
				staDayNetBean.setHn6(staDayNetJson.getDouble("hn6"));
				staDayNetBean.setHn7(staDayNetJson.getDouble("hn7"));
				staDayNetBean.setHn8(staDayNetJson.getDouble("hn8"));
				staDayNetBean.setHn9(staDayNetJson.getDouble("hn9"));
				staDayNetBean.setHn10(staDayNetJson.getDouble("hn10"));
				staDayNetBean.setHn11(staDayNetJson.getDouble("hn11"));
				staDayNetBean.setHn12(staDayNetJson.getDouble("hn12"));
				staDayNetBean.setHn13(staDayNetJson.getDouble("hn13"));
				staDayNetBean.setHn14(staDayNetJson.getDouble("hn14"));
				staDayNetBean.setHn15(staDayNetJson.getDouble("hn15"));
				staDayNetBean.setHn16(staDayNetJson.getDouble("hn16"));
				staDayNetBean.setHn17(staDayNetJson.getDouble("hn17"));
				staDayNetBean.setHn18(staDayNetJson.getDouble("hn18"));
				staDayNetBean.setHn19(staDayNetJson.getDouble("hn19"));
				staDayNetBean.setHn20(staDayNetJson.getDouble("hn20"));
				staDayNetBean.setHn21(staDayNetJson.getDouble("hn21"));
				staDayNetBean.setHn22(staDayNetJson.getDouble("hn22"));
				staDayNetBean.setHn23(staDayNetJson.getDouble("hn23"));
				staDayNetBeans.add(staDayNetBean);
			}
			return  staDayNetBeans;

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;

	}


    public static List<StaBean> json2StaBeanList(String json){
        List<StaBean> staBeans = new ArrayList<>();
        JSONObject originalJson = null;
        try {
            originalJson = new JSONObject(json);
            JSONArray staBeansJsonArray = originalJson.getJSONArray("data");
            for (int i = 0; i < staBeansJsonArray.length(); i++) {
                StaBean staBean = new StaBean();
                JSONObject staJson = staBeansJsonArray.getJSONObject(i);
                staBean.setCode(staJson.getString("code"));
                staBean.setName(staJson.getString("name"));
                staBean.setDescription(staJson.getString("description"));
                staBeans.add(staBean);
            }
            return  staBeans;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;

    }

	public static List<IraLogBean> json2IraLogBeanList(String json){
		List<IraLogBean> iraLogBeans = new ArrayList<>();
		JSONObject originalJson = null;
		try {
			originalJson = new JSONObject(json);
			JSONArray staBeansJsonArray = originalJson.getJSONArray("data");
			for (int i = 0; i < staBeansJsonArray.length(); i++) {
				IraLogBean iraLogBean = new IraLogBean();
				JSONObject staJson = staBeansJsonArray.getJSONObject(i);
				iraLogBean.setPosition(staJson.getString("position"));
				iraLogBean.setUpdateTime(staJson.getString("updateTime"));
				iraLogBean.setLastStartTime(staJson.getString("lastStartTime"));
				iraLogBean.setLastEndTime(staJson.getString("lastEndTime"));
				iraLogBean.setLastCardNumber(staJson.getString("lastCardNumber"));
				iraLogBean.setLastElec(staJson.getDouble("lastElec"));
				iraLogBean.setLastWater(staJson.getDouble("lastWater"));
				iraLogBean.setLastStartMoney(staJson.getDouble("lastStartMoney"));
				iraLogBean.setLastEndMoney(staJson.getDouble("lastEndMoney"));
				iraLogBean.setLastConsumeMoney(staJson.getDouble("lastConsumeMoney"));
				iraLogBeans.add(iraLogBean);
			}
			return  iraLogBeans;

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;

	}



	/**
	 * 将服务器获取的数据转化为一个预算豆子，如果预算花费为0，就是0
	 * 
	 * @param value
	 *            132^签了工作很开心^666^379.9^2015/10/17 21:31:14^工作签了，这是大事儿^
	 * @return
	 */
	public static BudgetBean string2budgetbean(String value) {
		value = ToolBox.avoidnull(value);
		String[] arr = value.split("\\^");
		if (arr[3].trim().equals("!!")) {
			arr[3] = "0";
		}
		if (arr[5].trim().equals("!!")) {
			arr[5] = "暂无";
		}
		BudgetBean cb = new BudgetBean(arr[0], arr[1], arr[3], arr[5], arr[2],
				arr[4]);
		return cb;
	}

	public static ArrayList<BudgetBean> strlist2budgetlist(
			ArrayList<String> strlist) {
		ArrayList<BudgetBean> budgetlist = new ArrayList<BudgetBean>();
		for (String str : strlist) {
			budgetlist.add(string2budgetbean(str));
		}
		return budgetlist;
	}

	/**
	 * 将从服务器直接获取得到的一行数据转化为consumebean的形式
	 * 
	 * @param
	 *
	 * @return 返回一个豆子
	 */
	public static ConsumeBean string2consumebean(String value) {
		value = ToolBox.avoidnull(value);
		String[] arr = value.split("\\^");
		String originalhtml = arr[6];
		if (!arr[6].equals("!!")) {
			arr[6] = HtmlRegexUtil.filterHtml(arr[6].trim());
		}
		ConsumeBean cb = new ConsumeBean(arr[0], arr[1], arr[3], arr[2],
				arr[4], arr[5], arr[6]);
		String imgurl = HtmlRegexUtil.filterImgSrc(originalhtml);
		if (imgurl != null && imgurl != "") {
			cb.setImgurl(MyConfig.ServerAddress + imgurl.trim());
		}
		return cb;
	}

	public static ArrayList<ConsumeBean> strlist2conbeanlist(
			ArrayList<String> strlist) {
		ArrayList<ConsumeBean> conbeanlist = new ArrayList<ConsumeBean>();

		if (strlist == null & strlist.size() == 0) {
			conbeanlist = null;
		} else {
			for (String str : strlist) {
				conbeanlist.add(string2consumebean(str));
			}
		}
		return conbeanlist;
	}

	/**
	 * 将服务器字串串转化为一个工作豆子
	 * 
	 * @param value
	 *            5^仇茜家教^87.5^1^李松^10^香江路香江花园2栋1单元301^2015/3/12
	 *            0:00:00^2015/3/16 0:00:00^已结算^晚上自己打车回来的，应该给报销车费^97.5^1^
	 * @return
	 */
	public static WorkBean string2workbean(String value) {
		value = ToolBox.avoidnull(value);
		String[] arr = value.split("\\^");
		WorkBean wb = new WorkBean(arr[0], arr[1], arr[2], arr[3], arr[4],
				arr[5], arr[6], arr[7], arr[8], arr[9], arr[10], arr[11],
				arr[12]);
		return wb;
	}

	public static ArrayList<WorkBean> strlist2workbeanlist(
			ArrayList<String> strlist) {
		ArrayList<WorkBean> workbeanlist = new ArrayList<WorkBean>();
		for (String str : strlist) {

			workbeanlist.add(string2workbean(str));
		}
		return workbeanlist;

	}

	/**
	 * 
	 * @param value
	 *            1^仇茜家教^87.5^香江路香江花园2栋1单元301^李松^6,7^2015/3/11 0:00:00^1900/1/1
	 *            0:00:00^正常^买7赠1^
	 * @return
	 */
	public static WorkItemBean string2workitembean(String value) {
		value = ToolBox.avoidnull(value);
		String[] arr = value.split("\\^");
		WorkItemBean wb = new WorkItemBean(arr[0], arr[1], arr[2], arr[3],
				arr[4], arr[5], arr[6], arr[7], arr[8], arr[9]);
		return wb;
	}

	public static ArrayList<WorkItemBean> strlist2workitembeanlist(
			ArrayList<String> strlist) {
		ArrayList<WorkItemBean> workitembeanlist = new ArrayList<WorkItemBean>();
		for (String str : strlist) {

			workitembeanlist.add(string2workitembean(str));
		}
		return workitembeanlist;

	}

	/**
	 * 
	 * @param value
	 *            106^李松7/8月份网络中心工资^李松^1000^2015/9/16
	 *            18:42:48^这次是一次发了两个月的，共计1000元^1 特殊情况下是这样的
	 *            106^李松7/8月份网络中心工资^李松^1000^2015/9/16 18:42:48^^0
	 * @return
	 */
	public static IncomeBean string2incomebean(String value) {
		if (value.endsWith("^")) {
			value = value + "^";
		}
		value = ToolBox.avoidnull(value);
		String[] arr = value.split("\\^");
		if (arr[5].trim().equals("!!")) {
			arr[5] = "暂无";
		}
		IncomeBean cb = new IncomeBean(arr[0], arr[1], arr[2], arr[3], arr[4],
				arr[5], arr[6]);
		return cb;
	}

	public static ArrayList<IncomeBean> strlist2incomelist(
			ArrayList<String> strlist) {
		ArrayList<IncomeBean> incomelist = new ArrayList<IncomeBean>();
		for (String str : strlist) {
			incomelist.add(string2incomebean(str));
		}
		return incomelist;
	}

	/**
	 * 
	 * @param value
	 *            89^admin^收入主界面^浏览^2015/12/8 20:45:34^网页端^121.251.247.142^消费^
	 * @return
	 */
	public static LogBean string2logbean(String value) {
		if (value.endsWith("^")) {
			value = value + "^";
		}
		value = ToolBox.avoidnull(value);
		String[] arr = value.split("\\^");
		if (arr[5].trim().equals("!!")) {
			arr[5] = "暂无";
		}
		LogBean lb = new LogBean(arr[0], arr[1], arr[2], arr[3], arr[4],
				arr[5], arr[6],arr[7]);
		return lb;
	}

	public static ArrayList<LogBean> strlist2loglist(ArrayList<String> strlist) {
		ArrayList<LogBean> loglist = new ArrayList<LogBean>();
		for (String str : strlist) {
			loglist.add(string2logbean(str));
		}
		return loglist;
	}
}
