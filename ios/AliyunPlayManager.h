//
//  AliyunPlayManager.h
//  AliyunPalyDemo
//
//  Created by 邓博 on 2018/7/4.
//  Copyright © 2018年 Facebook. All rights reserved.
//

// import RCTViewManager
#if __has_include(<React/RCTViewManager.h>)
#import <React/RCTViewManager.h>
#elif __has_include(“RCTViewManager.h”)
#import “RCTViewManager.h”
#else
#import “React/RCTViewManager.h” // Required when used as a Pod in a Swift project
#endif

// Subclass your view manager off the RCTViewManager
// http://facebook.github.io/react-native/docs/native-components-ios.html#ios-mapview-example
@interface AliyunPlayManager : RCTViewManager

@end
