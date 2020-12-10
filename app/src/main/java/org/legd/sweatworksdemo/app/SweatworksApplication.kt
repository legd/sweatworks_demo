package org.legd.sweatworksdemo.app

import android.app.Application
import org.legd.sweatworksdemo.database.SweatworksDatabase
import org.legd.sweatworksdemo.repositories.UserRepository

class SweatworksApplication : Application() {

    val sweatworksDatabase by lazy { SweatworksDatabase.getDatabase(this) }
    val userRepository by lazy { UserRepository(sweatworksDatabase.getUSerDao()) }
}