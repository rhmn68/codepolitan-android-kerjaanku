package com.codepolitan.kerjaanku.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.codepolitan.kerjaanku.db.DbContract.CompleteMyTask.Companion.TABLE_COMPLETE_TASK
import com.codepolitan.kerjaanku.db.DbContract.MyTask.Companion.TASK_ID
import com.codepolitan.kerjaanku.db.DbContract.MyTask.Companion.TASK_IS_COMPLETE
import com.codepolitan.kerjaanku.model.MainTask

class DbCompleteTaskHelper(context: Context?) {
    companion object{
        private const val TABLE_NAME = TABLE_COMPLETE_TASK

        private lateinit var dbHelper: DbHelper
        private lateinit var database: SQLiteDatabase
        private var instance: DbCompleteTaskHelper? = null

        fun getInstance(context: Context?): DbCompleteTaskHelper{
            if (instance == null){
                synchronized(SQLiteOpenHelper::class){
                    if (instance == null){
                        instance = DbCompleteTaskHelper(context)
                    }
                }
            }
            return instance as DbCompleteTaskHelper
        }
    }

    init {
        dbHelper = DbHelper(context)
    }

    private fun open(){
        database = dbHelper.writableDatabase
    }

    private fun close(){
        dbHelper.close()

        if (database.isOpen){
            database.close()
        }
    }

    fun insert(mainTask: MainTask?): Long{
        open()
        val values = ContentValues()
        values.put(DbContract.MyTask.TASK_TITLE, mainTask?.title)
        values.put(DbContract.MyTask.TASK_DETAILS, mainTask?.details)
        values.put(DbContract.MyTask.TASK_DATE, mainTask?.date)
        values.put(TASK_IS_COMPLETE, 0)

        val result = database.insert(TABLE_NAME, null, values)
        close()
        return result
    }

    fun getAllTask(): List<MainTask>?{
        open()
        val tasks: MutableList<MainTask>? = null
        val query = "SELECT * FROM $TABLE_NAME WHERE $TASK_IS_COMPLETE = 0"
        val cursor = database.rawQuery(query, null)
        if (cursor != null){
            while (cursor.moveToNext()){
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(TASK_ID))
                val title = cursor.getString(cursor.getColumnIndexOrThrow(DbContract.MyTask.TASK_TITLE))
                val details = cursor.getString(cursor.getColumnIndexOrThrow(DbContract.MyTask.TASK_DETAILS))
                val date = cursor.getString(cursor.getColumnIndexOrThrow(DbContract.MyTask.TASK_DATE))
                val isComplete = cursor.getInt(cursor.getColumnIndexOrThrow(TASK_IS_COMPLETE))
                var isCompleteTask: Boolean

                isCompleteTask = isComplete == 1
                tasks?.add(MainTask(id = id, title = title, date = date, isComplete = isCompleteTask, details = details))
            }
        }

        cursor.close()
        close()
        return tasks
    }

    fun updateTask(mainTask: MainTask?): Int{
        open()
        val values = ContentValues()
        values.put(DbContract.MyTask.TASK_TITLE, mainTask?.title)
        values.put(DbContract.MyTask.TASK_DETAILS, mainTask?.details)
        values.put(DbContract.MyTask.TASK_DATE, mainTask?.date)
        if (mainTask?.isComplete!!){
            values.put(TASK_IS_COMPLETE, 1)
        }else{
            values.put(TASK_IS_COMPLETE, 0)
        }
        val result = database.update(TABLE_NAME, values, "$TASK_ID = ${mainTask.id}", null)
        close()
        return result
    }

    fun deleteTask(id: Int): Int{
        open()
        val result = database.delete(TABLE_NAME, "$TASK_ID = ?", arrayOf(id.toString()))
        close()
        return result
    }
}