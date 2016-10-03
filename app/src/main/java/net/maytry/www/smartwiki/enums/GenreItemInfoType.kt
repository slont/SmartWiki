package net.maytry.www.smartwiki.enums

/**
 * Created by slont on 8/29/16.
 */
enum class GenreItemInfoType(val num: Int) {
    TEXT(1),
    TAG(2),
    PHOTO(3),
    MOVIE(4),
    TIME(5),
    MAP(6),
    RADIO_BTN(7),
    SEEK_BAR(8),
    RATING_BAR(9),
    ORIGINAL(10);

    companion object {
        fun intToEnum(i: Int): GenreItemInfoType? {
            when (i) {
                 1 -> return TEXT
                 2 -> return TAG
                 3 -> return PHOTO
                 4 -> return MOVIE
                 5 -> return TIME
                 6 -> return MAP
                 7 -> return RADIO_BTN
                 8 -> return SEEK_BAR
                 9 -> return RATING_BAR
                10 -> return ORIGINAL
                else -> return null
            }
        }

        fun strToEnum(s: String): GenreItemInfoType? {
            when (s) {
                TEXT.name -> return TEXT
                TAG.name -> return TAG
                PHOTO.name -> return PHOTO
                MOVIE.name -> return MOVIE
                TIME.name -> return TIME
                MAP.name -> return MAP
                RADIO_BTN.name -> return RADIO_BTN
                SEEK_BAR.name -> return SEEK_BAR
                RATING_BAR.name -> return RATING_BAR
                ORIGINAL.name -> return ORIGINAL
                else -> return null
            }
        }
    }
}