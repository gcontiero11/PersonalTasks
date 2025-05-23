package com.dev.contiero.personaltasks.persistence

import com.dev.contiero.personaltasks.model.Task

interface TaskDao {
    fun findAll(): MutableList<Task>
    fun findById(id: Int): Task
    fun saveTask(task: Task): Long
    fun deleteTask(id: Int): Int
    fun updateTask(task: Task): Int
    fun clear(): Int
}