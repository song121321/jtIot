package song.jtslkj.util;

import java.util.ArrayList;

import android.content.Context;
import song.jtslkj.bean.BudgetBatchBean;
import song.jtslkj.config.MyConfig;
import song.jtslkj.db.BudgetBatchSQLManager;

public class InitExitUtil {

	Context contenx;

	public InitExitUtil(Context contenx) {
		super();
		this.contenx = contenx;
	}

	public void initSetting() {
		// 初始化数据库和基本设置
		InitDB();
		ExitSetting();
	}

	public static ArrayList<BudgetBatchBean> getInitBudgetsrc() {
		ArrayList<BudgetBatchBean> src = new ArrayList<BudgetBatchBean>();
		BudgetBatchBean b1 = new BudgetBatchBean(false, "饭卡", "600", true);
		BudgetBatchBean b2 = new BudgetBatchBean(false, "水果", "150", true);
		BudgetBatchBean b3 = new BudgetBatchBean(false, "小吃", "100", true);
		BudgetBatchBean b4 = new BudgetBatchBean(false, "超市", "240", true);
		BudgetBatchBean b5 = new BudgetBatchBean(false, "衣服", "100", true);
		BudgetBatchBean b6 = new BudgetBatchBean(false, "交通", "50", true);
		BudgetBatchBean b7 = new BudgetBatchBean(false, "浪漫基金", "200", true);
		BudgetBatchBean b8 = new BudgetBatchBean(false, "话费", "100", false);
		BudgetBatchBean b9 = new BudgetBatchBean(false, "美容", "100", false);
		BudgetBatchBean b10 = new BudgetBatchBean(false, "旅游", "300", true);
		BudgetBatchBean b11 = new BudgetBatchBean(false, "学习", "50", false);
		BudgetBatchBean b12 = new BudgetBatchBean(false, "医疗", "50", false);
		BudgetBatchBean b13 = new BudgetBatchBean(false, "其他", "150", true);
		BudgetBatchBean b14 = new BudgetBatchBean(false, "生活", "80", true);

		src.add(b1);
		src.add(b2);
		src.add(b3);
		src.add(b4);
		src.add(b5);
		src.add(b6);
		src.add(b7);
		src.add(b8);
		src.add(b9);
		src.add(b10);
		src.add(b11);
		src.add(b12);
		src.add(b13);
		src.add(b14);
		return src;
	}

	/**
	 * 初始化数据库操作 这里主要是初始化批量预算数据库就是把生成的数据加进去
	 */
	public void InitDB() {
		BudgetBatchSQLManager bbsm = new BudgetBatchSQLManager(contenx);
		try {
			bbsm.addBudgetBatchbeanBatch(getInitBudgetsrc());
		} catch (Exception e) {
		} finally {
			bbsm = null;
		}
	}

	/**
	 * 用户退出的时候，应当清空所有与用户相关的设置，因为数据共用，所以相当于恢复默认设置 firstin清空 account
	 * closegesturelock savabudget全都清空
	 */
	public void ExitSetting() {

		AccountSharedPreferenceHelper asph = new AccountSharedPreferenceHelper(
				contenx);
		try {
			asph.writeStringToSharedpreference(
					MyConfig.sharedpreference_tablecol_account, "");
			asph.writeStringToSharedpreference(
					MyConfig.sharedpreference_tablecol_closegesturelock, "");
			asph.writeStringToSharedpreference(
					MyConfig.sharedpreference_tablecol_firstin, "");
			asph.writeStringToSharedpreference(
					MyConfig.sharedpreference_tablecol_savabudget, "");
			asph.writeStringToSharedpreference(
					MyConfig.sharedpreference_tablecol_typeadd, "yes");
			asph.writeStringToSharedpreference(
					MyConfig.sharedpreference_tablecol_typeupdate, "");
			asph.writeStringToSharedpreference(
					MyConfig.sharedpreference_tablecol_typedelete, "yes");
			asph.writeStringToSharedpreference(
					MyConfig.sharedpreference_tablecol_typelogin, "");
			asph.writeStringToSharedpreference(
					MyConfig.sharedpreference_tablecol_typeview, "");

		}

		catch (Exception e) {
			// TODO: handle exception
		} finally {
			asph = null;
		}

	}
}
