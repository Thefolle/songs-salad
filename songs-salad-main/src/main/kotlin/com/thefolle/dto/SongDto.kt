package com.thefolle.dto

import com.thefolle.domain.Fragment
import com.thefolle.domain.Phase
import com.thefolle.domain.Sheet
import org.springframework.hateoas.RepresentationModel
import java.net.URI

class SongDto(
        var id: String?,
        var text: String,
        var body: Set<Fragment>,
        var title: String,
        var phases: Set<Phase.PhaseValue> = setOf(),
        var sheet: Sheet?)
    : RepresentationModel<SongDto>() {

}