package com.thefolle.domain

import com.thefolle.dto.SheetDto
import org.springframework.data.neo4j.core.schema.Id
import org.springframework.data.neo4j.core.schema.Node

@Node
class Sheet(
        @Id
        val id: Long,
        val pageNumber: Long
) {
        fun toDto() = SheetDto(
                id,
                pageNumber
        )
}