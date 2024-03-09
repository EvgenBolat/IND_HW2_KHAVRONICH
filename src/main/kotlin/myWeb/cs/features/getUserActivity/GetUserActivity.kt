package myWeb.cs.features.getUserActivity

import myWeb.cs.entities.usersEntities.Admin
import myWeb.cs.entities.AuthManager
import myWeb.cs.entities.Dto.UserActivity
import myWeb.cs.entities.usersEntities.Visitor
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.getUserActivity() {
    routing {
        post("/getUserActivity") {
            val result = call.receive<UserActivityModelRequest>()

            val user = AuthManager.checkToken(result.token)
            if (user == null || user is Visitor) {
                call.respond(HttpStatusCode.Forbidden, "Войдите как админ")
            }
            var response: List<UserActivity>? = null
            try {
                response = (user as Admin).dataBaseAdapter.getUserActivity()
            } catch (e: NullPointerException) {
                call.respond(HttpStatusCode.BadGateway, "Список активности пуст :(")
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadGateway, "Произошла какая-то ошибка %((")
            }
            call.respond(HttpStatusCode.OK, UserActivityModelResponse(response!!))
        }
    }
}