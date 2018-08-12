package song.jtslkj.service;

import java.util.ArrayList;
import java.util.HashMap;

import song.jtslkj.activity.BaijiaBudgetActivity;
import song.jtslkj.activity.BaijiaConsumeActivity;
import song.jtslkj.activity.BaseActivity;
import song.jtslkj.activity.ChijiaActivity;
import song.jtslkj.activity.MainActivity;
import song.jtslkj.bean.LogBean;
import song.jtslkj.config.MyConfig;
import song.jtslkj.util.AccountSharedPreferenceHelper;
import song.jtslkj.util.ParseTools;
import song.jtslkj.util.ToolBox;
import song.jtslkj.util.WebServiceUtil;
import com.jtslkj.R;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;

public class NotificationService extends Service {
	private ArrayList<LogBean> loglist = new ArrayList<LogBean>();

	private int module;
	// 获取消息线程
	private MessageThread messageThread = null;

	// 点击查看
	private Intent messageIntent = null;
	private PendingIntent messagePendingIntent = null;

	// 通知栏消息
	private int messageNotificationID = 1000;
	private Notification messageNotification = null;
	private NotificationManager messageNotificatioManager = null;

	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		// 开启线程
		messageThread = new MessageThread();
		messageThread.isRunning = true;
		messageThread.start();

		return super.onStartCommand(intent, flags, startId);
	}

	/**
	 * 从服务器端获取消息
	 * 
	 */
	class MessageThread extends Thread {
		// 设置是否循环推送
		public boolean isRunning = true;

		public void run() {
			while (isRunning) {
				try {
					// 间隔时间
					Thread.sleep(2000);
					// 获取服务器消息

					ToolBox tb = new ToolBox(NotificationService.this);
					if (tb.checkNetworkAvailable()) {
						AccountSharedPreferenceHelper asph = new AccountSharedPreferenceHelper(
								NotificationService.this);
						String lateid = asph
								.readStringFromSharedpreference(MyConfig.sharedpreference_tablecol_loglateid);
						if (!lateid.equals("")) {
							new GetLogLateTask(lateid).execute();
						}
						asph = null;
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void onDestroy() {
		// System.exit(0);
		messageThread.isRunning = false;
		super.onDestroy();
	}

	@SuppressWarnings("deprecation")
	private void startNotice() {
		for (LogBean lb : loglist) {
			if (lb.getModule().equals("消费")) {
				module = 1;
			} else if (lb.getModule().equals("预算")) {
				module = 0;
			} else if (lb.getModule().equals("工作")) {
				module = 2;
			} else if (lb.getModule().equals("收入")) {
				module = 3;
			} else if (lb.getModule().equals("新闻")) {
				module = 4;
			} else if (lb.getModule().equals("统计报表")) {
				module = 5;
			} else {
				module = 6;
			}
			// 初始化
			messageNotification = new Notification();
			if (lb.getPerson().equals("admin")) {
				messageNotification.icon = R.drawable.avatar3;
			} else if (lb.getPerson().equals("hss")) {
				messageNotification.icon = R.drawable.avatar1;
			} else {
				messageNotification.icon = R.drawable.ic_launcher;
			}
			messageNotification.tickerText = lb.getIncident();
			// messageNotification.
			messageNotification.defaults = Notification.DEFAULT_SOUND;
			messageNotificatioManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
			System.out.println("--------------------" + module);
			messageNotification.flags |= Notification.FLAG_AUTO_CANCEL;
			switch (module) {
			case 0:
				messageIntent = new Intent(this, BaijiaBudgetActivity.class);
				break;
			case 1:
				messageIntent = new Intent(this, BaijiaConsumeActivity.class);
				break;
			case 2:
				messageIntent = new Intent(this, ChijiaActivity.class);
				break;
			case 3:
				messageIntent = new Intent(this, ChijiaActivity.class);
				break;
			default:
				 messageIntent = new Intent(this, MainActivity.class);
				break;
			}
			//messageIntent = new Intent(NotificationService.this, BaijiaBudgetActivity.class);
			messageIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			messagePendingIntent = PendingIntent.getActivity(NotificationService.this, 0,
					messageIntent,  PendingIntent.FLAG_CANCEL_CURRENT);
			messageNotification.contentIntent = messagePendingIntent;
			boolean yes = false;
			AccountSharedPreferenceHelper asph = new AccountSharedPreferenceHelper(
					this);

			if (!asph.readStringFromSharedpreference(
					MyConfig.sharedpreference_tablecol_typeadd).equals("")) {
				if (lb.getType().equals("添加")) {
					yes = true;
				}
			}
			if (!asph.readStringFromSharedpreference(
					MyConfig.sharedpreference_tablecol_typeupdate).equals("")) {
				if (lb.getType().equals("更新")) {
					yes = true;
				}
			}
			if (!asph.readStringFromSharedpreference(
					MyConfig.sharedpreference_tablecol_typedelete).equals("")) {
				if (lb.getType().equals("删除")) {
					yes = true;
				}
			}
			if (!asph.readStringFromSharedpreference(
					MyConfig.sharedpreference_tablecol_typelogin).equals("")) {
				if (lb.getType().equals("登陆")) {
					yes = true;
				}
			}
			if (!asph.readStringFromSharedpreference(
					MyConfig.sharedpreference_tablecol_typeview).equals("")) {
				if (lb.getType().equals("浏览")) {
					yes = true;
				}
			}
			String serverMessage = lb.getIncident();
			if (yes && serverMessage != null && !"".equals(serverMessage)) {
				// 更新通知栏
//				messageNotification.setLatestEventInfo(getApplicationContext(),
//						lb.getModule(), lb.getPerson() + lb.getType()
//								+ serverMessage, messagePendingIntent);
//				messageNotificatioManager.notify(messageNotificationID,
//						messageNotification);
				// 每次通知完，通知ID递增一下，避免消息覆盖掉
				messageNotificationID++;
			}
		}
	}

	private class GetLogLateTask extends
			AsyncTask<Void, Void, ArrayList<String>> {
		String id;

		public GetLogLateTask(String id) {
			super();
			this.id = id;
		}

		@Override
		protected ArrayList<String> doInBackground(Void... arg0) {
			HashMap<String, String> params = new HashMap<String, String>();
			params.put("id", id);
			String nameSpace = MyConfig.nameSpace;
			String methodName = MyConfig.methodName_GetLogLate;
			String endPoint = MyConfig.endPoint;
			return WebServiceUtil.getArrayOfAnyType(nameSpace, methodName,
					endPoint, params);
		}

		@Override
		protected void onPostExecute(ArrayList<String> result) {
			super.onPostExecute(result);
			loglist = ParseTools.strlist2loglist(result);
			if (loglist != null && loglist.size() > 0) {
				LogBean lb = loglist.get(0);
				AccountSharedPreferenceHelper asph = new AccountSharedPreferenceHelper(
						NotificationService.this);
				asph.writeStringToSharedpreference(
						MyConfig.sharedpreference_tablecol_loglateid,
						lb.getId());
				startNotice();
			}
		}
	}
}