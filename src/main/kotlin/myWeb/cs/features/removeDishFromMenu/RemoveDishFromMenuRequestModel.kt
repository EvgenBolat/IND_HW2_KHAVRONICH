package myWeb.cs.features.removeDishFromMenu

import kotlinx.serialization.Serializable

@Serializable
data class RemoveDishFromMenuRequestModel(val token: ULong, val name: String)
