package com.my.org.presentation.detailedCategories

import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.my.org.R
import com.my.org.domain.models.Event

import com.my.org.presentation.categoryFragment.CategoriesAdapter
import com.my.org.presentation.categoryFragment.CategoriesFragmentDirections
import com.my.org.presentation.categoryFragment.CategoriesViewModel
import com.my.org.presentation.categoryFragment.CategoriesViewModelFactory
import com.my.org.presentation.homeFragment.EventsViewModel
import com.my.org.presentation.homeFragment.EventsViewModelFactory
import com.my.org.presentation.inputMethodManager
import java.util.Locale

class DetailedCategoryFragment : Fragment(R.layout.fragment_detailed_category) {

    private val viewModel: DetailedCategoryViewModel by viewModels { DetailedCategoryViewModelFactory(requireContext()) }
    private val categoriesViewModel: CategoriesViewModel by viewModels { CategoriesViewModelFactory(requireContext()) }
    var categories: List<String> = emptyList()

    lateinit var categoryRV: RecyclerView

    lateinit var spinnerArrayAdapter: ArrayAdapter<String>

    private val categoriesAdapter = DetailedCategoryAdapter{

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle = arguments
        val addButton = view.findViewById<FloatingActionButton>(R.id.btn_detailed_add_task)

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
        val categories = listOf(args?.categoryName ?: "")
        spinnerArrayAdapter = ArrayAdapter<String>(requireContext(), androidx.transition.R.layout.support_simple_spinner_dropdown_item, categories)


        addButton.setOnClickListener{
            val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.add_and_edit_event_dialog, null)
            val name = dialogView.findViewById<EditText>(R.id.et_dialog_name)
            val description = dialogView.findViewById<TextView>(R.id.et_dialog_description)
            val spinner = dialogView.findViewById<Spinner>(R.id.spinnerEvent)
            spinnerArrayAdapter = ArrayAdapter<String>(requireContext(), androidx.transition.R.layout.support_simple_spinner_dropdown_item, categories)
            spinner.adapter = spinnerArrayAdapter

            val timeButton = dialogView.findViewById<Button>(R.id.btn_dialog_timepicker)
            var hour = 0
            var minute = 0

            timeButton.setOnClickListener{
                val onTimeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, selectedHour, selectedMinute ->
                    hour = selectedHour
                    minute = selectedMinute
                    timeButton.setText(String.format(Locale.getDefault(), "%02d:%02d",hour, minute))
                }
                val style = android.app.AlertDialog.THEME_HOLO_DARK

                val timePickerDialog = TimePickerDialog(requireContext(), style, onTimeSetListener, hour, minute, true)
                timePickerDialog.setTitle("Выберите время")
                timePickerDialog.show()
            }

            AlertDialog.Builder(requireContext())
                .setTitle("Создание задачи")
                .setView(dialogView)
                .setPositiveButton("Сохранить") { _, _ ->
                    /*selectedDate?.let { Event(text = name.text.toString(),
                        time = String.format(Locale.getDefault(), "%02d:%02d",hour, minute),
                        description = description.text.toString(),
                        category = spinner.selectedItem.toString(),
                        date = it) }
                        ?.let { viewModel.insertEvent(it) }
                    // Prepare EditText for reuse.
                    name.setText("")
                    description.setText("")*/
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
                .show()
        }

    }

}