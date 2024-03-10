package myWeb.cs.features.changeMealPrice

import myWeb.cs.entities.usersEntities.Admin
import myWeb.cs.entities.AuthManager
import myWeb.cs.entities.usersEntities.Visitor
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.changeMealPrice() {
    routing {
        post("/changeMealPrice") {
            val result = call.receive<ChangeMealPriceModelRequest>()

            val user = AuthManager.checkToken(result.token)
            if (user == null || user is Visitor) {
                call.respond(HttpStatusCode.Forbidden, "Войдите как админ")
            }

            try {
                (user as Admin).dataBaseAdapter.changeMealPrice(result.dish, result.price)
            } catch (e: NullPointerException) {
                call.respond(HttpStatusCode.BadGateway, "Произошла ошибка")
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadGateway)
            }
            call.respond(HttpStatusCode.OK, "Цена блюда успешно изменена")
        }
    }
}