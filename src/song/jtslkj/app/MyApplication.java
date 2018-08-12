package song.jtslkj.app;

import java.util.LinkedList;

import android.app.Activity;
import android.app.Application;

public class MyApplication extends Application {

	private LinkedList<Activity> activityList = new LinkedList<Activity>();
	private static MyApplication instance;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();

	}

	public static MyApplication getInstance() {
		if (null == instance) {
			instance = new MyApplication();
		}
		return instance;
	}

	public void addActivity(Activity activity) {
		activityList.add(activity);
	}

	public void removeActivity(Activity activity) {
		activityList.remove(activity);
	}

	public void exit() {
		for (Activity activity : activityList) {
			activity.finish();
		}
		System.exit(0);
	}

	@Override
	public void onTerminate() {
		// TODO Auto-generated method stub
		// ImageLoaderUtil.stopload(instance);
		super.onTerminate();
	}

}
