package com.thefolle.controller

import org.springframework.hateoas.CollectionModel
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HomeController {

    @GetMapping("")
    fun home(): ResponseEntity<CollectionModel<Set<*>>> {
        val link = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(SongController::class.java).getSongs(null, null)).withRel("songs")

        return ResponseEntity
                .ok(
                        CollectionModel
                                .empty(
                                        link
                                )

                )
    }

}