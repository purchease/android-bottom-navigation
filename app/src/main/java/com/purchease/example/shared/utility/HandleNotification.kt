package com.purchease.example.shared.utility

import android.annotation.TargetApi
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.navigation.NavDeepLinkBuilder
import com.purchease.example.R
import com.purchease.example.modules.library.BookFragment
import java.util.*


object HandleNotifications {

    private const val SMALL_ICON = R.drawable.ic_nav_notification
    private const val ONGOING_NOTIFICATION_ID = 50120
    private const val CHANNEL_NAME = "MAX notification Channel"
    private val CHANNEL_ID = "${getRandomNumber()}"

    fun showNotification(context: Context) {
        val isPreOreo = Build.VERSION.SDK_INT < Build.VERSION_CODES.O
        val notification =
                if (isPreOreo) PreO.createNotification(context)
                else O.createNotification(context)

        // display notification
        NotificationManagerCompat.from(context).notify(ONGOING_NOTIFICATION_ID, notification)
    }

    private fun getIntent(context: Context): PendingIntent {
        val bundle = Bundle().apply {
            putString(BookFragment.KEY_TITLE, "Game of Thrones: The short night")
            putString(BookFragment.KEY_DATE, "2019")
        }

        return NavDeepLinkBuilder(context)
                .setGraph(R.navigation.nav_graph_library)
                .setDestination(R.id.book_dest)
                .setArguments(bundle)
                .createPendingIntent()
    }

    private fun getNotification(context: Context, channelId: String): NotificationCompat.Builder {

        // Create Pending Intents.
        val piLaunchMainActivity = getIntent(context)

        return NotificationCompat.Builder(context, channelId)
                .setContentTitle("Game of Thrones")
                .setContentText("The short night")
                .setStyle(NotificationCompat.BigTextStyle())
                .setAutoCancel(true)
                .setSmallIcon(SMALL_ICON)
                .setContentIntent(piLaunchMainActivity)
    }

    //
    // Pre O specific versions.
    //

    @TargetApi(25)
    object PreO {

        fun createNotification(context: Context): Notification {

            // Create a notification.
            val builder = getNotification(context, CHANNEL_ID)

            // build notification
            return builder.build()
        }
    }


    //
    // Oreo and Above Specific versions.
    //

    @TargetApi(26)
    object O {
        fun createNotification(context: Context): Notification {
            val channelId = createChannel(context)
            // Create a notification.
            val builder = getNotification(context, channelId)
            return builder.build()
        }

        private fun createChannel(context: Context): String {
            // Create a channel.
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val importance = NotificationManager.IMPORTANCE_HIGH
            val notificationChannel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance)
            notificationManager.createNotificationChannel(notificationChannel)
            return CHANNEL_ID
        }
    }


    private fun getRandomNumber(): Int {
        return Random().nextInt(100000)
    }
}