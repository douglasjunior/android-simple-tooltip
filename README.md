# Android Simple Tooltip

Uma simples biblioteca baseada no [PopupWindow](http://developer.android.com/intl/pt-br/reference/android/widget/PopupWindow.html) para criação de Tooltips em Android.

## Demonstração 

![Demonstração](https://raw.githubusercontent.com/douglasjunior/android-simple-tooltip/master/screenshots/demo.gif)

Confira o [projeto de exemplo](https://github.com/douglasjunior/android-simple-tooltip/blob/master/sample/src/main/java/io/github/douglasjunior/androidSimpleTooltip/sample/MainActivity.java).

## Uso

```java
View yourView = findViewById(R.id.your_view);

new SimpleTooltip.Builder(this)
    .anchorView(yourView)
    .text("Texto do Tooltip")
    .gravity(Gravity.RIGHT)
    .animated(true)
    .transparentOverlay(false)
    .build()
    .show();
```

## Licença

```
The MIT License (MIT)

Copyright (c) 2016 Douglas Nassif Roma Junior
```

Veja o [arquivo de licença](https://github.com/douglasjunior/android-simple-tooltip/blob/master/LICENSE).
