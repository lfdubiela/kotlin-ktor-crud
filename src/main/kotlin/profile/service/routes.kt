package main.kotlin.profile.service

import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.*
import main.kotlin.profile.dao.NewProfile

fun Route.profile(profileService: ProfileService) {

    route("/profile") {

        get("/") {
            call.respond(profileService.getAllProfiles())
        }

        get("/{id}") {
            val id = call.parameters["id"]?.toInt() ?: throw IllegalStateException("Must provide id")
            val profile = profileService.getProfile(id)
            if (profile == null) call.respond(HttpStatusCode.NotFound)
            else call.respond(profile)
        }

        post("/") {
            val profile = call.receive<NewProfile>()
            call.respond(HttpStatusCode.Created, profileService.addProfile(profile))
        }

        put("/") {
            val profile = call.receive<NewProfile>()
            val updated = profileService.updateProfile(profile)
            if (!updated) call.respond(HttpStatusCode.NotFound)
            else call.respond(HttpStatusCode.OK, updated)
        }

        delete("/{id}") {
            val id = call.parameters["id"]?.toInt() ?: throw IllegalStateException("Must provide id");
            val removed = profileService.deleteProfile(id)
            if (removed) call.respond(HttpStatusCode.OK)
            else call.respond(HttpStatusCode.NotFound)
        }
    }
}