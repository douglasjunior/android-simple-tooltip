# Android Simple Tooltip [![Build Status](https://travis-ci.org/douglasjunior/android-simple-tooltip.svg?branch=master)](https://travis-ci.org/douglasjunior/android-simple-tooltip) [![Release](https://jitpack.io/v/douglasjunior/android-simple-tooltip.svg)](https://jitpack.io/#douglasjunior/android-simple-tooltip) [![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-Android%20Simple%20Tooltip-yellow.svg?style=flat)](http://android-arsenal.com/details/1/3578)

[PT](https://github.com/douglasjunior/android-simple-tooltip/blob/master/README.pt.md)

A simple library based on [PopupWindow](http://developer.android.com/intl/pt-br/reference/android/widget/PopupWindow.html) to create Tooltips on Android.

## Features

 - Working from Android 2.1 (API 7) *Note: animation above 3.0 (API 11)*
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

## Usege
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
<dimen name="simpletooltip_overlay_circle_offset">10dp</dimen>
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
### Gradle

1. Add it in your root `build.gradle` at the end of repositories:

    ```javascript
    allprojects {
    	repositories {
    		...
    		maven { url "https://jitpack.io" }
    	}
    }
    ```

2. Add the dependency

    ```javascript
    dependencies {
        compile 'com.github.douglasjunior:android-simple-tooltip:0.0.1'
    }
    ```

### Maven

1. Add it in your `pom.xml` at the end of repositories:

    ```xml
    <repositories>
        ...
    	<repository>
    	    <id>jitpack.io</id>
    	    <url>https://jitpack.io</url>
    	</repository>
    </repositories>
    ```

2. Add the dependency

    ```xml
    <dependency>
        <groupId>com.github.douglasjunior</groupId>
        <artifactId>android-simple-tooltip</artifactId>
        <version>0.0.1</version>
    </dependency>
    ```

### Snapshot

```javascript
dependencies {
    compile 'com.github.douglasjunior:android-simple-tooltip:master-SNAPSHOT'
}
```

or

```xml
<dependency>
    <groupId>com.github.douglasjunior</groupId>
    <artifactId>android-simple-tooltip</artifactId>
    <version>master-SNAPSHOT</version>
</dependency>
```

## Contributions

New features, bug fixes and improvements in the translation are welcome! For questions and suggestions use the [issues](https://github.com/douglasjunior/android-simple-tooltip/issues).

## Licence

```
The MIT License (MIT)

Copyright (c) 2016 Douglas Nassif Roma Junior
```

See complete on [licence file](https://github.com/douglasjunior/android-simple-tooltip/blob/master/LICENSE).
