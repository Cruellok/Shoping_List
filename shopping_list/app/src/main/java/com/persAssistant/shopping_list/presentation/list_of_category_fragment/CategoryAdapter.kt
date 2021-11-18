package com.persAssistant.shopping_list.presentation.list_of_category_fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.persAssistant.shopping_list.R
import com.persAssistant.shopping_list.domain.entities.Category
import java.util.*

class CategoryAdapter (private var items: LinkedList<Category>, private val onCategoryClickListener: OnCategoryClickListener) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val categoryRecycleView = items[position]
        holder.name.text = categoryRecycleView.name
        holder.bindView(categoryRecycleView, onCategoryClickListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.recycler_info_category, parent, false)
        return ViewHolder(itemView)
    }

    class ViewHolder(view: View ) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.TV_name_recycler_category)
        val menu: TextView = view.findViewById(R.id.TV_category_menu)

        fun bindView( category: Category, onCategoryClickListener: OnCategoryClickListener){
            name.setOnClickListener {
                onCategoryClickListener.clickedCategoryItem(category)
            }
            menu.setOnClickListener {
                // Creating a popup menu
                val popup = PopupMenu(it.context, menu)
                // Inflating menu from xml resource
                popup.inflate(R.menu.options_menu)
                // Adding click listener
                popup.setOnMenuItemClickListener { item ->
                    when (item.itemId) {
                        R.id.menu_delete -> {
                            onCategoryClickListener.deleteItem(category)
                        }
                        R.id.menu_edit -> {
                            onCategoryClickListener.editItem(category)
                        }
                    }
                    false
                }
                // Displaying the popup
                popup.show()
            }
        }
    }

    fun updateItems(items: LinkedList<Category>) {
        this.items = items
        notifyDataSetChanged()
    }

    fun removeCategory (id: Long?){
        val categoryToRemove = items.find {it.id == id}
        items.remove(categoryToRemove)
        notifyDataSetChanged()
    }
}







