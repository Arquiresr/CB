package com.example.cb

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class Perfil_dev : AppCompatActivity() {
    private lateinit var BTN_Pricipal: Button
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    private lateinit var textViewNome: TextView
    private lateinit var textViewEmail: TextView
    private lateinit var textViewCEP: TextView
    private lateinit var textViewCPF: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_perfil_dev)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()
        textViewNome = findViewById(R.id.textView14)
        textViewEmail = findViewById(R.id.textView13)
        textViewCEP = findViewById(R.id.textView23)
        BTN_Pricipal = findViewById(R.id.button)
        textViewCPF = findViewById(R.id.textView22)
        BTN_Pricipal.setOnClickListener {
            val cal = Intent(this, Principal_dev::class.java)
            startActivity(cal)
        }
        val user = auth.currentUser
        if (user != null) {
            // Obter dados do Firestore
            val userId = user.uid
            getUserData(userId)
        } else {
            Toast.makeText(this, "Usuário não autenticado", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getUserData(userId: String) {
        // Referência à coleção "Dev" onde os dados do usuário estão salvos
        firestore.collection("Empresa").document(userId).get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    val nome = document.getString("nome")
                    val email = document.getString("email")
                    val cep = document.getString("senha")
                    val cpf = document.getString("cpf")

                    textViewNome.text = "nome: $nome"
                    textViewEmail.text = "email: $email"
                    textViewCEP.text = "senha: $cep"
                    textViewCPF.text = "cpf: $cpf"
                } else {
                    Toast.makeText(this, "Documento não encontrado", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Erro ao carregar dados: $exception", Toast.LENGTH_SHORT).show()
            }
    }
}
