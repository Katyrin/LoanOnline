package com.katyrin.loan_online.data.storage

import androidx.room.Database
import androidx.room.RoomDatabase
import com.katyrin.loan_online.data.model.LoanEntity

@Database(entities = [LoanEntity::class], version = 1, exportSchema = false)
abstract class LoansDataBase : RoomDatabase() {
    abstract fun loansDao(): LoansDao
}