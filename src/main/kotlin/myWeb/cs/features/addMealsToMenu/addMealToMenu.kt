package myWeb.cs.features.addMealsToMenu

import myWeb.cs.entities.usersEntities.Admin
import myWeb.cs.entities.AuthManager
import myWeb.cs.entities.usersEntities.Visitor
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.addMealToMenu() {
    routing {
        post("/addMealToMenu") {
            val result = call.receive<AddMealToMenuModelRequest>()
            val user = AuthManager.checkToken(result.token)
            if (user == null || user is Visitor){
                call.respond(HttpStatusCode.Forbidden, "Войдите через админа")
            }
            try{
                (user as Admin).dataBaseAdapter.addMeal(result.dishName, result.price, result.cookingTime, result.amount)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, "Произошла ошибка")
            }

            call.respond(HttpStatusCode.OK, "В меню добавлено следующее блюдо: " + result.dishName)

        }
    }
}