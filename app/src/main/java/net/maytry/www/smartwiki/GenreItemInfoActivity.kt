package net.maytry.www.smartwiki

import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.AdapterView
import net.maytry.www.smartwiki.databinding.ActivityGenreItemInfoBinding
import net.maytry.www.smartwiki.db.GenreItemInfoTableAdapter
import net.maytry.www.smartwiki.enums.GenreItemInfoType
import net.maytry.www.smartwiki.fragment.EditGenreItemInfoDialogFragment
import net.maytry.www.smartwiki.fragment.GenreItemInfoFragment
import net.maytry.www.smartwiki.model.GenreItem
import net.maytry.www.smartwiki.model.GenreItemInfo

/**
 * Created by slont on 9/8/16.
 *
 * GenreItemInfo画面のアクティビティ
 */
class GenreItemInfoActivity : AppCompatActivity(), GenreItemInfoFragment.OnFragmentInteractionListener, EditGenreItemInfoDialogFragment.OnFragmentInteractionListener {

    companion object {
        private const val EDIT_ITEM_REQ_CODE = 100
        private const val LAYERED_REQ_CODE = 200
        private const val MENU_RES = R.menu.genre_item_info
        private val LAYOUT_RES = R.layout.activity_genre_item_info
    }

    private lateinit var mItem: GenreItem
    lateinit var infoList: List<GenreItemInfo>

    private val mInfoTableAdapter = GenreItemInfoTableAdapter(this)

    private lateinit var mFragment: GenreItemInfoFragment
    private lateinit var mDialog: EditGenreItemInfoDialogFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mItem = intent.getSerializableExtra("item") as GenreItem
        title = mItem.name
        DataBindingUtil.setContentView<ActivityGenreItemInfoBinding>(this, LAYOUT_RES).run {
            contentGenreItemInfo.headerGenreItemInfo.run {
                item = mItem
                backBtn.setOnClickListener { onBackPressed() }
                editBtn.setOnClickListener {
                    val intent = Intent(this@GenreItemInfoActivity, EditGenreItemInfoActivity::class.java)
                    intent.putExtra("item",  mItem)
                    startActivityForResult(intent, EDIT_ITEM_REQ_CODE)
                }
            }
        }

        mFragment = GenreItemInfoFragment.newInstance(mItem.infoList)
        supportFragmentManager.beginTransaction().add(R.id.fragment_genre_item_info, mFragment).commit()
    }

    /**
     * On click info item interface from @link{GenreItemInfoFragment}
     */
    override fun onClickInfo(parent: AdapterView<*>?, position: Int) {
        val info = parent?.getItemAtPosition(position) as GenreItemInfo
        mDialog = EditGenreItemInfoDialogFragment.newInstance(info)
        mDialog.show(fragmentManager, "dialog")
    }

    override fun onClickAddContentBtn(position: Int) {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onClickUpdateInfoBtn(info: GenreItemInfo) {
        if (GenreItemInfoType.TAG == info.type) {
            mDialog.safeSaveForMulti()
        } else {
            mDialog.safeSaveForSingle()
        }
        mInfoTableAdapter.run {
            open()
            val id = update(info)
            if (-1 != id) {
                val list = find("parent_id=${mItem.id}")
                mItem.infoList.clear()
                mItem.infoList.addAll(list)
                mFragment.notifyDataSetChanged()
            } else {
                Log.d(this.toString(), "failed update")
            }
            close()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    /**
     * ツールバーのメニュー
     */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(MENU_RES, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == R.id.action_edit) {
            val intent = Intent(this, EditGenreItemInfoActivity::class.java)
            intent.putExtra("item",  mItem)
            startActivityForResult(intent, EDIT_ITEM_REQ_CODE)
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            EDIT_ITEM_REQ_CODE -> {
                when (resultCode) {
                    RESULT_OK -> {
                        loadData()
                        mFragment.notifyDataSetChanged()
                    }
                    RESULT_CANCELED -> {}
                }
            }
            else -> {}
        }
    }

    /**
     * Load info data interface from @link{GenreItemInfoFragment}
     */
    override fun loadData() {
        mInfoTableAdapter.run {
            open()
            val list = find("parent_id=${mItem.id}")
            close()
            mItem.infoList.clear()
            mItem.infoList.addAll(list)
        }
    }
}
