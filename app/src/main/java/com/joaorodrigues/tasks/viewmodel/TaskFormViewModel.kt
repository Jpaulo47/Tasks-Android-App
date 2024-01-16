package com.joaorodrigues.tasks.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.joaorodrigues.tasks.service.listener.APIListener
import com.joaorodrigues.tasks.service.model.PriorityModel
import com.joaorodrigues.tasks.service.model.TaskModel
import com.joaorodrigues.tasks.service.model.ValidationModel
import com.joaorodrigues.tasks.service.repository.PriorityRepository
import com.joaorodrigues.tasks.service.repository.TaskRepository

class TaskFormViewModel(application: Application) : AndroidViewModel(application) {

    private val priorityRepository = PriorityRepository(application.applicationContext)
    private val taskRepository = TaskRepository(application.applicationContext)

    private val _priorityList = MutableLiveData<List<PriorityModel>>()
    val priorityList: LiveData<List<PriorityModel>> = _priorityList

    private val _taskSave = MutableLiveData<ValidationModel>()
    val taskSave: LiveData<ValidationModel> = _taskSave

    fun listPriorities() {
        _priorityList.value = priorityRepository.list()
    }

    fun save(taskModel: TaskModel) {
        taskRepository.create(taskModel, object : APIListener<Boolean> {
            override fun onSuccess(result: Boolean) {
                _taskSave.value = ValidationModel()
            }

            override fun onFailure(message: String) {
                _taskSave.value = ValidationModel(message)
            }
        })
    }

}