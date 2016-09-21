package net.maytry.www.smartwiki

import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.AdapterView
import android.widget.EditText
import net.maytry.www.smartwiki.databinding.ActivityAddGenreItemBinding
import net.maytry.www.smartwiki.db.GenreItemTableAdapter
import net.maytry.www.smartwiki.enums.EditType
import net.maytry.www.smartwiki.fragment.AddGenreItemContentFragment
import net.maytry.www.smartwiki.model.GenreItem

/**
 * Created by slont on 8/15/16.
 *
 * Genre item display activity.
 */
class AddGenreItemActivity : AppCompatActivity(), AddGenreItemContentFragment.OnFragmentInteractionListener {

    private val itemTableAdapter: GenreItemTableAdapter

    init {
        itemTableAdapter = GenreItemTableAdapter(this)
    }

    private var mItem: GenreItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityAddGenreItemBinding>(this@AddGenreItemActivity, R.layout.activity_add_genre_item)

        mItem = intent.getSerializableExtra("item") as GenreItem
        val type = intent.getSerializableExtra("type") as EditType

        val fragment = AddGenreItemContentFragment.newInstance(mItem!!, type!!)
        supportFragmentManager.beginTransaction().add(R.id.content_add_genre_item, fragment).commit()
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
        val id = addGenreItem(name)
        val intent = Intent()
        intent.putExtra("_id", id)
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

    private fun addGenreItem(name: String): Long {
        itemTableAdapter.open()
        val item = GenreItem(name = name)
        val id = itemTableAdapter.insert(item)
        itemTableAdapter.close()
        return id
    }
}
