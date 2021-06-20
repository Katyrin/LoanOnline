package com.katyrin.loan_online.di.modules

import android.content.Context
import androidx.room.Room
import com.katyrin.loan_online.data.storage.LoansDataBase
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

    private companion object {
        const val DB_NAME = "database.db"
    }
}