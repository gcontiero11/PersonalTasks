package com.dev.contiero.personaltasks.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.dev.contiero.personaltasks.R
import com.dev.contiero.personaltasks.activity.OnTaskClickListener
import com.dev.contiero.personaltasks.databinding.SimpleTaskBinding
import com.dev.contiero.personaltasks.utils.DateTimeConverter
import com.dev.contiero.personaltasks.model.Task

class TaskRecycleViewAdapter(
    private var tasks: MutableList<Task>,
    private val clickHandler: OnTaskClickListener
):
    RecyclerView.Adapter<TaskRecycleViewAdapter.TaskAdapterViewHolder>() {
    private val converter = DateTimeConverter()

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
            holder.rvTaskDate.text = task.dateTime.toLocalDate().format(converter.dateFormatter)
            holder.rvTaskTime.text = task.dateTime.toLocalTime().format(converter.timeFormatter)
            holder.rvCheckBox.isChecked = task.isDone
        }
    }

    inner class TaskAdapterViewHolder(simpleTaskBinding: SimpleTaskBinding):
        RecyclerView.ViewHolder(simpleTaskBinding.root) {
        val rvTaskTitle: TextView = simpleTaskBinding.taskTitle
        val rvTaskDate: TextView = simpleTaskBinding.taskDateTv
        val rvTaskTime: TextView = simpleTaskBinding.taskTimeTv
        val rvCheckBox: CheckBox = simpleTaskBinding.itemCheckbox
        init{
            // Criando o menu de contexto para cada célula associada a um novo holder
            simpleTaskBinding.root.setOnCreateContextMenuListener{ menu, v, menuInfo ->
                (clickHandler as AppCompatActivity).menuInflater.inflate(R.menu.task_menu, menu)
                menu.findItem(R.id.edit_option).setOnMenuItemClickListener {
                    clickHandler.onEditTaskMenuItemClick(adapterPosition)
                    true
                }
                menu.findItem(R.id.remove_option).setOnMenuItemClickListener {
                    clickHandler.onRemoveTaskMenuItemClick(adapterPosition)
                    true
                }
            }
            setClickListenerForEachTask(simpleTaskBinding)
            setCheckBoxClickListenerForEachTask(simpleTaskBinding)
        }
        private fun setClickListenerForEachTask(simpleTaskBinding: SimpleTaskBinding) {
                simpleTaskBinding.root.setOnClickListener { clickHandler.onTaskClick(adapterPosition) }
        }
        private fun setCheckBoxClickListenerForEachTask(simpleTaskBinding: SimpleTaskBinding){
            simpleTaskBinding.itemCheckbox.setOnClickListener {
                println("Clicou no CHECK BOX")
                clickHandler.onCheckBoxClick(adapterPosition)
            }

        }
    }
}