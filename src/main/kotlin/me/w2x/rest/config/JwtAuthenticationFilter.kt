package me.w2x.rest.config

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.nio.charset.StandardCharsets
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Created by Charles Chen on 1/24/21.
 */
@Component
class JwtAuthenticationFilter : OncePerRequestFilter() {

    @Value("\${jwt.signing.key}")
    lateinit var signingKey: String

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val jwt = request.getHeader("Authorization")
        val key = Keys.hmacShaKeyFor(signingKey.toByteArray(StandardCharsets.UTF_8))
        val claims = Jwts.parserBuilder().setSigningKey(key).build()
            .parseClaimsJws(jwt)
            .body
        val username = claims.get("username").toString()
        val authority = SimpleGrantedAuthority("username")

        val auth = UsernamePasswordAuthentication(
            username,
            null,
            listOf(authority)
        )

        SecurityContextHolder.getContext().authentication = auth

        filterChain.doFilter(request, response)
    }

    override fun shouldNotFilter(request: HttpServletRequest): Boolean {
        return request.servletPath == "/login"
    }
}