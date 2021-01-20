package me.w2x.rest.repository

import me.w2x.rest.entity.User
import org.springframework.data.repository.CrudRepository

/**
 * Created by Charles Chen on 1/20/21.
 */
interface UserRepository : CrudRepository<User, String> {

    fun findByUsername(username: String): User?
}