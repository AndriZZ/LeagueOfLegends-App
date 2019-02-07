package com.mentormate.andriivanov.retrofit101.db

import android.content.Context
import android.util.Log
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.google.gson.GsonBuilder
import com.mentormate.andriivanov.retrofit101.JsonPlaceHolderApi
import com.mentormate.andriivanov.retrofit101.data.League
import com.mentormate.andriivanov.retrofit101.data.Profile
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.Executors

class MasteriesWorker(context : Context, params : WorkerParameters)
    : Worker(context, params) {
    var mDao: LegendsDao? = null
    lateinit var build: Data
    lateinit var legendsRoomDatabase: LegendsRoomDatabase

    lateinit var profile: Profile
    internal lateinit var jsonPlaceHolderApi: JsonPlaceHolderApi

    init {


        //  db.clearAllTables();
    }


    private val API_KEY = "RGAPI-6f6ac484-bb85-48d1-aeb0-a824f3698143"

    override fun doWork(): Result {
        Log.e("in LeaguesWorker", "doing something")

        val gson = GsonBuilder().serializeNulls().create()
        val retrofit = Retrofit.Builder()
                .baseUrl("https://euw1.api.riotgames.com/lol/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .callbackExecutor(Executors.newCachedThreadPool())
                .build()
        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi::class.java)

        val profileId = inputData.getString("SUMMONER_ID")
        //val secondValue = inputData.getInt(SECOND_KEY, 87)


        val call = jsonPlaceHolderApi.getChampMasteries(profileId!!, API_KEY)
        val response = call.execute()
        if (response.isSuccessful) {
            val masteries = response.body()!!
            legendsRoomDatabase = LegendsRoomDatabase.getDatabase(applicationContext)
            mDao = legendsRoomDatabase?.legendsDao()!!
            var maxPoints=0
            var champID=0
            for (mastery in masteries!!) {


                mDao?.insert(mastery)
                if (mastery.championPoints!! > maxPoints) {
                    maxPoints =mastery.championPoints
                    champID =mastery.championId
                }


            }

            build = Data.Builder().putInt("BEST_CHAMP_ID", champID).build()
            return Result.success(build)
        } else {
            return Result.failure()
        }

    }

}