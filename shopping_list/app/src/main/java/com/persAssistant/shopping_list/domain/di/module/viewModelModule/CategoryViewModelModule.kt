package com.persAssistant.shopping_list.domain.di.module.viewModelModule

import com.persAssistant.shopping_list.domain.interactors.CategoryInteractor
import com.persAssistant.shopping_list.presentation.category.CreatorCategoryViewModel
import com.persAssistant.shopping_list.presentation.category.EditorCategoryViewModel
import com.persAssistant.shopping_list.presentation.list_of_category_fragment.ListOfCategoryViewModel
import dagger.Module
import dagger.Provides

@Module
class CategoryViewModelModule {
    @Provides
    fun provideCreatorCategoryViewModel(categoryInteractor: CategoryInteractor): CreatorCategoryViewModel {
        return CreatorCategoryViewModel(categoryInteractor)
    }
    @Provides
    fun provideEditorCategoryViewModel(categoryInteractor: CategoryInteractor): EditorCategoryViewModel {
        return EditorCategoryViewModel(categoryInteractor)
    }

    @Provides
    fun provideListOfCategoryViewModel(categoryInteractor: CategoryInteractor): ListOfCategoryViewModel {
        return ListOfCategoryViewModel(categoryInteractor)
    }

}