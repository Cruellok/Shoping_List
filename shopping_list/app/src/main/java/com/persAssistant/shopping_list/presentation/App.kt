package com.persAssistant.shopping_list.presentation

import android.app.Application
import com.persAssistant.shopping_list.domain.di.AppComponent
import com.persAssistant.shopping_list.domain.di.DaggerAppComponent
import com.persAssistant.shopping_list.domain.di.module.RoomModule

class App: Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        initDagger()
    }

    private fun initDagger(){
        appComponent = DaggerAppComponent.builder()
            .roomModule(RoomModule(this))
            .build()
    }
}