package com.persAssistant.shopping_list.presentation.list_of_purchase_list_fragment.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.persAssistant.shopping_list.R
import com.persAssistant.shopping_list.data.database.enitities.PurchaseList
import com.persAssistant.shopping_list.presentation.list_of_purchase_list_fragment.OnPurchaseListClickListener
import java.text.SimpleDateFormat
import java.util.*

class PurchaseListAdapter( private var items: LinkedList<PurchaseList>, private val onPurchaseListClickListener: OnPurchaseListClickListener)
    : RecyclerView.Adapter<PurchaseListAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val purchaseListRecycleView = items[position]
        holder.name.text = purchaseListRecycleView.name
        holder.date.text = SimpleDateFormat("dd.MM.yyyy").format(purchaseListRecycleView.date)
        holder.bindView(purchaseListRecycleView, onPurchaseListClickListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.recycler_info_purchase_list, parent, false)
        return ViewHolder(itemView)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.TV_name_recycler_purchaseList)
        val date: TextView = view.findViewById(R.id.TV_date_recycler_purchaseList)
        val menu: TextView = view.findViewById(R.id.TV_list_menu_purchase)

        fun bindView(purchaseList: PurchaseList, onPurchaseListClickListener: OnPurchaseListClickListener){
            name.setOnClickListener {
                onPurchaseListClickListener.clickedPurchaseListItem(purchaseList)
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
                            onPurchaseListClickListener.deleteItem(purchaseList)
                        }
                        R.id.menu_edit -> {
                            onPurchaseListClickListener.editItem(purchaseList)
                        }
                    }
                    false
                }
                // Displaying the popup
                popup.show()
            }
        }
    }

    fun updateItems(items: LinkedList<PurchaseList>) {
        this.items = items
        notifyDataSetChanged()
    }

    fun removePurchaseList(id: Long?){
        val purchaseListToRemove = items.find {it.id == id}
        items.remove(purchaseListToRemove)
        notifyDataSetChanged()
    }
}







