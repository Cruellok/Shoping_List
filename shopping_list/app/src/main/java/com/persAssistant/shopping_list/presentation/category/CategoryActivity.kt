package com.persAssistant.shopping_list.presentation.category

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.persAssistant.shopping_list.R
import com.persAssistant.shopping_list.databinding.ActivityCategoryBinding

abstract class CategoryActivity : AppCompatActivity() {

    protected abstract fun createViewModel(): CategoryViewModel

    private lateinit var ui: ActivityCategoryBinding
    private lateinit var viewModel: CategoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ui = DataBindingUtil.setContentView(this, R.layout.activity_category)

        viewModel = createViewModel()
        viewModel.closeEvent.observe(this, Observer {
            finish()
        })

        ui.vm = viewModel
        ui.lifecycleOwner = this
    }

}