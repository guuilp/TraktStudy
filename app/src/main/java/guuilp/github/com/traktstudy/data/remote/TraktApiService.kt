package guuilp.github.com.traktstudy.data.remote

import guuilp.github.com.traktstudy.data.remote.entities.TrendingShow
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET

interface TraktApiService {

    @GET("shows/trending")
    fun getTrendingShows(): Deferred<Response<List<TrendingShow>>>
}