package com.airbnb.android.react.maps;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.util.Log;

import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.DataSource;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.DraweeHolder;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.image.CloseableStaticBitmap;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLngBounds;

import javax.annotation.Nullable;

/**
 * Created by rameez on 01/06/17.
 */

public class AirMapOverlay extends AirMapFeature {

	private final Context context;
	private GroundOverlayOptions groundOverlayOptions;
	private GroundOverlay groundOverlay;
	private LatLngBounds bounds;
	private GoogleMap googleMap;

	private DraweeHolder<?> logoHolder;
	private DataSource<CloseableReference<CloseableImage>> dataSource;
	private BitmapDescriptor iconBitmapDescriptor;
	private Bitmap iconBitmap;
	private ControllerListener<ImageInfo> mLogoControllerListener =
			new BaseControllerListener<ImageInfo>() {
				@Override
				public void onFinalImageSet(
						String id,
						@Nullable final ImageInfo imageInfo,
						@Nullable Animatable animatable) {
					CloseableReference<CloseableImage> imageReference = null;
					try {
						imageReference = dataSource.getResult();
						if (imageReference != null) {
							CloseableImage image = imageReference.get();
							if (image != null && image instanceof CloseableStaticBitmap) {
								CloseableStaticBitmap closeableStaticBitmap = (CloseableStaticBitmap) image;
								Bitmap bitmap = closeableStaticBitmap.getUnderlyingBitmap();
								if (bitmap != null) {
									bitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);
									iconBitmap = bitmap;
									iconBitmapDescriptor = BitmapDescriptorFactory.fromBitmap(bitmap);
								}
							}
						}
					} finally {
						dataSource.close();
						if (imageReference != null) {
							CloseableReference.closeSafely(imageReference);
						}
						update();
					}
				}
			};

	public AirMapOverlay(Context context) {
		super(context);
		this.context = context;
		logoHolder = DraweeHolder.create(createDraweeHierarchy(), context);
		logoHolder.onAttach();
	}

	private GenericDraweeHierarchy createDraweeHierarchy() {
		return new GenericDraweeHierarchyBuilder(getResources())
				.setActualImageScaleType(ScalingUtils.ScaleType.FIT_CENTER)
				.setFadeDuration(0)
				.build();
	}

	private void update() {
		try {
			groundOverlayOptions = new GroundOverlayOptions()
					.image(iconBitmapDescriptor)
					.positionFromBounds(bounds);
			groundOverlay = googleMap.addGroundOverlay(groundOverlayOptions);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void addToMap(GoogleMap map) {
		this.googleMap = map;
		update();
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
		Log.d("Overlay", "Should set image - " + uri);
		if (uri == null) {
			iconBitmapDescriptor = null;
			update();
		} else if (uri.startsWith("http://") || uri.startsWith("https://") ||
				uri.startsWith("file://")) {
			ImageRequest imageRequest = ImageRequestBuilder
					.newBuilderWithSource(Uri.parse(uri))
					.build();

			ImagePipeline imagePipeline = Fresco.getImagePipeline();
			dataSource = imagePipeline.fetchDecodedImage(imageRequest, this);
			DraweeController controller = Fresco.newDraweeControllerBuilder()
					.setImageRequest(imageRequest)
					.setControllerListener(mLogoControllerListener)
					.setOldController(logoHolder.getController())
					.build();
			logoHolder.setController(controller);
		} else {
			iconBitmapDescriptor = getBitmapDescriptorByName(uri);
			update();
		}
	}

	public void setBounds(LatLngBounds bounds) {
		Log.d("Overlay", "Setting bounds - " + bounds.toString());
		this.bounds = bounds;
		update();
	}

	public GroundOverlayOptions getGroundOverlayOptions() {
		return groundOverlayOptions;
	}

	private int getDrawableResourceByName(String name) {
		return getResources().getIdentifier(
				name,
				"drawable",
				getContext().getPackageName());
	}

	private BitmapDescriptor getBitmapDescriptorByName(String name) {
		return BitmapDescriptorFactory.fromResource(getDrawableResourceByName(name));
	}
}
