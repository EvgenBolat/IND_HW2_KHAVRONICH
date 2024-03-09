package myWeb.cs.features.increaseMealAmount

import kotlinx.serialization.Serializable

@Serializable
data class IncreaseMealModerRequest(val token: ULong, val meal: String, val amount: UInt = 1u)
