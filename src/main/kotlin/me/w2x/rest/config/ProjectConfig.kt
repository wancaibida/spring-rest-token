package me.w2x.rest.config

import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter

/**
 * Created by Charles Chen on 1/8/21.
 */
@Configuration
class ProjectConfig(
    val usernamePasswordAuthenticationProvider: UsernamePasswordAuthenticationProvider
) : WebSecurityConfigurerAdapter() {

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.authenticationProvider(usernamePasswordAuthenticationProvider)
    }

    override fun configure(http: HttpSecurity) {
        http.httpBasic()
        http.authorizeRequests().anyRequest().authenticated()
    }
}