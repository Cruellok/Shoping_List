package com.persAssistant.shopping_list.domain.di.module.viewModelModule

import com.persAssistant.shopping_list.domain.interactors.FullPurchaseInteractor
import com.persAssistant.shopping_list.domain.interactors.PurchaseInteractor
import com.persAssistant.shopping_list.presentation.list_of_purchase_activity.ListOfPurchaseViewModel
import com.persAssistant.shopping_list.presentation.purchase.CreatorPurchaseViewModel
import com.persAssistant.shopping_list.presentation.purchase.EditorPurchaseViewModel
import dagger.Module
import dagger.Provides

@Module
class PurchaseViewModelModule {
    @Provides
    fun provideCreatorPurchaseViewModel(purchaseInteractor: PurchaseInteractor,
                                        fullPurchaseInteractor: FullPurchaseInteractor): CreatorPurchaseViewModel {
        return CreatorPurchaseViewModel(purchaseInteractor, fullPurchaseInteractor)
    }

    @Provides
    fun provideEditorPurchaseViewModel(purchaseInteractor: PurchaseInteractor,
                                       fullPurchaseInteractor: FullPurchaseInteractor): EditorPurchaseViewModel {
        return EditorPurchaseViewModel(purchaseInteractor, fullPurchaseInteractor)
    }

    @Provides
    fun provideListOfPurchaseViewModel(purchaseInteractor: PurchaseInteractor,
                                       fullPurchaseInteractor: FullPurchaseInteractor): ListOfPurchaseViewModel {
        return ListOfPurchaseViewModel(purchaseInteractor, fullPurchaseInteractor)
    }
}