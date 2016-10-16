package net.maytry.www.smartwiki

import android.content.Intent
import android.content.res.Configuration
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AlertDialog
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
import net.maytry.www.smartwiki.enums.GenreItemInfoType
import net.maytry.www.smartwiki.fragment.AddGenreItemFragment
import net.maytry.www.smartwiki.view.AnimatingRelativeLayout
import net.maytry.www.smartwiki.model.GenreItem
import net.maytry.www.smartwiki.model.GenreItemInfo

/**
 * Created by slont on 8/15/16.
 *
 * Genre item display activity.
 */
class AddGenreItemActivity : AppCompatActivity(), AddGenreItemFragment.OnFragmentInteractionListener {

    companion object {
        private const val LAYOUT_RES = R.layout.activity_add_genre_item
    }

    private lateinit var mItem: GenreItem

    private lateinit var mFragment: AddGenreItemFragment

    private lateinit var mBinding: ActivityAddGenreItemBinding

    private val mItemTableAdapter = GenreItemTableAdapter(this)
    private val mInfoTableAdapter = GenreItemInfoTableAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mItem = intent.getSerializableExtra("item") as GenreItem

        mBinding = DataBindingUtil.setContentView<ActivityAddGenreItemBinding>(this, LAYOUT_RES).apply {
            setSupportActionBar(toolbar)
            toolbar.setNavigationIcon(R.drawable.ic_menu_back)
            toolbar.setNavigationOnClickListener { onBackPressed() }

            contentAddGenreItem.menuAddGenreItemInfo.run {
                val animatingLayout: AnimatingRelativeLayout = menuBtnLayout
                showInfoMenuBtn.setOnClickListener { animatingLayout.show() }
                hideInfoMenuBtn.setOnClickListener { animatingLayout.hide() }
                onClickInfoMenuBtn = OnClickInfoMenuBtn()
            }
        }

        mFragment = AddGenreItemFragment.newInstance(mItem)
        supportFragmentManager.beginTransaction().add(R.id.content_add_genre_item, mFragment).commit()
    }

    override fun onBackPressed() {
        setResult(RESULT_CANCELED, Intent())
        finish()
    }

    /**
     * ツールバーのメニュー
     */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.add_genre_item_info, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == R.id.action_save) {
            val name = (findViewById(R.id.item_name_edit) as EditText).text.toString()
            if (name.isNullOrBlank()) {
                // TODO:切り出し
                AlertDialog.Builder(this)
                        .setTitle("エラー")
                        .setMessage("タイトルが空です")
                        .setPositiveButton("OK") { dialog, which -> }
                        .create().show()
            } else {
                val id = addGenreItem(name)
                setResult(RESULT_OK, Intent().apply { putExtra("_id", id) })
                finish()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onClickGenreItem(parent: AdapterView<*>?, position: Int) {
//        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
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
        mItemTableAdapter.run {
            mItem.name = name
            open()
            val id = insert(mItem)
            close()
            mItem.id = id
            addGenreItemInfo()
            return id
        }
    }

    /**
     * TODO
     */
    private fun addGenreItemInfo() {
        mInfoTableAdapter.run {
            open()
            mItem.infoList.forEach { info ->
                info.parentId = mItem.id!!
                insert(info)
            }
            close()
        }
    }

    private inner class OnClickInfoMenuBtn() : View.OnClickListener {
        override fun onClick(v: View) {
            GenreItemInfoType.strToEnum((v as Button).text.toString())?.let { type ->
                mItem.infoList.add(GenreItemInfo(name = type.name, type = type))
                mFragment.notifyDataSetChanged()
            }
        }
    }
}
