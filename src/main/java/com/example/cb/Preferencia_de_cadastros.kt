package com.example.cb

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Preferencia_de_cadastros : AppCompatActivity() {
    private lateinit var BTN_dev: Button
    private lateinit var btn_Empres: Button
    private lateinit var button: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_preferencia_de_cadastros)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        BTN_dev = findViewById(R.id.button3)
        BTN_dev.setOnClickListener{
            Mududar()
        }
        btn_Empres = findViewById(R.id.BTN_empres)
        btn_Empres.setOnClickListener{
            mudar()
        }


    }
    //fucao simples de navegação
    private fun Mududar(){
        val cal = Intent(this, Cadastro_dev::class.java)
        startActivity(cal)
    }
    private fun mudar(){
        val s = Intent(this, Cadastro_em::class.java)
        startActivity(s)
    }
    private fun Voltar(){
        val ss = Intent(this,Login::class.java)
        startActivity(ss)
    }

}