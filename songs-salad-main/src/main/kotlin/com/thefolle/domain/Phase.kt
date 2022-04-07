package com.thefolle.domain

import org.springframework.data.neo4j.core.schema.Id
import org.springframework.data.neo4j.core.schema.Node

@Node
class Phase(
        @Id
        var phaseValue: PhaseValue
        ) {

    enum class PhaseValue {
        Enter,
        Glory,
        Psalm,
        Alleluia,
        Offertory,
        Saint,
        Communion,
        End,
        Baptism,
        Adoration
    }
}