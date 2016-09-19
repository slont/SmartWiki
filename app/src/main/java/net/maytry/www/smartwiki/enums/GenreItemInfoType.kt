package net.maytry.www.smartwiki.enums

/**
 * Created by slont on 8/29/16.
 */
enum class GenreItemInfoType {
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
    }
}