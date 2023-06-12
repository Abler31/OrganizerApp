package com.my.org.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.my.org.R
import com.my.org.domain.models.Event

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

    fun updateList(newList: List<Event>){
        events.clear()
        events.addAll(newList)
        notifyDataSetChanged()
    }
}