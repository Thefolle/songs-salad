package com.thefolle.service

import com.thefolle.domain.Fragment
import com.thefolle.domain.Phase
import com.thefolle.domain.Sheet
import com.thefolle.domain.Song
import com.thefolle.dto.SongDto
import com.thefolle.repository.SongRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Example
import org.springframework.data.domain.ExampleMatcher
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.net.URI

@Service
class SongService {

    @Autowired
    lateinit var songRepository: SongRepository

    fun addSong(songDto: SongDto): Long {

        checkFragmentsIntegrity(songDto.body)

        return songRepository
                .save(
                        Song(
                                id = null,
                                text = songDto.text,
                                body = songDto.body,
                                title = songDto.title,
                                phases = songDto.phases.map { Phase(it) }.toSet(),
                                sheet = songDto.sheet
                        )
                )
                .id!!
    }

    private fun findByIdOrThrow(songId: Long): Song {
        return songRepository
                .findByIdOrNull(songId)
                ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "The song with id $songId does not exist.")
    }

    fun addPhase(songId: Long, phaseValue: Phase.PhaseValue) {
        var song = findByIdOrThrow(songId)
        song.phases = song.phases.plus(Phase(phaseValue))

        songRepository
                .save(song)
    }

    fun deletePhase(songId: Long, phaseValue: Phase.PhaseValue) {
        var song = findByIdOrThrow(songId)

        song.phases = song.phases.filterNot { it.phaseValue == phaseValue }.toSet()

        songRepository
                .save(song)
    }

    fun addSheet(songId: Long, sheet: Sheet) {
        var song = findByIdOrThrow(songId)
        song.sheet = sheet
        songRepository
                .save(song)
    }

    fun getSongsContainingText(searchString: String, searchTitleMapped: String): List<SongDto> {
        val exampleMatcher =
                ExampleMatcher
                        .matching()
                        .withMatcher("text", ExampleMatcher.GenericPropertyMatcher().contains().ignoreCase())
                        .withMatcher("title", ExampleMatcher.GenericPropertyMatcher().contains().ignoreCase())
                        .withIgnorePaths("id")

        val example = Example.of(Song(null, searchString, setOf(), searchTitleMapped, setOf(), null), exampleMatcher)
        return songRepository
                .findAll(example)
                .map { it.toDto() }
    }

    fun getSong(songId: Long): SongDto {
        return findByIdOrThrow(songId)
                .toDto()
    }

    fun deleteSongById(id: Long) {
        if (!songRepository.existsById(id)) {
            throw ResponseStatusException(
                    HttpStatus.NOT_FOUND
            )
        }

        songRepository
                .deleteById(id)
    }

    /***
     * Checks whether the fragments start from 0 up to fragments.length - 1
     */
    private fun checkFragmentsIntegrity(fragments: Set<Fragment>) {
        if (fragments.isEmpty()) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "A song has to contain at least one fragment!")
        } else if (fragments.any { it.position < 0 }) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "The position of a fragment cannot be negative!")
        }

        var sortedFragments = fragments
                .sortedBy { it.position }
        var nonStrictFragments = sortedFragments
                .filterIndexed { index, fragment -> fragment.position != index.toLong() }
        if (nonStrictFragments.isNotEmpty()) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Positions of fragments must be strictly incremental, starting from 0." +
                            "Please, redefine the fragment with position ${nonStrictFragments.first().position}")
        }
    }

}