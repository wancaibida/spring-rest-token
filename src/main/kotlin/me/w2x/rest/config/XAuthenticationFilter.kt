package me.w2x.rest.config

import me.w2x.rest.service.SessionService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Created by Charles Chen on 1/24/21.
 */
@Component
class XAuthenticationFilter : OncePerRequestFilter() {

    @Autowired
    lateinit var sessionService: SessionService

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val sessionId = request.getHeader("X-Auth-Token")
            ?: throw AccessDeniedException("Token can't be null")

        val session = sessionService.getSession(sessionId)
            ?: throw BadCredentialsException("Bad credentials.")
        val auth = UsernamePasswordAuthentication(
            session.user?.username,
            null,
            listOf(SimpleGrantedAuthority("ROLE_USER"))
        )

        SecurityContextHolder.getContext().authentication = auth

        filterChain.doFilter(request, response)
    }

    override fun shouldNotFilter(request: HttpServletRequest): Boolean {
        return request.servletPath == "/login"
    }
}