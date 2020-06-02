package com.codepolitan.kerjaanku.repository

import android.content.Context
import com.codepolitan.kerjaanku.model.Tasks
import com.google.gson.Gson
import java.io.IOException

object TaskRepository {

    fun getDataTasks(context: Context?): Tasks?{
        val json: String?
        try {
            val inputStream = context?.assets?.open("tasks.json")
            json = inputStream?.bufferedReader().use { it?.readText() }
        }catch (e: IOException){
            e.printStackTrace()
            return null
        }

        return Gson().fromJson(json, Tasks::class.java)
    }

}