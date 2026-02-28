package org.delcom.helpers

import org.delcom.dao.ObatDAO
import org.delcom.entities.Obat
import org.delcom.dao.PlantDAO
import org.delcom.entities.Plant

fun daoToModelObat(dao: ObatDAO): Obat {
    return Obat(
        id = dao.id.value.toString(),
        nama = dao.nama,
        pathGambar = dao.pathGambar,
        kategori = dao.kategori,
        deskripsi = dao.deskripsi,
        manfaat = dao.manfaat,
        efekSamping = dao.efekSamping,
        dosis = dao.dosis,
        peringatan = dao.peringatan,
        bentukSediaan = dao.bentukSediaan,
        golongan = dao.golongan,
        kontraindikasi = dao.kontraindikasi,
        interaksiObat = dao.interaksiObat,
        createdAt = dao.createdAt,
        updatedAt = dao.updatedAt
    )
}

fun daoToModel(dao: PlantDAO): Plant {
    return Plant(
        id = dao.id.value.toString(),
        nama = dao.nama,
        pathGambar = dao.pathGambar,
        deskripsi = dao.deskripsi,
        manfaat = dao.manfaat,
        efekSamping = dao.efekSamping,
        createdAt = dao.createdAt,
        updatedAt = dao.updatedAt
    )
}