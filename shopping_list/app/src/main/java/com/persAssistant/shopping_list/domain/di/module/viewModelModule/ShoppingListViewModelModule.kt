package com.persAssistant.shopping_list.domain.di.module.viewModelModule

import com.persAssistant.shopping_list.domain.interactors.ShoppingListInteractor
import com.persAssistant.shopping_list.presentation.list_of_shopping_list_fragment.ListOfShoppingListViewModel
import com.persAssistant.shopping_list.presentation.shopping_list.CreatorShoppingListViewModel
import com.persAssistant.shopping_list.presentation.shopping_list.EditorShoppingListViewModel
import dagger.Module
import dagger.Provides

@Module
class ShoppingListViewModelModule {
    @Provides
    fun provideCreatorShoppingListViewModel(shoppingListInteractor: ShoppingListInteractor): CreatorShoppingListViewModel {
        return CreatorShoppingListViewModel(shoppingListInteractor)
    }

    @Provides
    fun provideEditorShoppingListViewModel(shoppingListInteractor: ShoppingListInteractor): EditorShoppingListViewModel {
        return EditorShoppingListViewModel(shoppingListInteractor)
    }

    @Provides
    fun provideListOfShoppingListViewModel(shoppingListInteractor: ShoppingListInteractor): ListOfShoppingListViewModel {
        return ListOfShoppingListViewModel(shoppingListInteractor)
    }
}