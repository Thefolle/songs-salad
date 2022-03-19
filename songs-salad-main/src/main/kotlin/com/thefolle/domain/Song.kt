package com.thefolle.domain

import com.thefolle.dto.SongDto
import org.springframework.data.neo4j.core.schema.GeneratedValue
import org.springframework.data.neo4j.core.schema.Id
import org.springframework.data.neo4j.core.schema.Node
import java.net.URI
import java.net.URL

@Node
class Song(
        @Id
        @GeneratedValue
        var id: Long?,
        val text: String,
        val title: String,
        var phases: Set<Phase>,
        var sheet: Set<URI>
) {
        fun toDto() = SongDto(
                id.toString(),
                text,
                title,
                phases.map { it.phaseValue }.toSet(),
                sheet
        )
}