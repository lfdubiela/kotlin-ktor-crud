package main.kotlin.profile.dao

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption

enum class Service(val type: String) {
    SERVICE_1("SERVICE_1"),
    SERVICE_2("SERVICE_2"),
    SERVICE_3("SERVICE_3")
}

object ProfilesServices : IntIdTable("profile_service") {
    val profile = reference("profiles", Profiles.id, onDelete = ReferenceOption.CASCADE)
    val service = enumerationByName("services", 20, Service::class)
    override val primaryKey = PrimaryKey(profile, service, name = "PK_ProfileService")
}

class ProfileServices(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<ProfileServices>(ProfilesServices)

    var service by ProfilesServices.service
    var profile by Profile referencedOn ProfilesServices.profile
}

