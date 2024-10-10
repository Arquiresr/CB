package com.example.cb

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Cadastro_dev : AppCompatActivity() {
    private lateinit var inputEmail: EditText
    private lateinit var inputNome: EditText
    private lateinit var inputSenha: EditText
    private lateinit var inputSenhaAGN: EditText
    private lateinit var btnCriarConta: Button
    private lateinit var BTN_VOY: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_cadastro_dev)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        BTN_VOY = findViewById(R.id.BTN_VOY)
        BTN_VOY.setOnClickListener{
            VoltaPara()
        }
    }
    private fun VoltaPara(){
        val cal = Intent(this,Preferencia_de_cadastros::class.java)
        startActivity(cal)
    }
}