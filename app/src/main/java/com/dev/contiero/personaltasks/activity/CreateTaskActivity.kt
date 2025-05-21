package com.dev.contiero.personaltasks.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.dev.contiero.personaltasks.databinding.ActivityCreateTaskBinding
import com.dev.contiero.personaltasks.model.Constant.TASK
import com.dev.contiero.personaltasks.model.Task

//import com.dev.contiero.personaltasks.model.Constant.TASK

class CreateTaskActivity : AppCompatActivity() {
    private val createTaskBinding: ActivityCreateTaskBinding by lazy {
        ActivityCreateTaskBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContentView(createTaskBinding.root)
        setSupportActionBar(createTaskBinding.createTaskToolbarIn.createTaskActivityToolBar)
        supportActionBar?.subtitle = "New contact"

        val receivedTask =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                intent.getParcelableExtra(TASK, Task::class.java)
            } else {
                intent.getParcelableExtra(TASK)
            }

        receivedTask?.let { // if enters in this block means that the MainActivity sent a Task to this activity
            with(createTaskBinding){
                taskTitleEt.setText(receivedTask.title)
                taskDescriptionEt.setText(receivedTask.description)
            }

        }
        with(createTaskBinding) {
            submitBtn.setOnClickListener {
                Task(
                    receivedTask?.id ?: hashCode(),
                    taskTitleEt.text.toString().trim(),
                    taskDescriptionEt.text.toString().trim()
                ).let { contact ->
                    Intent().apply {
                        putExtra(TASK, contact)
                        setResult(RESULT_OK, this)
                    }
                }
                finish()
            }
        }
    }
}
