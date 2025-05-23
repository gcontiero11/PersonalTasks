package com.dev.contiero.personaltasks.persistence

import android.content.ContentValues
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.database.Cursor
import com.dev.contiero.personaltasks.model.Task
import java.time.LocalDateTime

class TaskSqlite(context: Context) : TaskDao {
    companion object {
        private const val DB_NAME = "personal_tasks_db"
        private const val TASK_TABLE = "task"

        private const val COL_ID = "id"
        private const val COL_TITLE = "title"
        private const val COL_DESC = "description"
        private const val COL_DATE = "date"
        private const val COL_DONE = "done"

        val CREATE_SQL = """
            CREATE TABLE IF NOT EXISTS $TASK_TABLE (
              $COL_ID        TEXT    NOT NULL PRIMARY KEY,
              $COL_TITLE     TEXT    NOT NULL,
              $COL_DESC      TEXT    NOT NULL,
              $COL_DATE      TEXT    NOT NULL,
              $COL_DONE      INTEGER NOT NULL
            );
        """.trimIndent()
    }

    private val db = context
        .openOrCreateDatabase(DB_NAME, MODE_PRIVATE, null)
        .also { it.execSQL(CREATE_SQL) }

    override fun findAll(): MutableList<Task> {
        val tasks = mutableListOf<Task>()
        db.rawQuery("SELECT * FROM $TASK_TABLE;", null).use { cursor ->
            while (cursor.moveToNext()) {
                tasks.add(cursor.toTask())
            }
        }
        return tasks
    }

    override fun findById(id: Int): Task {
        val cursor = db.query(
            TASK_TABLE,
            null,
            "$COL_ID = ?",
            arrayOf(id.toString()),
            null,
            null,
            null
        )

        cursor.use {
            if (it.moveToFirst()) {
                return it.toTask()
            } else {
                throw NoSuchElementException("Task with publicId=$id not found")
            }
        }
    }

    override fun saveTask(task: Task): Long {
        return db.insert(TASK_TABLE, null, task.toContentValues())
    }

    override fun deleteTask(id: Int): Int = db.delete(
        TASK_TABLE,
        "$COL_ID = ?",
        arrayOf(id.toString())
    )

    override fun updateTask(task: Task): Int = db.update(
        TASK_TABLE,
        task.toContentValues(),
        "$COL_ID = ?",
        arrayOf(task.id.toString())
    )

    override fun clear(): Int =db.delete(TASK_TABLE,"1 = 1", arrayOf())

    private fun Task.toContentValues() = ContentValues().apply {
        put(COL_ID, id)
        put(COL_TITLE, title)
        put(COL_DESC, description)
        put(COL_DATE, dateTime.toString())
        put(COL_DONE, if (isDone) 1 else 0)
    }


    private fun Cursor.toTask(): Task {
        val id = getInt(getColumnIndexOrThrow(COL_ID))
        val title = getString(getColumnIndexOrThrow(COL_TITLE))
        val desc = getString(getColumnIndexOrThrow(COL_DESC))
        val dateStr = getString(getColumnIndexOrThrow(COL_DATE))
        val doneInt = getInt(getColumnIndexOrThrow(COL_DONE))

        return Task(
            id = id,
            title = title,
            description = desc,
            dateTime = LocalDateTime.parse(dateStr),
            isDone = (doneInt == 1)
        )
    }
}