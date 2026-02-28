package org.delcom.tables

import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.kotlin.datetime.timestamp

object ObatTable : UUIDTable("obat") {
    val nama = varchar("nama", 255)
    val pathGambar = varchar("path_gambar", 255)
    val kategori = varchar("kategori", 100)
    val deskripsi = text("deskripsi")
    val manfaat = text("manfaat")
    val efekSamping = text("efek_samping")
    val dosis = text("dosis")
    val peringatan = text("peringatan")
    val bentukSediaan = varchar("bentuk_sediaan", 100)
    val golongan = varchar("golongan", 50)
    val kontraindikasi = text("kontraindikasi")
    val interaksiObat = text("interaksi_obat")
    val createdAt = timestamp("created_at")
    val updatedAt = timestamp("updated_at")
}