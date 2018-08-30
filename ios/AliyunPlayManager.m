//  Created by react-native-create-bridge
#import <Foundation/Foundation.h>
#import "AliyunPlayManager.h"
#import "AliyunPlayerView.h"

#if __has_include(<React/RCTBridge.h>)
#import <React/RCTBridge.h>
#elif __has_include(“RCTBridge.h”)
#import “RCTBridge.h”
#else
#import “React/RCTBridge.h” // Required when used as a Pod in a Swift project
#endif

@implementation AliyunPlayManager

@synthesize bridge = _bridge;

RCT_EXPORT_MODULE();

RCT_EXPORT_VIEW_PROPERTY(prepareAsyncParams, NSDictionary)
RCT_EXPORT_VIEW_PROPERTY(onEventCallback, RCTBubblingEventBlock)
RCT_EXPORT_VIEW_PROPERTY(onPlayingCallback, RCTBubblingEventBlock)

- (UIView *)view
{
  return [AliyunPlayerView new];
}

@end
