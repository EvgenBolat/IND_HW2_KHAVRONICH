package myWeb.cs.features.addMealsToMenu

import kotlinx.serialization.Serializable

@Serializable
class AddMealToMenuModelRequest(val token: ULong, val dishName: String, val price: UInt, val cookingTime: Int, val amount: UInt = 0u)