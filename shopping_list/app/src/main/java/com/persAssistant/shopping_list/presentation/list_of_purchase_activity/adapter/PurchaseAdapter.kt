package com.persAssistant.shopping_list.presentation.list_of_purchase_activity.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.persAssistant.shopping_list.R
import com.persAssistant.shopping_list.data.database.enitities.Purchase
import com.persAssistant.shopping_list.presentation.list_of_purchase_activity.OnPurchaseClickListener
import java.util.*

class PurchaseAdapter(private var items: LinkedList<Purchase>, private val onPurchaseClickListener: OnPurchaseClickListener)
    : RecyclerView.Adapter<PurchaseAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val purchaseRecycleView = items[position]
        holder.name.text = purchaseRecycleView.name
        holder.price.text = purchaseRecycleView.price.toString()+ "₽"
        holder.bindView(purchaseRecycleView, onPurchaseClickListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.recycler_info_purchase, parent, false)
        return ViewHolder(itemView)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.tv_name_recycler_purchase)
        val price: TextView = view.findViewById(R.id.tv_price_recycler_purchase)
        val menu: TextView = view.findViewById(R.id.tv_options_menu)

        fun bindView(purchase: Purchase, onPurchaseClickListener: OnPurchaseClickListener){
            name.setOnClickListener {onPurchaseClickListener.clickedPurchaseItem(purchase)}
            price.setOnClickListener {onPurchaseClickListener.clickedPurchaseItem(purchase)}
            menu.setOnClickListener {
                //creating a popup menu
                val popup = PopupMenu(it.context, menu)
                //inflating menu from xml resource
                popup.inflate(R.menu.options_menu)
                //adding click listener
                popup.setOnMenuItemClickListener { item ->
                    when (item.itemId) {
                        R.id.menu_delete -> {
                            onPurchaseClickListener.deleteItem(purchase)
                        }
                        R.id.menu_edit -> {
                            onPurchaseClickListener.editItem(purchase)
                        }
                    }
                    false
                }
                //displaying the popup
                popup.show()
            }
        }
    }

    fun updateItems(items: LinkedList<Purchase>) {
        this.items = items
        notifyDataSetChanged()
    }

    fun removePurchase(id: Long?){
        val purchaseToRemove = items.find {it.id == id}
        items.remove(purchaseToRemove)
        notifyDataSetChanged()
    }
}







