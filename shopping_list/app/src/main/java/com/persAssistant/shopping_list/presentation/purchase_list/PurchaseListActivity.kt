package com.persAssistant.shopping_list.presentation.purchase_list

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.persAssistant.shopping_list.R
import com.persAssistant.shopping_list.databinding.ActivityPurchaseListBinding
import java.text.SimpleDateFormat
import java.util.*

abstract class PurchaseListActivity : AppCompatActivity() {

    protected abstract fun createViewModel(): PurchaseListViewModel

    protected lateinit var ui: ActivityPurchaseListBinding
    protected lateinit var viewModel: PurchaseListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ui = DataBindingUtil.setContentView(this, R.layout.activity_purchase_list)

        viewModel = createViewModel()
        viewModel.closeEvent.observe(this, Observer {
            finish()
        })

        ui.tvDatePurchaseList.setOnClickListener {

            val date = SimpleDateFormat("dd.MM.yyyy").parse(viewModel.strDate.value!!)
            val calendar = Calendar.getInstance()
            calendar.time = date!!
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                calendar.set(year,monthOfYear,dayOfMonth)
                viewModel.date = calendar.time
            }, year, month, day)

            datePickerDialog.show()
        }
        ui.vmPurchaseList = viewModel
        ui.lifecycleOwner = this
    }

}
