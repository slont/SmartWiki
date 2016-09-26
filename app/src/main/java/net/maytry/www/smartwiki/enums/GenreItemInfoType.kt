package net.maytry.www.smartwiki.enums

/**
 * Created by slont on 8/29/16.
 */
enum class GenreItemInfoType(val num: Int) {
    PHOTO(1),
    MOVIE(2),
    TIME(3),
    MAP(4),
    RADIO_BTN(5),
    SEEK_BAR(6),
    RATING_BAR(7),
    ORIGINAL(8);

    companion object {
        fun intToEnum(i: Int): GenreItemInfoType? {
            when (i) {
                1 -> return PHOTO
                2 -> return MOVIE
                3 -> return TIME
                4 -> return MAP
                5 -> return RADIO_BTN
                6 -> return SEEK_BAR
                7 -> return RATING_BAR
                8 -> return ORIGINAL
                else -> return null
            }
        }

        fun strToEnum(s: String): GenreItemInfoType? {
            when (s) {
                GenreItemInfoType.PHOTO.name -> return PHOTO
                GenreItemInfoType.MOVIE.name -> return MOVIE
                GenreItemInfoType.TIME.name -> return TIME
                GenreItemInfoType.MAP.name -> return MAP
                GenreItemInfoType.RADIO_BTN.name -> return RADIO_BTN
                GenreItemInfoType.SEEK_BAR.name -> return SEEK_BAR
                GenreItemInfoType.RATING_BAR.name -> return RATING_BAR
                GenreItemInfoType.ORIGINAL.name -> return ORIGINAL
                else -> return null
            }
        }
    }
}