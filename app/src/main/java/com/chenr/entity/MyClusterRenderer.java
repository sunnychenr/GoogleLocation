package com.chenr.entity;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v7.widget.DrawableUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.chenr.googlelocationdemo.MainActivity;
import com.chenr.googlelocationdemo.R;
import com.chenr.http.AddressAnalysisRunn;
import com.chenr.http.GetWayRunn;
import com.chenr.utils.LogUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterItem;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;
import com.google.maps.android.ui.IconGenerator;
import com.google.maps.android.ui.SquareTextView;

import org.w3c.dom.Text;

/**
 * Created by ChenR on 2016/11/25.
 */

public class MyClusterRenderer extends DefaultClusterRenderer<MyClusterItem> implements ClusterManager.OnClusterClickListener<MyClusterItem>,
        ClusterManager.OnClusterInfoWindowClickListener<MyClusterItem>, ClusterManager.OnClusterItemClickListener<MyClusterItem>,
        ClusterManager.OnClusterItemInfoWindowClickListener<MyClusterItem> {

    private final int [] BUCKETS = new int[]{0, 5, 10, 20, 30, 40, 50, 70, 80, 90};

    private GoogleMap map;
    private Context context;
    private IconGenerator mIconGenerator;
    private Handler mHandler;
    private ClusterManager<MyClusterItem> mClusterManager;

    public MyClusterRenderer(Context context, GoogleMap map, ClusterManager<MyClusterItem> mClusterManager, Handler mHandler) {
        super(context, map, mClusterManager);
        this.map = map;
        this.context = context;
        this.mHandler = mHandler;
        this.mClusterManager = mClusterManager;
        mIconGenerator = new IconGenerator(context);
        mClusterManager.setOnClusterClickListener(this);
        mClusterManager.setOnClusterInfoWindowClickListener(this);
        mClusterManager.setOnClusterItemClickListener(this);
        mClusterManager.setOnClusterItemInfoWindowClickListener(this);
    }

    @Override
    protected void onBeforeClusterRendered(Cluster<MyClusterItem> cluster, MarkerOptions markerOptions) {
        View view = View.inflate(context, R.layout.cluster, null);
        TextView tv = (TextView) view.findViewById(R.id.num);
        tv.setText(getClusterText(cluster));
        mIconGenerator.setBackground(context.getResources().getDrawable(R.mipmap.wifis));
        mIconGenerator.setContentView(view);
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(mIconGenerator.makeIcon()));
    }

    private String getClusterText(Cluster<MyClusterItem> cluster) {
        int size = cluster.getSize();

        for (int i = 1; i < BUCKETS.length; i ++) {
            if (size >= BUCKETS[i] && size < BUCKETS[i + 1]) {
                return String.valueOf(BUCKETS[i]) + "+";
            }
        }
        return String.valueOf(cluster.getSize());
    }

    @Override
    protected void onBeforeClusterItemRendered(MyClusterItem item, MarkerOptions markerOptions) {
        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.wifi));
    }

    @Override
    public boolean onClusterClick(Cluster<MyClusterItem> cluster) {
        LogUtil.log("onClusterClick... ...被调用");
        // Zoom in the cluster. Need to create LatLngBounds and including all the cluster items
        // inside of bounds, then animate to center of the bounds.

        // Create the builder to collect all essential cluster items for the bounds.
        LatLngBounds.Builder builder = LatLngBounds.builder();
        for (ClusterItem item : cluster.getItems()) {
            builder.include(item.getPosition());
        }
        // Get the LatLngBounds
        final LatLngBounds bounds = builder.build();

        // Animate camera to the bounds
        try {
            map.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 80));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }

    @Override
    public void onClusterInfoWindowClick(Cluster<MyClusterItem> cluster) {
        LogUtil.log("onClusterInfoWindowClick... ...被调用");
    }

    @Override
    public boolean onClusterItemClick(MyClusterItem myClusterItem) {
        LogUtil.log("onClusterItemClick... ...被调用");
        final LatLng position = myClusterItem.getPosition();
        String msg = myClusterItem.getMsg();
        String title = msg.substring(0, msg.indexOf("&"));
        String dist = msg.substring(msg.indexOf("&") + 1, msg.indexOf(".")) + "m";

        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        View view = View.inflate(context, R.layout.dialog_layout, null);
        TextView tv_title = (TextView) view.findViewById(R.id.dialog_title);
        TextView tv_addr = (TextView) view.findViewById(R.id.dialog_address);
        Button btn_dist = (Button) view.findViewById(R.id.btn_dist);

        tv_title.setText(title);
        btn_dist.setText(dist);

        new Thread(new AddressAnalysisRunn(position, mHandler, tv_addr)).start();

        dialog.setView(view);
        final AlertDialog show = dialog.show();

        btn_dist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                map.clear();
                MainActivity.mLatlngs.clear();
                new Thread(new GetWayRunn(mHandler, MainActivity.currentLocation, position, 1)).start();
                addMarkerItems();
                show.dismiss();
            }
        });

        return true;
    }

    private void addMarkerItems() {
        LogUtil.log("addMarkerItems ------> 被调用");
        mClusterManager.clearItems();
        for (LocalWiFi.DataBean dataBean: MainActivity.wifis) {
            mClusterManager.addItem(new MyClusterItem(dataBean.getLati(), dataBean.getLongi(),
                    dataBean.getSsid() + "&" + dataBean.getDist()));
        }
        mClusterManager.cluster();
    }

    @Override
    public void onClusterItemInfoWindowClick(MyClusterItem myClusterItem) {
        LogUtil.log("onClusterItemInfoWindowClick... ...被调用");
    }
}
