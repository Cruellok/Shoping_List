package com.persAssistant.shopping_list

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.persAssistant.shopping_list.data.database.RoomDataBaseHelper
import com.persAssistant.shopping_list.data.database.enitities.Category
import com.persAssistant.shopping_list.data.database.enitities.Purchase
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
class TestPurchaseService : CommonTest() {
    private val appContext = InstrumentationRegistry.getInstrumentation().targetContext
    private val dataBaseHelper = RoomDataBaseHelper.getInstance(appContext)

    //---Category---
    private val categoryDao = dataBaseHelper.getCategoryRoomDao()
    private val categoryService = CategoryService(categoryDao)
    private val undefinedCategory = Category(name = "Неопределенно")
    private val foodCategory = Category(name = "Еда")
    private val homeCategory = Category(name = "Дом")

    //---PurchaseList---
    private val purchaseListDao = dataBaseHelper.getPurchaseListRoomDao()
    private val purchaseListService = PurchaseListService(purchaseListDao)
    private val dailyTime = 1000*60*60*24
    private val today = Date()
    private val yesterday = Date(today.time - dailyTime)
    private val everydayLifeList = PurchaseList(name = "быт", date = today )
    private val carList = PurchaseList(name = "Автомобиль", date = yesterday )

    //---Purchase---
    private val purchaseDao = dataBaseHelper.getPurchaseRoomDao()
    private val purchaseService = PurchaseService(purchaseDao)

    private fun initialized() {
        //---Category---
        categoryService.insert(foodCategory).blockingGet()
        categoryService.insert(undefinedCategory).blockingGet()
        categoryService.insert(homeCategory).blockingGet()

        //---PurchaseList---
        purchaseListService.insert(everydayLifeList).blockingGet()
        purchaseListService.insert(carList).blockingGet()
    }

    @Test
    fun insertTest() {
        initialized()
        val bread = Purchase(name = "хлеб", categoryId = foodCategory.id!!, listId = everydayLifeList.id!!, isCompleted = 0)
        val pliers = Purchase(name = "пассатижи", categoryId = undefinedCategory.id!!, listId = carList.id!!, isCompleted = 0)

        //---insert---
        purchaseService.insert(pliers).blockingGet()
        purchaseService.insert(bread).blockingGet()

        //---getById---
        assertEquals("Функция вернула не верный результат insertTest ", Purchase(id = bread.id, name = "хлеб", categoryId = foodCategory.id!!, listId = everydayLifeList.id!!, isCompleted = 0),
                purchaseService.getById(bread.id!!).blockingGet())
        assertEquals("Функция вернула не верный результат insertTest ", Purchase(id = pliers.id, name = "пассатижи", categoryId = undefinedCategory.id!!, listId = carList.id!!, isCompleted = 0),
                purchaseService.getById(pliers.id!!).blockingGet())
    }

    @Test
    fun updateTest() {
        initialized()
        var bread = Purchase(name = "хлеб", categoryId = foodCategory.id!!, listId = everydayLifeList.id!!, isCompleted = 0)
        var pliers = Purchase(name = "пассатижи", categoryId = undefinedCategory.id!!, listId = carList.id!!, isCompleted = 1)

        //---insert---
        purchaseService.insert(pliers).blockingGet()
        purchaseService.insert(bread).blockingGet()

        //---Update---
        bread = Purchase(id = bread.id!!, name = "утюг", categoryId = homeCategory.id!!, listId = everydayLifeList.id!!, isCompleted = 1)
        pliers = Purchase(id = pliers.id!!, name = "лампочка", categoryId = undefinedCategory.id!!, listId = carList.id!!, isCompleted = 0)
        purchaseService.update(pliers).blockingGet()
        purchaseService.update(bread).blockingGet()

        //---getById---
        assertEquals("Функция вернула не верный результат insertTest ", Purchase(id = bread.id, name = "утюг", categoryId = homeCategory.id!!, listId = everydayLifeList.id!!, isCompleted = 1),
                purchaseService.getById(bread.id!!).blockingGet())
        assertEquals("Функция вернула не верный результат insertTest ", Purchase(id = pliers.id, name = "лампочка", categoryId = undefinedCategory.id!!, listId = carList.id!!, isCompleted = 0),
                purchaseService.getById(pliers.id!!).blockingGet())
    }

    @Test
    fun deleteTest() {
        initialized()
        val bread = Purchase(name = "хлеб", categoryId = foodCategory.id!!, listId = everydayLifeList.id!!, isCompleted = 0)

        //---insert---
        purchaseService.insert(bread).blockingGet()

        //---delete---
        assertEquals("Функция вернула не верный результат insertTest ", null, purchaseService.delete(bread).blockingGet() )
    }

    @Test
    fun getAllByListIdTest() {
        initialized()
        val pliers = Purchase(name = "пассатижи", categoryId = undefinedCategory.id!!, listId = carList.id!!, isCompleted = 0)
        val matches = Purchase(name = "спички", categoryId = undefinedCategory.id!!, listId = everydayLifeList.id!!, isCompleted = 1)
        val bread = Purchase(name = "хлеб", categoryId = foodCategory.id!!, listId = everydayLifeList.id!!, isCompleted = 0)
        val duck = Purchase(name = "савок", categoryId = homeCategory.id!!, listId = everydayLifeList.id!!, isCompleted = 1)
        val iron = Purchase(name = "утюг", categoryId = homeCategory.id!!, listId = everydayLifeList.id!!, isCompleted = 1)
        val bulb = Purchase(name = "лампочка", categoryId = undefinedCategory.id!!, listId = carList.id!!, isCompleted = 0)

        //---insert---
        purchaseService.insert(iron).blockingGet()
        purchaseService.insert(duck).blockingGet()
        purchaseService.insert(bread).blockingGet()
        purchaseService.insert(matches).blockingGet()
        purchaseService.insert(pliers).blockingGet()
        purchaseService.insert(bulb).blockingGet()

        //---getAllByListId---
        assertEquals("Функция вернула не верный результат insertTest ", 2, purchaseService.getAllByListId(carList.id!!).blockingGet().size)
        assertEquals("Функция вернула не верный результат insertTest ", 4, purchaseService.getAllByListId(everydayLifeList.id!!).blockingGet().size)
    }

    @Test
    fun getAllByCategoryId() {
        initialized()
        val pliers = Purchase(name = "пассатижи", categoryId = undefinedCategory.id!!, listId = carList.id!!, isCompleted = 0)
        val matches = Purchase(name = "спички", categoryId = undefinedCategory.id!!, listId = everydayLifeList.id!!, isCompleted = 1)
        val bread = Purchase(name = "хлеб", categoryId = foodCategory.id!!, listId = everydayLifeList.id!!, isCompleted = 0)
        val duck = Purchase(name = "савок", categoryId = homeCategory.id!!, listId = everydayLifeList.id!!, isCompleted = 1)
        val iron = Purchase(name = "утюг", categoryId = homeCategory.id!!, listId = everydayLifeList.id!!, isCompleted = 1)
        val bulb = Purchase(name = "лампочка", categoryId = undefinedCategory.id!!, listId = carList.id!!, isCompleted = 0)

        //---insert---
        purchaseService.insert(iron).blockingGet()
        purchaseService.insert(duck).blockingGet()
        purchaseService.insert(bread).blockingGet()
        purchaseService.insert(matches).blockingGet()
        purchaseService.insert(pliers).blockingGet()
        purchaseService.insert(bulb).blockingGet()

        //---getAllByCategoryId---
        assertEquals("Функция вернула не верный результат insertTest ", 2, purchaseService.getAllByCategoryId(homeCategory.id!!).blockingGet().size)
        assertEquals("Функция вернула не верный результат insertTest ", 3, purchaseService.getAllByCategoryId(undefinedCategory.id!!).blockingGet().size)
        assertEquals("Функция вернула не верный результат insertTest ", 1, purchaseService.getAllByCategoryId(foodCategory.id!!).blockingGet().size)
    }

    @Test
    fun getAllTest() {
        initialized()
        val pliers = Purchase(name = "пассатижи", categoryId = undefinedCategory.id!! ,listId = carList.id!!, isCompleted = 0)
        val matches = Purchase(name = "спички", categoryId = undefinedCategory.id!!,listId = everydayLifeList.id!!, isCompleted = 1)
        val bread = Purchase(name = "хлеб", categoryId = foodCategory.id!!, listId = everydayLifeList.id!!, isCompleted = 0)
        val duck = Purchase(name = "савок", categoryId = homeCategory.id!!, listId = everydayLifeList.id!!, isCompleted = 1)
        val iron = Purchase(name = "утюг", categoryId = homeCategory.id!!, listId = everydayLifeList.id!!, isCompleted = 1)
        val bulb = Purchase(name = "лампочка", categoryId = undefinedCategory.id!!, listId = carList.id!!, isCompleted = 0)

        //---insert---
        purchaseService.insert(iron).blockingGet()
        purchaseService.insert(duck).blockingGet()
        purchaseService.insert(bread).blockingGet()
        purchaseService.insert(matches).blockingGet()
        purchaseService.insert(pliers).blockingGet()
        purchaseService.insert(bulb).blockingGet()

        //---getAll---
        assertEquals("Функция вернула не верный результат insertTest ", 6, purchaseService.getAll().blockingGet().size )
    }

















    @Test
    fun insertTest2() {
        initialized()
        val games = Purchase(name = "игрушка", categoryId = -100, listId = 1, isCompleted = 0)

        //---insert---
        purchaseService.insert(games).blockingGet()

        //---getById---
        assertEquals("Функция вернула не верный результат insertTest ", null, purchaseService.getById(games.id!!).blockingGet())
    }

}