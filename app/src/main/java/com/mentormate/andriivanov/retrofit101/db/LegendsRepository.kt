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
import android.arch.lifecycle.LiveData
import android.os.AsyncTask

import com.mentormate.andriivanov.retrofit101.data.AllChamps
import com.mentormate.andriivanov.retrofit101.data.ChampionInfo
import com.mentormate.andriivanov.retrofit101.data.ChampionMasteries
import com.mentormate.andriivanov.retrofit101.data.League
import com.mentormate.andriivanov.retrofit101.data.Matches
import com.mentormate.andriivanov.retrofit101.data.Profile

/**
 * This class holds the implementation code for the
 * methods that interact with the database.
 * Using a repository allows us to group the implementation
 * methods together, and allows the LegendsViewModel to be
 * a clean interface between the rest of the app and the database.
 *
 *
 * For insert, update and delete, and longer-running queries,
 * you must run the database interaction methods in the background.
 * Typically, all you need to do to implement a database method
 * is to call it on the data access object (DAO),
 * in the background if applicable.
 */

class LegendsRepository
//   private LiveData<List<BestChampion>> mBestChampion;


internal constructor(application: Application) {

    private val mLegendsDao: LegendsDao
    internal val allProfiles: LiveData<List<Profile>>
    internal val allChamps: LiveData<List<AllChamps>>
    internal val allChampMasteries: LiveData<List<ChampionMasteries>>
    internal val allLeagues: LiveData<List<League>>
    internal val allMatches: LiveData<List<Matches>>
    internal val allChampionInfo: LiveData<List<ChampionInfo>>

    init {
        val db = LegendsRoomDatabase.getDatabase(application)

        mLegendsDao = db.legendsDao()
        allProfiles = mLegendsDao.allProfiles
        allChamps = mLegendsDao.allChamps
        allChampMasteries = mLegendsDao.allMasteries
        allLeagues = mLegendsDao.allLeagues
        allMatches = mLegendsDao.allMatches
        allChampionInfo = mLegendsDao.allChampionInfo
        //  mBestChampion=mLegendsDao.getBestChampion();

    }

    //   LiveData<List<BestChampion>> getBestChampion() {
    //   return mBestChampion;
    // }


    fun insert(profile: Profile) {
        insertAsyncTask(mLegendsDao).execute(profile)
    }

    fun insert(allChamps: AllChamps) {
        insertAsyncTaskChamps(mLegendsDao).execute(allChamps)
    }

    fun insert(championMasteries: ChampionMasteries) {
        insertAsyncTaskMasteries(mLegendsDao).execute(championMasteries)
    }

    fun insert(league: League) {
        insertAsyncTaskLeagues(mLegendsDao).execute(league)
    }

    fun insert(matches: Matches) {
        insertAsyncTaskMatches(mLegendsDao).execute(matches)
    }

    fun insert(championInfo: ChampionInfo) {
        insertAsyncChampInfo(mLegendsDao).execute(championInfo)
    }

    //   public void insert(BestChampion bestChampion) {
    //       new insertAsyncBestChampion(mLegendsDao).execute(bestChampion);
    //   }


    fun deleteAll() {
        deleteAllWordsAsyncTask(mLegendsDao).execute()
    }

    fun deleteAllMatches() {
        deleteAllMatchesAsyncTask(mLegendsDao).execute()
    }

    // Need to run off main thread
    fun deleteWord(profile: Profile) {
        deleteWordAsyncTask(mLegendsDao).execute(profile)
    }

    // Static inner classes below here to run database interactions
    // in the background.

    /**
     * Insert a word into the database.
     */
    private class insertAsyncTask internal constructor(private val mAsyncTaskDao: LegendsDao) : AsyncTask<Profile, Void, Void>() {

        override fun doInBackground(vararg params: Profile): Void? {
            mAsyncTaskDao.insert(params[0])
            return null
        }
    }

    private class insertAsyncChampInfo internal constructor(private val mAsyncTaskDao: LegendsDao) : AsyncTask<ChampionInfo, Void, Void>() {

        override fun doInBackground(vararg params: ChampionInfo): Void? {
            mAsyncTaskDao.insert(params[0])
            return null
        }
    }

    private class insertAsyncTaskChamps internal constructor(private val mAsyncTaskDao: LegendsDao) : AsyncTask<AllChamps, Void, Void>() {

        override fun doInBackground(vararg params: AllChamps): Void? {
            mAsyncTaskDao.insert(params[0])
            return null
        }
    }

    private class insertAsyncTaskMasteries internal constructor(private val mAsyncTaskDao: LegendsDao) : AsyncTask<ChampionMasteries, Void, Void>() {

        override fun doInBackground(vararg params: ChampionMasteries): Void? {
            mAsyncTaskDao.insert(params[0])
            return null
        }
    }

    private class insertAsyncTaskLeagues internal constructor(private val mAsyncTaskDao: LegendsDao) : AsyncTask<League, Void, Void>() {

        override fun doInBackground(vararg params: League): Void? {
            mAsyncTaskDao.insert(params[0])
            return null
        }
    }

    private class insertAsyncTaskMatches internal constructor(private val mAsyncTaskDao: LegendsDao) : AsyncTask<Matches, Void, Void>() {

        override fun doInBackground(vararg params: Matches): Void? {
            mAsyncTaskDao.insert(params[0])
            return null
        }
    }
    /*  private static class insertAsyncBestChampion extends AsyncTask<BestChampion, Void, Void> {

        private LegendsDao mAsyncTaskDao;

        insertAsyncBestChampion(LegendsDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final BestChampion... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }*/


    /**
     * Delete all words from the database (does not delete the table)
     */
    private class deleteAllWordsAsyncTask internal constructor(private val mAsyncTaskDao: LegendsDao) : AsyncTask<Void, Void, Void>() {

        override fun doInBackground(vararg voids: Void): Void? {
            mAsyncTaskDao.deleteAllProfiles()
            return null
        }
    }

    private class deleteAllMatchesAsyncTask internal constructor(private val mAsyncTaskDao: LegendsDao) : AsyncTask<Void, Void, Void>() {

        override fun doInBackground(vararg voids: Void): Void? {
            mAsyncTaskDao.deleteAllMatches()
            return null
        }
    }

    /**
     * Delete a single word from the database.
     */
    private class deleteWordAsyncTask internal constructor(private val mAsyncTaskDao: LegendsDao) : AsyncTask<Profile, Void, Void>() {

        override fun doInBackground(vararg params: Profile): Void? {
            mAsyncTaskDao.deleteWord(params[0])
            return null
        }
    }
}
