package me.w2x.rest.repository

import me.w2x.rest.entity.Role
import org.springframework.data.jpa.repository.JpaRepository

/**
 * Created by Charles Chen on 2021/03/03/.
 */
interface RoleRepository : JpaRepository<Role, Long> {

    fun existsByAuthority(authority: String): Boolean

    fun findByAuthority(authority: String):Role?
}