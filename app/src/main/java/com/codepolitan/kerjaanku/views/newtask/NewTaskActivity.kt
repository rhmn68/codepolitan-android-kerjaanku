package com.codepolitan.kerjaanku.views.newtask

import android.app.AlertDialog
import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.DatePicker
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
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
    private var delayedTime: Long = 1200
    private var task: Task? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_task)

        setup()
        setupActionBar()
        setupAddSubTaskAdapter()
        onClick()
    }

    private fun setup() {
        dbTaskHelper = DbTaskHelper.getInstance(this)
        dbSubTaskHelper = DbSubTaskHelper.getInstance(this)

        getDataExtra()
    }

    private fun getDataExtra() {
        if (task != null){

        }else{
            task = Task(mainTask = MainTask())
        }
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
            DateKerjaanKu.showDatePicker(this,
                DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                    val dateString = DateKerjaanKu.dateFormatSql(year, month, dayOfMonth)
                    btnAddDateTask.text = DateKerjaanKu.dateFromSqlToDateViewTask(dateString)

                    task?.mainTask?.date = dateString
                    checkIsDateFilled(true)
                })
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
        val detailTask = etAddDetailsTask.text.toString()
        val dataSubTasks = addSubTaskAdapter.getData()

        if (titleTask.isEmpty()){
            etTitleTask.error = getString(R.string.please_field_your_title)
            etTitleTask.requestFocus()
            return
        }

        task?.mainTask?.title = titleTask

        if (detailTask.isNotEmpty()){
            task?.mainTask?.details = detailTask
        }

        if (isEdit){
            //ToDO Edit
        }else{
            val result = dbTaskHelper.insert(task?.mainTask)
            if (result > 0){
                if (dataSubTasks != null && dataSubTasks.isNotEmpty()){
                    var isSuccess = false
                    for (subTask: SubTask in dataSubTasks){
                        subTask.idTask = result.toInt()
                        val resultSubTask = dbSubTaskHelper.insert(subTask)
                        isSuccess = resultSubTask > 0
                    }
                    if (isSuccess){
                        val dialog = showSuccessDialog("Success add data to Database")
                        Handler().postDelayed({
                            dialog.dismiss()
                        }, 1200)
                    }else{
                        val dialog = showFailedDialog("Failed add data to Database")
                        Handler().postDelayed({
                            dialog.dismiss()
                        }, delayedTime)
                    }
                }
                val dialog = showSuccessDialog("Success add data to Database")
                Handler().postDelayed({
                    dialog.dismiss()
                    finish()
                }, 1200)
            }else{
                val dialog = showFailedDialog("Failed add data to Database")
                Handler().postDelayed({
                    dialog.dismiss()
                }, delayedTime)
            }
        }
    }

    private fun showSuccessDialog(desc: String): AlertDialog {
        return AlertDialog.Builder(this)
            .setTitle("Success")
            .setMessage(desc)
            .show()
    }

    private fun showFailedDialog(desc: String): AlertDialog {
        return AlertDialog.Builder(this)
            .setTitle("Failed")
            .setMessage(desc)
            .show()
    }

    private fun checkIsDateFilled(isDateFilled: Boolean) {
        if(isDateFilled){
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
        menuInflater.inflate(R.menu.new_task_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_remove_task -> {
                toast("Remove Task")
            }
        }
        return super.onOptionsItemSelected(item)
    }
}