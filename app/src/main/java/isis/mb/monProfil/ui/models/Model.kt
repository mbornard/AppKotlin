package isis.mb.monProfil.ui.models

import java.math.BigInteger

data class Movies(
    val page: Int,
    val results: List<Movie>,
    val total_pages: Int,
    val total_results: Int
)

data class Movie(
    val adult: Boolean = false,
    val backdrop_path: String = "",
    val genre_ids: List<Int> = emptyList(),
    val id: Int = 0,
    val original_language: String = "",
    val original_title: String = "",
    val overview: String = "",
    val popularity: Double = 0.0,
    val poster_path: String = "",
    val release_date: String = "",
    val title: String = "",
    val video: Boolean = false,
    val vote_average: Double = 0.0,
    val vote_count: Int = 0
)


data class Persons(
    val page: Int,
    val results: List<Person>,
    val total_pages: Int,
    val total_results: Int
)

data class Person(
    val adult: Boolean = false,
    val also_known_as: List<String> = emptyList(),
    val biography: String = "",
    val birthday: String = "",
    val deathday: Any? = null,
    val gender: Int = 0,
    val homepage: Any? = null,
    val id: Int = 0,
    val imdb_id: String = "",
    val known_for_department: String = "",
    val name: String = "",
    val place_of_birth: String = "",
    val popularity: Double = 0.0,
    val profile_path: String = "",
    val credits: CreditsPerson = CreditsPerson()
)
data class CreditsPerson(
    val cast: List<CastPerson> = listOf()
)
data class CastPerson(
    val adult: Boolean = false,
    val backdrop_path: String = "",
    val character: String = "",
    val credit_id: String = "",
    val genre_ids: List<Int> = listOf(),
    val id: Int = 0,
    val order: Int = 0,
    val original_language: String = "",
    val original_title: String = "",
    val overview: String = "",
    val popularity: Double = 0.0,
    val poster_path: String = "",
    val release_date: String = "",
    val title: String = "",
    val video: Boolean = false,
    val vote_average: Double = 0.0,
    val vote_count: Int = 0
)

data class Tvs(
    val page: Int,
    val results: List<Tv>,
    val total_pages: Int,
    val total_results: Int
)
data class Tv(
    val adult: Boolean = false,
    val backdrop_path: String = "",
    val first_air_date: String = "",
    val genre_ids: List<Int> = emptyList(),
    val id: Int = 0,
    val media_type: String = "",
    val name: String = "",
    val origin_country: List<String> = emptyList(),
    val original_language: String = "",
    val original_name: String = "",
    val overview: String = "",
    val popularity: Double = 0.0,
    val poster_path: String = "",
    val vote_average: Double = 0.0,
    val vote_count: Int = 0
)

data class MovieWithCast(
    val adult: Boolean = false,
    val backdrop_path: String = "",
    val budget: Int = 0,
    val credits: Credits = Credits(),
    val homepage: String = "",
    val id: Int = 0,
    val original_language: String = "",
    val original_title: String = "",
    val overview: String = "",
    val popularity: Double = 0.0,
    val poster_path: String = "",
    val release_date: String = "",
    val runtime: Int = 0,
    val status: String = "",
    val tagline: String = "",
    val title: String = "",
    val vote_average: Double = 0.0,
    val vote_count: Int = 0
)


data class Credits(
    val cast: List<Cast> = listOf()
)

data class Cast(
    val adult: Boolean = false,
    val cast_id: Int = 0,
    val character: String = "",
    val credit_id: String = "",
    val gender: Int = 0,
    val id: Int = 0,
    val known_for_department: String = "",
    val name: String = "",
    val order: Int = 0,
    val original_name: String = "",
    val popularity: Double = 0.0,
    val profile_path: String = ""
)


data class TvWithCast(
    val adult: Boolean = false,
    val backdrop_path: String = "",
    val created_by: List<CreatedBy> = listOf(),
    val credits: CreditsTV = CreditsTV(),
    val episode_run_time: List<Int> = listOf(),
    val first_air_date: String = "",
    val homepage: String = "",
    val id: Int = 0,
    val in_production: Boolean = false,
    val languages: List<String> = listOf(),
    val last_air_date: String = "",
    val name: String = "",
    val number_of_episodes: Int = 0,
    val number_of_seasons: Int = 0,
    val origin_country: List<String> = listOf(),
    val original_language: String = "",
    val original_name: String = "",
    val overview: String = "",
    val popularity: Double = 0.0,
    val poster_path: String = "",
    val status: String = "",
    val tagline: String = "",
    val type: String = "",
    val vote_average: Double = 0.0,
    val vote_count: Int = 0
)

data class CreatedBy(
    val credit_id: String = "",
    val gender: Int = 0,
    val id: Int = 0,
    val name: String = "",
    val profile_path: String = ""
)

data class CreditsTV(
    val cast: List<CastTV> = listOf()
)

data class CastTV(
    val adult: Boolean = false,
    val character: String = "",
    val credit_id: String = "",
    val gender: Int = 0,
    val id: Int = 0,
    val known_for_department: String = "",
    val name: String = "",
    val order: Int = 0,
    val original_name: String = "",
    val popularity: Double = 0.0,
    val profile_path: String = ""
)



