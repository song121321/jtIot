package song.jtslkj.activity;


import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import com.jtslkj.R;
import com.tianditu.android.maps.GeoPoint;
import com.tianditu.android.maps.MapController;
import com.tianditu.android.maps.MapView;
import com.tianditu.android.maps.TMapLayerManager;
import com.tianditu.android.maps.overlay.MarkerOverlay;
import com.tianditu.android.maps.overlay.PolygonOverlay;
import com.tianditu.android.maps.renderoption.PlaneOption;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import song.jtslkj.bean.WellBean;
import song.jtslkj.config.MyConfig;
import song.jtslkj.util.ParseTools;
import song.jtslkj.util.WebServiceUtil;
import song.jtslkj.util.WellOverlay;

public class GisActivity extends BaseActivity {

    MapView mMapView;
    TextView tvTopMiddle;
    GeoPoint center;
    private List<WellBean> wellBeans = new ArrayList<WellBean>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gis);
        findView();
        initView();
    }

    private void findView() {
        tvTopMiddle = (TextView) findViewById(R.id.tv_actionbar_usual_title);
        mMapView = (MapView) findViewById(R.id.mv_gis_map);
    }

    private void initView() {
        tvTopMiddle.setText(getResources().getString(R.string.gis_project_address));
        mMapView.setBuiltInZoomControls(true);
        MapController mMapController = mMapView.getController();
        center = new GeoPoint(36120400, 115869610);
        mMapView.setMapType(MapView.TMapType.MAP_TYPE_IMG);
        TMapLayerManager mLayerMnger = new TMapLayerManager(mMapView);
        mLayerMnger.setLayersShow(mLayerMnger.getLayers(MapView.TMapType.MAP_TYPE_IMG));
        mMapController.setCenter(center);
        mMapController.setZoom(12);
        new WellDownLoadTask().execute();
    }

    private class WellDownLoadTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... arg0) {
            HashMap<String, String> params = new HashMap<String, String>();
            String nameSpace = MyConfig.nameSpace;
            String methodName = MyConfig.methodName_GetWells;
            String endPoint = MyConfig.endPoint;
            return WebServiceUtil.getAnyType(nameSpace, methodName,
                    endPoint, params);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            wellBeans = ParseTools.json2WellBeanList(result);
            for (WellBean wb : wellBeans) {
                mMapView.addOverlay(new WellOverlay(GisActivity.this, wb.getPoint(), wb.getState()));
            }

            mMapView.addOverlay(new WellOverlay(GisActivity.this, center, 1));

            ArrayList<GeoPoint> points = new ArrayList<GeoPoint>();

            for (WellBean wb : wellBeans) {
                points.add(wb.getPoint());
            }


            PlaneOption polygonOption = new PlaneOption();
            polygonOption.setStrokeColor(getResources().getColor(R.color.btn_blue_pressed));
            polygonOption.setFillColor(getResources().getColor(R.color.map_filled));
            polygonOption.setStrokeWidth(5);
            PolygonOverlay polygonOverlay = new PolygonOverlay();
            polygonOverlay.setOption(polygonOption);
            polygonOverlay.setPoints(points);
            mMapView.addOverlay(polygonOverlay);


        }
    }
}
