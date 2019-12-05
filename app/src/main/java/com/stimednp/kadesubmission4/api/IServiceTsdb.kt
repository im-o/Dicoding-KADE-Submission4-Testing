package com.stimednp.kadesubmission4.api

import com.stimednp.kadesubmission4.model.*
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by rivaldy on 11/10/2019.
 */

interface IServiceTsdb {
    //list liga
    @GET("api/v1/json/1/all_leagues.php")
    fun getListLeagues(): Deferred<Response<ResponseLeagues>>

    //detail liga
    @GET("api/v1/json/1/lookupleague.php")
    fun getDetailById(@Query("id") idLiga: Int?): Deferred<Response<ResponseLeagues>>

    //next match
    @GET("api/v1/json/1/eventsnextleague.php")
    fun getNextMatch(@Query("id") id: String?): Deferred<Response<ResponseEvents>>

    //previous match
    @GET("api/v1/json/1/eventspastleague.php")
    fun getPrevMatch(@Query("id") id: String?): Deferred<Response<ResponseEvents>>

    //search event / pertandingan
    @GET("api/v1/json/1/searchevents.php")
    fun getSearchEvent(@Query("e") e: String?): Deferred<Response<ResponseSearch>>

    //detail team home
    @GET("api/v1/json/1/lookupteam.php")
    fun getDetailTeamH(@Query("id") idTeam: Int?): Deferred<Response<ResponseTeamsH>>

    //detail team away
    @GET("api/v1/json/1/lookupteam.php")
    fun getDetailTeamA(@Query("id") idTeam: Int?): Deferred<Response<ResponseTeamsA>>

    //detailEvent
    @GET("api/v1/json/1/lookupevent.php")
    fun getDetailEvent(@Query("id") idEvent: String?): Deferred<Response<ResponseEvents>>
}