package com.gvituskins.utilityly.data.network.api

import com.gvituskins.utilityly.data.network.dto.FactDto
import com.gvituskins.utilityly.BuildConfig
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface NinjaApiService {

    @Headers("X-Api-Key: ${BuildConfig.NINJA_API_KEY}")
    @GET("v1/facts")
    suspend fun getRandomFacts(): Response<List<FactDto>>
}
