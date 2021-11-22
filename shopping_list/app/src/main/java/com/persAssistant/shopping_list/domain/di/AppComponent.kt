package com.persAssistant.shopping_list.domain.di

import com.persAssistant.shopping_list.data.database.RoomDataBaseHelper
import com.persAssistant.shopping_list.domain.interactors.CategoryInteractor
import com.persAssistant.shopping_list.domain.interactors.FullPurchaseInteractor
import com.persAssistant.shopping_list.domain.interactors.PurchaseInteractor
import com.persAssistant.shopping_list.domain.interactors.ShoppingListInteractor
import com.persAssistant.shopping_list.domain.di.module.*
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [RoomModule::class, DaoModule::class, ServiceModule::class,
    RepositoryModule::class, InteractorModule::class])
interface AppComponent {
    
    fun getCategoryInteractor(): CategoryInteractor
    fun getPurchaseInteractor(): PurchaseInteractor
    fun getShoppingListInteractor(): ShoppingListInteractor
    fun getFullPurchaseInteractor(): FullPurchaseInteractor
    fun getRoomDataBase(): RoomDataBaseHelper

}