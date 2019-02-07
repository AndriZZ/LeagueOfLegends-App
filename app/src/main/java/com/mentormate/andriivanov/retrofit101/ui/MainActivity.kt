package com.mentormate.andriivanov.retrofit101.ui

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkContinuation
import androidx.work.WorkManager

import com.google.gson.GsonBuilder
import com.mentormate.andriivanov.retrofit101.JsonPlaceHolderApi
import com.mentormate.andriivanov.retrofit101.R

import com.mentormate.andriivanov.retrofit101.data.BestChampion
import com.mentormate.andriivanov.retrofit101.data.Profile
import com.mentormate.andriivanov.retrofit101.db.*


import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {


    private var jsonPlaceHolderApi: JsonPlaceHolderApi? = null
    private var goProfile: Button? = null
    private var summonerName: EditText? = null
    private var mLegendsViewModel: LegendsViewModel? = null
    internal lateinit var bestChampion: BestChampion
    internal var goodProfile: Profile? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        goProfile = findViewById(R.id.btn_search)
        summonerName = findViewById(R.id.summoner_search)
        mLegendsViewModel = ViewModelProviders.of(this).get(LegendsViewModel::class.java)

        goProfile!!.setOnClickListener {
            bestChampion = BestChampion.instance
            bestChampion.name = summonerName!!.text.toString()

            val intent = Intent(this@MainActivity, TabbedActivity::class.java)
            intent.putExtra("summonerName",   bestChampion.name)
            //intent.putExtra("summonerIconId", goodProfile!!.profileIconId)

            startActivity(intent)

            var builder = Data.Builder()
            builder.putString("name",bestChampion.name)
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
            val masteriesRequest = OneTimeWorkRequest.Builder(MasteriesWorker::class.java)
                    .build()
            val champInfoRequest = OneTimeWorkRequest.Builder(ChampInfoWorker::class.java)
                    .build()



            val filterRequests = mutableListOf<OneTimeWorkRequest>()
            filterRequests.add(allChampsRequest)
            filterRequests.add(matchesRequest)
            filterRequests.add(leaguesRequest)
            filterRequests.add(masteriesRequest)
            filterRequests.add(champInfoRequest)

            var workManager = WorkManager.getInstance()

            workManager.beginWith(filterRequest)
                    .then(filterRequests)
              //        .then(matchesRequest)
                //    .then(leaguesRequest)
                    .enqueue()
            //getProfile()
        }

        val gson = GsonBuilder().serializeNulls().create()

        val retrofit = Retrofit.Builder()
                .baseUrl("https://euw1.api.riotgames.com/lol/")
                .addConverterFactory(GsonConverterFactory.create(gson))

                .build()

        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi::class.java)


    }


    companion object {
        var API_KEY = "RGAPI-c0913d0a-a6e6-4c81-bb2b-c954d2bb062c"
    }


}


