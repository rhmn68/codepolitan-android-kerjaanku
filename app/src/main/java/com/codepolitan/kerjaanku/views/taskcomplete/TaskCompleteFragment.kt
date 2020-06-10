package com.codepolitan.kerjaanku.views.taskcomplete

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.codepolitan.kerjaanku.R
import com.codepolitan.kerjaanku.adapter.TaskAdapter
import com.codepolitan.kerjaanku.model.SubTask
import com.codepolitan.kerjaanku.model.Task
import com.codepolitan.kerjaanku.repository.TaskRepository
import kotlinx.android.synthetic.main.fragment_task_complete.*

class TaskCompleteFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_task_complete, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tasks = TaskRepository.getDataTasks(context)

        if (tasks != null){
            for (task: Task in tasks.tasks!!){
                task.mainTask?.isComplete = true

                if (task.subTasks != null){
                    for (subTask: SubTask in task.subTasks!!){
                        subTask.isComplete = true
                    }
                }
            }

            showTaskComplete()
            val taskAdapter = TaskAdapter()
            taskAdapter.setData(tasks.tasks)

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
