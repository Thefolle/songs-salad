package com.thefolle.controller

import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/songs")
class SheetController {

    @GetMapping("/{id}/sheet", produces = [MediaType.IMAGE_JPEG_VALUE])
    fun getSheet(@PathVariable("id") songId: Long): ResponseEntity<ByteArray> {
        val inputStream = javaClass.classLoader.getResourceAsStream("static/0/Turbellaria.jpg")
                ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "The sheet is not available yet.")

        return ResponseEntity
                .ok(
                        inputStream.readAllBytes()
                )
    }

}