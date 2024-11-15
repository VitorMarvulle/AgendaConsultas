package br.edu.fatecpg.agendaconsultas.view

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.edu.fatecpg.agendaconsultas.MainActivity
import br.edu.fatecpg.agendaconsultas.R
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.Timestamp
import java.util.*

class PacienteActivity : AppCompatActivity() {

    private lateinit var edtNome: EditText
    private lateinit var edtEmail: EditText
    private lateinit var edtTel: EditText
    private lateinit var edtData: EditText
    private lateinit var btnCriarAgendamento: Button
    private lateinit var btnSair: Button // Botão Sair
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_paciente)

        // Inicializar Firestore e FirebaseAuth
        firestore = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()

        // Inicializa os componentes da UI
        edtNome = findViewById(R.id.edtNome)
        edtEmail = findViewById(R.id.edtEmail)
        edtTel = findViewById(R.id.edtTel)
        edtData = findViewById(R.id.edtData)
        btnCriarAgendamento = findViewById(R.id.btnCadastro)
        btnSair = findViewById(R.id.btnSair) // Referencia o botão de sair


        // Clique no botão "Criar Agendamento"
        btnCriarAgendamento.setOnClickListener {
            // Recupera os dados dos campos
            val nomePaciente = edtNome.text.toString()
            val emailPaciente = edtEmail.text.toString()
            val telefonePaciente = edtTel.text.toString()
            val dataAgendamento = edtData.text.toString()

            // Verifica se todos os campos estão preenchidos
            if (TextUtils.isEmpty(nomePaciente) || TextUtils.isEmpty(emailPaciente) ||
                TextUtils.isEmpty(telefonePaciente) || TextUtils.isEmpty(dataAgendamento)) {
                Toast.makeText(this, "Por favor, preencha todos os campos!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Cria o map de dados para salvar no Firestore
            val agendamento = hashMapOf(
                "nome" to nomePaciente,
                "email" to emailPaciente,
                "telefone" to telefonePaciente,
                "data" to dataAgendamento,
                "timestamp" to Timestamp(Date()) // Adiciona o timestamp do agendamento
            )

            // Salva os dados na coleção "clinica" no Firestore
            firestore.collection("clinica")
                .add(agendamento)
                .addOnSuccessListener {
                    Toast.makeText(this, "Agendamento criado com sucesso!", Toast.LENGTH_SHORT).show()
                    // Limpar os campos após o agendamento
                    edtNome.text.clear()
                    edtEmail.text.clear()
                    edtTel.text.clear()
                    edtData.text.clear()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Erro ao criar agendamento: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        }

        // Lógica do botão "Sair"
        btnSair.setOnClickListener {
            // Realiza o logout do Firebase
            auth.signOut()

            // Redireciona para a MainActivity (ou LoginActivity, se preferir)
            val intent = Intent(this, MainActivity::class.java) // Ou pode ser LoginActivity, se preferir
            startActivity(intent)
            finish() // Finaliza a atividade atual para evitar que o usuário volte para ela ao pressionar o botão voltar
        }
    }
}
