package com.thesomeshkumar.flickophile.data.datasource.remote

import com.thesomeshkumar.flickophile.data.model.MovieDTO
import com.thesomeshkumar.flickophile.data.model.MovieDetailsDTO
import com.thesomeshkumar.flickophile.data.model.TVShowDTO
import com.thesomeshkumar.flickophile.data.model.TvShowDetailsDTO
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    /*-- Movie APIs-- */

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(@Query("page") page: Int = 0): MovieDTO

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(@Query("page") page: Int = 0): MovieDTO

    @GET("movie/popular")
    suspend fun getPopularMovies(@Query("page") page: Int = 0): MovieDTO

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(@Query("page") page: Int = 0): MovieDTO

    @GET("movie/{movie_id}")
    suspend fun getMoviesDetails(
        @Path("movie_id") movieId: Int
    ): MovieDetailsDTO

    /*-- Tv Show APIs-- */

    @GET("tv/airing_today")
    suspend fun getAiringTodayTvShows(@Query("page") page: Int = 0): TVShowDTO

    @GET("tv/popular")
    suspend fun getPopularTvShows(@Query("page") page: Int = 0): TVShowDTO

    @GET("tv/top_rated")
    suspend fun getTopRatedTvShows(@Query("page") page: Int = 0): TVShowDTO

    @GET("tv/{show_id}")
    suspend fun getTvShowDetails(
        @Path("show_id") showId: Int
    ): TvShowDetailsDTO
}
