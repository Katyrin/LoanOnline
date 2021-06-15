package com.katyrin.loan_online.data.storage

import androidx.room.*
import com.katyrin.loan_online.data.model.LoanEntity
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface LoansDao {

    @Query("SELECT * FROM loans")
    fun getLoans(): Single<List<LoanEntity>>

    @Query("SELECT * FROM loans WHERE id LIKE :id LIMIT 1")
    fun getLoanById(id: Int): Single<LoanEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun putLoans(loans: List<LoanEntity>): Completable

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun putLoanById(loan: LoanEntity): Completable

    @Query("DELETE FROM loans")
    fun deleteLoansTable(): Completable
}