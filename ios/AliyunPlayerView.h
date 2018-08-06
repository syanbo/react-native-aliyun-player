//
//  AliyunPlayerView.h
//  AliyunPlayerDemo
//
//  Created by cookiej on 2018/7/22.
//  Copyright © 2018年 Facebook. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <React/RCTComponent.h>
#import <AliyunVodPlayerSDK/AliyunVodPlayerSDK.h>

@interface AliyunPlayerView : UIView <AliyunVodPlayerDelegate>

@property (nonatomic, strong) AliyunVodPlayer *aliPlayer;

@property (nonatomic, copy) RCTBubblingEventBlock onEventCallback;
@property (nonatomic, copy) RCTBubblingEventBlock onGetAliyunMediaInfo;
@property (nonatomic, copy) RCTBubblingEventBlock onPlayingCallback;
@property (nonatomic, copy) RCTBubblingEventBlock onSwitchToQuality;
@property (nonatomic, copy) RCTBubblingEventBlock onPlayBackErrorModel;


@end
