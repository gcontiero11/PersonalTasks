package com.dev.contiero.personaltasks.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.dev.contiero.personaltasks.R
import com.dev.contiero.personaltasks.adapter.TaskRecycleViewAdapter
import  com.dev.contiero.personaltasks.databinding.ActivityMainBinding
import com.dev.contiero.personaltasks.model.Task

class MainActivity : AppCompatActivity() {
    private val mainBinding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val tasks: MutableList<Task> = mutableListOf()

    private val taskAdapter: TaskRecycleViewAdapter by lazy {
        TaskRecycleViewAdapter(tasks)
    }


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContentView(mainBinding.root)
        setSupportActionBar(mainBinding.toolbarIn.toolbar)

        val task = Task(1,"Gustavo","Gomes Contiero")
        tasks.add(task)
        taskAdapter.notifyItemInserted(tasks.lastIndex)
        tasks.add(task)
        taskAdapter.notifyItemInserted(tasks.lastIndex)
        println(tasks)



        ViewCompat.setOnApplyWindowInsetsListener(mainBinding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        mainBinding.taskRv.adapter = taskAdapter
        mainBinding.taskRv.layoutManager = LinearLayoutManager(this)
        mainBinding.taskRv.setHasFixedSize(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.task_menu_context,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        println("CLICOOU")
        return true
    }
}


