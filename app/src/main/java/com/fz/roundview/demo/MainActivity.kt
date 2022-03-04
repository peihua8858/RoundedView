package com.fz.roundview.demo

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.fz.imageloader.ImageLoader
import com.fz.imageloader.glide.ImageGlideFetcher
import com.fz.roundview.annotation.RoundedView
import com.fz.imageloader.widget.RatioImageView
import com.google.android.material.floatingactionbutton.FloatingActionButton

@RoundedView([
    View::class,  // 对应 com.fz.badgeview.BadgeView，不想用这个类的话就删了这一行
    ImageView::class,  // 对应 com.fz.badgeview.BadgeImageView，不想用这个类的话就删了这一行
    RatioImageView::class,// 对应 com.fz.badgeview.BadgeRatioImageView，不想用这个类的话就删了这一行
    TextView::class,  // 对应 com.fz.badgeview.BadgeFloatingTextView，不想用这个类的话就删了这一行
    RadioButton::class,  // 对应 com.fz.badgeview.BadgeRadioButton，不想用这个类的话就删了这一行
    LinearLayout::class,  // 对应 com.fz.badgeview.BadgeLinearLayout，不想用这个类的话就删了这一行
    FrameLayout::class,  // 对应 com.fz.badgeview.BadgeFrameLayout，不想用这个类的话就删了这一行
    RelativeLayout::class,  // 对应 com.fz.badgeview.BadgeRelativeLayout，不想用这个类的话就删了这一行
    FloatingActionButton::class]
)
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        ImageLoader.getInstance().createProcessor(ImageGlideFetcher())
//        val ivIcon: RatioImageView = findViewById(R.id.riv_image)
//        ivIcon.setImageUrl("https://img95.699pic.com/photo/50136/1351.jpg_wh300.jpg")
//        val ivIcon2: RatioImageView = findViewById(R.id.riv_image2)
//        ivIcon2.setImageUrl("https://img95.699pic.com/photo/50136/1351.jpg_wh300.jpg")
//        val ivIcon1: RoundedRatioImageView = findViewById(R.id.rriv_image)
//        ivIcon1.setRadius(floatArrayOf(20f,20f,0f,0f,20f,20f,0f,0f))
//        ivIcon1.setRadius(20f)
//        ivIcon1.setImageUrl("https://img95.699pic.com/photo/50136/1351.jpg_wh300.jpg")
//        val rtvText: RoundedTextView = findViewById(R.id.rtv_text)
//        rtvText.setText("https://img95.699pic.com/photo/50136/1351.jpg_wh300.jpg")

    }
}