package com.persAssistant.shopping_list.presentation.list_of_purchase_activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.persAssistant.shopping_list.R
import com.persAssistant.shopping_list.domain.enitities.Purchase
import com.persAssistant.shopping_list.databinding.RecyclerPurchaseBinding
import com.persAssistant.shopping_list.presentation.App
import com.persAssistant.shopping_list.presentation.purchase.CreatorPurchaseActivity
import com.persAssistant.shopping_list.presentation.purchase.EditorPurchaseActivity
import com.persAssistant.shopping_list.presentation.purchase.PurchaseActivity
import java.util.*

class ListOfPurchaseActivity: AppCompatActivity() {

    private lateinit var purchaseAdapter: PurchaseAdapter
    private lateinit var viewModel: ListOfPurchaseViewModel
    private lateinit var ui: RecyclerPurchaseBinding

    companion object{
        const val CLICKED_FROM_CATEGORY_FRAGMENT = "CLICKED_FROM_CATEGORY_FRAGMENT"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ui = DataBindingUtil.setContentView(this,R.layout.recycler_purchase)

        val app = (this.applicationContext as App)
        viewModel = ListOfPurchaseViewModel(app)

        purchaseAdapter = PurchaseAdapter(LinkedList(), object : OnPurchaseClickListener{
            override fun clickedPurchaseItem(purchase: Purchase) {}

            override fun deleteItem(purchase: Purchase) {
                viewModel.deleteItemPurchase(purchase)
            }

            override fun editItem(purchase: Purchase) {
                val intent = EditorPurchaseActivity.getIntent(applicationContext, purchase.id!!)
                startActivity(intent)
            }

        })
        ui.recyclerViewPurchase.adapter = purchaseAdapter

        viewModel.purchaseList.observe(this, androidx.lifecycle.Observer {
            purchaseAdapter.updateItems(it)
        })

        viewModel.deletePurchaseId.observe(this, androidx.lifecycle.Observer {
            purchaseAdapter.removePurchase(it)
        })

        initAdapter()

        val addPurchase: FloatingActionButton = ui.btnAddPurchase
        val clickedFromCategoryFragment: String? = intent.getStringExtra(CLICKED_FROM_CATEGORY_FRAGMENT)
        if(clickedFromCategoryFragment != null)
            addPurchase.visibility = View.GONE

        addPurchase.setOnClickListener {
            val intent = CreatorPurchaseActivity.getIntent(this, intent.getLongExtra(PurchaseActivity.KEY_SHOPPINGLIST_ID, -1))
            startActivity(intent)
        }
    }

    private fun initAdapter() {
        val categoryId = intent.getLongExtra(PurchaseActivity.KEY_CATEGORY_ID, -1)
        val shoppingListId = intent.getLongExtra(PurchaseActivity.KEY_SHOPPINGLIST_ID, -1)

        if(shoppingListId == -1L && categoryId != -1L) {
            viewModel.init(this, categoryId, viewModel.categories)
        }else if(shoppingListId != -1L && categoryId == -1L) {
            viewModel.init(this, shoppingListId, viewModel.shoppingList)
        }else
            throw Exception("Ошибка в ListOfPurchaseActivity отсутствует listId или categoryId")
    }
}