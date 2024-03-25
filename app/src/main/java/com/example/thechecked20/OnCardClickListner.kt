package com.example.thechecked20

import com.example.thechecked20.model.DadosChecklist

interface OnCardClickListner {

    fun onLongClick ( position: Int, card: DadosChecklist)
}