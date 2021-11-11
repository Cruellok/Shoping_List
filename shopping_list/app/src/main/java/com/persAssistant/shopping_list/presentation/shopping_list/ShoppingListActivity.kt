package com.persAssistant.shopping_list.presentation.shopping_list

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.persAssistant.shopping_list.R
import com.persAssistant.shopping_list.databinding.ActivityShoppingListBinding
import java.text.SimpleDateFormat
import java.util.*

abstract class ShoppingListActivity : AppCompatActivity() {

    protected abstract fun createViewModel(): ShoppingListViewModel

    protected lateinit var ui: ActivityShoppingListBinding
    protected lateinit var viewModel: ShoppingListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ui = DataBindingUtil.setContentView(this, R.layout.activity_shopping_list)

        viewModel = createViewModel()
        viewModel.closeEvent.observe(this, Observer {
            finish()
        })

        ui.tvDateShoppingList.setOnClickListener {

            val date = SimpleDateFormat("dd.MM.yyyy").parse(viewModel.strDate.value!!)
            val calendar = Calendar.getInstance()
            calendar.time = date!!
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(this, { view, yearDPD, monthOfYear, dayOfMonth ->
                calendar.set(yearDPD,monthOfYear,dayOfMonth)
                viewModel.date = calendar.time
            }, year, month, day)

            datePickerDialog.show()
        }
        ui.vmShoppingList = viewModel
        ui.lifecycleOwner = this
    }

}
