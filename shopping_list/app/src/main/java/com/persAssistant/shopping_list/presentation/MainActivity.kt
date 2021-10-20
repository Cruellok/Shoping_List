package com.persAssistant.shopping_list.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.persAssistant.shopping_list.R
import kotlinx.android.synthetic.main.activity_main.*

open class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNav.menu.getItem(0).isCheckable = true
        setFragment(ListOfPurchaseListFragment())

        bottomNav.setOnItemSelectedListener {menu ->

            when(menu.itemId){
                R.id.bottomNav_category -> {
                    setFragment(CategoryFragment())

                    true
                }
                R.id.bottomNav_list -> {
                    setFragment(ListOfPurchaseListFragment())
                    true
                }
                else -> false
            }
        }

    }

    fun setFragment(fragment : Fragment){
        val fragmentManager = supportFragmentManager.beginTransaction()
        fragmentManager.replace(R.id.fragment,fragment)
        fragmentManager.commit()
    }

}