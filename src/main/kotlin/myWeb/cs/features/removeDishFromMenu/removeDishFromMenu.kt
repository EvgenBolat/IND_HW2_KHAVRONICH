package myWeb.cs.features.removeDishFromMenu

import myWeb.cs.entities.usersEntities.Admin
import myWeb.cs.entities.AuthManager
import myWeb.cs.entities.usersEntities.Visitor

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.removeDishFromMenu() {
    routing {
        post("/removeDishFromMenu") {
            val result = call.receive<RemoveDishFromMenuRequestModel>()

            val user = AuthManager.checkToken(result.token)
            if (user == null || user is Visitor) {
                call.respond(HttpStatusCode.Forbidden, "Войдите как админ")
            }

            try {
                (user as Admin).dataBaseAdapter.removeMealFromMenu(result.name)
            } catch (e: NullPointerException) {
                call.respond(HttpStatusCode.BadGateway, "Произошла ошибка :(")
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadGateway, "Произошла какая-то ошибка :(((")
            }
            call.respond(HttpStatusCode.OK, "Блюдо успешно удалено из меню")
        }
    }
}