package myWeb.cs

import myWeb.cs.features.addMealsToMenu.addMealToMenu
import myWeb.cs.features.cancelOrder.cancelOrder
import myWeb.cs.features.changeMealPrice.changeMealPrice
import myWeb.cs.features.getIncome.getIncome
import myWeb.cs.features.getMenu.getMenu
import myWeb.cs.features.getUserActivity.getUserActivity
import myWeb.cs.features.increaseMealAmount.increaseMealAmount
import myWeb.cs.features.login.login
import myWeb.cs.features.logout.logout
import myWeb.cs.features.orderMeal.orderMeal
import myWeb.cs.features.pay.pay
import myWeb.cs.features.putOrder.putOrder
import myWeb.cs.features.userRegistrate.register
import myWeb.cs.features.removeDishFromMenu.removeDishFromMenu
import myWeb.cs.features.removeDishFromOrder.removeDishFromOrder
import myWeb.cs.plugins.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import myWeb.cs.features.addMealsToMenu.addMealToMenu
import myWeb.cs.features.changeMealPrice.changeMealCookingTime
import myWeb.cs.features.getIncome.getIncome
import myWeb.cs.features.increaseMealAmount.increaseMealAmount
import myWeb.cs.features.pay.pay
import myWeb.cs.features.putOrder.putOrder
import myWeb.cs.features.removeDishFromMenu.removeDishFromMenu
import myWeb.cs.features.removeDishFromOrder.removeDishFromOrder

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureSerialization()
    configureRouting()
    register()
    login()
    logout()
    getMenu()
    orderMeal()
    putOrder()
    pay()
    addMealToMenu()
    increaseMealAmount()
    cancelOrder()
    getIncome()
    getUserActivity()
    removeDishFromMenu()
    removeDishFromOrder()
    changeMealPrice()
    changeMealCookingTime()
}
