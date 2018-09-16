package song.jtslkj.activity;


import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import com.jtslkj.R;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tianditu.android.maps.GeoPoint;
import com.tianditu.android.maps.MapController;
import com.tianditu.android.maps.MapView;
import com.tianditu.android.maps.TMapLayerManager;
import com.tianditu.android.maps.overlay.MarkerOverlay;
import com.tianditu.android.maps.overlay.PolygonOverlay;
import com.tianditu.android.maps.renderoption.DrawableOption;
import com.tianditu.android.maps.renderoption.PlaneOption;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import song.jtslkj.bean.WellBean;
import song.jtslkj.config.MyConfig;
import song.jtslkj.util.ParseUtil;
import song.jtslkj.util.WebServiceUtil;

public class GisActivity extends BaseActivity implements MarkerOverlay.OnMarkerClickListener {

    private RefreshLayout mRefreshLayout;
    MapView mMapView;
    TextView tvTopMiddle;
    GeoPoint center;
    private List<WellBean> wellBeans = new ArrayList<>();
    private Map<String, WellBean> wellBeanMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gis);
        findView();
        initView();
    }

    private void findView() {
        mRefreshLayout = (RefreshLayout) findViewById(R.id.refreshLayout);
        tvTopMiddle = (TextView) findViewById(R.id.tv_actionbar_usual_title);
        mMapView = (MapView) findViewById(R.id.mv_gis_map);
    }

    private void initView() {
        tvTopMiddle.setText(getResources().getString(R.string.gis_project_address));
        mMapView.setBuiltInZoomControls(false);
        MapController mMapController = mMapView.getController();
        center = new GeoPoint(36120400, 115869610);
        mMapView.setMapType(MapView.TMapType.MAP_TYPE_IMG);
        TMapLayerManager mLayerMnger = new TMapLayerManager(mMapView);
        mLayerMnger.setLayersShow(mLayerMnger.getLayers(MapView.TMapType.MAP_TYPE_IMG));
        mMapController.setCenter(center);
        mMapController.setZoom(12);
        new WellDownLoadTask().execute();
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                new WellDownLoadTask().execute();
                refreshlayout.finishRefresh();
            }
        });
    }

    @Override
    public boolean onMarkerClick(MarkerOverlay markerOverlay) {

        Intent intent = new Intent();
        intent.setClass(GisActivity.this,
                DeviceWellDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("wellBean", wellBeanMap.get(markerOverlay.getTitle()));
        intent.putExtras(bundle);
        this.startActivity(intent);
        return false;
    }

    private class WellDownLoadTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... arg0) {
            HashMap<String, String> params = new HashMap<>();
            String nameSpace = MyConfig.nameSpace;
            String methodName = MyConfig.methodName_GetWells;
            String endPoint = MyConfig.endPoint;
            return WebServiceUtil.getAnyType(nameSpace, methodName,
                    endPoint, params);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            wellBeans = ParseUtil.json2WellBeanList(result);
            wellBeanMap = new HashMap<>();
            for (WellBean wellBean : wellBeans) {
                wellBeanMap.put(wellBean.getWellName(), wellBean);
            }
//            for (WellBean wb : wellBeans) {
//                mMapView.addOverlay(new WellOverlay(GisActivity.this, wb.getPoint(), wb.getState()));
//            }
//
//            mMapView.addOverlay(new WellOverlay(GisActivity.this, center, 1));

            ArrayList<GeoPoint> points = new ArrayList<>();

            for (WellBean wb : wellBeans) {
                points.add(new GeoPoint(wb.getPointLan(), wb.getPointLon()));
            }

            drawIcon();
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

    private void drawIcon() {
        for (int i = 0; i < wellBeans.size(); ++i) {
            GeoPoint geoPoint = new GeoPoint(wellBeans.get(i).getPointLan(), wellBeans.get(i).getPointLon());
            DrawableOption option = new DrawableOption();
            option.setAnchor(0.5f, 0.5f);
            MarkerOverlay mMarker = new MarkerOverlay();
            Drawable mDrawable;
            if (wellBeans.get(i).getState() == 0) {
                mDrawable = getResources().getDrawable(R.drawable.well_off);
            } else if (wellBeans.get(i).getState() == 1) {
                mDrawable = getResources().getDrawable(R.drawable.well_on);
            } else {
                mDrawable = getResources().getDrawable(R.drawable.well_down);
            }
            mMarker.setClickListener(this);
            mMarker.setOption(option);
            mMarker.setPosition(geoPoint);
            mMarker.setIcon(mDrawable);
            mMarker.setTitle(wellBeans.get(i).getWellName());
            mMapView.addOverlay(mMarker);
        }
    }


}