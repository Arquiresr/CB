package com.example.cb
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import android.content.Context

import android.net.Uri
import android.util.Log
import android.widget.Toast
import com.google.firebase.firestore.DocumentSnapshot


class servicoAdapter(private val documentList: List<DocumentSnapshot>) :
    RecyclerView.Adapter<servicoAdapter.DocumentViewHolder>() {
    class DocumentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewNome: TextView = itemView.findViewById(R.id.textViewNome)
        val textViewPrazo: TextView = itemView.findViewById(R.id.textViewPrazo)
        val textViewPreco: TextView = itemView.findViewById(R.id.textViewPreco)
        val textViewDescricao: TextView = itemView.findViewById(R.id.textViewDescricao)
        val btn_b: Button = itemView.findViewById(R.id.btnAcao)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DocumentViewHolder {
        // Inflar o layout para cada item
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_servico, parent, false)
        return DocumentViewHolder(view)
    }
    override fun onBindViewHolder(holder: DocumentViewHolder, position: Int) {
        // Obter o documento atual
        val document = documentList[position]

        // Pegar dados do documento
        val nome = document.getString("Nome") ?: "N/A"
        val prazo = document.getString("prazo") ?: "N/A"
        val preco = document.getString("preco") ?: "N/A"
        val descr = document.getString("descr") ?: "N/A"



        // Configurar as TextViews com os dados do documento
        holder.textViewNome.text = nome
        holder.textViewPrazo.text = prazo
        holder.textViewPreco.text = preco
        holder.textViewDescricao.text = descr

        holder.btn_b.setOnClickListener {
            val mensagem = "Nome: $nome\nPrazo: $prazo\nPreço: $preco\nDescrição: $descr"
            enviarParaWhatsApp(it.context, mensagem)
        }

    }
    override fun getItemCount(): Int {
        // Retornar o número de documentos na lista
        return documentList.size
    }


    // Método para enviar mensagem para o WhatsApp
    private fun enviarParaWhatsApp(context: Context, mensagem: String) {
        try {
            // Criar uma Intent genérica para enviar texto
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_TEXT, mensagem)

            // Exibir uma caixa de diálogo de seleção de aplicativo
            context.startActivity(Intent.createChooser(intent, "Escolha o aplicativo para enviar a mensagem"))
        } catch (e: Exception) {
            // Mostrar mensagem de erro se algo falhar
            Toast.makeText(context, "Erro ao abrir o aplicativo de mensagens: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }





    }