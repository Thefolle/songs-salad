package com.thefolle.repository

import com.thefolle.domain.Fragment
import com.thefolle.domain.Song
import org.springframework.data.neo4j.repository.Neo4jRepository
import org.springframework.data.neo4j.repository.query.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface SongRepository : Neo4jRepository<Song, Long> {

    @Query("match (n:Song)-[b:BODY]->(f:Fragment)\n" +
            "return [rels = (n)-[otherR]-(otherN) where toLower(f.text) contains toLower(\$text) and toLower(n.title) contains toLower(\$title) | rels]")
    fun getSongsContainingTextAndTitle(@Param("text") text: String, @Param("title") title: String): Set<Song>

}