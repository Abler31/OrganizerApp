package com.my.org.presentation.categoryFragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.my.org.R
import com.my.org.domain.models.Category
import com.my.org.domain.models.Event

class CategoriesAdapter(val onClick: (Category) -> Unit) :
    RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder>() {

    val categories = mutableListOf<Category>()

    inner class CategoriesViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val itemCategoryName = itemView.findViewById<TextView>(R.id.tv_category_name)

        init {
            itemView.setOnClickListener {
                onClick(categories[bindingAdapterPosition])
            }
        }
        fun bind(category: Category) {
            itemCategoryName.text = category.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.category_item_view, parent, false)
        return CategoriesViewHolder(
            itemView
        )
    }

    override fun getItemCount(): Int = categories.size

    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {
        holder.bind(categories[position])
    }

    fun updateList(newList: List<Category>){
        categories.clear()
        categories.addAll(newList)
        notifyDataSetChanged()
    }


}