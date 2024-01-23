package com.joaorodrigues.tasks.view.viewholder

import android.annotation.SuppressLint
import android.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.joaorodrigues.tasks.R
import com.joaorodrigues.tasks.databinding.RowTaskListBinding
import com.joaorodrigues.tasks.service.listener.TaskListener
import com.joaorodrigues.tasks.service.model.TaskModel
import java.text.SimpleDateFormat

class TaskViewHolder(private val itemBinding: RowTaskListBinding, val listener: TaskListener) :
    RecyclerView.ViewHolder(itemBinding.root) {


    @SuppressLint("SimpleDateFormat")
    fun bindData(task: TaskModel) {

        val date = SimpleDateFormat("yyyy-MM-dd").parse(task.dueDate)
        itemBinding.textDueDate.text = date?.let { SimpleDateFormat("dd/MM/yyyy").format(it) }

        itemBinding.textDescription.text = task.description.uppercase()
        itemBinding.textPriority.text = task.priorityDescription

        itemBinding.textDescription.setOnClickListener { listener.onListClick(task.id) }

        if (task.complete) {
            itemBinding.imageTask.setImageResource(R.drawable.ic_done)
        } else {
            itemBinding.imageTask.setImageResource(R.drawable.ic_todo)
        }

        // Eventos
        itemBinding.textDescription.setOnClickListener { listener.onListClick(task.id) }
        itemBinding.imageTask.setOnClickListener {
            if (task.complete) {
                listener.onUndoClick(task.id)
            } else {
                listener.onCompleteClick(task.id)
            }
        }

        itemBinding.textDescription.setOnLongClickListener {
            AlertDialog.Builder(itemView.context)
                .setTitle(R.string.remocao_de_tarefa)
                .setMessage(R.string.remover_tarefa)
                .setPositiveButton(R.string.sim) { _, _ ->
                    listener.onDeleteClick(task.id)
                }
                .setNegativeButton(R.string.cancelar, null)
                .show()
            true
        }

    }
}