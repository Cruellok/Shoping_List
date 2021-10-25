package com.persAssistant.shopping_list.presentation.list_of_purchase_fragment

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.persAssistant.shopping_list.R
import com.persAssistant.shopping_list.data.database.enitities.Purchase
import com.persAssistant.shopping_list.presentation.App
import com.persAssistant.shopping_list.presentation.list_of_purchase_fragment.adapter.PurchaseAdapter
import com.persAssistant.shopping_list.presentation.purchase.CreatorPurchaseActivity
import com.persAssistant.shopping_list.presentation.purchase.EditorPurchaseActivity
import com.persAssistant.shopping_list.presentation.purchase.PurchaseActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.lang.Exception
import java.util.*

class ListOfPurchaseActivity: AppCompatActivity() {

    private lateinit var recyclerViewPurchase: RecyclerView
    private lateinit var purchaseAdapter: PurchaseAdapter
    lateinit var app : App

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
        addPurchase.setOnClickListener {

            //делаю проверку , передался ли ID ... По сути этот год и не нужен
            val listId = intent.getLongExtra(PurchaseActivity.KEY_PURCHASELIST_ID,-1)
            if(listId == -1L)
                throw Exception("Ошибка в ListOfPurchaseActivity отсутствует listId")

            val intent = CreatorPurchaseActivity.getIntent(this)
            intent.putExtra(PurchaseActivity.KEY_PURCHASELIST_ID,listId)
            startActivity(intent)
        }
    }

    private fun initRecyclerView() {
        recyclerViewPurchase.layoutManager = LinearLayoutManager(this)
        recyclerViewPurchase.itemAnimator = DefaultItemAnimator()
        purchaseAdapter = PurchaseAdapter(LinkedList(), object: OnPurchaseClickListener {

            val purchaseService = app.purchaseService

            override fun purchaseItemClicked(purchase: Purchase) {
//                val intent = Intent(ListOfPurchaseListFragment, PurchaseActivity::class.java)
//                intent.putExtra(PurchaseActivity.KEY_PURCHASELIST_ID,purchase.id)
//                startActivity(intent)
//                ListOfPurchaseFragment

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
        val purchaseService = app.purchaseService
        purchaseService.getAll()
            .subscribeOn(Schedulers.single())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({/*Есть данные*/
                purchaseAdapter.updateItems(it)
            }, {/*Ошибка*/ })
    }
}