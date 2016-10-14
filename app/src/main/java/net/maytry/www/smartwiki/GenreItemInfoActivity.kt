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
import net.maytry.www.smartwiki.databinding.ActivityGenreItemInfoBinding
import net.maytry.www.smartwiki.db.GenreItemInfoTableAdapter
import net.maytry.www.smartwiki.enums.GenreItemInfoType
import net.maytry.www.smartwiki.fragment.EditGenreItemInfoDialogFragment
import net.maytry.www.smartwiki.fragment.GenreItemInfoContentFragment
import net.maytry.www.smartwiki.model.GenreItem
import net.maytry.www.smartwiki.model.GenreItemInfo
import net.maytry.www.smartwiki.view.AnimatingRelativeLayout

private val MENU_RES = R.menu.genre_item_info

/**
 * Created by slont on 9/8/16.
 *
 * GenreItem画面のアクティビティ
 * メインコンテンツではGenreItemInfoの管理を行う
 */
class GenreItemInfoActivity : AppCompatActivity(), GenreItemInfoContentFragment.OnFragmentInteractionListener, EditGenreItemInfoDialogFragment.OnFragmentInteractionListener {

    private lateinit var mItem: GenreItem
    lateinit var infoList: List<GenreItemInfo>

    private val infoTableAdapter: GenreItemInfoTableAdapter

    private lateinit var fragment: GenreItemInfoContentFragment
    private lateinit var dialog: EditGenreItemInfoDialogFragment

    init {
        infoTableAdapter = GenreItemInfoTableAdapter(this)
    }

    var isEditable = false
    set(value) {
        field = value
        fragment.isEditable = value
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

        val animatingLayout: AnimatingRelativeLayout = binding.contentGenreItemInfo.menuAddGenreItemInfo.menuButtonLayout
        binding.contentGenreItemInfo.menuAddGenreItemInfo.showInfoMenuButton.setOnClickListener { animatingLayout.show() }
        binding.contentGenreItemInfo.menuAddGenreItemInfo.hideInfoMenuButton.setOnClickListener { animatingLayout.hide() }
        binding.contentGenreItemInfo.menuAddGenreItemInfo.onClickInfoMenuButton = OnClickInfoMenuButton()

        fragment = GenreItemInfoContentFragment.newInstance(mItem.infoList)
        supportFragmentManager.beginTransaction().add(R.id.content_genre_item_info, fragment).commit()
    }

    /**
     * On click info item interface from @link{GenreItemInfoContentFragment}
     */
    override fun onClickInfoListItem(parent: AdapterView<*>?, position: Int) {
        val info = parent?.getItemAtPosition(position) as GenreItemInfo
        dialog = EditGenreItemInfoDialogFragment.newInstance(info)
        dialog.show(fragmentManager, "dialog")
    }

    override fun onClickAddContentButton(position: Int) {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onClickUpdateInfoButton(info: GenreItemInfo) {
        if (GenreItemInfoType.TAG == info.type) {
            dialog.notifyDataSetChanged()
        }
        infoTableAdapter.open()
        val id = infoTableAdapter.update(info)
        if (-1 != id) {
            val list = infoTableAdapter.select("parent_id=${mItem.id}")
            mItem.infoList.clear()
            mItem.infoList.addAll(list)
            fragment.notifyDataSetChanged()
        } else {
            Log.d(this.toString(), "failed update")
        }
        infoTableAdapter.close()
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
            isEditable = !isEditable
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
                fragment.notifyDataSetChanged()
                infoTableAdapter.open()
                infoTableAdapter.insert(info)
                infoTableAdapter.close()
            }
        }
    }

    /**
     * Load info data interface from @link{GenreItemInfoContentFragment}
     */
    override fun loadData() {
        infoTableAdapter.open()
        val list = infoTableAdapter.select("parent_id=${mItem.id}")
        infoTableAdapter.close()
        mItem.infoList.clear()
        mItem.infoList.addAll(list)
    }
}
