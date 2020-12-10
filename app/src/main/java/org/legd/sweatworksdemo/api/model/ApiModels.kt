package org.legd.sweatworksdemo.api.model

import android.os.Parcel
import android.os.Parcelable
import org.legd.sweatworksdemo.database.models.User

/**
 * File containing all the API models.
 */
data class RandomUserGenerator(
    val info: Info,
    val results: List<ApiUser>
)

data class Info(
    val seed: String,
    val results: Int,
    val page: Int,
    val version: String
)

data class Name(
    val title: String,
    val first: String,
    val last: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(first)
        parcel.writeString(last)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Name> {
        override fun createFromParcel(parcel: Parcel): Name {
            return Name(parcel)
        }

        override fun newArray(size: Int): Array<Name?> {
            return arrayOfNulls(size)
        }
    }
}

data class Location(
    val street: Street,
    val city: String,
    val state: String,
    val country: String,
    val postcode: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readParcelable(Street.javaClass.classLoader),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(street, flags)
        parcel.writeString(city)
        parcel.writeString(state)
        parcel.writeString(country)
        parcel.writeString(postcode)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Location> {
        override fun createFromParcel(parcel: Parcel): Location {
            return Location(parcel)
        }

        override fun newArray(size: Int): Array<Location?> {
            return arrayOfNulls(size)
        }
    }
}

data class Street(
    val number: Int,
    val name: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(number)
        parcel.writeString(name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Street> {
        override fun createFromParcel(parcel: Parcel): Street {
            return Street(parcel)
        }

        override fun newArray(size: Int): Array<Street?> {
            return arrayOfNulls(size)
        }
    }
}

data class Login(
    val uuid: String,
    val username: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(uuid)
        parcel.writeString(username)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Login> {
        override fun createFromParcel(parcel: Parcel): Login {
            return Login(parcel)
        }

        override fun newArray(size: Int): Array<Login?> {
            return arrayOfNulls(size)
        }
    }
}

data class Picture(
    val large: String,
    val medium: String,
    val thumbnail: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(large)
        parcel.writeString(medium)
        parcel.writeString(thumbnail)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Picture> {
        override fun createFromParcel(parcel: Parcel): Picture {
            return Picture(parcel)
        }

        override fun newArray(size: Int): Array<Picture?> {
            return arrayOfNulls(size)
        }
    }
}

data class ApiUser(
    val name: Name,
    val location: Location,
    val email: String,
    val login: Login,
    val phone: String,
    val cell: String,
    val picture: Picture
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readParcelable(Name.javaClass.classLoader),
        parcel.readParcelable(Location.javaClass.classLoader),
        parcel.readString(),
        parcel.readParcelable(Login.javaClass.classLoader),
        parcel.readString(),
        parcel.readString(),
        parcel.readParcelable(Picture.javaClass.classLoader)
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(name, flags)
        parcel.writeParcelable(location, flags)
        parcel.writeString(email)
        parcel.writeParcelable(login, flags)
        parcel.writeString(phone)
        parcel.writeString(cell)
        parcel.writeParcelable(picture, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ApiUser> {
        override fun createFromParcel(parcel: Parcel): ApiUser {
            return ApiUser(parcel)
        }

        override fun newArray(size: Int): Array<ApiUser?> {
            return arrayOfNulls(size)
        }
    }

    fun asDatabaseModel() : User {
        val fullName = String.format("%s%s%s", this.name.first, " ", this.name.last)
        val fullAddress = String.format("%s%s%s%s%s%s%s%s%s%s%s",
            this.location.street.number, " ",
            this.location.street.name, "\n",
            this.location.city, ", ",
            this.location.state, " ",
            this.location.postcode, "\n",
            this.location.country)

        return User(this.login.uuid, fullName, this.login.username, this.email, fullAddress,
            this.phone, this.cell, this.picture.thumbnail, this.picture.medium, this.picture.large)
    }
}