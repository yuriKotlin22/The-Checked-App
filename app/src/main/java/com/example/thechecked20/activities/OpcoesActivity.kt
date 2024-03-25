package com.example.thechecked20.activities

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.thechecked20.OnItemClickListener
import com.example.thechecked20.R
import com.example.thechecked20.OpcoesView

class OpcoesActivity : AppCompatActivity(), OnItemClickListener {

    private lateinit var listaDeOpcoes: RecyclerView
    private lateinit var novoCliente: ImageView
    private lateinit var novoRecall: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_opcoes)

        val opcoesView = mutableListOf<OpcoesView>()

        opcoesView.add(
            OpcoesView(
                id = 1,
                txt = R.string.Clientes,
                cor = Color.BLUE
            )
        )
        opcoesView.add(
            OpcoesView(
                id = 2,
                txt = R.string.Recall,
                cor = Color.RED
            )
        )

        opcoesView.add(
            OpcoesView(
                id = 3,
                txt = R.string.Reparo_Avançado,
                cor = Color.GREEN
            )
        )

        val adapter = MainAdapter(opcoesView, this)

        listaDeOpcoes = findViewById(R.id.rv_opcoes)
        listaDeOpcoes.adapter = adapter
        listaDeOpcoes.layoutManager = LinearLayoutManager(this)
        novoCliente = findViewById(R.id.novo_cliente)
        novoRecall = findViewById(R.id.novo_recall)

        novoCliente.setOnClickListener {

            val novoChecklist = Intent(this, CheckListActivity::class.java)
            novoChecklist.putExtra("checklistId", -1) // ->Novo Recall
            startActivity(novoChecklist)
        }

        novoCliente.setOnClickListener {
            novoChecklistCliente()
        }
            novoRecall.setOnClickListener {
            novoChecklistRecall()
        }

    }


private inner class MainAdapter(
    private val opcoesView: List<OpcoesView>,
    val onItemClickListener: OnItemClickListener
) : RecyclerView.Adapter<MainAdapter.MainViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val view = layoutInflater.inflate(R.layout.itens_opcoes, parent, false)
        return MainViewHolder(view)
    }

    override fun getItemCount(): Int {
        return opcoesView.size
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val itemCurrent = opcoesView[position]
        holder.bind(itemCurrent)
    }

    private inner class MainViewHolder(Itemview: View) : RecyclerView.ViewHolder(Itemview) {
        fun bind(item: OpcoesView) {

            val linearLayout: LinearLayout = itemView.findViewById(R.id.botao_opcao)
            val text: TextView = itemView.findViewById(R.id.texto_item)

            text.setText(item.txt)



            linearLayout.setOnClickListener {
                onItemClickListener.onClick(item.id)

            }


        }
    }

}

override fun onClick(id: Int) {

    when (id) {

        1 -> {
            val intent = Intent(this@OpcoesActivity, ListaActivity::class.java)
            intent.putExtra("listaTipo", "Reparos")
            startActivity(intent)
        }

        2 -> {
            val intent = Intent(this@OpcoesActivity, ListaActivity::class.java)
            intent.putExtra("listaTipo", "Recalls")
            startActivity(intent)
        }

        3 -> {
            val intent = Intent(this@OpcoesActivity, ListaActivity::class.java)
            intent.putExtra("listaTipo", "Reparo Avançado")
            startActivity(intent)

        }

    }
}
    fun novoChecklistCliente() {
        val novoChecklist = Intent(this, CheckListActivity::class.java)
        novoChecklist.putExtra("checklistId", -1) // -> Novo Checklist
        startActivity(novoChecklist)

    }

    fun novoChecklistRecall() {
        val novoChecklist = Intent(this, RecallActivity::class.java)
        novoChecklist.putExtra("checklistId", -1) // ->Novo Recall
        startActivity(novoChecklist)

    }
}