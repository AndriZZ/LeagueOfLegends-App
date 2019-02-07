package com.mentormate.andriivanov.retrofit101.db

import android.content.Context
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.google.gson.GsonBuilder
import com.mentormate.andriivanov.retrofit101.JsonPlaceHolderApi
import com.mentormate.andriivanov.retrofit101.data.Profile
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.Executors

class ProfileWorker(context: Context, params: WorkerParameters)
    : Worker(context, params) {

    var mDao: LegendsDao? = null
    lateinit var  outputData2: Data
    lateinit var legendsRoomDatabase: LegendsRoomDatabase

    lateinit var profile: Profile
    internal lateinit var jsonPlaceHolderApi: JsonPlaceHolderApi

    init {


        //  db.clearAllTables();
    }


    private val API_KEY = "RGAPI-6f6ac484-bb85-48d1-aeb0-a824f3698143"

    override fun doWork(): Result {


        val gson = GsonBuilder().serializeNulls().create()
        val retrofit = Retrofit.Builder()
                .baseUrl("https://euw1.api.riotgames.com/lol/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .callbackExecutor(Executors.newCachedThreadPool())
                .build()
        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi::class.java)

        val profileName = inputData.getString("name")

        val call = jsonPlaceHolderApi.getProfile(profileName, API_KEY)
        val response = call.execute()

                if (response.isSuccessful) {
                    legendsRoomDatabase = LegendsRoomDatabase.getDatabase(applicationContext)
                    mDao = legendsRoomDatabase?.legendsDao()!!

                    profile = response.body()!!
                    profile?.let { mDao!!.insert(it) }

                    outputData2 = createOutputData(profile.id, profile.accountId)

                }


        if( ::outputData2.isInitialized){
            return Result.success(this.outputData2)
        }else {
            return Result.failure()
        }



        // return Result.FAILURE


        // return Re
    }

    private fun createOutputData(summonerId: String?, accountId: String?): Data {
        return Data.Builder()
                .putString("SUMMONER_ID", summonerId)
                .putString("ACCOUNT_ID", accountId)
                .build()
    }


}