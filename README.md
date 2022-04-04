# Android Simple Tooltip

[PT](https://github.com/douglasjunior/android-simple-tooltip/blob/master/README.pt.md)

[![Licence MIT](https://img.shields.io/badge/licence-MIT-blue.svg)](https://github.com/douglasjunior/android-simple-tooltip/blob/master/LICENSE)
[![Build Status](https://travis-ci.org/douglasjunior/android-simple-tooltip.svg?branch=master)](https://travis-ci.org/douglasjunior/android-simple-tooltip)
[![Release](https://jitpack.io/v/douglasjunior/android-simple-tooltip.svg)](https://jitpack.io/#douglasjunior/android-simple-tooltip)
[![Downloads](https://jitpack.io/v/douglasjunior/android-simple-tooltip/month.svg)](#download)
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-Android%20Simple%20Tooltip-yellow.svg?style=flat)](http://android-arsenal.com/details/1/3578)

A simple library based on [PopupWindow](http://developer.android.com/intl/pt-br/reference/android/widget/PopupWindow.html) to create Tooltips on Android.

## Features

 - Working from Android 4.0 (API 14)
 - Simple to use: few parameters in a single line of code
 - Animation with speed and size control
 - Option to close with touch inside or outside of the tooltip.
 - Modal mode (prevents touch in the background)
 - Overlay (darkens the background highlighting the anchor)
 - Customizable arrow
 - Inflatable content from a `View` or `XML` layout.
 - Colors and dimensions customized by `Builder` or `XML` resources

## Demo

![Demo](https://raw.githubusercontent.com/douglasjunior/android-simple-tooltip/master/screenshots/demo.gif)

## Usage
### Basic

```java
View yourView = findViewById(R.id.your_view);

new SimpleTooltip.Builder(this)
    .anchorView(yourView)
    .text("Texto do Tooltip")
    .gravity(Gravity.END)
    .animated(true)
    .transparentOverlay(false)
    .build()
    .show();
```

### Resources

```xml
<color name="simpletooltip_background">@color/colorAccent</color>
<color name="simpletooltip_text">@android:color/primary_text_light</color>
<color name="simpletooltip_arrow">@color/colorAccent</color>
```
```xml
<dimen name="simpletooltip_max_width">150dp</dimen>
<dimen name="simpletooltip_overlay_offset">10dp</dimen>
<dimen name="simpletooltip_margin">10dp</dimen>
<dimen name="simpletooltip_padding">8dp</dimen>
<dimen name="simpletooltip_arrow_width">30dp</dimen>
<dimen name="simpletooltip_arrow_height">15dp</dimen>
<dimen name="simpletooltip_animation_padding">4dp</dimen>
```
```xml
<integer name="simpletooltip_overlay_alpha">120</integer>
<integer name="simpletooltip_animation_duration">800</integer>
```
```xml
<style name="simpletooltip_default" parent="@android:style/TextAppearance.Medium"></style>
```

More info on the [sample project](https://github.com/douglasjunior/android-simple-tooltip/blob/master/sample/src/main/java/io/github/douglasjunior/androidSimpleTooltip/sample/MainActivity.java) and [javadoc](https://jitpack.io/com/github/douglasjunior/android-simple-tooltip/master-SNAPSHOT/javadoc/).

## Download
### Release

1. Add it in your root `build.gradle` at the end of repositories:

    ```javascript
    allprojects {
    	repositories {
    		...
    		maven { url "https://jitpack.io" }
    	}
    }
    ```

2. Add the dependency in your `app/build.gradle`:

    ```javascript
    dependencies {
        implementation 'com.github.douglasjunior:android-simple-tooltip:1.1.0'
    }
    ```

### Snapshot

```javascript
dependencies {
    implementation('com.github.douglasjunior:android-simple-tooltip:master-SNAPSHOT') {
        changing = true // Gradle will then check for updates every 24 hours
    }
}
```

## Contribute

New features, bug fixes and improvements in the translation are welcome! For questions and suggestions use the [issues](https://github.com/douglasjunior/android-simple-tooltip/issues).

Before submit your PR, run the gradle check.
```bash
./gradlew build connectedCheck
```

<a href="https://www.patreon.com/douglasjunior"><img src="http://i.imgur.com/xEO164Z.png" alt="Become a Patron!" width="200" /></a>
[![Donate](https://www.paypalobjects.com/en_US/i/btn/btn_donateCC_LG.gif)](https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=E32BUP77SVBA2)


## Known issues

1. If you close the `Dialog/Activity` without the Tooltip is closed, there may be the exception `java.lang.IllegalArgumentException: Could not lock surface`. This error occurs because the animation continue for a while after closing the `Dialog/Activity`. (This error does not impact the execution of the app)

2. If you call `tooltip.show()` after `Activity/Dialog` is closed, there may be the exception `android.view.WindowLeaked: Activity has leaked window android.widget.PopupWindow$PopupViewContainer that was originally added here`. [Read more.](http://stackoverflow.com/questions/2850573/activity-has-leaked-window-that-was-originally-added) (This error does not impact the execution of the app)

3. From API 24, Android has changed the behavior of `PopupWindow` in relation to the [`setClippingEnabled`](https://developer.android.com/reference/android/widget/PopupWindow.html#setClippingEnabled(boolean)) property, which causes the Popup to be cut off. [Read more](https://github.com/douglasjunior/android-simple-tooltip/issues/40).

## Licence

```
The MIT License (MIT)

Copyright (c) 2016 Douglas Nassif Roma Junior
```

See the full [licence file](https://github.com/douglasjunior/android-simple-tooltip/blob/master/LICENSE).
