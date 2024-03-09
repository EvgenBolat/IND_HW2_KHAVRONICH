package myWeb.cs.features.userRegistrate

import myWeb.cs.entities.AuthManager
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.register() {
    routing {
        post("/registration") {
            val result = call.receive<RegistrationModel>()

            val authManager = AuthManager
            try{
                authManager.addUser(result.login, result.password, result.role)
            }  catch (e: ArrayStoreException) {
                call.respond(HttpStatusCode.Conflict, e.message.toString())
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadGateway, "Произошла ошибка, повторите позднее.")
            }
            call.respond(HttpStatusCode.OK, "Регистрация прошла успешно")
        }
    }
}
