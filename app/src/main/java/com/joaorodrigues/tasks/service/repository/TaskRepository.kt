package com.joaorodrigues.tasks.service.repository

import android.content.Context
import com.joaorodrigues.tasks.service.listener.APIListener
import com.joaorodrigues.tasks.service.model.TaskModel
import com.joaorodrigues.tasks.service.repository.remote.RetrofitClient
import com.joaorodrigues.tasks.service.repository.remote.TaskService

class TaskRepository(context: Context) : BaseRepository(context) {

    private val remote = RetrofitClient.getService(TaskService::class.java)

    fun listAll(listener: APIListener<List<TaskModel>>) {
        executeCall(remote.all(), listener)
    }

    fun listOverdue(listener: APIListener<List<TaskModel>>) {
        executeCall(remote.listOverdue(), listener)
    }

    fun listNextSevenDays(listener: APIListener<List<TaskModel>>) {
        executeCall(remote.listNextSevenDays(), listener)
    }

    fun create(task: TaskModel, listener: APIListener<Boolean>) {
        val call = remote.create(task.priorityId, task.description, task.dueDate, task.complete)
        executeCall(call, listener)
    }

    fun delete(id: Int, listener: APIListener<Boolean>) {
        executeCall(remote.delete(id), listener)
    }

    fun complete(id: Int, listener: APIListener<Boolean>) {
        executeCall(remote.complete(id), listener)
    }

    fun undo(id: Int, listener: APIListener<Boolean>) {
        executeCall(remote.undo(id), listener)
    }

}