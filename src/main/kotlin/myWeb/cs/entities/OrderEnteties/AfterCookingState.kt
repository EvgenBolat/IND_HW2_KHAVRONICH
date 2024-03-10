package myWeb.cs.entities.OrderEnteties

import myWeb.cs.entities.Dto.Meal

class AfterCookingState(private val order: Order): OrderState {
    override fun addMeal(meal: Meal) {
        throw NoSuchMethodException("Невозможно добавить блюдо в уже приготовленный заказ")
    }

    override fun cook() {
        throw NoSuchMethodException("Нельзя готовить уже приготовленный заказ")
    }
}