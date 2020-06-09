package com.codepolitan.kerjaanku.views.newtask

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.codepolitan.kerjaanku.R
import com.codepolitan.kerjaanku.adapter.AddSubTaskAdapter
import com.codepolitan.kerjaanku.db.DbSubTaskHelper
import com.codepolitan.kerjaanku.db.DbTaskHelper
import com.codepolitan.kerjaanku.model.MainTask
import com.codepolitan.kerjaanku.model.SubTask
import com.codepolitan.kerjaanku.model.Task
import com.codepolitan.kerjaanku.util.DateKerjaanKu
import kotlinx.android.synthetic.main.activity_new_task.*
import org.jetbrains.anko.toast

class NewTaskActivity : AppCompatActivity() {

    private lateinit var addSubTaskAdapter: AddSubTaskAdapter
    private lateinit var dbTaskHelper: DbTaskHelper
    private lateinit var dbSubTaskHelper: DbSubTaskHelper
    private var isEdit = false
    private var task: Task? = null
    private val delayedTime: Long = 1200

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_task)

        dbTaskHelper = DbTaskHelper.getInstance(this)
        dbSubTaskHelper = DbSubTaskHelper.getInStance(this)

        if (task != null){

        }else{
            task = Task(mainTask = MainTask())
        }
        setupActionBar()
        setupAddSubTaskAdapter()
        onClick()
    }

    private fun setupAddSubTaskAdapter() {
        addSubTaskAdapter = AddSubTaskAdapter()
        rvAddSubTask.adapter = addSubTaskAdapter
    }

    private fun onClick() {
        tbNewTask.setNavigationOnClickListener {
            finish()
        }

        btnAddDateTask.setOnClickListener {
            showDateDialog()
        }

        btnRemoveDateTask.setOnClickListener {
            btnAddDateTask.text = null
            checkIsDateFilled(false)
        }

        btnAddSubTask.setOnClickListener {
            val subTask = SubTask(null, null, "")
            addSubTaskAdapter.addTask(subTask)
        }

        btnSubmitTask.setOnClickListener {
            submitDataToDatabase()
        }
    }

    private fun submitDataToDatabase() {
        val titleTask = etTitleTask.text.toString()
        val detailsTask = etAddDetailsTask.text.toString()
        val dataSubTasks = addSubTaskAdapter.getData()
        if (titleTask.isEmpty()){
            etTitleTask.error = getString(R.string.please_field_your_title)
            etTitleTask.requestFocus()
            return
        }

        task?.mainTask?.title = titleTask
        task?.mainTask?.details = detailsTask

        if (isEdit){
//            val result = dbTaskHelper.updateTask(task?.mainTask)
        }else{
            val result = dbTaskHelper.insert(task?.mainTask)
            if (result > 0){
                dataSubTasks?.let {
                    if (it.isNotEmpty()){
                        var isError = false
                        for (subTask: SubTask in dataSubTasks){
                            subTask.idTask = result.toInt()
                            val resultSubTask = dbSubTaskHelper.insert(subTask)
                            isError = resultSubTask < 0
                        }
                        if (isError){
                            val dialog = showFailedDialog(getString(R.string.you_failed_add_data_to_database))
                            Handler().postDelayed({
                                dialog.dismiss()
                            }, delayedTime)
                        }else{
                            val dialog = showSuccessDialog(getString(R.string.you_sucess_add_data_to_database))
                            Handler().postDelayed({
                                dialog.dismiss()
                                finish()
                            }, delayedTime)
                        }
                    }else{
                        val dialog = showSuccessDialog(getString(R.string.you_sucess_add_data_to_database))
                        Handler().postDelayed({
                            dialog.dismiss()
                            finish()
                        }, delayedTime)
                    }
                }
            }else{
                val dialog = showFailedDialog(getString(R.string.you_failed_add_data_to_database))
                Handler().postDelayed({
                    dialog.dismiss()
                }, delayedTime)
            }
        }
    }

    private fun showFailedDialog(description: String): AlertDialog {
        return AlertDialog.Builder(this)
            .setTitle("Failed")
            .setMessage(description)
            .show()
    }

    private fun showSuccessDialog(description: String): AlertDialog {
        return AlertDialog.Builder(this)
            .setCancelable(true)
            .setTitle("Success")
            .setMessage(description)
            .show()
    }

    private fun showDateDialog() {
        DateKerjaanKu.showDatePicker(this,
            DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                val dateString = DateKerjaanKu.dateFormatString(dayOfMonth, month, year)
                btnAddDateTask.text = DateKerjaanKu.dateFormatView(dateString)

                task?.mainTask?.date = dateString

                checkIsDateFilled(true)
            })
    }

    private fun checkIsDateFilled(isDateFilled: Boolean) {
        if (isDateFilled){
            btnAddDateTask.background = ContextCompat.getDrawable(this, R.drawable.bg_btn_add_date_task)
            btnAddDateTask.setPadding(24, 24, 24, 24)
            btnRemoveDateTask.visibility = View.VISIBLE
        }else{
            btnAddDateTask.setBackgroundResource(0)
            btnAddDateTask.setPadding(0, 0, 0, 0)
            btnRemoveDateTask.visibility = View.GONE
        }
    }

    private fun setupActionBar() {
        setSupportActionBar(tbNewTask)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if (isEdit){
            menuInflater.inflate(R.menu.new_task_menu, menu)
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_remove_task -> {toast("Remove Task")}
        }
        return super.onOptionsItemSelected(item)
    }
}