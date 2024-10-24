package com.example.cb

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Message
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.GridView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.firestore.FirebaseFirestore

class Cadastro_em : AppCompatActivity() {
    private lateinit var Btn_volta: Button
    private lateinit var button3: Button
    private lateinit var TXT_nome: EditText
    private lateinit var TXT_email: EditText
    private lateinit var editTextText4: EditText
    private lateinit var editTextText5: EditText
    private lateinit var editTextText6: EditText
    private lateinit var editTextText7: EditText
    private lateinit var editTextTextPassword2: EditText
    private lateinit var editTextTextPassword3: EditText

    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_cadastro_em)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        Btn_volta= findViewById(R.id.volt)
        Btn_volta.setOnClickListener {
            Mudar()
        }
        //estanciar textInput
        TXT_nome = findViewById(R.id.TXT_nome)
        TXT_email = findViewById(R.id.TXT_email)
        editTextText4 = findViewById(R.id.editTextText4)
        editTextText5 = findViewById(R.id.editTextText5)
        editTextText6 = findViewById(R.id.editTextText6)
        editTextText7 = findViewById(R.id.editTextText7)
        editTextTextPassword2 = findViewById(R.id.editTextTextPassword2)
        editTextTextPassword3 = findViewById(R.id.editTextTextPassword3)
        button3 = findViewById(R.id.button3)
        //metodo para gravar
        button3.setOnClickListener {
            val Nome = TXT_nome.text.toString()
            val email = TXT_email.text.toString()
            val CNPJ = editTextText4.text.toString()
            val CEP = editTextText5.text.toString()
            val Cidade = editTextText6.text.toString()
            val Endereco = editTextText6.text.toString()
            val Senha = editTextTextPassword2.text.toString()
            val cSenha = editTextTextPassword3.text.toString()
            if(Nome.isNotEmpty() ||email.isNotEmpty() || CNPJ.isNotEmpty() || CEP.isNotEmpty() || Cidade.isNotEmpty() || Endereco.isNotEmpty() || Senha.isNotEmpty()  ){
             //Para conferir senha
                if(confereSenha(Senha,cSenha,it)){
                    auth.createUserWithEmailAndPassword(email, Senha).addOnCompleteListener{ cadastro ->
                        if(cadastro.isSuccessful){
                            val userId = auth.currentUser?.uid
                            if(userId != null){
                                val user = hashMapOf(
                                    "email" to email,
                                    "nome" to Nome,
                                  "CEP" to CEP,
                                   "CPF" to CNPJ,
                                    "Cidade" to Cidade,
                                   "endereco" to Endereco,
                                   "Senha" to Senha
                                )
                                firestore.collection("Empresa ").document(userId).set(user).addOnCompleteListener{ task ->
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
    private fun Mudar(){
        var cal = Intent(this, Preferencia_de_cadastros ::class.java)
        startActivity(cal)
    }
    //funcao basico para gravar
    private fun showSnack(view: View,message: String, color: Int){
        val snackbar = Snackbar.make(view,message, Snackbar.LENGTH_SHORT)
        snackbar.setBackgroundTint(color)
        snackbar.show()
    }
    private fun confereSenha(senha: String, senhaConf: String, view: View): Boolean{
        if(senha != senhaConf){
            showSnack(view, "Senha não esta igual",Color.RED)
            return false
        }
        else
        return true
    }
}
