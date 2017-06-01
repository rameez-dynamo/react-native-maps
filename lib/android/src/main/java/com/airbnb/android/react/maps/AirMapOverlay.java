package com.airbnb.android.react.maps;

import android.content.Context;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.GroundOverlayOptions;

/**
 * Created by rameez on 01/06/17.
 */

public class AirMapOverlay extends AirMapFeature {

	private final Context context;
	private GroundOverlayOptions groundOverlayOptions;
	private GroundOverlay groundOverlay;

	public AirMapOverlay(Context context) {
		super(context);
		this.context = context;
	}

	@Override
	public void addToMap(GoogleMap map) {

	}

	@Override
	public void removeFromMap(GoogleMap map) {

	}

	@Override
	public Object getFeature() {
		return null;
	}

	public void setImage(String uri) {
	}
}
