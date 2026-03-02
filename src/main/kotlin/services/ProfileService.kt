package org.delcom.services

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.*
import io.ktor.server.response.*
import org.delcom.data.DataResponse
import java.io.File

class ProfileService {
    // Mengambil data profile pengembang
    suspend fun getProfile(call: ApplicationCall) {
        val response = DataResponse(
            status = "success",
            message = "✅ Profil pengembang berhasil dimuat",
            data = mapOf(
                "username" to "ifs23004",
                "nama" to "Rudi Alva Jonathan Ginting",
                "tentang" to """
                    |🌟 Tentang Saya:
                    |Halo! Saya Rudi Alva Jonathan Ginting, seorang mahasiswa Informatika yang memiliki 
                    |ketertarikan mendalam di dunia pengembangan aplikasi mobile, khususnya Android.
  
                """.trimMargin().replace("\n", " ").trim()
            )
        )
        call.respond(response)
    }

    // Mengambil photo profile
    suspend fun getProfilePhoto(call: ApplicationCall) {
        val file = File("uploads/profile/me.png")

        if (!file.exists()) {
            return call.respond(HttpStatusCode.NotFound, DataResponse(
                status = "error",
                message = "❌ Foto profil tidak ditemukan. Pastikan file berada di uploads/profile/me.png",
                data = null
            ))
        }

        call.respondFile(file)
    }
}