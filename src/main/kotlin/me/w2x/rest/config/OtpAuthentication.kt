package me.w2x.rest.config

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.GrantedAuthority

/**
 * Created by Charles Chen on 1/20/21.
 */
class OtpAuthentication : UsernamePasswordAuthenticationToken {

    constructor(principal: Any?, credentials: Any?) : super(principal, credentials) {}
    constructor(
        principal: Any?,
        credentials: Any?,
        authorities: Collection<GrantedAuthority?>?
    ) : super(principal, credentials, authorities)
}