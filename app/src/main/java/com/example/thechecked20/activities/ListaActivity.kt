package com.example.thechecked20.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.thechecked20.App
import com.example.thechecked20.OnCardClickListner
import com.example.thechecked20.R
import com.example.thechecked20.databinding.ActivityListaBinding
import com.example.thechecked20.model.DadosChecklist
import java.text.SimpleDateFormat
import java.util.Locale
import kotlin.concurrent.thread

class ListaActivity : AppCompatActivity(), OnCardClickListner {

    private lateinit var binding: ActivityListaBinding
    private lateinit var rvList: RecyclerView
    private lateinit var adapter: ListViewAdapter
    private lateinit var listaBd: MutableList<DadosChecklist>
    private var listaTipo: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        listaTipo = intent?.extras?.getString("listaTipo") // -> O QUE RECEBE
        val nomeChecklist = intent?.extras?.getString("nomeChecklist")
        val btnBusca = binding.imgBusca
        val btnVoltarOpcpes = binding.btnVoltar

        listaBd = mutableListOf()
        adapter = ListViewAdapter(listaBd, this)
        rvList = binding.rvLista
        rvList.adapter = adapter

        rvList.layoutManager = LinearLayoutManager(this).apply {
            stackFromEnd = true
            reverseLayout = true

        }

        btnBusca.setOnClickListener {
            val intent = Intent(this@ListaActivity,BuscarChecklistActivity::class.java)
            startActivity(intent)
        }
        btnVoltarOpcpes.setOnClickListener {
            val intent = Intent(this@ListaActivity,OpcoesActivity::class.java)
            startActivity(intent)
        }



        when (listaTipo) {

            "Reparos" ->
                Thread {
                    val app = application as App
                    val dao = app.db.checklistDao()
                    val resposta = dao.pegarTodosChecklist()

                    runOnUiThread {
                        listaBd.addAll(resposta)
                        adapter.notifyDataSetChanged()
                    }

                }.start()

            "Recalls" ->
                Thread {
                    val app = application as App
                    val dao = app.db.checklistDao()
                    val resposta = dao.pegarRecalls()

                    runOnUiThread {
                        listaBd.addAll(resposta)
                        adapter.notifyDataSetChanged()
                    }

                }.start()

            "Reparo Avançado" ->
                Thread {
                    val app = application as App
                    val dao = app.db.checklistDao()
                    val resposta = dao.pegarRAvancado(rAvancado = true)

                    runOnUiThread {
                        listaBd.addAll(resposta)
                        adapter.notifyDataSetChanged()
                    }

                }.start()

            "Buscar Checklist" ->
                Thread {
                    val app = application as App
                    val dao = app.db.checklistDao()
                    val buscarChecklist = dao.pegarChecklistPorNome(nome = nomeChecklist.toString())

                    runOnUiThread{
                        listaBd.addAll(buscarChecklist)
                        adapter.notifyDataSetChanged()
                    }
                }.start()
        }

    }

    override fun onLongClick(position: Int, card: DadosChecklist) {

        AlertDialog.Builder(this)
            .setMessage("Deseja excluir esse Checklist ?")
            .setNegativeButton(android.R.string.cancel) { dialog, witch ->
            }
            .setPositiveButton(R.string.SIM) { dialog, witch ->
               Thread {
                   val app = application as App
                   val dao = app.db.checklistDao()
                   val deleteCard = dao.delete(card)

                   if (deleteCard > 0) {
                           runOnUiThread {
                               listaBd.removeAt(position)
                               adapter.notifyDataSetChanged()
                           }
                   }
               }.start()

            }.create()
            .show()

    }

    override fun onRestart() {
        super.onRestart()
        recreate()
    }


    private inner class ListViewAdapter(
        private val checklist: List<DadosChecklist>,
        private val listener: OnCardClickListner,
    ) : RecyclerView.Adapter<ListViewAdapter.ListViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
            val view = layoutInflater.inflate(R.layout.itens_lista, parent, false)
            return ListViewHolder(view)
        }

        override fun getItemCount(): Int {
            return checklist.size
        }

        override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
            val itemCurrent = checklist[position]
            holder.bind(itemCurrent)
        }

        private inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            fun bind(item: DadosChecklist) {

                val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale("pt", "BR"))
                val data = sdf.format(item.data)

                val cardview: CardView = itemView.findViewById(R.id.cardview_list)
                val editAparelho: TextView = itemView.findViewById(R.id.txt_aparelho)
                val editNome: TextView = itemView.findViewById(R.id.txt_nome_cliente)
                val editData: TextView = itemView.findViewById(R.id.txt_data_reparo)
                val id: TextView = itemView.findViewById(R.id.txt_id_list)

                editAparelho.setText(item.aparelho)
                editNome.setText(item.nome)
                editData.text = getString(R.string.data, data)
                id.text = item.id.toString()

                cardview.setOnClickListener {

                    if (listaTipo == "Reparos") {

                        val intent = Intent(this@ListaActivity, CheckListActivity::class.java)
                        intent.putExtra("checklistId", item.id)
                        startActivity(intent)

                    } else if (listaTipo == "Recalls") {
                        val intent = Intent(this@ListaActivity, RecallActivity::class.java)
                        intent.putExtra("checklistId", item.id)
                        startActivity(intent)

                    }

                    else if (listaTipo == "Reparo Avançado") {
                        val intent = Intent(this@ListaActivity, CheckListActivity::class.java)
                        intent.putExtra("checklistId", item.id)
                        startActivity(intent)

                    }

                    else if (listaTipo == "Buscar Checklist") {
                        val intent = Intent(this@ListaActivity, CheckListActivity::class.java)
                        intent.putExtra("checklistId", item.id)
                        startActivity(intent)

                    }


                }

                cardview.setOnLongClickListener {
                    listener.onLongClick(adapterPosition, item)
                    true

                }

            }
        }

    }


}