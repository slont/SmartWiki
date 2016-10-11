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

        fun getKey(type: GenreItemInfoType): Int {
            when (type) {
                TEXT -> return R.string.genre_item_info_type_text
                TAG -> return R.string.genre_item_info_type_tag
                PHOTO -> return R.string.genre_item_info_type_photo
                MOVIE -> return R.string.genre_item_info_type_movie
                TIME -> return R.string.genre_item_info_type_time
                MAP -> return R.string.genre_item_info_type_map
                RADIO_BTN -> return R.string.genre_item_info_type_radio_btn
                SEEK_BAR -> return R.string.genre_item_info_type_seek_bar
                RATING_BAR -> return R.string.genre_item_info_type_rating_bar
                ORIGINAL -> return R.string.genre_item_info_type_original
                else -> return R.string.genre_item_info_type_photo
            }
        }

        fun getItemLayout(type: GenreItemInfoType): Int {
            when (type) {
                TEXT -> return R.layout.genre_item_info_list_item_text
                TAG -> return R.layout.genre_item_info_list_item_tag
                PHOTO -> return R.layout.genre_item_info_list_item_photo
                MOVIE -> return R.layout.genre_item_info_list_item_movie
                TIME -> return R.layout.genre_item_info_list_item_time
                MAP -> return R.layout.genre_item_info_list_item_map
                RADIO_BTN -> return R.layout.genre_item_info_list_item_radio_btn
                SEEK_BAR -> return R.layout.genre_item_info_list_item_seek_bar
                RATING_BAR -> return R.layout.genre_item_info_list_item_rating_bar
                ORIGINAL -> return R.layout.genre_item_info_list_item_original
                else -> return R.layout.genre_item_info_list_item_common
            }
        }

        fun getEditLayout(type: GenreItemInfoType): Int {
            when (type) {
                TEXT -> return R.layout.dialog_edit_genre_item_info_single
                TAG -> return R.layout.dialog_edit_genre_item_info_multi
                PHOTO -> return R.layout.dialog_edit_genre_item_info_single
                MOVIE -> return R.layout.dialog_edit_genre_item_info_single
                TIME -> return R.layout.dialog_edit_genre_item_info_single
                MAP -> return R.layout.dialog_edit_genre_item_info_single
                RADIO_BTN -> return R.layout.dialog_edit_genre_item_info_single
                SEEK_BAR -> return R.layout.dialog_edit_genre_item_info_single
                RATING_BAR -> return R.layout.dialog_edit_genre_item_info_single
                ORIGINAL -> return R.layout.dialog_edit_genre_item_info_single
                else -> return R.layout.dialog_edit_genre_item_info_single
            }
        }
    }
}