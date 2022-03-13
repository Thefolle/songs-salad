package com.thefolle

import com.fasterxml.jackson.databind.MapperFeature
import com.fasterxml.jackson.databind.SerializationFeature
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SongsSaladMainApplication

fun main(args: Array<String>) {
	runApplication<SongsSaladMainApplication>(*args)
}
