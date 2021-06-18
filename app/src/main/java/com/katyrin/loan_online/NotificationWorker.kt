package com.katyrin.loan_online

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.katyrin.loan_online.utils.DEFAULT_COUNT_LOANS
import com.katyrin.loan_online.utils.NOTIFY_DATE
import com.katyrin.loan_online.utils.NOTIFY_LOANS
import javax.inject.Inject

class NotificationWorker @Inject constructor(
    private val context: Context,
    workerParameters: WorkerParameters
) : Worker(context, workerParameters) {

    override fun doWork(): Result =
        try {
            val loans = inputData.getInt(NOTIFY_LOANS, DEFAULT_COUNT_LOANS)
            showNotification(
                context.getString(R.string.payment_notification),
                getNotificationMessage(loans)
            )
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }

    private fun getNotificationMessage(loans: Int): String =
        if (loans > 1) {
            context.getString(R.string.several_loans_info) + " $loans"
        } else {
            context.getString(R.string.notification_info) + " ${inputData.getString(NOTIFY_DATE)}"
        }

    private fun showNotification(title: String, message: String) {
        val notificationBuilder = createNotificationBuilder(title, message)
        val notificationManager: NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(notificationManager, title, message)
        }
        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build())
    }

    private fun createNotificationBuilder(title: String, message: String) =
        NotificationCompat.Builder(context, CHANNEL_ID).apply {
            setSmallIcon(R.drawable.ic_coins_planet)
            setContentTitle(title)
            setContentText(message)
            setStyle(NotificationCompat.BigTextStyle().bigText(message))
            priority = NotificationCompat.PRIORITY_HIGH
        }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(
        notificationManager: NotificationManager,
        title: String,
        message: String
    ) {
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(CHANNEL_ID, title, importance).apply {
            description = message
        }
        notificationManager.createNotificationChannel(channel)
    }

    companion object {
        private const val CHANNEL_ID = "Notification channel"
        private const val NOTIFICATION_ID = 24
    }
}