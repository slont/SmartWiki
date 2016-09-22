package net.maytry.www.smartwiki

import android.content.Intent
import android.content.res.Configuration
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.EditText
import net.maytry.www.smartwiki.databinding.ActivityAddGenreItemBinding
import net.maytry.www.smartwiki.db.GenreItemInfoTableAdapter
import net.maytry.www.smartwiki.db.GenreItemTableAdapter
import net.maytry.www.smartwiki.enums.EditType
import net.maytry.www.smartwiki.enums.GenreItemInfoType
import net.maytry.www.smartwiki.fragment.AddGenreItemContentFragment
import net.maytry.www.smartwiki.layout.AnimatingRelativeLayout
import net.maytry.www.smartwiki.model.Genre
import net.maytry.www.smartwiki.model.GenreItem
import net.maytry.www.smartwiki.model.GenreItemInfo

/**
 * Created by slont on 8/15/16.
 *
 * Genre item display activity.
 */
class AddGenreItemActivity : AppCompatActivity(), AddGenreItemContentFragment.OnFragmentInteractionListener {

    private val itemTableAdapter: GenreItemTableAdapter
    private val infoTableAdapter: GenreItemInfoTableAdapter

    init {
        itemTableAdapter = GenreItemTableAdapter(this)
        infoTableAdapter = GenreItemInfoTableAdapter(this)
    }

    private lateinit var mItem: GenreItem
    private var mInfoList: MutableList<GenreItemInfo> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityAddGenreItemBinding>(this@AddGenreItemActivity, R.layout.activity_add_genre_item)

        mItem = intent.getSerializableExtra("item") as GenreItem
        val type = intent.getSerializableExtra("type") as EditType

        val toolbar = binding.toolbar
        setSupportActionBar(toolbar)
        toolbar.setNavigationIcon(R.drawable.ic_menu_back)
        toolbar.setNavigationOnClickListener { onBackPressed() }

        val animatingLayout: AnimatingRelativeLayout = binding.contentAddGenreItem.menuAddGenreItemInfo.menuButtonLayout
        animatingLayout.hide()
        binding.contentAddGenreItem.menuAddGenreItemInfo.showInfoMenuButton.setOnClickListener { animatingLayout.show() }
        binding.contentAddGenreItem.menuAddGenreItemInfo.hideInfoMenuButton.setOnClickListener { animatingLayout.hide() }
        val lis: OnClickInfoMenuButton = OnClickInfoMenuButton()
        binding.contentAddGenreItem.menuAddGenreItemInfo.onClickInfoMenuButton = lis

        val fragment = AddGenreItemContentFragment.newInstance(mItem, type)
        supportFragmentManager.beginTransaction().add(R.id.content_add_genre_item, fragment).commit()
    }

    override fun onBackPressed() {
        setResult(RESULT_CANCELED, Intent())
        finish()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.add_genre_item_info, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_save) {
            return true
        }

        return super.onOptionsItemSelected(item)
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
        addGenreItemInfo()
        val intent = Intent()
        intent.putExtra("_id", id)
        setResult(RESULT_OK, intent)
        finish()
    }

    override fun onClickUpdateButton(v: View) {
        val name = (findViewById(R.id.item_name_edit) as EditText).text.toString()
        val intent = Intent()
        mItem.name = name
        intent.putExtra("item", mItem)
        setResult(RESULT_OK, intent)
        finish()
    }

    override fun openOptionsMenu() {
        super.openOptionsMenu()
        val config = resources.configuration
        if (config.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK > Configuration.SCREENLAYOUT_SIZE_LARGE) {
            val originalScreenLayout = config.screenLayout
            config.screenLayout = Configuration.SCREENLAYOUT_SIZE_LARGE
            super.openOptionsMenu()
            config.screenLayout = originalScreenLayout
        } else {
            super.openOptionsMenu()
        }
    }

    private fun addGenreItem(name: String): Long {
        val item = mItem
        itemTableAdapter.open()
        val id = itemTableAdapter.insert(GenreItem(name = name, parentId = item.parentId!!))
        itemTableAdapter.close()
        mItem.id = id
        return id
    }

    /**
     * TODO
     */
    private fun addGenreItemInfo() {
        val item = mItem
        infoTableAdapter.open()
        item.infoList.forEach { info ->
            info.parentId = item.id!!
            infoTableAdapter.insert(info)
        }
        infoTableAdapter.close()
    }

    private inner class OnClickInfoMenuButton() : View.OnClickListener {
        override fun onClick(v: View) {
            val type = GenreItemInfoType.strToEnum((v as Button).text.toString())
            if (null != type) {
                mItem.infoList.add(GenreItemInfo(name = type.name, type = type))
            }
        }
    }
}
