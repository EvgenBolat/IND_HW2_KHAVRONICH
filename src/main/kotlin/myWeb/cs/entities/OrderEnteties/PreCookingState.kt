package myWeb.cs.entities.OrderEnteties

import myWeb.cs.entities.Dto.Meal

class PreCookingState(private val order: Order): OrderState {

    override fun addMeal(meal: Meal) {
        order.mealsList.add(meal)
    }

    override fun cook() {
        order.orderState = CookingState(order)
        order.orderState.cook()
    }
}