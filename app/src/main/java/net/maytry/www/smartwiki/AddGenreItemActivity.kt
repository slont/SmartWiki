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

    private lateinit var mItem: GenreItem

    private lateinit var mFragment: AddGenreItemFragment

    private lateinit var mBinding: ActivityAddGenreItemBinding

    private val mItemTableAdapter = GenreItemTableAdapter(this)
    private val mInfoTableAdapter = GenreItemInfoTableAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView<ActivityAddGenreItemBinding>(this@AddGenreItemActivity, R.layout.activity_add_genre_item)

        mItem = intent.getSerializableExtra("item") as GenreItem

        val toolbar = mBinding.toolbar
        setSupportActionBar(toolbar)
        toolbar.setNavigationIcon(R.drawable.ic_menu_back)
        toolbar.setNavigationOnClickListener { onBackPressed() }

        val animatingLayout: AnimatingRelativeLayout = mBinding.contentAddGenreItem.menuAddGenreItemInfo.menuBtnLayout
        mBinding.contentAddGenreItem.menuAddGenreItemInfo.showInfoMenuBtn.setOnClickListener { animatingLayout.show() }
        mBinding.contentAddGenreItem.menuAddGenreItemInfo.hideInfoMenuBtn.setOnClickListener { animatingLayout.hide() }
        mBinding.contentAddGenreItem.menuAddGenreItemInfo.onClickInfoMenuBtn = OnClickInfoMenuBtn()

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
                addGenreItemInfo()
                val intent = Intent()
                intent.putExtra("_id", id)
                setResult(RESULT_OK, intent)
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
        val item = mItem
        item.name = name
        mItemTableAdapter.open()
        val id = mItemTableAdapter.insert(item)
        mItemTableAdapter.close()
        item.id = id
        return id
    }

    /**
     * TODO
     */
    private fun addGenreItemInfo() {
        val item = mItem
        mInfoTableAdapter.open()
        item.infoList.forEach { info ->
            info.parentId = item.id!!
            mInfoTableAdapter.insert(info)
        }
        mInfoTableAdapter.close()
    }

    private inner class OnClickInfoMenuBtn() : View.OnClickListener {
        override fun onClick(v: View) {
            val type = GenreItemInfoType.strToEnum((v as Button).text.toString())
            if (null != type) {
                mItem.infoList.add(GenreItemInfo(name = type.name, type = type))
                mFragment.notifyDataSetChanged()
            }
        }
    }
}
