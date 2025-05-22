package com.dev.contiero.personaltasks.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dev.contiero.personaltasks.databinding.SimpleTaskBinding
import com.dev.contiero.personaltasks.model.Task
import java.time.format.DateTimeFormatter
import java.util.Locale

class TaskRecycleViewAdapter(private var tasks: MutableList<Task>):
    RecyclerView.Adapter<TaskRecycleViewAdapter.TaskAdapterViewHolder>() {
    private val dateFormatter = DateTimeFormatter.ofPattern("MM-dd-yyyy", Locale.getDefault())
    private val timeFormatter = DateTimeFormatter.ofPattern("HH:mm", Locale.getDefault())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskAdapterViewHolder =
        TaskAdapterViewHolder(
            SimpleTaskBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun getItemCount(): Int = tasks.size

    override fun onBindViewHolder(holder: TaskAdapterViewHolder, position: Int) {
        tasks[position].let{ task ->
            holder.rvTaskTitle.text =  task.title
            holder.rvTaskDate.text = task.dateTime.toLocalDate().format(dateFormatter)
            holder.rvTaskTime.text = task.dateTime.toLocalTime().format(timeFormatter)
        }
    }

    inner class TaskAdapterViewHolder(simpleTaskBinding: SimpleTaskBinding):
        RecyclerView.ViewHolder(simpleTaskBinding.root) {
        val rvTaskTitle: TextView = simpleTaskBinding.taskTitle
        val rvTaskDate: TextView = simpleTaskBinding.taskDateTv
        val rvTaskTime: TextView = simpleTaskBinding.taskTimeTv
    }
}