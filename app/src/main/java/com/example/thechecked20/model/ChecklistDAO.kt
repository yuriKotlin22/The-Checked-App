package com.example.thechecked20.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ChecklistDAO {

    @Insert
    fun insert(checklist: DadosChecklist)

    @Delete
    fun delete(checklist: DadosChecklist): Int

    @Update
    fun update(checklist: DadosChecklist)

    @Query("SELECT * FROM DadosChecklist WHERE defeito IS NOT NULL AND reparo_avancado = 0 ")
    fun pegarTodosChecklist(): List<DadosChecklist>

    @Query("SELECT * FROM DadosChecklist WHERE defeito IS NULL")
    fun pegarRecalls(): List<DadosChecklist>

    @Query("SELECT * FROM DadosChecklist WHERE reparo_avancado =:rAvancado")
    fun pegarRAvancado(rAvancado: Boolean): List<DadosChecklist>

    @Query("SELECT * FROM DadosChecklist WHERE id= :id")
    fun pegarChecklistPorID(id:Int): DadosChecklist

    @Query("SELECT * FROM DadosChecklist WHERE nome =:nome")
    fun pegarChecklistPorNome(nome:String): List<DadosChecklist>

//@Query("SELECT * FROM Reparo WHERE reparoId IS NOT NULL")
//    fun buscarReparosPreenchidos(): List<Reparo>

}