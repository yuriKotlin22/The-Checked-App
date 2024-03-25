package com.example.thechecked20.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity
data class DadosChecklist(

    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo("data_format") val data: Date = Date(),

    @ColumnInfo("data") val dataChecklist: String,
    @ColumnInfo("nome") val nome: String,
    @ColumnInfo("endreco") val endereco: String?,
    @ColumnInfo("contato") val contato: String?,
    @ColumnInfo("aparelho") val aparelho: String,
    @ColumnInfo("defeito") val defeito: String? =  null,

    @ColumnInfo("camera_front_etd") val cameraFrontEtd: Boolean,
    @ColumnInfo("reparo_avancado") val rAvancado: Boolean,
    @ColumnInfo("camera_tras_etd") val cameraTrasEtd: Boolean,
    @ColumnInfo("microfones_etd") val microfonesEtd: Boolean,
    @ColumnInfo("alto_falante_etd") val altoFalanteEtd: Boolean,
    @ColumnInfo("redes_etd") val redesEtd: Boolean,
    @ColumnInfo("chip_opdr_etd") val chipEtd: Boolean,
    @ColumnInfo("carregamento_etd") val carregamentoEtd: Boolean,
    @ColumnInfo("sensor_etd") val sensorEtd: Boolean,
    @ColumnInfo("identificador_etd") val identificadorEtd: Boolean,
    @ColumnInfo("observacao_etd") val observacaoEtd: String?,

    @ColumnInfo("pg_total") val pgTotal: String?,
    @ColumnInfo("pg_entrada") val pgEntrada: String?,
    @ColumnInfo("forma_pg") val formaPg: String?,
    @ColumnInfo("pg_final") val pgFinal: String?,

    @ColumnInfo("camera_front_sd") val cameraFrontSd: Boolean,
    @ColumnInfo("camera_tras_sd") val cameraTrasSd: Boolean,
    @ColumnInfo("microfone_sd") val microfoneESd: Boolean,
    @ColumnInfo("alto_falante_sd") val altoFalanteSd: Boolean,
    @ColumnInfo("redes_sd") val redesSd: Boolean,
    @ColumnInfo("chip_opdr_sd") val chipSd: Boolean,
    @ColumnInfo("carregamento_sd") val carregamentoSd: Boolean,
    @ColumnInfo("sensor_sd") val sensorSd: Boolean,
    @ColumnInfo("identificador_sd") val identificadorSd: Boolean,
    @ColumnInfo("observacao_sd") val observacaoSD: String,

    //Recall
    @ColumnInfo("id_reparo") val reparoId: String?,
    @ColumnInfo("reparo_ant") val reparoAnterior: String?,
    @ColumnInfo("motivo_recall") val motivoRecall: String?,
    @ColumnInfo("observacao_recall") val observacaoRecall: String?,
) : java.io.Serializable
