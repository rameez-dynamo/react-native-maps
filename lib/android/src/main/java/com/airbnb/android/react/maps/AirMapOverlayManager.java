package com.airbnb.android.react.maps;

import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.annotations.ReactProp;

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
}
