package song.jtslkj.db;

import java.util.ArrayList;

import song.jtslkj.bean.BudgetBatchBean;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class BudgetBatchSQLManager {
	private SQLhelper helper;
	private SQLiteDatabase db;
	public static String TABLE_NAME = "BudgetBatch";
	/**
	 * id 编号 name 预算名称 ischeck 1为是，0为否 是否选择 money 金额 2 isusual 是否常用 常用为1 否则为0
	 * 2015-7-10 10:16:53 detail 消费详细
	 */
	public static String[] TABLECOL_NAME = { "id", "ischeck", "name", "money",
			"isusual" };

	public BudgetBatchSQLManager(Context context) {
		helper = new SQLhelper(context, null);
	}

	/**
	 * 添加一个预算豆子
	 * 
	 * @param cb
	 * @return
	 */
	public Boolean addBudgetBatchbean(BudgetBatchBean cb) {
		Boolean result = false;
		db = helper.getWritableDatabase();
		db.beginTransaction();
		try {
			ContentValues values = new ContentValues();
			values.put(TABLECOL_NAME[1], cb.isIscheck());
			values.put(TABLECOL_NAME[2], cb.getName());
			values.put(TABLECOL_NAME[3], cb.getMoney());
			values.put(TABLECOL_NAME[4], cb.isIsusual());
			db.insert(TABLE_NAME, null, values);
			db.setTransactionSuccessful();
			result = true;
		} catch (Exception e) {
			Log.e("song121321", "addBudgetBatchbean  wrong");
		} finally {
			db.endTransaction();
			db.close();
		}
		return result;
	}

	/**
	 * 批量添加预算豆子
	 * 
	 * @param cblist
	 * @return
	 */
	public Boolean addBudgetBatchbeanBatch(ArrayList<BudgetBatchBean> cblist) {
		Boolean result = true;
		for (int i = 0; i < cblist.size(); i++) {
			if (!addBudgetBatchbean(cblist.get(i))) {
				result = false;
				break;
			}
		}

		return result;

	}

	/**
	 * 删除BudgetBatch表中所有的数据
	 * 
	 * @return 失败 返回0 成功返回删除的数目
	 */
	public int deleteBudgetBatch(String id) {
		db = helper.getWritableDatabase();
		String[] args = { id };
		int res = db.delete(TABLE_NAME, "id=?", args);
		db.close();
		if (res == 0) {
			return 0;
		} else {
			return res;
		}

	}

	/**
	 * 更新一条记录
	 * 
	 * @param id
	 *            预算记录对应的id
	 * @param cb
	 *            新的预算豆子
	 * @return 返回更改的条目个数
	 */
	public int UpdateBudgetBatch(String id, BudgetBatchBean cb) {

		db = helper.getWritableDatabase();
		try {
			ContentValues values = new ContentValues();
			values.put(TABLECOL_NAME[0], cb.getId());
			values.put(TABLECOL_NAME[1], cb.isIscheck());
			values.put(TABLECOL_NAME[2], cb.getName());
			values.put(TABLECOL_NAME[3], cb.getMoney());
			values.put(TABLECOL_NAME[4], cb.isIsusual());
			String[] args = { String.valueOf(id) };

			return db.update(TABLE_NAME, values, "id=?", args);
		} catch (Exception e) {
			return -1;
		} finally {
			db.close();
		}

	}

	public int UpdateBudgetBatch(BudgetBatchBean cb) {
		return UpdateBudgetBatch(cb.getId(), cb);
	}

	/**
	 * 得到存储的预算豆子链表
	 * 
	 * @param where
	 *            添加的条件 name = '猪猪' 如果为"" 则是返回所有的数据
	 * @return
	 */
	public ArrayList<BudgetBatchBean> getBudgetBatch(String where) {
		db = helper.getWritableDatabase();
		ArrayList<BudgetBatchBean> cblist = new ArrayList<BudgetBatchBean>();
		Cursor c = queryTheCursor(" * ", where);
		while (c.moveToNext()) {
			cblist.add(new BudgetBatchBean(c.getString(c
					.getColumnIndex(TABLECOL_NAME[0])), c.getString(c
					.getColumnIndex(TABLECOL_NAME[1])), c.getString(c
					.getColumnIndex(TABLECOL_NAME[2])), c.getString(c
					.getColumnIndex(TABLECOL_NAME[3])), c.getString(c
					.getColumnIndex(TABLECOL_NAME[4]))));
		}
		db.close();

		for (BudgetBatchBean bbb : cblist) {
			System.out.println(bbb.getId() + "----------------"
					+ bbb.isIscheck() + " " + bbb.getName() + " "
					+ bbb.getMoney() + " " + bbb.isIsusual());
		}

		return cblist;

	}

	/**
	 * 查询数据库中的数据，表是 上面写的那个 BudgetBatch
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
			c = db.rawQuery("SELECT " + quarycontent + " FROM " + TABLE_NAME,
					null);
			Log.e("ls", "SELECT " + quarycontent + " FROM " + TABLE_NAME);
		} else {
			String sqlstr = "SELECT " + quarycontent + " FROM " + TABLE_NAME
					+ " where " + where;
			Log.e("ls", sqlstr);
			c = db.rawQuery(sqlstr, null);
		}
		return c;
	}

}
