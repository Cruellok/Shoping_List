package com.persAssistant.shopping_list.presentation.purchase

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.persAssistant.shopping_list.R
import com.persAssistant.shopping_list.domain.entities.Category
import com.persAssistant.shopping_list.databinding.ActivityPurchaseBinding
import java.util.*

abstract class PurchaseActivity: AppCompatActivity() {
    protected abstract fun createViewModel(): PurchaseViewModel
    private lateinit var ui: ActivityPurchaseBinding
    protected lateinit var viewModel: PurchaseViewModel

    companion object {
        const val KEY_SHOPPING_LIST_ID = "SHOPPING_LIST_ID"
        const val KEY_PURCHASE_ID = "PURCHASE_ID"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ui = DataBindingUtil.setContentView(this, R.layout.activity_purchase)

        viewModel = createViewModel()
        viewModel.closeEvent.observe(this, Observer {
            finish()
        })

        ui.llSelectionOfCategoriesForPurchases.setOnClickListener {
            SelectionOfCategoryInDialog.show(this@PurchaseActivity, object:
                SelectionOfCategoryInDialog.DialogButtonsClickedListener{
                override fun okClickListener(category: Category) {
                    viewModel.setCategory(category)
                }
            })
        }

        ui.vmPurchase = viewModel
        ui.lifecycleOwner = this
    }
}
