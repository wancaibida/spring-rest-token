package me.w2x.rest.controller

import me.w2x.rest.enu.RoleTypes
import org.springframework.http.ResponseEntity
import org.springframework.security.access.annotation.Secured
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping

/**
 * Created by Charles Chen on 2021/03/03/.
 */
@Controller
class SecuredController {

    @Secured(RoleTypes.Constants.ROLE_ADMIN)
    @RequestMapping("admin")
    fun admin(): ResponseEntity<String> {
        return ResponseEntity.ok("admin")
    }

    @Secured(RoleTypes.Constants.ROLE_USER)
    @RequestMapping("user")
    fun user(): ResponseEntity<Any?> {
        return ResponseEntity.ok("user")
    }
}