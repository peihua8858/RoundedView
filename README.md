:heartpulse:RoundedView-Android:heartpulse:
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

### 1、添加生成圆角视图注解

```kotlin
@RoundedView([RatioImageView::class, FrameLayout::class, LinearLayout::class, TextView::class, ConstraintLayout::class])
class MainActivity : AppCompatActivity(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}
```

### 2、xml 布局引入
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
### 3、 自定义属性说明
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

属性名 | 说明 | 默认值
:----------- | :----------- | :-----------
rv_radius         | 圆角半径        | 0dp
rv_left_top_radius         | 左上角圆角半径        | 0dp
rv_left_bottom_radius         | 左下角圆角半径        | 0dp
rv_right_top_radius         | 右上角圆角半径        | 0dp
rv_right_bottom_radius         | 右下角圆角半径        | 0dp
rv_borderWidth         | 边框线宽度        | 0dp
rv_borderColor         | 边框线颜色值        | Color.TRANSPARENT
rv_drawBorder         | 是否绘制边框        | rv_borderWidth>0
rv_drawCircle         | 是否是圆形       | false

### 接口说明

``` java
  /**
     * 设置边框宽度
     *
     * @param borderWidth
     */
    void setBorderWidth(float borderWidth);

    /**
     * 设置是否绘制边框
     *
     * @param drawBorder
     */
    void setDrawBorder(boolean drawBorder);

    /**
     * 设置边框颜色
     *
     * @param borderColor
     */
    void setBorderColor(@ColorInt int borderColor);

    /**
     * 设置圆角半径
     *
     * @param radius
     */
    void setRadius(float radius);

    /**
     * 设置圆角半径
     *
     * @param radius 8 个值的数组，4 对 [X,Y] 半径
     */
    void setRadius(float[] radius);

    /**
     * 设置是否绘制成圆形
     *
     * @param drawCircle
     */
    void setDrawCircle(boolean drawCircle);

    /**
     * 获取视图的宽度
     *
     * @return
     */
    int getWidth();

    /**
     * 获取视图的高度
     *
     * @return
     */
    int getHeight();

    /**
     * 获取上下文
     *
     * @return
     */
    Context getContext();

    /**
     * 重新绘制
     */
    void postInvalidate();

    /**
     * 设置圆角
     *
     * @param leftTopRadius     左上角半径
     * @param leftBottomRadius  左下角半径
     * @param rightTopRadius    右上角半径
     * @param rightBottomRadius 右下角半径
     * @author dingpeihua
     * @date 2023/10/12 18:23
     * @version 1.0
     */
    void setRoundedCorners(float leftTopRadius, float leftBottomRadius, float rightTopRadius, float rightBottomRadius);
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
