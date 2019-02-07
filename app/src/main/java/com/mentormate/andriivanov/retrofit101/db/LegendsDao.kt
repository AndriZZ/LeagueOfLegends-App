/*
 * Copyright (C) 2018 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mentormate.andriivanov.retrofit101.db

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query

import com.mentormate.andriivanov.retrofit101.data.AllChamps
import com.mentormate.andriivanov.retrofit101.data.ChampionInfo
import com.mentormate.andriivanov.retrofit101.data.ChampionMasteries
import com.mentormate.andriivanov.retrofit101.data.League
import com.mentormate.andriivanov.retrofit101.data.Matches
import com.mentormate.andriivanov.retrofit101.data.Profile

/**
 * Data Access Object (DAO) for a word.
 * Each method performs a database operation, such as inserting or deleting a word,
 * running a DB query, or deleting all words.
 */

@Dao
interface LegendsDao {

    @get:Query("SELECT * from profiles LIMIT 1")
    val anyWord: LiveData<Profile>


    @get:Query("SELECT * from profiles ORDER BY id ASC")
    val allProfiles: LiveData<List<Profile>>

    @get:Query("SELECT * from allchamps ORDER BY id ASC")
    val allChamps: LiveData<List<AllChamps>>

    @get:Query("SELECT * from champ_masteries ORDER BY championId ASC")
    val allMasteries: LiveData<List<ChampionMasteries>>

    @get:Query("SELECT * from league ORDER BY leagueId ASC")
    val allLeagues: LiveData<List<League>>

    @get:Query("SELECT * from matches ORDER BY gameId ASC")
    val allMatches: LiveData<List<Matches>>

    @get:Query("SELECT * from champinfo ORDER BY id ASC")
    val allChampionInfo: LiveData<List<ChampionInfo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(league: League)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(championMasteries: ChampionMasteries)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(allChamps: AllChamps)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(profile: Profile)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(matches: Matches)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(championInfo: ChampionInfo)

    //   @Insert(onConflict = OnConflictStrategy.REPLACE)
    //  void insert(BestChampion bestChampion);


    @Query("DELETE FROM profiles")
    fun deleteAllProfiles()

    @Query("DELETE FROM matches")
    fun deleteAllMatches()

    @Delete
    fun deleteWord(profile: Profile)

    // @Query("SELECT * from bestchampion ORDER BY id ASC")
    //LiveData<List<BestChampion>> getBestChampion();


}
