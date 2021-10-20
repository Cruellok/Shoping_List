package com.persAssistant.shopping_list.presentation

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.persAssistant.shopping_list.R
import com.persAssistant.shopping_list.data.database.enitities.PurchaseList
import com.persAssistant.shopping_list.presentation.category.CreatorCategoryActivity
import com.persAssistant.shopping_list.presentation.category.EditorCategoryActivity
import com.persAssistant.shopping_list.presentation.purchaseList.CreatorPurchaseListActivity
import com.persAssistant.shopping_list.presentation.purchaseList.EditorPurchaseListActivity
import com.persAssistant.shopping_list.presentation.recycleVIew.OnPurchaseListClickListener
import com.persAssistant.shopping_list.presentation.recycleVIew.adapter.PurchaseListAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*


class MainActivity : AppCompatActivity() {

    private lateinit var recyclerViewPurchaseList: RecyclerView
    private lateinit var purchaseListAdapter: PurchaseListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        initRecyclerView()

        (application as App).purchaseListService.getChangeManager().observe(this, androidx.lifecycle.Observer {
            initAdapter()
        })

        val addPurchaseList: FloatingActionButton = findViewById(R.id.btn_add_purchaseList)
        addPurchaseList.setOnClickListener {
            val intent = CreatorPurchaseListActivity.getIntent(this)
            startActivity(intent)
        }
    }

    private fun initRecyclerView() {
        recyclerViewPurchaseList = findViewById(R.id.recyclerView_purchaseList)
        recyclerViewPurchaseList.layoutManager = LinearLayoutManager(applicationContext)
        recyclerViewPurchaseList.itemAnimator = DefaultItemAnimator()
        purchaseListAdapter = PurchaseListAdapter(LinkedList(), object : OnPurchaseListClickListener {

            val purchaseListService = (application as App).purchaseListService

            override fun purchaseListItemClicked(purchaseList: PurchaseList) {
                val intent = Intent(this@MainActivity, CategoryMainActivity::class.java)
                startActivity(intent)
            }

            override fun deleteItem(purchaseList: PurchaseList) {
                //---Delete---
                purchaseListService.delete(purchaseList)
                    .subscribeOn(Schedulers.single())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({/*Выполнено*/
                        purchaseListAdapter.removePurchaseList(purchaseList.id)
                    }, {/*Ошибка*/ })

                Toast.makeText(
                    this@MainActivity,
                    " id = " + purchaseList.id + " name = " + purchaseList.name,
                    Toast.LENGTH_LONG
                ).show()
            }

            override fun editItem(purchaseList: PurchaseList) {
                val intent = EditorPurchaseListActivity.getIntent(applicationContext, purchaseList.id!!)
                startActivity(intent)            }
        })
        recyclerViewPurchaseList.adapter = purchaseListAdapter
        initAdapter()
    }

    private fun initAdapter() {
        val purchaseListService = (application as App).purchaseListService
        purchaseListService.getAll()
            .subscribeOn(Schedulers.single())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({/*Есть данные*/
                purchaseListAdapter.updateItems(it)
            }, {/*Ошибка*/ })
    }
}