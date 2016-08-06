package net.maytry.www.smartwiki.viewmodel

import android.content.Context
import android.util.AttributeSet
import android.widget.ListView
import net.maytry.www.smartwiki.model.Genre

/**
 * Created by slont on 8/7/16.
 */
class GenreListView : ListView {
//    var genre: Genre

    init {

    }

    constructor(ctx: Context, attrs: AttributeSet) : super(ctx, attrs)

    constructor(ctx: Context) : super(ctx)
}