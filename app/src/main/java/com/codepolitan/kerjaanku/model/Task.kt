package com.codepolitan.kerjaanku.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Task(
    val mainTask: MainTask? = null,
    val subTasks: List<SubTask>? = null
): Parcelable