    package com.example.pms

    import android.content.Intent
    import android.os.Bundle
    import android.view.View
    import android.widget.TextView
    import android.widget.Toast
    import androidx.appcompat.app.AppCompatActivity
    import com.google.android.material.button.MaterialButton


    class Login : AppCompatActivity() {
        companion object {
          init {
             System.loadLibrary("pms")
          }
        }

        external fun jniLogin(j_name : String, j_password: String, j_email: String): String

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.login_page)

            val username = findViewById<View>(R.id.username) as TextView
            val password = findViewById<View>(R.id.password) as TextView
            val registerbtn = findViewById<View>(R.id.registerbtn) as MaterialButton
            val loginbtn = findViewById<View>(R.id.loginbtn) as MaterialButton

            //admin and admin
            loginbtn.setOnClickListener {
                val response =
                    jniLogin(
                        username.text.toString(),
                        password.text.toString(),
                        ""
                    );
                Toast.makeText(
                    this@Login,
                    response,
                Toast.LENGTH_SHORT)
                .show()
            }
            registerbtn.setOnClickListener {
                val myIntent = Intent(
                    applicationContext,
                    Register::class.java
                )
                startActivity(myIntent)
            }
        }
    }