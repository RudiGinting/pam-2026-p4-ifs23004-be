package org.delcom.services

import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.util.cio.*
import io.ktor.utils.io.*
import org.delcom.data.AppException
import org.delcom.data.DataResponse
import org.delcom.data.ObatRequest
import org.delcom.helpers.ValidatorHelper
import org.delcom.repositories.IObatRepository
import java.io.File
import java.util.*

class ObatService(private val obatRepository: IObatRepository) {

    // Mengambil semua data obat
    suspend fun getAllObat(call: ApplicationCall) {
        val search = call.request.queryParameters["search"] ?: ""

        val obatList = obatRepository.getObat(search)

        val response = DataResponse(
            "success",
            "Berhasil mengambil daftar obat",
            mapOf(Pair("obat", obatList))
        )
        call.respond(response)
    }

    // Mengambil data obat berdasarkan id
    suspend fun getObatById(call: ApplicationCall) {
        val id = call.parameters["id"]
            ?: throw AppException(400, "ID obat tidak boleh kosong!")

        val obat = obatRepository.getObatById(id)
            ?: throw AppException(404, "Data obat tidak tersedia!")

        val response = DataResponse(
            "success",
            "Berhasil mengambil data obat",
            mapOf(Pair("obat", obat))
        )
        call.respond(response)
    }

    // Ambil data request
    private suspend fun getObatRequest(call: ApplicationCall): ObatRequest {
        val obatReq = ObatRequest()

        val multipartData = call.receiveMultipart(formFieldLimit = 1024 * 1024 * 5)
        multipartData.forEachPart { part ->
            when (part) {
                is PartData.FormItem -> {
                    when (part.name) {
                        "nama" -> obatReq.nama = part.value.trim()
                        "kategori" -> obatReq.kategori = part.value.trim()
                        "deskripsi" -> obatReq.deskripsi = part.value
                        "manfaat" -> obatReq.manfaat = part.value
                        "efekSamping" -> obatReq.efekSamping = part.value
                        "dosis" -> obatReq.dosis = part.value
                        "peringatan" -> obatReq.peringatan = part.value
                        "bentukSediaan" -> obatReq.bentukSediaan = part.value
                        "golongan" -> obatReq.golongan = part.value
                        "kontraindikasi" -> obatReq.kontraindikasi = part.value
                        "interaksiObat" -> obatReq.interaksiObat = part.value
                    }
                }

                is PartData.FileItem -> {
                    val ext = part.originalFileName
                        ?.substringAfterLast('.', "")
                        ?.let { if (it.isNotEmpty()) ".$it" else "" }
                        ?: ""

                    val fileName = UUID.randomUUID().toString() + ext
                    val filePath = "uploads/obat/$fileName"

                    val file = File(filePath)
                    file.parentFile.mkdirs()

                    part.provider().copyAndClose(file.writeChannel())
                    obatReq.pathGambar = filePath
                }

                else -> {}
            }

            part.dispose()
        }

        return obatReq
    }

    // Validasi request data dari pengguna
    private fun validateObatRequest(obatReq: ObatRequest) {
        val validatorHelper = ValidatorHelper(obatReq.toMap())
        validatorHelper.required("nama", "Nama obat tidak boleh kosong")
        validatorHelper.required("kategori", "Kategori tidak boleh kosong")
        validatorHelper.required("deskripsi", "Deskripsi tidak boleh kosong")
        validatorHelper.required("manfaat", "Manfaat tidak boleh kosong")
        validatorHelper.required("efekSamping", "Efek Samping tidak boleh kosong")
        validatorHelper.required("dosis", "Dosis tidak boleh kosong")
        validatorHelper.required("peringatan", "Peringatan tidak boleh kosong")
        validatorHelper.required("bentukSediaan", "Bentuk sediaan tidak boleh kosong")
        validatorHelper.required("golongan", "Golongan obat tidak boleh kosong")
        validatorHelper.required("kontraindikasi", "Kontraindikasi tidak boleh kosong")
        validatorHelper.required("interaksiObat", "Interaksi obat tidak boleh kosong")
        validatorHelper.required("pathGambar", "Gambar tidak boleh kosong")
        validatorHelper.validate()

        val file = File(obatReq.pathGambar)
        if (!file.exists()) {
            throw AppException(400, "Gambar obat gagal diupload!")
        }
    }

    // Menambahkan data obat
    suspend fun createObat(call: ApplicationCall) {
        val obatReq = getObatRequest(call)
        validateObatRequest(obatReq)

        val existObat = obatRepository.getObatByName(obatReq.nama)
        if (existObat != null) {
            val tmpFile = File(obatReq.pathGambar)
            if (tmpFile.exists()) {
                tmpFile.delete()
            }
            throw AppException(409, "Obat dengan nama ini sudah terdaftar!")
        }

        val obatId = obatRepository.addObat(obatReq.toEntity())

        val response = DataResponse(
            "success",
            "Berhasil menambahkan data obat",
            mapOf(Pair("obatId", obatId))
        )
        call.respond(response)
    }

    // Mengubah data obat
    suspend fun updateObat(call: ApplicationCall) {
        val id = call.parameters["id"]
            ?: throw AppException(400, "ID obat tidak boleh kosong!")

        val oldObat = obatRepository.getObatById(id)
            ?: throw AppException(404, "Data obat tidak tersedia!")

        val obatReq = getObatRequest(call)

        if (obatReq.pathGambar.isEmpty()) {
            obatReq.pathGambar = oldObat.pathGambar
        }

        validateObatRequest(obatReq)

        if (obatReq.nama != oldObat.nama) {
            val existObat = obatRepository.getObatByName(obatReq.nama)
            if (existObat != null) {
                val tmpFile = File(obatReq.pathGambar)
                if (tmpFile.exists()) {
                    tmpFile.delete()
                }
                throw AppException(409, "Obat dengan nama ini sudah terdaftar!")
            }
        }

        if (obatReq.pathGambar != oldObat.pathGambar) {
            val oldFile = File(oldObat.pathGambar)
            if (oldFile.exists()) {
                oldFile.delete()
            }
        }

        val isUpdated = obatRepository.updateObat(id, obatReq.toEntity())
        if (!isUpdated) {
            throw AppException(400, "Gagal memperbarui data obat!")
        }

        val response = DataResponse(
            "success",
            "Berhasil mengubah data obat",
            null
        )
        call.respond(response)
    }

    // Menghapus data obat
    suspend fun deleteObat(call: ApplicationCall) {
        val id = call.parameters["id"]
            ?: throw AppException(400, "ID obat tidak boleh kosong!")

        val oldObat = obatRepository.getObatById(id)
            ?: throw AppException(404, "Data obat tidak tersedia!")

        val oldFile = File(oldObat.pathGambar)

        val isDeleted = obatRepository.removeObat(id)
        if (!isDeleted) {
            throw AppException(400, "Gagal menghapus data obat!")
        }

        if (oldFile.exists()) {
            oldFile.delete()
        }

        val response = DataResponse(
            "success",
            "Berhasil menghapus data obat",
            null
        )
        call.respond(response)
    }

    // Mengambil gambar obat
    suspend fun getObatImage(call: ApplicationCall) {
        val id = call.parameters["id"]
            ?: return call.respond(HttpStatusCode.BadRequest)

        val obat = obatRepository.getObatById(id)
            ?: return call.respond(HttpStatusCode.NotFound)

        val file = File(obat.pathGambar)

        if (!file.exists()) {
            return call.respond(HttpStatusCode.NotFound)
        }

        call.respondFile(file)
    }
}