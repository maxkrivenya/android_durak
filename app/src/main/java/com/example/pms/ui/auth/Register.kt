package com.example.pms.ui.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pms.R
import com.example.pms.ui.auth.Login.Companion.emailRegex
import com.google.android.material.button.MaterialButton


class Register : AppCompatActivity() {
    companion object {
        init {
            System.loadLibrary("pms")
        }
    }

    external fun jniRegister(j_name : String, j_password: String, j_email: String): String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_page)

        val username = findViewById<View>(R.id.username) as TextView
        val password = findViewById<View>(R.id.password) as TextView
        val email    = findViewById<View>(R.id.email)    as TextView
        val registerbtn = findViewById<View>(R.id.registerbtn) as MaterialButton
        val loginbtn = findViewById<View>(R.id.loginbtn) as MaterialButton

        //admin and admin
        registerbtn.setOnClickListener {
            val response : String;
            if (!username.text.matches(emailRegex.toRegex()) && email.text.matches(emailRegex.toRegex())) {
                 response =
                    jniRegister(
                        username.text.toString(),
                        password.text.toString(),
                        email.text.toString()
                    );
            }else{
                 response = "invalid username or email format";
            }
            Toast.makeText(
                this@Register,
                response,
                Toast.LENGTH_SHORT)
                .show()
        }
        loginbtn.setOnClickListener {
            val myIntent = Intent(
                this,
                Login::class.java
            )
            startActivity(myIntent)
        }
    }
}