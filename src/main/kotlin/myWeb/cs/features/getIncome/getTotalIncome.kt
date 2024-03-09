package myWeb.cs.features.getIncome

import myWeb.cs.entities.usersEntities.Admin
import myWeb.cs.entities.AuthManager
import myWeb.cs.entities.usersEntities.Visitor
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.getIncome() {
    routing {
        post("/getIncome") {
            val result = call.receive<IncomeModelRequest>()

            val user = AuthManager.checkToken(result.token)
            if (user == null || user is Visitor) {
                call.respond(HttpStatusCode.Forbidden, "Войдите как админ")
            }
            var response: UInt = 0u
            try {
                response = (user as Admin).dataBaseAdapter.getIncome()
            } catch (e: NullPointerException) {
                call.respond(HttpStatusCode.BadGateway, "Произошла ошибка :(")
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadGateway)
            }
            call.respond(HttpStatusCode.OK, IncomeModelResponse(response))
        }
    }
}