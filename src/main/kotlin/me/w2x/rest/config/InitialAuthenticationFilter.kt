package me.w2x.rest.config

import me.w2x.rest.service.SessionService
import me.w2x.rest.util.JSON
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
        val username = request.getParameter("username")
        val password = request.getParameter("password")

        val result: Authentication =
            manager.authenticate(UsernamePasswordAuthentication(username, password))

        if (result.isAuthenticated) {
            val session = sessionService.createSession(username)
            val body = mapOf("token" to session.id)

            response.contentType = "application/json"
            response.outputStream.write(JSON.toJson(body).encodeToByteArray())
        }
    }

    override fun shouldNotFilter(request: HttpServletRequest): Boolean {
        return request.servletPath != "/login"
    }
}