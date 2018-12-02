package guuilp.github.com.traktstudy.data.local

import guuilp.github.com.traktstudy.data.local.entities.Show
import guuilp.github.com.traktstudy.data.remote.entities.TrendingShow

class TraktLocalDataSource(val traktDAO: TraktDAO){

    fun saveShow(shows: List<Show>){
        traktDAO.saveShows(shows)
    }

    fun getShows(): List<TrendingShow> {
        return traktDAO.findAllTrendingShows()
    }
}