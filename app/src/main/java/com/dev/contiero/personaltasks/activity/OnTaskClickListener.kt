package com.dev.contiero.personaltasks.activity

sealed interface OnTaskClickListener {
    fun onTaskClick(position: Int)

    // Funções abstratas relacionadas ao menu de contexto. Poderiam estar numa interface separada e
    // mais específica.
    fun onRemoveTaskMenuItemClick(position: Int)
    fun onEditTaskMenuItemClick(position: Int)
    fun onCheckBoxClick(position: Int)
}