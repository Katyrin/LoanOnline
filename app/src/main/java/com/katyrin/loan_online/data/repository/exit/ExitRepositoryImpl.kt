package com.katyrin.loan_online.data.repository.exit

import com.katyrin.loan_online.data.datasource.exit.ExitDataSource
import com.katyrin.loan_online.data.datasource.session.SessionManager
import io.reactivex.Completable
import javax.inject.Inject

class ExitRepositoryImpl @Inject constructor(
    private val exitDataSource: ExitDataSource,
    private val sessionManager: SessionManager
) : ExitRepository {
    override fun clearAllData(): Completable {
        sessionManager.saveAuthToken(null)
        sessionManager.saveIsRegistered(false)
        return exitDataSource.deleteLoansTable()
    }
}