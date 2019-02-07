package com.mentormate.andriivanov.retrofit101.data

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey


@Entity(tableName = "profiles", indices = arrayOf(Index(value = *arrayOf("accountId"), unique = true)))
class Profile (
    @field:PrimaryKey
    @field:ColumnInfo(name = "id")
    var id: String="",
    var accountId: String? = null,
    var puuid: String? = null,
    var name: String? = null,
    var profileIconId: Int? = null,
    var revisionDate: Long = 0,
    var summonerLevel: Int? = null,
    var profileImage: String? = null
)


