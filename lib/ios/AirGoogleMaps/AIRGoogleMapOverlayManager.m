//
//  AIRGoogleMapOverlayManager.m
//  Gronalund
//
//  Created by Rameez Hussain on 07/06/17.
//  Copyright © 2017 Facebook. All rights reserved.
//

#import "AIRGoogleMapOverlayManager.h"
#import "AIRGoogleMapOverlay.h"
#import <MapKit/MapKit.h>
#import <React/RCTUIManager.h>
#import "RCTConvert+AirMap.h"

@implementation AIRGoogleMapOverlayManager

RCT_EXPORT_MODULE()

- (UIView *)view
{
  AIRGoogleMapOverlay *overlay = [AIRGoogleMapOverlay new];
  overlay.bridge = self.bridge;
  return overlay;
}

RCT_REMAP_VIEW_PROPERTY(bounds, boundsRect, NSArray)
RCT_REMAP_VIEW_PROPERTY(image, imageSrc, NSString)

@end
