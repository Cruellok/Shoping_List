package com.persAssistant.shopping_list.data.database.service

import androidx.lifecycle.LiveData
import com.persAssistant.shopping_list.data.database.dao.CategoryRoomDao
import com.persAssistant.shopping_list.data.database.dao.enitity.RoomCategory
import com.persAssistant.shopping_list.data.database.enitities.Category
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import java.lang.Exception
import java.util.*

class CategoryService(private val categoryDao: CategoryRoomDao) {

    fun getChangeManager(): LiveData<List<RoomCategory>> {
        return categoryDao.getChangeManager()
    }

    // добавления записи в таблицу
    fun insert(category: Category): Completable {
        val roomCategory = RoomCategory(id = category.id,name = category.name)

        return Completable.fromAction {
            val result = categoryDao.insert(roomCategory)
            if (result != -1L) {
                category.id = result
            }
            else
                throw Exception("Failed to execute insert")
        }
    }

    //запрос всех списков
    fun getAll (): Single<LinkedList<Category>> {
        return categoryDao.getAll()
            .toObservable()
            .flatMapIterable {/*list*/it  }
            .map {
                Category(it.id, it.name)}
            .toList()
            .map {
                val linkedList = LinkedList<Category>()
                linkedList.addAll(it)
                linkedList
            }
    }

    //запрос одного списка по айди
    fun getById(id: Long): Maybe<Category> {
        return categoryDao.getById(id)
            .map { Category(id = it.id, name = it.name) }
    }

    //обновление списка
    fun update(category: Category): Completable {
        val roomCategory = RoomCategory(id = category.id,name = category.name)
        return Completable.fromAction {
            categoryDao.update(roomCategory)
        }
    }

    //удаление списка по айди
    fun delete(category: Category): Completable {
        val roomCategory = RoomCategory(id = category.id,name = category.name)
        return Completable.fromAction {
            categoryDao.delete(roomCategory)
        }
    }
}












