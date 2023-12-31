package com.my.org.presentation.homeFragment

import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.children
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.CalendarMonth
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.daysOfWeek
import com.kizitonwose.calendar.view.CalendarView
import com.kizitonwose.calendar.view.MonthDayBinder
import com.kizitonwose.calendar.view.MonthHeaderFooterBinder
import com.kizitonwose.calendar.view.ViewContainer
import com.my.org.R
import com.my.org.domain.models.Event
import com.my.org.presentation.categoryFragment.CategoriesViewModel
import com.my.org.presentation.inputMethodManager
import com.my.org.presentation.makeInVisible
import com.my.org.presentation.makeVisible
import com.my.org.presentation.setTextColorRes
import dagger.hilt.android.AndroidEntryPoint
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Calendar
import java.util.Date
import java.util.Locale

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private val viewModel: EventsViewModel by viewModels()
    private val categoriesViewModel: CategoriesViewModel by viewModels()
    lateinit var eventsRV: RecyclerView
    var categories: List<String> = emptyList()
    lateinit var spinnerArrayAdapter: ArrayAdapter<String>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        calendarView = view.findViewById(R.id.calendarView)
        val daysOfWeek = daysOfWeek(firstDayOfWeek = DayOfWeek.MONDAY)
        val currentMonth = YearMonth.now()
        val startMonth = currentMonth.minusMonths(50)
        val endMonth = currentMonth.plusMonths(50)
        configureBinders(daysOfWeek)
        val calendarView = view.findViewById<CalendarView>(R.id.calendarView)
        val addButton = view.findViewById<FloatingActionButton>(R.id.btn_addTask)
        val monthText = view.findViewById<TextView>(R.id.tv_calendar_header_month)


        eventsRV = view.findViewById(R.id.rv_tasks)
        eventsRV.layoutManager = LinearLayoutManager(requireContext())
        eventsRV.adapter = eventsAdapter


        viewModel.eventsLiveData.observe(viewLifecycleOwner){
            eventsAdapter.updateList(it.filter {
                it.date == selectedDate
            })
        }

        //отображение категорий в спиннере
        spinnerArrayAdapter = ArrayAdapter<String>(requireContext(), androidx.transition.R.layout.support_simple_spinner_dropdown_item, categories)
        categoriesViewModel.categoriesLiveData.observe(viewLifecycleOwner){
            categories = it.map { category ->
                category.name
            }
            spinnerArrayAdapter.notifyDataSetChanged()
        }
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
                    selectedDate?.let { Event(text = name.text.toString(),
                        time = String.format(Locale.getDefault(), "%02d:%02d",hour, minute),
                        description = description.text.toString(),
                        category = spinner.selectedItem.toString(),
                        date = it) }
                        ?.let { viewModel.insertEvent(it) }
                    // Prepare EditText for reuse.
                    name.setText("")
                    description.setText("")
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

        calendarView.apply {
            setup(startMonth, endMonth, daysOfWeek.first())
            scrollToMonth(currentMonth)
        }
        val months = arrayOf(
            "Январь",
            "Февраль",
            "Март",
            "Апрель",
            "Май",
            "Июнь",
            "Июль",
            "Август",
            "Сентябрь",
            "Октябрь",
            "Ноябрь",
            "Декабрь"
        )

        calendarView.monthScrollListener = {
            val cal = Calendar.getInstance()
            val calDate = Date.from(it.yearMonth.atDay(1).atStartOfDay(ZoneId.systemDefault()).toInstant());
            cal.setTime(calDate)
            monthText.text = months[cal.get(Calendar.MONTH)]
            selectDate(it.yearMonth.atDay(1))
        }

        if (savedInstanceState == null) {
            // Show today's events initially.
            calendarView.post { selectDate(today) }
        }

        ItemTouchHelper(object: ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                AlertDialog.Builder(requireContext())
                    .setTitle("Удалить")
                    .setMessage("Вы действительно хотите удалить задачу?")
                    .setPositiveButton(
                        "Да"
                    ) { dialog, whichButton ->
                        viewModel.deleteEvent(eventsAdapter.getEventAt(viewHolder.bindingAdapterPosition))
                        Toast.makeText(requireContext(), "Удалено", Toast.LENGTH_SHORT).show()
                    }
                    .setNegativeButton(
                        "Нет"
                    ){dialog, whichButton ->
                        eventsAdapter.notifyItemChanged(viewHolder.bindingAdapterPosition)
                    }.show()
            }
        }).attachToRecyclerView(eventsRV)
    }

    private var selectedDate: LocalDate? = null
    private val today = LocalDate.now()

    private val headerFormatter = DateTimeFormatter.ofPattern("MMM d")
    private val selectionFormatter = DateTimeFormatter.ofPattern("d MMM yyyy")
    private val eventsMap = mutableMapOf<LocalDate, List<Event>>()
    private lateinit var calendarView : CalendarView

    private fun configureBinders(daysOfWeek: List<DayOfWeek>) {
        class DayViewContainer(view: View) : ViewContainer(view) {
            lateinit var day: CalendarDay // Will be set when this container is bound.
            val dayText = view.findViewById<TextView>(R.id.dayText)
            val dotView = view.findViewById<View>(R.id.dotView)

            init {
                view.setOnClickListener {
                    if (day.position == DayPosition.MonthDate) {
                        selectDate(day.date)
                    }
                }
            }
        }
        calendarView.dayBinder = object : MonthDayBinder<DayViewContainer> {
            override fun create(view: View) = DayViewContainer(view)
            override fun bind(container: DayViewContainer, data: CalendarDay) {
                container.day = data
                val textView = container.dayText
                val dotView = container.dotView

                textView.text = data.date.dayOfMonth.toString()

                if (data.position == DayPosition.MonthDate) {
                    textView.makeVisible()
                    when (data.date) {
                        today -> {
                            textView.setTextColorRes(R.color.white)
                            textView.setBackgroundResource(R.drawable.calendar_today_bg)
                            dotView.makeInVisible()
                        }
                        selectedDate -> {
                            textView.setTextColorRes(R.color.white)
                            textView.setBackgroundResource(R.drawable.calendar_selected_bg)
                            dotView.makeInVisible()
                        }
                        else -> {
                            textView.setTextColorRes(R.color.black)
                            textView.background = null
                            dotView.isVisible = viewModel.getEventsByDate(data.date).isNotEmpty()
                        }
                    }
                } else {
                    textView.makeInVisible()
                    dotView.makeInVisible()
                }
            }
        }

        class MonthViewContainer(view: View) : ViewContainer(view) {
            val legendLayout = view.findViewById<LinearLayout>(R.id.legendLayout)
        }
        calendarView.monthHeaderBinder =
            object : MonthHeaderFooterBinder<MonthViewContainer> {
                override fun create(view: View) = MonthViewContainer(view)
                override fun bind(container: MonthViewContainer, data: CalendarMonth) {
                    // Setup each header day text if we have not done that already.
                    if (container.legendLayout.tag == null) {
                        container.legendLayout.tag = data.yearMonth
                        container.legendLayout.children.map { it as TextView }
                            .forEachIndexed { index, tv ->
                                tv.text = daysOfWeek[index].getDisplayName(TextStyle.SHORT, Locale.getDefault()).replaceFirstChar { it.uppercaseChar() }
                            }
                    }
                }
            }
    }
    private fun selectDate(date: LocalDate) {
        if (selectedDate != date) {
            val oldDate = selectedDate
            selectedDate = date
            oldDate?.let { calendarView.notifyDateChanged(it) }
            calendarView.notifyDateChanged(date)

            updateAdapterForDate(date)

            val selectedText = requireView().findViewById<TextView>(R.id.tv_selectedDate)
            selectedText.text = selectionFormatter.format(date)

            val dayOfWeekHeader = requireView().findViewById<TextView>(R.id.tv_calendar_day_of_week_top)
            dayOfWeekHeader.text = date.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault()).replaceFirstChar { it.uppercaseChar() } + ","

            val dateHeader = requireView().findViewById<TextView>(R.id.tv_calendar_date_top)
            dateHeader.text = headerFormatter.format(date)

            val yearHeader = requireView().findViewById<TextView>(R.id.tv_calendar_year_top)
            yearHeader.text = date.year.toString()
        }
    }
    private fun updateAdapterForDate(date: LocalDate) {
        eventsAdapter.updateList(viewModel.getEventsByDate(date).orEmpty())
    }
    private val eventsAdapter = EventsAdapter {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.add_and_edit_event_dialog, null)
        val name = dialogView.findViewById<EditText>(R.id.et_dialog_name)
        name.setText(it.text)
        val description = dialogView.findViewById<TextView>(R.id.et_dialog_description)
        description.setText(it.description)
        val spinner = dialogView.findViewById<Spinner>(R.id.spinnerEvent)
        spinnerArrayAdapter = ArrayAdapter<String>(requireContext(), androidx.transition.R.layout.support_simple_spinner_dropdown_item, categories)
        spinner.adapter = spinnerArrayAdapter

        val timeButton = dialogView.findViewById<Button>(R.id.btn_dialog_timepicker)
        timeButton.setText(it.time)
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
                selectedDate?.let { Event(text = name.text.toString(),
                    time = String.format(Locale.getDefault(), "%02d:%02d",hour, minute),
                    description = description.text.toString(),
                    category = spinner.selectedItem.toString(),
                    date = it) }
                    ?.let { viewModel.updateEvent(it) }
                // Prepare EditText for reuse.
                name.setText("")
                description.setText("")
            }
            .setNegativeButton("Закрыть", null)
            .create()
            .show()
    }
}


