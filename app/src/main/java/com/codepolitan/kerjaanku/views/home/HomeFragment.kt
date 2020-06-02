package com.codepolitan.kerjaanku.views.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.codepolitan.kerjaanku.R
import com.codepolitan.kerjaanku.adapter.TaskAdapter
import com.codepolitan.kerjaanku.repository.TaskRepository
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tasks = TaskRepository.getDataTasks(context)

        if (tasks != null){
            showTasks()
            val taskAdapter = TaskAdapter()
            tasks.tasks?.let { taskAdapter.setData(it) }

            rvTask.adapter = taskAdapter
        }else{
            hideTasks()
        }
    }

    private fun hideTasks() {
        rvTask.visibility = View.GONE
        layoutEmptyTask.visibility = View.VISIBLE
    }

    private fun showTasks() {
        rvTask.visibility = View.VISIBLE
        layoutEmptyTask.visibility = View.GONE
    }
}
