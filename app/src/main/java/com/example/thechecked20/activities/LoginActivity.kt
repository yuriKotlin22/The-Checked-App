package com.example.thechecked20.activities

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import com.example.thechecked20.Adm
import com.example.thechecked20.R
import com.example.thechecked20.databinding.ActivityLoginBinding
import com.example.thechecked20.databinding.ActivityOpcoesBinding
import com.google.android.material.snackbar.Snackbar

class LoginActivity : AppCompatActivity() {

    private var adm = Adm()

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val loginMain = binding.login.text
        val senhaMain = binding.senha.text
        val btnEntrar = binding.btnEntrar

        fun validador(): Boolean {
            return (loginMain.toString().isNotEmpty()
                    && senhaMain.toString().isNotEmpty()
                    && loginMain.toString() == adm.login
                    && senhaMain.toString() == adm.senha.toString()
                    )
        }

        btnEntrar.setOnClickListener {
            if (!validador()) {
                showSnackBar(it)

            }

            else {
                val pgOptions = Intent(this, OpcoesActivity::class.java)
                startActivity(pgOptions)
                finish()
            }
        }



    }
    private fun showSnackBar (view: View){

        Snackbar.make(view, "Login/Senha incorretos", Snackbar.LENGTH_SHORT)
            .setActionTextColor(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white)))
            .setBackgroundTint(Color.RED)
            .show()

    }

}


