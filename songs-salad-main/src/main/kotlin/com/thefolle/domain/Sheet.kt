package com.thefolle.domain

import org.springframework.data.neo4j.core.schema.Id
import org.springframework.data.neo4j.core.schema.Node

@Node
class Sheet(
        @Id
        val id: Long,
        val pageNumber: Long
)