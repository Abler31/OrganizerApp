package com.example.resgraphs

import com.my.org.data.retrofit.Data
import retrofit2.Response
import retrofit2.http.GET

interface ResApi {
    @GET("/ponF04")
    suspend fun getPonF04Data(): Response<Data>

    @GET("/pon")
    suspend fun getPonData(): Response<Data>

    @GET("/pov")
    suspend fun getPovData(): Response<Data>

    @GET("/asimF04")
    suspend fun getAsimF04Data(): Response<Data>

    @GET("/prevLimit")
    suspend fun getPrevLimit(): Response<Data>

    @GET("/povBranch")
    suspend fun getPovBranchData(): Response<Data>

    @GET("/ponF04Branch")
    suspend fun getPonF04BranchData(): Response<Data>

    @GET("/ponBranch")
    suspend fun getPonBranchData(): Response<Data>

    @GET("/asimF04Branch")
    suspend fun getAsimF04BranchData(): Response<Data>

    @GET("/prevLimitBranch")
    suspend fun getPrevLimitBranch(): Response<Data>

}