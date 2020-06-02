package com.codepolitan.kerjaanku.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepolitan.kerjaanku.R
import com.codepolitan.kerjaanku.model.Task
import kotlinx.android.synthetic.main.item_task.view.*

class TaskAdapter : RecyclerView.Adapter<TaskAdapter.ViewHolder>() {
    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        fun bind(task: Task) {
            itemView.tvTitleTask.text = task.mainTask?.title

            if (task.mainTask?.date != null && task.mainTask.date.isNotEmpty()){
                showDateTask()
                itemView.tvDateTask.text = task.mainTask.date
            }else{
                hideDateTask()
            }

            if (task.subTasks != null){
                showSubTasks()
                val subTaskAdapter = SubTaskAdapter()
                subTaskAdapter.setData(task.subTasks)

                itemView.rvSubTask.adapter = subTaskAdapter
            }else{
                hideSubTasks()
            }

            itemView.btnDoneTask.setOnClickListener {
                if (task.mainTask?.isComplete!!){
                    inCompleteTask()
                    task.mainTask.isComplete = false
                }else{
                    completeTask()
                    task.mainTask.isComplete = true
                }
            }
        }

        private fun completeTask() {
            itemView.btnDoneTask.setImageResource(R.drawable.ic_complete_task_black_24dp)
        }

        private fun inCompleteTask() {
            itemView.btnDoneTask.setImageResource(R.drawable.ic_done_task_black_24dp)
        }

        private fun hideSubTasks() {
            itemView.lineTask.visibility = View.GONE
            itemView.rvSubTask.visibility = View.GONE
        }

        private fun showSubTasks() {
            itemView.lineTask.visibility = View.VISIBLE
            itemView.rvSubTask.visibility = View.VISIBLE
        }

        private fun hideDateTask() {
            itemView.containerDateTask.visibility = View.GONE
        }

        private fun showDateTask() {
            itemView.containerDateTask.visibility = View.VISIBLE
        }

    }

    private lateinit var tasks: List<Task>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false))

    override fun getItemCount(): Int = tasks.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(tasks[position])
    }

    fun setData(tasks: List<Task>){
        this.tasks = tasks
    }
}