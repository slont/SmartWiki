package net.maytry.www.smartwiki

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.RelativeLayout
import net.maytry.www.smartwiki.databinding.ActivityEditGenreItemInfoBinding
import net.maytry.www.smartwiki.db.GenreItemInfoTableAdapter
import net.maytry.www.smartwiki.enums.GenreItemInfoType
import net.maytry.www.smartwiki.fragment.EditGenreItemInfoDialogFragment
import net.maytry.www.smartwiki.fragment.EditGenreItemInfoFragment
import net.maytry.www.smartwiki.model.GenreItem
import net.maytry.www.smartwiki.model.GenreItemInfo
import net.maytry.www.smartwiki.view.AnimatingRelativeLayout
import net.maytry.www.smartwiki.viewmodel.GenreItemInfoAdapter

/**
 * Created by slont on 10/15/16.
 *
 * GenreItemInfo編集画面のアクティビティ
 */
class EditGenreItemInfoActivity : AppCompatActivity(), EditGenreItemInfoFragment.OnFragmentInteractionListener, EditGenreItemInfoDialogFragment.OnFragmentInteractionListener {

    companion object {
        private val EDIT_ITEM_REQ_CODE = 100
        private val LAYERED_REQ_CODE = 200
        private val MENU_RES = R.menu.close_edit_genre_item_info
    }

    private lateinit var mItem: GenreItem
    private var mAddedInfoList = mutableListOf<GenreItemInfo>()

    private val mInfoTableAdapter: GenreItemInfoTableAdapter

    private lateinit var mFragment: EditGenreItemInfoFragment
    private lateinit var mDialog: EditGenreItemInfoDialogFragment

    init {
        mInfoTableAdapter = GenreItemInfoTableAdapter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityEditGenreItemInfoBinding>(this@EditGenreItemInfoActivity, R.layout.activity_edit_genre_item_info)
        val contentBinding = binding.contentEditGenreItemInfo
        mItem = intent.getSerializableExtra("item") as GenreItem
        title = mItem.name

        val animatingLayout: AnimatingRelativeLayout = contentBinding.menuAddGenreItemInfo.menuBtnLayout
        contentBinding.deleteSelectedBtn.setOnClickListener { }
        contentBinding.menuAddGenreItemInfo.isVisibleShowBtn = false
        contentBinding.menuAddGenreItemInfo.onClickInfoMenuBtn = OnClickInfoMenuBtn()
        contentBinding.menuAddGenreItemInfo.hideInfoMenuBtn.setOnClickListener { animatingLayout.hide() }
        contentBinding.headerEditGenreItemInfo.item = mItem
        contentBinding.headerEditGenreItemInfo.addBtn.setOnClickListener { animatingLayout.show() }
        contentBinding.headerEditGenreItemInfo.deleteBtn.setOnClickListener {}
        contentBinding.headerEditGenreItemInfo.saveBtn.setOnClickListener {
            mInfoTableAdapter.open()
            mInfoTableAdapter.insertAll(mAddedInfoList)
            mInfoTableAdapter.close()
            setResult(RESULT_OK)
            finish()
        }
        contentBinding.headerEditGenreItemInfo.closeBtn.setOnClickListener {
            setResult(RESULT_OK)
            finish()
        }
        mFragment = EditGenreItemInfoFragment.newInstance(mItem.infoList)
        supportFragmentManager.beginTransaction().add(R.id.fragment_edit_genre_item_info, mFragment).commit()
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
            val list = mInfoTableAdapter.find("parent_id=${mItem.id}")
            mItem.infoList.clear()
            mItem.infoList.addAll(list)
            mFragment.notifyDataSetChanged()
        } else {
            Log.d(this.toString(), "failed update")
        }
        mInfoTableAdapter.close()
    }

    /**
     * ツールバーのメニュー
     */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(MENU_RES, menu)
        return true
    }

    /**
     * GenreInfoの追加アクションリスナー
     */
    private inner class OnClickInfoMenuBtn() : View.OnClickListener {
        override fun onClick(v: View) {
            val type = GenreItemInfoType.strToEnum((v as Button).text.toString())
            if (null != type) {
                val info = GenreItemInfo(
                        name = type.name,
                        parentId = mItem.id!!,
                        type = type)
                mItem.infoList.add(info)
                mAddedInfoList.add(info)
                mFragment.notifyDataSetChanged()
            }
        }
    }

    /**
     * Load info data interface from @link{GenreItemInfoFragment}
     */
    override fun loadData() {
        mInfoTableAdapter.open()
        val list = mInfoTableAdapter.find("parent_id=${mItem.id}")
        mInfoTableAdapter.close()
        mItem.infoList.clear()
        mItem.infoList.addAll(list)
    }
}
