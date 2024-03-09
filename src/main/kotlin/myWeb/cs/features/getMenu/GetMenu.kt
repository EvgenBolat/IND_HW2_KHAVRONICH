package myWeb.cs.features.getMenu

import entities.DataBaseAdapter
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.getMenu() {
    routing {
        get("/menu") {
            call.respond(HttpStatusCode.OK, DataBaseAdapter.getMenu())
        }
    }
}