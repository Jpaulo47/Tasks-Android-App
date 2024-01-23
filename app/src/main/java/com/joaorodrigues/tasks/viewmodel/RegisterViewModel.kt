package com.joaorodrigues.tasks.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.joaorodrigues.tasks.service.constants.TaskConstants
import com.joaorodrigues.tasks.service.listener.APIListener
import com.joaorodrigues.tasks.service.model.PersonModel
import com.joaorodrigues.tasks.service.model.ValidationModel
import com.joaorodrigues.tasks.service.repository.PersonRepository
import com.joaorodrigues.tasks.service.repository.SecurityPreferences
import com.joaorodrigues.tasks.service.repository.remote.RetrofitClient

class RegisterViewModel(application: Application) : AndroidViewModel(application) {

    private val personRepository = PersonRepository(application.applicationContext)
    private val securityPreferences = SecurityPreferences(application.applicationContext)
    private val _create = MutableLiveData<ValidationModel>()
    val create: MutableLiveData<ValidationModel> = _create

    fun create(name: String, email: String, password: String) {
        personRepository.create(name, email, password, object : APIListener<PersonModel> {
            override fun onSuccess(result: PersonModel) {
                securityPreferences.store(TaskConstants.SHARED.TOKEN_KEY, result.token)
                securityPreferences.store(TaskConstants.SHARED.PERSON_KEY, result.personKey)
                securityPreferences.store(TaskConstants.SHARED.PERSON_NAME, result.name)

                RetrofitClient.addHeaders(result.token, result.personKey)

                _create.value = ValidationModel()
            }

            override fun onFailure(message: String) {
                _create.value = ValidationModel(message)
            }
        })
    }

}