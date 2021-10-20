package com.persAssistant.shopping_list

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.persAssistant.shopping_list.data.database.RoomDataBaseHelper
import com.persAssistant.shopping_list.data.database.enitities.Purchase
import com.persAssistant.shopping_list.data.database.enitities.PurchaseList
import com.persAssistant.shopping_list.data.database.service.PurchaseService
import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import java.util.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class TestPurchaseService : CommonTest(){
    private val appContext = InstrumentationRegistry.getInstrumentation().targetContext
    private val dataBaseHelper = RoomDataBaseHelper.getInstance(appContext)
    private val purchaseDao = dataBaseHelper.getPurchaseRoomDao()
    private val purchaseService = PurchaseService(purchaseDao)

    @Test
    fun purchaseTest() {

        //---Insert---
        var iron = Purchase(name = "утюг", categoryId = ,listId = ,isCompleted = 1)
        var duck = Purchase(name = "савок", categoryId = ,listId = ,isCompleted = 1)
        var bread = Purchase(name = "хлеб", categoryId = ,listId = ,isCompleted = 1)
        var matches = Purchase(name = "спички", categoryId = ,listId = ,isCompleted = 1)

    }
}