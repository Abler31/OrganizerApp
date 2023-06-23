package com.my.org.presentation.detailedCategories

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.my.org.R

import com.my.org.presentation.categoryFragment.CategoriesAdapter
import com.my.org.presentation.categoryFragment.CategoriesFragmentDirections
import com.my.org.presentation.categoryFragment.CategoriesViewModel
import com.my.org.presentation.categoryFragment.CategoriesViewModelFactory
import com.my.org.presentation.homeFragment.EventsViewModel
import com.my.org.presentation.homeFragment.EventsViewModelFactory

class DetailedCategoryFragment : Fragment(R.layout.fragment_detailed_category) {

    private val viewModel: DetailedCategoryViewModel by viewModels { DetailedCategoryViewModelFactory(requireContext()) }

    lateinit var categoryRV: RecyclerView

    private val categoriesAdapter = DetailedCategoryAdapter{

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle = arguments

        if(bundle == null){
            Log.d("test", "null bundle")
        }

        val args = bundle?.let { DetailedCategoryFragmentArgs.fromBundle(bundle)}
        if (args != null) {
            Log.d("test", args.categoryName)
        }

        categoryRV = view.findViewById(R.id.rv_detailed_category)
        categoryRV.layoutManager = LinearLayoutManager(requireContext())
        categoryRV.adapter = categoriesAdapter

        if (args != null) {
            //viewModel.getEventsByCategory(args.categoryName)
            categoriesAdapter.updateList(viewModel.getEventsByCategory(args.categoryName).orEmpty())
        }

        viewModel.eventsLiveData.observe(viewLifecycleOwner){
            if (args != null) {
                categoriesAdapter.updateList(viewModel.getEventsByCategory(args.categoryName))
            }
        }

    }

}