package me.w2x.rest.config

import me.w2x.rest.entity.Otp
import me.w2x.rest.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component

/**
 * Created by Charles Chen on 1/20/21.
 */
@Component
class OtpAuthenticationProvider : AuthenticationProvider {

    @Autowired
    lateinit var userService: UserService

    override fun authenticate(authentication: Authentication): Authentication {
        val username = authentication.name
        val code = authentication.credentials.toString()

        val otp = Otp().apply {
            this.username = username
            this.code = code
        }

        val result: Boolean = userService.check(otp)

        return if (result) {
            OtpAuthentication(username, code)
        } else {
            throw BadCredentialsException("Bad credentials.")
        }
    }

    override fun supports(authentication: Class<*>): Boolean {
        return OtpAuthentication::class.java.isAssignableFrom(authentication)
    }
}