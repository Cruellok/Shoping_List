package com.persAssistant.shopping_list

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
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
class TestShoppingListInteractor : CommonTest() {
    private val appContext = InstrumentationRegistry.getInstrumentation().targetContext
    private val app = appContext.applicationContext as App
    private var getShoppingListInteractor = app.appComponent.getShoppingListInteractor()

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

        getShoppingListInteractor.insert(travelList).blockingGet()
        getShoppingListInteractor.insert(carList).blockingGet()
        getShoppingListInteractor.insert(homeList).blockingGet()
        //Проверка инсерта, что вернется объект по добавленному id
        assertEquals("Функция вернула не верный результат ShoppingList ", ShoppingList(id = travelList.id, name = "Путешествие", date = today ), getShoppingListInteractor.getById(travelList.id!!).blockingGet())
        assertEquals("Функция вернула не верный результат ShoppingList ", ShoppingList(id = carList.id, name = "Автомобиль", date = today ), getShoppingListInteractor.getById(carList.id!!).blockingGet())
        assertEquals("Функция вернула не верный результат ShoppingList ", ShoppingList(id = homeList.id, name = "Дом", date = today ), getShoppingListInteractor.getById(homeList.id!!).blockingGet())

        //---Update---
        travelList = ShoppingList(id = travelList.id, name = "Тренировка", date = tomorrow )
        carList = ShoppingList(id = carList.id, name = "Работа", date = yesterday )
        homeList = ShoppingList(id = homeList.id, name = "Дом", date = afterTomorrow )

        getShoppingListInteractor.update(travelList).blockingGet()
        getShoppingListInteractor.update(carList).blockingGet()
        getShoppingListInteractor.update(homeList).blockingGet()
        //Проверка инсерта, что вернется объект по добавленному id
        assertEquals("Функция вернула не верный результат ShoppingList ", ShoppingList(id = travelList.id  , name = "Тренировка", date = tomorrow ), getShoppingListInteractor.getById(travelList.id!!).blockingGet())
        assertEquals("Функция вернула не верный результат ShoppingList ", ShoppingList(id = carList.id , name = "Работа", date = yesterday ), getShoppingListInteractor.getById(carList.id!!).blockingGet())
        assertEquals("Функция вернула не верный результат ShoppingList ", ShoppingList(id = homeList.id , name = "Дом", date = afterTomorrow ), getShoppingListInteractor.getById(homeList.id!!).blockingGet())

        //---Delete---
        getShoppingListInteractor.delete(carList).blockingGet()
        //Проверка инсерта, что вернется объект по добавленному id
        assertEquals("Функция вернула не верный результат ShoppingList ", null, getShoppingListInteractor.getById(carList.id!!).blockingGet())

        //---Get All---
        assertEquals("Функция вернула не верный результат ShoppingList ", 2, getShoppingListInteractor.getAll().blockingGet().size)
    }
}