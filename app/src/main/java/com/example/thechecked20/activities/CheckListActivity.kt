package com.example.thechecked20.activities

import android.Manifest
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.text.TextPaint
import android.view.View
import android.widget.CompoundButton
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.thechecked20.App
import com.example.thechecked20.R
import com.example.thechecked20.databinding.ActivityChecklistBinding
import com.example.thechecked20.model.DadosChecklist
import com.google.android.material.snackbar.Snackbar
import java.io.File
import java.io.FileOutputStream

class CheckListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChecklistBinding
    private lateinit var tituloEntrada: TextView
    private lateinit var tituloSaida: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChecklistBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val checklistId = intent?.extras?.getInt("checklistId", -1) ?: throw Exception("ERRO") // -> pega um valor inteiro


        pegarChecklistPorId(checklistId)

        val btnPdf = binding.btnPdf
        btnPdf.setOnClickListener {

            if (binding.formaPag.text.toString().isNotEmpty() &&
                binding.pagRestante.text.toString().isNotEmpty()
            ) {
                generarPdf()
            }
            else {
                showSnackBarPdf(it)
            }

        }


        binding.fabSalvar.setOnClickListener {
            binding.txtData.text.toString()

            if (!validate()) {
                showSnackBar(it)
                return@setOnClickListener
            }

            Thread {
                val app = application as App
                val dao = app.db.checklistDao()

                if (checklistId == -1) {
                    val checklist = DadosChecklist(
                        dataChecklist = binding.txtData.text.toString(),
                        nome = binding.txtNome.text.toString(),
                        endereco = binding.txtEndereco.text.toString(),
                        contato = binding.txtContato.text.toString(),
                        aparelho = binding.txtAparelho.text.toString(),
                        defeito = binding.txtDefeitos.text.toString(),
                        cameraFrontEtd = binding.checkCameraFront.isChecked,
                        rAvancado = binding.checkRAvancado.isChecked,
                        cameraTrasEtd = binding.checkCameraBack.isChecked,
                        microfonesEtd = binding.checkMicrofones.isChecked,
                        altoFalanteEtd = binding.checkSomExterno.isChecked,
                        redesEtd = binding.checkRedes.isChecked,
                        chipEtd = binding.checkChip.isChecked,
                        carregamentoEtd = binding.checkCarregamento.isChecked,
                        sensorEtd = binding.checkSensor.isChecked,
                        identificadorEtd = binding.checkId.isChecked,
                        observacaoEtd = binding.txtObs.text.toString(),
                        pgEntrada = binding.valorEntrada.text.toString(),
                        pgTotal = binding.valorTotal.text.toString(),
                        pgFinal = binding.pagRestante.text.toString(),
                        formaPg = binding.formaPag.text.toString(),
                        cameraFrontSd = binding.checkCameraFrontSaida.isChecked,
                        cameraTrasSd = binding.checkCameraBackSaida.isChecked,
                        microfoneESd = binding.checkMicrofonesSaida.isChecked,
                        altoFalanteSd = binding.checkSomExternoSaida.isChecked,
                        redesSd = binding.checkRedesSaida.isChecked,
                        chipSd = binding.checkChipSaida.isChecked,
                        carregamentoSd = binding.checkCarregamentoSaida.isChecked,
                        sensorSd = binding.checkSensorSaida.isChecked,
                        identificadorSd = binding.checkIdSaida.isChecked,
                        observacaoSD = binding.txtObsSaida.text.toString(),
                        reparoId = null,
                        reparoAnterior = null,
                        motivoRecall = null,
                        observacaoRecall = null
                    )
                    dao.insert(checklist)

                } else {
                    val cliente = dao.pegarChecklistPorID(checklistId)
                    val suporte = DadosChecklist(
                        id = cliente.id,
                        dataChecklist = cliente.dataChecklist,
                        nome = cliente.nome,
                        endereco = binding.txtEndereco.text.toString(),
                        contato = binding.txtContato.text.toString(),
                        aparelho = binding.txtAparelho.text.toString(),
                        defeito = binding.txtDefeitos.text.toString(),
                        rAvancado = binding.checkRAvancado.isChecked,
                        cameraFrontEtd = binding.checkCameraFront.isChecked,
                        cameraTrasEtd = binding.checkCameraBack.isChecked,
                        microfonesEtd = binding.checkMicrofones.isChecked,
                        altoFalanteEtd = binding.checkSomExterno.isChecked,
                        redesEtd = binding.checkRedes.isChecked,
                        chipEtd = binding.checkChip.isChecked,
                        carregamentoEtd = binding.checkCarregamento.isChecked,
                        sensorEtd = binding.checkSensor.isChecked,
                        identificadorEtd = binding.checkId.isChecked,
                        observacaoEtd = binding.txtObs.text.toString(),
                        pgEntrada = binding.valorEntrada.text.toString(),
                        pgTotal = binding.valorTotal.text.toString(),
                        pgFinal = binding.pagRestante.text.toString(),
                        formaPg = binding.formaPag.text.toString(),
                        cameraFrontSd = binding.checkCameraFrontSaida.isChecked,
                        cameraTrasSd = binding.checkCameraBackSaida.isChecked,
                        microfoneESd = binding.checkMicrofonesSaida.isChecked,
                        altoFalanteSd = binding.checkSomExternoSaida.isChecked,
                        redesSd = binding.checkRedesSaida.isChecked,
                        chipSd = binding.checkChipSaida.isChecked,
                        carregamentoSd = binding.checkCarregamentoSaida.isChecked,
                        sensorSd = binding.checkSensorSaida.isChecked,
                        identificadorSd = binding.checkIdSaida.isChecked,
                        observacaoSD = binding.txtObsSaida.text.toString(),

                        reparoId = null,
                        reparoAnterior = null,
                        motivoRecall = null,
                        observacaoRecall = null,
                    )
                    dao.update(suporte)
                }

                runOnUiThread {
                    finish()
                }

            }.start()

        }

        if (checkPermission()) {
            Toast.makeText(this, "Permiso Aceptado", Toast.LENGTH_LONG)
        } else {
            requestPermissions()
        }


    }

    private fun pegarChecklistPorId(checkId: Int) {
        if (checkId == -1) {
            return
        }

        Thread {
            val app = application as App
            val dao = app.db.checklistDao()
            val cliente = dao.pegarChecklistPorID(checkId)

            runOnUiThread {
                binding.txtData.setText(cliente.dataChecklist)
                binding.txtNome.setText(cliente.nome)
                binding.txtEndereco.setText(cliente.endereco)
                binding.txtContato.setText(cliente.contato)
                binding.txtAparelho.setText(cliente.aparelho)
                binding.txtDefeitos.setText(cliente.defeito)
                binding.checkRAvancado.isChecked = cliente.rAvancado
                binding.checkCameraFront.isChecked = cliente.cameraFrontEtd
                binding.checkCameraBack.isChecked = cliente.cameraTrasEtd
                binding.checkMicrofones.isChecked = cliente.microfonesEtd
                binding.checkSomExterno.isChecked = cliente.altoFalanteEtd
                binding.checkRedes.isChecked = cliente.redesEtd
                binding.checkChip.isChecked = cliente.chipEtd
                binding.checkCarregamento.isChecked = cliente.carregamentoEtd
                binding.checkSensor.isChecked = cliente.sensorEtd
                binding.checkId.isChecked = cliente.identificadorEtd
                binding.txtObs.setText(cliente.observacaoEtd)
                binding.valorEntrada.setText(cliente.pgEntrada)
                binding.valorTotal.setText(cliente.pgTotal)
                binding.formaPag.setText(cliente.formaPg)
                binding.pagRestante.setText(cliente.pgFinal)
                binding.checkCameraFrontSaida.isChecked = (cliente.cameraFrontSd)
                binding.checkCameraBackSaida.isChecked = (cliente.cameraTrasSd)
                binding.checkMicrofonesSaida.isChecked = (cliente.microfoneESd)
                binding.checkSomExternoSaida.isChecked = (cliente.altoFalanteSd)
                binding.checkRedesSaida.isChecked = (cliente.redesSd)
                binding.checkChipSaida.isChecked = (cliente.chipSd)
                binding.checkCarregamentoSaida.isChecked = (cliente.carregamentoSd)
                binding.checkSensorSaida.isChecked = (cliente.sensorSd)
                binding.checkIdSaida.isChecked = (cliente.identificadorSd)
                binding.txtObsSaida.setText(cliente.observacaoSD)
            }

        }.start()
    }

    fun validate(): Boolean { // não preenchidos
        return (
                binding.txtData.text.toString().isNotEmpty() &&
                        binding.txtNome.text.toString().isNotEmpty() &&
                        binding.txtContato.text.toString().isNotEmpty() &&
                        binding.txtEndereco.text.toString().isNotEmpty() &&
                        binding.txtAparelho.text.toString().isNotEmpty() &&
                        binding.txtDefeitos.text.toString().isNotEmpty() &&
                        binding.valorTotal.text.toString().isNotEmpty() &&
                        binding.valorEntrada.text.toString().isNotEmpty()
                )
    }

    private fun showSnackBar(view: View) {

        Snackbar.make(view, "Preencha os campos corretamente", Snackbar.LENGTH_SHORT)
            .setActionTextColor(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white)))
            .setBackgroundTint(Color.RED)
            .show()
    }
    private fun showSnackBarPdf(view: View) {

        Snackbar.make(view, "Preencha correntamente os campos de pagamento", Snackbar.LENGTH_SHORT)
            .setActionTextColor(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white)))
            .setBackgroundTint(Color.RED)
            .show()
    }


    // GERADOR DE PDF

    private fun checkPermission(): Boolean {
        val permission1 = ContextCompat.checkSelfPermission(
            applicationContext,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        val permission2 = ContextCompat.checkSelfPermission(
            applicationContext,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
        return permission1 == PackageManager.PERMISSION_GRANTED && permission2 == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ),
            200
        )
    }

    fun generarPdf() {
        

        var tituloText: String
        var descripcionText: String

        tituloEntrada = binding.tituloEntrada
        tituloSaida = binding.tituloSaida


        tituloText = "INFOMAÇÕES GERAIS DO SERVIÇO"

        descripcionText =
            "Cliente: " + binding.txtNome.text.toString() + "\nValor do Conserto: R$" + binding.valorTotal.text.toString() +
                    "\nAparelho: " + binding.txtAparelho.text.toString() + "\nSinal de Entrada: R$" + binding.valorEntrada.text.toString() +
                    "\nDefeito: " + binding.txtDefeitos.text.toString() + "\nPagamento de Final: R$" + binding.pagRestante.text.toString() +
                    "\nData: " + binding.txtData.text.toString() + "\nForma de Pagamento: " + binding.formaPag.text.toString() +
                    "\n" + "\n" +
                    "\n" + tituloEntrada.text.toString() + "➘" + "\n" + tituloSaida.text.toString() + "➘" +
                    "\n" + "\n" +
                    "\nCamera Frontal ➔  " + checkboxToString(binding.checkCameraFront) + "\nCamera Frontal (saída) ➔  " + checkboxToString(
                binding.checkCameraFrontSaida
            ) +
                    "\nCamera Traseira ➔  " + checkboxToString(binding.checkCameraBack) + "\nCamera Traseira (saída)➔  " + checkboxToString(
                binding.checkCameraBackSaida
            ) +
                    "\nMicrofone ➔  " + checkboxToString(binding.checkMicrofones) + "\nMicrofone (saída) ➔  " + checkboxToString(
                binding.checkMicrofonesSaida
            ) +
                    "\nAlto Falante ➔  " + checkboxToString(binding.checkSomExterno) + "\nAlto Falante (saída) ➔ " + checkboxToString(
                binding.checkSomExternoSaida
            ) +
                    "\nSensor de Proximidade ➔  " + checkboxToString(binding.checkSensor) + "\nSensor de Proximidade (saída)➔ " + checkboxToString(
                binding.checkSensorSaida
            ) +
                    "\nPorta de Carregamento ➔  " + checkboxToString(binding.checkCarregamento) + "\nPorta de Carregamento (saída)➔ " + checkboxToString(
                binding.checkCarregamentoSaida
            ) +
                    "\nWifi ➔  " + checkboxToString(binding.checkRedes) + "\nWifi (saída) ➔  " + checkboxToString(
                binding.checkRedesSaida
            ) +
                    "\nLeitor de Chip ➔  " + checkboxToString(binding.checkChip) + "\nLeitor de Chip (saída) ➔  " + checkboxToString(
                binding.checkChipSaida
            ) +
                    "\nBiometria ➔  " + checkboxToString(binding.checkId) + "\nBiometria (saída) ➔  " + checkboxToString(
                binding.checkIdSaida
            ) +
                    "\n" + "\n" +
                    "\nOBSERVAÇÕES ➘ " + binding.txtObsSaida.text.toString().chunked(60)
                .joinToString("\n\n")

        val rodapeText1 =
            "EXCLUSÃO DE GARANTIA ➘ A garantia não cobre danos causados por mau uso, negligência, "
        val rodapeText2 =
            " modificações não autorizadas,exposição a condições extremas, quedas, contato com líquidos,"
        val rodapeText3 =
            " desgaste normal ou quaisquer outras circunstâncias além do controle da Empresa. "
        val rodapeText4 =
            " Além disso, a garantia será considerada inválida se o Cliente tentar reparar ou modificar "
        val rodapeText5 =
            " o smartphone por conta própria ou por terceiros não autorizados. "




        var pdfDocument = PdfDocument()
        var paint = Paint()
        var titulo = TextPaint()
        var descripcion = TextPaint()

        var paginaInfo = PdfDocument.PageInfo.Builder(816, 1054, 1).create()
        var pagina1 = pdfDocument.startPage(paginaInfo)

        var canvas = pagina1.canvas

        var bitmap = BitmapFactory.decodeResource(resources, R.drawable.logo_app)
        var bitmapEscala = Bitmap.createScaledBitmap(bitmap, 75, 75, false)
        canvas.drawBitmap(bitmapEscala, 368f, 16f, paint)

        titulo.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        titulo.textSize = 20f

        val canvasWidth = canvas.width
        val colWidth = canvasWidth / 2

        val textWidthTitulo = titulo.measureText(tituloText)
        val xTitulo = (canvasWidth - textWidthTitulo) / 2
        canvas.drawText(tituloText, xTitulo, 125f, titulo)

        val rodapeY = canvas.height - 70f
        val rodapeX = 10

        val lineHeight = 18f // Tamanho da fonte desejado

        paint.textSize = lineHeight

        val rodapeTexts = listOf(
            rodapeText1, rodapeText2, rodapeText3, rodapeText4, rodapeText5
        )

        for ((index, text) in rodapeTexts.withIndex()) {
            val rodapeYPosition = rodapeY + (index * lineHeight)
            canvas.drawText(text, rodapeX.toFloat(), rodapeYPosition, paint)
        }



        val arrDescripcion = descripcionText.split("\n")
        descripcion.textSize = 22f

        var y = 200f
        for ((index, item) in arrDescripcion.withIndex()) {
            val col = index % 2  // Determina a coluna atual
            val x = col * colWidth
            canvas.drawText(item, x + 16f, y, descripcion)

            if (col == 1) {
                y += 35
            }
        }

        pdfDocument.finishPage(pagina1)

        val fileName = "${binding.txtNome.text}.pdf"
        val documentsDirectory =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        val file = File(documentsDirectory, fileName)

        try {
            pdfDocument.writeTo(FileOutputStream(file))

            Toast.makeText(
                this,
                " O Comprovante de ${binding.txtNome.text} foi salvo com sucesso",
                Toast.LENGTH_LONG
            ).show()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }

        pdfDocument.close()

    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 200) {
            if (grantResults.size > 0) {
                val writeStorage = grantResults[0] == PackageManager.PERMISSION_GRANTED
                val readStorage = grantResults[1] == PackageManager.PERMISSION_GRANTED

                if (writeStorage && readStorage) {
                    Toast.makeText(this, "Permissão concedida", Toast.LENGTH_LONG)
                } else {
                    Toast.makeText(this, "Permissão não concedida", Toast.LENGTH_LONG)
                    finish()
                }
            }
        }
    }

    fun checkboxToString(checkbox: CompoundButton): String {
        return if (checkbox.isChecked) {
            "[ OK ]"
        } else {
            "[ X ]"
        }
    }

}