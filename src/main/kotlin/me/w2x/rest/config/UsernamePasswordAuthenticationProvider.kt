package me.w2x.rest.config

import me.w2x.rest.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Component

/**
 * Created by Charles Chen on 1/20/21.
 */
@Component
class UsernamePasswordAuthenticationProvider : AuthenticationProvider {

    @Autowired
    lateinit var userService: UserService

    override fun authenticate(authentication: Authentication): Authentication {
        val username = authentication.name
        val password = authentication.credentials.toString()
        val u = userService.auth(username, password)
        val authorities = u.roles.map { SimpleGrantedAuthority(it.authority) }

        return UsernamePasswordAuthenticationToken(
            username,
            password,
            authorities
        )
    }

    override fun supports(authentication: Class<*>): Boolean {
        return UsernamePasswordAuthentication::class.java.isAssignableFrom(authentication)
    }
}