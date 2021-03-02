package me.w2x.rest.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter

/**
 * Created by Charles Chen on 1/24/21.
 */
@Configuration
class SecurityConfig : WebSecurityConfigurerAdapter() {

    @Autowired
    lateinit var initialAuthenticationFilter: InitialAuthenticationFilter

    @Autowired
    lateinit var xAuthenticationFilter: XAuthenticationFilter

    @Autowired
    lateinit var usernamePasswordAuthenticationProvider: UsernamePasswordAuthenticationProvider

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.authenticationProvider(usernamePasswordAuthenticationProvider)
    }

    override fun configure(http: HttpSecurity) {
        http.csrf().disable()

        http.addFilterAt(initialAuthenticationFilter, BasicAuthenticationFilter::class.java)
            .addFilterAfter(xAuthenticationFilter, BasicAuthenticationFilter::class.java)

        http.authorizeRequests().anyRequest().authenticated()
    }

    @Bean
    override fun authenticationManager(): AuthenticationManager {
        return super.authenticationManager()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }
}