package myWeb.cs.features.orderMeal

import myWeb.cs.entities.usersEntities.Admin
import myWeb.cs.entities.AuthManager
import myWeb.cs.entities.usersEntities.Visitor
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*


fun Application.orderMeal() {
    routing {
        post("/orderMeal") {
            val result = call.receive<OrderMealModelRequest>()

            val authManager = AuthManager
            val user = AuthManager.checkToken(result.token)
            if (user == null || user is Admin){
                call.respond(HttpStatusCode.Forbidden, "Войдите как посетитель")
            }
            try {
                (user as Visitor).orderBuilder.addMeal(result.name)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, e.message.toString())
            }
            call.respond(HttpStatusCode.OK, "Добавлено блюдо: " + result.name)
        }
    }
}