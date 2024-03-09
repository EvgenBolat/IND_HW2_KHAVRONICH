package myWeb.cs.features.pay

import kotlinx.serialization.Serializable

@Serializable
data class PayRequest(val token: ULong, val cardNumber: UInt)
