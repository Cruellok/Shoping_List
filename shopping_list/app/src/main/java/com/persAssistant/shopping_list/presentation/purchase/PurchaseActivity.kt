package com.persAssistant.shopping_list.presentation.purchase

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.persAssistant.shopping_list.R
import com.persAssistant.shopping_list.data.database.enitities.Category
import com.persAssistant.shopping_list.databinding.ActivityPurchaseBinding
import com.persAssistant.shopping_list.presentation.App
import com.persAssistant.shopping_list.presentation.list_of_category_fragment.OnCategoryClickListener
import com.persAssistant.shopping_list.presentation.list_of_category_fragment.adapter.CategoryAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*

abstract class PurchaseActivity: AppCompatActivity() {
    protected abstract fun createViewModel(): PurchaseViewModel
    protected lateinit var ui: ActivityPurchaseBinding
    protected lateinit var viewModel: PurchaseViewModel

    lateinit var categoryAdapter : CategoryAdapter

    companion object {
        const val KEY_PURCHASELIST_ID = "PURCHASE_LIST_ID"
        const val KEY_PURCHASE_ID = "PURCHASE_ID"
        const val KEY_CATEGORY_ID = "CATEGORY_ID"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ui = DataBindingUtil.setContentView(this, R.layout.activity_purchase)

        viewModel = createViewModel()
        viewModel.closeEvent.observe(this, Observer {
            finish()
        })

        ui.llSelectionOfCategoriesForPurchases.setOnClickListener {
            initRecyclerView()
            initAdapter()
        }

        ui.vmPurchase = viewModel
        ui.lifecycleOwner = this
    }

    private fun initRecyclerView() {
        val builder = AlertDialog.Builder(this)
        val inflater: LayoutInflater = this.layoutInflater
        val dialogView: View = inflater.inflate(R.layout.custom_dialog, null)
        builder.setView(dialogView)
        builder.create()

        val mAlertDialog = builder.show()

        val recyclerView: RecyclerView = dialogView.findViewById(R.id.recyclerView_category_custom_dialog)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.itemAnimator = DefaultItemAnimator()
        categoryAdapter = CategoryAdapter(LinkedList(), object : OnCategoryClickListener {

            override fun clickedCategoryItem(category: Category) {
                viewModel.categoryId = category.id!!
                ui.etChooseCategoryInThePurchase.text = category.name
                mAlertDialog.dismiss()
            }
            override fun deleteItem(category: Category) {}
            override fun editItem(category: Category) {}
        })
        recyclerView.adapter = categoryAdapter
    }

    private fun initAdapter() {
        val categoryService = viewModel.getApplication<App>().categoryService
        categoryService.getAll()
            .subscribeOn(Schedulers.single())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({/*Есть данные*/
                categoryAdapter.updateItems(it)
            }, {/*Ошибка*/ })
    }

}
