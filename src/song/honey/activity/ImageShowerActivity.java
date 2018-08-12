package song.honey.activity;

import song.honey.util.AsyncLoadImageUtil;
import song.honey.util.ImageLoadingDialog;
import song.shuang.honey.R;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class ImageShowerActivity extends Activity {
	ImageView img;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.imageshower);
		img = (ImageView) findViewById(R.id.iv_imageloader);
		String imguri = getIntent().getStringExtra("imguri").toString().trim();

		if (!imguri.equals("")) {

			ImageLoader.getInstance().init(
					ImageLoaderConfiguration.createDefault(this));
			AsyncLoadImageUtil.loadImage(img, imguri, 5);// 加载图片
		}
		final ImageLoadingDialog dialog = new ImageLoadingDialog(this);
		dialog.setCanceledOnTouchOutside(false);
		dialog.show();
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				dialog.dismiss();
			}
		}, 1000 * 1);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		//finish();
		return true;
	}

}
