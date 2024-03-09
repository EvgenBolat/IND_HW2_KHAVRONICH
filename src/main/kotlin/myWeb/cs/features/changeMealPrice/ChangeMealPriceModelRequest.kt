package myWeb.cs.features.changeMealPrice

import kotlinx.serialization.Serializable

@Serializable
data class ChangeMealPriceModelRequest(val token: ULong, val dish: String, val price: UInt)
