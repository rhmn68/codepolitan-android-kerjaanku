package com.codepolitan.kerjaanku.views.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.codepolitan.kerjaanku.R
import com.codepolitan.kerjaanku.adapter.TaskAdapter
import com.codepolitan.kerjaanku.db.DbSubTaskHelper
import com.codepolitan.kerjaanku.db.DbTaskHelper
import com.codepolitan.kerjaanku.repository.TaskRepository
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    private lateinit var dbTask: DbTaskHelper
    private lateinit var dbSubTask: DbSubTaskHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dbTask = DbTaskHelper.getInstance(context)
        dbSubTask = DbSubTaskHelper.getInStance(context)
    }

    override fun onResume() {
        super.onResume()
        val tasks = TaskRepository.getDataTasksFromDatabase(dbTask, dbSubTask)

        if (tasks != null && tasks.isNotEmpty()){
            showTasks()
            val taskAdapter = TaskAdapter(dbTask, dbSubTask)
            taskAdapter.setData(tasks)

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
