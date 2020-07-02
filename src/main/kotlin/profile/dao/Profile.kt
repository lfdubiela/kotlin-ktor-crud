package main.kotlin.profile.dao

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object Profiles : IntIdTable("profile") {
    val hash = varchar("hash", 6)
    val title = varchar("title", 100)
    val about = text("about").nullable()
    val dateUpdated = long("dateUpdated")
}

class Profile(id: EntityID<Int>): IntEntity(id) {
    companion object : IntEntityClass<Profile>(Profiles)
    var hash by Profiles.hash
    var title by Profiles.title
    var about by Profiles.about
    var dateUpdated by Profiles.dateUpdated

//    val services by ProfileServices referrersOn ProfilesServices.service
}

data class NewProfile(
    val id: Int?,
    val hash: String,
    val title: String,
    val about: String
//    val services: List<Service>
)
