package com.my.org.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.view.children
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
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
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Calendar
import java.util.Date
import java.util.Locale

class HomeFragment : Fragment(R.layout.fragment_home) {
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
    }



    val titleRes: Int = R.string.home_title

    private var selectedDate: LocalDate? = null
    private val today = LocalDate.now()

    private val titleSameYearFormatter = DateTimeFormatter.ofPattern("MMMM")
    private val headerFormatter = DateTimeFormatter.ofPattern("MMM d")
    private val selectionFormatter = DateTimeFormatter.ofPattern("d MMM yyyy")
    private val events = mutableMapOf<LocalDate, List<Event>>()
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
                            dotView.isVisible = events[data.date].orEmpty().isNotEmpty()
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
        }
    }
    private fun updateAdapterForDate(date: LocalDate) {
        eventsAdapter.apply {
            events.clear()
            events.addAll(this@HomeFragment.events[date].orEmpty())
            notifyDataSetChanged()
        }

        val selectedText = requireView().findViewById<TextView>(R.id.tv_selectedDate)
        selectedText.text = selectionFormatter.format(date)

        val dayOfWeekHeader = requireView().findViewById<TextView>(R.id.tv_calendar_day_of_week_top)
        dayOfWeekHeader.text = date.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault()).replaceFirstChar { it.uppercaseChar() } + ","

        val dateHeader = requireView().findViewById<TextView>(R.id.tv_calendar_date_top)
        dateHeader.text = headerFormatter.format(date)

        val yearHeader = requireView().findViewById<TextView>(R.id.tv_calendar_year_top)
        yearHeader.text = date.year.toString()
    }
    private val eventsAdapter = EventsAdapter {
        AlertDialog.Builder(requireContext())
            .setMessage("Delete this event?")
            .setPositiveButton("Delete") { _, _ ->
                deleteEvent(it)
            }
            .setNegativeButton("Close", null)
            .show()
    }
    private fun deleteEvent(event: Event) {
        val date = event.date
        events[date] = events[date].orEmpty().minus(event)
        updateAdapterForDate(date)
    }
}
data class Event(val id: String, val text: String, val date: LocalDate)

class EventsAdapter(val onClick: (Event) -> Unit) :
    RecyclerView.Adapter<EventsAdapter.EventsViewHolder>() {

    val events = mutableListOf<Event>()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EventsAdapter.EventsViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.event_item_view, parent, false)
        return EventsViewHolder(
            itemView
        )
    }

    override fun onBindViewHolder(viewHolder: EventsViewHolder, position: Int) {
        viewHolder.bind(events[position])
    }

    override fun getItemCount(): Int = events.size
    inner class EventsViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val itemEventText = itemView.findViewById<TextView>(R.id.itemEventText)
        init {
            itemView.setOnClickListener {
                onClick(events[bindingAdapterPosition])
            }
        }
        fun bind(event: Event) {
            itemEventText.text = event.text
        }
    }
}