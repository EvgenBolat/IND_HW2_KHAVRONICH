package myWeb.cs.entities.OrderEnteties

import entities.DataBaseAdapter
import myWeb.cs.entities.Dto.Meal

class OrderBuilder {
    private var order: Order = Order()
    private val dbAdapter: DataBaseAdapter = DataBaseAdapter
    fun addMeal(mealName: String){
        val meal: Meal
        try {
            meal = dbAdapter.getMeal(mealName)
        } catch (e: IndexOutOfBoundsException) {
            throw IllegalArgumentException("Такого блюда в меню нет")
        }
        dbAdapter.reduceMealAmount(meal.id)
        order.addMeal(meal)
    }

    fun removeMeal(mealName: String) {
        try{
            order.removeMeal(dbAdapter.getMeal(mealName));
        } catch (e: NullPointerException) {
            throw NoSuchMethodException("Нет возможности убрать блюдо")
        } catch (e: IndexOutOfBoundsException) {
            throw e
        } catch (e:NoSuchMethodException) {
            throw e
        } catch (e: Exception) {
            throw Exception("Произошла ошибка :(")
        }
    }

    fun cookOrder(): Unit { //
        return order.cook()
    }

    fun cancelOrder() {
        order.cancel()
        order = Order()
    }

    fun getOrder(): Order? {
        if (order.orderState is CookedState) return order
        return null
    }

}