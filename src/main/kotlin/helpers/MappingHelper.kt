package org.delcom.helpers

import kotlinx.coroutines.Dispatchers
import org.delcom.dao.ObatDAO
import org.delcom.entities.Obat
import org.delcom.dao.PlantDAO
import org.delcom.entities.Plant
import org.jetbrains.exposed.sql.Transaction
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

suspend fun <T> suspendTransaction(block: Transaction.() -> T): T =
    newSuspendedTransaction(Dispatchers.IO, statement = block)

/**
 * Konversi dari ObatDAO ke Obat entity (sangat ringkas)
 */
fun daoToModelObat(dao: ObatDAO): Obat = Obat(
    dao.id.value.toString(),
    dao.nama,
    dao.pathGambar,
    dao.kategori,
    dao.deskripsi,
    dao.manfaat,
    dao.efekSamping,
    dao.dosis,
    dao.peringatan,
    dao.bentukSediaan,
    dao.golongan,
    dao.kontraindikasi,
    dao.interaksiObat,
    dao.createdAt,
    dao.updatedAt
)


fun daoToModel(dao: PlantDAO): Plant = Plant(
    dao.id.value.toString(),
    dao.nama,
    dao.pathGambar,
    dao.deskripsi,
    dao.manfaat,
    dao.efekSamping,
    dao.createdAt,
    dao.updatedAt
)