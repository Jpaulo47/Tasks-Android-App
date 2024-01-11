package com.joaorodrigues.tasks.service.repository

import android.content.Context
import com.google.gson.Gson
import com.joaorodrigues.tasks.R
import com.joaorodrigues.tasks.service.constants.TaskConstants
import com.joaorodrigues.tasks.service.listener.ApiListener
import com.joaorodrigues.tasks.service.model.PersonModel
import com.joaorodrigues.tasks.service.repository.remote.PersonService
import com.joaorodrigues.tasks.service.repository.remote.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PersonRepository(val context: Context) {

    private val mRemote = RetrofitClient.getService(PersonService::class.java)

    fun login(email: String, password: String, listener: ApiListener<PersonModel>) {
        val call = mRemote.login(email, password)
        call.enqueue(object : Callback<PersonModel> {
            override fun onResponse(call: Call<PersonModel>, response: Response<PersonModel>) {

                if (response.code() == TaskConstants.HTTP.SUCCESS) {
                    response.body()?.let { listener.onSuccess(it) }
                } else {
                    listener.onFailure(failureResponse(response.errorBody()!!.string()))
                }

            }

            override fun onFailure(call: Call<PersonModel>, t: Throwable) {
                listener.onFailure(context.getString(R.string.ERROR_UNEXPECTED))
            }
        })
    }

    private fun failureResponse(str: String): String {
        return Gson().fromJson(str, String::class.java)
    }
}
