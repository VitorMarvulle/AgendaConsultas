package br.edu.fatecpg.agendaconsultas.view

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.Query
import androidx.recyclerview.widget.RecyclerView
import br.edu.fatecpg.agendaconsultas.R
import br.edu.fatecpg.agendaconsultas.adapter.ConsultaAdapter
import br.edu.fatecpg.agendaconsultas.model.Consulta

class MedicoActivity : AppCompatActivity() {

    private lateinit var firestore: FirebaseFirestore
    private lateinit var auth: FirebaseAuth

    private lateinit var recyclerView: RecyclerView
    private lateinit var consultaAdapter: ConsultaAdapter
    private lateinit var consultasList: MutableList<Consulta>

    // Definindo uma tag para logar
    private val TAG = "MedicoActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_medico)

        Log.d(TAG, "onCreate: Iniciando MedicoActivity")

        firestore = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()

        recyclerView = findViewById(R.id.recyclerViewConsultas)
        recyclerView.layoutManager = LinearLayoutManager(this)
        consultasList = mutableListOf()
        consultaAdapter = ConsultaAdapter(consultasList)
        recyclerView.adapter = consultaAdapter

        loadConsultas()
    }

    private fun loadConsultas() {
        // Recuperar as consultas da coleção "clinica"
        firestore.collection("clinica")
            .orderBy("timestamp", Query.Direction.DESCENDING) // Ordena por timestamp
            .get()
            .addOnSuccessListener { documents ->


                consultasList.clear()
                for (document in documents) {
                    try {
                        val consulta = document.toObject(Consulta::class.java)
                        consultasList.add(consulta)
                        Log.d(TAG, "Consulta carregada: ${consulta.nome}")
                    } catch (e: Exception) {
                        Log.e(TAG, "loadConsultas: Erro ao converter documento para objeto Consulta", e)
                    }
                }

                consultaAdapter.notifyDataSetChanged() // Atualiza o RecyclerView
            }
            .addOnFailureListener { exception ->
                Log.e(TAG, "loadConsultas: Erro ao carregar as consultas", exception)
                Toast.makeText(this, "Erro ao carregar as consultas: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
    }
}
