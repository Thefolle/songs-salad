package com.thefolle.controller

import com.thefolle.domain.Phase
import com.thefolle.domain.Sheet
import com.thefolle.dto.FragmentDto
import com.thefolle.dto.SongDto
import com.thefolle.service.SongService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.hateoas.CollectionModel
import org.springframework.hateoas.LinkRelation
import org.springframework.hateoas.RepresentationModel
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/songs", consumes = ["application/vnd.api+json", "application/json"])
class SongController {

    @Autowired
    lateinit var songService: SongService

    @PostMapping("")
    fun addSong(@RequestBody songDto: SongDto): ResponseEntity<RepresentationModel<*>> {
        val songId = songService
                .addSong(songDto)

        val songLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(SongController::class.java).getSong(songId)).withSelfRel()

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                       RepresentationModel
                               .of(
                                       null,
                                       listOf(songLink)
                               )
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

    @PutMapping("/{id}/sheet")
    fun addSheet(@PathVariable("id") songId: Long, @RequestBody sheet: Sheet): ResponseEntity<Void> {
        songService
                .addSheet(songId, sheet)

        return ResponseEntity
                .ok(
                        null
                )
    }

    @PostMapping("/{id}/fragments")
    fun addFragment(@PathVariable("id") songId: Long, @RequestBody fragmentDto: FragmentDto): ResponseEntity<Void> {
        songService
                .addFragment(songId, fragmentDto)

        return ResponseEntity
                .ok(
                        null
                )
    }

    @PutMapping("/{id}/fragments/{position}")
    fun updateFragment(@PathVariable("id") songId: Long, @PathVariable("position") position: Long, @RequestBody fragmentDto: FragmentDto): ResponseEntity<Void> {
        if (position != fragmentDto.position) throw ResponseStatusException(HttpStatus.CONFLICT, "The parameter 'position' in the URI and the corresponding field in the request body must match!")

        songService
                .updateFragment(songId, position, fragmentDto)

        return ResponseEntity
                .ok(
                        null
                )
    }

    @PutMapping("/{id}/author")
    fun updateAuthor(@PathVariable("id") songId: Long, @RequestBody author: String): ResponseEntity<Void> {
        songService
                .updateAuthor(songId, author)

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
    fun getSongs(@RequestParam("searchInText", required = false) searchInText: String?, @RequestParam("searchInTitle", required = false) searchInTitle: String?, @RequestParam("searchPage", required = false) searchPhase: Phase.PhaseValue?): ResponseEntity<CollectionModel<SongDto>> {
        val searchInTextMapped = searchInText ?: ""
        val searchInTitleMapped = searchInTitle ?: ""

        val songs = songService.getSongsContainingText(searchInTextMapped, searchInTitleMapped, searchPhase)

        songs.forEach {
            it.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.javaClass).getSong(it.id!!.toLong())).withSelfRel())
        }

        return ResponseEntity
                .ok(
                        CollectionModel
                                .of(
                                        songs
                                )
                )
    }

    @CrossOrigin
    @GetMapping("/{id}", produces = ["application/vnd.api+json"])
    fun getSong(@PathVariable("id") songId: Long) : ResponseEntity<SongDto> {
        val song = songService
                .getSong(
                        songId
                )

        val link = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.javaClass).getSongs(null, null, null)).withRel("collection")
        val selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.javaClass).getSong(songId)).withSelfRel()
        song.add(selfLink)
        song.add(link)
        val sheetId = song.sheet?.id
        if (sheetId != null) {
            val sheetLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(SheetController::class.java).getSheet(sheetId)).withRel("sheet")
            song.add(sheetLink)
        }


        return ResponseEntity
                .ok(
                        song
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