package com.joaorodrigues.tasks.service.repository

import com.joaorodrigues.tasks.service.model.PersonModel
import com.joaorodrigues.tasks.service.repository.remote.PersonService
import com.joaorodrigues.tasks.service.repository.remote.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PersonRepository {

    private val mRemote = RetrofitClient.getService(PersonService::class.java)

    fun login(email: String, password: String) {
        val call = mRemote.login(email, password)
        call.enqueue(object : Callback<PersonModel> {
            override fun onResponse(call: Call<PersonModel>, response: Response<PersonModel>) {
                if (response.code() == 200) {
                    val person: PersonModel? = response.body()
                    // Salvar dados no Shared Preferences
                }
                
            }

            override fun onFailure(call: Call<PersonModel>, t: Throwable) {
                // Tratar erro
            }
        })
    }
}