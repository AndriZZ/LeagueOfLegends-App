package com.mentormate.andriivanov.retrofit101.db

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.google.gson.GsonBuilder
import com.mentormate.andriivanov.retrofit101.JsonPlaceHolderApi
import com.mentormate.andriivanov.retrofit101.data.League
import com.mentormate.andriivanov.retrofit101.data.Profile
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.Executors

class AllChampsWorker(context : Context, params : WorkerParameters)
    : Worker(context, params) {
    var mDao: LegendsDao? = null
    lateinit var lgs: List<League>
    lateinit var legendsRoomDatabase: LegendsRoomDatabase

    lateinit var profile: Profile
    internal lateinit var jsonPlaceHolderApi: JsonPlaceHolderApi

    init {


        //  db.clearAllTables();
    }


    private val API_KEY = "RGAPI-6f6ac484-bb85-48d1-aeb0-a824f3698143"

    override fun doWork(): Result {
        Log.e("in LeaguesWorker","doing something")

        val gson = GsonBuilder().serializeNulls().create()
        val retrofit = Retrofit.Builder()
                .baseUrl("https://euw1.api.riotgames.com/lol/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .callbackExecutor(Executors.newCachedThreadPool())
                .build()
        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi::class.java)

      //  val profileId = inputData.getString("SUMMONER_ID")
        //val secondValue = inputData.getInt(SECOND_KEY, 87)


       val call = jsonPlaceHolderApi.allChamps

        val response = call.execute()
        if (response.isSuccessful) {
            legendsRoomDatabase = LegendsRoomDatabase.getDatabase(applicationContext)
            mDao = legendsRoomDatabase?.legendsDao()!!

            val allChamps = response.body()

            for (post in allChamps!!) {

                this.mDao?.insert(post)


            }


            return Result.success()
        }else{
            return Result.failure()
        }

    }
}