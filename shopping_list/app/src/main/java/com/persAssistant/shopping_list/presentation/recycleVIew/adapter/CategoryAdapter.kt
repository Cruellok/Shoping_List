package com.persAssistant.shopping_list.presentation.recycleVIew.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.persAssistant.shopping_list.R
import com.persAssistant.shopping_list.data.database.enitities.Category
import com.persAssistant.shopping_list.presentation.recycleVIew.OnCategoryClickListener
import java.util.*

class CategoryAdapter (private var items: LinkedList<Category>, private val onCategoryClickListener: OnCategoryClickListener)
    : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

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
        var name: TextView = view.findViewById(R.id.tv_name_recycler_category)
        val delete: ImageView = view.findViewById(R.id.iv_category_delete)
        
        fun bindView( category: Category, onCategoryClickListener: OnCategoryClickListener){
            delete.setOnClickListener { onCategoryClickListener.deleteItem(category) }
            name.setOnClickListener{ onCategoryClickListener.categoryItemClicked(category)}
        }
    }

    fun updateItems(items: LinkedList<Category>) {
        this.items = items
        notifyDataSetChanged()
    }

    fun addCategory (category: Category){
        items.add(category)
        notifyDataSetChanged()
    }

    fun removeCategory (id: Long?){
        val categoryToRemove = items.find {it.id == id}
        items.remove(categoryToRemove)
        notifyDataSetChanged()
    }


}







