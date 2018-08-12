package song.honey.activity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import org.kobjects.base64.Base64;

import song.honey.app.MyApplication;
import song.honey.config.MyConfig;
import song.honey.ui.SelectPicPopupWindow;
import song.honey.util.ToolBox;
import song.honey.util.WebServiceUtil;
import song.shuang.honey.R;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kyleduo.switchbutton.SwitchButton;

public class BaijiaConsumeAddActivity extends BaseActivity implements
		OnClickListener {
	SelectPicPopupWindow menuWindow;
	SwitchButton sb_img;
	ImageView img;
	EditText et_name, et_money, et_content;
	RelativeLayout rl_time, rl_budget, rl_person, rl_image;
	TextView tv_time_temp, tv_budget_temp, tv_person_temp, tv_actionbar_cancel,
			tv_actionbar_sure, tv_actionbar_title;
	String name, person, budget, money, time, detail;
	String[] budgetlist;
	private static final int PHOTO_CARMERA = 1;
	private static final int PHOTO_PICK = 2;
	private static final int PHOTO_CUT = 3;
	private boolean ispic = false;
	// 创建一个以当前系统时间为名称的文件，防止重复
	private File tempFile = new File(Environment.getExternalStorageDirectory(),
			ToolBox.getPhotoFileName());

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_baijia_consume_add);
		MyApplication.getInstance().addActivity(BaijiaConsumeAddActivity.this);
		findview();
		initview();
		setlistener();
	}

	private void findview() {
		sb_img = (SwitchButton) findViewById(R.id.sb_baijia_add_image);
		img = (ImageView) findViewById(R.id.iv_baijia_add_image);
		et_name = (EditText) findViewById(R.id.et_baijia_add_name);
		et_money = (EditText) findViewById(R.id.et_baijia_add_money);
		et_content = (EditText) findViewById(R.id.et_baijia_add_content);
		rl_time = (RelativeLayout) findViewById(R.id.rl_baijia_add_time);
		rl_budget = (RelativeLayout) findViewById(R.id.rl_baijia_add_budget);
		rl_person = (RelativeLayout) findViewById(R.id.rl_baijia_add_person);
		rl_image = (RelativeLayout) findViewById(R.id.rl_baijia_add_image);
		tv_time_temp = (TextView) findViewById(R.id.tv_baijia_add_time_temp);
		tv_budget_temp = (TextView) findViewById(R.id.tv_baijia_add_budget_temp);
		tv_person_temp = (TextView) findViewById(R.id.tv_baijia_add_person_temp);

		tv_actionbar_cancel = (TextView) findViewById(R.id.tv_actionbar_usual_cancel);
		tv_actionbar_sure = (TextView) findViewById(R.id.tv_actionbar_usual_sure);
		tv_actionbar_title = (TextView) findViewById(R.id.tv_actionbar_usual_title);

	}

	private void initview() {
		name = "";
		money = "0";
		time = ToolBox.getcurrentdatetime();
		budget = getString(R.string.baijia_add_budget_frute);
		person = getString(R.string.baijia_add_person_public);
		detail = getString(R.string.baijia_add_person_fromphone);
		tv_actionbar_title.setText(getString(R.string.baijia_daohang_add));
		tv_time_temp.setText(time);
	}

	private void setlistener() {
		tv_actionbar_cancel.setOnClickListener(this);
		tv_actionbar_sure.setOnClickListener(this);
		rl_time.setOnClickListener(this);
		rl_person.setOnClickListener(this);
		rl_budget.setOnClickListener(this);

		sb_img.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				ispic = arg1;
				if (ispic) {
					img.setVisibility(View.VISIBLE);
					menuWindow = new SelectPicPopupWindow(
							BaijiaConsumeAddActivity.this, itemsOnClick);
					menuWindow.showAtLocation(BaijiaConsumeAddActivity.this
							.findViewById(R.id.scr_consume_add), Gravity.BOTTOM
							| Gravity.CENTER_HORIZONTAL, 0, 0);

				} else {
					img.setVisibility(View.GONE);
				}
			}
		});
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.tv_actionbar_usual_cancel:
			MyApplication.getInstance().removeActivity(
					BaijiaConsumeAddActivity.this);
			finish();
			break;
		case R.id.tv_actionbar_usual_sure:
			addConsume();
			break;
		case R.id.rl_baijia_add_time:
			showTimeDialog();
			break;
		case R.id.rl_baijia_add_budget:
			showBudgetDialog();
			break;
		case R.id.rl_baijia_add_person:
			showPersonDialog();
			break;

		default:
			break;
		}
	}

	private OnClickListener itemsOnClick = new OnClickListener() {

		public void onClick(View v) {
			menuWindow.dismiss();
			switch (v.getId()) {
			case R.id.btn_take_photo:
				startCamera();
				break;
			case R.id.btn_pick_photo:
				startPick();
				break;
			case R.id.btn_cancel:
				sb_img.setChecked(false);
				img.setVisibility(View.GONE);
				ispic = false;
				break;
			default:
				break;
			}

		}

	};

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case PHOTO_CARMERA:
			startPhotoZoom(Uri.fromFile(tempFile), 300);
			break;
		case PHOTO_PICK:
			if (null != data) {
				startPhotoZoom(data.getData(), 300);
			}
			break;
		case PHOTO_CUT:
			if (null != data) {
				setPicToView(data);
			}
			break;

		default:
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	// 将裁剪后的图片显示在ImageView上
	private void setPicToView(Intent data) {
		Bundle bundle = data.getExtras();
		if (null != bundle) {
			final Bitmap bmp = bundle.getParcelable("data");
			img.setVisibility(View.VISIBLE);
			img.setImageBitmap(bmp);

			saveCropPic(bmp);
			Log.i("MainActivity", tempFile.getAbsolutePath());
		}
	}

	private void addConsume() {

		if (et_name.getText().toString().equals("")
				|| et_money.getText().toString().equals("")) {
			// 通过AlertDialog.Builder这个类来实例化我们的一个AlertDialog的对象
			AlertDialog.Builder builder = new AlertDialog.Builder(
					BaijiaConsumeAddActivity.this);
			// 设置Title的图标
			builder.setIcon(android.R.drawable.ic_dialog_info);
			// 设置Title的内容
			builder.setTitle("提示");
			// 设置Content来显示一个信息
			builder.setMessage("内容和金额不能为空");
			// 设置一个PositiveButton
			builder.setPositiveButton("确定",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
						}
					});
			// 显示出该对话框
			builder.show();
		} else {
			showLoadingDialog("正在添加...");
			String name = tempFile.getName();
			String bytee = Compressimg2byte();
			if (bytee != null && ispic == true) {
				new UploadImageTask(name, bytee).execute();

				detail = et_content.getText().toString()
						+ "<p><img src=\"/UpLoad/image/m/" + tempFile.getName()
						+ ".jpg \" width=\"500\" height=\"500\" /></p>";
			}else{
					detail = et_content.getText().toString();
			}
			name = et_name.getText().toString();
			money = et_money.getText().toString();
		
			new AddConsumeTask(name, person, budget, money, time, detail)
					.execute();
			addlog("0", "消费名" + name,"1");

		}

	}

	private void showPersonDialog() {
		final AlertDialog dlg = new AlertDialog.Builder(this).create();
		dlg.show();
		Window window = dlg.getWindow();
		// *** 主要就是在这里实现这种效果的.
		// 设置窗口的内容页面,shrew_exit_dialog.xml文件中定义view内容
		window.setContentView(R.layout.item_baijai_add_person);
		// 为确认按钮添加事件,执行退出应用操作
		TextView tv_public = (TextView) window.findViewById(R.id.tv_content1);
		tv_public.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				person = getString(R.string.baijia_add_person_public);
				tv_person_temp.setText(person);
				dlg.cancel();
			}
		});
		TextView tv_ls = (TextView) window.findViewById(R.id.tv_content2);
		tv_ls.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				person = getString(R.string.lisong);
				tv_person_temp.setText(person);
				dlg.cancel();
			}
		});

		TextView tv_hss = (TextView) window.findViewById(R.id.tv_content3);
		tv_hss.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				person = getString(R.string.houshuangshuang);
				tv_person_temp.setText(person);
				dlg.cancel();
			}
		});

	}

	private void showTimeDialog() {
		Dialog dialog = null;
		dialog = new DatePickerDialog(BaijiaConsumeAddActivity.this,
				new DatePickerDialog.OnDateSetListener() {
					public void onDateSet(DatePicker dp, int year, int month,
							int dayOfMonth) {
						month = month + 1;
						if (month <= 9) {
							time = year + "-0" + month;
						} else {
							time = year + "-" + month;
						}
						if (dayOfMonth <= 9) {
							time = time + "-0" + dayOfMonth;
						} else {
							time = time + "-" + dayOfMonth;
						}
						tv_time_temp.setText(time);
					}

				},

				Calendar.getInstance().get(Calendar.YEAR), Calendar
						.getInstance().get(Calendar.MONTH), Calendar
						.getInstance().get(Calendar.DAY_OF_MONTH));
		dialog.show();

	}

	private void showBudgetDialog() {
		showLoadingDialog("正在拼命加载当月预算");
		new TypeTask().execute();
	}

	private class TypeTask extends AsyncTask<Void, Void, ArrayList<String>> {

		@Override
		protected ArrayList<String> doInBackground(Void... arg0) {
			HashMap<String, String> params = new HashMap<String, String>();
			params.put("time", ToolBox.getcurrentmonth());
			String nameSpace = MyConfig.nameSpace;
			String methodName = MyConfig.methodName_GetBudget;
			String endPoint = MyConfig.endPoint;
			return WebServiceUtil.getArrayOfAnyType(nameSpace, methodName,
					endPoint, params);
		}

		@Override
		protected void onPostExecute(ArrayList<String> result) {
			super.onPostExecute(result);
			result.remove(0);
			budgetlist = new String[result.size()];
			result.toArray(budgetlist);
			new AlertDialog.Builder(BaijiaConsumeAddActivity.this)
					.setTitle("选择预算")
					.setIcon(android.R.drawable.ic_dialog_info)
					.setSingleChoiceItems(budgetlist, 0,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									budget = budgetlist[which];
									tv_budget_temp.setText(budget);
									dialog.dismiss();
								}
							})
					.setNegativeButton(getString(R.string.system_cancel), null)
					.show();
			closeLoadingDialog();
		}

	}

	private class AddConsumeTask extends
			AsyncTask<Void, Void, ArrayList<String>> {
		String name, person, budget, money, time, detail;

		public AddConsumeTask(String name, String person, String budget,
				String money, String time, String detail) {
			super();
			this.name = name;
			this.person = person;
			this.budget = budget;
			this.money = money;
			this.time = time;
			this.detail = detail;
		}

		@Override
		protected ArrayList<String> doInBackground(Void... arg0) {

			HashMap<String, String> params = new HashMap<String, String>();
			params.put("name", name);
			params.put("person", person);
			params.put("Budget", budget);
			params.put("omoney", money);
			params.put("time", time);
			params.put("detail", detail);
			String nameSpace = MyConfig.nameSpace;
			String methodName = MyConfig.methodName_AddConsume;
			String endPoint = MyConfig.endPoint;
			return WebServiceUtil.getArrayOfAnyType(nameSpace, methodName,
					endPoint, params);
		}

		@Override
		protected void onPostExecute(ArrayList<String> result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			Boolean res = false;
			if (result.size() == 1) {
				if (result.get(0).trim().equals("success")) {
					res = true;
				}
			}
			closeLoadingDialog();
			if (res) {
				Toast.makeText(BaijiaConsumeAddActivity.this, "添加成功",
						Toast.LENGTH_LONG).show();
				MyApplication.getInstance().removeActivity(
						BaijiaConsumeAddActivity.this);
				finish();
			} else {
				Toast.makeText(BaijiaConsumeAddActivity.this, "哎呀，失败了，好好查查",
						Toast.LENGTH_LONG).show();

			}

		}
	}

	private class UploadImageTask extends AsyncTask<Void, Void, String> {
		String title;

		String bytestr;

		public UploadImageTask(String title, String bytestr) {
			super();
			this.title = title;

			this.bytestr = bytestr;
		}

		@Override
		protected String doInBackground(Void... arg0) {
			HashMap<String, String> params = new HashMap<String, String>();
			params.put("title", title);
			params.put("bytestr", bytestr);
			Log.e("fffff", bytestr);
			String nameSpace = MyConfig.nameSpace;
			String methodName = MyConfig.methodName_FileUploadImage;
			String endPoint = MyConfig.endPoint;
			return WebServiceUtil.getAnyType(nameSpace, methodName, endPoint,
					params);
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			if (result.equals("true")) {
				showShortToast("Image UpLoad Success!");
			} else {
				showShortToast("Image UpLoad Failed!");
			}

		}
	}

	// 把裁剪后的图片保存
	public void saveCropPic(Bitmap bmp) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		FileOutputStream fis = null;
		bmp.compress(Bitmap.CompressFormat.PNG, 100, baos);
		try {
			fis = new FileOutputStream(tempFile);
			fis.write(baos.toByteArray());
			fis.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != baos) {
					baos.close();
				}
				if (null != fis) {
					fis.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public String Compressimg2byte() {
		try {

			String srcUrl = tempFile.getAbsolutePath(); // "/mnt/sdcard/"; //路径
			// String fileName = PhotoName+".jpg"; //文件名
			FileInputStream fis = new FileInputStream(srcUrl);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			byte[] buffer = new byte[8192];
			int count = 0;
			while ((count = fis.read(buffer)) >= 0) {
				baos.write(buffer, 0, count);
			}
			String uploadBuffer = new String(Base64.encode(baos.toByteArray())); // 进行Base64编码
			return uploadBuffer;

		} catch (Exception e) {
			e.printStackTrace();
		}
		// return soapObject;
		return null;

	}

	// 调用系统相册
	protected void startPick() {

		Intent intent = new Intent(Intent.ACTION_PICK, null);
		intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
				"image/*");
		startActivityForResult(intent, PHOTO_PICK);
	}

	// 调用系统相机
	protected void startCamera() {

		// 调用系统的拍照功能
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra("camerasensortype", 2); // 调用前置摄像头
		intent.putExtra("autofocus", true); // 自动对焦
		intent.putExtra("fullScreen", false); // 全屏
		intent.putExtra("showActionIcons", false);
		// 指定调用相机拍照后照片的存储路径
		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
		startActivityForResult(intent, PHOTO_CARMERA);
	}

	// 调用系统裁剪
	private void startPhotoZoom(Uri uri, int size) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		// crop为true是设置在开启的intent中设置显示的view可以裁剪
		intent.putExtra("crop", true);
		// aspectX,aspectY是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX,outputY是裁剪图片的宽高
		intent.putExtra("outputX", size);
		intent.putExtra("outputY", size);
		// 设置是否返回数据
		intent.putExtra("return-data", true);
		startActivityForResult(intent, PHOTO_CUT);
	}
}
