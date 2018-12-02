package guuilp.github.com.traktstudy.data.remote

import guuilp.github.com.traktstudy.data.Result
import guuilp.github.com.traktstudy.data.remote.entities.TrendingShow
import guuilp.github.com.traktstudy.utils.safeApiCall
import java.io.IOException

class TraktRemoteDataSource(private val service: TraktApiService) {
    suspend fun getTrendingShows() = safeApiCall(
        call = { requestSearch() },
        errorMessage = "Error getting Trakt data"
    )

    private suspend fun requestSearch(): Result<List<TrendingShow>> {
        val response = service.getTrendingShows().await()
        if (response.isSuccessful) {
            val body = response.body()
            if (body != null) {
                return Result.Success(body)
            }
        }
        return Result.Error(
            IOException("Error getting Trakt data ${response.code()} ${response.message()}")
        )
    }
}