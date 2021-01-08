package me.w2x.rest.config

import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Component

/**
 * Created by Charles Chen on 1/8/21.
 */
@Component
class UsernamePasswordAuthenticationProvider : AuthenticationProvider {

    override fun authenticate(authentication: Authentication): Authentication {
        val username = authentication.name
        val password = authentication.credentials.toString()

        if ("admin" == username && "admin" == password) {
            return UsernamePasswordAuthenticationToken(
                username,
                password,
                listOf(SimpleGrantedAuthority("ROLE_ADMIN"))
            )
        } else {
            throw AuthenticationCredentialsNotFoundException("Error")
        }
    }

    override fun supports(authentication: Class<*>): Boolean {
        return UsernamePasswordAuthenticationToken::class.java.isAssignableFrom(authentication)
    }
}