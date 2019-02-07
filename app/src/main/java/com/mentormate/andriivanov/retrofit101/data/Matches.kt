package com.mentormate.andriivanov.retrofit101.data

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "matches")
class Matches(var platformId: String?, @field:PrimaryKey
@field:ColumnInfo(name = "gameId")
              val gameId: Long,
              var champion: Int?,
              val queue: Int?,
              val season: Int?,
              val timestamp: Long,
              val role: String,
              val lane: String)
