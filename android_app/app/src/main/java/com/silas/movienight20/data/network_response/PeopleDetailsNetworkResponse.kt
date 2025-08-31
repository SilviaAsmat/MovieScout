package com.silas.movienight20.data.network_response

import com.google.gson.annotations.SerializedName

class PeopleDetailsNetworkResponse (
    @SerializedName("biography")
    val bio: String?,
    @SerializedName("birthday")
    val birthday: String?,
    @SerializedName("deathday")
    val deathday: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("place_of_birth")
    val birthPlace: String?,
    @SerializedName("profile_path")
    val profilePath: String?
)