package com.katyrin.loan_online.di

import android.content.Context
import androidx.room.Room
import com.katyrin.loan_online.data.room.LoansDataBase
import com.katyrin.loan_online.utils.DB_NAME
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val context: Context) {

    @Provides
    @Singleton
    fun context(): Context = context

    @Singleton
    @Provides
    fun database(context: Context): LoansDataBase =
        Room.databaseBuilder(context, LoansDataBase::class.java, DB_NAME).build()
}