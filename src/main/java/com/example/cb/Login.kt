package com.example.cb
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var inputEmail: EditText
    private lateinit var inputSenha: EditText
    private lateinit var btnLogin: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Inicializar FirebaseAuth
        auth = FirebaseAuth.getInstance()

        // Inicializar os campos e botões
        inputEmail = findViewById(R.id.inputEmail)
        inputSenha = findViewById(R.id.inputSenha)
        btnLogin = findViewById(R.id.btnLogin)

        // Configurar o clique no botão de login
        btnLogin.setOnClickListener {
            val email = inputEmail.text.toString()
            val senha = inputSenha.text.toString()
            login(email, senha)
        }
    }

    // Função para realizar o login do usuário
    private fun login(email: String, senha: String) {
        if (email.isNotEmpty() && senha.isNotEmpty()) {
            auth.signInWithEmailAndPassword(email, senha)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Login bem-sucedido, redirecionar para a tela de perfil
                        val intent = Intent(this, ProfileActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this, "Erro ao realizar login: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
        } else {
            Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show()
        }
    }
}

