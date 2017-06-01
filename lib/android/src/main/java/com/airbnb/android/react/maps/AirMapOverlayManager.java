package com.airbnb.android.react.maps;

import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import java.lang.reflect.Array;

import javax.annotation.Nullable;

/**
 * Created by rameez on 01/06/17.
 */

public class AirMapOverlayManager extends ViewGroupManager<AirMapOverlay> {

	public AirMapOverlayManager() {
	}

	@Override
	public String getName() {
		return "AIRMapOverlay";
	}

	@Override
	public AirMapOverlay createViewInstance(ThemedReactContext context) {
		return new AirMapOverlay(context);
	}

	@ReactProp(name = "image")
	public void setImage(AirMapOverlay view, @Nullable String source) {
		view.setImage(source);
	}

	@ReactProp(name = "bounds")
	public void setBounds(AirMapOverlay view, @Nullable ReadableArray array) {
		try {
			ReadableArray neBounds = array.getArray(0);
			ReadableArray swBounds = array.getArray(1);
			Double neLat = neBounds.getDouble(0),
					neLng = neBounds.getDouble(1),
					swLat = swBounds.getDouble(0),
					swLng = swBounds.getDouble(1);
			LatLng northEastBounds = new LatLng(neLat, neLng);
			LatLng southWestBounds = new LatLng(swLat, swLng);
			LatLngBounds bounds = new LatLngBounds(northEastBounds, southWestBounds);
			view.setBounds(bounds);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
