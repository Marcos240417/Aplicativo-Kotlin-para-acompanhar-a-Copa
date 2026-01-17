package com.example.mycopa.notificationscheduler

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresPermission
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.mycopa.R // CORRIGIDO: de myopa para mycopa

private const val CHANNEL_ID = "matches_channel"
private const val NOTIFICATION_NAME = "Notificações de Partidas"

@RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
fun Context.showNotification(title: String, content: String) {
    createNotificationChannel()
    val notification = getNotification(title, content)

    NotificationManagerCompat.from(this).notify(content.hashCode(), notification)
}

private fun Context.createNotificationChannel() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(CHANNEL_ID, NOTIFICATION_NAME, importance)
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.createNotificationChannel(channel)
    }
}

private fun Context.getNotification(title: String, content: String): Notification =
    NotificationCompat.Builder(this, CHANNEL_ID)
        .setSmallIcon(R.drawable.ic_soccer) // Agora o R será resolvido
        .setContentTitle(title)
        .setContentText(content)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setAutoCancel(true)
        .setContentIntent(getOpenAppPendingIntent())
        .build()

private fun Context.getOpenAppPendingIntent(): PendingIntent = PendingIntent.getActivity(
    this, 0, packageManager.getLaunchIntentForPackage(packageName),
    PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
)