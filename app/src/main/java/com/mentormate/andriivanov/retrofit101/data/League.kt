package com.mentormate.andriivanov.retrofit101.data

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "league")
class League(
        var leagueId: String?,
        var leagueName: String?,
        var tier: String?,
        var queueType: String?,
        var rank: String?,
        var summonerId: String?,
        @field:PrimaryKey
        @field:ColumnInfo(name = "summonerName")
        var summonerName: String,
        var leaguePoints: Int,
        var wins: Int,
        var losses: Int) {

    override fun toString(): String {
        return "$leagueName || $summonerName || $leagueId"
    }
}
