package myWeb.cs.entities.Dto

import kotlinx.serialization.Serializable

@Serializable
class Meal(val id: Int, val name: String, var cookingTime: UInt, var price: UInt) {
}