package com.example.supersubtest.repository.service

import com.example.supersubtest.model.Exercise
import com.example.supersubtest.model.Explore
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * This interface is for APIs
 * It contains all the api which we wil be using for operations
 * */
interface ExerciseApi {
    @GET("explore")
    suspend fun getExploreData() : Response<Explore>

    @GET("exercise/{exerciseId}")
    suspend fun getExerciseDetails(@Path("exerciseId") exerciseId : String ) : Response<Exercise>
}