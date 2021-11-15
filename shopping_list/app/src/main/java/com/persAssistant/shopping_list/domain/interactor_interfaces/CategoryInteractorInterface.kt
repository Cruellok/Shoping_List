package com.persAssistant.shopping_list.domain.interactor_interfaces

import androidx.lifecycle.LiveData
import com.persAssistant.shopping_list.data.database.dao.enitity.RoomCategory
import com.persAssistant.shopping_list.domain.enitities.Category
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import java.util.*

//abstract class CategoryInteractorInterface: CategoryRepositoryInterface()
abstract class CategoryInteractorInterface{
    abstract fun getChangeSingle(): LiveData<List<RoomCategory>>
    abstract fun insert(category: Category): Completable
    abstract fun getAll(): Single<LinkedList<Category>>
    abstract fun getById(id: Long): Maybe<Category>
    abstract fun update(category: Category): Completable
    abstract fun delete(category: Category): Completable
}