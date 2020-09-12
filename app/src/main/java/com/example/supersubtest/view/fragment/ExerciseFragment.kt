package com.example.supersubtest.view.fragment

import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat.setBackground
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.NavigationUI
import com.bumptech.glide.Glide
import com.example.supersubtest.R
import com.example.supersubtest.model.Exercise
import com.example.supersubtest.util.Resource
import com.example.supersubtest.util.Util
import com.example.supersubtest.view.MainActivity
import com.example.supersubtest.viewmodel.ExerciseViewModel
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_exercise.*


/**
 * A simple [Fragment] subclass.
 * Use the [ExerciseFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ExerciseFragment : Fragment(){

    lateinit var viewModel: ExerciseViewModel
    val args : FeedFragmentArgs by navArgs()
    val TAG = "ExerciseFragment"
    lateinit var youTubePlayer: YouTubePlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Get Viewmodel
        viewModel = (activity as MainActivity).viewModel

        // Get String id of Drill
        val drillId = args.drillId

        // Get Data of respective drill
        viewModel.getExerciseData(drillId)

        setHasOptionsMenu(false)

        (activity as MainActivity).toolbar.navigationIcon = resources.getDrawable(R.drawable.ic_baseline_arrow_back)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_exercise, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set up observers
        setUpViewModelObservers()

        // Lets pause video if player is not visible
        nestedScrollView.setOnScrollChangeListener { v: NestedScrollView?, scrollX: Int, scrollY: Int,
                                                     oldScrollX: Int, oldScrollY: Int ->
           if (!isViewVisible(youtubePlayerView)) {
               youTubePlayer.pause()
           }
            else {
               youTubePlayer.play()
           }
        }
    }

    private fun setUpViewModelObservers() {
        // Get Data From  View Model
        viewModel.exerciseData.observe(viewLifecycleOwner, Observer { exerciseResponse ->
            when (exerciseResponse) {
                is Resource.Success -> {
                    exerciseResponse.data?.let { exercise ->
                        // Set data to UI
                        setData(exercise)
                        // hide progressbar
                        Util.hideView(loading)
                    }
                }

                is Resource.Error -> {
                    Util.hideView(loading)
                    exerciseResponse.message?.let { message ->
                        Log.e(TAG, "Error :: $message")
                    }
                }

                is Resource.Loading -> {
                    //Show progressbar
                    Util.showView(loading)
                }
            }
        })
    }

    /**
     * Set data to UI
     * After adding data binding
     * Remove this method
     * */
    private fun setData(exercise: Exercise) {
        tvDrillTitle.text = exercise.title
        tvDrillDescription.text = exercise.description
        tvIllustrationCaption.text = exercise.illustration!!.caption
        tvIllustrationDescription.text = exercise.illustration!!.description
        Glide.with(this)
            .load(exercise.illustration!!.imageUrl)
            .into(ivIllustration)

        // Lets PLay video
        loadVideo(exercise.video.url)
    }

    // Initiate youtube player view
    private fun loadVideo(url: String?) {
        lifecycle.addObserver(youtubePlayerView)
        youtubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(player: YouTubePlayer) {
                youTubePlayer = player
                val videoId = Util.getVideoIdFromUrl(url!!)
                youTubePlayer.loadVideo(videoId, 0f)
            }
        })
    }

    private fun isViewVisible(view: View): Boolean {
        val scrollBounds = Rect()
        nestedScrollView.getDrawingRect(scrollBounds)
        val top = view.y
        val bottom = top + view.height
        return scrollBounds.top < top && scrollBounds.bottom > bottom
    }
}