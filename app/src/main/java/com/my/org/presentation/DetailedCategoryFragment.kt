package com.my.org.presentation

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.my.org.R

class DetailedCategoryFragment : Fragment(R.layout.fragment_detailed_category) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle = arguments

        if(bundle == null){
            Log.d("test", "null bundle")
        }

        val args = bundle?.let { DetailedCategoryFragmentArgs.fromBundle(it) }
        if (args != null) {
            Log.d("test", args.categoryName)
        }

    }

}