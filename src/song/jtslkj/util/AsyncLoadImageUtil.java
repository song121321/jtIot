package song.jtslkj.util;

import com.jtslkj.R;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

public class AsyncLoadImageUtil {

	public static void loadImage(ImageView imageView, String url,
			int cornerRadiusPixels) {
		DisplayImageOptions options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.ic_launcher)
				.showImageForEmptyUri(R.drawable.ic_launcher)
				.showImageOnFail(R.drawable.ic_launcher).cacheInMemory(true)
				.cacheOnDisk(true).bitmapConfig(Bitmap.Config.RGB_565)
				.resetViewBeforeLoading(true)
				.displayer(new RoundedBitmapDisplayer(cornerRadiusPixels))
				.build();
		ImageLoader.getInstance().displayImage(url, imageView, options);
	}
}
