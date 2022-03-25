package com.thefolle.repository

import com.thefolle.domain.Sheet
import org.springframework.data.neo4j.repository.Neo4jRepository
import org.springframework.stereotype.Repository

@Repository
interface SheetRepository : Neo4jRepository<Sheet, Long> {
}