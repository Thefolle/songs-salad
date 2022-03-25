package com.thefolle.domain

import org.springframework.data.neo4j.core.schema.GeneratedValue
import org.springframework.data.neo4j.core.schema.Id
import org.springframework.data.neo4j.core.schema.Node

@Node
class Fragment(
        @Id
        @GeneratedValue
        var id: Long?,
        var position: Long,
        var text: String,
        var isChorus: Boolean
)