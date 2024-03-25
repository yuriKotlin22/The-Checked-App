package com.example.thechecked20.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.thechecked20.App
import com.example.thechecked20.R

class BuscarChecklistActivity : AppCompatActivity() {

    private lateinit var nomeCliente: EditText
    private lateinit var btnBuscar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buscar_checklist)

        nomeCliente = findViewById(R.id.txt_nome_busca)
        btnBuscar = findViewById(R.id.btn_buscar)


        btnBuscar.setOnClickListener {

            val nomeEncontrado = nomeCliente.text.toString()
            val intent = Intent(this@BuscarChecklistActivity,ListaActivity::class.java)
            intent.putExtra("listaTipo", "Buscar Checklist")
            intent.putExtra("nomeChecklist", nomeEncontrado)
            startActivity(intent)

        }

    }
}