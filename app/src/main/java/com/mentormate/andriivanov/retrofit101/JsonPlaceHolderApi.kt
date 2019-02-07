package com.mentormate.andriivanov.retrofit101

import com.mentormate.andriivanov.retrofit101.data.AllChamps
import com.mentormate.andriivanov.retrofit101.data.ChampionInfo
import com.mentormate.andriivanov.retrofit101.data.ChampionMasteries
import com.mentormate.andriivanov.retrofit101.data.League
import com.mentormate.andriivanov.retrofit101.data.MatchList
import com.mentormate.andriivanov.retrofit101.data.Profile

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface JsonPlaceHolderApi {
    @get:GET("https://gist.githubusercontent.com/jfisher446/2844002/raw/077bdbbeece2d20b254304c035bd05bbe780b9b2/lol-champions.json")
    val allChamps: Call<List<AllChamps>>

    @GET("https://raw.communitydragon.org/pbe/plugins/rcp-be-lol-game-data/global/default/v1/champions/{champId}.json")
    fun getChampInfo(@Path("champId") id: Int?): Call<ChampionInfo>

    @GET("https://euw1.api.riotgames.com/lol/match/v4/matchlists/by-account/{accountId}")
    fun getMatches(@Path("accountId") id: String, @Query("api_key") key: String): Call<MatchList>

    @GET("https://euw1.api.riotgames.com/lol/league/v4/positions/by-summoner/{summonerId}")
    fun getLeague(@Path("summonerId") id: String?, @Query("api_key") key: String): Call<List<League>>

    @GET("https://euw1.api.riotgames.com/lol/summoner/v4/summoners/by-name/{summonerName}")
    fun getProfile(@Path("summonerName") name: String?, @Query("api_key") key: String): Call<Profile>

    @GET("https://euw1.api.riotgames.com/lol/champion-mastery/v4/champion-masteries/by-summoner/{id}")
    fun getChampMasteries(@Path("id") id: String, @Query("api_key") key: String): Call<List<ChampionMasteries>>


}
