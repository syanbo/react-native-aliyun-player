//  Created by react-native-create-bridge
#import <Foundation/Foundation.h>
#import "AliyunPlayManager.h"

// import RCTBridge
#if __has_include(<React/RCTBridge.h>)
#import <React/RCTBridge.h>
#elif __has_include(“RCTBridge.h”)
#import “RCTBridge.h”
#else
#import “React/RCTBridge.h” // Required when used as a Pod in a Swift project
#endif

static BOOL s_autoPlay = NO;
@interface AliyunPlayManager ()

@property (nonatomic, copy) RCTBubblingEventBlock onEventCallback;
@property(nonatomic ,strong)UIView *playerView;
@property(nonatomic ,strong)AliyunVodPlayer *aliPlayer;

@end

@implementation AliyunPlayManager

@synthesize bridge = _bridge;


// Export a native module
// https://facebook.github.io/react-native/docs/native-modules-ios.html
RCT_EXPORT_MODULE();

RCT_EXPORT_VIEW_PROPERTY(exampleProp, NSString)
//RCT_EXPORT_VIEW_PROPERTY(onEventCallback, RCTBubblingEventBlock)

// Export constants
// https://facebook.github.io/react-native/releases/next/docs/native-modules-ios.html#exporting-constants
- (NSDictionary *)constantsToExport
{
  return @{
           @"EXAMPLE_CONSTANT": @"example"
           };
}

#pragma mark - 播放器初始化
-(AliyunVodPlayer *)aliPlayer{
  if (!_aliPlayer) {
    _aliPlayer = [[AliyunVodPlayer alloc] init];
    _aliPlayer.delegate = self;
    [_aliPlayer setAutoPlay:s_autoPlay];
    _aliPlayer.quality=  0;
    //        [_aliPlayer setPrintLog:YES];
    _aliPlayer.circlePlay = YES;
    NSArray *pathArray = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES);
    NSString *docDir = [pathArray objectAtIndex:0];
    //maxsize MB    maxDuration 秒
    [_aliPlayer setPlayingCache:YES saveDir:docDir maxSize:3000 maxDuration:100000];
  }
  return _aliPlayer;
}

- (UIView *)playerView{
  if (!_playerView) {
    _playerView = [[UIView alloc] init];
  }
  return  _playerView;
}

// Return the native view that represents your React component
- (UIView *)view
{
   self.playerView = self.aliPlayer.playerView;
  
  [_aliPlayer prepareWithVid:@"04aa91c809c741178d76af4b95f1b680" accessKeyId:@"LTAIH1j0TQwkhfjC" accessKeySecret:@"M8wXRq0Vq03JY8jHdMddCupyAPNvJs" securityToken:@"eyJTZWN1cml0eVRva2VuIjoiQ0FJUzN3SjFxNkZ0NUIyeWZTaklyNG5kQjhuT25JZ1cvSWFLWldYbTEwUS9ZN2g5cWEzZHFEejJJSHBOZTNocUIrMGZzUGt3bEdsVTZmZ2Nsck1xRnNjZkhoYWVONUVxdE1RUHExUDRKcExGc3QySjZyOEpqc1ZkcE1OazNscXBzdlhKYXNEVkVma3VFNVhFTWlJNS8wMGU2TC8rY2lyWVhEN0JHSmFWaUpsaFE4MEtWdzJqRjFSdkQ4dFhJUTBRazYxOUszemRaOW1nTGlidWkzdnhDa1J2MkhCaWptOHR4cW1qL015UTV4MzFpMXYweStCM3dZSHRPY3FjYThCOU1ZMVdUc3Uxdm9oemFyR1Q2Q3BaK2psTStxQVU2cWxZNG1YcnM5cUhFa0ZOd0JpWFNaMjJsT2RpTndoa2ZLTTNOcmRacGZ6bjc1MUN0L2ZVaXA3OHhtUW1YNGdYY1Z5R0ZkMzhtcE9aUXJ6eGFvWmdLZStxQVJtWGpJRFRiS3VTbWhnL2ZIY1dPRGxOZjljY01YSnFBWFF1TUdxQWMvRDJvZzZYTzFuK0ZQamNqUDVvajRBSjVsSHA3TWVNR1YrRGVMeVF5aDBFSWFVN2EwNDQxTUtpUXVranBzUWFnQUdUcTdBbVZMOWtuV2dzMXVzd0o2bHNXZWVzaUVKU2owUmROa01ySkVOejI3R0FWdUYrVzFZQkRGNVA1dFBsYk45ZDMreE02QkQyTHJVdUdMT1dCbXE1b2JyOVJmeW95MTBNZ2FFS1NObVI0VUl1dXFSdjdweFFscnFWNmlBcGZZR0NaV1VxM0JTQW5nM0VLY1hoS1QyeTZVbVBSYW8wait5Tkk1d2o4cC8zdFE9PSIsIkF1dGhJbmZvIjoie1wiQ2FsbGVyXCI6XCJZMXN3bnBGK2RGL2g4RVVLeTBSU0xOcHVWMGxnQ3ZvSXNyUU1PcE9ROXRRPVxcclxcblwiLFwiRXhwaXJlVGltZVwiOlwiMjAxOC0wNi0wNlQwNjoxNDozMVpcIixcIk1lZGlhSWRcIjpcIjhlODgwZjRlNjg1MjQ4MzY4ZDZjMDgyMjJlNzlmMmYyXCIsXCJTaWduYXR1cmVcIjpcIkpqaHhvcmRqSVFXNXRqZVJoRW1zemNjRmVOMD1cIn0iLCJWaWRlb01ldGEiOnsiU3RhdHVzIjoiTm9ybWFsIiwiVmlkZW9JZCI6IjhlODgwZjRlNjg1MjQ4MzY4ZDZjMDgyMjJlNzlmMmYyIiwiVGl0bGUiOiLor77nqIvlvJXlhaXigJTigJTnmb3ph5Hov5jmmK"];
  
  return self.playerView;
}

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
