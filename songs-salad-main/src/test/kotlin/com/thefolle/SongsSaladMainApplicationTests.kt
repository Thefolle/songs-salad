package com.thefolle

import com.thefolle.domain.Phase
import com.thefolle.dto.FragmentDto
import com.thefolle.dto.SongDto
import com.thefolle.service.SongService
import org.junit.jupiter.api.*
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.neo4j.core.Neo4jClient

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SongsSaladMainApplicationTests {

	@Autowired
	lateinit var songService: SongService

	@Autowired
	lateinit var neo4j: Neo4jClient

	@BeforeAll
	fun startupFixture() {
		val isEmpty = neo4j.query("match (n) return count(n)")
				.fetchAs(Long::class.java)
				.one()
				.get() == 0L

		if (!isEmpty) {
			fail { "The test database must be empty when the test suite starts!" }
		}
	}

	@AfterEach
	fun cleanDatabase() {
		neo4j.query("match (n) detach delete n")
				.fetchAs(Void::class.java)
				.first()
	}

	@Test
	fun getSongsWhenNoSongAndNoFilterShouldReturnEmptyList() {
		assert(songService.getSongsContainingText("", "", null).isEmpty())
	}

	@Test
	fun addSongWhenTwoFragmentsAndOnePhaseAndNoSheetShouldSucceed() {
		songService.addSong(
				SongDto(
						null,
						null,
						setOf(
								FragmentDto(
										null,
										0,
										"Acqua che scendi dai monti...",
										false
								),
								FragmentDto(
										null,
										1,
										"In mezzo alle nostre case...",
										true
								)
						),
						"Fratello torrente",
						null,
						setOf(
								Phase.PhaseValue.Enter
						),
						null
				)
		)

		// the request is enough as proof
	}

	@Test
	fun getSongsWhenOneSongAndNoFilterShouldReturnOneSong() {
		// setup
		val oracleSong = SongDto(
				null,
				null,
				setOf(
						FragmentDto(
								null,
								0,
								"Acqua che scendi dai monti...",
								false
						),
						FragmentDto(
								null,
								1,
								"In mezzo alle nostre case...",
								true
						)
				),
				"Fratello torrente",
				null,
				setOf(
						Phase.PhaseValue.Enter
				),
				null
		)

		val songId = songService.addSong(
				oracleSong
		)

		val song = songService
				.getSongsContainingText("", "", null)
				.first()

		assert(songId == song.id!!.toLong())
	}

	@Test
	fun getSongsWhenOneSongAndNonMatchingTextAndMatchingPhaseShouldReturnEmptyList() {
		// setup
		val oracleSong = SongDto(
				null,
				null,
				setOf(
						FragmentDto(
								null,
								0,
								"Acqua che scendi dai monti...",
								false
						),
						FragmentDto(
								null,
								1,
								"In mezzo alle nostre case...",
								true
						)
				),
				"Fratello torrente",
				null,
				setOf(
						Phase.PhaseValue.Enter
				),
				null
		)

		songService.addSong(
				oracleSong
		)

		// act
		val songs = songService
				.getSongsContainingText("pp17)", "", oracleSong.phases.first())

		assert(songs.isEmpty())
	}

	@Test
	fun getSongsWhenOneSongMatchingTextAndMatchingTitleAndMatchingPhaseShouldReturnOneSong() {
		// setup
		val oracleSong = SongDto(
				null,
				null,
				setOf(
						FragmentDto(
								null,
								0,
								"Acqua che scendi dai monti...",
								false
						),
						FragmentDto(
								null,
								1,
								"In mezzo alle nostre case...",
								true
						)
				),
				"Fratello torrente",
				null,
				setOf(
						Phase.PhaseValue.Enter
				),
				null
		)

		val songId = songService.addSong(
				oracleSong
		)

		// act
		val songs = songService
				.getSongsContainingText(oracleSong.body.first().text, oracleSong.title, oracleSong.phases.first())

		assert(songs.count() == 1)
		assert(songs.first().id!!.toLong() == songId)
	}

	@Test
	fun getSongsWhenMultipleSongsAndNoFilterShouldReturnAllSongs() {
		// setup
		val texts = setOf(
				setOf("Frutto della nostra terra...", "E sarò pane, e sarò vino..."),
				setOf("Pane di vita sei...", "Il tuo corpo ci sazierà..."),
				setOf("Santo santo santo"),
				setOf("Tantum ergo sacramentum...")
				)
		val titles = setOf(
				"Frutto della nostra terra",
				"Pane di vita",
				"Santo",
				"Tantum ergo"
		)
		val phases = setOf(
				Phase(Phase.PhaseValue.Communion),
				Phase(Phase.PhaseValue.Communion),
				Phase(Phase.PhaseValue.Saint),
				Phase(Phase.PhaseValue.Adoration)
		)
		val indexes = setOf(0, 1, 2, 3)
		val oracleSongs = indexes
				.map {
					SongDto(
							null,
							null,
							texts.elementAt(it).mapIndexed { index, text ->
													FragmentDto(
															null,
															index.toLong(),
															text,
															false
													)
							}.toSet(),
							titles.elementAt(it),
							null,
							setOf(
								phases.elementAt(it).phaseValue
							),
							null
					)
				}

		val songIds = oracleSongs
				.map {
					songService
							.addSong(it)
				}

		// act
		val songs = songService
				.getSongsContainingText("", "", null)


		assert(songIds.containsAll(songs.map { it.id!!.toLong() }) && songIds.count() == songs.count())
	}

	@Test
	fun getSongsWhenMultipleSongsAndMatchingTextShouldReturnSomeSongs() {
		// setup
		val texts = setOf(
				setOf("Frutto della nostra terra...", "E sarò pane, e sarò vino..."),
				setOf("Pane di vita sei...", "Il tuo corpo ci sazierà..."),
				setOf("Santo santo santo"),
				setOf("Tantum ergo sacramentum...")
		)
		val titles = setOf(
				"Frutto della nostra terra",
				"Pane di vita",
				"Santo",
				"Tantum ergo"
		)
		val phases = setOf(
				Phase(Phase.PhaseValue.Communion),
				Phase(Phase.PhaseValue.Communion),
				Phase(Phase.PhaseValue.Saint),
				Phase(Phase.PhaseValue.Adoration)
		)
		val indexes = setOf(0, 1, 2, 3)
		val oracleSongs = indexes
				.map {
					SongDto(
							null,
							null,
							texts.elementAt(it).mapIndexed { index, text ->
								FragmentDto(
										null,
										index.toLong(),
										text,
										false
								)
							}.toSet(),
							titles.elementAt(it),
							null,
							setOf(
									phases.elementAt(it).phaseValue
							),
							null
					)
				}

		val songIds = oracleSongs
				.map {
					songService
							.addSong(it)
				}

		// act
		val songs = songService
				.getSongsContainingText("pane", "", null)

		assert(songs.count() == 2)
	}

}
