package com.joaorodrigues.tasks.service.repository

import android.content.Context
import com.joaorodrigues.tasks.service.listener.APIListener
import com.joaorodrigues.tasks.service.model.PriorityModel
import com.joaorodrigues.tasks.service.repository.local.TaskDatabase
import com.joaorodrigues.tasks.service.repository.remote.PriorityService
import com.joaorodrigues.tasks.service.repository.remote.RetrofitClient

class PriorityRepository(context: Context) : BaseRepository(context) {

    private val remote = RetrofitClient.getService(PriorityService::class.java)
    private val database = TaskDatabase.getDatabase(context).priorityDAO()

    companion object {
        private val cache = mutableMapOf<Int, String>()
        fun getDescription(id: Int): String {
            return cache[id] ?: ""
        }

        fun setDescription(id: Int, description: String) {
            cache[id] = description
        }
    }

    fun getDescription(id: Int): String {

        val cached = PriorityRepository.getDescription(id)

        return if (cached == "") {
            val description = database.getDescription(id)
            setDescription(id, description)
            description
        } else {
            cached
        }
    }

    fun list(listener: APIListener<List<PriorityModel>>) {
        executeCall(remote.list(), listener)
    }

    fun list(): List<PriorityModel> {
        return database.list()
    }

    fun save(list: List<PriorityModel>) {
        database.clear()
        database.save(list)
    }

}