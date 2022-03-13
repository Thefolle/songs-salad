package com.thefolle.domain

import com.thefolle.dto.SongDto
import org.springframework.data.neo4j.core.schema.GeneratedValue
import org.springframework.data.neo4j.core.schema.Id
import org.springframework.data.neo4j.core.schema.Node

@Node
class Song(
        @Id
        @GeneratedValue
        var id: Long?,
        val text: String,
        val title: String
) {
        fun toDto() = SongDto(
                id.toString(),
                text,
                title
        )
}