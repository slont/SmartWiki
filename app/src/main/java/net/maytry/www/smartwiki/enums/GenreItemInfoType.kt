package net.maytry.www.smartwiki.enums

/**
 * Created by slont on 8/29/16.
 */
enum class GenreItemInfoType() {
    PHOTO,
    MOVIE,
    TIME,
    MAP,
    RADIO_BTN,
    SEEK_BAR,
    RATING_BAR,
    ORIGINAL;

    companion object {
        /**
         * TODO
         */
        fun intToEnum(int: Int): GenreItemInfoType {
            return GenreItemInfoType.TIME
        }

        fun strToEnum(str: String): GenreItemInfoType? {
            when (str) {
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