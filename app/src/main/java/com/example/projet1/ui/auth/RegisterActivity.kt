package com.example.projet1.ui.auth

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.projet1.R
import com.example.projet1.data.models.auth.RegisterData
import com.example.projet1.data.network.Api
import com.example.projet1.utils.Constants

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.auth_activity_register)
    }
    public fun goToLogin(view: View){
      finish()
    }
    public fun register(view: View){
        val login = findViewById<EditText>(R.id.registerEmail).text
        val password = findViewById<EditText>(R.id.registerPassword).text

        val registerData = RegisterData(
            login = login.toString(),
            password = password.toString()

        )
        Api().post<RegisterData>(Constants.API_USERS_REGISTER, registerData, ::registerSuccess)

    }
    private fun registerSuccess(responseCode: Int){

            if(responseCode == Constants.HTTP_SUCCESS){
                Toast.makeText(
                    this,
                    "Votre compte a été crée",
                    Toast.LENGTH_LONG
                ).show()
                finish()
            }else{
                Toast.makeText(
                    this,
                    "Erreur lors de la création de compte",
                    Toast.LENGTH_LONG
                ).show()
            }


    }

}