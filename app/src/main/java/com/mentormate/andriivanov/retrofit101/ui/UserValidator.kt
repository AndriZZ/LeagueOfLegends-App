package com.mentormate.andriivanov.retrofit101.ui

import android.util.Patterns
import android.widget.TextView

class UserValidator {
    fun validateUser(email: TextView, password: TextView): Int {
        if (email.text.toString().isEmpty()) {
            email.error = "Email is required"
            email.requestFocus()
            return 0
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email.text.toString()).matches()) {
            email.error = "Please enter a valid email"
            email.requestFocus()
            return 0
        }

        if (password.text.toString().isEmpty()) {
            password.error = "Password is required"
            password.requestFocus()
            return 0
        }

        if (password.text.length < 6) {
            password.error = "Minimum lenght of password should be 6"
            password.requestFocus()
            return 0
        }
        return 1

    }
}
