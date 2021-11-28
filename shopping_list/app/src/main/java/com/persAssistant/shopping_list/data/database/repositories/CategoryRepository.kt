package com.persAssistant.shopping_list.data.database.repositories

import androidx.lifecycle.LiveData
import com.persAssistant.shopping_list.data.database.dao.entity.RoomCategory
import com.persAssistant.shopping_list.domain.interactor_repositories.CategoryRepositoryInterface
import com.persAssistant.shopping_list.data.database.service.CategoryService
import com.persAssistant.shopping_list.domain.entities.Category
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import java.util.*
import javax.inject.Inject

class CategoryRepository @Inject constructor(private val categoryService: CategoryService):
    CategoryRepositoryInterface() {

    // сигнал об изменении в таблице
    override fun getChangeSignal(): LiveData<List<RoomCategory>> {
        return categoryService.getChangeSignal()
    }

    // добавления записи в таблицу
    override fun insert(category: Category): Completable {
        return categoryService.insert(category)
    }

    //запрос всех списков
    override fun getAll(): Single<LinkedList<Category>> {
        return categoryService.getAll()
    }

    //запрос одного списка по айди
    override fun getById(id: Long): Maybe<Category> {
        return categoryService.getById(id)
    }

    //обновление списка
    override fun update(category: Category): Completable {
        return categoryService.update(category)
    }

    //удаление списка
    override fun delete(category: Category): Completable {
        return categoryService.delete(category)
    }

}