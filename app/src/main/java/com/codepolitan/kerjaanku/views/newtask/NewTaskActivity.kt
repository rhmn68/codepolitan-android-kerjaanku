package com.codepolitan.kerjaanku.views.newtask

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.DatePicker
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.codepolitan.kerjaanku.R
import com.codepolitan.kerjaanku.adapter.AddSubTaskAdapter
import com.codepolitan.kerjaanku.model.SubTask
import com.codepolitan.kerjaanku.util.DateKerjaanKu
import kotlinx.android.synthetic.main.activity_new_task.*
import org.jetbrains.anko.toast

class NewTaskActivity : AppCompatActivity() {

    private lateinit var addSubTaskAdapter: AddSubTaskAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_task)

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
            DateKerjaanKu.showDatePicker(this,
                DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                    val dateString = DateKerjaanKu.dateFormatSql(year, month, dayOfMonth)
                    btnAddDateTask.text = DateKerjaanKu.dateFromSqlToDateViewTask(dateString)

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