package song.jtslkj.activity;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

import song.jtslkj.app.MyApplication;
import song.jtslkj.bean.LogBean;
import song.jtslkj.config.MyConfig;
import song.jtslkj.fragment.WojiaZujiAllFragment;
import song.jtslkj.fragment.WojiaZujiHssFragment;
import song.jtslkj.fragment.WojiaZujiLsFragment;
import song.jtslkj.ui.PagerSlidingTabStrip;
import song.jtslkj.util.ParseTools;
import song.jtslkj.util.WebServiceUtil;
import com.jtslkj.R;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.ViewConfiguration;

public class WojiaZujiActivity extends FragmentActivity {
	private ArrayList<LogBean> loglist = new ArrayList<LogBean>();
	private WojiaZujiAllFragment allf, lsf, ssf;
	private PagerSlidingTabStrip tabs;
	/**
	 * 获取当前屏幕的密度
	 */
	private DisplayMetrics dm;
	ViewPager pager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyApplication.getInstance().addActivity(this);
		setContentView(R.layout.activity_wojia_zuji);
		findview();
		new GetLogTask().execute();
	}

	private void findview() {
		pager = (ViewPager) findViewById(R.id.pager_wojia_log);
		tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs_wojia_log);
	}

	private void refresh() {
		setOverflowShowingAlways();
		dm = getResources().getDisplayMetrics();
		FragmentManager fm = getSupportFragmentManager();
		pager.setAdapter(new MyPagerAdapter(fm));
		tabs.setViewPager(pager);
		setTabsValue();
	}

	private class GetLogTask extends AsyncTask<Void, Void, ArrayList<String>> {
		@Override
		protected ArrayList<String> doInBackground(Void... arg0) {
			HashMap<String, String> params = new HashMap<String, String>();
			String nameSpace = MyConfig.nameSpace;
			String methodName = MyConfig.methodName_GetLog;
			String endPoint = MyConfig.endPoint;
			return WebServiceUtil.getArrayOfAnyType(nameSpace, methodName,
					endPoint, params);
		}

		@Override
		protected void onPostExecute(ArrayList<String> result) {
			// 如果db为真，表示只是更新数据库，否则，更新UI
			super.onPostExecute(result);
			loglist = ParseTools.strlist2loglist(result);

			refresh();
		}
	}

	/**
	 * 对PagerSlidingTabStrip的各项属性进行赋值。
	 */
	private void setTabsValue() {
		// 设置Tab是自动填充满屏幕的
		tabs.setShouldExpand(true);

		tabs.setUnderlineColorResource(R.color.tv_White);
		// 设置Tab的分割线是透明的
		tabs.setDividerColor(Color.TRANSPARENT);
		// 设置Tab底部线的高度
		tabs.setUnderlineHeight((int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, 1, dm));
		// 设置Tab Indicator的高度
		tabs.setIndicatorHeight((int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, 4, dm));
		// 设置Tab标题文字的大小
		tabs.setTextSize((int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_SP, 16, dm));
		// 设置Tab Indicator的颜色
		tabs.setIndicatorColor(getResources().getColor(R.color.actionbar_bg));
		// 设置选中Tab文字的颜色 (这是我自定义的一个方法)
		tabs.setSelectedTextColor(getResources().getColor(R.color.actionbar_bg));
		// 取消点击Tab时的背景色
		tabs.setTabBackground(0);
	}

	public class MyPagerAdapter extends FragmentPagerAdapter {

		public MyPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		private final String[] titles = WojiaZujiActivity.this.getResources()
				.getStringArray(R.array.person_arr1);

		@Override
		public CharSequence getPageTitle(int position) {
			return titles[position];
		}

		@Override
		public int getCount() {
			return titles.length;
		}

		@Override
		public Fragment getItem(int position) {
			switch (position) {
			case 0:
				if (allf == null) {
					allf = WojiaZujiAllFragment.newInstance(loglist);
				}
				return allf;
			case 1:
				if (lsf == null) {
					ArrayList<LogBean> lslist = new ArrayList<LogBean>();
					for (LogBean logBean : loglist) {
						if (logBean.getPerson().trim()
								.equals(getString(R.string.admin))
								|| logBean.getPerson().trim()
										.equals(getString(R.string.Admin))) {
							lslist.add(logBean);
						}
					}
					lsf = WojiaZujiLsFragment.newInstance(lslist);
				}
				return lsf;

			case 2:
				if (ssf == null) {
					ArrayList<LogBean> sslist = new ArrayList<LogBean>();
					for (LogBean logBean : loglist) {
						if (logBean.getPerson().trim()
								.equals(getString(R.string.hss))
								|| logBean.getPerson().trim()
										.equals(getString(R.string.Hss))) {
							sslist.add(logBean);
						}
					}
					ssf = WojiaZujiHssFragment.newInstance(sslist);
				}
				return ssf;
			default:
				return null;
			}
		}

	}

	private void setOverflowShowingAlways() {
		try {
			ViewConfiguration config = ViewConfiguration.get(this);
			Field menuKeyField = ViewConfiguration.class
					.getDeclaredField("sHasPermanentMenuKey");
			menuKeyField.setAccessible(true);
			menuKeyField.setBoolean(config, false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
