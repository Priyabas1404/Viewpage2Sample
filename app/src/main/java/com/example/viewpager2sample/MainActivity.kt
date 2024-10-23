package com.example.viewpager2sample

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.example.viewpager2sample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewPager = findViewById<ViewPager2>(R.id.viewPager)
        val pagerAdapter = SplashPagerAdapter(this)
        viewPager.adapter = pagerAdapter

        // Apply custom animation
        viewPager.setPageTransformer(ZoomOutPageTransformer())

        // Optional: Disable swiping on the last page (Sign-in page)
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                if (position == 3) {
                    viewPager.isUserInputEnabled = false // Disable swiping on the sign-in page
                } else {
                    viewPager.isUserInputEnabled = true // Enable swiping for onboarding pages
                }
            }
        })
    }
    // Define ZoomOutPageTransformer inside MainActivity
    class ZoomOutPageTransformer : ViewPager2.PageTransformer {
        override fun transformPage(view: View, position: Float) {
            view.apply {
                val scaleFactor = Math.max(0.85f, 1 - Math.abs(position))
                val pageWidth = width
                val pageHeight = height
                alpha = if (position <= -1 || position >= 1) {
                    0f
                } else {
                    1f
                }

                scaleX = scaleFactor
                scaleY = scaleFactor

                val verticalMargin = pageHeight * (1 - scaleFactor) / 2
                val horizontalMargin = pageWidth * (1 - scaleFactor) / 2
                translationX = if (position < 0) {
                    horizontalMargin - verticalMargin / 2
                } else {
                    horizontalMargin + verticalMargin / 2
                }
            }
        }
    }
}