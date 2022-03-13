package com.thefolle.service

import com.thefolle.domain.Song
import com.thefolle.dto.SongDto
import com.thefolle.repository.SongRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Example
import org.springframework.data.domain.ExampleMatcher
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class SongService {

    @Autowired
    lateinit var songRepository: SongRepository

    fun addSong(songDto: SongDto): Long {
        return songRepository
                .save(
                        Song(
                                id = null,
                                text = songDto.text,
                                title = songDto.title
                        )
                )
                .id!!
    }

    fun getSongsContainingText(searchString: String, searchTitleMapped: String): List<SongDto> {
        val exampleMatcher =
                ExampleMatcher
                        .matching()
                        .withMatcher("text", ExampleMatcher.GenericPropertyMatcher().contains().ignoreCase())
                        .withMatcher("title", ExampleMatcher.GenericPropertyMatcher().contains().ignoreCase())
                        .withIgnorePaths("id")

        val example = Example.of(Song(null, searchString, searchTitleMapped), exampleMatcher)
        return songRepository
                .findAll(example)
                .map { it.toDto() }
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

}