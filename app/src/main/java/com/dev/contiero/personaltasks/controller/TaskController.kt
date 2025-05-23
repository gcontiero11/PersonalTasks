package com.dev.contiero.personaltasks.controller

import com.dev.contiero.personaltasks.activity.MainActivity
import com.dev.contiero.personaltasks.model.Task
import com.dev.contiero.personaltasks.persistence.TaskDao
import com.dev.contiero.personaltasks.persistence.TaskSqlite

class TaskController(mainActivity: MainActivity) {
    private val taskDao: TaskDao = TaskSqlite(mainActivity)

    fun insertTask(task: Task) = taskDao.saveTask(task)
    fun getTask(id: Int) = taskDao.findById(id)
    fun getTasks() = taskDao.findAll()
    fun modifyTask(task: Task) = taskDao.updateTask(task)
    fun removeTask(id: Int) = taskDao.deleteTask(id)
}