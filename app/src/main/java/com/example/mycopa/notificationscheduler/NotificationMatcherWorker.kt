package com.example.mycopa.notificationscheduler

import android.Manifest
import android.content.Context
import androidx.annotation.RequiresPermission
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.example.mycopa.domain.model.MatchDomain
import java.time.Duration
import java.time.LocalDateTime

class NotificationMatcherWorker(
    private val context: Context,
    workerParams: WorkerParameters
) : Worker(context, workerParams) {


    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    override fun doWork(): Result {
        val title = inputData.getString("TITLE") ?: "Jogo da Copa"
        val content = inputData.getString("CONTENT") ?: "A partida vai começar!"

        // Extensão que criamos na Parte 1 para mostrar a notificação real
        context.showNotification(title, content)
        return Result.success()
    }

    companion object {

        fun start(context: Context, match: MatchDomain) {
            // Calcula o tempo restante até 5 minutos antes do jogo
            val initialDelay = Duration.between(LocalDateTime.now(), match.date).minusMinutes(5)
            val delay = if (initialDelay.isNegative) Duration.ZERO else initialDelay

            val data = workDataOf(
                "TITLE" to "Prepare o coração!",
                "CONTENT" to "Hoje tem ${match.team1.flag} vs ${match.team2.flag}"
            )

            val request = OneTimeWorkRequestBuilder<NotificationMatcherWorker>()
                .setInitialDelay(delay)
                .setInputData(data)
                .build()

            WorkManager.getInstance(context).enqueueUniqueWork(
                match.id,
                ExistingWorkPolicy.REPLACE,
                request
            )
        }

        fun cancel(context: Context, match: MatchDomain) {
            WorkManager.getInstance(context).cancelUniqueWork(match.id)
        }
    }
}