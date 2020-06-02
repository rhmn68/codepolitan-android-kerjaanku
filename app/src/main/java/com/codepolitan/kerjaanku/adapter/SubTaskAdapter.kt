package com.codepolitan.kerjaanku.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.codepolitan.kerjaanku.R
import com.codepolitan.kerjaanku.model.SubTask
import kotlinx.android.synthetic.main.item_sub_task.view.*

class SubTaskAdapter : RecyclerView.Adapter<SubTaskAdapter.ViewHolder>() {
    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        fun bind(subTask: SubTask) {
            itemView.tvTitleSubTask.text = subTask.title

            itemView.btnDoneSubTask.setOnClickListener {
                if (subTask.isComplete){
                    inCompleteSubTask()
                    subTask.isComplete = false
                }else{
                    completeSubTask()
                    subTask.isComplete = true
                }
            }
        }

        private fun completeSubTask() {
            itemView.btnDoneSubTask.setImageResource(R.drawable.ic_complete_task_black_24dp)
        }

        private fun inCompleteSubTask() {
            itemView.btnDoneSubTask.setImageResource(R.drawable.ic_done_task_black_24dp)
        }
    }

    private lateinit var subTasks: List<SubTask>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_sub_task, parent, false))

    override fun getItemCount(): Int = subTasks.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(subTasks[position])
    }

    fun setData(subTasks: List<SubTask>){
        this.subTasks = subTasks
        notifyDataSetChanged()
    }
}