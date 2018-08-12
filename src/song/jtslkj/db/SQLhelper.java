package song.jtslkj.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLhelper extends SQLiteOpenHelper {

	public static String DB_NAME = "LoveHoney.db";
	public static int DB_VERSION = 1;

	public SQLhelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	public SQLhelper(Context context, CursorFactory factory) {
		super(context, DB_NAME, factory, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// id 消费记录编号
		// name 名称
		// otype 隶属名字
		// person 负责人
		// money 金额
		// otime 消费时间
		// detail 消费详细记录

		String sql = "CREATE  TABLE Consume (id INTEGER PRIMARY KEY , name VARCHAR, otype VARCHAR, "
				+ " person VARCHAR, money VARCHAR,otime VARCHAR, detail VARCHAR)";
		db.execSQL(sql);
		// id 预算id
		// ischeck 是否选中
		// name 预算名称
		// money 预算金额
		// isusual 是否常用
		sql = "CREATE  TABLE BudgetBatch (id INTEGER PRIMARY KEY ,ischeck VARCHAR, name VARCHAR, money VARCHAR,isusual VARCHAR)";
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}

}
