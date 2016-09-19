package net.maytry.www.smartwiki.model

import android.content.ContentValues
import java.io.Serializable

/**
 * Created by slont on 9/19/16.
 */
abstract class AbstractModel<T>() : Serializable {
    abstract fun contentValues(): ContentValues
}
