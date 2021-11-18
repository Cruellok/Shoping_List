package com.persAssistant.shopping_list.presentation.list_of_shopping_list_fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.persAssistant.shopping_list.R
import com.persAssistant.shopping_list.domain.entities.ShoppingList
import java.text.SimpleDateFormat
import java.util.*

class ShoppingListAdapter(private var items: LinkedList<ShoppingList>, private val onShoppingListClickListener: OnShoppingListClickListener)
    : RecyclerView.Adapter<ShoppingListAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val shoppingListRecycleView = items[position]
        holder.name.text = shoppingListRecycleView.name
        holder.date.text = SimpleDateFormat("dd.MM.yyyy").format(shoppingListRecycleView.date)
        holder.bindView(shoppingListRecycleView, onShoppingListClickListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.recycler_info_shopping_list, parent, false)
        return ViewHolder(itemView)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.TV_name_recycler_shoppingList)
        val date: TextView = view.findViewById(R.id.TV_date_recycler_shoppingList)
        val menu: TextView = view.findViewById(R.id.TV_list_menu_purchase)

        fun bindView(shoppingList: ShoppingList, onShoppingListClickListener: OnShoppingListClickListener){
            name.setOnClickListener {
                onShoppingListClickListener.clickedShoppingListItem(shoppingList)
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
                            onShoppingListClickListener.deleteItem(shoppingList)
                        }
                        R.id.menu_edit -> {
                            onShoppingListClickListener.editItem(shoppingList)
                        }
                    }
                    false
                }
                // Displaying the popup
                popup.show()
            }
        }
    }

    fun updateItems(items: LinkedList<ShoppingList>) {
        this.items = items
        notifyDataSetChanged()
    }

    fun removeShoppingList(id: Long?){
        val shoppingListToRemove = items.find {it.id == id}
        items.remove(shoppingListToRemove)
        notifyDataSetChanged()
    }
}







