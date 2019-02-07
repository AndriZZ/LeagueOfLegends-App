package com.mentormate.andriivanov.retrofit101.db

import android.annotation.SuppressLint
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

class MatchesWorker(context: Context, params: WorkerParameters)
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

    @SuppressLint("RestrictedApi")
    override fun doWork(): Result {
        Log.e("in МаtchesWorker", "doing something in Matches")

        val gson = GsonBuilder().serializeNulls().create()
        val retrofit = Retrofit.Builder()
                .baseUrl("https://euw1.api.riotgames.com/lol/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .callbackExecutor(Executors.newCachedThreadPool())
                .build()
        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi::class.java)

        val accountId = inputData.getString("ACCOUNT_ID")
        //val secondValue = inputData.getInt(SECOND_KEY, 87)


        val call = jsonPlaceHolderApi.getMatches(accountId!!, API_KEY)
        val response = call?.execute()
        if (response != null) {
            if (response.isSuccessful) {

                legendsRoomDatabase = LegendsRoomDatabase.getDatabase(applicationContext)
                mDao = legendsRoomDatabase?.legendsDao()!!
                val matches = response.body()

                for (match in matches!!.matches!!) {

                    mDao?.insert(match)
                }

                return Result.success()


            } else {
                return Result.failure()
            }
        }
        return Result.success()
    }
}