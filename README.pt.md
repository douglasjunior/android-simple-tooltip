# Android Simple Tooltip

[EN](https://github.com/douglasjunior/android-simple-tooltip/blob/master/README.md)

[![Licence MIT](https://img.shields.io/badge/licence-MIT-blue.svg)](https://github.com/douglasjunior/android-simple-tooltip/blob/master/LICENSE)
[![Build Status](https://travis-ci.org/douglasjunior/android-simple-tooltip.svg?branch=master)](https://travis-ci.org/douglasjunior/android-simple-tooltip)
[![Release](https://jitpack.io/v/douglasjunior/android-simple-tooltip.svg)](https://jitpack.io/#douglasjunior/android-simple-tooltip)
[![Downloads](https://jitpack.io/v/douglasjunior/android-simple-tooltip/month.svg)](#download)
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-Android%20Simple%20Tooltip-yellow.svg?style=flat)](http://android-arsenal.com/details/1/3578)

Uma simples biblioteca baseada no [PopupWindow](http://developer.android.com/intl/pt-br/reference/android/widget/PopupWindow.html) para criação de Tooltips em Android.

## Funcionalidades

 - Trabalha a partir do Android 2.1 (API 7) *Obs: animação acima do 3.0 (API 11)*
 - Uso simplificado: poucos parâmetros em uma única linha de código
 - Animação com controle de velocidade e tamanho
 - Opção para fechar com toque dentro ou fora do *tooltip*
 - Modo modal (impede o toque nos componentes do background)
 - Overlay (escurece o fundo dando destaque no componente âncora)
 - Personalização da seta (*arrow*)
 - Conteúdo inflável a partir de uma `View` ou um `XML` de *layout*
 - Cores e dimenções customizadas via `Builder` ou `XMLs` de *resources*

## Demonstração 

![Demonstração](https://raw.githubusercontent.com/douglasjunior/android-simple-tooltip/master/screenshots/demo.gif)

## Uso
### Básico

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

Para mais informações, confira o [projeto de exemplo](https://github.com/douglasjunior/android-simple-tooltip/blob/master/sample/src/main/java/io/github/douglasjunior/androidSimpleTooltip/sample/MainActivity.java) e o [javadoc](https://jitpack.io/com/github/douglasjunior/android-simple-tooltip/master-SNAPSHOT/javadoc/).

## Download
### Release

1. Adicione em seu arquivo raiz `build.gradle` ao final dos repositórios:

    ```javascript
    allprojects {
    	repositories {
    		...
    		maven { url "https://jitpack.io" }
    	}
    }
    ```

2. Adicione a dependência

    ```javascript
    dependencies {
        compile 'com.github.douglasjunior:android-simple-tooltip:0.2.2'
    }
    ```

### Snapshot

```javascript
dependencies {
    compile('com.github.douglasjunior:android-simple-tooltip:master-SNAPSHOT') {
        changing = true // Gradle irá checar por atualizações a cada 24 horas
    }
}
```

## Contribua

Novas funcionalidades, correções de bug e melhorias na tradução serão bem vindas! Para dúvidas e sugestões utilize as [issues](https://github.com/douglasjunior/android-simple-tooltip/issues).

Antes de enviar seu PR, execute a checagem do gradle.
```bash
./gradlew build connectedCheck
```

[![Doar](https://www.paypalobjects.com/pt_BR/BR/i/btn/btn_donateCC_LG.gif)](https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=TM8BGN6PJF6SS)

## Problemas conhecidos

1. Se fechar a `Dialog/Activity` sem que a Tooltip seja encerrada, pode ocorrer o erro `java.lang.IllegalArgumentException: Could not lock surface`. Este erro ocorre devido a animação continuar após o fechamento da `Dialog/Activity`. (Este erro não afeta no funcionamento do programa.)

2. Se chamar `tooltip.show()` após a `Activity/Dialog` ser encerrada, pode ocorrer o erro `android.view.WindowLeaked: Activity has leaked window android.widget.PopupWindow$PopupViewContainer that was originally added here`. [Leia mais.](http://stackoverflow.com/questions/2850573/activity-has-leaked-window-that-was-originally-added) (Este erro não afeta no funcionamento do programa.)

3. À partir da API 24 o Android mudou o comportamento da `PopupWindow` em relação a propriedade [`setClippingEnabled`](https://developer.android.com/reference/android/widget/PopupWindow.html#setClippingEnabled(boolean)), isso faz com que a Popup possa ficar cortada se não couber na tela. [Leia mais](https://github.com/douglasjunior/android-simple-tooltip/issues/40).

## Licença

```
The MIT License (MIT)

Copyright (c) 2016 Douglas Nassif Roma Junior
```

Veja o [arquivo de licença](https://github.com/douglasjunior/android-simple-tooltip/blob/master/LICENSE) completo.
