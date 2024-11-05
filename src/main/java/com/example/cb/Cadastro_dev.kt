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
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.firestore.FirebaseFirestore

class Cadastro_dev : AppCompatActivity() {
    private lateinit var inputEmail: EditText
    private lateinit var inputNome: EditText
    private lateinit var inputSenha: EditText
    private lateinit var inputSenhaAGN: EditText
    private lateinit var CEP: EditText
    private lateinit var CPF: EditText
    private lateinit var Endereco: EditText
    private lateinit var Cidade: EditText
    private lateinit var btnCriarConta: Button
    private lateinit var BTN_VOY: Button
    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_cadastro_dev)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        inputNome = findViewById(R.id.TXT_nome)
        inputEmail = findViewById(R.id.TXT_email)
        inputSenha = findViewById(R.id.editTextTextPassword3)
        inputSenhaAGN = findViewById(R.id.editTextTextPassword2)
        CPF = findViewById(R.id.editTextText4)
        CEP = findViewById(R.id.editTextText5)
        Endereco = findViewById(R.id.editTextText7)
        Cidade = findViewById(R.id.editTextText6)
        btnCriarConta = findViewById(R.id.button3)
        BTN_VOY = findViewById(R.id.BTN_VOY)

        BTN_VOY.setOnClickListener{
            VoltaPara()
        }
        btnCriarConta.setOnClickListener{
            val Nome = inputNome.text.toString()
            val CEP = CEP.text.toString()
            val CPF = CPF.text.toString()
            val Endereco= Endereco.text.toString()
            val Cidade = Cidade.text.toString()
            val email = inputEmail.text.toString()
            val senha = inputSenha.text.toString()
            val coSenha = inputSenhaAGN.text.toString()
            // if para valor vasio
            if(Nome.isNotEmpty() ||email.isNotEmpty() || CPF.isNotEmpty() || CEP.isNotEmpty() || Cidade.isNotEmpty() || Endereco.isNotEmpty() || senha.isNotEmpty()  || coSenha.isNotEmpty()){
                if(confereSenha(senha,coSenha,it)){
                    auth.createUserWithEmailAndPassword(email, senha).addOnCompleteListener{ cadastro ->
                        if(cadastro.isSuccessful){
                            val userId = auth.currentUser?.uid
                            if(userId != null){
                                val user = hashMapOf(
                                    "email" to email,
                                    "nome" to Nome,
                                    "CEP" to CEP,
                                    "CPF" to CPF,
                                    "Cidade" to Cidade,
                                    "endereco" to Endereco,
                                    "senha" to senha
                                )
                                firestore.collection("Dev").document(userId).set(user).addOnCompleteListener{ task ->
                                    if(task.isSuccessful){
                                        showSnack(it, "Cadastro realizados com sucesso!!", Color.GREEN)
                                    }
                                    else
                                        showSnack(it, "ERRO DE BAMCO", Color.RED)
                                }
                            }

                        }

                    }.addOnCompleteListener{ exception ->
                        val mensagemErro = when (exception) {
                            //  is FirebaseAuthWeakPasswordException -> "Digite uma senha no mínimo de 6 caracteres!"
                            is FirebaseAuthInvalidCredentialsException -> "Digite um email válido"
                            //  is FirebaseAuthUserCollisionException -> "Conta já cadastrada!"
                            is FirebaseNetworkException -> "Sem conexão com a internet!"
                            else -> "Erro ao cadastrar!"
                        }
                        showSnack(it, mensagemErro, Color.RED)
                    }
                }
            }else
                showSnack(it, "Todos campos", Color.RED)

        }

    }
    private fun VoltaPara(){
        val cal = Intent(this,Preferencia_de_cadastros::class.java)
        startActivity(cal)
    }
    private fun showSnack(view: View, message: String, color: Int){
        val snackbar = Snackbar.make(view,message, Snackbar.LENGTH_SHORT)
        snackbar.setBackgroundTint(color)
        snackbar.show()
    }
    private fun confereSenha(senha: String, senhaConf: String, view: View): Boolean{
        if(senha != senhaConf){
            showSnack(view, "Senha não esta igual", Color.RED)
            return false
        }
        else
            return true
    }
}
