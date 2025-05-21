package com.dev.contiero.personaltasks.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
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

    private lateinit var resultLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContentView(mainBinding.root)
        setSupportActionBar(mainBinding.includedToolBar.mainActivityToolBar)

        val task = Task(1,"Gustavo","Gomes Contiero")
        tasks.add(task)
        taskAdapter.notifyItemInserted(tasks.lastIndex)
        tasks.add(task)
        taskAdapter.notifyItemInserted(tasks.lastIndex)
        println(tasks)


        resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                println("Tudo certo no retorno")
            }
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
        if(item.itemId == R.id.insert_option){
            resultLauncher.launch(Intent(this,CreateTaskActivity::class.java))
            return true
        }
        return true

    }
}


