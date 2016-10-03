package net.maytry.www.smartwiki.model

import android.content.ContentValues
import android.databinding.BaseObservable
import java.io.Serializable

/**
 * Created by slont on 9/19/16.
 */
abstract class AbstractModel<T>(

        /**
         * ID
         */
        var id: Long? = null

) :  BaseObservable(), Serializable {
    abstract fun contentValues(): ContentValues
}
