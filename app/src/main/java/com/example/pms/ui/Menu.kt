package com.example.pms.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.pms.R
import androidx.fragment.app.Fragment
import com.example.pms.android.Solitaire
import com.example.pms.databinding.MenuBinding
import com.example.pms.ui.auth.Login
import kotlin.math.abs

class Menu : AppCompatActivity() {

        var scaleGestureDetector: ScaleGestureDetector? = null

        private lateinit var binding : MenuBinding

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            binding = MenuBinding.inflate(layoutInflater)
            setContentView(binding.root)    

            replaceFragment(Home())
            scaleGestureDetector = ScaleGestureDetector(this, CustomOnScaleGestureListener())

            binding.bottomNavigationView.setOnItemSelectedListener {

                when(it.itemId){

                    R.id.home -> replaceFragment(Home())
                    R.id.history -> replaceFragment(History())
                    R.id.settings -> replaceFragment(Settings())

                    else ->{}

                }

                true

            }

            window.decorView.setOnTouchListener(object: OnSwipeTouchListener(this@Menu) {
                override fun onSwipeLeft() {
                    val setter = when (binding.bottomNavigationView.selectedItemId){
                        R.id.home -> History()
                        R.id.history -> Settings()
                        R.id.settings -> Home()
                        else -> null
                    }
                    val id = when (binding.bottomNavigationView.selectedItemId){
                        R.id.home -> R.id.history
                        R.id.history -> R.id.settings
                        R.id.settings -> R.id.home
                        else -> null
                    }
                    if (setter != null) {
                        binding.bottomNavigationView.selectedItemId = setter.id;
                    }
                    if (id != null) {
                        binding.bottomNavigationView.selectedItemId = id;
                    }
                }
                override fun onSwipeRight() {
                    val setter = when (binding.bottomNavigationView.selectedItemId){
                        R.id.home -> Settings()
                        R.id.history -> Home()
                        R.id.settings -> History()
                        else -> null
                    }
                    val id = when (binding.bottomNavigationView.selectedItemId){
                        R.id.home -> R.id.settings
                        R.id.history -> R.id.home
                        R.id.settings -> R.id.history
                        else -> null
                    }
                    if (setter != null) {
                        replaceFragment(setter);
                    }
                    if (id != null) {
                        binding.bottomNavigationView.selectedItemId = id;
                    }
                }
            })


        }

        private fun replaceFragment(fragment : Fragment){

            val fragmentManager = supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.frame_layout,fragment)
            fragmentTransaction.commit()

        }


    override fun onTouchEvent(event: MotionEvent): Boolean {
        scaleGestureDetector?.onTouchEvent(event)
        return true
    }

    inner class CustomOnScaleGestureListener : ScaleGestureDetector.SimpleOnScaleGestureListener() {
        override fun onScale(detector: ScaleGestureDetector): Boolean {
            val scaleFactor = detector.scaleFactor
            if (scaleFactor > 1) {
                val myIntent = Intent(
                    applicationContext,
                    Solitaire::class.java
                )
                startActivity(myIntent)
            } else {
                val myIntent = Intent(
                    applicationContext,
                    Login::class.java
                )
                startActivity(myIntent)
            }
            return true
        }

        override fun onScaleBegin(detector: ScaleGestureDetector): Boolean {
            return true
        }

        override fun onScaleEnd(detector: ScaleGestureDetector) { }
    }

    open inner class OnSwipeTouchListener(ctx: Context) : View.OnTouchListener {

        private val gestureDetector: GestureDetector

        private val SWIPE_THRESHOLD = 100
        private val SWIPE_VELOCITY_THRESHOLD = 100


        init {
            gestureDetector = GestureDetector(ctx, GestureListener())
        }

        override fun onTouch(v: View, event: MotionEvent): Boolean {
            return gestureDetector.onTouchEvent(event)
        }

        private inner class GestureListener : GestureDetector.SimpleOnGestureListener() {


            override fun onDown(e: MotionEvent): Boolean {
                return true
            }

            override fun onFling(e1: MotionEvent?, e2: MotionEvent, velocityX: Float, velocityY: Float): Boolean {
                var result = false
                try {
                    val diffY = if (e1 == null) { e2.y } else{ e2.y - e1.y }
                    val diffX = if (e1 == null) { e2.x } else{ e2.x - e1.x }
                    if (abs(diffX) > abs(diffY)) {
                        if (abs(diffX) > SWIPE_THRESHOLD && abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                            if (diffX > 0) {
                                onSwipeRight()
                            } else {
                                onSwipeLeft()
                            }
                            result = true
                        }
                    } else if (Math.abs(diffY) > SWIPE_THRESHOLD && abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffY > 0) {
                            onSwipeBottom()
                        } else {
                            onSwipeTop()
                        }
                        result = true
                    }
                } catch (exception: Exception) {
                    exception.printStackTrace()
                }

                return result
            }


        }

        open fun onSwipeRight() {}

        open fun onSwipeLeft() {}

        open fun onSwipeTop() {}

        open fun onSwipeBottom() {}
    }
}

