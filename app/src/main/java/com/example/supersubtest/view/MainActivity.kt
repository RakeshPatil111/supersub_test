package com.example.supersubtest.view

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.example.supersubtest.R
import com.example.supersubtest.repository.ExerciseRepository
import com.example.supersubtest.viewmodel.ExerciseViewModel
import com.example.supersubtest.viewmodel.ExerciseViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    lateinit var viewModel: ExerciseViewModel
    lateinit var navController : NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        navController = findNavController(R.id.exercise_fragment)
        // AppBar config
        val appBarConfiguration = AppBarConfiguration.Builder(navController.graph)
            .build()

        // Change theme of toolbar as per fragment
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            when (destination.getId()) {
                R.id.feedFragment -> {
                    setSupportActionBar(toolbar)
                    toolbar.title = "Feed"
                    toolbar.setTitleTextColor(resources.getColor(R.color.colorGreen))
                    toolbar.setBackgroundColor(resources.getColor(R.color.colorWhite))
                    toolbar.navigationIcon = resources.getDrawable(R.drawable.ic_outline_person_outline)
                    NavigationUI.setupActionBarWithNavController(
                        this,
                        navController,
                        appBarConfiguration
                    )
                }
                R.id.exerciseFragment -> {
                    setSupportActionBar(toolbar)
                    toolbar.title = "Batting"
                    toolbar.setTitleTextColor(resources.getColor(R.color.colorWhite))
                    toolbar.setBackgroundColor(resources.getColor(R.color.colorBgDark))
                    toolbar.navigationIcon = resources.getDrawable(R.drawable.ic_baseline_arrow_back)
                    NavigationUI.setupActionBarWithNavController(
                        this,
                        navController,
                        appBarConfiguration
                    )
                }
            }
        }

        // Initiate ViewModel for activity
        // This is the only view model, it wil be used by the fragments
        initiateViewModel()
    }

    private fun initiateViewModel() {
        // Get provider
        val viewModelProvider = ExerciseViewModelFactory(ExerciseRepository())
        viewModel = ViewModelProvider(this, viewModelProvider).get(ExerciseViewModel::class.java)
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, null)
    }
}