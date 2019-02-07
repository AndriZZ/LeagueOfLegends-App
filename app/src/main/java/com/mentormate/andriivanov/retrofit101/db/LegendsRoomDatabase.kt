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

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import android.os.AsyncTask
import android.util.Log
import androidx.work.*
import androidx.work.PeriodicWorkRequest.MIN_PERIODIC_FLEX_MILLIS

import com.google.gson.GsonBuilder
import com.mentormate.andriivanov.retrofit101.data.AllChamps
import com.mentormate.andriivanov.retrofit101.data.BestChampion
import com.mentormate.andriivanov.retrofit101.data.ChampionInfo
import com.mentormate.andriivanov.retrofit101.data.ChampionMasteries
import com.mentormate.andriivanov.retrofit101.JsonPlaceHolderApi
import com.mentormate.andriivanov.retrofit101.data.League
import com.mentormate.andriivanov.retrofit101.data.MatchList
import com.mentormate.andriivanov.retrofit101.data.Matches
import com.mentormate.andriivanov.retrofit101.data.Profile
import java.util.concurrent.Executors

import kotlinx.coroutines.*
import java.util.concurrent.ExecutorService

import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.Duration
import javax.inject.Singleton

/**
 * LegendsRoomDatabase. Includes code to create the database.
 * After the app creates the database, all further interactions
 * with it happen through the LegendsViewModel.
 */

@Database(entities = arrayOf(Profile::class, AllChamps::class, ChampionMasteries::class, League::class, Matches::class, ChampionInfo::class),
        version = 156, exportSchema = false)
abstract class LegendsRoomDatabase : RoomDatabase() {
    lateinit var profile: Profile

    abstract fun legendsDao(): LegendsDao

    // Populate the database with the initial data set
    // only if the database has no entries.

    private class PopulateDbAsync
    //

    internal constructor(db: LegendsRoomDatabase) : AsyncTask<Void, Void, Void>() {
        internal lateinit var jsonPlaceHolderApi: JsonPlaceHolderApi
        private val mDao: LegendsDao

        internal lateinit var bestChampion: BestChampion

        init {
            mDao = db.legendsDao()
            //  db.clearAllTables();
        }

        override fun onPostExecute(result: Void?) {
            super.onPostExecute(result)

        }
        override fun doInBackground(vararg params: Void): Void? {

            val gson = GsonBuilder().serializeNulls().create()
            val retrofit = Retrofit.Builder()
                    .baseUrl("https://euw1.api.riotgames.com/lol/")
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .callbackExecutor(Executors.newCachedThreadPool())
                    .build()

            jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi::class.java)

            bestChampion = BestChampion.instance

            val filterRequests = mutableListOf<OneTimeWorkRequest>()



           // if(!bestChampion.name.equals("")) {
          //      bestChampion.name?.let { doWork(it) }
              /*
                var builder = Data.Builder()
                builder.putString("name", bestChampion.name)
                val filterRequest = OneTimeWorkRequest.Builder(ProfileWorker::class.java)
                        //  .setInputData(buildInputDataForFilter(this, 0))
                        .setInputData(builder.build())
                        .build()
                //    filterRequests.add(filterRequest)
                var builder2 = Data.Builder()
                builder2.putString("SUMMONER_ID", "r6NVF37eYt5-egmPa5XewwZu7pBH2wKwa1dWdBrAE0rdHv4")
                val leaguesRequest = OneTimeWorkRequest.Builder(LeagesWorker::class.java)
                     //.setInputData(builder2.build())
                        .build()
                val matchesRequest = OneTimeWorkRequest.Builder(MatchesWorker::class.java)
                        .build()
                val allChampsRequest = OneTimeWorkRequest.Builder(AllChampsWorker::class.java)
                        .build()

                var workManager = WorkManager.getInstance()

                workManager.beginWith(filterRequest)
                        .then(leaguesRequest)
                        .then(matchesRequest)
                        .then(allChampsRequest)
                       .enqueue()
                    */
            return null
            }
           //   getProfile(bestChampion.name)



        }

/*
        private fun getProfile(name: String?) {

            val call = jsonPlaceHolderApi.getProfile(name, API_KEY)

            call.enqueue(object : retrofit2.Callback<Profile> { //do in background
                override fun onResponse(call: Call<Profile>, response: Response<Profile>) {
                    if (!response.isSuccessful) {
                        //  textViewResult.setText("Code: " + response.code());
                        return
                    }

                    profile = response.body()
                    profile?.let { mDao.insert(it) }


                    getAllChamps()
                    getLeague(profile!!.id)
                    getMasteries(profile!!.id)
                    getMatches(profile!!.accountId)


                }

                override fun onFailure(call: Call<Profile>, t: Throwable) {
                    //  textViewResult.setText(t.getMessage());
                    Log.e("PROFILE", "PROFILE FAILUSE", t)

                }
            })


        }


        private fun getAllChamps() {

            val call = jsonPlaceHolderApi.allChamps
            call.enqueue(object : retrofit2.Callback<List<AllChamps>> { //do in background
                override fun onResponse(call: Call<List<AllChamps>>, response: Response<List<AllChamps>>) {
                    if (!response.isSuccessful) {

                        return
                    }

                    val allChamps = response.body()

                    for (post in allChamps!!) {

                        mDao.insert(post)


                    }

                }

                override fun onFailure(call: Call<List<AllChamps>>, t: Throwable) {
                    //  textMasteries.setText(t.getMessage());
                }
            })
        }

        private fun getMasteries(id: String) {

            val call = jsonPlaceHolderApi.getChampMasteries(id, API_KEY)
            call.enqueue(object : retrofit2.Callback<List<ChampionMasteries>> { //do in background
                override fun onResponse(call: Call<List<ChampionMasteries>>, response: Response<List<ChampionMasteries>>) {
                    if (!response.isSuccessful) {
                        // textViewResult.setText("Code: " + response.code());
                        return
                    }

                    val posts = response.body()
                    bestChampion.setMaxPoints(0)
                    bestChampion.name = profile!!.name

                    for (championMasteries in posts!!) {

                        if (championMasteries.championPoints!! > bestChampion.getMaxPoints()!!) {
                            bestChampion.setMaxPoints(championMasteries.championPoints)
                            bestChampion.setBestChampId(championMasteries.championId)

                        }

                        mDao.insert(championMasteries)
                    }
                    getChampInfo(bestChampion.getBestChampId())


                }


                override fun onFailure(call: Call<List<ChampionMasteries>>, t: Throwable) {
                    //   textViewResult.setText(t.getMessage());
                    Log.d("A", "CAN'T GET MASTERIES")
                }
            })
        }

        private fun getLeague(id: String) {


            val call = jsonPlaceHolderApi.getLeague(id, API_KEY)
            call.enqueue(object : retrofit2.Callback<List<League>> { //do in background
                override fun onResponse(call: Call<List<League>>, response: Response<List<League>>) {
                    if (!response.isSuccessful) {
                        //  textFlexSR.setText("Code: " + response.code());
                        return
                    }


                    val posts = response.body()

                    for (post in posts!!) {


                        mDao.insert(post)
                    }

                }

                override fun onFailure(call: Call<List<League>>, t: Throwable) {
                    //textFlexSR.setText(t.getMessage());
                }
            })
        }

        private fun getMatches(AccountId: String?) {

            mDao.deleteAllMatches()
            val call = jsonPlaceHolderApi.getMatches(AccountId!!, API_KEY)

            call.enqueue(object : retrofit2.Callback<MatchList> { //do in background
                override fun onResponse(call: Call<MatchList>, response: Response<MatchList>) {
                    if (!response.isSuccessful) {

                        return
                    }

                    val matches = response.body()

                    for (match in matches!!.matches!!) {

                        mDao.insert(match)

                    }

                }

                override fun onFailure(call2: Call<MatchList>, t: Throwable) {
                    //  Toast.makeText(getActivity(), "Failed to get match data", Toast.LENGTH_SHORT).show();


                }
            })
        }

        private fun getChampInfo(bestChampId: Int?) {

            val call = jsonPlaceHolderApi.getChampInfo(bestChampId)
            call.enqueue(object : retrofit2.Callback<ChampionInfo> { //do in background
                override fun onResponse(call: Call<ChampionInfo>, response: Response<ChampionInfo>) {
                    if (!response.isSuccessful) {
                        //     Toast.makeText(get, "Code: " + response.code(), Toast.LENGTH_SHORT).show();
                        return
                    }

                    val post = response.body()
                    if (post != null) {
                        mDao.insert(post)
                    }
                    Log.e("ads", "THIS THING IS NOT FAILIGN")


                }


                override fun onFailure(call: Call<ChampionInfo>, t: Throwable) {
                    // textMasteries.setText(t.getMessage());
                    Log.e("ads", "THIS THING IS FAILIGN")
                }
            })
        }

    }
        private fun doWork(name:String ){
            var builder = Data.Builder()
            builder.putString("name",name)
            val filterRequest = OneTimeWorkRequest.Builder(ProfileWorker::class.java)
                    //  .setInputData(buildInputDataForFilter(this, 0))
                    .setInputData(builder.build())
                    .build()
            //    filterRequests.add(filterRequest)
            var builder2 = Data.Builder()
            builder2.putString("SUMMONER_ID", "r6NVF37eYt5-egmPa5XewwZu7pBH2wKwa1dWdBrAE0rdHv4")
            val leaguesRequest = OneTimeWorkRequest.Builder(LeagesWorker::class.java)
                    //.setInputData(builder2.build())
                    .build()
            val matchesRequest = OneTimeWorkRequest.Builder(MatchesWorker::class.java)
                    .build()
            val allChampsRequest = OneTimeWorkRequest.Builder(AllChampsWorker::class.java)
                    .build()

            var workManager = WorkManager.getInstance()

            workManager.beginWith(filterRequest)
                    .then(leaguesRequest)
                    .then(matchesRequest)
                    .then(allChampsRequest)
                    .enqueue()

        }
*/


    companion object{

        private val API_KEY = "RGAPI-598d4462-c753-4662-9ca0-ede60516a90b"
        private var profile: Profile? = null

        private  var INSTANCE: LegendsRoomDatabase? = null

        fun getDatabase(context: Context): LegendsRoomDatabase {

            synchronized(LegendsRoomDatabase::class.java) {

                // Create database here
                INSTANCE = Room.databaseBuilder(context.applicationContext,
                        LegendsRoomDatabase::class.java, "word_database")
                        // Wipes and rebuilds instead of migrating if no Migration object.
                        // Migration is not part of this practical.
                        .fallbackToDestructiveMigration()
                        .addCallback(sRoomDatabaseCallback)
                        .build()


            }
            return this.INSTANCE!!
        }



        // This callback is called when the database has opened.
        // In this case, use PopulateDbAsync to populate the database
        // with the initial data set if the database has no entries.
        private val sRoomDatabaseCallback = object : RoomDatabase.Callback() {

            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
                PopulateDbAsync(INSTANCE!!).execute()

            }

        }
    }


}


