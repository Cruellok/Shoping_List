package com.persAssistant.shopping_list.presentation.purchaseList

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import java.util.*

abstract class PurchaseListViewModel (application: Application): AndroidViewModel(application) {
    var closeEvent = MutableLiveData<Unit>()
    var name = MutableLiveData<String>()
    var strDate = MutableLiveData<String>()
    var date : Date? = null

    abstract fun save()

}