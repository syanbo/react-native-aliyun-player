//  Created by react-native-create-bridge
#import <Foundation/Foundation.h>
#import "AliyunPlayManager.h"

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
RCT_EXPORT_VIEW_PROPERTY(muteMode, BOOL)
RCT_EXPORT_VIEW_PROPERTY(quality, NSInteger)
RCT_EXPORT_VIEW_PROPERTY(volume, float)
RCT_EXPORT_VIEW_PROPERTY(brightness, float)

RCT_EXPORT_VIEW_PROPERTY(onGetAliyunMediaInfo, RCTBubblingEventBlock)
RCT_EXPORT_VIEW_PROPERTY(onEventCallback, RCTBubblingEventBlock)
RCT_EXPORT_VIEW_PROPERTY(onPlayingCallback, RCTBubblingEventBlock)
RCT_EXPORT_VIEW_PROPERTY(onPlayBackErrorModel, RCTBubblingEventBlock)
RCT_EXPORT_VIEW_PROPERTY(onSwitchToQuality, RCTBubblingEventBlock)



- (UIView *)view
{
    AliyunPlayerView *playerView = [AliyunPlayerView new];
    self.playerView = playerView;
    return playerView;
}

#pragma mark - 开始播放
RCT_EXPORT_METHOD(start) {
   [self.playerView.aliPlayer start];
}

RCT_EXPORT_METHOD(resume) {
    [self.playerView.aliPlayer resume];
}

RCT_EXPORT_METHOD(pause) {
   [self.playerView.aliPlayer pause];
}

RCT_EXPORT_METHOD(stop) {
    [self.playerView.aliPlayer stop];
}

RCT_EXPORT_METHOD(seekToTime:(double)time) {
    [self.playerView.aliPlayer seekToTime: time];
}

RCT_EXPORT_METHOD(playerState) {
    
}

//getAliyunMediaInfo

- (dispatch_queue_t)methodQueue
{
    return dispatch_get_main_queue();
}

+ (BOOL)requiresMainQueueSetup
{
    return YES;
}

@end
