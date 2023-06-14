package com.my.org.presentation.categoryFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.my.org.R
import com.my.org.domain.models.Category
import com.my.org.domain.models.Event
import com.my.org.presentation.inputMethodManager
import java.util.Locale

class CategoriesFragment : Fragment(R.layout.fragment_categories) {
    private val viewModel: CategoriesViewModel by viewModels { CategoriesViewModelFactory(requireContext()) }

    lateinit var categoriesRV: RecyclerView

    private val categoriesAdapter = CategoriesAdapter{

    }

    private val inputDialog by lazy{
        val view = LayoutInflater.from(requireContext()).inflate(R.layout.add_category_dialog, null)
        val name = view.findViewById<EditText>(R.id.et_category_name)

        AlertDialog.Builder(requireContext())
            .setTitle("Создание задачи")
            .setView(view)
            .setPositiveButton("Сохранить") { _, _ ->
                viewModel.insertCategory(Category(name.text.toString()))
                name.setText("")
            }
            .setNegativeButton("Закрыть", null)
            .create()
            .apply {
                setOnShowListener {
                    // Show the keyboard
                    name.requestFocus()
                    context.inputMethodManager
                        .toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
                }
                setOnDismissListener {
                    // Hide the keyboard
                    context.inputMethodManager
                        .toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
                }
            }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnAddCategory = view.findViewById<FloatingActionButton>(R.id.btn_addCategory)

        btnAddCategory.setOnClickListener {
            inputDialog.show()
        }

        categoriesRV = view.findViewById(R.id.rv_categories)
        categoriesRV.layoutManager = GridLayoutManager(requireContext(), 2)
        categoriesRV.adapter = categoriesAdapter

        viewModel.categoriesLiveData.observe(viewLifecycleOwner){
            categoriesAdapter.updateList(it)
        }

    }

}