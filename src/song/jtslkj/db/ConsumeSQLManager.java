package song.jtslkj.db;

import java.util.ArrayList;

import song.jtslkj.bean.ConsumeBean;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class ConsumeSQLManager {
	private SQLhelper helper;
	private SQLiteDatabase db;
	public static String TABLE_NAME_CONSUME = "Consume";
	/**
	 * id 消费编号 name 消费名称 otype 隶属名称 比如 水果 person 负责人 李松 money 金额 2 otime 时间
	 * 2015-7-10 10:16:53 detail 消费详细
	 */
	public static String[] TABLECOL_NAME_CONSUME = { "id", "name", "otype",
			"person", "money", "otime", "detail" };

	public ConsumeSQLManager(Context context) {
		helper = new SQLhelper(context, null);
	}

	/**
	 * 插入一个单独的消费豆子
	 * 
	 * @param cb
	 *            要插入的消费豆子
	 * @return 成功就返回true
	 */
	public Boolean addConsumebean(ConsumeBean cb) {
		Boolean result = false;
		db = helper.getWritableDatabase();
		db.beginTransaction();
		try {
			ContentValues values = new ContentValues();
			values.put(TABLECOL_NAME_CONSUME[0], cb.getId());
			values.put(TABLECOL_NAME_CONSUME[1], cb.getName());
			values.put(TABLECOL_NAME_CONSUME[2], cb.getOtype());
			values.put(TABLECOL_NAME_CONSUME[3], cb.getPerson());
			values.put(TABLECOL_NAME_CONSUME[4], cb.getMoney());
			values.put(TABLECOL_NAME_CONSUME[5], cb.getOtime());
			values.put(TABLECOL_NAME_CONSUME[6], cb.getOther());
			db.insert(TABLE_NAME_CONSUME, null, values);
			db.setTransactionSuccessful();
			result = true;
		} catch (Exception e) {
			Log.e("song121321", "addConsumebean  wrong");
		} finally {
			db.endTransaction();
			db.close();
		}
		return result;
	}

	/**
	 * 批量插入消费豆子
	 * 
	 * @param cblist
	 *            链表形式的消费豆子
	 * @return 如果全部成功就返回true 有任何一个出错都会返回false
	 */
	public Boolean addConsumebean(ArrayList<ConsumeBean> cblist) {
		Boolean result = true;
		for (int i = 0; i < cblist.size(); i++) {
			if (!addConsumebean(cblist.get(i))) {
				result = false;
				break;
			}
		}

		return result;
	}

	/**
	 * 删除Consume表中所有的数据
	 * 
	 * @return 失败 返回0 成功返回删除的数目
	 */
	public int deleteConsume() {
		db = helper.getWritableDatabase();
		int res = db.delete(TABLE_NAME_CONSUME, null, null);
		db.close();
		if (res == 0) {
			return 0;
		} else {
			return res;
		}

	}

	/**
	 * 删除Consume表中所有的数据
	 * 
	 * @return 失败 返回0 成功返回删除的数目
	 */
	public int deleteConsume(String id) {
		db = helper.getWritableDatabase();
		String[] args = { id };
		int res = db.delete(TABLE_NAME_CONSUME, "id=?", args);
		db.close();
		if (res == 0) {
			return 0;
		} else {
			return res;
		}

	}

	/**
	 * 模糊查询
	 * 
	 * @param text
	 *            要查询的字段
	 * @return
	 */
	public ArrayList<ConsumeBean> getConsumeVague(String text) {
		String where = " id like '%" + text + "%' or ";
		for (int i = 1; i < TABLECOL_NAME_CONSUME.length; i++) {
			if (i == TABLECOL_NAME_CONSUME.length - 1) {
				where = where + TABLECOL_NAME_CONSUME[i] + " like '%" + text
						+ "%' ";
			} else {
				where = where + TABLECOL_NAME_CONSUME[i] + " like '%" + text
						+ "%' or ";
			}
		}
		return getConsume(where);

	}

	/**
	 * 获取数据库中最近的消费记录id
	 * 
	 * @return 87
	 */
	public String getMaxId() {
		db = helper.getWritableDatabase();

		String m = "-1";
		try {
			Cursor c = queryTheCursor("  max(id) as id ", "");
			if (c.moveToNext()) {
				m = c.getString(c.getColumnIndex(TABLECOL_NAME_CONSUME[0]));
			}
			if(m==null){
				return "-1";
			}
			return m;
		} catch (Exception e) {
			db.close();
			return "-1";
		}

	}

	/**
	 * 查询Consume表中的全部字段数据
	 * 
	 * @param where
	 *            添加的条件 name = '猪猪'
	 * @return 返回所有符合条件的记录
	 */
	public ArrayList<ConsumeBean> getConsume(String where) {
		db = helper.getWritableDatabase();
		ArrayList<ConsumeBean> cblist = new ArrayList<ConsumeBean>();
		Cursor c = queryTheCursor(" * ", where);
		while (c.moveToNext()) {
			cblist.add(new ConsumeBean(c.getString(c
					.getColumnIndex(TABLECOL_NAME_CONSUME[0])), c.getString(c
					.getColumnIndex(TABLECOL_NAME_CONSUME[1])), c.getString(c
					.getColumnIndex(TABLECOL_NAME_CONSUME[2])), c.getString(c
					.getColumnIndex(TABLECOL_NAME_CONSUME[3])), c.getString(c
					.getColumnIndex(TABLECOL_NAME_CONSUME[4])), c.getString(c
					.getColumnIndex(TABLECOL_NAME_CONSUME[5])), c.getString(c
					.getColumnIndex(TABLECOL_NAME_CONSUME[6]))));
		}
		db.close();
		return cblist;

	}

	/**
	 * 查询数据库中的数据，表是 上面写的那个Consume
	 * 
	 * @param quarycontent
	 *            查询内容
	 * @param where
	 *            如果内容是"" 那就默认是全部查询，否则让格式是 name='猪猪'
	 * @return
	 */
	public Cursor queryTheCursor(String quarycontent, String where) {
		Cursor c;
		if (where.equals("")) {
			c = db.rawQuery("SELECT " + quarycontent + " FROM "
					+ TABLE_NAME_CONSUME, null);
			Log.e("ls", "SELECT " + quarycontent + " FROM "
					+ TABLE_NAME_CONSUME);
		} else {
			String sqlstr = "SELECT " + quarycontent + " FROM "
					+ TABLE_NAME_CONSUME + " where " + where;
			Log.e("ls", sqlstr);
			c = db.rawQuery(sqlstr, null);
		}
		return c;
	}

}
