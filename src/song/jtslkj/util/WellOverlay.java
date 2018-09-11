package song.jtslkj.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.Toast;

import com.jtslkj.R;
import com.tianditu.android.maps.GeoPoint;
import com.tianditu.android.maps.MapView;
import com.tianditu.android.maps.MapViewRender;
import com.tianditu.android.maps.Overlay;
import com.tianditu.android.maps.renderoption.DrawableOption;

import javax.microedition.khronos.opengles.GL10;

public class WellOverlay extends Overlay {
    private Drawable mDrawable = null;
    private DrawableOption mOption = null;
    private GeoPoint mGeoPoint = null;
    private Context context;

    public WellOverlay(Context context, GeoPoint mGeoPoint, int state) {
        this.context = context;
        if (state == 0) {
            mDrawable = context.getResources().getDrawable(R.drawable.well_off);
        } else if (state == 1) {
            mDrawable = context.getResources().getDrawable(R.drawable.well_on);
        } else {
            mDrawable = context.getResources().getDrawable(R.drawable.well_down);
        }
        this.mGeoPoint = mGeoPoint;
        mOption = new DrawableOption();
        mOption.setAnchor(0.5f, 1.0f);
    }

    @Override
    public boolean onTap(GeoPoint point, MapView mapView) {
        mGeoPoint = point;
        Toast.makeText(context,
                (point.getLatitudeE6()+","+point.getLongitudeE6()),Toast.LENGTH_SHORT).show();
        return true;
    }

    @Override
    public void draw(GL10 gl, MapView mapView, boolean shadow) {
        if (shadow)
            return;
        MapViewRender render = mapView.getMapViewRender();
        render.drawDrawable(gl, mOption, mDrawable, mGeoPoint);
    }
}