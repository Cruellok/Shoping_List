package com.persAssistant.shopping_list

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.persAssistant.shopping_list.data.database.RoomDataBaseHelper
import com.persAssistant.shopping_list.data.database.repositories.ShoppingListRepository
import com.persAssistant.shopping_list.domain.entities.ShoppingList
import com.persAssistant.shopping_list.data.database.service.ShoppingListService
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
class TestShoppingListInteractor : CommonTest(){
    private val appContext = InstrumentationRegistry.getInstrumentation().targetContext
    private val dataBaseHelper = RoomDataBaseHelper.getInstance(appContext)
    private val shoppingListDao = dataBaseHelper.getShoppingListRoomDao()
    private val shoppingListService = ShoppingListService(shoppingListDao)
    private val shoppingListRepository = ShoppingListRepository(shoppingListService)
    private val shoppingListInteractor = ShoppingListInteractor(shoppingListRepository)

    @Test
    fun shoppingListTest() {

        //---Insert---
        val dailyTime = 1000*60*60*24
        val today = Date()
        val yesterday = Date(today.time - dailyTime)
        val tomorrow = Date(today.time + dailyTime)
        val afterTomorrow = Date(tomorrow.time + dailyTime)

        var travelList = ShoppingList(name = "Путешествие", date = today )
        var carList = ShoppingList(name = "Автомобиль", date = today )
        var homeList = ShoppingList(name = "Дом", date = today )

        shoppingListInteractor.insert(travelList).blockingGet()
        shoppingListInteractor.insert(carList).blockingGet()
        shoppingListInteractor.insert(homeList).blockingGet()
        //Проверка инсерта, что вернется объект по добавленному id
        assertEquals("Функция вернула не верный результат ShoppingList ", ShoppingList(id = travelList.id, name = "Путешествие", date = today ), shoppingListInteractor.getById(travelList.id!!).blockingGet())
        assertEquals("Функция вернула не верный результат ShoppingList ", ShoppingList(id = carList.id, name = "Автомобиль", date = today ), shoppingListInteractor.getById(carList.id!!).blockingGet())
        assertEquals("Функция вернула не верный результат ShoppingList ", ShoppingList(id = homeList.id, name = "Дом", date = today ), shoppingListInteractor.getById(homeList.id!!).blockingGet())

        //---Update---
        travelList = ShoppingList(id = travelList.id, name = "Тренировка", date = tomorrow )
        carList = ShoppingList(id = carList.id, name = "Работа", date = yesterday )
        homeList = ShoppingList(id = homeList.id, name = "Дом", date = afterTomorrow )

        shoppingListInteractor.update(travelList).blockingGet()
        shoppingListInteractor.update(carList).blockingGet()
        shoppingListInteractor.update(homeList).blockingGet()
        //Проверка инсерта, что вернется объект по добавленному id
        assertEquals("Функция вернула не верный результат ShoppingList ", ShoppingList(id = travelList.id  , name = "Тренировка", date = tomorrow ), shoppingListInteractor.getById(travelList.id!!).blockingGet())
        assertEquals("Функция вернула не верный результат ShoppingList ", ShoppingList(id = carList.id , name = "Работа", date = yesterday ), shoppingListInteractor.getById(carList.id!!).blockingGet())
        assertEquals("Функция вернула не верный результат ShoppingList ", ShoppingList(id = homeList.id , name = "Дом", date = afterTomorrow ), shoppingListInteractor.getById(homeList.id!!).blockingGet())

        //---Delete---
        shoppingListInteractor.delete(carList).blockingGet()
        //Проверка инсерта, что вернется объект по добавленному id
        assertEquals("Функция вернула не верный результат ShoppingList ", null, shoppingListInteractor.getById(carList.id!!).blockingGet())

        //---Get All---
        assertEquals("Функция вернула не верный результат ShoppingList ", 2, shoppingListInteractor.getAll().blockingGet().size)
    }
}