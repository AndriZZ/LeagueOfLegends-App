package com.mentormate.andriivanov.retrofit101.data

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey


@Entity(tableName = "champ_masteries")
class ChampionMasteries(@field:PrimaryKey
                        @field:ColumnInfo(name = "championId")
                        val championId: Int,
                        val championLevel: Int?,
                        val championPoints: Int?,
                        val championPointsSinceLastLevel: Int?,
                        val championPointsUntilNextLevel: Int?,
                        var chestGranted: String?,
                        val tokensEarned: Int?,
                        val summonerId: String)
