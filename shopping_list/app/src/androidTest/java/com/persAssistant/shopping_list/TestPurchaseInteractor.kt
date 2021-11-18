package com.persAssistant.shopping_list

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.persAssistant.shopping_list.data.database.RoomDataBaseHelper
import com.persAssistant.shopping_list.data.database.repositories.CategoryRepository
import com.persAssistant.shopping_list.data.database.repositories.PurchaseRepository
import com.persAssistant.shopping_list.data.database.repositories.ShoppingListRepository
import com.persAssistant.shopping_list.domain.entities.Category
import com.persAssistant.shopping_list.domain.entities.Purchase
import com.persAssistant.shopping_list.domain.entities.ShoppingList
import com.persAssistant.shopping_list.data.database.service.CategoryService
import com.persAssistant.shopping_list.data.database.service.ShoppingListService
import com.persAssistant.shopping_list.data.database.service.PurchaseService
import com.persAssistant.shopping_list.domain.interactors.CategoryInteractor
import com.persAssistant.shopping_list.domain.interactors.PurchaseInteractor
import com.persAssistant.shopping_list.domain.interactors.ShoppingListInteractor
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
class TestPurchaseInteractor : CommonTest() {
    private val appContext = InstrumentationRegistry.getInstrumentation().targetContext
    private val dataBaseHelper = RoomDataBaseHelper.getInstance(appContext)

    //---Category---
    private val categoryDao = dataBaseHelper.getCategoryRoomDao()
    private val categoryService = CategoryService(categoryDao)
    private val categoryRepository = CategoryRepository(categoryService)
    private val categoryInteractor = CategoryInteractor(categoryRepository)
    private val undefinedCategory = Category(name = "Неопределенно")
    private val foodCategory = Category(name = "Еда")
    private val homeCategory = Category(name = "Дом")

    //---ShoppingList---
    private val shoppingListDao = dataBaseHelper.getShoppingListRoomDao()
    private val shoppingListService = ShoppingListService(shoppingListDao)
    private val shoppingListRepository = ShoppingListRepository(shoppingListService)
    private val shoppingListInteractor = ShoppingListInteractor(shoppingListRepository)
    private val dailyTime = 1000*60*60*24
    private val today = Date()
    private val yesterday = Date(today.time - dailyTime)
    private val everydayLifeList = ShoppingList(name = "быт", date = today )
    private val carList = ShoppingList(name = "Автомобиль", date = yesterday )

    //---Purchase---
    private val purchaseDao = dataBaseHelper.getPurchaseRoomDao()
    private val purchaseService = PurchaseService(purchaseDao)
    private val purchaseRepository = PurchaseRepository(purchaseService)
    private val purchaseInteractor = PurchaseInteractor(purchaseRepository)

    private fun initialized() {
        //---Category---
        categoryInteractor.insert(foodCategory).blockingGet()
        categoryInteractor.insert(undefinedCategory).blockingGet()
        categoryInteractor.insert(homeCategory).blockingGet()

        //---ShoppingList---
        shoppingListInteractor.insert(everydayLifeList).blockingGet()
        shoppingListInteractor.insert(carList).blockingGet()
    }

    @Test
    fun insertTest() {
        initialized()
        val bread = Purchase(name = "хлеб", categoryId = foodCategory.id!!, listId = everydayLifeList.id!!, isCompleted = 0)
        val pliers = Purchase(name = "пассатижи", categoryId = undefinedCategory.id!!, listId = carList.id!!, isCompleted = 0)

        //---insert---
        purchaseInteractor.insert(pliers).blockingGet()
        purchaseInteractor.insert(bread).blockingGet()

        //---getById---
        assertEquals("Функция вернула не верный результат insertTest ", Purchase(id = bread.id, name = "хлеб", categoryId = foodCategory.id!!, listId = everydayLifeList.id!!, isCompleted = 0),
            purchaseInteractor.getById(bread.id!!).blockingGet())
        assertEquals("Функция вернула не верный результат insertTest ", Purchase(id = pliers.id, name = "пассатижи", categoryId = undefinedCategory.id!!, listId = carList.id!!, isCompleted = 0),
            purchaseInteractor.getById(pliers.id!!).blockingGet())
    }

    @Test
    fun updateTest() {
        initialized()
        var bread = Purchase(name = "хлеб", categoryId = foodCategory.id!!, listId = everydayLifeList.id!!, isCompleted = 0)
        var pliers = Purchase(name = "пассатижи", categoryId = undefinedCategory.id!!, listId = carList.id!!, isCompleted = 1)

        //---insert---
        purchaseInteractor.insert(pliers).blockingGet()
        purchaseInteractor.insert(bread).blockingGet()

        //---Update---
        bread = Purchase(id = bread.id!!, name = "утюг", categoryId = homeCategory.id!!, listId = everydayLifeList.id!!, isCompleted = 1)
        pliers = Purchase(id = pliers.id!!, name = "лампочка", categoryId = undefinedCategory.id!!, listId = carList.id!!, isCompleted = 0)
        purchaseInteractor.update(pliers).blockingGet()
        purchaseInteractor.update(bread).blockingGet()

        //---getById---
        assertEquals("Функция вернула не верный результат insertTest ", Purchase(id = bread.id, name = "утюг", categoryId = homeCategory.id!!, listId = everydayLifeList.id!!, isCompleted = 1),
            purchaseInteractor.getById(bread.id!!).blockingGet())
        assertEquals("Функция вернула не верный результат insertTest ", Purchase(id = pliers.id, name = "лампочка", categoryId = undefinedCategory.id!!, listId = carList.id!!, isCompleted = 0),
            purchaseInteractor.getById(pliers.id!!).blockingGet())
    }

    @Test
    fun deleteTest() {
        initialized()
        val bread = Purchase(name = "хлеб", categoryId = foodCategory.id!!, listId = everydayLifeList.id!!, isCompleted = 0)

        //---insert---
        purchaseInteractor.insert(bread).blockingGet()

        //---delete---
        assertEquals("Функция вернула не верный результат insertTest ", null, purchaseInteractor.delete(bread).blockingGet() )
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
        purchaseInteractor.insert(iron).blockingGet()
        purchaseInteractor.insert(duck).blockingGet()
        purchaseInteractor.insert(bread).blockingGet()
        purchaseInteractor.insert(matches).blockingGet()
        purchaseInteractor.insert(pliers).blockingGet()
        purchaseInteractor.insert(bulb).blockingGet()

        //---getAllByListId---
        assertEquals("Функция вернула не верный результат insertTest ", 2, purchaseInteractor.getAllByListId(carList.id!!).blockingGet().size)
        assertEquals("Функция вернула не верный результат insertTest ", 4, purchaseInteractor.getAllByListId(everydayLifeList.id!!).blockingGet().size)
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
        purchaseInteractor.insert(iron).blockingGet()
        purchaseInteractor.insert(duck).blockingGet()
        purchaseInteractor.insert(bread).blockingGet()
        purchaseInteractor.insert(matches).blockingGet()
        purchaseInteractor.insert(pliers).blockingGet()
        purchaseInteractor.insert(bulb).blockingGet()

        //---getAllByCategoryId---
        assertEquals("Функция вернула не верный результат insertTest ", 2, purchaseInteractor.getAllByCategoryId(homeCategory.id!!).blockingGet().size)
        assertEquals("Функция вернула не верный результат insertTest ", 3, purchaseInteractor.getAllByCategoryId(undefinedCategory.id!!).blockingGet().size)
        assertEquals("Функция вернула не верный результат insertTest ", 1, purchaseInteractor.getAllByCategoryId(foodCategory.id!!).blockingGet().size)
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
        purchaseInteractor.insert(iron).blockingGet()
        purchaseInteractor.insert(duck).blockingGet()
        purchaseInteractor.insert(bread).blockingGet()
        purchaseInteractor.insert(matches).blockingGet()
        purchaseInteractor.insert(pliers).blockingGet()
        purchaseInteractor.insert(bulb).blockingGet()

        //---getAll---
        assertEquals("Функция вернула не верный результат insertTest ", 6, purchaseInteractor.getAll().blockingGet().size )
    }

    @Test
    fun nonexistentShoppingListAndCategoryTest() {
        initialized()
        val games = Purchase(name = "игрушка", categoryId = -100, listId = 1, isCompleted = 0)

        //---insert---
        purchaseInteractor.insert(games).blockingGet()

        //---CheckingId---
        assertEquals("Функция вернула не верный результат insertTest ", null, games.id)
    }

    @Test
    fun nonexistentShoppingListTest() {
        val car = Category( name= "Машина")
        //---insert---
        categoryInteractor.insert(car).blockingGet()

        val games = Purchase(name = "Игрушка", categoryId = car.id!!, listId = -100, isCompleted = 0)

        //---insert---
        purchaseInteractor.insert(games).blockingGet()

        //---getAll---
        Thread.sleep(500)
        assertEquals("Функция вернула не верный результат Category ", 2,  categoryInteractor.getAll().blockingGet().size)

        //---CheckingId---
        assertEquals("Функция вернула не верный результат Purchase ", null, games.id)
    }

    @Test
    fun testCheckingDefaultValueAfterDeletion () {
        val car = Category( name= "Машина")

        //---insert---
        shoppingListInteractor.insert(everydayLifeList).blockingGet()
        categoryInteractor.insert(car).blockingGet()

        val games = Purchase(name = "Игрушка", categoryId = car.id!!, listId = everydayLifeList.id!!, isCompleted = 0)

        //---insert---
        purchaseInteractor.insert(games).blockingGet()

        //---getAll---
        Thread.sleep(500)
        assertEquals("Функция вернула не верный результат Purchase ", 1L, games.id)
        assertEquals("Функция вернула не верный результат Purchase ", 1, purchaseInteractor.getAll().blockingGet().size)
        assertEquals("Функция вернула не верный результат Category ", 2, categoryInteractor.getAll().blockingGet().size)

        //---delete---
        categoryInteractor.delete(car).blockingGet()

        //---getAll---
        assertEquals("Функция вернула не верный результат Category ", 1, categoryInteractor.getAll().blockingGet().size)
        assertEquals("Функция вернула не верный результат Purchase ", 1, purchaseInteractor.getAll().blockingGet().size)

    }
}