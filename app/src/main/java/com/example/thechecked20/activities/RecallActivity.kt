package com.example.thechecked20.activities

import android.content.res.ColorStateList
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.example.thechecked20.App
import com.example.thechecked20.R
import com.example.thechecked20.databinding.ActivityRecallBinding
import com.example.thechecked20.model.DadosChecklist
import com.google.android.material.snackbar.Snackbar

class RecallActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecallBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecallBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val checklistId = intent?.extras?.getInt("checklistId",-1) ?: throw Exception("ERRO")

        pegarChecklistPorId(checklistId)

        binding.fabSalvarRecall.setOnClickListener {

            binding.txtDataRecall.text.toString()

            if (!validate()){
                showSnackBar(it)
                return@setOnClickListener
            }

            Thread{
                val app = application as App
                val dao = app.db.checklistDao()

                if (checklistId == -1) {
                    val checklistRecall = DadosChecklist(
                        dataChecklist = binding.txtDataRecall.text.toString(),
                        nome = binding.txtNomeRecall.text.toString(),
                        reparoId = binding.txtIdRecall.text.toString(),
                        reparoAnterior = binding.txtReparoAnterior.text.toString(),
                        motivoRecall = binding.txtMotivoRecall.text.toString(),
                        observacaoRecall = binding.txtObsRecall.text.toString(),
                        cameraFrontSd = binding.checkCameraFrontSaidaR.isChecked,
                        cameraTrasSd = binding.checkCameraBackSaidaR.isChecked,
                        microfoneESd = binding.checkMicrofonesSaidaR.isChecked,
                        altoFalanteSd = binding.checkSomExternoSaidaR.isChecked,
                        redesSd = binding.checkRedesSaidaR.isChecked,
                        chipSd = binding.checkChipSaidaR.isChecked,
                        carregamentoSd = binding.checkCarregamentoSaidaR.isChecked,
                        sensorSd = binding.checkSensorSaidaR.isChecked,
                        identificadorSd = binding.checkIndentificacaoSaidaR.isChecked,
                        observacaoSD = binding.txtObsSaidaR.text.toString(),
                        aparelho = binding.textAparelhoRecall.text.toString(),

                        endereco = null,
                        contato = null,
                        defeito = null,
                        rAvancado = false,
                        cameraFrontEtd = false,
                        cameraTrasEtd = false,
                        microfonesEtd = false,
                        altoFalanteEtd = false,
                        redesEtd = false,
                        chipEtd = false,
                        carregamentoEtd = false,
                        sensorEtd = false,
                        identificadorEtd = false,
                        observacaoEtd = null,
                        pgEntrada = null,
                        pgTotal = null,
                        pgFinal = null,
                        formaPg = null
                    )
                    dao.insert(checklistRecall)

                } else {
                    val recallId = dao.pegarChecklistPorID(checklistId)
                    val suporte = DadosChecklist(
                        id = recallId.id,
                        dataChecklist = binding.txtDataRecall.text.toString(),
                        nome = binding.txtNomeRecall.text.toString(),
                        reparoId = binding.txtIdRecall.text.toString(),
                        reparoAnterior = binding.txtReparoAnterior.text.toString(),
                        motivoRecall = binding.txtMotivoRecall.text.toString(),
                        observacaoRecall = binding.txtObsRecall.text.toString(),
                        cameraFrontSd = binding.checkCameraFrontSaidaR.isChecked,
                        cameraTrasSd = binding.checkCameraBackSaidaR.isChecked,
                        microfoneESd = binding.checkMicrofonesSaidaR.isChecked,
                        altoFalanteSd = binding.checkSomExternoSaidaR.isChecked,
                        redesSd = binding.checkRedesSaidaR.isChecked,
                        chipSd = binding.checkChipSaidaR.isChecked,
                        carregamentoSd = binding.checkCarregamentoSaidaR.isChecked,
                        sensorSd = binding.checkSensorSaidaR.isChecked,
                        identificadorSd = binding.checkIndentificacaoSaidaR.isChecked,
                        observacaoSD = binding.txtObsSaidaR.text.toString(),
                        aparelho = binding.textAparelhoRecall.text.toString(),

                        endereco = null,
                        contato = null,
                        defeito = null,
                        rAvancado = false,
                        cameraFrontEtd = false,
                        cameraTrasEtd = false,
                        microfonesEtd = false,
                        altoFalanteEtd = false,
                        redesEtd = false,
                        chipEtd = false,
                        carregamentoEtd = false,
                        sensorEtd = false,
                        identificadorEtd = false,
                        observacaoEtd = null,
                        pgEntrada = null,
                        pgTotal = null,
                        pgFinal = null,
                        formaPg = null)

                    dao.update(suporte)
                }

                runOnUiThread{
                    finish()
                }

            }.start()

        }

    }

    private fun pegarChecklistPorId (checkId: Int){
        if (checkId== -1){
            return
        }

        Thread{
            val app = application as App
            val dao = app.db.checklistDao()
            val cliente = dao.pegarChecklistPorID(checkId)

            runOnUiThread{
                binding.txtDataRecall.setText(cliente.dataChecklist)
                binding.txtNomeRecall.setText(cliente.nome)
                binding.txtIdRecall.setText(cliente.reparoId)
                binding.textAparelhoRecall.setText(cliente.aparelho)
                binding.txtReparoAnterior.setText(cliente.reparoAnterior)
                binding.txtMotivoRecall.setText(cliente.motivoRecall)
                binding.txtObsRecall.setText(cliente.observacaoRecall)
                binding.checkCameraFrontSaidaR.isChecked = (cliente.cameraTrasSd)
                binding.checkCameraBackSaidaR.isChecked = (cliente.cameraTrasSd)
                binding.checkMicrofonesSaidaR.isChecked = (cliente.microfoneESd)
                binding.checkSomExternoSaidaR.isChecked = (cliente.altoFalanteSd)
                binding.checkRedesSaidaR.isChecked = (cliente.redesSd)
                binding.checkChipSaidaR.isChecked = (cliente.chipSd)
                binding.checkCarregamentoSaidaR.isChecked = (cliente.carregamentoSd)
                binding.checkSensorSaidaR.isChecked = (cliente.sensorSd)
                binding.checkIndentificacaoSaidaR.isChecked =(cliente.identificadorSd)
                binding.txtObsSaidaR.setText(cliente.observacaoSD)
            }

        }.start()
    }

    fun validate(): Boolean {

        return (binding.txtNomeRecall.text.toString().isNotEmpty() &&
            binding.txtIdRecall.text.toString().isNotEmpty() &&
            binding.txtReparoAnterior.text.toString().isNotEmpty() &&
            binding.txtMotivoRecall.text.toString().isNotEmpty()
        )

    }
    private fun showSnackBar(view: View) {

        Snackbar.make(view, "Preencha todas as informações necessárias", Snackbar.LENGTH_SHORT)
            .setActionTextColor(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white)))
            .setBackgroundTint(Color.RED)
            .show()
    }

}
