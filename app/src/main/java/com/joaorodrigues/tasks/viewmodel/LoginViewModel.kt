package com.joaorodrigues.tasks.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.joaorodrigues.tasks.service.listener.ApiListener
import com.joaorodrigues.tasks.service.model.PersonModel
import com.joaorodrigues.tasks.service.repository.PersonRepository

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val personRepository = PersonRepository(application.applicationContext)

    /**
     * Faz login usando API
     */
    fun doLogin(email: String, password: String) {
        personRepository.login(email, password, object : ApiListener<PersonModel> {
            override fun onSuccess(result: PersonModel) {
                // Salvar dados no Shared Preferences
            }

            override fun onFailure(messsage: String) {
                // Tratar erro
            }
        })
    }

    /**
     * Verifica se usuário está logado
     */
    fun verifyLoggedUser() {
    }

}
