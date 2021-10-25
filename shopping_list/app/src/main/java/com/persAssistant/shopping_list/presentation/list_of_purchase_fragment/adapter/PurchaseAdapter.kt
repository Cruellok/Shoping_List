package com.persAssistant.shopping_list.presentation.list_of_purchase_fragment.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.persAssistant.shopping_list.R
import com.persAssistant.shopping_list.data.database.enitities.Purchase
import com.persAssistant.shopping_list.presentation.list_of_purchase_fragment.OnPurchaseClickListener
import com.persAssistant.shopping_list.presentation.purchase.PurchaseViewModel
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
        val edit: ImageView = view.findViewById(R.id.iv_purchase_edit)
        val delete: ImageView = view.findViewById(R.id.iv_purchase_delete)

        fun bindView(purchase: Purchase, onPurchaseClickListener: OnPurchaseClickListener){
            delete.setOnClickListener {onPurchaseClickListener.deleteItem(purchase)}
            edit.setOnClickListener {onPurchaseClickListener.editItem(purchase)}
            name.setOnClickListener {onPurchaseClickListener.purchaseItemClicked(purchase)}
            price.setOnClickListener {onPurchaseClickListener.purchaseItemClicked(purchase)}
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







