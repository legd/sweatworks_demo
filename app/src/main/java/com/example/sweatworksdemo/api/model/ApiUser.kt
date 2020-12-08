package com.example.sweatworksdemo.api.model

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
)

data class Location(
    val street: Street,
    val city: String,
    val state: String,
    val postcode: String
)

data class Street(
        val number: Int,
        val name: String
)

data class Picture(
    val large: String,
    val medium: String,
    val thumbnail: String
)

data class ApiUser(
    val name: Name,
    val location: Location,
    val email: String,
    val phone: String,
    val cell: String,
    val picture: Picture
)