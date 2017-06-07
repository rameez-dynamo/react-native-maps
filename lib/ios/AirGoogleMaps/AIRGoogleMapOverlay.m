//
//  AIRGoogleMapOverlay.m
//  Gronalund
//
//  Created by Rameez Hussain on 07/06/17.
//  Copyright © 2017 Facebook. All rights reserved.
//

#import "AIRGoogleMapOverlay.h"
#import <React/RCTBridge.h>
#import <React/RCTEventDispatcher.h>
#import <React/RCTImageLoader.h>
#import <React/RCTUtils.h>
#import <React/UIView+React.h>


@interface AIRGoogleMapOverlay ()
  @property (nonatomic, strong, readwrite) UIImage *overlayImage;
@end

@implementation AIRGoogleMapOverlay {
  RCTImageLoaderCancellationBlock _reloadImageCancellationBlock;
  CLLocationCoordinate2D _topRightCoordinate;
  CLLocationCoordinate2D _bottomLeftCoordinate;
}

- (instancetype)init
{
  if ((self = [super init])) {
  }
  return self;
}

- (void)setImageSrc:(NSString *)imageSrc
{
  NSLog(@">>> AIRGoogleMapOverlay SET IMAGESRC: %@", imageSrc);
  _imageSrc = imageSrc;
  
  if (_reloadImageCancellationBlock) {
    _reloadImageCancellationBlock();
    _reloadImageCancellationBlock = nil;
  }
  __weak typeof(self) weakSelf = self;
  _reloadImageCancellationBlock = [_bridge.imageLoader loadImageWithURLRequest:[RCTConvert NSURLRequest:_imageSrc]
                                                                          size:weakSelf.bounds.size
                                                                         scale:RCTScreenScale()
                                                                       clipped:YES
                                                                    resizeMode:RCTResizeModeCenter
                                                                 progressBlock:nil
                                                              partialLoadBlock:nil
                                                               completionBlock:^(NSError *error, UIImage *image) {
                                                                 if (error) {
                                                                   NSLog(@"%@", error);
                                                                 }
                                                                 dispatch_async(dispatch_get_main_queue(), ^{
                                                                   NSLog(@">>> IMAGE: %@", image);
                                                                   weakSelf.overlayImage = image;
                                                                   [weakSelf update];
                                                                 });
                                                               }];
}

- (void)setBoundsRect:(NSArray *)boundsRect {
  NSLog(@">>> AIRGoogleMapOverlay SET BOUNDS RECT: %@", boundsRect);
  _boundsRect = boundsRect;
  
  _bottomLeftCoordinate = CLLocationCoordinate2DMake([boundsRect[0][0] doubleValue], [boundsRect[0][1] doubleValue]);
  _topRightCoordinate = CLLocationCoordinate2DMake([boundsRect[1][0] doubleValue], [boundsRect[1][1] doubleValue]);
  
  [self update];
}

- (void)update
{
//  if (!_renderer) return;
//  
  if (_map == nil) return;
  GMSCoordinateBounds *overlayBounds = [[GMSCoordinateBounds alloc] initWithCoordinate:_bottomLeftCoordinate
                                                                            coordinate:_topRightCoordinate];
  GMSGroundOverlay *overlay = [GMSGroundOverlay groundOverlayWithBounds:overlayBounds
                                                                   icon:_overlayImage];
  overlay.map = _map;
}


@end
