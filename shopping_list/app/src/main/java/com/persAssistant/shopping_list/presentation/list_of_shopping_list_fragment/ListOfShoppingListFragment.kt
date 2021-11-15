package com.persAssistant.shopping_list.presentation.list_of_shopping_list_fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.persAssistant.shopping_list.domain.enitities.ShoppingList
import com.persAssistant.shopping_list.databinding.RecyclerShoppingListBinding
import com.persAssistant.shopping_list.presentation.App
import com.persAssistant.shopping_list.presentation.list_of_purchase_activity.ListOfPurchaseActivity
import com.persAssistant.shopping_list.presentation.shopping_list.CreatorShoppingListActivity
import com.persAssistant.shopping_list.presentation.shopping_list.EditorShoppingListActivity
import com.persAssistant.shopping_list.presentation.purchase.PurchaseActivity
import java.util.*

class ListOfShoppingListFragment: Fragment() {

    private lateinit var app : App
    private lateinit var ui: RecyclerShoppingListBinding
    private lateinit var viewModel: ListOfShoppingListViewModel
    private lateinit var shoppingListAdapter: ShoppingListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        ui = RecyclerShoppingListBinding.inflate(layoutInflater)

        shoppingListAdapter = ShoppingListAdapter(LinkedList(), object: OnShoppingListClickListener {

            override fun clickedShoppingListItem(shoppingList: ShoppingList) {
                val intent = ListOfPurchaseActivity.getIntent(requireContext(),shoppingList.id!!,"")
                startActivity(intent)
            }

            override fun deleteItem(shoppingList: ShoppingList) {
                viewModel.deleteItemShoppingList(shoppingList)
            }

            override fun editItem(shoppingList: ShoppingList) {
                val intent = EditorShoppingListActivity.getIntent(requireContext(), shoppingList.id!!)
                startActivity(intent)
            }
        })
        ui.recyclerViewShoppingList.adapter = shoppingListAdapter

        viewModel = ListOfShoppingListViewModel(app)
        viewModel.deleteShoppingListId.observe(requireActivity(), androidx.lifecycle.Observer {
            shoppingListAdapter.removeShoppingList(it)
        })

        viewModel.listOfShoppingList.observe(requireActivity(), androidx.lifecycle.Observer {
            shoppingListAdapter.updateItems(it)
        })

        viewModel.init(this)

        val addShoppingList: FloatingActionButton = ui.btnAddShoppingList
        addShoppingList.setOnClickListener {
            val intent = CreatorShoppingListActivity.getIntent(requireContext())
            startActivity(intent)
        }
        
        return ui.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        app = (context.applicationContext as App)
    }
}