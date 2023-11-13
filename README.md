# Android圆角视图
一个可设置指定角为圆角的库

[![Jitpack](https://jitpack.io/v/peihua8858/RoundedView.svg)](https://github.com/peihua8858)
[![PRs Welcome](https://img.shields.io/badge/PRs-Welcome-brightgreen.svg)](https://github.com/peihua8858)
[![Star](https://img.shields.io/github/stars/peihua8858/RoundedView.svg)](https://github.com/peihua8858/RoundedView)


## 目录
-[最新版本](https://github.com/peihua8858/RoundedView/releases/tag/1.0.6-beta2)<br>
-[如何引用](#如何引用)<br>
-[进阶使用](#进阶使用)<br>
-[如何提Issues](https://github.com/peihua8858/RoundedView/wiki/%E5%A6%82%E4%BD%95%E6%8F%90Issues%3F)<br>
-[License](#License)<br>


## 如何引用

使用 Gradle

```sh
repositories {
  google()
  mavenCentral()
}

dependencies {
    // KotlinCommonUtils
    implementation 'com.github.peihua8858.RoundedView:annotation:1.0.6-beta2'
    kapt 'com.github.peihua8858.RoundedView:compiler:1.0.6-beta2'
    implementation 'com.github.peihua8858.RoundedView:api:1.0.6-beta2'
}
```

或 Maven:

```xml
<dependency>
  <groupId>com.github.peihua8858.RoundedView</groupId>
  <artifactId>annotation</artifactId>
  <version>1.0.6-beta2</version>
</dependency>
<dependency>
  <groupId>com.github.peihua8858.RoundedView</groupId>
  <artifactId>compiler</artifactId>
  <version>1.0.6-beta2</version>
</dependency>
<dependency>
  <groupId>com.github.peihua8858.RoundedView</groupId>
  <artifactId>api</artifactId>
  <version>1.0.6-beta2</version>
</dependency>
```

## 进阶使用

1、添加生成圆角视图注解

```kotlin
@RoundedView([RatioImageView::class, FrameLayout::class, LinearLayout::class, TextView::class, ConstraintLayout::class])
class MainActivity : AppCompatActivity(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}
```

2、xml 布局引入
```xml
 <com.fz.roundview.RoundedLinearLayout
        android:id="@+id/llPointRedeem"
        android:layout_width="wrap_content"
        android:layout_height="@dimen/_18sdp"
        android:background="#FFE7EB"
        android:gravity="start|center_vertical"
        android:orientation="horizontal"
        android:paddingStart="@dimen/_8sdp"
        android:paddingEnd="@dimen/_8sdp"
        app:rv_radius="@dimen/_2sdp">
    <TextView
            android:id="@+id/tvPointPrice"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_gravity="start|center"
            android:singleLine="true"
            android:textColor="@color/colorAccent"
            android:textSize="@dimen/_12ssp"
            tools:text="$5.99 +" />

        <ImageView
            android:layout_width="@dimen/_14sdp"
            android:layout_height="@dimen/_14sdp"
            android:layout_gravity="start|center"
            android:layout_marginStart="@dimen/_4sdp"
            android:src="@mipmap/ic_ponts" />
</com.fz.roundview.RoundedLinearLayout>
```
3、字段说明
```xml
 <declare-styleable name="RoundView">
        <!-- 圆角半径 -->
        <attr name="rv_radius" format="dimension" />
        <!-- 左上角圆角半径 -->
        <attr name="rv_left_top_radius format="dimension" "/>
        <!-- 左下角圆角半径 -->
        <attr name="rv_left_bottom_radius" format="dimension" />
        <!-- 右上角圆角半径 -->
        <attr name="rv_right_top_radius" format="dimension" />
        <!-- 右下角圆角半径 -->
        <attr name="rv_right_bottom_radius" format="dimension" />
        <!-- 边框线宽度 -->
        <attr name="rv_borderWidth" format="dimension" />
        <!-- 边框线颜色值 -->
        <attr name="rv_borderColor" format="color"/>
        <!-- 是否绘制边框 -->
        <attr name="rv_drawBorder" format="boolean"/>
        <!-- 是否是圆形 -->
        <attr name="rv_drawCircle" format="boolean"/>
    </declare-styleable>
```

## License

```sh
Copyright 2023 peihua

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
