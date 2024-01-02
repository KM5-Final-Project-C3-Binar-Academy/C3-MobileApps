package com.c3.mobileapps.utils

// NotificationHelper.kt

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.c3.mobileapps.R

object NotificationHelper {
    private const val CHANNEL_ID = "PAYMENT"
    private const val CHANNEL_NAME = "Payment Successful"
    private const val CHANNEL_DESCRIPTION = "Payment Notification"

    fun makeStatusNotification(message: String, context: Context) {
        createNotificationChannel(context)

        // Create the notification
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(CHANNEL_NAME)
            .setContentText("Kamu berhasil masuk di kelas $message")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setVibrate(LongArray(0))

        // Show the notification
        NotificationManagerCompat.from(context).notify(1, builder.build())
    }

    private fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance)
            channel.description = CHANNEL_DESCRIPTION

            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?

            notificationManager?.createNotificationChannel(channel)
        }
    }
}
