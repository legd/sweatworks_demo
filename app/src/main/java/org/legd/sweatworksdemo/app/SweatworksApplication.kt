package org.legd.sweatworksdemo.app

import android.app.Application
import org.legd.sweatworksdemo.database.SweatworksDatabase
import org.legd.sweatworksdemo.repositories.UserRepository

/**
 * Class declaration for universal access of the user repository and the
 * initialization of the database.
 */
class SweatworksApplication : Application() {

    val sweatworksDatabase by lazy { SweatworksDatabase.getDatabase(this) }
    val userRepository by lazy { UserRepository(sweatworksDatabase.getUSerDao()) }
}