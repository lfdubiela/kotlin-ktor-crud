package main.kotlin.profile.service

import main.kotlin.infra.DatabaseFactory.dbQuery
import main.kotlin.profile.dao.*
import org.jetbrains.exposed.sql.*

class ProfileService {
    
    suspend fun getAllProfiles(): List<Profile> = dbQuery {
        Profiles.selectAll().map { toProfile(it) }
    }

    suspend fun getProfile(id: Int): Profile? = dbQuery {
        Profiles.select {
            (Profiles.id eq id)
        }.mapNotNull { toProfile(it) }
            .singleOrNull()
    }

    suspend fun updateProfile(profile: NewProfile): Boolean {
        return dbQuery {
            Profiles.update({ Profiles.id eq profile.id }) {
                it[hash] = profile.hash
                it[title] = profile.title
                it[about] = profile.about
                it[dateUpdated] = System.currentTimeMillis()
            } > 0
        }
    }

    suspend fun addProfile(profile: NewProfile): Boolean {
        return dbQuery {
            (Profiles.insert {
                it[hash] = profile.hash
                it[title] = profile.title
                it[about] = profile.about
            } get Profiles.id).value > 0
        }
    }

    suspend fun deleteProfile(id: Int): Boolean {
        return dbQuery {
            Profiles.deleteWhere { Profiles.id eq id } > 0
        }
    }

    private fun toProfile(row: ResultRow): Profile {
        println(row[Profiles.id])

        val profile = Profile(id = row[Profiles.id])
        profile.hash = row[Profiles.hash]
        profile.title = row[Profiles.title]
        profile.about = row[Profiles.about]

        return profile
    }
}