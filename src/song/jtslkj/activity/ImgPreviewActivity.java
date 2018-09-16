package song.jtslkj.activity;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jtslkj.R;

import java.util.List;

public class ImgPreviewActivity extends Activity {

    ViewPager vp;
    TextView tv_position;
    List<String> imgList;
    int mPosition;
    LayoutInflater inflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_img_page);
        Bundle b = getIntent().getBundleExtra("b");
        imgList = b.getStringArrayList("imglist");
        mPosition=b.getInt("position");
        inflater = LayoutInflater.from(this);
        initView();
        initViewPaper();
    }

    private void initView() {
        vp = (ViewPager) findViewById(R.id.vp);
        tv_position = (TextView) findViewById(R.id.tv_position);
        tv_position.setText((mPosition+1)+"/" + imgList.size());
    }

    private void initViewPaper() {
        vp.setAdapter(new ImagePagerAdapter(imgList));
        vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mPosition = position;
                tv_position.setText((mPosition + 1) + "/" + imgList.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        vp.setCurrentItem(mPosition);
    }

    private class ImagePagerAdapter extends PagerAdapter {

        private List<String> images;
        private LayoutInflater inflater;

        ImagePagerAdapter(List<String> images) {
            this.images = images;
            inflater = getLayoutInflater();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return images.size();
        }

        @Override
        public Object instantiateItem(ViewGroup view, final int position) {
            View imageLayout = inflater.inflate(R.layout.item_pager_image, view, false);
            assert imageLayout != null;
            final ProgressBar progressBar = (ProgressBar) imageLayout.findViewById(R.id.loading);
            ImageView image = (ImageView) imageLayout.findViewById(R.id.image);
            Glide.with(ImgPreviewActivity.this)
                    .load(imgList.get(position))
                    .into(image);
            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
            view.addView(imageLayout, 0);
            return imageLayout;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view.equals(object);
        }
    }

}
