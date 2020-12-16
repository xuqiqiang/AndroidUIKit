[![](https://jitpack.io/v/xuqiqiang/AndroidUIKit.svg)](https://jitpack.io/#xuqiqiang/AndroidUIKit)

# AndroidUIKit
Android常用工具库

```
AndroidUIKit
│  .gitignore
│  build.gradle
│  proguard-rules.pro
│
└─src
    ├─androidTest
    ├─main
    │  │  AndroidManifest.xml
    │  │
    │  ├─java
    │  │  └─com
    │  │      └─xuqiqiang
    │  │          └─uikit
    │  │              ├─activity
    │  │              │      ActivityUtils.java
    │  │              │      BaseAppActivity.java
    │  │              │      BaseAppCompatActivity.java
    │  │              │      BaseThemeActivity.java
    │  │              │
    │  │              ├─fragment
    │  │              │      BaseFragment.java
    │  │              │      FragmentAdapter.java
    │  │              │      ViewPagerAdapter.java
    │  │              │
    │  │              ├─gesture
    │  │              │      Gesture.java
    │  │              │      GestureView.java
    │  │              │      PullRefreshGesture.java
    │  │              │      ScrollableGesture.java
    │  │              │      ViewGroupGesture.java
    │  │              │
    │  │              ├─utils
    │  │              │  │  AnimUtils.java // 动画相关工具
    │  │              │  │  ApplicationUtils.java // 应用相关工具
    │  │              │  │  ArrayUtils.java // 列表相关工具
    │  │              │  │  BitmapUtils.java // Bitmap相关工具
    │  │              │  │  Cache.java // 缓存相关工具
    │  │              │  │  DeviceUtils.java // 设备信息相关工具
    │  │              │  │  DisplayUtils.java // DisplayMetrics相关工具
    │  │              │  │  FileUtils.java // 文件相关工具
    │  │              │  │  ImageLoader.java // Bitmap缓存加载器
    │  │              │  │  ImageUtils.java // Bitmap处理方法
    │  │              │  │  IntentUtils.java // 各种常用的Intent
    │  │              │  │  KeyboardManager.java // 输入法管理器
    │  │              │  │  LanguageUtils.java // 语言相关工具
    │  │              │  │  Logger.java // 简单日志打印工具
    │  │              │  │  MathUtils.java // 计算相关工具
    │  │              │  │  MimeUtils.java // 文件扩展名相关工具
    │  │              │  │  NotificationsUtils.java // 通知栏消息相关工具
    │  │              │  │  NotNull.java
    │  │              │  │  ObjectUtils.java
    │  │              │  │  OSUtils.java
    │  │              │  │  PermissionPageUtils.java
    │  │              │  │  PermissionUtils.java
    │  │              │  │  ReflectionUtils.java
    │  │              │  │  ScreenUtils.java
    │  │              │  │  SingleTaskHandler.java
    │  │              │  │  SPUtils.java
    │  │              │  │  StatusBarUtils.java
    │  │              │  │  StringUtils.java
    │  │              │  │  SystemBarTintManager.java
    │  │              │  │  TextViewUtils.java
    │  │              │  │  TimeUtils.java
    │  │              │  │  UriUtils.java
    │  │              │  │  Utils.java
    │  │              │  │  ViewUtils.java
    │  │              │  │  ZipUtils.java
    │  │              │  │
    │  │              │  ├─code // 编码
    │  │              │  │      Base32.java
    │  │              │  │      Base64Utils.java
    │  │              │  │      EncodingUtils.java
    │  │              │  │      Hex.java
    │  │              │  │      Md5.java
    │  │              │  │
    │  │              │  └─networkMonitor // 网络连接监控
    │  │              │          NetWorkMonitor.java
    │  │              │          NetWorkMonitorManager.java
    │  │              │          NetWorkState.java
    │  │              │          NetWorkStateReceiverMethod.java
    │  │              │
    │  │              └─view
    │  │                  │  CustomProgressDialog.java // 进度对话框
    │  │                  │  ElasticScrollView.java // 带弹性的ScrollView
    │  │                  │  LoadingView.java
    │  │                  │  MarqueeTextView.java
    │  │                  │  Mask.java // 屏蔽touch事件
    │  │                  │  MaxHeightView.java // 可设置maxHeight的FrameLayout
    │  │                  │  NoScrollViewPager.java // 屏蔽手势的ViewPager
    │  │                  │  PagerIndicator.java // 自定义ViewPager指示器
    │  │                  │  RoundProgressBar.java // 圆形ProgressBar
    │  │                  │  ScaleImageView.java // 支持centerTopCrop和centerBottomCrop的ImageView
    │  │                  │  SignKeyWordTextView.java
    │  │                  │  StatusBarHeightView.java // StatusBar高度的View
    │  │                  │  ToastMaster.java // 自定义Toast
    │  │                  │  TouchLayout.java
    │  │                  │  TouchOpacity.java
    │  │                  │  TouchRipple.java
    │  │                  │  ViewPagerScroller.java // 调整ViewPager切换时间
    │  │                  │
    │  │                  ├─dialog
    │  │                  │      BaseDialog.java // 对话框
    │  │                  │
    │  │                  ├─front
    │  │                  │      BringToFrontHelper.java // bringToFront方法优化
    │  │                  │      BringToFrontLinear.java
    │  │                  │      BringToFrontLinearLayout.java
    │  │                  │      BringToFrontRelative.java
    │  │                  │      BringToFrontRelativeLayout.java
    │  │                  │
    │  │                  ├─listener
    │  │                  │      AppBarStateChangeListener.java
    │  │                  │      JustAfterChangedTextWatcher.java
    │  │                  │      JustChangedTextWatcher.java
    │  │                  │      JustOnPageSelectedListener.java
    │  │                  │      OnItemClickListener.java
    │  │                  │      OnItemLongClickListener.java
    │  │                  │
    │  │                  ├─menu
    │  │                  │      PopupMenu.java // 底部弹出式菜单
    │  │                  │
    │  │                  ├─popup
    │  │                  │      CommonPopupWindow.java // PopupWindow封装
    │  │                  │      PopupController.java
    │  │                  │
    │  │                  └─square
    │  │                          SquareFrameLayout.java // 正方形FrameLayout
    │  │                          SquareRelativeLayout.java // 正方形RelativeLayout
    │  │
    │  └─res
	│
    └─test
```

## Gradle dependency

```
allprojects {
        repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}

dependencies {
	implementation 'com.github.xuqiqiang:AndroidUIKit:1.0.0'
}
```

## Usage

[Simple demo](https://github.com/xuqiqiang/AndroidUIKit/blob/main/demo/src/main/java/com/xuqiqiang/uikit/demo/MainActivity.java)

## Apache License
       Apache License

       Copyright [2020] [xuqiqiang]

       Licensed under the Apache License, Version 2.0 (the "License");
       you may not use this file except in compliance with the License.
       You may obtain a copy of the License at

           http://www.apache.org/licenses/LICENSE-2.0

       Unless required by applicable law or agreed to in writing, software
       distributed under the License is distributed on an "AS IS" BASIS,
       WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
       See the License for the specific language governing permissions and
       limitations under the License.

