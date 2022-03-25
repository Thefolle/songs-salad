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
        @Deprecated("The text property is going to be removed in favor of verses and chorus.")
        val text: String,
        var body: Set<Fragment>,
        val title: String,
        var phases: Set<Phase>,
        var sheet: Sheet? = null
) {
        fun toDto() = SongDto(
                id.toString(),
                text,
                body,
                title,
                phases.map { it.phaseValue }.toSet(),
                sheet
        )
}