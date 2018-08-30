
package com.aliyun.vodplayerview;

import android.app.Activity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;
import com.facebook.react.bridge.JavaScriptModule;
public class RNAliyunPlayerPackage implements ReactPackage {

    @Override
    public List<NativeModule> createNativeModules(ReactApplicationContext reactContext) {
      return Arrays.<NativeModule>asList(new RNAliyunPlayerModule(reactContext),new AliyunVideoMoudle(reactContext));
    }

    // Deprecated from RN 0.47
    public List<Class<? extends JavaScriptModule>> createJSModules() {
      return Collections.emptyList();
    }

    public List<ViewManager> createViewManagers(ReactApplicationContext reactContext) {
        // Register your native component's view manager
        // https://facebook.github.io/react-native/docs/native-components-android.html#4-register-the-viewmanager
        return Arrays.<ViewManager>asList(
                new AliyunPlayManager(),
                new ALiVideoViewManager()
        );
    }
}