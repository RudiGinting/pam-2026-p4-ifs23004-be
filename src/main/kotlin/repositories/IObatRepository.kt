package org.delcom.repositories

import org.delcom.entities.Obat

interface IObatRepository {
    suspend fun getObat(search: String): List<Obat>
    suspend fun getObatById(id: String): Obat?
    suspend fun getObatByName(name: String): Obat?
    suspend fun addObat(obat: Obat): String
    suspend fun updateObat(id: String, newObat: Obat): Boolean
    suspend fun removeObat(id: String): Boolean
}