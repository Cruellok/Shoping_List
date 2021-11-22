package com.persAssistant.shopping_list

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.persAssistant.shopping_list.domain.entities.Category
import com.persAssistant.shopping_list.presentation.App
import junit.framework.Assert.assertEquals

import org.junit.Test
import org.junit.runner.RunWith


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class TestCategoryInteractor : CommonTest() {
    private val appContext = InstrumentationRegistry.getInstrumentation().targetContext
    private val app = appContext.applicationContext as App
    private var getCategoryInteractor = app.appComponent.getCategoryInteractor()

    @Test
    fun categoryTest() {

        //---Insert---
        var foodCategory = Category(name = "Еда")
        var undefinedCategory = Category(name = "Неопределенно")

        getCategoryInteractor.insert(foodCategory).blockingGet()
        getCategoryInteractor.insert(undefinedCategory).blockingGet()

        //Проверка инсерта, что вернется объект по добавленному id
        assertEquals("Функция вернула не верный результат CategoryTest ", Category(id = foodCategory.id, name = "Еда"), getCategoryInteractor.getById(foodCategory.id!!).blockingGet())
        assertEquals("Функция вернула не верный результат CategoryTest ", Category(id = undefinedCategory.id, name = "Неопределенно"), getCategoryInteractor.getById(undefinedCategory.id!!).blockingGet())

        //---Update---
        foodCategory = Category(id = foodCategory.id!!, name = "Молочка")
        undefinedCategory = Category(id = undefinedCategory.id!!, name = "Неизвестно")

        getCategoryInteractor.update(foodCategory).blockingGet()
        getCategoryInteractor.update(undefinedCategory).blockingGet()

        //Проверка инсерта, что вернется объект по добавленному id
        assertEquals("Функция вернула не верный результат CategoryTest ", Category(id = foodCategory.id, name = "Молочка"), getCategoryInteractor.getById(foodCategory.id!!).blockingGet())
        assertEquals("Функция вернула не верный результат CategoryTest ", Category(id = undefinedCategory.id, name = "Неизвестно"), getCategoryInteractor.getById(undefinedCategory.id!!).blockingGet())

        //---Delete---
        getCategoryInteractor.delete(foodCategory).blockingGet()
        //Проверка инсерта, что вернется объект по добавленному id
        assertEquals("Функция вернула не верный результат CategoryTest ", null, getCategoryInteractor.getById(foodCategory.id!!).blockingGet())

        //---Get All---
        assertEquals("Функция вернула не верный результат CategoryTest ", 2, getCategoryInteractor.getAll().blockingGet().size)
    }
}