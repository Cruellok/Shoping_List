package com.persAssistant.shopping_list.presentation.list_of_purchase_activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.persAssistant.shopping_list.R
import com.persAssistant.shopping_list.data.database.enitities.Purchase
import com.persAssistant.shopping_list.presentation.App
import com.persAssistant.shopping_list.presentation.list_of_purchase_activity.adapter.PurchaseAdapter
import com.persAssistant.shopping_list.presentation.purchase.CreatorPurchaseActivity
import com.persAssistant.shopping_list.presentation.purchase.EditorPurchaseActivity
import com.persAssistant.shopping_list.presentation.purchase.PurchaseActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*


class ListOfPurchaseActivity: AppCompatActivity() {

    private lateinit var recyclerViewPurchase: RecyclerView
    private lateinit var purchaseAdapter: PurchaseAdapter
    lateinit var app : App

    companion object{
        const val CLICKED_FROM_CATEGORY_FRAGMENT = "CLICKED_FROM_CATEGORY_FRAGMENT"
        const val YES_CLICKED = "YES_CLICKED"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recycler_purchase)

        app = (this.applicationContext as App)
        app.purchaseService.getChangeSingle().observe(this, androidx.lifecycle.Observer {
            initAdapter()
        })

        recyclerViewPurchase = findViewById(R.id.recyclerView_purchase)
        initRecyclerView()

        val addPurchase: FloatingActionButton = findViewById(R.id.btn_add_purchase)
        val clickedFromCategoryFragment: String? = intent.getStringExtra(CLICKED_FROM_CATEGORY_FRAGMENT)
        if(clickedFromCategoryFragment != null)
            addPurchase.visibility = View.GONE

        addPurchase.setOnClickListener {
            val intent = CreatorPurchaseActivity.getIntent(this, intent.getLongExtra(PurchaseActivity.KEY_PURCHASELIST_ID, -1))
            startActivity(intent)
        }
    }

    private fun initRecyclerView() {
        recyclerViewPurchase.layoutManager = LinearLayoutManager(this)
        recyclerViewPurchase.itemAnimator = DefaultItemAnimator()
        purchaseAdapter = PurchaseAdapter(LinkedList(), object : OnPurchaseClickListener {
            val purchaseService = app.purchaseService

            override fun clickedPurchaseItem(purchase: Purchase) {
            }

            override fun deleteItem(purchase: Purchase) {
                //---Delete---
                purchaseService.delete(purchase)
                    .subscribeOn(Schedulers.single())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({/*Выполнено*/
                        purchaseAdapter.removePurchase(purchase.id)
                    }, {/*Ошибка*/ })

                Toast.makeText(applicationContext, " id = " + purchase.id + " name = " + purchase.name, Toast.LENGTH_LONG).show()
            }

            override fun editItem(purchase: Purchase) {
                val intent = EditorPurchaseActivity.getIntent(applicationContext, purchase.id!!)
                startActivity(intent)
            }
        })
        recyclerViewPurchase.adapter = purchaseAdapter
        initAdapter()
    }

    private fun initAdapter() {
        val categoryId = intent.getLongExtra(PurchaseActivity.KEY_CATEGORY_ID, -1)
        val purchaseListId = intent.getLongExtra(PurchaseActivity.KEY_PURCHASELIST_ID, -1)

        if(purchaseListId == -1L && categoryId != -1L) {
            app.purchaseService.getAllByCategoryId(categoryId)
                .subscribeOn(Schedulers.single())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({/*Есть данные*/
                    purchaseAdapter.updateItems(it)
                }, {/*Ошибка*/ })

        }else if(purchaseListId != -1L && categoryId == -1L) {
            app.purchaseService.getAllByListId(purchaseListId)
                .subscribeOn(Schedulers.single())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({/*Есть данные*/
                    purchaseAdapter.updateItems(it)
                }, {/*Ошибка*/ })
        }else
            throw Exception("Ошибка в ListOfPurchaseActivity отсутствует listId или categoryId")
    }
}