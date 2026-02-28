package org.delcom.repositories

import org.delcom.dao.ObatDAO
import org.delcom.entities.Obat
import org.delcom.helpers.daoToModelObat
import org.delcom.helpers.suspendTransaction
import org.delcom.tables.ObatTable
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.lowerCase
import java.util.UUID

class ObatRepository : IObatRepository {
    override suspend fun getObat(search: String): List<Obat> = suspendTransaction {
        if (search.isBlank()) {
            ObatDAO.all()
                .orderBy(ObatTable.createdAt to SortOrder.DESC)
                .limit(20)
                .map(::daoToModelObat)
        } else {
            val keyword = "%${search.lowercase()}%"

            ObatDAO
                .find {
                    ObatTable.nama.lowerCase() like keyword
                }
                .orderBy(ObatTable.nama to SortOrder.ASC)
                .limit(20)
                .map(::daoToModelObat)
        }
    }

    override suspend fun getObatById(id: String): Obat? = suspendTransaction {
        ObatDAO
            .find { (ObatTable.id eq UUID.fromString(id)) }
            .limit(1)
            .map(::daoToModelObat)
            .firstOrNull()
    }

    override suspend fun getObatByName(name: String): Obat? = suspendTransaction {
        ObatDAO
            .find { (ObatTable.nama eq name) }
            .limit(1)
            .map(::daoToModelObat)
            .firstOrNull()
    }

    override suspend fun addObat(obat: Obat): String = suspendTransaction {
        val obatDAO = ObatDAO.new {
            nama = obat.nama
            pathGambar = obat.pathGambar
            kategori = obat.kategori
            deskripsi = obat.deskripsi
            manfaat = obat.manfaat
            efekSamping = obat.efekSamping
            dosis = obat.dosis
            peringatan = obat.peringatan
            bentukSediaan = obat.bentukSediaan
            golongan = obat.golongan
            kontraindikasi = obat.kontraindikasi
            interaksiObat = obat.interaksiObat
            createdAt = obat.createdAt
            updatedAt = obat.updatedAt
        }

        obatDAO.id.value.toString()
    }

    override suspend fun updateObat(id: String, newObat: Obat): Boolean = suspendTransaction {
        val obatDAO = ObatDAO
            .find { ObatTable.id eq UUID.fromString(id) }
            .limit(1)
            .firstOrNull()

        if (obatDAO != null) {
            obatDAO.nama = newObat.nama
            obatDAO.pathGambar = newObat.pathGambar
            obatDAO.kategori = newObat.kategori
            obatDAO.deskripsi = newObat.deskripsi
            obatDAO.manfaat = newObat.manfaat
            obatDAO.efekSamping = newObat.efekSamping
            obatDAO.dosis = newObat.dosis
            obatDAO.peringatan = newObat.peringatan
            obatDAO.bentukSediaan = newObat.bentukSediaan
            obatDAO.golongan = newObat.golongan
            obatDAO.kontraindikasi = newObat.kontraindikasi
            obatDAO.interaksiObat = newObat.interaksiObat
            obatDAO.updatedAt = newObat.updatedAt
            true
        } else {
            false
        }
    }

    override suspend fun removeObat(id: String): Boolean = suspendTransaction {
        val rowsDeleted = ObatTable.deleteWhere {
            ObatTable.id eq UUID.fromString(id)
        }
        rowsDeleted == 1
    }
}