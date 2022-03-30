package com.thefolle.service

import com.thefolle.domain.Sheet
import com.thefolle.domain.Song
import com.thefolle.dto.SheetDto
import com.thefolle.repository.SheetRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class SheetService {

    @Autowired
    lateinit var sheetRepository: SheetRepository

    fun getSheet(sheetId: Long): SheetDto {
        return findByIdOrThrow(sheetId)
                .toDto()
    }

    private fun findByIdOrThrow(sheetId: Long): Sheet {
        return sheetRepository
                .findByIdOrNull(sheetId)
                ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "The sheet with id $sheetId does not exist.")
    }

}