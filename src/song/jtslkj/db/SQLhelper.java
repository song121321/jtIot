package song.jtslkj.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLhelper extends SQLiteOpenHelper {

	public static String DB_NAME = "JTIOT.db";
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
        //create table
		String sql = "CREATE  TABLE table1 (id INTEGER PRIMARY KEY , name VARCHAR, otype VARCHAR, "
				+ " person VARCHAR, money VARCHAR,otime VARCHAR, detail VARCHAR)";
		db.execSQL(sql);

		sql = "CREATE  TABLE table2 (id INTEGER PRIMARY KEY ,ischeck VARCHAR, name VARCHAR, money VARCHAR,isusual VARCHAR)";
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}

}
