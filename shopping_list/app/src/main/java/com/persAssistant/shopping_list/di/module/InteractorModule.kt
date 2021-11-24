package com.persAssistant.shopping_list.di.module

import com.persAssistant.shopping_list.data.database.repositories.CategoryRepository
import com.persAssistant.shopping_list.data.database.repositories.PurchaseRepository
import com.persAssistant.shopping_list.data.database.repositories.ShoppingListRepository
import com.persAssistant.shopping_list.domain.interactors.CategoryInteractor
import com.persAssistant.shopping_list.domain.interactors.FullPurchaseInteractor
import com.persAssistant.shopping_list.domain.interactors.PurchaseInteractor
import com.persAssistant.shopping_list.domain.interactors.ShoppingListInteractor
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class InteractorModule {
    @Provides
    @Singleton
    fun provideCategoryInteractor(categoryRepository: CategoryRepository): CategoryInteractor {
        return CategoryInteractor(categoryRepository)
    }

    @Provides
    @Singleton
    fun providePurchaseInteractor(purchaseRepository: PurchaseRepository): PurchaseInteractor {
        return PurchaseInteractor(purchaseRepository)
    }

    @Provides
    @Singleton
    fun provideShoppingListInteractor(shoppingListRepository: ShoppingListRepository): ShoppingListInteractor {
        return ShoppingListInteractor(shoppingListRepository)
    }

    @Provides
    @Singleton
    fun provideFullPurchaseInteractor(purchaseInteractor: PurchaseInteractor, categoryInteractor: CategoryInteractor): FullPurchaseInteractor {
        return FullPurchaseInteractor(purchaseInteractor, categoryInteractor)
    }
}
