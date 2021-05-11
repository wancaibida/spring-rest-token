package me.w2x.rest.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AnonymousAuthenticationProvider
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.access.ExceptionTranslationFilter
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource


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

    private final val anonymousKey = "anonymous"

    val anonymousAuthenticationProvider = AnonymousAuthenticationProvider(anonymousKey)

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.authenticationProvider(usernamePasswordAuthenticationProvider)
    }

    override fun configure(http: HttpSecurity) {
        http.csrf().disable()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

        http.anonymous()
            .authenticationFilter(AnonymousAuthenticationFilter(anonymousKey))
            .authenticationProvider(anonymousAuthenticationProvider)

        http.addFilterAfter(xAuthenticationFilter, ExceptionTranslationFilter::class.java)
            .addFilterAfter(initialAuthenticationFilter, ExceptionTranslationFilter::class.java)

        http.cors(Customizer.withDefaults())
        http.requestMatchers().anyRequest()
            .and().authorizeRequests()
            .antMatchers("/").permitAll()
            .antMatchers("/hello").permitAll()
            .anyRequest().authenticated()
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration().apply {
            allowedOrigins = listOf("*")
            allowedMethods = listOf("*")
        }

        return UrlBasedCorsConfigurationSource().apply {
            registerCorsConfiguration("/**", configuration)
        }
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