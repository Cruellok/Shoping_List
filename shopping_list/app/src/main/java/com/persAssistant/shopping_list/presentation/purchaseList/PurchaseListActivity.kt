package com.persAssistant.shopping_list.presentation.purchaseList

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.persAssistant.shopping_list.R
import com.persAssistant.shopping_list.databinding.ActivityPurchaseListBinding
import java.util.*
import javax.xml.datatype.DatatypeConstants.MONTHS

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
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, years, monthOfYear, dayOfMonth ->

                //сам добавил цифру 1
                val monthY: Int = monthOfYear + 1
                val strDate = "$dayOfMonth.$monthY.$years"
                ui.tvDatePurchaseList.text = strDate
                viewModel.strDate.value = strDate
                viewModel.date = Date(years-1900, monthOfYear, dayOfMonth)

            }, year, month, day)

            datePickerDialog.show()
        }

        ui.vmPurchaseList = viewModel
        ui.lifecycleOwner = this
    }

}
