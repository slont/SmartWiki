package net.maytry.www.smartwiki.enums

import net.maytry.www.smartwiki.R

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
        fun intToEnum(i: Int): GenreItemInfoType? = run {
            when (i) {
                 1 -> TEXT
                 2 -> TAG
                 3 -> PHOTO
                 4 -> MOVIE
                 5 -> TIME
                 6 -> MAP
                 7 -> RADIO_BTN
                 8 -> SEEK_BAR
                 9 -> RATING_BAR
                10 -> ORIGINAL
                else -> null
            }
        }

        fun strToEnum(s: String): GenreItemInfoType? = run {
            when (s) {
                TEXT.name -> TEXT
                TAG.name -> TAG
                PHOTO.name -> PHOTO
                MOVIE.name -> MOVIE
                TIME.name -> TIME
                MAP.name -> MAP
                RADIO_BTN.name -> RADIO_BTN
                SEEK_BAR.name -> SEEK_BAR
                RATING_BAR.name -> RATING_BAR
                ORIGINAL.name -> ORIGINAL
                else -> null
            }
        }

        fun getKey(type: GenreItemInfoType): Int = run {
            when (type) {
                TEXT -> R.string.genre_item_info_type_text
                TAG -> R.string.genre_item_info_type_tag
                PHOTO -> R.string.genre_item_info_type_photo
                MOVIE -> R.string.genre_item_info_type_movie
                TIME -> R.string.genre_item_info_type_time
                MAP -> R.string.genre_item_info_type_map
                RADIO_BTN -> R.string.genre_item_info_type_radio_btn
                SEEK_BAR -> R.string.genre_item_info_type_seek_bar
                RATING_BAR -> R.string.genre_item_info_type_rating_bar
                ORIGINAL -> R.string.genre_item_info_type_original
                else -> R.string.genre_item_info_type_photo
            }
        }

        fun getItemLayout(type: GenreItemInfoType): Int = run {
            when (type) {
                TEXT -> R.layout.genre_item_info_text
                TAG -> R.layout.genre_item_info_tag
                PHOTO -> R.layout.genre_item_info_photo
                MOVIE -> R.layout.genre_item_info_movie
                TIME -> R.layout.genre_item_info_time
                MAP -> R.layout.genre_item_info_map
                RADIO_BTN -> R.layout.genre_item_info_radio_btn
                SEEK_BAR -> R.layout.genre_item_info_seek_bar
                RATING_BAR -> R.layout.genre_item_info_rating_bar
                ORIGINAL -> R.layout.genre_item_info_original
                else -> R.layout.genre_item_info_common
            }
        }

        fun getEditLayout(type: GenreItemInfoType): Int = run {
            when (type) {
                TEXT -> R.layout.dialog_edit_genre_item_info_single
                TAG -> R.layout.dialog_edit_genre_item_info_multi
                PHOTO -> R.layout.dialog_edit_genre_item_info_single
                MOVIE -> R.layout.dialog_edit_genre_item_info_single
                TIME -> R.layout.dialog_edit_genre_item_info_single
                MAP -> R.layout.dialog_edit_genre_item_info_single
                RADIO_BTN -> R.layout.dialog_edit_genre_item_info_single
                SEEK_BAR -> R.layout.dialog_edit_genre_item_info_single
                RATING_BAR -> R.layout.dialog_edit_genre_item_info_single
                ORIGINAL -> R.layout.dialog_edit_genre_item_info_single
                else -> R.layout.dialog_edit_genre_item_info_single
            }
        }
    }
}