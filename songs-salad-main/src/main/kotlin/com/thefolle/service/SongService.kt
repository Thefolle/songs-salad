package com.thefolle.service

import com.thefolle.domain.Fragment
import com.thefolle.domain.Phase
import com.thefolle.domain.Sheet
import com.thefolle.domain.Song
import com.thefolle.dto.FragmentDto
import com.thefolle.dto.SongDto
import com.thefolle.repository.FragmentRepository
import com.thefolle.repository.SongRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class SongService {

    @Autowired
    lateinit var songRepository: SongRepository

    @Autowired
    lateinit var fragmentRepository: FragmentRepository

    fun addSong(songDto: SongDto): Long {

        val fragments = songDto.body.map { Fragment(it.id, it.position, it.text, it.isChorus) }.toSet()
        checkFragmentsIntegrity(fragments)

        return songRepository
                .save(
                        Song(
                                id = null,
                                text = songDto.text,
                                body = fragments,
                                title = songDto.title,
                                author = songDto.author,
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
        val song = findByIdOrThrow(songId)
        song.sheet = sheet
        songRepository
                .save(song)
    }

    fun addFragment(songId: Long, fragmentDto: FragmentDto) {
        val song = findByIdOrThrow(songId)
        val fragment = Fragment(null, fragmentDto.position, fragmentDto.text, fragmentDto.isChorus)
        song.body = song.body + fragment
        checkFragmentsIntegrity(song.body)

        songRepository
                .save(song)
    }

    fun updateFragment(songId: Long, position: Long, fragmentDto: FragmentDto) {
        val song = findByIdOrThrow(songId)
        if (!song.body.any { it.position == position }) throw ResponseStatusException(HttpStatus.NOT_FOUND, "No fragment with position $position exists!")
        song.body = song.body.filterNot { it.position == position }.plus(Fragment(null, position, fragmentDto.text, fragmentDto.isChorus)).toSet()
        checkFragmentsIntegrity(song.body)
        songRepository
                .save(song)
    }

    fun updateAuthor(songId: Long, author: String) {
        val song = findByIdOrThrow(songId)
        song.author = author
        songRepository
                .save(song)
    }

    fun getSongsContainingText(searchString: String, searchTitleMapped: String): List<SongDto> {
        return songRepository
                .getSongsContainingTextAndTitle(searchString, searchTitleMapped)
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
        } else if (fragments.count { it.isChorus } > 1) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "A song cannot contain more than one chorus!")
        }

        val sortedFragments = fragments
                .sortedBy { it.position }
        val nonStrictFragments = sortedFragments
                .filterIndexed { index, fragment -> fragment.position != index.toLong() }
        if (nonStrictFragments.isNotEmpty()) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Positions of fragments must be strictly incremental, starting from 0." +
                            "Please, redefine the fragment with position ${nonStrictFragments.first().position}")
        }
    }

}