package com.gvituskins.vituskinsandroid.data.network.api

import com.gvituskins.vituskinsandroid.BuildConfig
import com.gvituskins.vituskinsandroid.data.network.dto.FactDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface NinjaApiService {

    @Headers("X-Api-Key: ${BuildConfig.NINJA_API_KEY}")
    @GET("v1/facts")
    suspend fun getRandomFacts(): Response<List<FactDto>>
}
