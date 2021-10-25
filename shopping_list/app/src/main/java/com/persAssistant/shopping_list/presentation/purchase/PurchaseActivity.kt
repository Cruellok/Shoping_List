package com.persAssistant.shopping_list.presentation.purchase

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.persAssistant.shopping_list.R
import com.persAssistant.shopping_list.databinding.ActivityPurchaseBinding
import java.lang.Exception

abstract class PurchaseActivity: AppCompatActivity() {
    protected abstract fun createViewModel(listId: Long): PurchaseViewModel
    protected lateinit var ui: ActivityPurchaseBinding
    protected lateinit var viewModel: PurchaseViewModel

    companion object {
        const val KEY_PURCHASELIST_ID = "PURCHASE_LIST_ID"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ui = DataBindingUtil.setContentView(this, R.layout.activity_purchase)

        val listId = intent.getLongExtra(KEY_PURCHASELIST_ID,-1L)
        if(listId == -1L)
            throw Exception("Ошибка в PurchaseActivity отсутствует listId")

        viewModel = createViewModel(listId)
        viewModel.closeEvent.observe(this, Observer {
            finish()
        })

        ui.vmPurchase = viewModel
        ui.lifecycleOwner = this
    }
}
