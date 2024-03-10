package myWeb.cs.features.changeMealPrice

import kotlinx.serialization.Serializable

@Serializable
data class ChangeMealCookingTimeModelRequest(val token: ULong, val dish: String, val newCookingTime: UInt)
