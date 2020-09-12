package com.example.supersubtest.repository

import com.example.supersubtest.repository.service.RetrofitClient

/**
 * This is repository class
 * Use this class to communicate further with either local storage or remote server
 * Here we used remote server
 * */
class ExerciseRepository {
    suspend fun getExploreData() =
        RetrofitClient.api.getExploreData()

    suspend fun getExerciseDetails(exercisedId : String) =
        RetrofitClient.api.getExerciseDetails(exercisedId)
}