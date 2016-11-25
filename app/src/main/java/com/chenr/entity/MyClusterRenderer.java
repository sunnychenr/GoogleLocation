package com.chenr.entity;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chenr.googlelocationdemo.R;
import com.chenr.utils.LogUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterItem;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;
import com.google.maps.android.ui.IconGenerator;
import com.google.maps.android.ui.SquareTextView;

/**
 * Created by ChenR on 2016/11/25.
 */

public class MyClusterRenderer extends DefaultClusterRenderer<MyClusterItem> implements ClusterManager.OnClusterClickListener<MyClusterItem>,
        ClusterManager.OnClusterInfoWindowClickListener<MyClusterItem>, ClusterManager.OnClusterItemClickListener<MyClusterItem>,
        ClusterManager.OnClusterItemInfoWindowClickListener<MyClusterItem> {

    private final int [] BUCKETS = new int[]{0, 5, 10, 20, 30, 40, 50, 70, 80, 90, 100};

    private GoogleMap map;
    private Context context;
    private IconGenerator mIconGenerator;

    public MyClusterRenderer(Context context, GoogleMap map, ClusterManager<MyClusterItem> clusterManager) {
        super(context, map, clusterManager);
        this.map = map;
        this.context = context;
        mIconGenerator = new IconGenerator(context);
        clusterManager.setOnClusterClickListener(this);
        clusterManager.setOnClusterInfoWindowClickListener(this);
        clusterManager.setOnClusterItemClickListener(this);
        clusterManager.setOnClusterItemInfoWindowClickListener(this);
    }

    @Override
    protected void onBeforeClusterRendered(Cluster<MyClusterItem> cluster, MarkerOptions markerOptions) {
        //mIconGenerator.setBackground(context.getResources().getDrawable(R.mipmap.wifis));
        //mIconGenerator.setContentView(makeSquareTextView());

        View view = View.inflate(context, R.layout.cluster, null);
        TextView tv = (TextView) view.findViewById(R.id.num);
        tv.setText(getClusterText(cluster));
        mIconGenerator.setContentView(view);
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(mIconGenerator.makeIcon()));
    }

    private SquareTextView makeSquareTextView() {
        SquareTextView tv = new SquareTextView(context);
        ViewGroup.LayoutParams params =  new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        tv.setTextSize(25.0f);
        tv.setTextColor(0x087b05);
        tv.setLayoutParams(params);
        tv.setGravity(Gravity.CENTER_VERTICAL);
        return tv;
    }

    private String getClusterText(Cluster<MyClusterItem> cluster) {
        int size = cluster.getSize();
        if (size < BUCKETS[1]) {
            return String.valueOf(size);
        }
        for (int i = 1; i < BUCKETS.length; i ++) {
            if (size > BUCKETS[i] && size < BUCKETS[i + 1]) {
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
        return false;
    }

    @Override
    public void onClusterItemInfoWindowClick(MyClusterItem myClusterItem) {
        LogUtil.log("onClusterItemInfoWindowClick... ...被调用");
    }
}
