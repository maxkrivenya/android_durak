package com.example.pms.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.pms.R
import androidx.fragment.app.Fragment
import com.example.pms.databinding.MenuBinding

class Menu : AppCompatActivity() {

        private lateinit var binding : MenuBinding

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            binding = MenuBinding.inflate(layoutInflater)
            setContentView(binding.root)    

            replaceFragment(Home())

            binding.bottomNavigationView.setOnItemSelectedListener {

                when(it.itemId){

                    R.id.home -> replaceFragment(Home())
                    R.id.history -> replaceFragment(History())
                    R.id.settings -> replaceFragment(Settings())

                    else ->{}

                }

                true

            }


        }

        private fun replaceFragment(fragment : Fragment){

            val fragmentManager = supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.frame_layout,fragment)
            fragmentTransaction.commit()


        }
}