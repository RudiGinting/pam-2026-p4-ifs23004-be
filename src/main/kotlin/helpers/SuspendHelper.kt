package org.delcom.helpers

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.transactions.transaction

/**
 * Helper sederhana untuk menjalankan operasi database dalam coroutine
 * Menggunakan withContext + transaction biasa
 */
suspend fun <T> suspendTransaction(block: () -> T): T =
    withContext(Dispatchers.IO) {
        transaction {
            block()
        }
    }