package org.delcom.module

import org.delcom.repositories.IObatRepository
import org.delcom.repositories.IPlantRepository
import org.delcom.repositories.ObatRepository
import org.delcom.repositories.PlantRepository
import org.delcom.services.ObatService
import org.delcom.services.PlantService
import org.delcom.services.ProfileService
import org.koin.dsl.module

val appModule = module {
    // Plant Repository
    single<IPlantRepository> {
        PlantRepository()
    }

    // Plant Service
    single {
        PlantService(get())
    }

    // Profile Service
    single {
        ProfileService()
    }

    // Obat Repository
    single<IObatRepository> {
        ObatRepository()
    }

    // Obat Service
    single {
        ObatService(get())
    }
}