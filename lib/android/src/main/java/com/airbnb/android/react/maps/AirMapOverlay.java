package com.airbnb.android.react.maps;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLngBounds;

/**
 * Created by rameez on 01/06/17.
 */

public class AirMapOverlay extends AirMapFeature {

	private final Context context;
	private GroundOverlayOptions groundOverlayOptions;
	private GroundOverlay groundOverlay;
	private String imageUri;
	private LatLngBounds bounds;

	public AirMapOverlay(Context context) {
		super(context);
		this.context = context;
	}

	@Override
	public void addToMap(GoogleMap map) {
		groundOverlay = map.addGroundOverlay(getGroundOverlayOptions());
	}

	@Override
	public void removeFromMap(GoogleMap map) {
		groundOverlay.remove();
		groundOverlay = null;
	}

	@Override
	public Object getFeature() {
		return groundOverlay;
	}

	public void setImage(String uri) {
		Log.d("Android", "Should set image - " + uri);
	}

	public void setBounds(LatLngBounds bounds) {
		Log.d("Android", "Setting bounds - " + bounds.toString());
		groundOverlayOptions = new GroundOverlayOptions()
				.positionFromBounds(bounds);
	}

	public GroundOverlayOptions getGroundOverlayOptions() {
		return groundOverlayOptions;
	}
}
