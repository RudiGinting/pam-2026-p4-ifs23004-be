package org.delcom.dao

import org.delcom.tables.ObatTable
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import java.util.UUID

class ObatDAO(id: EntityID<UUID>) : Entity<UUID>(id) {
    companion object : EntityClass<UUID, ObatDAO>(ObatTable)

    var nama by ObatTable.nama
    var pathGambar by ObatTable.pathGambar
    var kategori by ObatTable.kategori
    var deskripsi by ObatTable.deskripsi
    var manfaat by ObatTable.manfaat
    var efekSamping by ObatTable.efekSamping
    var dosis by ObatTable.dosis
    var peringatan by ObatTable.peringatan
    var bentukSediaan by ObatTable.bentukSediaan
    var golongan by ObatTable.golongan
    var kontraindikasi by ObatTable.kontraindikasi
    var interaksiObat by ObatTable.interaksiObat
    var createdAt by ObatTable.createdAt
    var updatedAt by ObatTable.updatedAt
}