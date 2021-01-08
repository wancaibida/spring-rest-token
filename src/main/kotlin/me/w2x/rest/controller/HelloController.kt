package me.w2x.rest.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

/**
 * Created by Charles Chen on 1/8/21.
 */
@RestController
class HelloController {

    @GetMapping("hello")
    fun hello(): String {
        return "hello"
    }
}