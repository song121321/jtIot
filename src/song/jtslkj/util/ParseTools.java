package song.jtslkj.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import song.jtslkj.bean.IraLogBean;
import song.jtslkj.bean.StaBean;
import song.jtslkj.bean.StaDayNetBean;
import song.jtslkj.bean.WellAttrBean;
import song.jtslkj.bean.WellBean;

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
}
