package com.mt.mycircleprogressbar

import android.animation.ValueAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.mt.mycircleprogressbar.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var mainBinding: ActivityMainBinding
    lateinit var valueAnimator: ValueAnimator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        valueAnimator = ValueAnimator.ofInt(0, 100)
        valueAnimator.apply {
            repeatCount = -1
            duration = 2000
            addUpdateListener(mainBinding.progressbar)
        }.start()
    }

}