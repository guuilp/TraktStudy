package guuilp.github.com.traktstudy

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import guuilp.github.com.traktstudy.data.local.TraktDAO
import guuilp.github.com.traktstudy.data.local.TraktDatabase
import guuilp.github.com.traktstudy.data.local.entities.Ids
import guuilp.github.com.traktstudy.data.local.entities.Show
import org.junit.*

class TraktDAOTest {
    private lateinit var database: TraktDatabase
    private lateinit var traktDAO: TraktDAO
    private lateinit var show: Show

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        val context = InstrumentationRegistry.getInstrumentation().context
        database = Room.inMemoryDatabaseBuilder(context, TraktDatabase::class.java).build()
        traktDAO = database.traktDAO()
        show = Show(
            ids = Ids(imdb = "2"),
            title = "Sherlock",
            year = 2013,
            trending = 1
        )
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertAndGetLoggedInUser() {
        // Given a LoggedInUser that has been inserted into the DB
        traktDAO.saveShow(show)

        // When getting the LoggedInUser via the DAO
        val trendingShow = traktDAO.findTrendingShows()

        // Then the retrieved LoggedInUser matches the original LoggedInUser object
        Assert.assertEquals(trendingShow, show)
    }
}