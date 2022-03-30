package com.thefolle.repository

import com.thefolle.domain.Fragment
import org.springframework.data.neo4j.repository.Neo4jRepository
import org.springframework.data.neo4j.repository.query.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface FragmentRepository : Neo4jRepository<Fragment, Long> {

}