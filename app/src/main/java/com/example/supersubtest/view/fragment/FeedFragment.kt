package com.example.supersubtest.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.supersubtest.R
import com.example.supersubtest.databinding.FragmentFeedBinding
import com.example.supersubtest.model.Explore
import com.example.supersubtest.util.Resource
import com.example.supersubtest.util.Util
import com.example.supersubtest.view.MainActivity
import com.example.supersubtest.view.adapter.CategoryAdapter
import com.example.supersubtest.view.adapter.DrillsAdapter
import com.example.supersubtest.viewmodel.ExerciseViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_feed.*
/**
 * Used to show feed details
 * Data binding enabled
 * Test code first normal way, after add data bindng and do respective chenges
 * Add dialog or progressbar, until we get data from server
 */
class FeedFragment : Fragment() {
    lateinit var viewModel: ExerciseViewModel
    val TAG = "FeedFragment"
    lateinit var categoryAdapter : CategoryAdapter
    lateinit var drillsAdapter: DrillsAdapter

    var binding: FragmentFeedBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        // Set Navigation Icon
        (activity as MainActivity).toolbar.navigationIcon = resources.getDrawable(R.drawable.ic_outline_person_outline)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Data binding
        binding = FragmentFeedBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set recycler views
        setUpRecyclerView()

        // Set observers and view model
        setUpViewModel()
    }

    private fun setUpViewModel() {
        // Get ViewModel
        viewModel = (activity as MainActivity).viewModel

        // Listen for data
        viewModel.exploreData.observe(viewLifecycleOwner, Observer { exploreResponse ->
            when (exploreResponse) {
                is Resource.Success -> {

                    exploreResponse.data?.let { explore ->
                        // Set data  to UI
                        setData(explore)
                        // Hide progressbar
                        Util.hideView(loading)
                    }
                }

                is Resource.Error -> {
                    //Hide progressbar
                    Util.hideView(loading)
                    // Log an error message
                    exploreResponse.message?.let { message ->
                        Log.e(TAG, "Error :: $message")
                    }
                }

                is Resource.Loading -> {
                    //show progressbar till we get data
                    Util.showView(loading)
                }
            }
        })
    }

    private fun setData(explore: Explore) {
        // Set Category RecyclerView
        rvCategories.visibility = View.VISIBLE
        categoryAdapter.differ.submitList(explore.categories)

        // Set Banner
        Glide.with(this)
            .load(explore.banner?.image)
            .into(ivBanner)

        // Set Top Picks
        drillsAdapter.differ.submitList(explore.topPicks!!.drills)
    }

    private fun setUpRecyclerView() {
        categoryAdapter = CategoryAdapter()
        drillsAdapter = DrillsAdapter()

        // set recycler view for categories
        rvCategories.apply {
            adapter = categoryAdapter
            layoutManager = LinearLayoutManager(context,  LinearLayoutManager.HORIZONTAL, false)
        }

        // Set recycler view for top picks
        rvTopPicks.apply {
            adapter = drillsAdapter
            layoutManager = LinearLayoutManager(context)
        }

        // Navigate to exercise(drill details) fragment
        // Put drill id in bundle and use it retrieve sata from server
        drillsAdapter.setOnItemCLickListener {
            // Get clicked item
            // Go to exercise fragment
            val bundle = Bundle().apply {
                putSerializable("drillId", it.id)
            }
            findNavController().navigate(R.id.action_feedFragment_to_exerciseFragment, bundle)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_toolbar, menu)
        super.onCreateOptionsMenu(menu, menuInflater)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}