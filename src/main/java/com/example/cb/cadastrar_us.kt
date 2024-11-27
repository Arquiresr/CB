package com.example.cb

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

import com.google.firebase.firestore.FirebaseFirestore

class Serviso_atual : AppCompatActivity() {
    private lateinit var Nome_do_servico: EditText
    private lateinit var Prazo: EditText
    private lateinit var Salario: EditText
    private lateinit var descricao1: EditText
    private lateinit var Btn_gravar: Button
    private lateinit var radioButton: RadioButton

    // Referência ao Firestore
    private val db = FirebaseFirestore.getInstance()
    private lateinit var Butão_para_editar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_serviso_atual)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // Initialize the TextViews

        Nome_do_servico = findViewById(R.id.editTextText8)
        Prazo = findViewById(R.id.editTextText9)
        Salario = findViewById(R.id.editTextText10)
        descricao1 = findViewById(R.id.editTextText11)
        Btn_gravar = findViewById(R.id.button)
        Butão_para_editar = findViewById(R.id.button7)
        radioButton = findViewById(R.id.radioButton2)
        Btn_gravar.setOnClickListener {
            var cal = Intent(this, Principal_dev::class.java)
            startActivity(cal)
        }




        fetchFirestoreData()

        Butão_para_editar.setOnClickListener {
            val isSelected = radioButton.isChecked

                val nome = Nome_do_servico.text.toString()
                val preco = Salario.text.toString()
                val descricao = descricao1.text.toString()
                val data = Prazo.text.toString()
            Log.d("DEBUG", "Estado do RadioButton: $isSelected")
                if (nome.isNotBlank() && descricao.isNotBlank() && preco.isNotEmpty() && data.isNotBlank()) {
                    val servico = hashMapOf(
                        "Nome" to nome,
                        "descr" to descricao,
                        "preco" to preco,
                        "prazo" to data,
                        "Status" to isSelected
                    )

                    db.collection("Servico")
                        .add(servico)
                        .addOnSuccessListener {
                            Toast.makeText(this, "Serviço atualizado com sucesso!", Toast.LENGTH_SHORT)
                                .show()
                            Nome_do_servico.text.clear()
                            descricao1.text.clear()
                            Salario.text.clear()
                            Prazo.text.clear()
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(
                                this,
                                "Erro ao salvar serviço: ${e.message}",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                } else {
                    Toast.makeText(this, "Por favor, preencha todos os campos!", Toast.LENGTH_SHORT)
                        .show()
                }


        }
    }
        private fun fetchFirestoreData() {
            // Substitua "NomeDaColecao" pelo nome da sua coleção
            // e "IDDoDocumento" pelo ID do documento que você quer buscar
            val documentReference = db.collection("Servico").limit(1)
                .get()


                .addOnSuccessListener { querySnapshot ->
                    // Verifique se há documentos na coleção
                    if (!querySnapshot.isEmpty) {
                        // Obtenha o primeiro documento da coleção
                        val document = querySnapshot.documents[0]

                        // Obtenha os dados do documento
                        val nome = document.getString("Nome")
                        val prazo = document.getString("prazo")
                        val preco = document.getString("preco")
                        val descr = document.getString("descr")

                        // Atribua os dados aos campos de texto
                        Nome_do_servico.setText(nome)
                        Prazo.setText(prazo)
                        Salario.setText(preco)
                        descricao1.setText(descr)
                    } else {
                        Toast.makeText(this, "Documento não encontrado!", Toast.LENGTH_SHORT).show()
                    }
                }
                .addOnFailureListener { exception ->
                    Toast.makeText(
                        this,
                        "Erro ao buscar documento: ${exception.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
        }

}
