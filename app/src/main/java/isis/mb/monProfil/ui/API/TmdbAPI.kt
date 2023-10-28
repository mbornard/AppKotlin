package isis.mb.monProfil.ui.API

import isis.mb.monProfil.ui.models.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbAPI {
    @GET("trending/movie/week")
    suspend fun lastMovies(@Query("api_key") api_key: String): Movies

    @GET("trending/tv/week")
    suspend fun lastTvs(@Query("api_key") api_key: String): Tvs

    @GET("trending/person/week")
    suspend fun lastPersons(@Query("api_key") api_key: String): Persons

    @GET("movie/{id}")
    suspend fun OneMovie(
        @Path("id") id: Int,
        @Query("api_key") api_key: String,
        @Query("language") language: String = "fr-FR",
        @Query("append_to_response") append_to_response: String = "credits"
    ): MovieWithCast


    @GET("tv/{id}")
    suspend fun OneTv(
        @Path("id") id: Int,
        @Query("api_key") api_key: String,
        @Query("language") language: String = "fr-FR",
        @Query("append_to_response") append_to_response: String = "credits"

    ): TvWithCast

    @GET("person/{id}")
    suspend fun OnePerson(
        @Path("id") id: Int,
        @Query("api_key") api_key: String,
        @Query("language") language: String = "fr-FR",

    ): Person

    @GET("search/movie")
    suspend fun SearchMovie(
        @Query("api_key") api_key: String,
        @Query("query") query: String,
        @Query("language") language: String = "fr-FR",

    ):Movies

    @GET("search/tv")
    suspend fun SearchTv(
        @Query("api_key") api_key: String,
        @Query("query") query: String,
        @Query("language") language: String = "fr-FR",

    ):Tvs

    @GET("search/person")
    suspend fun SearchPerson(
        @Query("api_key") api_key: String,
        @Query("query") query: String,
        @Query("language") language: String = "fr-FR",

    ):Persons
}

