package me.w2x.rest.config

import me.w2x.rest.service.SessionService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Created by Charles Chen on 1/20/21.
 */
@Component
class InitialAuthenticationFilter : OncePerRequestFilter() {

    @Autowired
    lateinit var manager: AuthenticationManager

    @Autowired
    lateinit var sessionService: SessionService

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val username = request.getHeader("username")
        val password = request.getHeader("password")
        val result: Authentication =
            manager.authenticate(UsernamePasswordAuthentication(username, password))

        if (result.isAuthenticated) {
            val session = sessionService.createSession(username)

            response.setHeader("X-Auth-Token", session.id)
        }
    }

    override fun shouldNotFilter(request: HttpServletRequest): Boolean {
        return request.servletPath != "/login"
    }
}