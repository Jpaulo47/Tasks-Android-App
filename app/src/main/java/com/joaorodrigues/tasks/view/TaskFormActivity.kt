package com.joaorodrigues.tasks.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.joaorodrigues.tasks.viewmodel.TaskFormViewModel
import com.joaorodrigues.tasks.databinding.ActivityTaskFormBinding

class TaskFormActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var viewModel: TaskFormViewModel
    private lateinit var binding: ActivityTaskFormBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Variáveis da classe
        viewModel = ViewModelProvider(this).get(TaskFormViewModel::class.java)
        binding = ActivityTaskFormBinding.inflate(layoutInflater)

        // Eventos
        binding.buttonSave.setOnClickListener(this)
        binding.buttonDate.setOnClickListener(this)

        // Layout
        setContentView(binding.root)
    }

    override fun onClick(v: View) {
        TODO("Not yet implemented")
    }
}