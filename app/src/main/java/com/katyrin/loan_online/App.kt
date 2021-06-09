package com.katyrin.loan_online

import android.app.Application
import com.katyrin.loan_online.di.AppComponent
import com.katyrin.loan_online.di.DaggerAppComponent

class App: Application() {
    val appComponent: AppComponent by lazy { DaggerAppComponent.builder().build() }
}