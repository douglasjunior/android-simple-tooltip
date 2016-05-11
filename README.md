# Android Simple Tooltip [![Release](https://jitpack.io/v/douglasjunior/android-simple-tooltip.svg)](https://jitpack.io/#douglasjunior/android-simple-tooltip)

Uma simples biblioteca baseada no [PopupWindow](http://developer.android.com/intl/pt-br/reference/android/widget/PopupWindow.html) para criação de Tooltips em Android.

## Funcionalidades

 - Trabalha a partir do Android 2.3 (API 10) *Obs: animação acima do 3.0 (API 11)*
 - Instanciação simples: poucos parâmetros em uma única linha de código
 - Animação com controle de velocidade e tamanho
 - Opção para fechar com toque dentro ou fora do *tooltip*
 - Função modal (impede o toque nos componentes do background)
 - Overlay (escurece o fundo dando destaque no componente âncora)
 - Personalização da seta (*arrow*)
 - Conteúdo inflável a partir de uma `View` ou um `XML` de *layout*
 - Cores e dimenções customizadas via `Builder` ou `XMLs` de *resources*

## Demonstração 

![Demonstração](https://raw.githubusercontent.com/douglasjunior/android-simple-tooltip/master/screenshots/demo.gif)

## Uso
### Instanciação

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

### Resources
```xml
<color name="simpletooltip_background">@color/colorAccent</color>
<color name="simpletooltip_text">@android:color/primary_text_light</color>
<color name="simpletooltip_arrow">@color/colorAccent</color>
```
```xml
<dimen name="simpletooltip_max_width">150dp</dimen>
<dimen name="simpletooltip_circle_radius">50dp</dimen>
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


Confira o [projeto de exemplo](https://github.com/douglasjunior/android-simple-tooltip/blob/master/sample/src/main/java/io/github/douglasjunior/androidSimpleTooltip/sample/MainActivity.java) e o
 [javadoc](https://jitpack.io/com/github/douglasjunior/android-simple-tooltip/master-SNAPSHOT/javadoc/) para mais informações.

## Download
### Gradle

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
        compile 'com.github.douglasjunior:android-simple-tooltip:0.0.1'
    }
    ```

### Maven

1. Adicione em seu arquivo `pom.xml` ao final dos repositórios:

    ```xml
    <repositories>
        ...
    	<repository>
    	    <id>jitpack.io</id>
    	    <url>https://jitpack.io</url>
    	</repository>
    </repositories>
    ```
2. Adicione a dependência

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

ou

```xml
    <dependency>
        <groupId>com.github.douglasjunior</groupId>
        <artifactId>android-simple-tooltip</artifactId>
        <version>master-SNAPSHOT</version>
    </dependency>
```

## Licença

```
The MIT License (MIT)

Copyright (c) 2016 Douglas Nassif Roma Junior
```

Veja o [arquivo de licença](https://github.com/douglasjunior/android-simple-tooltip/blob/master/LICENSE).
