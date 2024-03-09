package myWeb.cs.features.increaseMealAmount

import myWeb.cs.entities.usersEntities.Admin
import myWeb.cs.entities.AuthManager
import myWeb.cs.entities.usersEntities.Visitor
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.increaseMealAmount() {
    routing {
        post("/increaseMealAmount") {
            val result = call.receive<IncreaseMealModerRequest>()

            val user = AuthManager.checkToken(result.token)
            if (user == null || user is Visitor) {
                call.respond(HttpStatusCode.Forbidden, "Войдите, как админ")
            }

            try {
                (user as Admin).dataBaseAdapter.increaseMealAmount(result.meal, result.amount)
            } catch (e: NullPointerException) {
                call.respond(HttpStatusCode.BadGateway, "Произошла ошибка")
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadGateway, "Произошла серьёзная ошибка")
            }
            call.respond(HttpStatusCode.OK, "Количество блюд увеличено")
        }
    }
}