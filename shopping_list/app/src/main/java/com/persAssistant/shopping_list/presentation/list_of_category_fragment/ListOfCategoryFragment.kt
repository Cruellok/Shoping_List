package com.persAssistant.shopping_list.presentation.list_of_category_fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.persAssistant.shopping_list.data.database.enitities.Category
import com.persAssistant.shopping_list.databinding.RecyclerCategoryBinding
import com.persAssistant.shopping_list.presentation.App
import com.persAssistant.shopping_list.presentation.category.CreatorCategoryActivity
import com.persAssistant.shopping_list.presentation.category.EditorCategoryActivity
import com.persAssistant.shopping_list.presentation.list_of_category_fragment.adapter.CategoryAdapter
import com.persAssistant.shopping_list.presentation.list_of_purchase_activity.ListOfPurchaseActivity
import com.persAssistant.shopping_list.presentation.purchase.PurchaseActivity
import java.util.*

class ListOfCategoryFragment : Fragment() {

    private lateinit var app : App
    private lateinit var viewModel: ListOfCategoryViewModel
    private lateinit var ui: RecyclerCategoryBinding
    private lateinit var categoryAdapter: CategoryAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        ui = RecyclerCategoryBinding.inflate(layoutInflater)

        categoryAdapter = CategoryAdapter(LinkedList(), object : OnCategoryClickListener {
            override fun clickedCategoryItem(category: Category) {
                val intent = Intent(requireContext(), ListOfPurchaseActivity::class.java)
                intent.putExtra(PurchaseActivity.KEY_CATEGORY_ID, category.id)
                startActivity(intent)
            }

            override fun deleteItem(category: Category) {
                viewModel.deleteItemCategory(category)
            }

            override fun editItem(category: Category) {
                val intent = EditorCategoryActivity.getIntent(requireContext(), category.id!!)
                startActivity(intent)
            }
        })

        viewModel = ListOfCategoryViewModel(app)
        viewModel.categoryAdapter = categoryAdapter

        app.categoryService.getChangeSingle().observe(requireActivity(), androidx.lifecycle.Observer {
            viewModel.getAllCategories()
        })

        ui.vm = viewModel

        val addCategory: FloatingActionButton = ui.btnAddCategory
        addCategory.setOnClickListener {
            val intent = CreatorCategoryActivity.getIntent(requireContext())
            startActivity(intent)
        }

        return ui.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        app = (context.applicationContext as App)
    }
}