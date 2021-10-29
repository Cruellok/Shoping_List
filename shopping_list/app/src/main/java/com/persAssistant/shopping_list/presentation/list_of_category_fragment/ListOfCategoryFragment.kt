package com.persAssistant.shopping_list.presentation.list_of_category_fragment

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
import com.persAssistant.shopping_list.data.database.enitities.Category
import com.persAssistant.shopping_list.presentation.App
import com.persAssistant.shopping_list.presentation.category.CreatorCategoryActivity
import com.persAssistant.shopping_list.presentation.category.EditorCategoryActivity
import com.persAssistant.shopping_list.presentation.list_of_category_fragment.adapter.CategoryAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*

class ListOfCategoryFragment : Fragment() {

    private lateinit var recyclerViewCategory: RecyclerView
    private lateinit var categoryAdapter: CategoryAdapter
    lateinit var app : App

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
         val view = inflater.inflate(R.layout.recycler_category, container, false)

        initRecyclerView(view)
        app.categoryService.getChangeSingle().observe(requireActivity(), androidx.lifecycle.Observer {
            initAdapter()
        })
        val addCategory: FloatingActionButton = view.findViewById(R.id.btn_add_category)
        addCategory.setOnClickListener {
            val intent = CreatorCategoryActivity.getIntent(requireContext())
            startActivity(intent)}

        return view
    }

    private fun initRecyclerView(view: View) {
        recyclerViewCategory = view.findViewById(R.id.recyclerView_category)
        recyclerViewCategory.layoutManager = LinearLayoutManager(requireContext().applicationContext)
        recyclerViewCategory.itemAnimator = DefaultItemAnimator()
        categoryAdapter = CategoryAdapter(LinkedList(), object : OnCategoryClickListener {

            val categoryService = app.categoryService

            override fun clickedCategoryItem(category: Category) {
//                val intent = EditorCategoryActivity.getIntent(requireContext().applicationContext, category.id!!)
//                startActivity(intent)
            }

            override fun deleteItem(category: Category) {
                //---Delete---
                categoryService.delete(category)
                        .subscribeOn(Schedulers.single())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({/*Выполнено*/
                            categoryAdapter.removeCategory(category.id)
                        }, {/*Ошибка*/ })

                Toast.makeText(requireContext(), " id = " + category.id + " name = " + category.name, Toast.LENGTH_LONG).show()
            }

            override fun editItem(category: Category) {
                val intent = EditorCategoryActivity.getIntent(requireContext().applicationContext, category.id!!)
                startActivity(intent)
            }
        })
        recyclerViewCategory.adapter = categoryAdapter
        initAdapter()
    }

    private fun initAdapter() {
        val categoryService = app.categoryService
        categoryService.getAll()
                .subscribeOn(Schedulers.single())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({/*Есть данные*/
                    categoryAdapter.updateItems(it)
                }, {/*Ошибка*/ })
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        app = (context.applicationContext as App)
    }

}