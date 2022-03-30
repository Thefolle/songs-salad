package com.thefolle.dto

import org.springframework.hateoas.RepresentationModel

class SheetDto  (
            val id: Long,
            val pageNumber: Long
        ) : RepresentationModel<SheetDto>()