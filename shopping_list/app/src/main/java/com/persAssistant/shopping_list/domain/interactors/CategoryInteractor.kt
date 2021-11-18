package com.persAssistant.shopping_list.domain.interactors

import androidx.lifecycle.LiveData
import com.persAssistant.shopping_list.data.database.dao.entity.RoomCategory
import com.persAssistant.shopping_list.domain.entities.Category
import com.persAssistant.shopping_list.domain.interactor_interfaces.CategoryInteractorInterface
import com.persAssistant.shopping_list.domain.interactor_repositories.CategoryRepositoryInterface
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import java.util.*

class CategoryInteractor(private val categoryRepositoryInterface: CategoryRepositoryInterface):
    CategoryInteractorInterface() {

    // сигнал об изменении в таблице
    override fun getChangeSingle(): LiveData<List<RoomCategory>> {
        return categoryRepositoryInterface.getChangeSingle()
    }

    // добавления записи в таблицу
    override fun insert(category: Category): Completable {
        return categoryRepositoryInterface.insert(category)
    }

    //запрос всех списков
    override fun getAll(): Single<LinkedList<Category>> {
        return categoryRepositoryInterface.getAll()
    }

    //запрос одного списка по айди
    override fun getById(id: Long): Maybe<Category> {
        return categoryRepositoryInterface.getById(id)
    }

    //обновление списка
    override fun update(category: Category): Completable {
        return categoryRepositoryInterface.update(category)
    }

    //удаление списка
    override fun delete(category: Category): Completable {
        return categoryRepositoryInterface.delete(category)
    }

}