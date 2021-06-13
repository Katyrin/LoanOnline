package com.katyrin.loan_online.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.katyrin.loan_online.data.model.LoanEntity

@Database(entities = [LoanEntity::class], version = 1)
abstract class LoansDataBase : RoomDatabase() {
    abstract fun loansDao(): LoansDao
}