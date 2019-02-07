package com.mentormate.andriivanov.retrofit101.data

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "allchamps")
class AllChamps(val desc: String, @field:PrimaryKey
@field:ColumnInfo(name = "id")
val id: Int, val name: String)
