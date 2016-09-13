package net.maytry.www.smartwiki

import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.AdapterView
import android.widget.EditText
import net.maytry.www.smartwiki.databinding.ActivityEditGenreItemBinding
import net.maytry.www.smartwiki.enums.EditType
import net.maytry.www.smartwiki.fragment.EditGenreItemContentFragment
import net.maytry.www.smartwiki.model.GenreItem

/**
 * Created by slont on 8/15/16.
 *
 * Genre item display activity.
 */
class EditGenreItemActivity : AppCompatActivity(), EditGenreItemContentFragment.OnFragmentInteractionListener {

    private var mItem: GenreItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityEditGenreItemBinding>(this@EditGenreItemActivity, R.layout.activity_edit_genre_item)

        mItem = intent.getSerializableExtra("item") as GenreItem
        val type = intent.getSerializableExtra("type") as EditType

        val fragment = EditGenreItemContentFragment.newInstance(mItem!!, type!!)
        supportFragmentManager.beginTransaction().add(R.id.content_edit_genre_item, fragment).commit()
    }

    override fun onClickGenreItemListItem(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
//        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onClickCancelButton(v: View) {
        setResult(RESULT_CANCELED, Intent())
        finish()
    }

    override fun onClickCreateButton(v: View) {
        val name = (findViewById(R.id.item_name_edit) as EditText).text.toString()
        val intent = Intent()
        mItem!!.name = name
        intent.putExtra("item", mItem)
        setResult(RESULT_OK, intent)
        finish()
    }

    override fun onClickUpdateButton(v: View) {
        val name = (findViewById(R.id.item_name_edit) as EditText).text.toString()
        val intent = Intent()
        mItem!!.name = name
        intent.putExtra("item", mItem)
        setResult(RESULT_OK, intent)
        finish()
    }
}
