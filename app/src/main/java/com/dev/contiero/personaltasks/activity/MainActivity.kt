package com.dev.contiero.personaltasks.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.dev.contiero.personaltasks.R
import com.dev.contiero.personaltasks.adapter.TaskRecycleViewAdapter
import com.dev.contiero.personaltasks.controller.TaskController
import  com.dev.contiero.personaltasks.databinding.ActivityMainBinding
import com.dev.contiero.personaltasks.model.Constant.TASK
import com.dev.contiero.personaltasks.model.Task
import java.time.LocalDateTime

class MainActivity : AppCompatActivity(), OnTaskClickListener {
    private val mainBinding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private var tasks: MutableList<Task> = mutableListOf()

    private val taskAdapter: TaskRecycleViewAdapter by lazy {
        TaskRecycleViewAdapter(tasks, this)
    }

    private val taskController: TaskController by lazy {
        TaskController(this)
    }

    private lateinit var activityHandler: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

//        taskController.deleteAll()

        setContentView(mainBinding.root)
        setSupportActionBar(mainBinding.includedToolBar.mainActivityToolBar)

        fillContactList()

        activityHandler =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == RESULT_OK) {
                    val receivedTask = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        result.data?.getParcelableExtra(TASK, Task::class.java)
                    } else result.data?.getParcelableExtra(TASK)
                    receivedTask?.let {
                        val position = tasks.indexOfFirst { it.id == receivedTask.id }
                        if (position == -1) {
                            tasks.add(receivedTask)
                            taskAdapter.notifyItemInserted(tasks.lastIndex)
                            taskController.insertTask(receivedTask)
                        }
                        else{
                            tasks[position] = receivedTask
                            taskAdapter.notifyItemChanged(position)
                            taskController.modifyTask(receivedTask)
                        }
                    }
                }
            }

        mainBinding.taskRv.adapter = taskAdapter
        mainBinding.taskRv.layoutManager = LinearLayoutManager(this)
        mainBinding.taskRv.setHasFixedSize(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_activity_toolbar_menu_context, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.insert_option) {
            activityHandler.launch(Intent(this, CreateTaskActivity::class.java))
            return true
        }
        if (item.itemId == R.id.delete_all_checked_option) {
            for(task in tasks){
                if(task.isDone)
                    taskController.removeTask(task.id!!)
            }
            tasks.removeAll{it.isDone}
            println(tasks)
            taskAdapter.notifyDataSetChanged()
            return true
        }
        return true

    }

    override fun onTaskClick(position: Int) {
        Intent(this, VisualizeTaskActivity::class.java).apply {
            putExtra(TASK, tasks[position])
            activityHandler.launch(this)
        }
    }

    override fun onRemoveTaskMenuItemClick(position: Int) {
        taskController.removeTask(tasks[position].id!!)
        tasks.removeAt(position)
        taskAdapter.notifyItemRemoved(position)
        Toast.makeText(this, "Contact removed!", Toast.LENGTH_SHORT).show()
    }

    override fun onEditTaskMenuItemClick(position: Int) {
        Intent(this, CreateTaskActivity::class.java).apply {
            putExtra(TASK, tasks[position])
            activityHandler.launch(this)
        }
    }

    override fun onCheckBoxClick(position: Int) {
        tasks[position].isDone = !tasks[position].isDone
        println(tasks)
    }

    private fun fillContactList() {
        tasks.clear()
        Thread {
            tasks.addAll(taskController.getTasks())
            taskAdapter.notifyDataSetChanged()
        }.start()
    }
}


