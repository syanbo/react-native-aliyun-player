//
//  AliyunPlayerView.m
//  AliyunPlayerDemo
//
//  Created by cookiej on 2018/7/22.
//  Copyright © 2018年 Facebook. All rights reserved.
//

#import "AliyunPlayerView.h"

@interface AliyunPlayerView ()

@property (nonatomic, strong) NSDictionary *prepareAsyncParams;
@property (nonatomic, strong) AliyunVodPlayer *aliPlayer;

@end

@implementation AliyunPlayerView

#pragma mark - Props config
- (void)setPrepareAsyncParams:(NSDictionary *)prepareAsyncParams {
  _prepareAsyncParams = prepareAsyncParams;
  [self setupAliPlayer];
}

- (void)setupAliPlayer {
  [self addSubview:self.aliPlayer.playerView];
  
  NSString *type = [_prepareAsyncParams objectForKey:@"type"];
  if ([type isEqualToString:@"vidSts"]) {
    NSString *vid = [_prepareAsyncParams objectForKey:@"vid"];
    NSString *accessKeyId = [_prepareAsyncParams objectForKey:@"accessKeyId"];
    NSString *accessKeySecret = [_prepareAsyncParams objectForKey:@"accessKeySecret"];
    NSString *securityToken = [_prepareAsyncParams objectForKey:@"securityToken"];
    [self.aliPlayer prepareWithVid:vid accessKeyId:accessKeyId accessKeySecret:accessKeySecret securityToken:securityToken];
  }
}

- (void) layoutSubviews {
  [super layoutSubviews];
  for(UIView* view in self.subviews) {
    [view setFrame:self.bounds];
  }
}

#pragma mark - 播放器初始化
-(AliyunVodPlayer *)aliPlayer{
  if (!_aliPlayer) {
    _aliPlayer = [[AliyunVodPlayer alloc] init];
    _aliPlayer.delegate = self;
    _aliPlayer.quality= 0;
    _aliPlayer.circlePlay = YES;
    _aliPlayer.autoPlay = NO;
    NSArray *pathArray = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES);
    NSString *docDir = [pathArray objectAtIndex:0];
    //maxsize: MB, maxDuration: second
    [_aliPlayer setPlayingCache:YES saveDir:docDir maxSize:3000 maxDuration:100000];
  }
  return _aliPlayer;
}

#pragma mark - AliyunVodPlayerDelegate
- (void)vodPlayer:(AliyunVodPlayer *)vodPlayer onEventCallback:(AliyunVodPlayerEvent)event{
  NSLog(@"onEventCallback: %ld", event);
//  self.onEventCallback(@{@"event": @(event)});
  //这里监控播放事件回调
  //主要事件如下：
  switch (event) {
    case AliyunVodPlayerEventPrepareDone:
      [_aliPlayer start];
      //播放准备完成时触发
      break;
    case AliyunVodPlayerEventPlay:
      //暂停后恢复播放时触发
      break;
    case AliyunVodPlayerEventFirstFrame:
      //播放视频首帧显示出来时触发
      break;
    case AliyunVodPlayerEventPause:
      //视频暂停时触发
      break;
    case AliyunVodPlayerEventStop:
      //主动使用stop接口时触发
      break;
    case AliyunVodPlayerEventFinish:
      //视频正常播放完成时触发
      break;
    case AliyunVodPlayerEventBeginLoading:
      //视频开始载入时触发
      break;
    case AliyunVodPlayerEventEndLoading:
      //视频加载完成时触发
      break;
    case AliyunVodPlayerEventSeekDone:
      //视频Seek完成时触发
      break;
    default:
      break;
  }
}

- (void)vodPlayer:(AliyunVodPlayer *)vodPlayer playBackErrorModel:(AliyunPlayerVideoErrorModel *)errorModel{
  //播放出错时触发，通过errorModel可以查看错误码、错误信息、视频ID、视频地址和requestId。
  NSLog(@"errorModel: %d", errorModel.errorCode);
}
- (void)vodPlayer:(AliyunVodPlayer*)vodPlayer willSwitchToQuality:(AliyunVodPlayerVideoQuality)quality videoDefinition:(NSString*)videoDefinition{
  //将要切换清晰度时触发
  NSLog(@"willSwitchToQuality:%@", videoDefinition);
}
- (void)vodPlayer:(AliyunVodPlayer *)vodPlayer didSwitchToQuality:(AliyunVodPlayerVideoQuality)quality videoDefinition:(NSString*)videoDefinition{
  //清晰度切换完成后触发
}
- (void)vodPlayer:(AliyunVodPlayer*)vodPlayer failSwitchToQuality:(AliyunVodPlayerVideoQuality)quality videoDefinition:(NSString*)videoDefinition{
  //清晰度切换失败触发
}
- (void)onCircleStartWithVodPlayer:(AliyunVodPlayer*)vodPlayer{
  //开启循环播放功能，开始循环播放时接收此事件。
}
- (void)onTimeExpiredErrorWithVodPlayer:(AliyunVodPlayer *)vodPlayer{
  //播放器鉴权数据过期回调，出现过期可重新prepare新的地址或进行UI上的错误提醒。
}
/*
 *功能：播放过程中鉴权即将过期时提供的回调消息（过期前一分钟回调）
 *参数：videoid：过期时播放的videoId
 *参数：quality：过期时播放的清晰度，playauth播放方式和STS播放方式有效。
 *参数：videoDefinition：过期时播放的清晰度，MPS播放方式时有效。
 *备注：使用方法参考高级播放器-点播。
 */
- (void)vodPlayerPlaybackAddressExpiredWithVideoId:(NSString *)videoId quality:(AliyunVodPlayerVideoQuality)quality videoDefinition:(NSString*)videoDefinition{
  //鉴权有效期为2小时，在这个回调里面可以提前请求新的鉴权，stop上一次播放，prepare新的地址，seek到当前位置
}

@end