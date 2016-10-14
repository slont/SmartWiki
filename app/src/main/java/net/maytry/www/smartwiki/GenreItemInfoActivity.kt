package net.maytry.www.smartwiki

import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import net.maytry.www.smartwiki.databinding.ActivityGenreItemInfoBinding
import net.maytry.www.smartwiki.db.GenreItemInfoTableAdapter
import net.maytry.www.smartwiki.enums.GenreItemInfoType
import net.maytry.www.smartwiki.fragment.EditGenreItemInfoDialogFragment
import net.maytry.www.smartwiki.fragment.GenreItemInfoFragment
import net.maytry.www.smartwiki.model.GenreItem
import net.maytry.www.smartwiki.model.GenreItemInfo
import net.maytry.www.smartwiki.view.AnimatingRelativeLayout

/**
 * Created by slont on 9/8/16.
 *
 * GenreItemInfo画面のアクティビティ
 */
class GenreItemInfoActivity : AppCompatActivity(), GenreItemInfoFragment.OnFragmentInteractionListener, EditGenreItemInfoDialogFragment.OnFragmentInteractionListener {

    companion object {
        private val EDIT_ITEM_REQ_CODE = 100
        private val LAYERED_REQ_CODE = 200
        private val MENU_RES = R.menu.genre_item_info
    }

    private lateinit var mItem: GenreItem
    lateinit var infoList: List<GenreItemInfo>

    private val mInfoTableAdapter: GenreItemInfoTableAdapter

    private lateinit var mFragment: GenreItemInfoFragment
    private lateinit var mDialog: EditGenreItemInfoDialogFragment

    init {
        mInfoTableAdapter = GenreItemInfoTableAdapter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityGenreItemInfoBinding>(this@GenreItemInfoActivity, R.layout.activity_genre_item_info)

        mItem = intent.getSerializableExtra("item") as GenreItem
        title = mItem.name

        val toolbar = binding.toolbar
        setSupportActionBar(toolbar)
        toolbar.setNavigationIcon(R.drawable.ic_menu_back)
        toolbar.setNavigationOnClickListener { onBackPressed() }

        val animatingLayout: AnimatingRelativeLayout = binding.contentGenreItemInfo.menuAddGenreItemInfo.menuBtnLayout
        binding.contentGenreItemInfo.menuAddGenreItemInfo.showInfoMenuBtn.setOnClickListener { animatingLayout.show() }
        binding.contentGenreItemInfo.menuAddGenreItemInfo.hideInfoMenuBtn.setOnClickListener { animatingLayout.hide() }
        binding.contentGenreItemInfo.menuAddGenreItemInfo.onClickInfoMenuButton = OnClickInfoMenuButton()

        mFragment = GenreItemInfoFragment.newInstance(mItem.infoList)
        supportFragmentManager.beginTransaction().add(R.id.content_genre_item_info, mFragment).commit()
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
            mDialog.notifyDataSetChanged()
        }
        mInfoTableAdapter.open()
        val id = mInfoTableAdapter.update(info)
        if (-1 != id) {
            val list = mInfoTableAdapter.select("parent_id=${mItem.id}")
            mItem.infoList.clear()
            mItem.infoList.addAll(list)
            mFragment.notifyDataSetChanged()
        } else {
            Log.d(this.toString(), "failed update")
        }
        mInfoTableAdapter.close()
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
//            val intent = Intent(this@GenreItemInfoActivity, EditGenreItemInfoActivity::class.java)
//            intent.putExtra("item",  mItem)
//            startActivityForResult(intent, EDIT_ITEM_REQ_CODE)
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    /**
     * GenreInfoの追加アクションリスナー
     */
    private inner class OnClickInfoMenuButton() : View.OnClickListener {
        override fun onClick(v: View) {
            val type = GenreItemInfoType.strToEnum((v as Button).text.toString())
            if (null != type) {
                val info = GenreItemInfo(name = type.name, type = type)
                mItem.infoList.add(info)
                mFragment.notifyDataSetChanged()
                mInfoTableAdapter.open()
                mInfoTableAdapter.insert(info)
                mInfoTableAdapter.close()
            }
        }
    }

    /**
     * Load info data interface from @link{GenreItemInfoFragment}
     */
    override fun loadData() {
        mInfoTableAdapter.open()
        val list = mInfoTableAdapter.select("parent_id=${mItem.id}")
        mInfoTableAdapter.close()
        mItem.infoList.clear()
        mItem.infoList.addAll(list)
    }
}
