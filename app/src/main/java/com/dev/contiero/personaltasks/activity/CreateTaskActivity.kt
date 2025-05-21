package com.dev.contiero.personaltasks.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.dev.contiero.personaltasks.R
import com.dev.contiero.personaltasks.databinding.ActivityCreateTaskBinding

class CreateTaskActivity : AppCompatActivity() {
    private val createTaskBinding: ActivityCreateTaskBinding by lazy{
        ActivityCreateTaskBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContentView(createTaskBinding.root)
        setSupportActionBar(createTaskBinding.createTaskToolbarIn.createTaskActivityToolBar)

    }
}