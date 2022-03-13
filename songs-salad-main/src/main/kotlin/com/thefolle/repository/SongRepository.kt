package com.thefolle.repository

import com.thefolle.domain.Song
import org.springframework.data.neo4j.repository.Neo4jRepository
import org.springframework.data.neo4j.repository.query.Query
import org.springframework.stereotype.Repository

@Repository
interface SongRepository : Neo4jRepository<Song, Long> {
}