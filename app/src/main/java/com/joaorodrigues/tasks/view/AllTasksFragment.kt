package com.joaorodrigues.tasks.view

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.joaorodrigues.tasks.databinding.FragmentAllTasksBinding
import com.joaorodrigues.tasks.service.constants.TaskConstants
import com.joaorodrigues.tasks.service.listener.TaskListener
import com.joaorodrigues.tasks.view.adapter.TaskAdapter
import com.joaorodrigues.tasks.viewmodel.TaskListViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope.coroutineContext
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class AllTasksFragment : Fragment() {

    private lateinit var viewModel: TaskListViewModel
    private var _binding: FragmentAllTasksBinding? = null
    private val binding get() = _binding!!
    private var adapter = TaskAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, b: Bundle?): View {
        viewModel = ViewModelProvider(this)[TaskListViewModel::class.java]
        _binding = FragmentAllTasksBinding.inflate(inflater, container, false)

        binding.recyclerAllTasks.layoutManager = LinearLayoutManager(context)
        binding.recyclerAllTasks.adapter = adapter

        listener()

        observe()

        return binding.root
    }

    override fun onResume() {
        super.onResume()
       viewModel.list()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observe() {
        viewModel.tasks.observe(viewLifecycleOwner) {
            adapter.updateList(it)
        }

        viewModel.delete.observe(viewLifecycleOwner) {
            if (!it.success()) {
                Toast.makeText(context, it.message(), Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.status.observe(viewLifecycleOwner) {
            if (!it.success()) {
                Toast.makeText(context, it.message(), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun listener() {
        val listener = object : TaskListener {
            override fun onListClick(id: Int) {
                val intent = Intent(context, TaskFormActivity::class.java)
                intent.putExtra(TaskConstants.BUNDLE.TASKID, id)
                startActivity(intent)
            }

            override fun onDeleteClick(id: Int) {
                viewModel.delete(id)
            }

            override fun onCompleteClick(id: Int) {
                viewModel.status(id, true)
            }

            override fun onUndoClick(id: Int) {
                viewModel.status(id, false)
            }
        }

        adapter.attachListener(listener)
    }
}