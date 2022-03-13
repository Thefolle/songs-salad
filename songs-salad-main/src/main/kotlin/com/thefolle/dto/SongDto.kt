package com.thefolle.dto

import org.springframework.hateoas.RepresentationModel

class SongDto : RepresentationModel<SongDto> {

        var id: String?
        var text: String
        var title: String

        constructor(
                    id: String?,
                    text: String,
                    title: String
        ) {
                this.id = id
                this.text = text
                this.title = title
        }
}