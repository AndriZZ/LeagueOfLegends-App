package com.mentormate.andriivanov.retrofit101.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast

import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.mentormate.andriivanov.retrofit101.R

class RegisterActivity : AppCompatActivity() {
    private var emailAddressText: EditText? = null
    private var passwordText: EditText? = null
    internal lateinit var progressBar: ProgressBar
    private var mAuth: FirebaseAuth? = null
    private var regButton: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        emailAddressText = findViewById(R.id.summoner_search)
        passwordText = findViewById(R.id.password)
        regButton = findViewById(R.id.btn_search)
        progressBar = findViewById<View>(R.id.progressBar) as ProgressBar

        mAuth = FirebaseAuth.getInstance()

        regButton!!.setOnClickListener { registerUser() }
    }

    private fun registerUser() {

        val userValidator = UserValidator()
        userValidator.validateUser(this!!.emailAddressText!!, this!!.passwordText!!)

        progressBar.visibility = View.VISIBLE

        mAuth!!.createUserWithEmailAndPassword(emailAddressText!!.text.toString(), passwordText!!.text.toString()).addOnCompleteListener { task ->
            progressBar.visibility = View.GONE
            if (task.isSuccessful) {
                finish()
                //startActivity(new Intent(RegisterActivity.this, ProfileActivity.class));
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
