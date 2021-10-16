package com.persAssistant.shopping_list.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.persAssistant.shopping_list.R
import com.persAssistant.shopping_list.data.database.enitities.Category
import com.persAssistant.shopping_list.presentation.category.CreatorCategoryActivity
import com.persAssistant.shopping_list.presentation.category.EditorCategoryActivity
import com.persAssistant.shopping_list.presentation.recycleVIew.OnCategoryClickListener
import com.persAssistant.shopping_list.presentation.recycleVIew.adapter.CategoryAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.recycler_info_category.*
import java.util.*


class MainActivity : AppCompatActivity() {

    private lateinit var recyclerViewCategory: RecyclerView
    private lateinit var categoryAdapter: CategoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.recycler_category)

        initRecyclerView()

        (application as App).categoryService.getChangeManager().observe(this, androidx.lifecycle.Observer {
            initAdapter()
        })

        val addCategory: FloatingActionButton = findViewById(R.id.btn_add_category)
        addCategory.setOnClickListener {
            val intent = CreatorCategoryActivity.getIntent(this)
            startActivity(intent)
        }
    }

    private fun initRecyclerView() {
        recyclerViewCategory = findViewById(R.id.recyclerView_category)
        recyclerViewCategory.layoutManager = LinearLayoutManager(applicationContext)
        recyclerViewCategory.itemAnimator = DefaultItemAnimator()
        categoryAdapter = CategoryAdapter(LinkedList(), object : OnCategoryClickListener {

            val categoryService = (application as App).categoryService

            override fun categoryItemClicked(category: Category) {
                val intent = EditorCategoryActivity.getIntent(applicationContext, category.id!!)
                startActivity(intent)
            }

            override fun deleteItem(category: Category) {
                //---Delete---
                categoryService.delete(category)
                    .subscribeOn(Schedulers.single())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({/*Выполнено*/
                        categoryAdapter.removeCategory(category.id)
                    }, {/*Ошибка*/ })

                Toast.makeText(
                    this@MainActivity,
                    " id = " + category.id + " name = " + category.name,
                    Toast.LENGTH_LONG
                ).show()
            }
        })
        recyclerViewCategory.adapter = categoryAdapter
        initAdapter()
    }

    private fun initAdapter() {
        val categoryService = (application as App).categoryService
        categoryService.getAll()
            .subscribeOn(Schedulers.single())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({/*Есть данные*/
                categoryAdapter.updateItems(it)
            }, {/*Ошибка*/ })
    }
}