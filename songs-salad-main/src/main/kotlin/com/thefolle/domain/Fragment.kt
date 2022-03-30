package com.thefolle.domain

import com.thefolle.dto.FragmentDto
import org.springframework.data.neo4j.core.schema.GeneratedValue
import org.springframework.data.neo4j.core.schema.Id
import org.springframework.data.neo4j.core.schema.Node
import org.springframework.data.neo4j.core.schema.Relationship

@Node
class Fragment(
        @Id
        @GeneratedValue
        var id: Long?,
        // the structural position of the fragment, not about the execution order
        var position: Long,
        var text: String,
        var isChorus: Boolean
) {
        fun toDto() = FragmentDto(
                id,
                position,
                text,
                isChorus
        )
}