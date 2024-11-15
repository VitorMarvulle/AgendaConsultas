package br.edu.fatecpg.agendaconsultas.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.edu.fatecpg.agendaconsultas.R
import br.edu.fatecpg.agendaconsultas.model.Consulta

class ConsultaAdapter(private val consultas: List<Consulta>) :
    RecyclerView.Adapter<ConsultaAdapter.ConsultaViewHolder>() {

    // ViewHolder para a consulta
    class ConsultaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtNome: TextView = itemView.findViewById(R.id.txtNome)
        val txtEmail: TextView = itemView.findViewById(R.id.txtEmail)
        val txtTelefone: TextView = itemView.findViewById(R.id.txtTelefone)
        val txtData: TextView = itemView.findViewById(R.id.txtData)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConsultaViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_consulta, parent, false)
        return ConsultaViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ConsultaViewHolder, position: Int) {
        val consulta = consultas[position]
        holder.txtNome.text = consulta.nome
        holder.txtEmail.text = consulta.email
        holder.txtTelefone.text = consulta.telefone
        holder.txtData.text = consulta.data
    }

    override fun getItemCount(): Int {
        return consultas.size
    }
}
