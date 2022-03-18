package com.thefolle.controller

import com.thefolle.domain.Phase
import com.thefolle.dto.SongDto
import com.thefolle.service.SongService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.hateoas.CollectionModel
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/songs", consumes = ["application/vnd.api+json", "application/json"])
class SongController {

    @Autowired
    lateinit var songService: SongService

    @PostMapping("")
    fun addSong(@RequestBody songDto: SongDto): ResponseEntity<Long> {
        return ResponseEntity
                .ok(
                        songService
                                .addSong(songDto)
                )

    }

    @PostMapping("/{id}/phases")
    fun addPhase(@PathVariable("id") songId: Long, @RequestBody phase: Phase.PhaseValue): ResponseEntity<Void> {
        songService
                .addPhase(
                        songId,
                        phase
                )

        return ResponseEntity
                .ok(
                        null
                )
    }

    @DeleteMapping("/{id}/phases")
    fun deletePhase(@PathVariable("id") songId: Long, @RequestBody phase: Phase.PhaseValue): ResponseEntity<Void> {
        songService
                .deletePhase(songId, phase)

        return ResponseEntity
                .ok(
                        null
                )
    }



    /**
     * Useful to either:
     * - search a song by theme or tag: Masses often turn around a keyword;
     * - search a song whose title you don't remember at the moment
     */
    @CrossOrigin
    @GetMapping("", produces = ["application/vnd.api+json"])
    fun getSongsContainingText(@RequestParam("searchInText", required = false) searchInText: String?, @RequestParam("searchInTitle", required = false) searchInTitle: String?): ResponseEntity<CollectionModel<SongDto>> {
        var searchInTextMapped = searchInText ?: ""
        var searchInTitleMapped = searchInTitle ?: ""

        var songs = songService.getSongsContainingText(searchInTextMapped, searchInTitleMapped)

        return ResponseEntity
                .ok(
                        CollectionModel
                                .of(
                                        songs
                                )
                )
    }

    @DeleteMapping("/{id}")
    fun deleteSongById(@PathVariable("id") id: Long): ResponseEntity<Unit> {
        songService
                .deleteSongById(id)

        return ResponseEntity
                .ok()
                .build()
    }
}