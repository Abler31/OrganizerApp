package com.my.org.presentation.detailedCategories

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.my.org.R
import com.my.org.domain.models.Event
import com.my.org.presentation.homeFragment.EventsAdapter
import java.time.format.DateTimeFormatter

class DetailedCategoryAdapter(val onClick: (Event) -> Unit) :
    RecyclerView.Adapter<DetailedCategoryAdapter.EventsViewHolder>() {

    val events = mutableListOf<Event>()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EventsViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.detailed_event_item_view, parent, false)
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
        val itemEventText = itemView.findViewById<TextView>(R.id.detailedItemEventText)
        val itemEventDescription = itemView.findViewById<TextView>(R.id.detailedItemEventDescription)
        val itemEventCategory = itemView.findViewById<TextView>(R.id.detailedItemEventCategory)
        val itemEventTime = itemView.findViewById<TextView>(R.id.detailedItemEventTime)
        val itemEventDate = itemView.findViewById<TextView>(R.id.detailedItemEventDate)

        init {
            itemView.setOnClickListener {
                onClick(events[bindingAdapterPosition])
            }
        }
        fun bind(event: Event) {
            itemEventText.text = event.text
            itemEventDescription.text = event.description
            itemEventCategory.text = event.category
            itemEventTime.text = event.time
            itemEventDate.text = event.date.format(DateTimeFormatter.ofPattern("MMM d"))
        }
    }

    fun updateList(newList: List<Event>){
        events.clear()
        events.addAll(newList)
        notifyDataSetChanged()
    }
}