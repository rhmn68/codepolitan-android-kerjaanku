package com.codepolitan.kerjaanku.views.taskcomplete

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
import kotlinx.android.synthetic.main.fragment_task_complete.*

class TaskCompleteFragment : Fragment() {

    private lateinit var dbTask: DbTaskHelper
    private lateinit var dbSubTask: DbSubTaskHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_task_complete, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dbTask = DbTaskHelper.getInstance(context)
        dbSubTask = DbSubTaskHelper.getInStance(context)
    }

    override fun onResume() {
        super.onResume()
        val tasks = TaskRepository.getDataCompleteTaskFromDatabase(dbTask, dbSubTask)

        if (tasks != null && tasks.isNotEmpty()){
            showTaskComplete()

            val taskAdapter = TaskAdapter(dbTask, dbSubTask)
            taskAdapter.setData(tasks)

            rvTaskComplete.adapter = taskAdapter
        }else{
            hideTaskComplete()
        }
    }

    private fun hideTaskComplete() {
        rvTaskComplete.visibility = View.GONE
        layoutEmptyTaskComplete.visibility = View.VISIBLE
        fabDeleteTaskComplete.visibility = View.GONE
    }

    private fun showTaskComplete() {
        rvTaskComplete.visibility = View.VISIBLE
        layoutEmptyTaskComplete.visibility = View.GONE
        fabDeleteTaskComplete.visibility = View.VISIBLE
    }
}
