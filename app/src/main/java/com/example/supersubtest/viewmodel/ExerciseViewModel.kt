package com.example.supersubtest.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.supersubtest.model.Exercise
import com.example.supersubtest.model.Explore
import com.example.supersubtest.repository.ExerciseRepository
import com.example.supersubtest.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class ExerciseViewModel(val repository: ExerciseRepository) : ViewModel() {
    val exploreData : MutableLiveData<Resource<Explore>> = MutableLiveData()
    val exerciseData : MutableLiveData<Resource<Exercise>> = MutableLiveData()
    var exploreResponse : Explore? = null
    var exerciseResponse : Exercise? = null
    init {
        getExploreData()
    }

    private fun getExploreData() = viewModelScope.launch {
        exploreData.postValue(Resource.Loading())
        val data = repository.getExploreData()
        exploreData.postValue(handleExploreResponse(data))
    }

    private fun handleExploreResponse(response: Response<Explore>): Resource<Explore>? {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(exploreResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    fun getExerciseData(exerciseId : String) = viewModelScope.launch {
        exerciseData.postValue(Resource.Loading())
        val response = repository.getExerciseDetails(exerciseId)
        exerciseData.postValue(handleExerciseResponse(response))
    }

    private fun handleExerciseResponse(response: Response<Exercise>): Resource<Exercise>? {
        if (response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(exerciseResponse ?: it)
            }
        }
        return Resource.Error(response.message())
    }
}