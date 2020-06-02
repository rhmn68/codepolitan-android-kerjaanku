package com.codepolitan.kerjaanku.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MainTask(
    val id: Int? = null,
    val title: String? = null,
    val details: String? = null,
    val date: String? = null,
    var isComplete: Boolean = false
): Parcelable