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

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData

import com.mentormate.andriivanov.retrofit101.data.AllChamps
import com.mentormate.andriivanov.retrofit101.data.ChampionInfo
import com.mentormate.andriivanov.retrofit101.data.ChampionMasteries
import com.mentormate.andriivanov.retrofit101.data.League
import com.mentormate.andriivanov.retrofit101.data.Matches
import com.mentormate.andriivanov.retrofit101.data.Profile

/**
 * The LegendsViewModel provides the interface between the UI and the data layer of the app,
 * represented by the repository
 */

class LegendsViewModel
// private LiveData<List<BestChampion>> mAllBestChamp;

(application: Application) : AndroidViewModel(application) {

    private val mRepository: LegendsRepository


    val allProfiles: LiveData<List<Profile>>
    val allChamps: LiveData<List<AllChamps>>
    val allChampMasteries: LiveData<List<ChampionMasteries>>
    val allLeagues: LiveData<List<League>>
    val allMatches: LiveData<List<Matches>>
    val allChampInfo: LiveData<List<ChampionInfo>>

    init {
        mRepository = LegendsRepository(application)
        allProfiles = mRepository.allProfiles
        allChamps = mRepository.allChamps
        allChampMasteries = mRepository.allChampMasteries
        allLeagues = mRepository.allLeagues
        allMatches = mRepository.allMatches
        allChampInfo = mRepository.allChampionInfo
        //   mAllBestChamp = mRepository.getBestChampion();
    }

    // public LiveData<List<BestChampion>> getAllBestChamp() {
    //    return mAllBestChamp;
    // }


    fun insert(word: Profile) {
        mRepository.insert(word)
    }

    fun insert(word: AllChamps) {
        mRepository.insert(word)
    }

    fun insert(word: ChampionMasteries) {
        mRepository.insert(word)
    }

    fun insert(word: League) {
        mRepository.insert(word)
    }

    fun insert(matches: Matches) {
        mRepository.insert(matches)
    }


    fun insert(championInfo: ChampionInfo) {
        mRepository.insert(championInfo)
    }

    // public void insert(BestChampion bestChampion) {
    //     mRepository.insert(bestChampion);
    //  }


    fun deleteAll() {
        mRepository.deleteAll()
    }

    fun deleteAllMatches() {
        mRepository.deleteAllMatches()
    }

    fun deleteWord(profile: Profile) {
        mRepository.deleteWord(profile)
    }
}
