# Android Simple Tooltip

Uma simples biblioteca baseada no [PopupWindow](http://developer.android.com/intl/pt-br/reference/android/widget/PopupWindow.html) para criação de Tooltips em Android.

## Demonstração 

![Demonstração](https://raw.githubusercontent.com/douglasjunior/android-simple-tooltip/master/screenshots/demo.gif)

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

Confira o [projeto de exemplo](https://github.com/douglasjunior/android-simple-tooltip/blob/master/sample/src/main/java/io/github/douglasjunior/androidSimpleTooltip/sample/MainActivity.java).

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

## Licença

```
The MIT License (MIT)

Copyright (c) 2016 Douglas Nassif Roma Junior
```

Veja o [arquivo de licença](https://github.com/douglasjunior/android-simple-tooltip/blob/master/LICENSE).
