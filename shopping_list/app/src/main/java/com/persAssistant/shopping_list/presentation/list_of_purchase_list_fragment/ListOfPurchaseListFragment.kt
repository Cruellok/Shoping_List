package com.persAssistant.shopping_list.presentation.list_of_purchase_list_fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.persAssistant.shopping_list.R
import com.persAssistant.shopping_list.data.database.enitities.PurchaseList
import com.persAssistant.shopping_list.presentation.App
import com.persAssistant.shopping_list.presentation.purchase_list.CreatorPurchaseListActivity
import com.persAssistant.shopping_list.presentation.purchase_list.EditorPurchaseListActivity
import com.persAssistant.shopping_list.presentation.list_of_purchase_list_fragment.adapter.PurchaseListAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*

class ListOfPurchaseListFragment: Fragment() {

    private lateinit var recyclerViewPurchaseList: RecyclerView
    private lateinit var purchaseListAdapter: PurchaseListAdapter
    lateinit var app : App

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.recycler_purchase_list , container , false)

        app.purchaseListService.getChangeSingle().observe(requireActivity(), androidx.lifecycle.Observer {
            initAdapter()
        })

        initRecyclerView(view)

        val addPurchaseList: FloatingActionButton = view.findViewById(R.id.btn_add_purchaseList)
        addPurchaseList.setOnClickListener {
            val intent = CreatorPurchaseListActivity.getIntent(requireContext())
            startActivity(intent)
        }

        return view
    }

    private fun initRecyclerView(view: View) {
        recyclerViewPurchaseList = view.findViewById(R.id.recyclerView_purchaseList)
        recyclerViewPurchaseList.layoutManager = LinearLayoutManager(requireContext())
        recyclerViewPurchaseList.itemAnimator = DefaultItemAnimator()
        purchaseListAdapter = PurchaseListAdapter(LinkedList(), object : OnPurchaseListClickListener {

            val purchaseListService = app.purchaseListService

            override fun purchaseListItemClicked(purchaseList: PurchaseList) {
//                val intent = Intent(requireContext(), CategoryFragment::class.java)
//                startActivity(intent)
            }
            override fun deleteItem(purchaseList: PurchaseList) {
                //---Delete---
                purchaseListService.delete(purchaseList)
                        .subscribeOn(Schedulers.single())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({/*Выполнено*/
                            purchaseListAdapter.removePurchaseList(purchaseList.id)
                        }, {/*Ошибка*/ })

                Toast.makeText(requireContext(), " id = " + purchaseList.id + " name = " + purchaseList.name, Toast.LENGTH_LONG).show()
            }

            override fun editItem(purchaseList: PurchaseList) {
                val intent = EditorPurchaseListActivity.getIntent(requireContext(), purchaseList.id!!)
                startActivity(intent)            }
        })
        recyclerViewPurchaseList.adapter = purchaseListAdapter
        initAdapter()
    }

    private fun initAdapter() {
        val purchaseListService = app.purchaseListService
        purchaseListService.getAll()
                .subscribeOn(Schedulers.single())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({/*Есть данные*/
                    purchaseListAdapter.updateItems(it)
                }, {/*Ошибка*/ })
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        app = (context.applicationContext as App)
    }

}