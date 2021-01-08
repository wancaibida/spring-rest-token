package me.w2x.rest.config

import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.password.NoOpPasswordEncoder

/**
 * Created by Charles Chen on 1/8/21.
 */
@Configuration
class ProjectConfig : WebSecurityConfigurerAdapter() {

    override fun configure(auth: AuthenticationManagerBuilder) {

        auth.inMemoryAuthentication()
            .withUser("admin")
            .password("admin")
            .authorities("ROLE_ADMIN")
            .and()
            .passwordEncoder(NoOpPasswordEncoder.getInstance())
    }

    override fun configure(http: HttpSecurity) {
        http.httpBasic()
        http.authorizeRequests().anyRequest().authenticated()
    }
}