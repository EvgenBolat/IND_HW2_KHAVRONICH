package myWeb.cs.features.removeDishFromOrder

import myWeb.cs.entities.usersEntities.Admin
import myWeb.cs.entities.AuthManager
import myWeb.cs.entities.usersEntities.Visitor
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.removeDishFromOrder() {
    routing {
        post("/removeDishFromOrder") {
            val result = call.receive<RemoveDishFromOrderModelRequest>()

            val user = AuthManager.checkToken(result.token)
            if (user == null || user is Admin) {
                call.respond(HttpStatusCode.Forbidden, "Войдите, как посетитель")
            }
            try {
                (user as Visitor).orderBuilder.removeMeal(result.name)
            } catch (e: NoSuchMethodException) {
                call.respond(HttpStatusCode.MethodNotAllowed, e.message.toString())
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, "Невозможно удалить блюдо из заказа")
            }
            call.respond(HttpStatusCode.OK, "Блюдо успешно удалено из заказа")
        }
    }
}