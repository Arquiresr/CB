package com.example.cb

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import org.w3c.dom.Text

class Login : AppCompatActivity() {
    private lateinit var btn_cadas: Button
    private lateinit var TXT_login: EditText
    private lateinit var TXT_SENHA: EditText
    private lateinit var BTN_Cadastrar: Button
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        TXT_login = findViewById(R.id.editTextText)
        TXT_SENHA = findViewById(R.id.editTextTextPassword)
        BTN_Cadastrar = findViewById(R.id.btn_login)
        btn_cadas = findViewById(R.id.btn_cadas)

        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        BTN_Cadastrar.setOnClickListener {
            val username = TXT_login.text.toString()
            val password = TXT_SENHA.text.toString()
            if (username.isNotEmpty() && password.isNotEmpty()) {
                auth.signInWithEmailAndPassword(username, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful && username != "arquiresstoco@gmail.com") {
                            val intent = Intent(this, Principal::class.java)
                            startActivity(intent)
                        } else {
                            val intent = Intent(this,Preferencia_de_cadastros::class.java)
                            startActivity(intent)
                        }
                    }
            }else {
                showSnack(it, "Por favor, preencha todos os campos", Color.RED)
            }

        }
        btn_cadas.setOnClickListener{
            Irparacadsatro()
        }










    }
    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        // updateUI(currentUser)
    }
    private fun showSnack(view: View, message: String, color: Int) {
        val snackbar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT)
        snackbar.setBackgroundTint(color)
        snackbar.show()
    }
    private fun   Irparacadsatro(){
        val ca = Intent(this,Preferencia_de_cadastros::class.java)
        startActivity(ca)
    }
}
