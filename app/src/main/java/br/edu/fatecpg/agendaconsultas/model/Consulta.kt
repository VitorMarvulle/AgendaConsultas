package br.edu.fatecpg.agendaconsultas.model

import com.google.firebase.Timestamp

data class Consulta(
    val nome: String = "",
    val email: String = "",
    val telefone: String = "",
    val data: String = "",
    val timestamp: Timestamp = Timestamp.now()
)
