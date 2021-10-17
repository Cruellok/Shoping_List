package com.persAssistant.shopping_list.presentation.purchaseList

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import com.persAssistant.shopping_list.data.database.DbStruct
import java.sql.Date.valueOf
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class CreatorPurchaseListActivity : PurchaseListActivity() {

    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, CreatorPurchaseListActivity::class.java)
        }
    }

    override fun createViewModel(): PurchaseListViewModel {
        return CreatorPurchaseListViewModel(application)
    }
}