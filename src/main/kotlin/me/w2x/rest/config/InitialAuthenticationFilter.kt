package me.w2x.rest.config

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.nio.charset.StandardCharsets
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

    @Value("\${jwt.signing.key}")
    lateinit var signingKey: String

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val username = request.getHeader("username")
        val password = request.getHeader("password")
        val code = request.getHeader("code")

        if (code == null) {
            val a = UsernamePasswordAuthentication(username, password)

            manager.authenticate(a)
        } else {
            val a = OtpAuthentication(username, code)

            manager.authenticate(a)

            val key = Keys.hmacShaKeyFor(signingKey.toByteArray(StandardCharsets.UTF_8))
            val jwt = Jwts.builder().setClaims(mapOf("username" to username))
                .signWith(key)
                .compact()

            response.setHeader("Authorization", jwt)
        }
    }

    override fun shouldNotFilter(request: HttpServletRequest): Boolean {
        return request.servletPath != "/login"
    }
}