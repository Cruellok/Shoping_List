package com.persAssistant.shopping_list

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.persAssistant.shopping_list.data.database.RoomDataBaseHelper
import com.persAssistant.shopping_list.data.database.enitities.Category
import com.persAssistant.shopping_list.data.database.enitities.PurchaseList
import com.persAssistant.shopping_list.data.database.service.CategoryService
import com.persAssistant.shopping_list.data.database.service.PurchaseListService
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
class TestPurchaseListService : CommonTest(){

    private val appContext = InstrumentationRegistry.getInstrumentation().targetContext
    private val dataBaseHelper = RoomDataBaseHelper.getInstance(appContext)

    private val purchaseListDao = dataBaseHelper.getPurchaseListRoomDao()
    private val purchaseListService = PurchaseListService(purchaseListDao)

    @Test
    fun purchaseListTest() {

        //---Insert---
        val dailyTime = 1000*60*60*24
        val today = Date()
        val yesterday = Date(today.time - dailyTime)
        val tomorrow = Date(today.time + dailyTime)
        val afterTomorrow = Date(tomorrow.time + dailyTime)

        var travelList = PurchaseList(name = "Путешествие", date = today )
        var carList = PurchaseList(name = "Автомобиль", date = today )
        var homeList = PurchaseList(name = "Дом", date = today )

        purchaseListService.insert(travelList).blockingGet()
        purchaseListService.insert(carList).blockingGet()
        purchaseListService.insert(homeList).blockingGet()
        //Проверка инсерта, что вернется объект по добавленному id
        assertEquals("Функция вернула не верный результат execut ", PurchaseList(id = travelList.id , name = "Путешествие", date = today ), purchaseListService.getById(travelList.id!!).blockingGet())
        assertEquals("Функция вернула не верный результат execut ", PurchaseList(id = carList.id , name = "Автомобиль", date = today ), purchaseListService.getById(carList.id!!).blockingGet())
        assertEquals("Функция вернула не верный результат execut ", PurchaseList(id = homeList.id , name = "Дом", date = today ), purchaseListService.getById(homeList.id!!).blockingGet())

        //---Update---
        travelList = PurchaseList(id = travelList.id, name = "Тренировка", date = tomorrow )
        carList = PurchaseList(id = carList.id, name = "Работа", date = yesterday )
        homeList = PurchaseList(id = homeList.id, name = "Дом", date = afterTomorrow )

        purchaseListService.update(travelList).blockingGet()
        purchaseListService.update(carList).blockingGet()
        purchaseListService.update(homeList).blockingGet()
        //Проверка инсерта, что вернется объект по добавленному id
        assertEquals("Функция вернула не верный результат execut ", PurchaseList(id = travelList.id  , name = "Тренировка", date = tomorrow ), purchaseListService.getById(travelList.id!!).blockingGet())
        assertEquals("Функция вернула не верный результат execut ", PurchaseList(id = carList.id , name = "Работа", date = yesterday ), purchaseListService.getById(carList.id!!).blockingGet())
        assertEquals("Функция вернула не верный результат execut ", PurchaseList(id = homeList.id , name = "Дом", date = afterTomorrow ), purchaseListService.getById(homeList.id!!).blockingGet())

        //---Delete---
        purchaseListService.delete(carList).blockingGet()
        //Проверка инсерта, что вернется объект по добавленному id
        assertEquals("Функция вернула не верный результат execut ", null, purchaseListService.getById(carList.id!!).blockingGet())

        //---Get All---
        assertEquals("Функция вернула не верный результат execut ", 2, purchaseListService.getAll().blockingGet().size)
    }
    @Test
    fun purchaseListTest2() {

        //---Insert---
        val dailyTime = 1000*60*60*24
        val today = Date()
        val yesterday = Date(today.time - dailyTime)
        val tomorrow = Date(today.time + dailyTime)
        val afterTomorrow = Date(tomorrow.time + dailyTime)

        var travelList = PurchaseList(name = "Путешествие", date = today )
        var carList = PurchaseList(name = "Автомобиль", date = today )
        var homeList = PurchaseList(name = "Дом", date = today )

        purchaseListService.insert(travelList).blockingGet()
        purchaseListService.insert(carList).blockingGet()
        purchaseListService.insert(homeList).blockingGet()
        //Проверка инсерта, что вернется объект по добавленному id
        assertEquals("Функция вернула не верный результат execut ", PurchaseList(id = travelList.id , name = "Путешествие", date = today ), purchaseListService.getById(travelList.id!!).blockingGet())
        assertEquals("Функция вернула не верный результат execut ", PurchaseList(id = carList.id , name = "Автомобиль", date = today ), purchaseListService.getById(carList.id!!).blockingGet())
        assertEquals("Функция вернула не верный результат execut ", PurchaseList(id = homeList.id , name = "Дом", date = today ), purchaseListService.getById(homeList.id!!).blockingGet())

        //---Update---
        travelList = PurchaseList(id = travelList.id, name = "Тренировка", date = tomorrow )
        carList = PurchaseList(id = carList.id, name = "Работа", date = yesterday )
        homeList = PurchaseList(id = homeList.id, name = "Дом", date = afterTomorrow )

        purchaseListService.update(travelList).blockingGet()
        purchaseListService.update(carList).blockingGet()
        purchaseListService.update(homeList).blockingGet()
        //Проверка инсерта, что вернется объект по добавленному id
        assertEquals("Функция вернула не верный результат execut ", PurchaseList(id = travelList.id  , name = "Тренировка", date = tomorrow ), purchaseListService.getById(travelList.id!!).blockingGet())
        assertEquals("Функция вернула не верный результат execut ", PurchaseList(id = carList.id , name = "Работа", date = yesterday ), purchaseListService.getById(carList.id!!).blockingGet())
        assertEquals("Функция вернула не верный результат execut ", PurchaseList(id = homeList.id , name = "Дом", date = afterTomorrow ), purchaseListService.getById(homeList.id!!).blockingGet())

        //---Delete---
        purchaseListService.delete(carList).blockingGet()
        //Проверка инсерта, что вернется объект по добавленному id
        assertEquals("Функция вернула не верный результат execut ", null, purchaseListService.getById(carList.id!!).blockingGet())

        //---Get All---
        assertEquals("Функция вернула не верный результат execut ", 2, purchaseListService.getAll().blockingGet().size)
    }

}