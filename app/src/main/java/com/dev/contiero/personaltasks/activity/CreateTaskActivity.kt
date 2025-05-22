package com.dev.contiero.personaltasks.activity

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.dev.contiero.personaltasks.databinding.ActivityCreateTaskBinding
import com.dev.contiero.personaltasks.model.Constant.TASK
import com.dev.contiero.personaltasks.model.Task
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

class CreateTaskActivity : AppCompatActivity() {
    private val createTaskBinding: ActivityCreateTaskBinding by lazy {
        ActivityCreateTaskBinding.inflate(layoutInflater)
    }

    private val calendar = Calendar.getInstance()
    private val dateFormatter = DateTimeFormatter.ofPattern("MM-dd-yyyy", Locale.getDefault())
    private val timeFormatter = DateTimeFormatter.ofPattern("HH:mm", Locale.getDefault())

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
            with(createTaskBinding) {
                taskTitleEt.setText(receivedTask.title)
                taskDescriptionEt.setText(receivedTask.description)
            }

        }
        with(createTaskBinding) {
            submitBtn.setOnClickListener {
                val dateTime = LocalDateTime.of(
                    LocalDate.parse(taskDateEt.text.toString(),dateFormatter),
                    LocalTime.parse(taskTimeEt.text.toString(), timeFormatter)
                )

                Task(
                    receivedTask?.id ?: hashCode(),
                    taskTitleEt.text.toString().trim(),
                    taskDescriptionEt.text.toString().trim(),
                    dateTime
                ).let { contact ->
                    Intent().apply {
                        putExtra(TASK, contact)
                        setResult(RESULT_OK, this)
                    }
                }
                finish()
            }
            cancelBtn.setOnClickListener {
                finish()
            }
            taskDateEt.setText(
                dateFormatter.format(
                    LocalDateTime.now()
                )
            )
            taskTimeEt.setText(
                timeFormatter.format(
                    LocalDateTime.now().plusHours(1)
                )
            ) // deixa o valor inicial do campo para daqui uma hora
        }
        createTaskBinding.taskDateEt.setOnClickListener {
            val dateSetListener =
                DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                    calendar.set(Calendar.YEAR, year)
                    calendar.set(Calendar.MONTH, month)
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                    createTaskBinding.taskDateEt.setText(
                        LocalDateTime.ofInstant(
                            calendar.time.toInstant(),
                            ZoneId.systemDefault()
                        ).format(dateFormatter)
                    )
                }

            DatePickerDialog(
                this,
                dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        createTaskBinding.taskTimeEt.setOnClickListener {
            val timeSetListener = TimePickerDialog.OnTimeSetListener { _, hour, minute ->
                calendar.set(Calendar.HOUR_OF_DAY, hour)
                calendar.set(Calendar.MINUTE, minute)

                createTaskBinding.taskTimeEt.setText(
                    LocalDateTime.ofInstant(
                        calendar.time.toInstant(),
                        ZoneId.systemDefault()
                    ).format(timeFormatter)
                )
            }

            TimePickerDialog(
                this,
                timeSetListener,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true
            ).show()
        }

    }
}