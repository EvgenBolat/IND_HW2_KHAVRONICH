package myWeb.cs.features.userRegistrate

import kotlinx.serialization.Serializable

@Serializable
data class RegistrationModel (
    val login: String,
    val password: String,
    val role: Boolean = false
)