package song.jtslkj.util;

import java.util.ArrayList;

import android.content.Context;

import song.jtslkj.config.MyConfig;

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


	/**
	 * 初始化数据库操作 这里主要是初始化批量预算数据库就是把生成的数据加进去
	 */
	public void InitDB() {

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


		}

		catch (Exception e) {
			// TODO: handle exception
		} finally {
			asph = null;
		}

	}
}
