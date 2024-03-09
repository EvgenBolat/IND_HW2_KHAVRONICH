package myWeb.cs.features.orderMeal

import kotlinx.serialization.Serializable

@Serializable
data class OrderMealModelRequest(val token: ULong, val name: String)
