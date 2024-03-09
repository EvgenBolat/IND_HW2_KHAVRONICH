package myWeb.cs.entities.OrderEnteties

import myWeb.cs.entities.Dto.Meal

interface OrderState {
    fun addMeal(meal: Meal): Unit
    fun cook():Unit
}