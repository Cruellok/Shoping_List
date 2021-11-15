package com.persAssistant.shopping_list

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.persAssistant.shopping_list.data.database.RoomDataBaseHelper
import com.persAssistant.shopping_list.data.database.repositories.CategoryRepository
import com.persAssistant.shopping_list.domain.enitities.Category
import com.persAssistant.shopping_list.data.database.service.CategoryService
import com.persAssistant.shopping_list.domain.interactors.CategoryInteractor

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class TestCategoryService : CommonTest() {
    private val appContext = InstrumentationRegistry.getInstrumentation().targetContext
    private val dataBaseHelper = RoomDataBaseHelper.getInstance(appContext)
    private val categoryDao = dataBaseHelper.getCategoryRoomDao()
    private val categoryService = CategoryService(categoryDao)
    private val categoryRepository = CategoryRepository(categoryService)
    private val categoryInteractor = CategoryInteractor(categoryRepository)

    @Test
    fun categoryTest() {

        //---Insert---
        var foodCategory = Category(name = "Еда")
        var undefinedCategory = Category(name = "Неопределенно")

        categoryInteractor.insert(foodCategory).blockingGet()
        categoryInteractor.insert(undefinedCategory).blockingGet()
        //Проверка инсерта, что вернется объект по добавленному id
        assertEquals("Функция вернула не верный результат CategoryTest ",
            Category(id = foodCategory.id, name = "Еда"), categoryInteractor.getById(foodCategory.id!!).blockingGet())
        assertEquals("Функция вернула не верный результат CategoryTest ",
            Category(id = undefinedCategory.id, name = "Неопределенно"), categoryInteractor.getById(undefinedCategory.id!!).blockingGet())

        //---Update---
        foodCategory = Category(id = foodCategory.id!!, name = "Молочка")
        undefinedCategory = Category(id = undefinedCategory.id!!, name = "Неизвестно")

        categoryInteractor.update(foodCategory).blockingGet()
        categoryInteractor.update(undefinedCategory).blockingGet()
        //Проверка инсерта, что вернется объект по добавленному id
        assertEquals("Функция вернула не верный результат CategoryTest ",
            Category(id = foodCategory.id, name = "Молочка"), categoryInteractor.getById(foodCategory.id!!).blockingGet())
        assertEquals("Функция вернула не верный результат CategoryTest ",
            Category(id = undefinedCategory.id, name = "Неизвестно"), categoryInteractor.getById(undefinedCategory.id!!).blockingGet())

        //---Delete---
        categoryInteractor.delete(foodCategory).blockingGet()
        //Проверка инсерта, что вернется объект по добавленному id
        assertEquals("Функция вернула не верный результат CategoryTest ",
            null, categoryInteractor.getById(foodCategory.id!!).blockingGet())

        //---Get All---
        assertEquals("Функция вернула не верный результат CategoryTest ",
            2, categoryInteractor.getAll().blockingGet().size)
    }
}