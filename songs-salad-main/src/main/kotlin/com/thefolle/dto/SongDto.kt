package com.thefolle.dto

import com.thefolle.domain.Phase
import org.springframework.hateoas.RepresentationModel
import java.net.URI

class SongDto : RepresentationModel<SongDto> {

        var id: String?
        var text: String
        var title: String
        var phases: Set<Phase.PhaseValue>
        var sheet: Set<URI>

        constructor(
                    id: String?,
                    text: String,
                    title: String,
                    phases: Set<Phase.PhaseValue> = setOf(),
                    sheet: Set<URI> = setOf()
        ) {
                this.id = id
                this.text = text
                this.title = title
                this.phases = phases
                this.sheet = sheet
        }
}