package guuilp.github.com.traktstudy

import guuilp.github.com.traktstudy.data.Result
import guuilp.github.com.traktstudy.data.remote.entities.Ids
import guuilp.github.com.traktstudy.data.remote.entities.Show
import guuilp.github.com.traktstudy.data.remote.entities.TrendingShow
import guuilp.github.com.traktstudy.data.remote.TraktApiService
import guuilp.github.com.traktstudy.data.remote.TraktRemoteDataSource
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Assert
import org.junit.Test
import retrofit2.Response

class TraktRepositoryTest {
    private val service: TraktApiService = mockk()
    private val dataSource = TraktRemoteDataSource(service)
    private val errorResponseBody = ResponseBody.create(MediaType.parse(""), "Error")

    @Test
    fun getTrendingShows_withSuccess() = runBlocking {
        // Given that the service responds with success
        withSuccess(trendingShows)

        // When performing a search
        val response = dataSource.getTrendingShows()

        // Then the response is as expected
        Assert.assertNotNull(response)
        Assert.assertEquals(Result.Success(trendingShows), response)
    }

    @Test
    fun getTrendingShows_withError() = runBlocking {
        withError()

        val response = dataSource.getTrendingShows()

        Assert.assertTrue(response is Result.Error)
    }

    private fun withSuccess(trendingShows: List<TrendingShow>?) {
        val result = Response.success(trendingShows)
        every { service.getTrendingShows() } returns CompletableDeferred(result)
    }

    private fun withError() {
        val result = Response.error<List<TrendingShow>>(400, errorResponseBody)
        every { service.getTrendingShows() } returns CompletableDeferred(result)
    }
}

val trendingShows = listOf(
    TrendingShow(
        show = Show(
            ids = Ids(imdb = "1"),
            title = "Supernatural",
            year = 2013
        ),
        watchers = 10
    ),
    TrendingShow(
        show = Show(
            ids = Ids(imdb = "2"),
            title = "Sherlock",
            year = 2013
        ),
        watchers = 100
    ),
    TrendingShow(
        show = Show(
            ids = Ids(imdb = "3"),
            title = "The Sinner",
            year = 2013
        ),
        watchers = 1000
    )
)