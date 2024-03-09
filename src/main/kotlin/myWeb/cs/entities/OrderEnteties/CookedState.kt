package myWeb.cs.entities.OrderEnteties

import myWeb.cs.entities.Dto.Meal
import myWeb.cs.entities.Dto.Order

class CookedState(private val order: Order): OrderState {
    override fun addMeal(meal: Meal) {
        throw NoSuchMethodException("Невозможно добавить блюдо в уже приготовленный заказ")
    }

    override fun cook() {
        throw NoSuchMethodException("Нельзя готовить уже приготовленный заказ")
    }
}