//
//  AIRGoogleMapOverlay.h
//  Gronalund
//
//  Created by Rameez Hussain on 07/06/17.
//  Copyright © 2017 Facebook. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <GoogleMaps/GoogleMaps.h>
#import <React/RCTBridge.h>

@interface AIRGoogleMapOverlay : UIView

@property (nonatomic, weak) RCTBridge *bridge;
@property (nonatomic, copy) NSString *imageSrc;
@property (nonatomic, strong, readonly) UIImage *overlayImage;
@property (nonatomic, copy) NSArray *boundsRect;
@property (nonatomic, weak) GMSMapView *map;


@end
