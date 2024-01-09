package com.joaorodrigues.tasks.view.viewholder

import android.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.joaorodrigues.tasks.R
import com.joaorodrigues.tasks.databinding.RowTaskListBinding
import com.joaorodrigues.tasks.service.listener.TaskListener
import com.joaorodrigues.tasks.service.model.TaskModel

class TaskViewHolder(private val itemBinding: RowTaskListBinding, val listener: TaskListener) :
    RecyclerView.ViewHolder(itemBinding.root) {

    /**
     * Atribui valores aos elementos de interface e também eventos
     */
    fun bindData(task: TaskModel) {

        itemBinding.textDescription.text = ""
        itemBinding.textPriority.text = ""
        itemBinding.textDueDate.text = ""

        // Eventos
        // itemBinding.textDescription.setOnClickListener { listener.onListClick(task.id) }
        // itemBinding.imageTask.setOnClickListener { }

        itemBinding.textDescription.setOnLongClickListener {
            AlertDialog.Builder(itemView.context)
                .setTitle(R.string.remocao_de_tarefa)
                .setMessage(R.string.remover_tarefa)
                .setPositiveButton(R.string.sim) { dialog, which ->
                    // listener.onDeleteClick(task.id)
                }
                .setNeutralButton(R.string.cancelar, null)
                .show()
            true
        }

    }
}