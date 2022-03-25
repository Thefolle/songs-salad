package com.thefolle.controller

import org.springframework.hateoas.CollectionModel
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.Link
import org.springframework.hateoas.LinkRelation
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
class HomeController {

    @GetMapping("")
    fun home(): ResponseEntity<CollectionModel<Set<*>>> {
        return ResponseEntity
                .ok(
                        CollectionModel
                                .empty(
                                        Link.of("http://localhost:8081/songs", LinkRelation.of("songs"))
                                )

                )
    }

}