package com.persAssistant.shopping_list.presentation.list_of_purchase_activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.persAssistant.shopping_list.R
import com.persAssistant.shopping_list.domain.entities.Purchase
import com.persAssistant.shopping_list.databinding.RecyclerPurchaseBinding
import com.persAssistant.shopping_list.presentation.App
import com.persAssistant.shopping_list.presentation.purchase.CreatorPurchaseActivity
import com.persAssistant.shopping_list.presentation.purchase.EditorPurchaseActivity
import com.persAssistant.shopping_list.presentation.list_of_purchase_activity.ListOfPurchaseViewModel.*
import java.util.*

class ListOfPurchaseActivity: AppCompatActivity() {

    private lateinit var purchaseAdapter: PurchaseAdapter
    private lateinit var ui: RecyclerPurchaseBinding
    private lateinit var viewModel: ListOfPurchaseViewModel

    companion object{
        const val KEY_SHOPPING_LIST_ID = "PURCHASE_LIST_ID"
        const val KEY_CATEGORY_ID = "CATEGORY_ID"
        const val KEY_CREATION_TYPE = "CREATION_TYPE"
        const val KEY_VISIBILITY_BUTTON = "KEY_VISIBILITY_BUTTON"

        fun getIntentByShoppingListId(context: Context, id: Long): Intent {
            val intent = Intent(context, ListOfPurchaseActivity::class.java)
            intent.putExtra(KEY_SHOPPING_LIST_ID, id)
            intent.putExtra(KEY_VISIBILITY_BUTTON, true)
            intent.putExtra(KEY_CREATION_TYPE, IdTypes.SHOPPINGLIST.name)
            return intent
        }

        fun getIntentByCategoryId(context: Context, id: Long): Intent{
            val intent = Intent(context, ListOfPurchaseActivity::class.java)
            intent.putExtra(KEY_CATEGORY_ID, id)
            intent.putExtra(KEY_VISIBILITY_BUTTON, false)
            intent.putExtra(KEY_CREATION_TYPE, IdTypes.CATEGORY.name)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ui = DataBindingUtil.setContentView(this,R.layout.recycler_purchase)

        val app = applicationContext as App
        viewModel = app.appComponent.getListOfPurchaseViewModel()

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

        viewModel.purchaseListPair.observe(this, androidx.lifecycle.Observer {
            purchaseAdapter.updateItems(it)
        })

        viewModel.deletePurchaseId.observe(this, androidx.lifecycle.Observer {
            purchaseAdapter.removePurchase(it)
        })

        val categoryId = intent.getLongExtra(KEY_CATEGORY_ID, -1)
        val shoppingListId = intent.getLongExtra(KEY_SHOPPING_LIST_ID, -1)
        viewModel.init(this, categoryId, shoppingListId, intent.getStringExtra(KEY_CREATION_TYPE)!!)

        val buttonAdd: FloatingActionButton = ui.btnAddPurchase
        val buttonVisible = intent.getBooleanExtra(KEY_VISIBILITY_BUTTON,false)
        if (buttonVisible)
            buttonAdd.visibility = View.VISIBLE

        buttonAdd.setOnClickListener {
            val intent = CreatorPurchaseActivity.getIntent(this, intent.getLongExtra(KEY_SHOPPING_LIST_ID, -1))
            startActivity(intent)
        }
    }
}