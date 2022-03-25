package com.thefolle.controller

import com.thefolle.domain.Sheet
import com.thefolle.service.SheetService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/sheets")
class SheetController {

    @Autowired
    lateinit var sheetService: SheetService

    @CrossOrigin
    @GetMapping("/{id}/pages/{pageId}", produces = [MediaType.IMAGE_JPEG_VALUE])
    fun getPage(@PathVariable("id") sheetId: Long, @PathVariable("pageId") page: Long): ResponseEntity<ByteArray> {
        val inputStream = javaClass.classLoader.getResourceAsStream("static/$sheetId/Turbellaria.jpg")
                ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "The sheet is not available yet.")

        return ResponseEntity
                .ok(
                        inputStream.readAllBytes()
                )
    }

    @CrossOrigin
    @GetMapping("/{id}", produces = ["application/vnd.api+json"])
    fun getSheet(@PathVariable("id") sheetId: Long): ResponseEntity<Sheet> {
        return ResponseEntity
                .ok(
                        sheetService
                                .getSheet(
                                        sheetId
                                )
                )
    }

}