package com.example.projet1.ui.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.projet1.ui.houses.HousesActivity
import com.example.projet1.R
import com.example.projet1.data.models.auth.AuthResponse
import com.example.projet1.data.models.auth.LoginData
import com.example.projet1.data.network.Api
import com.example.projet1.utils.Constants

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.auth_activity_login)
    }
    public fun registerNewAccount(view: View){
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }
    private fun loginSuccess(responseCode: Int, response: AuthResponse?){

            if(responseCode == Constants.HTTP_SUCCESS){
                val intent = Intent(
                    this,
                    HousesActivity::class.java
                )
                intent.putExtra("Token",response?.token)
                startActivity(intent)
            }else{
                Toast.makeText(
                    this,
                    "Erreur lors de la connexion ",
                    Toast.LENGTH_LONG
                ).show()
            }


    }

    fun login (view: View){
        val login = findViewById<EditText>(R.id.loginEmail).text
        val password = findViewById<EditText>(R.id.loginPassword).text
        try {
            val loginData = LoginData(
                login = login.toString(),
                password = password.toString()
            )
            Api().post<LoginData, AuthResponse>(
                Constants.API_USERS_AUTH,
                loginData,
                ::loginSuccess
            )
        } catch (e: Exception) {
            Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
        }
    }
}