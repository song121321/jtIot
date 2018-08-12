package song.jtslkj.util;

import song.jtslkj.config.MyConfig;
import android.content.Context;
import android.content.SharedPreferences;
/**
 * 管理账号信息，轻量级，所有的数据均存在于xjk这张表中
 * 目前拥有两个列 
 * firstin 表示是否第一次使用本程序
 * account 用户账号信息，表示要不要登陆
 * @author Administrator
 *
 */
public class AccountSharedPreferenceHelper {
	Context context;
	

	public AccountSharedPreferenceHelper(Context context) {
		super();
		this.context = context;
	}
	/**
	 * 向Account数据表的colname列中写数据
	 * @param colname 列名称
	 * @param str 列具体的值
	 */
	public  void writeStringToSharedpreference(String colname,String str){
		SharedPreferences preferences = context.getSharedPreferences(MyConfig.sharedpreference_tablename, Context.MODE_PRIVATE);// 获取本程序sharedprefere（用来获取本程序保存的值）
		SharedPreferences.Editor  spediEditor = preferences.edit();
		spediEditor.putString(colname, str);
		spediEditor.commit();
	}
	
	
	/**
	 * 从Account数据标中读取某一列的数据，默认值为""
	 * @param colname 列名称
	 * @return 这一列具体的值
	 */
	public  String readStringFromSharedpreference(String colname){
		SharedPreferences preferences = context.getSharedPreferences(MyConfig.sharedpreference_tablename, Context.MODE_PRIVATE);// 获取本程序sharedprefere（用来获取本程序保存的值）
	    return preferences.getString(colname, "").toString();
		
	}

}
