package com.mentormate.andriivanov.retrofit101.ui


import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.mentormate.andriivanov.retrofit101.R

class LoginActivity : AppCompatActivity() {
    private var emailAddressText: EditText? = null
    private var passwordText: EditText? = null
    internal lateinit var progressBar: ProgressBar
    private var mAuth: FirebaseAuth? = null
    private var loginButton: Button? = null
    private var registerAccount: TextView? = null
    lateinit var token:SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(application.applicationContext)
        setContentView(R.layout.activity_login)


        setContentView(R.layout.activity_login)







        emailAddressText = findViewById(R.id.summoner_search)
        passwordText = findViewById(R.id.password)
        loginButton = findViewById(R.id.btn_search)
        progressBar = findViewById<View>(R.id.progressBar) as ProgressBar
        registerAccount = findViewById(R.id.no_account_txt)

       token = getSharedPreferences("username", Context.MODE_PRIVATE)


        if(token.getString("username"," ")!=" "){
            var intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        mAuth = FirebaseAuth.getInstance()

        registerAccount!!.setOnClickListener { startActivity(Intent(this@LoginActivity, RegisterActivity::class.java)) }
        loginButton!!.setOnClickListener { loginUser() }

    }

    private fun loginUser() {
        val email = emailAddressText!!.text.toString().trim { it <= ' ' }
        val password = passwordText!!.text.toString().trim { it <= ' ' }

        var editor =token.edit()
        editor.putString("username",email)
        editor.apply()
        val userValidator = UserValidator()
        userValidator.validateUser(emailAddressText!!, passwordText!!)

        progressBar.visibility = View.VISIBLE

        mAuth!!.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            progressBar.visibility = View.GONE
            if (task.isSuccessful) {
                finish()
                startActivity(Intent(this@LoginActivity, MainActivity::class.java))
            } else {

                if (task.exception is FirebaseAuthUserCollisionException) {
                    Toast.makeText(applicationContext, "You are already registered", Toast.LENGTH_SHORT).show()

                } else {
                    Toast.makeText(applicationContext, task.exception!!.message, Toast.LENGTH_SHORT).show()
                }

            }
        }

    }

}
