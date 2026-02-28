package org.delcom.data

import kotlinx.serialization.Serializable
import org.delcom.entities.Obat

@Serializable
data class ObatRequest(
    var nama: String = "",
    var kategori: String = "",
    var deskripsi: String = "",
    var manfaat: String = "",
    var efekSamping: String = "",
    var dosis: String = "",
    var peringatan: String = "",
    var bentukSediaan: String = "",
    var golongan: String = "",
    var kontraindikasi: String = "",
    var interaksiObat: String = "",
    var pathGambar: String = "",
) {
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "nama" to nama,
            "kategori" to kategori,
            "deskripsi" to deskripsi,
            "manfaat" to manfaat,
            "efekSamping" to efekSamping,
            "dosis" to dosis,
            "peringatan" to peringatan,
            "bentukSediaan" to bentukSediaan,
            "golongan" to golongan,
            "kontraindikasi" to kontraindikasi,
            "interaksiObat" to interaksiObat,
            "pathGambar" to pathGambar
        )
    }

    fun toEntity(): Obat {
        return Obat(
            nama = nama,
            kategori = kategori,
            deskripsi = deskripsi,
            manfaat = manfaat,
            efekSamping = efekSamping,
            dosis = dosis,
            peringatan = peringatan,
            bentukSediaan = bentukSediaan,
            golongan = golongan,
            kontraindikasi = kontraindikasi,
            interaksiObat = interaksiObat,
            pathGambar = pathGambar,
        )
    }
}