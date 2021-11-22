package com.persAssistant.shopping_list

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.persAssistant.shopping_list.data.database.RoomDataBaseHelper
import com.persAssistant.shopping_list.domain.entities.Category
import com.persAssistant.shopping_list.domain.entities.Purchase
import com.persAssistant.shopping_list.domain.entities.ShoppingList
import com.persAssistant.shopping_list.presentation.App
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
class TestPurchaseInteractor: CommonTest() {
    private val appContext = InstrumentationRegistry.getInstrumentation().targetContext
    private val app = appContext.applicationContext as App
    private var getShoppingListInteractor = app.appComponent.getShoppingListInteractor()
    private var getPurchaseInteractor = app.appComponent.getPurchaseInteractor()
    private var getCategoryInteractor = app.appComponent.getCategoryInteractor()

    //---Category---
    private lateinit var  undefinedCategory : Category
    private lateinit var  foodCategory : Category
    private lateinit var  homeCategory : Category

    //---ShoppingList---
    private val dailyTime = 1000*60*60*24
    private val today = Date()
    private val yesterday = Date(today.time - dailyTime)
    private lateinit var  everydayLifeList : ShoppingList
    private lateinit var  carList : ShoppingList

    private fun initialized() {
        undefinedCategory = Category(name = "Неопределенно")
        foodCategory = Category(name = "Еда")
        homeCategory = Category(name = "Дом")

        //---ShoppingList---
        everydayLifeList = ShoppingList(name = "быт", date = today )
        carList = ShoppingList(name = "Автомобиль", date = yesterday )

        //---Category---
        getCategoryInteractor.insert(foodCategory).blockingGet()
        getCategoryInteractor.insert(undefinedCategory).blockingGet()
        getCategoryInteractor.insert(homeCategory).blockingGet()

        //---ShoppingList---
        getShoppingListInteractor.insert(everydayLifeList).blockingGet()
        getShoppingListInteractor.insert(carList).blockingGet()
    }

    @Test
    fun insertTest() {
        initialized()
        val bread = Purchase(name = "хлеб", categoryId = foodCategory.id!!, listId = everydayLifeList.id!!, isCompleted = 0)
        val pliers = Purchase(name = "пассатижи", categoryId = undefinedCategory.id!!, listId = carList.id!!, isCompleted = 0)

        //---insert---
        getPurchaseInteractor.insert(pliers).blockingGet()
        getPurchaseInteractor.insert(bread).blockingGet()

        //---getById---
        assertEquals("Функция вернула не верный результат insertTest ", Purchase(id = bread.id, name = "хлеб", categoryId = foodCategory.id!!, listId = everydayLifeList.id!!, isCompleted = 0), getPurchaseInteractor.getById(bread.id!!).blockingGet())
        assertEquals("Функция вернула не верный результат insertTest ", Purchase(id = pliers.id, name = "пассатижи", categoryId = undefinedCategory.id!!, listId = carList.id!!, isCompleted = 0), getPurchaseInteractor.getById(pliers.id!!).blockingGet())

        // ---delete---
        getPurchaseInteractor.delete(pliers).blockingGet()
        getPurchaseInteractor.delete(bread).blockingGet()

        //---getAll--
        assertEquals("Функция вернула не верный результат insertTest ", 0, getPurchaseInteractor.getAll().blockingGet().size)

    }

    @Test
    fun updateTest() {
        initialized()
        var bread = Purchase(name = "хлеб", categoryId = foodCategory.id!!, listId = everydayLifeList.id!!, isCompleted = 0)
        var pliers = Purchase(name = "пассатижи", categoryId = undefinedCategory.id!!, listId = carList.id!!, isCompleted = 1)

        //---insert---
        getPurchaseInteractor.insert(pliers).blockingGet()
        getPurchaseInteractor.insert(bread).blockingGet()

        //---Update---
        bread = Purchase(id = bread.id!!, name = "утюг", categoryId = homeCategory.id!!, listId = everydayLifeList.id!!, isCompleted = 1)
        pliers = Purchase(id = pliers.id!!, name = "лампочка", categoryId = undefinedCategory.id!!, listId = carList.id!!, isCompleted = 0)
        getPurchaseInteractor.update(pliers).blockingGet()
        getPurchaseInteractor.update(bread).blockingGet()

        //---getById---
        assertEquals("Функция вернула не верный результат insertTest ", Purchase(id = bread.id, name = "утюг", categoryId = homeCategory.id!!, listId = everydayLifeList.id!!, isCompleted = 1),
            getPurchaseInteractor.getById(bread.id!!).blockingGet())
        assertEquals("Функция вернула не верный результат insertTest ", Purchase(id = pliers.id, name = "лампочка", categoryId = undefinedCategory.id!!, listId = carList.id!!, isCompleted = 0),
            getPurchaseInteractor.getById(pliers.id!!).blockingGet())

        // ---delete---
        getPurchaseInteractor.delete(pliers).blockingGet()
        getPurchaseInteractor.delete(bread).blockingGet()

        //---getAll--
        assertEquals("Функция вернула не верный результат insertTest ", 0, getPurchaseInteractor.getAll().blockingGet().size)

    }

    @Test
    fun deleteTest() {
        initialized()
        val bread = Purchase(name = "хлеб", categoryId = foodCategory.id!!, listId = everydayLifeList.id!!, isCompleted = 0)

        //---insert---
        getPurchaseInteractor.insert(bread).blockingGet()

        //---delete---
        getPurchaseInteractor.delete(bread).blockingGet()

        //---getAll---
        assertEquals("Функция вернула не верный результат insertTest ", 0, getPurchaseInteractor
            .getAll().blockingGet().size )
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
        getPurchaseInteractor.insert(iron).blockingGet()
        getPurchaseInteractor.insert(duck).blockingGet()
        getPurchaseInteractor.insert(bread).blockingGet()
        getPurchaseInteractor.insert(matches).blockingGet()
        getPurchaseInteractor.insert(pliers).blockingGet()
        getPurchaseInteractor.insert(bulb).blockingGet()

        //---getAllByListId---
        assertEquals("Функция вернула не верный результат insertTest ", 2, getPurchaseInteractor.getAllByListId(carList.id!!).blockingGet().size)
        assertEquals("Функция вернула не верный результат insertTest ", 4, getPurchaseInteractor.getAllByListId(everydayLifeList.id!!).blockingGet().size)

        // ---delete---
        getPurchaseInteractor.delete(iron).blockingGet()
        getPurchaseInteractor.delete(duck).blockingGet()
        getPurchaseInteractor.delete(pliers).blockingGet()
        getPurchaseInteractor.delete(bread).blockingGet()
        getPurchaseInteractor.delete(matches).blockingGet()
        getPurchaseInteractor.delete(bulb).blockingGet()

        //---getAll--
        assertEquals("Функция вернула не верный результат insertTest ", 0, getPurchaseInteractor.getAll().blockingGet().size)
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
        getPurchaseInteractor.insert(iron).blockingGet()
        getPurchaseInteractor.insert(duck).blockingGet()
        getPurchaseInteractor.insert(bread).blockingGet()
        getPurchaseInteractor.insert(matches).blockingGet()
        getPurchaseInteractor.insert(pliers).blockingGet()
        getPurchaseInteractor.insert(bulb).blockingGet()

        //---getAllByCategoryId---
        assertEquals("Функция вернула не верный результат insertTest ", 2, getPurchaseInteractor.getAllByCategoryId(homeCategory.id!!).blockingGet().size)
        assertEquals("Функция вернула не верный результат insertTest ", 3, getPurchaseInteractor.getAllByCategoryId(undefinedCategory.id!!).blockingGet().size)
        assertEquals("Функция вернула не верный результат insertTest ", 1, getPurchaseInteractor.getAllByCategoryId(foodCategory.id!!).blockingGet().size)

        // ---delete---
        getPurchaseInteractor.delete(iron).blockingGet()
        getPurchaseInteractor.delete(duck).blockingGet()
        getPurchaseInteractor.delete(pliers).blockingGet()
        getPurchaseInteractor.delete(bread).blockingGet()
        getPurchaseInteractor.delete(matches).blockingGet()
        getPurchaseInteractor.delete(bulb).blockingGet()

        //---getAll--
        assertEquals("Функция вернула не верный результат insertTest ", 0, getPurchaseInteractor.getAll().blockingGet().size)
    }

    @Test
    fun getAllTest() { //20
        initialized()
        val pliers = Purchase(name = "пассатижи", categoryId = undefinedCategory.id!! ,listId = carList.id!!, isCompleted = 0)
        val matches = Purchase(name = "спички", categoryId = undefinedCategory.id!!,listId = everydayLifeList.id!!, isCompleted = 1)
        val bread = Purchase(name = "хлеб", categoryId = foodCategory.id!!, listId = everydayLifeList.id!!, isCompleted = 0)
        val duck = Purchase(name = "савок", categoryId = homeCategory.id!!, listId = everydayLifeList.id!!, isCompleted = 1)
        val iron = Purchase(name = "утюг", categoryId = homeCategory.id!!, listId = everydayLifeList.id!!, isCompleted = 1)
        val bulb = Purchase(name = "лампочка", categoryId = undefinedCategory.id!!, listId = carList.id!!, isCompleted = 0)

//        Thread.sleep(10000)
        //---insert---
        getPurchaseInteractor.insert(iron).blockingGet()
        getPurchaseInteractor.insert(duck).blockingGet()
        getPurchaseInteractor.insert(bread).blockingGet()
        getPurchaseInteractor.insert(matches).blockingGet()
        getPurchaseInteractor.insert(pliers).blockingGet()
        getPurchaseInteractor.insert(bulb).blockingGet()

        //---getAll---
        assertEquals("Функция вернула не верный результат Purchase.getAll() ", 6, getPurchaseInteractor.getAll().blockingGet().size)

        // ---delete---
        getPurchaseInteractor.delete(iron).blockingGet()
        getPurchaseInteractor.delete(duck).blockingGet()
        getPurchaseInteractor.delete(pliers).blockingGet()
        getPurchaseInteractor.delete(bread).blockingGet()
        getPurchaseInteractor.delete(matches).blockingGet()
        getPurchaseInteractor.delete(bulb).blockingGet()

        //---getAll--
        assertEquals("Функция вернула не верный результат insertTest ", 0, getPurchaseInteractor.getAll().blockingGet().size)
    }

    @Test
    fun nonexistentShoppingListAndCategoryTest() {
        initialized()
        val games = Purchase(name = "игрушка", categoryId = -100, listId = 1, isCompleted = 0)

        //---insert---
        getPurchaseInteractor.insert(games).blockingGet()

        //---CheckingId---
        assertEquals("Функция вернула не верный результат insertTest ", null, games.id)

        //---delete---
        getPurchaseInteractor.delete(games).blockingGet()

        //---getAll--
        assertEquals("Функция вернула не верный результат insertTest ", 0, getPurchaseInteractor.getAll().blockingGet().size)
    }

    @Test
    fun nonexistentShoppingListTest() {
        val car = Category(name = "Машина")
        //---insert---
        getCategoryInteractor.insert(car).blockingGet()

        val games = Purchase(name = "Игрушка", categoryId = car.id!!, listId = -100, isCompleted = 0)

        //---insert---
        getPurchaseInteractor.insert(games).blockingGet()

        //---getAll---
//        Thread.sleep(10000)
        assertEquals("Функция вернула не верный результат Category.getAll() ", 5, getCategoryInteractor.getAll().blockingGet().size)

        //---CheckingId---
        assertEquals("Функция вернула не верный результат Purchase.Id ", null, games.id)

        //---delete---
        getPurchaseInteractor.delete(games).blockingGet()

        //---getAll--
        assertEquals("Функция вернула не верный результат insertTest ", 0, getPurchaseInteractor.getAll().blockingGet().size)
    }

    @Test
    fun testCheckingDefaultValueAfterDeletion () {
        initialized()
        val car = Category( name= "Машина")
        //---insert---
        getShoppingListInteractor.insert(everydayLifeList).blockingGet()
        getCategoryInteractor.insert(car).blockingGet()

        val games = Purchase(name = "Игрушка", categoryId = car.id!!, listId = everydayLifeList.id!!, isCompleted = 0)

        //---insert---
        getPurchaseInteractor.insert(games).blockingGet()

        //---getAll---
        assertEquals("Функция вернула не верный результат Purchase ", 1, getPurchaseInteractor.getAll().blockingGet().size)

        //---delete---
        getCategoryInteractor.delete(car).blockingGet()
        getPurchaseInteractor.delete(games).blockingGet()

        //---getAll---
        assertEquals("Функция вернула не верный результат Purchase.getAll()", 0, getPurchaseInteractor.getAll().blockingGet().size)
        assertEquals("Функция вернула не верный результат insertTest ", 0, getPurchaseInteractor.getAll().blockingGet().size)
    }

}