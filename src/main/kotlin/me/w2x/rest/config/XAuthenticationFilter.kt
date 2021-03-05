package me.w2x.rest.config

import me.w2x.rest.service.SessionService
import me.w2x.rest.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.authentication.AnonymousAuthenticationToken
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

    @Autowired
    lateinit var userService: UserService

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val sessionId = request.getHeader("X-Auth-Token")

        if (sessionId == null && isAnonymous()) {
            filterChain.doFilter(request, response)
            return
        }

        if (sessionId == null) {
            throw AccessDeniedException("Token can't be null")
        }

        val session = sessionService.getSession(sessionId)
            ?: throw BadCredentialsException("Bad credentials.")
        val username =
            session.user?.username ?: throw BadCredentialsException("Username can't be null")
        val user = userService.getUser(username) ?: throw BadCredentialsException("User not found")
        val authorities = user.roles.map { SimpleGrantedAuthority(it.authority) }
        val auth = UsernamePasswordAuthentication(
            username,
            null,
            authorities
        )

        SecurityContextHolder.getContext().authentication = auth

        filterChain.doFilter(request, response)
    }

    override fun shouldNotFilter(request: HttpServletRequest): Boolean {
        return request.servletPath == "/login"
    }

    private fun isAnonymous(): Boolean {
        return SecurityContextHolder.getContext().authentication is AnonymousAuthenticationToken
    }
}