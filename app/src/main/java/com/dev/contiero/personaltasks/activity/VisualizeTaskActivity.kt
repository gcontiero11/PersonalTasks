package com.dev.contiero.personaltasks.activity

import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.dev.contiero.personaltasks.databinding.ActivityVisualizeTaskBinding
import com.dev.contiero.personaltasks.model.Constant.TASK
import com.dev.contiero.personaltasks.utils.DateTimeConverter
import com.dev.contiero.personaltasks.model.Task

class VisualizeTaskActivity : AppCompatActivity() {
    private val visualizeTaskBinding: ActivityVisualizeTaskBinding by lazy{
        ActivityVisualizeTaskBinding.inflate(layoutInflater)
    }
    private val converter = DateTimeConverter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContentView(visualizeTaskBinding.root)
        setSupportActionBar(visualizeTaskBinding.visualizeTaskToolbar.visualizeTaskActivityToolBar)

        val receivedTask =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                intent.getParcelableExtra(TASK, Task::class.java)
            } else {
                intent.getParcelableExtra(TASK)
            }

        receivedTask?.let { // if enters in this block means that the MainActivity sent a Task to this activity
            with(visualizeTaskBinding) {
                visualizeTaskToolbar.visualizeTaskActivityToolBar.title = receivedTask.title
                taskDescriptionInfoTv.text = receivedTask.description
                taskDateInfoTv.text = receivedTask.dateTime.toLocalDate().format(converter.dateFormatter)
                taskTimeInfoTv.text = receivedTask.dateTime.toLocalTime().format(converter.timeFormatter)
            }

        }
        visualizeTaskBinding.visualizeTaskBackBtn.setOnClickListener{
            finish()
        }
    }
}