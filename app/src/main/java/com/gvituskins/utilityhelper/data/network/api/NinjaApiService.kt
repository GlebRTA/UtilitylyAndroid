package com.gvituskins.utilityhelper.data.network.api

import com.gvituskins.utilityhelper.data.network.dto.FactDto
import com.gvituskins.utilityhelper.BuildConfig
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface NinjaApiService {

    @Headers("X-Api-Key: ${BuildConfig.NINJA_API_KEY}")
    @GET("v1/facts")
    suspend fun getRandomFacts(): Response<List<FactDto>>
}
